/**
 * Copyright 2014 Unicon (R) Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0

 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package unicon.matthews.xapi.endpoint;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author ggilbert
 *
 */
@Component
public class XAPIRequestValidationFilter extends OncePerRequestFilter {

	private Logger log = Logger.getLogger(XAPIRequestValidationFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String versionHeader = request.getHeader(XApiConstants.XAPI_VERSION_HEADER);
		log.debug(String.format("versionHeader {}",versionHeader));
		if (StringUtils.isNotBlank(versionHeader)) {
			// for now we are just checking for the header
			// in the future we'll deal with specific versioning checking
			filterChain.doFilter(request, response);
		}
		else {
			log.warn("Request missing XAPI VERSION HEADER");
			response.sendError(400, "Missing "+XApiConstants.XAPI_VERSION_HEADER+" Header");
		}
		
	}

}
