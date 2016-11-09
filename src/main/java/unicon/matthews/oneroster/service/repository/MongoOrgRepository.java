/**
 * 
 */
package unicon.matthews.oneroster.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ggilbert
 *
 */
public interface MongoOrgRepository extends MongoRepository<MongoOrg, String> {
  
  MongoOrg findByApiKeyAndApiSecret(final String apiKey, final String apiSecret);
  MongoOrg findByTenantIdAndOrgSourcedId(final String tenantId, final String sourcedId);
}
