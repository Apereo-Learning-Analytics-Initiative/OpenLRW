package org.apereo.openlrw.oneroster.service.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 *
 */

public interface MongoUserRepository extends MongoRepository<MongoUser, String> {
  MongoUser findByTenantIdAndOrgIdAndUserSourcedId(String tenantId, String orgId, String userSourcedId);
  Collection<MongoUser> findByTenantIdAndOrgId(final String tenantId, final String orgId);
  Long deleteByTenantIdAndOrgIdAndUserSourcedId(String tenantId, String orgId, String userSourcedId);
}