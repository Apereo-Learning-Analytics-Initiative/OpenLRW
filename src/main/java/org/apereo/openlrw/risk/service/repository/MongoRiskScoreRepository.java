package org.apereo.openlrw.risk.service.repository;

import org.apereo.openlrw.risk.RiskScore;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoRiskScoreRepository extends MongoRepository<RiskScore, String> {
  List<RiskScore> findByTenantIdAndOrgIdAndClassSourcedIdAndActive(String tenantId, String orgId, String classSourcedId, boolean active);
  RiskScore findTopByTenantIdAndOrgIdAndUserSourcedIdAndClassSourcedIdAndActiveOrderByDateTime(String tenantId, String orgId, String userSourcedId, String classSourcedId, boolean active);
}
