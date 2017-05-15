package unicon.matthews.admin;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = AdminUser.Builder.class)
public final class AdminUser implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id private String id;
  private String tenantId;
  private String orgId;
  private boolean superAdmin;
  
  @NotNull private String username;
  @NotNull private String password;
  
  private Map<String, String> metadata;
  
  private LocalDateTime lastModified;
  
  private AdminUser() {}

  public String getId() {
    return id;
  }

  public String getTenantId() {
    return tenantId;
  }

  public String getOrgId() {
    return orgId;
  }

  public boolean isSuperAdmin() {
    return superAdmin;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public LocalDateTime getLastModified() {
    return lastModified;
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
    result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
    result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
    result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
    result = prime * result + ((password == null) ? 0 : password.hashCode());
    result = prime * result + (superAdmin ? 1231 : 1237);
    result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
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
    AdminUser other = (AdminUser) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (lastModified == null) {
      if (other.lastModified != null)
        return false;
    } else if (!lastModified.equals(other.lastModified))
      return false;
    if (metadata == null) {
      if (other.metadata != null)
        return false;
    } else if (!metadata.equals(other.metadata))
      return false;
    if (orgId == null) {
      if (other.orgId != null)
        return false;
    } else if (!orgId.equals(other.orgId))
      return false;
    if (password == null) {
      if (other.password != null)
        return false;
    } else if (!password.equals(other.password))
      return false;
    if (superAdmin != other.superAdmin)
      return false;
    if (tenantId == null) {
      if (other.tenantId != null)
        return false;
    } else if (!tenantId.equals(other.tenantId))
      return false;
    if (username == null) {
      if (other.username != null)
        return false;
    } else if (!username.equals(other.username))
      return false;
    return true;
  }

  public static class Builder {
    // See Tenant for an example of the builder structure we are using
  }

}
