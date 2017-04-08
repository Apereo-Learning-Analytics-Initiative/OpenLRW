package unicon.matthews.oneroster.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicon.matthews.oneroster.AcademicSession;
import unicon.matthews.oneroster.exception.AcademicSessionNotFoundException;
import unicon.matthews.oneroster.service.repository.MongoAcademicSession;
import unicon.matthews.oneroster.service.repository.MongoAcademicSessionRepository;

/**
 * @author stalele
 *
 */
@Service
public class AcademicSessionService {
  private MongoAcademicSessionRepository mongoAcademicSessionRepository;
  
  /**Constructor 
   * @param mongoAcademicSessionRepository
   * @param classService
   */
  @Autowired
  public AcademicSessionService(MongoAcademicSessionRepository mongoAcademicSessionRepository, 
      ClassService classService) {
    this.mongoAcademicSessionRepository = mongoAcademicSessionRepository;
  }

  
  /** Find academic session from sourcedId
   * @param tenantId
   * @param orgId
   * @param academicSessionSourcedId
   * @return AcademicSession object
   * @throws AcademicSessionNotFoundException
   */
  public AcademicSession findBySourcedId(final String tenantId, final String orgId, final String academicSessionSourcedId) throws AcademicSessionNotFoundException {
    MongoAcademicSession mongoAcademicSession
      =  mongoAcademicSessionRepository
        .findByTenantIdAndOrgIdAndAcademicSessionSourcedId(tenantId, orgId, academicSessionSourcedId);
    
    if (mongoAcademicSession != null) {
      return mongoAcademicSession.getAcademicSession();
    }
    
    throw new AcademicSessionNotFoundException(String.format("Academic Session not found for sourcedId %s", academicSessionSourcedId));
  }

  
  /** Saves the academic session, if it does not exist. If it exists, it updates the existing academic session object
   * @param tenantId
   * @param orgId
   * @param academicSession
   * @return AcademicSession
   */
  public AcademicSession save(final String tenantId, final String orgId, AcademicSession academicSession) {
    if (StringUtils.isBlank(tenantId) 
        || StringUtils.isBlank(orgId)
        || academicSession == null
        || StringUtils.isBlank(academicSession.getSourcedId())) {
      throw new IllegalArgumentException();
    }
    
    MongoAcademicSession mongoAcademicSession
    =  mongoAcademicSessionRepository
      .findByTenantIdAndOrgIdAndAcademicSessionSourcedId(tenantId, orgId, academicSession.getSourcedId());
    
    if (mongoAcademicSession == null) {
      mongoAcademicSession 
        = new MongoAcademicSession.Builder()
          .withAcademicSessionSourcedId(academicSession.getSourcedId())
          .withOrgId(orgId)
          .withTenantId(tenantId)
          .withAcademicSession(academicSession)
          .build();
    }
    else {
      mongoAcademicSession
        = new MongoAcademicSession.Builder()
          .withId(mongoAcademicSession.getId())
          .withAcademicSessionSourcedId(mongoAcademicSession.getAcademicSessionSourcedId())
          .withOrgId(mongoAcademicSession.getOrgId())
          .withTenantId(mongoAcademicSession.getTenantId())
          .withAcademicSession(academicSession)
          .build();
    }
    
    MongoAcademicSession saved = mongoAcademicSessionRepository.save(mongoAcademicSession);

    return saved.getAcademicSession(); 

  }

}
