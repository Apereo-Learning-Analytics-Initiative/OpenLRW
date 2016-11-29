package unicon.matthews.xapi;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Holds a representation of a statement object
 *
 * @author Robert E. Long (rlong @ unicon.net)
 */
@JsonInclude(Include.NON_NULL)
public class XApiObject {

    @NotNull(message="object.id can't be null") private String id;
	
    private XApiObjectTypes objectType;
    private XApiObjectDefinition definition;
    private XApiActor actor;
    private XApiVerb verb;
    private XApiObject object;
    
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the objectType
	 */
	public XApiObjectTypes getObjectType() {
		return objectType;
	}
	/**
	 * @param objectType the objectType to set
	 */
	public void setObjectType(XApiObjectTypes objectType) {
		this.objectType = objectType;
	}
	/**
	 * @return the definition
	 */
	public XApiObjectDefinition getDefinition() {
		return definition;
	}
	/**
	 * @param definition the definition to set
	 */
	public void setDefinition(XApiObjectDefinition definition) {
		this.definition = definition;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LRSObject [id=" + id + ", objectType=" + objectType
				+ ", definition=" + definition + "]";
	}
	/**
	 * @return the actor
	 */
	public XApiActor getActor() {
		return actor;
	}
	/**
	 * @param actor the actor to set
	 */
	public void setActor(XApiActor actor) {
		this.actor = actor;
	}
	/**
	 * @return the verb
	 */
	public XApiVerb getVerb() {
		return verb;
	}
	/**
	 * @param verb the verb to set
	 */
	public void setVerb(XApiVerb verb) {
		this.verb = verb;
	}
	/**
	 * @return the object
	 */
	public XApiObject getObject() {
		return object;
	}
	/**
	 * @param object the object to set
	 */
	public void setObject(XApiObject object) {
		this.object = object;
	}
}
