package org.apereo.openlrw.risk.endpoint;

import org.apereo.openlrw.caliper.exception.EventNotFoundException;
import org.apereo.openlrw.common.exception.BadRequestException;
import org.apereo.openlrw.risk.service.RiskService;
import org.apereo.openlrw.risk.service.repository.MongoRiskRepository;
import org.apereo.openlrw.risk.MongoRisk;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.List;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@RestController
@RequestMapping("/api/risks")
public class RiskController {

  private RiskService riskService;
  private MongoRiskRepository mongoRiskRepository;
  
  @Autowired
  public RiskController(MongoRiskRepository mongoRiskRepository, RiskService riskService) {
    this.mongoRiskRepository = mongoRiskRepository;
    this.riskService = riskService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> post(JwtAuthenticationToken token, @RequestBody MongoRisk mongoRisk, @RequestParam(value="check", required=false) Boolean check) {
    UserContext userContext = (UserContext) token.getPrincipal();

    try {
      MongoRisk result = riskService.save(userContext.getTenantId(), userContext.getOrgId(), mongoRisk, (check == null) ? true : check);
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getSourcedId()).toUri());
      return new ResponseEntity<>(result, httpHeaders, HttpStatus.CREATED);
    } catch(Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Risk object does not have the expected structure.");
    }

  }
  @RequestMapping(value = "/classes/{classSourcedId:.+}/users/{userSourcedId:.+}", method = RequestMethod.GET)
  public Collection<MongoRisk> getClassUser(
          JwtAuthenticationToken token,
          @PathVariable final String classSourcedId,
          @PathVariable final String userSourcedId,
          @RequestParam(value="date", required=false, defaultValue = "") String date
  ) {
    UserContext userContext = (UserContext) token.getPrincipal();
    try {
      return riskService.getRisksForUserAndClass(userContext.getTenantId(), userContext.getOrgId(), classSourcedId, userSourcedId, date);
    } catch (EventNotFoundException e) {
      throw new EventNotFoundException(e.getMessage());
    } catch (BadRequestException e) {
      throw new BadRequestException(e.getMessage());
    }

  }

  
  @RequestMapping(value = "/{classSourcedId}/latest", method = RequestMethod.GET)
  public List<MongoRisk> getLatestRiskScoresForClass(JwtAuthenticationToken token, @PathVariable String classSourcedId) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return mongoRiskRepository.findByTenantIdAndOrgIdAndClassSourcedIdAndActive(userContext.getTenantId(), userContext.getOrgId(), classSourcedId, true);
  }

}
