/**
 * 
 */
package org.apereo.openlrw.tenant.endpoint;

import org.apereo.openlrw.tenant.Tenant;
import org.apereo.openlrw.tenant.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Optional;

/**
 * @author ggilbert
 *
 */
@RestController
@RequestMapping("/api/tenants")
public class TenantController {
  
  private final TenantService tenantService;
  
  @Autowired
  public TenantController(TenantService tenantService) {
    this.tenantService = tenantService;
  }
  
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> post(@RequestBody Tenant tenant) {
    Tenant savedTenant = this.tenantService.save(tenant);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedTenant.getId()).toUri());
    return new ResponseEntity<>(savedTenant, httpHeaders, HttpStatus.CREATED);
  }
  
  @RequestMapping(method = RequestMethod.GET)
  public Collection<Tenant> get() {
    return this.tenantService.findAll();
  }
  
  @RequestMapping(value = "/{tenantId}", method = RequestMethod.GET)
  public Tenant getOne(@PathVariable final String tenantId) {
    Optional<Tenant> maybeTenant = this.tenantService.findById(tenantId);
    maybeTenant.orElseThrow(IllegalStateException::new);
    return maybeTenant.get();
  }
}
