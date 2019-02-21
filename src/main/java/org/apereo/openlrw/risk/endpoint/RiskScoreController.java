package org.apereo.openlrw.risk.endpoint;

import org.apereo.openlrw.risk.service.repository.MongoRiskScoreRepository;
import org.apereo.openlrw.risk.RiskScore;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.List;
import java.util.UUID;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@RestController
@RequestMapping("/api/risks")
public class RiskScoreController {

  private MongoRiskScoreRepository mongoRiskScoreRepository;
  
  @Autowired
  public RiskScoreController(MongoRiskScoreRepository mongoRiskScoreRepository) {
    this.mongoRiskScoreRepository = mongoRiskScoreRepository;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> postLineItem(JwtAuthenticationToken token, @RequestBody RiskScore riskScore, @RequestParam(value="check", required=false) Boolean check) {
    UserContext userContext = (UserContext) token.getPrincipal();
    
    RiskScore risk = mongoRiskScoreRepository.findTopByTenantIdAndOrgIdAndUserSourcedIdAndClassSourcedIdAndActiveOrderByDateTime(
                userContext.getTenantId(),
                userContext.getOrgId(),
                riskScore.getUserSourcedId(),
                riskScore.getClassSourcedId(),
                true
    );

    if (riskScore.getDateTime() == null)
      riskScore.setDateTime(Instant.now());

    if (risk != null){
      risk = new RiskScore.Builder()
              .withActive(true)
              .withClassSourcedId(risk.getClassSourcedId())
              .withOrgId(risk.getOrgId())
              .withTenantId(risk.getTenantId())
              .withUserSourcedId(risk.getUserSourcedId())
              .withId(risk.getId())
              .withDateTime(riskScore.getDateTime())
              .withModelType(riskScore.getModelType())
              .withName(riskScore.getName())
              .withVelocity(riskScore.getVelocity())
              .build();
    } else {
      risk = new RiskScore.Builder()
              .withActive(true)
              .withClassSourcedId(riskScore.getClassSourcedId())
              .withDateTime(riskScore.getDateTime())
              .withId(UUID.randomUUID().toString())
              .withModelType(riskScore.getModelType())
              .withUserSourcedId(riskScore.getUserSourcedId())
              .withName(riskScore.getName())
              .withVelocity(riskScore.getVelocity())
              .withOrgId(userContext.getOrgId())
              .withTenantId(userContext.getTenantId())
              .build();
    }

    return new ResponseEntity<>(mongoRiskScoreRepository.save(risk), null, HttpStatus.CREATED);
  }
  
  @RequestMapping(value = "/{classSourcedId}/latest", method = RequestMethod.GET)
  public List<RiskScore> getLatestRiskScoresForClass(JwtAuthenticationToken token, @PathVariable String classSourcedId) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return mongoRiskScoreRepository.findByTenantIdAndOrgIdAndClassSourcedIdAndActive(userContext.getTenantId(), userContext.getOrgId(), classSourcedId, true);
  }

}
