/**
 * 
 */
package unicon.matthews.tenant.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicon.matthews.tenant.Tenant;
import unicon.matthews.tenant.service.repository.TenantRepository;

/**
 * @author ggilbert
 *
 */
@Service
public class TenantService {
  private final TenantRepository tenantRepository;
  
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
}
