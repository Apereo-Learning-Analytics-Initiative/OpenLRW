package unicon.matthews.oneroster.service.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoClassRepository extends MongoRepository<MongoClass, String> {
  MongoClass 
    findByTenantIdAndOrgIdAndClassSourcedId(final String tenantId, final String orgId,
      final String classSourcedId);
  
  Collection<MongoClass> findByTenantIdAndOrgIdAndKlassCourseSourcedId(final String tenantId, final String orgId, final String courseSourcedId);
}
