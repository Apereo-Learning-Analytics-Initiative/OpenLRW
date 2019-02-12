package org.apereo.openlrw.entity.risk;

import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk")
public class RiskScoreController {

  private MongoRiskScoreRepository mongoRiskScoreRepository;
  
  @Autowired
  public RiskScoreController(MongoRiskScoreRepository mongoRiskScoreRepository) {
    this.mongoRiskScoreRepository = mongoRiskScoreRepository;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> postLineItem(JwtAuthenticationToken token, @RequestBody RiskScore riskScore) {
    UserContext userContext = (UserContext) token.getPrincipal();
    
    RiskScore rs 
      = mongoRiskScoreRepository
        .findTopByTenantIdAndOrgIdAndUserSourcedIdAndClassSourcedIdAndActiveOrderByDateTime(userContext.getTenantId(), userContext.getOrgId(), 
            riskScore.getUserSourcedId(), riskScore.getClassSourcedId(), true);
    
    RiskScore updatedRs
      = new RiskScore.Builder()
            .withActive(false)
            .withClassSourcedId(rs.getClassSourcedId())
            .withDateTime(rs.getDateTime())
            .withId(rs.getId())
            .withModelType(rs.getModelType())
            .withOrgId(rs.getOrgId())
            .withTenantId(rs.getTenantId())
            .withUserSourcedId(rs.getUserSourcedId())
            .build();
    
    mongoRiskScoreRepository.save(updatedRs);
    
    RiskScore newRs
    = new RiskScore.Builder()
          .withActive(true)
          .withClassSourcedId(riskScore.getClassSourcedId())
          .withDateTime(riskScore.getDateTime())
          .withId(null)
          .withModelType(riskScore.getModelType())
          .withOrgId(userContext.getOrgId())
          .withTenantId(userContext.getTenantId())
          .withUserSourcedId(riskScore.getUserSourcedId())
          .build();
    
    return new ResponseEntity<>(mongoRiskScoreRepository.save(newRs), null, HttpStatus.CREATED);
  }
  
  @RequestMapping(value = "/{classSourcedId}/latest", method = RequestMethod.GET)
  public List<RiskScore> getLatestRiskScoresForClass(JwtAuthenticationToken token, @PathVariable String classSourcedId) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return mongoRiskScoreRepository.findByTenantIdAndOrgIdAndClassSourcedIdAndActive(userContext.getTenantId(), userContext.getOrgId(), classSourcedId, true);
  }

}
