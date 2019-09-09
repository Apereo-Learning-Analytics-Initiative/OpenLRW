package org.apereo.openlrw.oneroster.endpoint;

import org.apereo.model.oneroster.AcademicSession;
import org.apereo.model.oneroster.Result;
import org.apereo.openlrw.oneroster.exception.AcademicSessionNotFoundException;
import org.apereo.openlrw.oneroster.service.AcademicSessionService;
import org.apereo.openlrw.oneroster.service.ResultService;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;


/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 *
 */
@RestController
@RequestMapping("/api/results")
public class ResultController {

  private ResultService resultService;

  @Autowired
  public ResultController(ResultService resultService) {
    this.resultService = resultService;
  }

  /**
   * GET /api/results
   *
   * @param token
   * @param page
   * @param limit
   * @return
   * @throws Exception
   */
  @RequestMapping(method = RequestMethod.GET)
  public Collection<Result> getResults(
          JwtAuthenticationToken token,
          @RequestParam(value = "page", required = false, defaultValue = "0") String page,
          @RequestParam(value = "limit", required = false, defaultValue = "1000") String limit
  ) throws Exception {
    UserContext userContext = (UserContext) token.getPrincipal();
    return resultService.findAll(userContext.getTenantId(), userContext.getOrgId(), page, limit);
  }


}
