/**
 * 
 */
package unicon.matthews.oneroster;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author ggilbert
 *
 */
@JsonDeserialize(builder = LineItem.Builder.class)
public class LineItem {
  private String sourcedId;
  private Status status;
  private Map<String, String> metadata;
  private String title;
  private String description;
  private LocalDateTime assignDate;
  private LocalDateTime dueDate;
  
  @JsonProperty("class")
  private Class klass;
  
  private LineItemCategory category;
  
  private LineItem() {}
  
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

  public String getDescription() {
    return description;
  }

  public LocalDateTime getAssignDate() {
    return assignDate;
  }

  public LocalDateTime getDueDate() {
    return dueDate;
  }

  public Class getKlass() {
    return klass;
  }

  public LineItemCategory getCategory() {
    return category;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((assignDate == null) ? 0 : assignDate.hashCode());
    result = prime * result + ((category == null) ? 0 : category.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
    result = prime * result + ((klass == null) ? 0 : klass.hashCode());
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
    LineItem other = (LineItem) obj;
    if (assignDate == null) {
      if (other.assignDate != null)
        return false;
    } else if (!assignDate.equals(other.assignDate))
      return false;
    if (category == null) {
      if (other.category != null)
        return false;
    } else if (!category.equals(other.category))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (dueDate == null) {
      if (other.dueDate != null)
        return false;
    } else if (!dueDate.equals(other.dueDate))
      return false;
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
    private LineItem _lineItem = new LineItem();
    
    public Builder withSourcedId(String sourcedId) {
      _lineItem.sourcedId = sourcedId;
      return this;
    }
    
    public Builder withStatus(Status status) {
      _lineItem.status = status;
      return this;
    }
    
    public Builder withMetadata(Map<String,String> metadata) {
      _lineItem.metadata = metadata;
      return this;
    }
    
    public Builder withTitle(String title) {
      _lineItem.title = title;
      return this;
    }
    
    public Builder withDescription(String description) {
      _lineItem.description = description;
      return this;
    }
    
    public Builder withAssignDate(LocalDateTime assignDate) {
      _lineItem.assignDate = assignDate;
      return this;
    }
    
    public Builder withDueDate(LocalDateTime dueDate) {
      _lineItem.dueDate = dueDate;
      return this;
    }
    
    public Builder withCategory(LineItemCategory category) {
      _lineItem.category = category;
      return this;
    }
    
    public Builder withClass(Class klass) {
      _lineItem.klass = klass;
      return this;
    }
    
    public LineItem build() {
      if (_lineItem.klass == null) {
        throw new IllegalStateException(_lineItem.toString());
      }
      return _lineItem;
    }
  }

}
