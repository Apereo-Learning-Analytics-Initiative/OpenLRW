package org.apereo.openlrw.risk.service.repository;

import org.apereo.openlrw.risk.MongoRisk;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoRiskRepository extends MongoRepository<MongoRisk, String> {
  List<MongoRisk> findByTenantIdAndOrgIdAndClassSourcedIdAndActive(String tenantId, String orgId, String classSourcedId, boolean active);
  MongoRisk findTopByTenantIdAndOrgIdAndUserSourcedIdAndClassSourcedIdAndActiveOrderByDateTime(String tenantId, String orgId, String userSourcedId, String classSourcedId, boolean active);
}
