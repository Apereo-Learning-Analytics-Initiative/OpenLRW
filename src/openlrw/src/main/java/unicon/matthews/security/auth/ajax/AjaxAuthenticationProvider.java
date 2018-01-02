package unicon.matthews.security.auth.ajax;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import unicon.matthews.Vocabulary;
import unicon.matthews.oneroster.Org;
import unicon.matthews.oneroster.exception.OrgNotFoundException;
import unicon.matthews.oneroster.service.OrgService;
import unicon.matthews.security.model.UserContext;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private final OrgService orgService;

    @Autowired
    public AjaxAuthenticationProvider(final OrgService orgService) {
        this.orgService = orgService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String key = (String) authentication.getPrincipal();
        String secret = (String) authentication.getCredentials();
        
        Org org;
        try {
          org = orgService.findByApiKeyAndApiSecret(key, secret);
        } 
        catch (OrgNotFoundException e) {
          throw new AuthenticationCredentialsNotFoundException(e.getMessage());
        }
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ORG_ADMIN"));        
        UserContext userContext = UserContext.create(org.getMetadata().get(Vocabulary.TENANT), org.getSourcedId(), authorities);        
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
