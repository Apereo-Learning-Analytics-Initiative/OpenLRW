/**
 * 
 */
package unicon.matthews.oneroster.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ggilbert
 *
 */
public interface MongoUserRepository extends MongoRepository<MongoUser, String> {
  MongoUser findByTenantIdAndOrgIdAndUserSourcedId(String tenantId, String orgId, String userSourcedId);
}
