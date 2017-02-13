package unicon.matthews.oneroster.service.repository;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import unicon.matthews.oneroster.Result;

@Document
public class MongoResult {
  @Id private String id;
  
  private String orgId;
  private String tenantId;
  
  @Indexed private String userSourcedId;
  @Indexed private String classSourcedId;
  @Indexed private String lineitemSourcedId;
  
  private Result result;
  
  private MongoResult() {}

  public String getId() {
    return id;
  }

  public String getOrgId() {
    return orgId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public String getUserSourcedId() {
    return userSourcedId;
  }

  public String getClassSourcedId() {
    return classSourcedId;
  }

  public String getLineitemSourcedId() {
    return lineitemSourcedId;
  }

  public Result getResult() {
    return result;
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
    result = prime * result + ((lineitemSourcedId == null) ? 0 : lineitemSourcedId.hashCode());
    result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
    result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
    result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
    result = prime * result + ((userSourcedId == null) ? 0 : userSourcedId.hashCode());
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
    MongoResult other = (MongoResult) obj;
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
    if (lineitemSourcedId == null) {
      if (other.lineitemSourcedId != null)
        return false;
    } else if (!lineitemSourcedId.equals(other.lineitemSourcedId))
      return false;
    if (orgId == null) {
      if (other.orgId != null)
        return false;
    } else if (!orgId.equals(other.orgId))
      return false;
    if (result == null) {
      if (other.result != null)
        return false;
    } else if (!result.equals(other.result))
      return false;
    if (tenantId == null) {
      if (other.tenantId != null)
        return false;
    } else if (!tenantId.equals(other.tenantId))
      return false;
    if (userSourcedId == null) {
      if (other.userSourcedId != null)
        return false;
    } else if (!userSourcedId.equals(other.userSourcedId))
      return false;
    return true;
  }

  public static class Builder {
    MongoResult _mongoResult = new MongoResult();
    
    public Builder withId(String id) {
      _mongoResult.id = id;
      return this;
    }

    public Builder withOrgId(String orgId) {
      _mongoResult.orgId = orgId;
      return this;
    }
    
    public Builder withTenantId(String tenantId) {
      _mongoResult.tenantId = tenantId;
      return this;
    }

    public Builder withClassSourcedId(String classSourcedId) {
      _mongoResult.classSourcedId = classSourcedId;
      return this;
    }

    public Builder withUserSourcedId(String userSourcedId) {
      _mongoResult.userSourcedId = userSourcedId;
      return this;
    }
    
    public Builder withLineitemSourcedId(String lineitemSourcedId) {
      _mongoResult.lineitemSourcedId = lineitemSourcedId;
      return this;
    }
    
    public Builder withResult(Result result) {
      _mongoResult.result = result;
      return this;
    }
    
    public MongoResult build() {
      if (StringUtils.isBlank(_mongoResult.orgId)
          || StringUtils.isBlank(_mongoResult.tenantId)
          || StringUtils.isBlank(_mongoResult.classSourcedId)
          || StringUtils.isBlank(_mongoResult.lineitemSourcedId)
          || _mongoResult.result == null) {
        throw new IllegalStateException(_mongoResult.toString());
      }
      
      return _mongoResult;

    }
  }

}
