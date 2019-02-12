package org.apereo.openlrw.oneroster.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 *
 */
public interface MongoLineItemRepository extends MongoRepository<MongoLineItem, String> {
  Collection<MongoLineItem> findByOrgIdAndClassSourcedId(final String orgId, final String classSourcedId);
  MongoLineItem findByTenantIdAndOrgIdAndLineItemSourcedId(final String tenantId, final String orgId, final String lineItemSourcedId);
  Collection<MongoLineItem> findByTenantIdAndOrgId(final String tenantId, final String orgId);
  Long deleteByTenantIdAndOrgIdAndLineItemSourcedId(final String tenantId, final String orgId, final String lineItemSourcedId);
}
