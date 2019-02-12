package org.apereo.openlrw.entity.risk;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoRiskScoreRepository extends MongoRepository<RiskScore, String> {
  List<RiskScore> findByTenantIdAndOrgIdAndClassSourcedIdAndActive(String tenantId, String orgId, String classSourcedId, boolean active);
  RiskScore findTopByTenantIdAndOrgIdAndUserSourcedIdAndClassSourcedIdAndActiveOrderByDateTime(String tenantId, String orgId, String userSourcedId, String classSourcedId, boolean active);
}
