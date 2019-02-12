package org.apereo.openlrw.security.endpoint;

import org.apereo.model.oneroster.Org;
import org.apereo.openlrw.Vocabulary;
import org.apereo.openlrw.oneroster.exception.OrgNotFoundException;
import org.apereo.openlrw.oneroster.service.OrgService;
import org.apereo.openlrw.security.auth.jwt.extractor.TokenExtractor;
import org.apereo.openlrw.security.auth.jwt.verifier.TokenVerifier;
import org.apereo.openlrw.security.config.JwtSettings;
import org.apereo.openlrw.security.config.WebSecurityConfig;
import org.apereo.openlrw.security.exception.InvalidJwtToken;
import org.apereo.openlrw.security.model.UserContext;
import org.apereo.openlrw.security.model.token.JwtToken;
import org.apereo.openlrw.security.model.token.JwtTokenFactory;
import org.apereo.openlrw.security.model.token.RawAccessJwtToken;
import org.apereo.openlrw.security.model.token.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * RefreshTokenEndpoint
 * 
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
@RestController
public class RefreshTokenEndpoint {
    @Autowired private JwtTokenFactory tokenFactory;
    @Autowired private JwtSettings jwtSettings;
    @Autowired private OrgService orgService;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;
    
    @RequestMapping(value="/api/auth/token", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));
        
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String orgId = refreshToken.getSubject();
        String tenantId = refreshToken.getClaims().getBody().get("tenant", String.class);

        Org org;
        try {
          org = orgService.findByTenantIdAndOrgSourcedId(tenantId, orgId);
        } 
        catch (OrgNotFoundException e) {
          throw new AuthenticationCredentialsNotFoundException(e.getMessage());
        }

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ORG_ADMIN"));        
        UserContext userContext = UserContext.create(org.getMetadata().get(Vocabulary.TENANT), org.getSourcedId(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);
    }
}
