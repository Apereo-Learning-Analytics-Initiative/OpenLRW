package org.apereo.openlrw.oneroster.service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */

public interface MongoResultRepository extends MongoRepository<MongoResult, String> {
  Collection<MongoResult> findByTenantIdAndOrgIdAndClassSourcedId(final String tenantId, final String orgId, final String classSourcedId);
  MongoResult findByTenantIdAndOrgIdAndResultSourcedId(final String tenantId, final String orgId, final String resultSourcedId);
  Collection<MongoResult> findByTenantIdAndOrgIdAndLineitemSourcedId(final String tenantId, final String orgId, final String lineItemSourcedId);
  MongoResult findByTenantIdAndOrgIdAndUserSourcedId(final String tenantId, final String orgId, final String userSourcedId);
  List<MongoResult> findTopByTenantIdAndOrgIdOrderByResultDateDesc(final String tenantId, final String orgId, Pageable pageRequest);
}
