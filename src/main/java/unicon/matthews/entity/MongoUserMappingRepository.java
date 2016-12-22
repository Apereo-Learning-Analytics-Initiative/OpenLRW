/**
 * 
 */
package unicon.matthews.entity;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ggilbert
 *
 */
public interface MongoUserMappingRepository extends MongoRepository<UserMapping, String> {
  UserMapping findByTenantIdAndOrganizationIdAndUserExternalId(String tenantId, String organizationId, String userExternalId);
}
