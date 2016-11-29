/**
 * 
 */
package unicon.matthews.oneroster;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author ggilbert
 *
 */
@JsonDeserialize(builder = Enrollment.Builder.class)
public final class Enrollment {
  
  private String sourcedId;
  private Status status;
  private Map<String, String> metadata;
  private Role role;
  private boolean primary;
  private User user;
  
  private Enrollment() {}
  
  @JsonProperty("class")
  private Class klass;

  public String getSourcedId() {
    return sourcedId;
  }

  public Status getStatus() {
    return status;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public Role getRole() {
    return role;
  }

  public boolean isPrimary() {
    return primary;
  }

  public User getUser() {
    return user;
  }

  public Class getKlass() {
    return klass;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((klass == null) ? 0 : klass.hashCode());
    result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
    result = prime * result + (primary ? 1231 : 1237);
    result = prime * result + ((role == null) ? 0 : role.hashCode());
    result = prime * result + ((sourcedId == null) ? 0 : sourcedId.hashCode());
    result = prime * result + ((status == null) ? 0 : status.hashCode());
    result = prime * result + ((user == null) ? 0 : user.hashCode());
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
    Enrollment other = (Enrollment) obj;
    if (klass == null) {
      if (other.klass != null)
        return false;
    } else if (!klass.equals(other.klass))
      return false;
    if (metadata == null) {
      if (other.metadata != null)
        return false;
    } else if (!metadata.equals(other.metadata))
      return false;
    if (primary != other.primary)
      return false;
    if (role != other.role)
      return false;
    if (sourcedId == null) {
      if (other.sourcedId != null)
        return false;
    } else if (!sourcedId.equals(other.sourcedId))
      return false;
    if (status != other.status)
      return false;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public static class Builder {
    private Enrollment _enrollment = new Enrollment();
    
    public Builder withSourcedId(String sourcedId) {
      _enrollment.sourcedId = sourcedId;
      return this;
    }
    
    public Builder withStatus(Status status) {
      _enrollment.status = status;
      return this;
    }
    
    public Builder withMetadata(Map<String,String> metadata) {
      _enrollment.metadata = metadata;
      return this;
    }
    
    public Builder withRole(Role role) {
      _enrollment.role = role;
      return this;
    }
    
    public Builder withPrimary(boolean primary) {
      _enrollment.primary = primary;
      return this;
    }
    
    public Builder withUser(User user) {
      _enrollment.user = user;
      return this;
    }
    
    @JsonProperty("class")
    public Builder withKlass(Class klass) {
      _enrollment.klass = klass;
      return this;
    }
    
    public Enrollment build() {
      return _enrollment;
    }
  }
}
