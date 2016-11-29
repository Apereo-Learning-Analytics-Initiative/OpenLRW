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

import java.net.URI;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author ggilbert
 *
 */
@JsonInclude(Include.NON_NULL)
public class XApiResult {
	private boolean success;
	private boolean completion;
	private String response;
	private String duration;
	private XApiScore score;
	private Map<URI, Object> extensions;
	
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * @return the completion
	 */
	public boolean isCompletion() {
		return completion;
	}
	/**
	 * @param completion the completion to set
	 */
	public void setCompletion(boolean completion) {
		this.completion = completion;
	}
	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}
	/**
	 * @return the score
	 */
	public XApiScore getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(XApiScore score) {
		this.score = score;
	}
	/**
	 * @return the extensions
	 */
	public Map<URI, Object> getExtensions() {
		return extensions;
	}
	/**
	 * @param extensions the extensions to set
	 */
	public void setExtensions(Map<URI, Object> extensions) {
		this.extensions = extensions;
	}
}
