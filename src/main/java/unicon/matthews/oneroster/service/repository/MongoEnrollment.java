/**
 * 
 */
package unicon.matthews.oneroster.service.repository;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import unicon.matthews.oneroster.Enrollment;

/**
 * @author ggilbert
 *
 */
@Document
public class MongoEnrollment {
  @Id private String id;
  private Enrollment enrollment;
  private String tenantId;
  private String orgId;
  private String userSourcedId;
  private String classSourcedId;
  
  private MongoEnrollment() {}
  
  public String getId() {
    return id;
  }

  public Enrollment getEnrollment() {
    return enrollment;
  }

  public String getTenantId() {
    return tenantId;
  }

  public String getOrgId() {
    return orgId;
  }

  public String getUserSourcedId() {
    return userSourcedId;
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
    result = prime * result + ((enrollment == null) ? 0 : enrollment.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
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
    MongoEnrollment other = (MongoEnrollment) obj;
    if (classSourcedId == null) {
      if (other.classSourcedId != null)
        return false;
    } else if (!classSourcedId.equals(other.classSourcedId))
      return false;
    if (enrollment == null) {
      if (other.enrollment != null)
        return false;
    } else if (!enrollment.equals(other.enrollment))
      return false;
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
    if (userSourcedId == null) {
      if (other.userSourcedId != null)
        return false;
    } else if (!userSourcedId.equals(other.userSourcedId))
      return false;
    return true;
  }

  public static class Builder {
    private MongoEnrollment _mongoEnrollment = new MongoEnrollment();
    
    public Builder withUserSourcedId(String userSourcedId) {
      _mongoEnrollment.userSourcedId = userSourcedId;
      return this;
    }
    
    public Builder withClassSourcedId(String classSourcedId) {
      _mongoEnrollment.classSourcedId = classSourcedId;
      return this;
    }
    
    public Builder withEnrollment(Enrollment enrollment) {
      _mongoEnrollment.enrollment = enrollment;
      return this;
    }
    
    public Builder withTenantId(String tenantId) {
      _mongoEnrollment.tenantId = tenantId;
      return this;
    }
    
    public Builder withOrgId(String orgId) {
      _mongoEnrollment.orgId = orgId;
      return this;
    }
    
    public Builder withId(String id) {
      _mongoEnrollment.id = id;
      return this;
    }
    
    public MongoEnrollment build() {
      return _mongoEnrollment;
    }
  }


}
