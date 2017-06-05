package unicon.matthews.oneroster.service.repository;

import java.io.Serializable;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import unicon.matthews.oneroster.AcademicSession;

/**
 * @author stalele
 *
 */
@Document
public class MongoAcademicSession implements Serializable{

  private static final long serialVersionUID = 1L;
  
  @Id private String id;
  private String orgId;
  private String tenantId;
  private AcademicSession academicSession;
  private String academicSessionSourcedId;
  
  public String getAcademicSessionSourcedId() {
    return academicSessionSourcedId;
  }
  public String getId() {
    return id;
  }

  public String getOrgId() {
    return orgId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public AcademicSession getAcademicSession() {
    return academicSession;
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
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    MongoAcademicSession other = (MongoAcademicSession) obj;
    if (academicSession == null) {
      if (other.academicSession != null)
        return false;
    }
    else if (!academicSession.equals(other.academicSession))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    }
    else if (!id.equals(other.id))
      return false;
    if (orgId == null) {
      if (other.orgId != null)
        return false;
    }
    else if (!orgId.equals(other.orgId))
      return false;
    if (tenantId == null) {
      if (other.tenantId != null)
        return false;
    }
    else if (!tenantId.equals(other.tenantId))
      return false;
    return true;
  }

  public static class Builder {
    private MongoAcademicSession _mongoAcademicSession = new MongoAcademicSession();

    public Builder withId(String id) {
      _mongoAcademicSession.id = id;
      return this;
    }

    public Builder withOrgId(String orgId) {
      _mongoAcademicSession.orgId = orgId;
      return this;
    }

    public Builder withTenantId(String tenantId) {
      _mongoAcademicSession.tenantId = tenantId;
      return this;
    }

    public Builder withAcademicSession(AcademicSession academicSession) {
      _mongoAcademicSession.academicSession = academicSession;
      return this;
    }

    public Builder withAcademicSessionSourcedId(String sourcedId) {
      _mongoAcademicSession.academicSessionSourcedId = sourcedId;
      return this;
    }

    public MongoAcademicSession build() {
      if (StringUtils.isBlank(_mongoAcademicSession.orgId) || StringUtils.isBlank(_mongoAcademicSession.tenantId)) {
        throw new IllegalStateException(_mongoAcademicSession.toString());
      }

      return _mongoAcademicSession;
    }
  }
}
