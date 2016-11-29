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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author ggilbert
 *
 */
@JsonInclude(Include.NON_NULL)
public class XApiScore {
	private Double scaled;
	private Double raw;
	private Double min;
	private Double max;
	
	/**
	 * @return the scaled
	 */
	public Double getScaled() {
		return scaled;
	}
	/**
	 * @param scaled the scaled to set
	 */
	public void setScaled(Double scaled) {
		this.scaled = scaled;
	}
	/**
	 * @return the raw
	 */
	public Double getRaw() {
		return raw;
	}
	/**
	 * @param raw the raw to set
	 */
	public void setRaw(Double raw) {
		this.raw = raw;
	}
	/**
	 * @return the min
	 */
	public Double getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(Double min) {
		this.min = min;
	}
	/**
	 * @return the max
	 */
	public Double getMax() {
		return max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(Double max) {
		this.max = max;
	}
}
