package org.apereo.openlrw.xapi.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apereo.openlrw.xapi.About;

/**
 * @author ggilbert (ggilbert @ unicon.net)
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@RestController
public class AboutController {

	@Value("${xapi.version:1.0.1}")
	private String version;
	
	@RequestMapping(value="/xAPI/about", method=RequestMethod.GET, produces = "application/json;charset=utf-8")
	public About about() {
		return new About(version);
	}
}
