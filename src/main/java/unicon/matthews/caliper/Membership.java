/**
 * 
 */
package unicon.matthews.caliper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = Membership.Builder.class)
public class Membership implements Serializable {
  private static final long serialVersionUID = 1L;
  
  @NotNull
  @JsonProperty("@id")
  private String id;
  
  @NotNull
  @JsonProperty("@type")
  private String type;
  
  @JsonProperty("@context")
  private String context;
  
  private String name;
  private String description;
  private Map<String, String> extensions;
  private LocalDateTime dateCreated;
  private LocalDateTime dateModified;
  
  private String member;
  private String organization;
  private List<String> roles;
  private String status;

  private Membership() {}

  public String getId() {
    return id;
  }

  public String getType() {
    return type;
  }
  
  public String getContext() {
    return context;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Map<String, String> getExtensions() {
    return extensions;
  }

  public LocalDateTime getDateCreated() {
    return dateCreated;
  }

  public LocalDateTime getDateModified() {
    return dateModified;
  }

  public String getMember() {
    return member;
  }

  public String getOrganization() {
    return organization;
  }

  public List<String> getRoles() {
    return roles;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((context == null) ? 0 : context.hashCode());
    result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
    result = prime * result + ((dateModified == null) ? 0 : dateModified.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((extensions == null) ? 0 : extensions.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((member == null) ? 0 : member.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((organization == null) ? 0 : organization.hashCode());
    result = prime * result + ((roles == null) ? 0 : roles.hashCode());
    result = prime * result + ((status == null) ? 0 : status.hashCode());
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
    Membership other = (Membership) obj;
    if (context == null) {
      if (other.context != null)
        return false;
    } else if (!context.equals(other.context))
      return false;
    if (dateCreated == null) {
      if (other.dateCreated != null)
        return false;
    } else if (!dateCreated.equals(other.dateCreated))
      return false;
    if (dateModified == null) {
      if (other.dateModified != null)
        return false;
    } else if (!dateModified.equals(other.dateModified))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (extensions == null) {
      if (other.extensions != null)
        return false;
    } else if (!extensions.equals(other.extensions))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (member == null) {
      if (other.member != null)
        return false;
    } else if (!member.equals(other.member))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (organization == null) {
      if (other.organization != null)
        return false;
    } else if (!organization.equals(other.organization))
      return false;
    if (roles == null) {
      if (other.roles != null)
        return false;
    } else if (!roles.equals(other.roles))
      return false;
    if (status == null) {
      if (other.status != null)
        return false;
    } else if (!status.equals(other.status))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
  }

  public static class Builder {
    private Membership _membership = new Membership();
    
    @JsonProperty("@id")
    public Builder withId(String id) {
      _membership.id = id;
      return this;
    }
    
    @JsonProperty("@type")
    public Builder withType(String type) {
      _membership.type = type;
      return this;
    }
    
    @JsonProperty("@context")
    public Builder withContext(String context) {
      _membership.context = context;
      return this;
    }
    
    public Builder withName(String name) {
      _membership.name = name;
      return this;
    }
    
    public Builder withDescription(String description) {
      _membership.description = description;
      return this;
    }
    
    public Builder withExtensions(Map<String,String> extensions) {
      _membership.extensions = extensions;
      return this;
    }
    
    public Builder withDateCreated(LocalDateTime dateCreated) {
      _membership.dateCreated = dateCreated;
      return this;
    }
    
    public Builder withDateModified(LocalDateTime dataModified) {
      _membership.dateModified = dataModified;
      return this;
    }
    
    public Builder withMember(String member) {
      _membership.member = member;
      return this;
    }
    
    public Builder withOrganization(String organization) {
      _membership.organization = organization;
      return this;
    }
    
    public Builder withRoles(List<String> roles) {
      _membership.roles = roles;
      return this;
    }
    
    public Builder withStatus(String status) {
      _membership.status = status;
      return this;
    }
    
    public Membership build() {
      if (StringUtils.isBlank(_membership.id) 
          || StringUtils.isBlank(_membership.type)) {
        throw new IllegalStateException(_membership.toString());
      }
      
      return _membership;
    }
  }

}
