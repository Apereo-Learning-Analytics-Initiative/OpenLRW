package unicon.matthews.oneroster.service.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoResultRepository extends MongoRepository<MongoResult, String> {
  Collection<MongoResult> 
  findByTenantIdAndOrgIdAndClassSourcedId(final String tenantId, final String orgId,
    final String classSourcedId);
  
  MongoResult findByTenantIdAndOrgIdAndResultSourcedId(final String tenantId, final String orgId, final String resultSourcedId);
  
  MongoResult
  findByTenantIdAndOrgIdAndLineitemSourcedId(final String tenantId, final String orgId, 
    final String lineItemSourcedId);

  MongoResult
  findByTenantIdAndOrgIdAndUserSourcedId(final String tenantId, final String orgId, 
    final String userSourcedId);

}
