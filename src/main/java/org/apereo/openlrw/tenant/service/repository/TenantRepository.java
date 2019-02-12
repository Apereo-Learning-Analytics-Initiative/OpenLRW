/**
 * 
 */
package org.apereo.openlrw.tenant.service.repository;

import org.apereo.openlrw.tenant.Tenant;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ggilbert
 *
 */
public interface TenantRepository extends MongoRepository<Tenant, String> {
  Tenant findByName(final String name);
}
