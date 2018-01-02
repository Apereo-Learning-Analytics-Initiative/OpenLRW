package unicon.matthews.security.model;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author vladimir.stankovic
 *
 *         Aug 4, 2016
 */
public class UserContext {
  private final String tenantId;
  private final String orgId;
  private final List<GrantedAuthority> authorities;

  private UserContext(String tenantId, String orgId, List<GrantedAuthority> authorities) {
    this.orgId = orgId;
    this.tenantId = tenantId;
    this.authorities = authorities;
  }

  public static UserContext create(String tenantId, String orgId, List<GrantedAuthority> authorities) {
    
    if (authorities == null || authorities.isEmpty()) {
      throw new IllegalArgumentException("No authorities");
    }
    
    Optional<GrantedAuthority> maybeSuperAdmin
      = authorities.stream()
        .filter(authority -> authority.getAuthority().equals("ROLE_SUPER_ADMIN")).findAny();
    
    if (maybeSuperAdmin.isPresent()) {
      return new UserContext("*", "*", authorities);
    }

    Optional<GrantedAuthority> maybeTenantAdmin
    = authorities.stream()
      .filter(authority -> authority.getAuthority().equals("ROLE_TENANT_ADMIN")).findAny();

    if (maybeTenantAdmin.isPresent()) {
      return new UserContext(tenantId, "*", authorities);
    }
   
    return new UserContext(tenantId, orgId, authorities);
  }

  public String getTenantId() {
    return tenantId;
  }

  public String getOrgId() {
    return orgId;
  }

  public List<GrantedAuthority> getAuthorities() {
    return authorities;
  }
}
