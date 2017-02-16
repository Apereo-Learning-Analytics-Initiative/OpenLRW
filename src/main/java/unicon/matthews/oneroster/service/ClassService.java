package unicon.matthews.oneroster.service;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicon.matthews.oneroster.Class;
import unicon.matthews.oneroster.Enrollment;
import unicon.matthews.oneroster.LineItem;
import unicon.matthews.oneroster.exception.EnrollmentNotFoundException;
import unicon.matthews.oneroster.exception.LineItemNotFoundException;
import unicon.matthews.oneroster.service.repository.MongoClass;
import unicon.matthews.oneroster.service.repository.MongoClassRepository;

@Service
public class ClassService {
  
  private MongoClassRepository mongoClassRepository;
  private EnrollmentService enrollmentService;
  private LineItemService lineItemService;
  
  @Autowired
  public ClassService(MongoClassRepository mongoClassRepository,
      EnrollmentService enrollmentService,
      LineItemService lineItemService) {
    this.mongoClassRepository = mongoClassRepository;
    this.enrollmentService = enrollmentService;
    this.lineItemService = lineItemService;
  }

  public Class save(final String tenantId, final String orgId, Class klass) {
    if (StringUtils.isBlank(tenantId) 
        || StringUtils.isBlank(orgId)
        || klass == null
        || StringUtils.isBlank(klass.getSourcedId())
        || StringUtils.isBlank(klass.getTitle())) {
      throw new IllegalArgumentException();
    }
    
    MongoClass mongoClass
      = mongoClassRepository
        .findByTenantIdAndOrgIdAndClassSourcedId(tenantId, orgId, klass.getSourcedId());
    
    
    if (mongoClass == null) {
      mongoClass 
        = new MongoClass.Builder()
          .withClassSourcedId(klass.getSourcedId())
          .withOrgId(orgId)
          .withTenantId(tenantId)
          .withKlass(klass)
          .build();
    }
    else {
      mongoClass
        = new MongoClass.Builder()
          .withId(mongoClass.getId())
          .withClassSourcedId(mongoClass.getClassSourcedId())
          .withOrgId(mongoClass.getOrgId())
          .withTenantId(mongoClass.getTenantId())
          .withKlass(klass)
          .build();
    }
    
    MongoClass saved = mongoClassRepository.save(mongoClass);
    
    try {
      Collection<Enrollment> classEnrollments = enrollmentService.findEnrollmentsForClass(tenantId, orgId, mongoClass.getClassSourcedId());
      
      if (classEnrollments != null) {
        for (Enrollment enrollment : classEnrollments) {
          Enrollment updatedEnrollment
            = new Enrollment.Builder()
              .withKlass(mongoClass.getKlass())
              .withMetadata(enrollment.getMetadata())
              .withPrimary(enrollment.isPrimary())
              .withRole(enrollment.getRole())
              .withSourcedId(enrollment.getSourcedId())
              .withStatus(enrollment.getStatus())
              .withUser(enrollment.getUser())
              .build();
          
          enrollmentService.save(tenantId, orgId, updatedEnrollment);
        }
      }
      
    } 
    catch (EnrollmentNotFoundException e) {
      // TODO
      e.printStackTrace();
    }
    
    try {
      Collection<LineItem> classLineItems = lineItemService.getLineItemsForClass(tenantId, orgId, mongoClass.getClassSourcedId());
      
      if (classLineItems != null) {
        for (LineItem li : classLineItems) {
          LineItem updatedLineItem
            = new LineItem.Builder()
              .withAssignDate(li.getAssignDate())
              .withCategory(li.getCategory())
              .withClass(mongoClass.getKlass())
              .withDescription(li.getDescription())
              .withDueDate(li.getDueDate())
              .withMetadata(li.getMetadata())
              .withSourcedId(li.getSourcedId())
              .withStatus(li.getStatus())
              .withTitle(li.getTitle())
              .build();
          
          lineItemService.save(tenantId, orgId, updatedLineItem);
        }
      }
    } 
    catch (LineItemNotFoundException e) {
      // TODO 
      e.printStackTrace();
    }
    
   return saved.getKlass(); 

  }
}
