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
public class XApiContext {
	private String registration;
	private String revision;
	private String platform;
	private String language;
	private XApiActor instructor;
	private XApiActor team;
	private XApiContextActivities contextActivities;
	private XApiStatementRef statement;
	private Map<URI, Object> extensions;
	
	/**
	 * @return the registration
	 */
	public String getRegistration() {
		return registration;
	}
	/**
	 * @param registration the registration to set
	 */
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	/**
	 * @return the revision
	 */
	public String getRevision() {
		return revision;
	}
	/**
	 * @param revision the revision to set
	 */
	public void setRevision(String revision) {
		this.revision = revision;
	}
	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}
	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the instructor
	 */
	public XApiActor getInstructor() {
		return instructor;
	}
	/**
	 * @param instructor the instructor to set
	 */
	public void setInstructor(XApiActor instructor) {
		this.instructor = instructor;
	}
	/**
	 * @return the team
	 */
	public XApiActor getTeam() {
		return team;
	}
	/**
	 * @param team the team to set
	 */
	public void setTeam(XApiActor team) {
		this.team = team;
	}
	/**
	 * @return the contextActivities
	 */
	public XApiContextActivities getContextActivities() {
		return contextActivities;
	}
	/**
	 * @param contextActivities the contextActivities to set
	 */
	public void setContextActivities(XApiContextActivities contextActivities) {
		this.contextActivities = contextActivities;
	}
	/**
	 * @return the statement
	 */
	public XApiStatementRef getStatement() {
		return statement;
	}
	/**
	 * @param statement the statement to set
	 */
	public void setStatement(XApiStatementRef statement) {
		this.statement = statement;
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
