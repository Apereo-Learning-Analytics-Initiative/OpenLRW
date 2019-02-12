package org.apereo.openlrw.entity;

import org.apereo.model.entity.UserMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ggilbert
 *
 */
public interface MongoUserMappingRepository extends MongoRepository<UserMapping, String> {
  UserMapping findByTenantIdAndOrganizationIdAndUserExternalIdIgnoreCase(String tenantId, String organizationId, String userExternalId);
  UserMapping findByTenantIdAndUserExternalIdIgnoreCase(String tenantId, String userExternalId);
}
