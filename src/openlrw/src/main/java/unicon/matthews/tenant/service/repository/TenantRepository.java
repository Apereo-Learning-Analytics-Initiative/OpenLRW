/**
 * 
 */
package unicon.matthews.tenant.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import unicon.matthews.tenant.Tenant;

/**
 * @author ggilbert
 *
 */
public interface TenantRepository extends MongoRepository<Tenant, String> {
  Tenant findByName(final String name);
}
