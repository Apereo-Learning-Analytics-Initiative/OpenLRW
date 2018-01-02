/**
 * 
 */
package unicon.matthews.oneroster.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicon.matthews.oneroster.Class;
import unicon.matthews.oneroster.Enrollment;
import unicon.matthews.oneroster.Status;
import unicon.matthews.oneroster.User;
import unicon.matthews.oneroster.exception.EnrollmentNotFoundException;
import unicon.matthews.oneroster.service.repository.MongoEnrollment;
import unicon.matthews.oneroster.service.repository.MongoEnrollmentRepository;

/**
 * @author ggilbert
 *
 */
@Service
public class EnrollmentService {
  
  private MongoEnrollmentRepository mongoEnrollmentRepository;
  
  @Autowired
  public EnrollmentService(MongoEnrollmentRepository mongoEnrollmentRepository) {
    this.mongoEnrollmentRepository = mongoEnrollmentRepository;
  }

  public Enrollment save(final String tenantId, final String orgId, Enrollment enrollment) {
    
    if (StringUtils.isBlank(tenantId) 
        || StringUtils.isBlank(orgId)
        || enrollment == null
        || enrollment.getKlass() == null
        || StringUtils.isBlank(enrollment.getKlass().getSourcedId())
        || enrollment.getUser() == null
        || StringUtils.isBlank(enrollment.getUser().getSourcedId())) {
      throw new IllegalArgumentException();
    }
    
    unicon.matthews.oneroster.Class klassLink =
        new Class.Builder()
          .withSourcedId(enrollment.getKlass().getSourcedId())
          .build();
    
    User userLink =
        new User.Builder()
          .withSourcedId(enrollment.getUser().getSourcedId())
          .build();
    
    Enrollment enrollmentWithLinks =
        new Enrollment.Builder()
          .withKlass(klassLink)
          .withMetadata(enrollment.getMetadata())
          .withPrimary(enrollment.isPrimary())
          .withRole(enrollment.getRole())
          .withSourcedId(enrollment.getSourcedId())
          .withStatus(enrollment.getStatus())
          .withUser(userLink)
          .build();
    
    MongoEnrollment mongoEnrollment
      = mongoEnrollmentRepository
        .findByTenantIdAndOrgIdAndClassSourcedIdAndUserSourcedIdIgnoreCase(tenantId, orgId, enrollmentWithLinks.getKlass().getSourcedId(), enrollmentWithLinks.getUser().getSourcedId());
    
    
    if (mongoEnrollment == null) {
      mongoEnrollment = convert(tenantId, orgId, 
          enrollmentWithLinks.getKlass().getSourcedId(), enrollmentWithLinks.getUser().getSourcedId(), enrollmentWithLinks);
    }
    else {
      mongoEnrollment
        = new MongoEnrollment.Builder()
          .withId(mongoEnrollment.getId())
          .withClassSourcedId(mongoEnrollment.getClassSourcedId())
          .withEnrollment(enrollmentWithLinks)
          .withOrgId(mongoEnrollment.getOrgId())
          .withTenantId(mongoEnrollment.getTenantId())
          .withUserSourcedId(mongoEnrollment.getUserSourcedId())
          .build();
    }
    
    MongoEnrollment savedMongoEnrollment = mongoEnrollmentRepository.save(mongoEnrollment);
    
    return savedMongoEnrollment.getEnrollment(); 
  }
  
  public Collection<Enrollment> findEnrollmentsForClass(final String tenantId, final String orgId, 
      final String classSourcedId) throws EnrollmentNotFoundException {
    Collection<MongoEnrollment> mongoEnrollments 
      = mongoEnrollmentRepository.findByTenantIdAndOrgIdAndClassSourcedIdAndEnrollmentStatus(tenantId, orgId, classSourcedId, Status.active);
    
    if (mongoEnrollments != null && !mongoEnrollments.isEmpty()) {
      return mongoEnrollments.stream().map(MongoEnrollment::getEnrollment).collect(Collectors.toList());
    }
    throw new EnrollmentNotFoundException();
  }
  
  public Collection<Enrollment> findEnrollmentsForUser(final String tenantId, final String orgId, 
      final String userSourcedId) throws EnrollmentNotFoundException {
    
    Collection<MongoEnrollment> mongoEnrollments
      = mongoEnrollmentRepository.findByTenantIdAndOrgIdAndUserSourcedIdIgnoreCaseAndEnrollmentStatus(tenantId, orgId, userSourcedId, Status.active);
    if (mongoEnrollments != null && !mongoEnrollments.isEmpty()) {
      return mongoEnrollments.stream().map(MongoEnrollment::getEnrollment).collect(Collectors.toList());
    }
    throw new EnrollmentNotFoundException();
  }
  
  private MongoEnrollment convert(final String tenantId, final String orgId, 
      final String classSourcedId, final String userSourcedId, Enrollment enrollment) {
    MongoEnrollment mongoEnrollment
      = new MongoEnrollment.Builder()
        .withClassSourcedId(classSourcedId)
        .withEnrollment(enrollment)
        .withOrgId(orgId)
        .withTenantId(tenantId)
        .withUserSourcedId(userSourcedId)
        .build();
    
    return mongoEnrollment;
  }
  
}
