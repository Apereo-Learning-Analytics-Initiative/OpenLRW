package org.apereo.openlrw.xapi.endpoint;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 *
 */
@Component
public class XAPIRequestValidationFilter extends OncePerRequestFilter {

	private static Logger logger = LoggerFactory.getLogger(XAPIRequestValidationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String versionHeader = request.getHeader(XApiConstants.XAPI_VERSION_HEADER);
		logger.debug(String.format("versionHeader {}",versionHeader));
		if (StringUtils.isNotBlank(versionHeader)) {
			// for now we are just checking for the header
			// in the future we'll deal with specific versioning checking
			filterChain.doFilter(request, response);
		}
		else {
			logger.warn("Request missing XAPI VERSION HEADER");
			response.sendError(400, "Missing "+XApiConstants.XAPI_VERSION_HEADER+" Header");
		}
		
	}

}
