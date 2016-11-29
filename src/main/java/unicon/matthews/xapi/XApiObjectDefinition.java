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
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author ggilbert
 *
 */
@JsonInclude(Include.NON_NULL)
public class XApiObjectDefinition {
	private Map<String, String> name;
	private Map<String, String> description;
	private String type;
	private String moreInfo;
	private String interactionType;
	private List<String> correctResponsesPattern;
	private List<XApiInteractionComponent> choices;
	private List<XApiInteractionComponent> scale;
	private List<XApiInteractionComponent> source;
	private List<XApiInteractionComponent> target;
	private List<XApiInteractionComponent> steps;
	private Map<URI, Object> extensions;
	
	/**
	 * @return the name
	 */
	public Map<String, String> getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(Map<String, String> name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public Map<String, String> getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(Map<String, String> description) {
		this.description = description;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the moreInfo
	 */
	public String getMoreInfo() {
		return moreInfo;
	}
	/**
	 * @param moreInfo the moreInfo to set
	 */
	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "XApiObjectDefinition [name=" + name + ", description="
				+ description + ", type=" + type + ", moreInfo=" + moreInfo
				+ "]";
	}
	/**
	 * @return the interactionType
	 */
	public String getInteractionType() {
		return interactionType;
	}
	/**
	 * @param interactionType the interactionType to set
	 */
	public void setInteractionType(String interactionType) {
		this.interactionType = interactionType;
	}
	/**
	 * @return the correctResponsesPattern
	 */
	public List<String> getCorrectResponsesPattern() {
		return correctResponsesPattern;
	}
	/**
	 * @param correctResponsesPattern the correctResponsesPattern to set
	 */
	public void setCorrectResponsesPattern(List<String> correctResponsesPattern) {
		this.correctResponsesPattern = correctResponsesPattern;
	}
	/**
	 * @return the choices
	 */
	public List<XApiInteractionComponent> getChoices() {
		return choices;
	}
	/**
	 * @param choices the choices to set
	 */
	public void setChoices(List<XApiInteractionComponent> choices) {
		this.choices = choices;
	}
	/**
	 * @return the scale
	 */
	public List<XApiInteractionComponent> getScale() {
		return scale;
	}
	/**
	 * @param scale the scale to set
	 */
	public void setScale(List<XApiInteractionComponent> scale) {
		this.scale = scale;
	}
	/**
	 * @return the source
	 */
	public List<XApiInteractionComponent> getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(List<XApiInteractionComponent> source) {
		this.source = source;
	}
	/**
	 * @return the target
	 */
	public List<XApiInteractionComponent> getTarget() {
		return target;
	}
	/**
	 * @param target the target to set
	 */
	public void setTarget(List<XApiInteractionComponent> target) {
		this.target = target;
	}
	/**
	 * @return the steps
	 */
	public List<XApiInteractionComponent> getSteps() {
		return steps;
	}
	/**
	 * @param steps the steps to set
	 */
	public void setSteps(List<XApiInteractionComponent> steps) {
		this.steps = steps;
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
