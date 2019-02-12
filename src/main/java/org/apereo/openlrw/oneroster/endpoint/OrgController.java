package org.apereo.openlrw.oneroster.endpoint;

import org.apereo.model.oneroster.Org;
import org.apereo.openlrw.entity.DataSync;
import org.apereo.openlrw.oneroster.exception.OrgNotFoundException;
import org.apereo.openlrw.oneroster.service.OrgService;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


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
  
  @RequestMapping(value = "/{orgId}/datasyncs/{syncType}", method = RequestMethod.GET)
  public DataSync getLatestDataSync(JwtAuthenticationToken token, @PathVariable final String orgId, @PathVariable final String syncType) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return this.orgService.findLatestDataSync(userContext.getTenantId(), orgId, syncType);
  }
  
  @RequestMapping(value = "/{orgId}/datasyncs", method = RequestMethod.POST)
  public ResponseEntity<?> postDataSync(JwtAuthenticationToken token, @PathVariable final String orgId, @RequestBody DataSync dataSync) {
    UserContext userContext = (UserContext) token.getPrincipal();
    this.orgService.saveDataSync(userContext.getTenantId(), orgId, dataSync);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
