/**
 * 
 */
package unicon.matthews.caliper;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author ggilbert
 *
 */
//@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = Event.Builder.class)
public class Event implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String id;
  
  @JsonProperty("@context")
  private String context;
  @JsonProperty("@type")
  private String type;
  
  @NotNull
  @JsonProperty("actor")
  private Agent agent;
  
  @NotNull
  private String action;
  
  @NotNull
  private Entity object;
  
  private Entity target;
  private Agent edApp;
  private Entity generated;
  private Group group;
  private Membership membership;
  private LocalDateTime eventTime;
  private String federatedSession;
  
  private Event() {}

  public String getId() {
    return id;
  }

  public String getContext() {
    return context;
  }

  public String getType() {
    return type;
  }

  public Agent getAgent() {
    return agent;
  }

  public String getAction() {
    return action;
  }

  public Entity getObject() {
    return object;
  }

  public Entity getTarget() {
    return target;
  }

  public Agent getEdApp() {
    return edApp;
  }

  public Entity getGenerated() {
    return generated;
  }

  public Group getGroup() {
    return group;
  }

  public Membership getMembership() {
    return membership;
  }

  public String getFederatedSession() {
    return federatedSession;
  }

  public LocalDateTime getEventTime() {
    return eventTime;
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((action == null) ? 0 : action.hashCode());
    result = prime * result + ((agent == null) ? 0 : agent.hashCode());
    result = prime * result + ((context == null) ? 0 : context.hashCode());
    result = prime * result + ((edApp == null) ? 0 : edApp.hashCode());
    result = prime * result + ((eventTime == null) ? 0 : eventTime.hashCode());
    result = prime * result + ((federatedSession == null) ? 0 : federatedSession.hashCode());
    result = prime * result + ((generated == null) ? 0 : generated.hashCode());
    result = prime * result + ((group == null) ? 0 : group.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((membership == null) ? 0 : membership.hashCode());
    result = prime * result + ((object == null) ? 0 : object.hashCode());
    result = prime * result + ((target == null) ? 0 : target.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Event other = (Event) obj;
    if (action == null) {
      if (other.action != null)
        return false;
    } else if (!action.equals(other.action))
      return false;
    if (agent == null) {
      if (other.agent != null)
        return false;
    } else if (!agent.equals(other.agent))
      return false;
    if (context == null) {
      if (other.context != null)
        return false;
    } else if (!context.equals(other.context))
      return false;
    if (edApp == null) {
      if (other.edApp != null)
        return false;
    } else if (!edApp.equals(other.edApp))
      return false;
    if (eventTime == null) {
      if (other.eventTime != null)
        return false;
    } else if (!eventTime.equals(other.eventTime))
      return false;
    if (federatedSession == null) {
      if (other.federatedSession != null)
        return false;
    } else if (!federatedSession.equals(other.federatedSession))
      return false;
    if (generated == null) {
      if (other.generated != null)
        return false;
    } else if (!generated.equals(other.generated))
      return false;
    if (group == null) {
      if (other.group != null)
        return false;
    } else if (!group.equals(other.group))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (membership == null) {
      if (other.membership != null)
        return false;
    } else if (!membership.equals(other.membership))
      return false;
    if (object == null) {
      if (other.object != null)
        return false;
    } else if (!object.equals(other.object))
      return false;
    if (target == null) {
      if (other.target != null)
        return false;
    } else if (!target.equals(other.target))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
  }

  public static class Builder {
    Event _basicEvent = new Event();
    
    public Builder withId(String id) {
      _basicEvent.id = id;
      return this;
    }
    
    @JsonProperty("@context")
    public Builder withContext(String context) {
      _basicEvent.context = context;
      return this;
    }
    
    @JsonProperty("@type") 
    public Builder withType(String type) {
      _basicEvent.type = type;
      return this;
    }
    
    @JsonProperty("actor")
    public Builder withAgent(Agent agent) {
      _basicEvent.agent = agent;
      return this;
    }
    
    public Builder withAction(String action) {
      _basicEvent.action = action;
      return this;
    }
    
    public Builder withObject(Entity entity) {
      _basicEvent.object = entity;
      return this;
    }
    
    public Builder withTarget(Entity target) {
      _basicEvent.target = target;
      return this;
    }
    
    public Builder withEdApp(Agent edApp) {
      _basicEvent.edApp = edApp;
      return this;
    }
    
    public Builder withGenerated(Entity generated) {
      _basicEvent.generated = generated;
      return this;
    }
    
    public Builder withGroup(Group group) {
      _basicEvent.group = group;
      return this;
    }
    
    public Builder withMembership(Membership membership) {
      _basicEvent.membership = membership;
      return this;
    }
    
    public Builder withEventTime(LocalDateTime eventTime) {
      _basicEvent.eventTime = eventTime;
      return this;
    }
    
    public Builder withFederatedSession(String federatedSession) {
      _basicEvent.federatedSession = federatedSession;
      return this;
    }
    
    public Event build() {
      
      if (_basicEvent.agent == null 
          || StringUtils.isBlank(_basicEvent.action)
          || _basicEvent.object == null
          || _basicEvent.eventTime == null) {
        throw new IllegalStateException(_basicEvent.toString());
      }
      
      return _basicEvent;
    }
  }
  
}
