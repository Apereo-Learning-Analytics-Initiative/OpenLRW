package org.apereo.openlrw.oneroster.service;

import org.apache.commons.lang3.StringUtils;
import org.apereo.model.oneroster.Enrollment;
import org.apereo.model.oneroster.Link;
import org.apereo.model.oneroster.Status;
import org.apereo.model.oneroster.User;
import org.apereo.openlrw.oneroster.exception.EnrollmentNotFoundException;
import org.apereo.openlrw.oneroster.service.repository.MongoEnrollment;
import org.apereo.openlrw.oneroster.service.repository.MongoEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 *
 */
@Service
public class EnrollmentService {
  
  private MongoEnrollmentRepository mongoEnrollmentRepository;
  private final MongoOperations mongoOps;
  
  @Autowired
  public EnrollmentService(MongoEnrollmentRepository mongoEnrollmentRepository, MongoOperations mongoOperations) {
    this.mongoEnrollmentRepository = mongoEnrollmentRepository;
    this.mongoOps = mongoOperations;
  }

  /**
   * Record an Enrollment
   *
   * @param tenantId
   * @param orgId
   * @param classId
   * @param enrollment
   * @param check
   * @return
   */
  public Enrollment save(final String tenantId, final String orgId, final String classId, Enrollment enrollment, boolean check) {

    if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId) || enrollment == null || enrollment.getUser() == null || StringUtils.isBlank(enrollment.getUser().getSourcedId())) {
      throw new IllegalArgumentException();
    }


    Link linkClass = new Link.Builder().withSourcedId(classId).build();
    
    Link linkUser = new Link.Builder().withSourcedId(enrollment.getUser().getSourcedId()).build();

    Enrollment buildEnrollment = new Enrollment.Builder()
          .withKlass(linkClass)
          .withDateLastModified(Instant.now())
          .withMetadata(enrollment.getMetadata())
          .withPrimary(enrollment.isPrimary())
          .withRole(enrollment.getRole())
          .withSourcedId(enrollment.getSourcedId())
          .withStatus(enrollment.getStatus())
          .withBeginDate(enrollment.getBeginDate())
          .withEndDate(enrollment.getEndDate())
          .withUser(linkUser)
          .build();


    MongoEnrollment mongoEnrollment = null;

    if (check) {
      mongoEnrollment = mongoEnrollmentRepository.findByTenantIdAndOrgIdAndClassSourcedIdAndUserSourcedIdIgnoreCase(tenantId, orgId, buildEnrollment.getKlass().getSourcedId(), buildEnrollment.getUser().getSourcedId());
    }

    if (mongoEnrollment == null) {
      mongoEnrollment = new MongoEnrollment.Builder()
              .withClassSourcedId(buildEnrollment.getKlass().getSourcedId())
              .withEnrollment(buildEnrollment)
              .withOrgId(orgId)
              .withTenantId(tenantId)
              .withUserSourcedId(buildEnrollment.getUser().getSourcedId())
              .build();
    } else {
      mongoEnrollment = new MongoEnrollment.Builder()
          .withId(mongoEnrollment.getId())
          .withClassSourcedId(mongoEnrollment.getClassSourcedId())
          .withEnrollment(buildEnrollment)
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
    throw new EnrollmentNotFoundException("Enrollment not found.");
  }
  
  public Collection<Enrollment> findEnrollmentsForUser(final String tenantId, final String orgId, final String userSourcedId) throws EnrollmentNotFoundException {

    Collection<MongoEnrollment> mongoEnrollments;
    Query query = new Query();

    query.addCriteria(where("userSourcedId").is(userSourcedId).and("orgId").is(orgId).and("tenantId").is(tenantId));

    mongoEnrollments= mongoOps.find(query, MongoEnrollment.class);

    if (!mongoEnrollments.isEmpty())
      return mongoEnrollments.stream().map(MongoEnrollment::getEnrollment).collect(Collectors.toList());

    throw new EnrollmentNotFoundException("Enrollment not found.");
  }

  
  public List<String> findUniqueUserIdsWithRole(final String tenantId, final String orgId, final String role) throws EnrollmentNotFoundException {
    List<String> userIds = new ArrayList<>();
    Collection<MongoEnrollment> enrollments = mongoEnrollmentRepository.findByTenantIdAndOrgIdAndEnrollmentRole(tenantId, orgId, role);
    for(MongoEnrollment mongoEnrollment: enrollments) {
        if(!userIds.contains(mongoEnrollment.getEnrollment().getUser().getSourcedId())) {
          userIds.add(mongoEnrollment.getEnrollment().getUser().getSourcedId());
        }
    }
    return userIds;
  }


  /**
   * Delete an enrollment
   *
   * @param tenantId      tenant id
   * @param orgId         organization id
   * @param enrollmentId  its Id
   * @return               boolean (if it has been deleted)
   */
  public boolean delete(final String tenantId, final String orgId, final String enrollmentId) {
    if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId) || StringUtils.isBlank(enrollmentId))
      throw new IllegalArgumentException();

    return mongoEnrollmentRepository.deleteByTenantIdAndOrgIdAndEnrollmentSourcedId(tenantId, orgId, enrollmentId) > 0;
  }

  /**
   * Delete all enrollments
   *
   * @param tenantId      tenant id
   * @param orgId         organization id
   * @return              boolean
   */
  public boolean deleteAll(final String tenantId, final String orgId) {
    if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId))
      throw new IllegalArgumentException();

    return mongoEnrollmentRepository.deleteAllByTenantIdAndOrgId(tenantId, orgId) > 0;
  }
  
}
