package org.apereo.openlrw.oneroster.endpoint;

import org.apereo.model.oneroster.AcademicSession;
import org.apereo.openlrw.oneroster.exception.AcademicSessionNotFoundException;
import org.apereo.openlrw.oneroster.service.AcademicSessionService;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


/**
 * @author stalele
 *
 */
@RestController
@RequestMapping("/api/academicsessions")
public class AcademicSessionController {
  
  private AcademicSessionService academicSessionService;

  @Autowired
  public AcademicSessionController(AcademicSessionService academicSessionService) {
    this.academicSessionService = academicSessionService;
  }
  
  @RequestMapping(value = "/{academicSessionId}", method = RequestMethod.GET)
  public AcademicSession getAcademicSession(JwtAuthenticationToken token, @PathVariable final String academicSessionId) throws AcademicSessionNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return academicSessionService.findBySourcedId(userContext.getTenantId(), userContext.getOrgId(), academicSessionId);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> postAcademicSession(JwtAuthenticationToken token, @RequestBody AcademicSession academicSession) {
    UserContext userContext = (UserContext) token.getPrincipal();
    AcademicSession saved = academicSessionService.save(userContext.getTenantId(), userContext.getOrgId(), academicSession);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(saved.getSourcedId()).toUri());
    return new ResponseEntity<>(saved, httpHeaders, HttpStatus.CREATED);
  } 

}
