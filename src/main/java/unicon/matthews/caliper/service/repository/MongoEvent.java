/**
 * 
 */
package unicon.matthews.caliper.service.repository;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import unicon.matthews.caliper.Event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author ggilbert
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = MongoEvent.Builder.class)
@Document
public class MongoEvent implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id
  private String id;
  private String userId;
  private String classId;
  private String organizationId;
  private String tenantId;
  private Event event;
  
  private MongoEvent() {}

  public String getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public String getClassId() {
    return classId;
  }

  public String getOrganizationId() {
    return organizationId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public Event getEvent() {
    return event;
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((classId == null) ? 0 : classId.hashCode());
    result = prime * result + ((event == null) ? 0 : event.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((organizationId == null) ? 0 : organizationId.hashCode());
    result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
    MongoEvent other = (MongoEvent) obj;
    if (classId == null) {
      if (other.classId != null)
        return false;
    } else if (!classId.equals(other.classId))
      return false;
    if (event == null) {
      if (other.event != null)
        return false;
    } else if (!event.equals(other.event))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (organizationId == null) {
      if (other.organizationId != null)
        return false;
    } else if (!organizationId.equals(other.organizationId))
      return false;
    if (tenantId == null) {
      if (other.tenantId != null)
        return false;
    } else if (!tenantId.equals(other.tenantId))
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    return true;
  }

  public static class Builder {
    private MongoEvent _mongoEvent = new MongoEvent();
    
    public Builder withId(String id) {
      _mongoEvent.id = id;
      return this;
    }
    
    public Builder withUserId(String userId) {
      _mongoEvent.userId = userId;
      return this;
    }
    
    public Builder withClassId(String classId) {
      _mongoEvent.classId = classId;
      return this;
    }
    
    public Builder withOrganizationId(String organizationId) {
      _mongoEvent.organizationId = organizationId;
      return this;
    }
    
    public Builder withTenantId(String tenantId) {
      _mongoEvent.tenantId = tenantId;
      return this;
    }
    
    public Builder withEvent(Event event) {
      _mongoEvent.event = event;
      return this;
    }
    
    public MongoEvent build() {
      
      if (_mongoEvent.event == null 
          || StringUtils.isBlank(_mongoEvent.tenantId)) {
        throw new IllegalStateException(_mongoEvent.toString());
      }
      
      return _mongoEvent;
    }
  }
}
