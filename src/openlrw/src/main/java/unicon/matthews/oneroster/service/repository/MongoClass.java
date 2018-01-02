package unicon.matthews.oneroster.service.repository;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import unicon.matthews.oneroster.Class;

@Document
public class MongoClass implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id private String id;
  private String orgId;
  private String tenantId;
  private Class klass;
  private String classSourcedId;
  
  private MongoClass() {}

  public String getId() {
    return id;
  }

  public String getOrgId() {
    return orgId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public Class getKlass() {
    return klass;
  }

  public String getClassSourcedId() {
    return classSourcedId;
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
    result = prime * result + ((klass == null) ? 0 : klass.hashCode());
    result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
    result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
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
    MongoClass other = (MongoClass) obj;
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
    if (klass == null) {
      if (other.klass != null)
        return false;
    } else if (!klass.equals(other.klass))
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
    return true;
  }
  
  public static class Builder {
    private MongoClass _mongoClass = new MongoClass();
    
    public Builder withClassSourcedId(String classSourcedId) {
      _mongoClass.classSourcedId = classSourcedId;
      return this;
    }
    
    public Builder withId(String id) {
      _mongoClass.id = id;
      return this;
    }
    
    public Builder withKlass(Class klass) {
      _mongoClass.klass = klass;
      return this;
    }
    
    public Builder withOrgId(String orgId) {
      _mongoClass.orgId = orgId;
      return this;
    }
    
    public Builder withTenantId(String tenantId) {
      _mongoClass.tenantId = tenantId;
      return this;
    }
    
    public MongoClass build() {
      
      if (StringUtils.isBlank(_mongoClass.orgId)
          || StringUtils.isBlank(_mongoClass.tenantId)
          || StringUtils.isBlank(_mongoClass.classSourcedId)
          || _mongoClass.klass == null) {
        throw new IllegalStateException(_mongoClass.toString());
      }
      
      return _mongoClass;
    }
  }


}
