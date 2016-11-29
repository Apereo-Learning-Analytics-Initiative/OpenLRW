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
package unicon.matthews.xapi;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Information about the LRS
 * see https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#aboutresource
 * 
 * @author ggilbert (ggilbert @ unicon.net)
 *
 */
@JsonInclude(Include.NON_NULL)
public class About implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull private String version;
	private Map<URI, String> extensions;
	
	public About (String version) {
		this.version = version;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the extensions
	 */
	public Map<URI, String> getExtensions() {
		return extensions;
	}

	/**
	 * @param extensions the extensions to set
	 */
	public void setExtensions(Map<URI, String> extensions) {
		this.extensions = extensions;
	}

}
