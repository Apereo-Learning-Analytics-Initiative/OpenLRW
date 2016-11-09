/**
 * 
 */
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

import unicon.matthews.oneroster.Org;
import unicon.matthews.oneroster.exception.OrgNotFoundException;
import unicon.matthews.oneroster.service.OrgService;
import unicon.matthews.security.auth.JwtAuthenticationToken;
import unicon.matthews.security.model.UserContext;

/**
 * @author ggilbert
 *
 */
@RestController
@RequestMapping("/api/orgs")
public class OrgController {
  
  private OrgService orgService;
  
  @Autowired
  public OrgController(OrgService orgService) {
    this.orgService = orgService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> post(JwtAuthenticationToken token, @RequestBody Org org) {
    UserContext userContext = (UserContext) token.getPrincipal();
    Org savedOrg = this.orgService.save(userContext.getTenantId(), org);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedOrg.getSourcedId()).toUri());
    return new ResponseEntity<>(savedOrg, httpHeaders, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{orgId}", method = RequestMethod.GET)
  public Org getOne(JwtAuthenticationToken token, @PathVariable final String orgId) throws OrgNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    Org maybeOrg = this.orgService.findByTenantIdAndOrgSourcedId(userContext.getTenantId(), orgId);
    return maybeOrg;
  }

}
