/**
 * 
 */
package unicon.matthews.oneroster.service.repository;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import unicon.matthews.oneroster.LineItem;

/**
 * @author ggilbert
 *
 */
@Document
public class MongoLineItem implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id private String id;
  private String orgId;
  private String tenantId;
  private LineItem lineItem;
  private String classSourcedId;
  
  private MongoLineItem() {}

  public String getId() {
    return id;
  }

  public String getOrgId() {
    return orgId;
  }

  public LineItem getLineItem() {
    return lineItem;
  }

  public String getClassSourcedId() {
    return classSourcedId;
  }
  
  public String getTenantId() {
    return tenantId;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((classSourcedId == null) ? 0 : classSourcedId.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((lineItem == null) ? 0 : lineItem.hashCode());
    result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
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
    MongoLineItem other = (MongoLineItem) obj;
    if (classSourcedId == null) {
      if (other.classSourcedId != null)
        return false;
    } else if (!classSourcedId.equals(other.classSourcedId))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (lineItem == null) {
      if (other.lineItem != null)
        return false;
    } else if (!lineItem.equals(other.lineItem))
      return false;
    if (orgId == null) {
      if (other.orgId != null)
        return false;
    } else if (!orgId.equals(other.orgId))
      return false;
    return true;
  }

  public static class Builder {
    private MongoLineItem _mongoLineItem = new MongoLineItem();
    
    public Builder withClassSourcedId(String classSourcedId) {
      _mongoLineItem.classSourcedId = classSourcedId;
      return this;
    }
    
    public Builder withId(String id) {
      _mongoLineItem.id = id;
      return this;
    }
    
    public Builder withLineItem(LineItem lineItem) {
      _mongoLineItem.lineItem = lineItem;
      return this;
    }
    
    public Builder withOrgId(String orgId) {
      _mongoLineItem.orgId = orgId;
      return this;
    }
    
    public Builder withTenantId(String tenantId) {
      _mongoLineItem.tenantId = tenantId;
      return this;
    }
    
    public MongoLineItem build() {
      
      if (StringUtils.isBlank(_mongoLineItem.orgId)
          || StringUtils.isBlank(_mongoLineItem.tenantId)
          || StringUtils.isBlank(_mongoLineItem.classSourcedId)
          || _mongoLineItem.lineItem == null) {
        throw new IllegalStateException(_mongoLineItem.toString());
      }
      
      return _mongoLineItem;
    }
  }
  
}
