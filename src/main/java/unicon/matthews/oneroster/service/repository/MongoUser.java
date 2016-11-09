/**
 * 
 */
package unicon.matthews.oneroster.service.repository;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import unicon.matthews.oneroster.User;

/**
 * @author ggilbert
 *
 */
@Document
public class MongoUser implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id private String id;
  private String orgId;
  private String tenantId;
  private User user;
  
  private MongoUser() {}

  public String getId() {
    return id;
  }

  public String getOrgId() {
    return orgId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public User getUser() {
    return user;
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
    result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
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
    MongoUser other = (MongoUser) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (orgId == null) {
      if (other.orgId != null)
        return false;
    } else if (!orgId.equals(other.orgId))
      return false;
    if (tenantId == null) {
      if (other.tenantId != null)
        return false;
    } else if (!tenantId.equals(other.tenantId))
      return false;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    return true;
  }

  public static class Builder {
    private MongoUser _mongoUser = new MongoUser();
    
    public Builder withId(String id) {
      this._mongoUser.id = id;
      return this;
    }
    
    public Builder withOrgId(String orgId) {
      this._mongoUser.orgId = orgId;
      return this;
    }
    
    public Builder withTenantId(String tenantId) {
      this._mongoUser.tenantId = tenantId;
      return this;
    }
    
    public Builder withUser(User user) {
      this._mongoUser.user = user;
      return this;
    }
    
    public MongoUser build() {
      return _mongoUser;
    }
  }
}
