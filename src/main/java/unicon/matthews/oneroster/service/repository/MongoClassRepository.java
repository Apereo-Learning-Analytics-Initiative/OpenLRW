package unicon.matthews.oneroster.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoClassRepository extends MongoRepository<MongoClass, String> {
  MongoClass 
    findByTenantIdAndOrgIdAndClassSourcedId(final String tenantId, final String orgId,
      final String classSourcedId);

}
