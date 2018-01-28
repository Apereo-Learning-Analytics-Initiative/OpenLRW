package unicon.matthews.entity.risk;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ggilbert
 *
 */
public interface MongoRiskScoreRepository extends MongoRepository<RiskScore, String> {
    List<RiskScore> findByTenantIdAndOrgIdAndClassSourcedIdAndActive(String tenantId, String orgId, String classSourcedId, boolean active);
    RiskScore findTopByTenantIdAndOrgIdAndUserSourcedIdAndClassSourcedIdAndActiveOrderByDateTime(String tenantId, String orgId, String userSourcedId, String classSourcedId, boolean active);
}
