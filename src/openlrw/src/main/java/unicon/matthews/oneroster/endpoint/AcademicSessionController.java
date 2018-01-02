package unicon.matthews.oneroster.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import unicon.matthews.oneroster.AcademicSession;
import unicon.matthews.oneroster.exception.AcademicSessionNotFoundException;
import unicon.matthews.oneroster.service.AcademicSessionService;
import unicon.matthews.security.auth.JwtAuthenticationToken;
import unicon.matthews.security.model.UserContext;

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
