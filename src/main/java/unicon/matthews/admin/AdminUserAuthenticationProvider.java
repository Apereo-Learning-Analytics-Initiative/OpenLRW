package unicon.matthews.admin;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import unicon.matthews.admin.service.AdminUserService;
import unicon.matthews.oneroster.service.OrgService;
import unicon.matthews.security.model.UserContext;

@Component
public class AdminUserAuthenticationProvider implements AuthenticationProvider {

    private static Logger logger = LoggerFactory.getLogger(AdminUserAuthenticationProvider.class);

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private OrgService orgService;

    /**
     * Authenticate admin user
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        UserContext userContext = null;
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        try {
            AdminUser adminUser = adminUserService.authenticateUser(username, password);
            if (adminUser != null) {
                String tenantId = adminUser.getTenantId();
                String orgId = adminUser.getOrgId();
                if (adminUser.isSuperAdmin()) {
                    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
                    userContext = UserContext.create(tenantId, orgId, authorities);
                } else {
                    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ORG_ADMIN"));
                    userContext = UserContext.create(tenantId, orgId, authorities);
                }
            }
        } catch (AuthenticationException ex) {
            logger.error(String.format("Unable to authenticate user=%s. Invalid credentials supplied ", username) + ex.getMessage(), ex);
            throw ex;
        }
        return new AdminUserAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (AdminUserAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
