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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author ggilbert
 *
 */
@JsonInclude(Include.NON_NULL)
public class XApiContextActivities {
	
	private List<XApiObject> parent;
	private List<XApiObject> grouping;
	private List<XApiObject> category;
	private List<XApiObject> other;
	/**
	 * @return the parent
	 */
	public List<XApiObject> getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(List<XApiObject> parent) {
		this.parent = parent;
	}
	/**
	 * @return the grouping
	 */
	public List<XApiObject> getGrouping() {
		return grouping;
	}
	/**
	 * @param grouping the grouping to set
	 */
	public void setGrouping(List<XApiObject> grouping) {
		this.grouping = grouping;
	}
	/**
	 * @return the category
	 */
	public List<XApiObject> getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(List<XApiObject> category) {
		this.category = category;
	}
	/**
	 * @return the other
	 */
	public List<XApiObject> getOther() {
		return other;
	}
	/**
	 * @param other the other to set
	 */
	public void setOther(List<XApiObject> other) {
		this.other = other;
	}
}
