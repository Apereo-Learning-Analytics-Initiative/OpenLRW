package unicon.matthews.oneroster.service;

import java.util.Collection;
import java.util.concurrent.Callable;

import unicon.matthews.oneroster.Enrollment;
import unicon.matthews.oneroster.exception.EnrollmentNotFoundException;
import unicon.matthews.oneroster.service.repository.MongoClass;

public class ClassEnrollmentUpdater implements Callable<Boolean> {
  
  private EnrollmentService enrollmentService;
  private String tenantId; 
  private String orgId;
  private MongoClass mongoClass;
  

  public ClassEnrollmentUpdater(EnrollmentService enrollmentService, String tenantId, String orgId, MongoClass mongoClass) {
    super();
    this.enrollmentService = enrollmentService;
    this.tenantId = tenantId;
    this.orgId = orgId;
    this.mongoClass = mongoClass;
  }


  @Override
  public Boolean call() throws Exception {
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
    }
    
    return true;
  }

}
