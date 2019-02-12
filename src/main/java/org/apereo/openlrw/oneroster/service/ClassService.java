package org.apereo.openlrw.oneroster.service;

import org.apache.commons.lang3.StringUtils;
import org.apereo.model.oneroster.Class;
import org.apereo.openlrw.oneroster.service.repository.MongoClass;
import org.apereo.openlrw.oneroster.service.repository.MongoClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@Service
public class ClassService {
  
  private static Logger logger = LoggerFactory.getLogger(ClassService.class);
  
  private MongoClassRepository mongoClassRepository;
  
  @Autowired
  public ClassService(MongoClassRepository mongoClassRepository) {
    this.mongoClassRepository = mongoClassRepository;
  }
  
  public Class findBySourcedId(final String tenantId, final String orgId, final String classSourcedId) {
    MongoClass mongoClass
      =  mongoClassRepository
        .findByTenantIdAndOrgIdAndClassSourcedId(tenantId, orgId, classSourcedId);
    
    if (mongoClass != null) {
      return mongoClass.getKlass();
    }
    
    return null;
  }

  /**
   * Finds and returns all the classes that belong to a tenant and an organization given
   *
   * @param tenantId  an id of a tenant
   * @param orgId     an id of an organization
   * @return          the classes
   */
  public Collection<MongoClass> findAll(final String tenantId, final String orgId) {
    if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId))
      throw new IllegalArgumentException();

    return mongoClassRepository.findByTenantIdAndOrgId(tenantId, orgId);
  }
  
  public Collection<Class> findClassesForCourse(final String tenantId, final String orgId,
      final String courseSourcedId) {
    Collection<MongoClass> mongoClasses 
      = mongoClassRepository.findByTenantIdAndOrgIdAndKlassCourseSourcedId(tenantId, orgId, courseSourcedId);
    
    if (mongoClasses != null && !mongoClasses.isEmpty()) {
      return mongoClasses.stream().map(MongoClass::getKlass).collect(Collectors.toList());
    }
    return null;
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
        
    return saved.getKlass(); 

  }
}
