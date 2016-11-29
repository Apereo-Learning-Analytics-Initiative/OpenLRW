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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import unicon.matthews.xapi.About;

/**
 * @author ggilbert (ggilbert @ unicon.net)
 *
 */
@RestController
public class AboutController {

	@Value("${xapi.version:1.0.1}")
	private String version;
	
	@RequestMapping(value="/xAPI/about", method=RequestMethod.GET, produces = "application/json;charset=utf-8")
	public About about() {
		About about = new About(version);
		return about;
	}
}
