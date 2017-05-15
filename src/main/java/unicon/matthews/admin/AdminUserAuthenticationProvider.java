package unicon.matthews.admin;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import unicon.matthews.oneroster.service.OrgService;
import unicon.matthews.security.model.UserContext;

@Component
public class AdminUserAuthenticationProvider implements AuthenticationProvider {
  
  private final AdminUserService adminUserService;
  private final OrgService orgService;

  @Autowired
  public AdminUserAuthenticationProvider(AdminUserService adminUserService, OrgService orgService) {
    super();
    this.adminUserService = adminUserService;
    this.orgService = orgService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Assert.notNull(authentication, "No authentication data provided");

    String username = (String) authentication.getPrincipal();
    String password = (String) authentication.getCredentials();
    
    // Do the check of user/pass with admin user service 
    
    // If found
    // -- look up org / tenant
    // -- determine if super or org admin and set authority (ROLE_ORG_ADMIN, ROLE_SUPER_ADMIN)
    // hardcoded for testing purposes
    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ORG_ADMIN"));        
    UserContext userContext = UserContext.create("TENANTID", "ORGID", authorities);        
    return new AdminUserAuthenticationToken(userContext, null, userContext.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (AdminUserAuthenticationToken.class.isAssignableFrom(authentication));
  }

}
