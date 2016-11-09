/**
 * 
 */
package unicon.matthews.oneroster;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


/**
 * @author ggilbert
 *
 */
@JsonDeserialize(builder = Org.Builder.class)
public class Org {
  private String sourcedId;
  private Status status;
  private Map<String, String> metadata;
  private LocalDateTime dateLastModified;
  private String name;
  private OrgType type;
  private String identifier;
  
  private Org() {}

  public String getSourcedId() {
    return sourcedId;
  }

  public Status getStatus() {
    return status;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public LocalDateTime getDateLastModified() {
    return dateLastModified;
  }

  public String getName() {
    return name;
  }

  public OrgType getType() {
    return type;
  }

  public String getIdentifier() {
    return identifier;
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dateLastModified == null) ? 0 : dateLastModified.hashCode());
    result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
    result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((sourcedId == null) ? 0 : sourcedId.hashCode());
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
    Org other = (Org) obj;
    if (dateLastModified == null) {
      if (other.dateLastModified != null)
        return false;
    } else if (!dateLastModified.equals(other.dateLastModified))
      return false;
    if (identifier == null) {
      if (other.identifier != null)
        return false;
    } else if (!identifier.equals(other.identifier))
      return false;
    if (metadata == null) {
      if (other.metadata != null)
        return false;
    } else if (!metadata.equals(other.metadata))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (sourcedId == null) {
      if (other.sourcedId != null)
        return false;
    } else if (!sourcedId.equals(other.sourcedId))
      return false;
    if (status != other.status)
      return false;
    if (type != other.type)
      return false;
    return true;
  }

  public static class Builder {
    private Org _org = new Org();
    
    public Builder withSourcedId(String sourcedId) {
      _org.sourcedId = sourcedId;
      return this;
    }
    
    public Builder withStatus(Status status) {
      _org.status = status;
      return this;
    }
    
    public Builder withMetadata(Map<String,String> metadata) {
      _org.metadata = metadata;
      return this;
    }
    
    public Builder withDateLastModified(LocalDateTime dateLastModified) {
      _org.dateLastModified = dateLastModified;
      return this;
    }
    
    public Builder withName(String name) {
      _org.name = name;
      return this;
    }
    
    public Builder withType(OrgType type) {
      _org.type = type;
      return this;
    }
    
    public Builder withIdentifier(String identifier) {
      _org.identifier = identifier;
      return this;
    }
    
    public Org build() {
      return _org;
    }
  }
}
