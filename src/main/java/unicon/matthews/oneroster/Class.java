/**
 * 
 */
package unicon.matthews.oneroster;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author ggilbert
 *
 */
@JsonDeserialize(builder = Class.Builder.class)
public final class Class {
  private String sourcedId;
  private Status status;
  private Map<String, String> metadata;
  private String title;
  
  private Class() {}
  
  public String getSourcedId() {
    return sourcedId;
  }

  public Status getStatus() {
    return status;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public String getTitle() {
    return title;
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
    result = prime * result + ((sourcedId == null) ? 0 : sourcedId.hashCode());
    result = prime * result + ((status == null) ? 0 : status.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
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
    Class other = (Class) obj;
    if (metadata == null) {
      if (other.metadata != null)
        return false;
    } else if (!metadata.equals(other.metadata))
      return false;
    if (sourcedId == null) {
      if (other.sourcedId != null)
        return false;
    } else if (!sourcedId.equals(other.sourcedId))
      return false;
    if (status != other.status)
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    return true;
  }

  public static class Builder {
    private Class _class = new Class();
    
    public Builder withSourcedId(String sourcedId) {
      _class.sourcedId = sourcedId;
      return this;
    }
    
    public Builder withStatus(Status status) {
      _class.status = status;
      return this;
    }
    
    public Builder withMetadata(Map<String,String> metadata) {
      _class.metadata = metadata;
      return this;
    }
    
    public Builder withTitle(String title) {
      _class.title = title;
      return this;
    }
    
    public Class build() {
      return _class;
    }
  }
}
