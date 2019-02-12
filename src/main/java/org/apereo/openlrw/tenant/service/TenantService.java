/**
 * 
 */
package org.apereo.openlrw.tenant.service;

import org.apereo.openlrw.tenant.Tenant;
import org.apereo.openlrw.tenant.service.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * @author ggilbert
 *
 */
@Service
public class TenantService {
  private final TenantRepository tenantRepository;
  
  public static final String DEFAULT_TENANT_NAME = "DEFAULT_TENANT";
  
  @Autowired
  public TenantService(TenantRepository tenantRepository) {
    this.tenantRepository = tenantRepository;
  }
  
  public Tenant save(Tenant tenant) {
    return tenantRepository.save(tenant);
  }
  
  public Collection<Tenant> findAll() {
    return tenantRepository.findAll();
  }
  
  public Optional<Tenant> findById(final String tenantId){
    Tenant tenant = tenantRepository.findOne(tenantId);
    return Optional.ofNullable(tenant);
  }
  
  public Optional<Tenant> findByName(final String name) {
    Tenant tenant = tenantRepository.findByName(name);
    return Optional.ofNullable(tenant);
  }
}
