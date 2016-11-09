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
@JsonDeserialize(builder = SubOrganizationOf.Builder.class)
public class SubOrganizationOf implements Serializable {

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
  private SubOrganizationOf subOrganizationOf;
  private String category;
  private String academicSession;
  private String courseNumber;
  private Map<String, String> extensions;
  private LocalDateTime dateCreated;
  private LocalDateTime dateModified;

  private SubOrganizationOf() {}

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

  public SubOrganizationOf getSubOrganizationOf() {
    return subOrganizationOf;
  }

  public String getCategory() {
    return category;
  }

  public String getAcademicSession() {
    return academicSession;
  }

  public String getCourseNumber() {
    return courseNumber;
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

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((academicSession == null) ? 0 : academicSession.hashCode());
    result = prime * result + ((category == null) ? 0 : category.hashCode());
    result = prime * result + ((context == null) ? 0 : context.hashCode());
    result = prime * result + ((courseNumber == null) ? 0 : courseNumber.hashCode());
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
    SubOrganizationOf other = (SubOrganizationOf) obj;
    if (academicSession == null) {
      if (other.academicSession != null)
        return false;
    } else if (!academicSession.equals(other.academicSession))
      return false;
    if (category == null) {
      if (other.category != null)
        return false;
    } else if (!category.equals(other.category))
      return false;
    if (context == null) {
      if (other.context != null)
        return false;
    } else if (!context.equals(other.context))
      return false;
    if (courseNumber == null) {
      if (other.courseNumber != null)
        return false;
    } else if (!courseNumber.equals(other.courseNumber))
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
    private SubOrganizationOf _subOrganizationOf = new SubOrganizationOf();
    
    @JsonProperty("@id")
    public Builder withId(String id) {
      _subOrganizationOf.id = id;
      return this;
    }
    
    @JsonProperty("@type")
    public Builder withType(String type) {
      _subOrganizationOf.type = type;
      return this;
    }
    
    @JsonProperty("@context")
    public Builder withContext(String context) {
      _subOrganizationOf.context = context;
      return this;
    }
    
    public Builder withName(String name) {
      _subOrganizationOf.name = name;
      return this;
    }
    
    public Builder withDescription(String description) {
      _subOrganizationOf.description = description;
      return this;
    }
    
    public Builder withExtensions(Map<String,String> extensions) {
      _subOrganizationOf.extensions = extensions;
      return this;
    }
    
    public Builder withDateCreated(LocalDateTime dateCreated) {
      _subOrganizationOf.dateCreated = dateCreated;
      return this;
    }
    
    public Builder withDateModified(LocalDateTime dataModified) {
      _subOrganizationOf.dateModified = dataModified;
      return this;
    }
    
    public Builder withAcademicSession(String academicSession) {
      _subOrganizationOf.academicSession = academicSession;
      return this;
    }
    
    public Builder withCourseNumber(String courseNumber) {
      _subOrganizationOf.courseNumber = courseNumber;
      return this;
    }
    
    public Builder withCategory(String category) {
      _subOrganizationOf.category = category;
      return this;
    }
    
    public Builder withSubOrganizationOf(SubOrganizationOf subOrganizationOf) {
      _subOrganizationOf.subOrganizationOf = subOrganizationOf;
      return this;
    }
    
    public SubOrganizationOf build() {
      if (StringUtils.isBlank(_subOrganizationOf.id) 
          || StringUtils.isBlank(_subOrganizationOf.type)) {
        throw new IllegalStateException(_subOrganizationOf.toString());
      }
      
      return _subOrganizationOf;
    }
  }

}
