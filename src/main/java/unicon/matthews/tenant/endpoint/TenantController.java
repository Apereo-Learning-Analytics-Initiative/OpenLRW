/**
 * 
 */
package unicon.matthews.tenant.endpoint;

import java.util.Collection;
import java.util.Optional;

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

import unicon.matthews.tenant.Tenant;
import unicon.matthews.tenant.service.TenantService;

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
