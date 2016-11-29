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

import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The statement model represents all the available properties of a learning event
 * see https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#stmtprops
 * 
 * @author Robert E. Long (rlong @ unicon.net)
 */
@JsonInclude(Include.NON_NULL)
public class Statement {
	
	@Transient private Logger log = Logger.getLogger(Statement.class);
    private static final long serialVersionUID = 1L;
    @JsonIgnore public static final String OBJECT_KEY = "STATEMENT";

    /**
     * UUID
     * see https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#stmtid
     * 
     * Recommended
     */
    @Id private String id;

    /**
     * An agent (an individual) is a persona or system
     * see https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#actor
     * 
     * Required
     */
    @NotNull(message="actor can't be null") private XApiActor actor;

    /**
     * Action between actor and activity
     * see https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#verb
     * 
     * Required
     */
    @NotNull(message="verb can't be null") private XApiVerb verb;

    /**
     * an Activity, Agent/Group, Sub-Statement, or Statement Reference. It is the "this" part of the Statement, i.e. "I did this"
     * see https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#object
     * 
     * Required
     */
    @NotNull(message="object can't be null") private XApiObject object;

    /**
     * optional field that represents a measured outcome related to the Statement in which it is included
     * see https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#result
     * 
     * Optional
     */
    private XApiResult result;

    /**
     * optional field that provides a place to add contextual information to a Statement. All properties are optional
     * see https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#context
     * 
     * Optional
     */
    private XApiContext context;

    /**
     *  time at which the experience occurred
     *  see https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#timestamp
     *  
     *  Optional
     */
    private String timestamp;

    /**
     * time at which a Statement is stored by the LRS
     * see https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#stored
     * 
     * Set by the LRS
     */
    private String stored;

    /**
     * provides information about whom or what has asserted that this Statement is true
     * see https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#authority
     * 
     * Optional
     */
    private XApiActor authority;

    /**
     * information in Statements helps systems that process data from an LRS get their bearings. Since the Statement data model 
     * is guaranteed consistent through all 1.0.x versions, in order to support data flow among such LRSs the LRS is given some 
     * flexibility on Statement versions that are accepted
     * see https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#version
     * 
     * Not recommended to be set
     */
    private String version;

    /**
     * Object array of digital artifacts providing evidence of a learning experience
     * see https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#attachments
     */
    //private Object[] attachments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public XApiActor getActor() {
        return actor;
    }

    public void setActor(XApiActor actor) {
        this.actor = actor;
    }

    public XApiVerb getVerb() {
        return verb;
    }

    public void setVerb(XApiVerb verb) {
        this.verb = verb;
    }

    public XApiObject getObject() {
        return object;
    }

    public void setObject(XApiObject object) {
        this.object = object;
    }

    public XApiResult getResult() {
        return result;
    }

    public void setResult(XApiResult result) {
        this.result = result;
    }

    public XApiContext getContext() {
        return context;
    }

    public void setContext(XApiContext context) {
        this.context = context;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStored() {
        return stored;
    }

    public void setStored(String stored) {
        this.stored = stored;
    }

    public XApiActor getAuthority() {
        return authority;
    }

    public void setAuthority(XApiActor authority) {
        this.authority = authority;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

//    public Object[] getAttachments() {
//        return attachments;
//    }
//
//    public void setAttachments(Object[] attachments) {
//        this.attachments = attachments;
//    }

    @JsonIgnore
    public String toJSON() {
    	ObjectMapper om = new ObjectMapper();
    	String rawJson = null;
    	try {
			rawJson = om.writer().writeValueAsString(this);
		} 
    	catch (JsonProcessingException e) {
			log.error(e.getMessage(), e); 
		}
		return rawJson;
    }

    @Override
    public String toString() {
        return toJSON();        
    }

}
