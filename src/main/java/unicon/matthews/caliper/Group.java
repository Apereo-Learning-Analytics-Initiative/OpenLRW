/**
 * 
 */
package unicon.matthews.caliper;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@JsonDeserialize(builder = Group.Builder.class)
public class Group implements Serializable {

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
  
  private SubOrganizationOf subOrganizationOf;

  private Group() {}

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

  public SubOrganizationOf getSubOrganizationOf() {
    return subOrganizationOf;
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
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((subOrganizationOf == null) ? 0 : subOrganizationOf.hashCode());
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
    Group other = (Group) obj;
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
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (subOrganizationOf == null) {
      if (other.subOrganizationOf != null)
        return false;
    } else if (!subOrganizationOf.equals(other.subOrganizationOf))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
  }

  public static class Builder {
    private Group _group = new Group();
    
    @JsonProperty("@id")
    public Builder withId(String id) {
      _group.id = id;
      return this;
    }
    
    @JsonProperty("@type")
    public Builder withType(String type) {
      _group.type = type;
      return this;
    }
    
    @JsonProperty("@context")
    public Builder withContext(String context) {
      _group.context = context;
      return this;
    }
    
    public Builder withName(String name) {
      _group.name = name;
      return this;
    }
    
    public Builder withDescription(String description) {
      _group.description = description;
      return this;
    }
    
    public Builder withExtensions(Map<String,String> extensions) {
      _group.extensions = extensions;
      return this;
    }
    
    public Builder withDateCreated(LocalDateTime dateCreated) {
      _group.dateCreated = dateCreated;
      return this;
    }
    
    public Builder withDateModified(LocalDateTime dataModified) {
      _group.dateModified = dataModified;
      return this;
    }
    
    public Builder withSubOrganizationOf(SubOrganizationOf subOrganizationOf) {
      _group.subOrganizationOf = subOrganizationOf;
      return this;
    }
    
    public Group build() {
      if (StringUtils.isBlank(_group.id) 
          || StringUtils.isBlank(_group.type)) {
        throw new IllegalStateException(_group.toString());
      }
      
      return _group;
    }
  }

}
