package unicon.matthews.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = AdminUser.Builder.class)
public final class AdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String tenantId;

    private String orgId;

    private boolean superAdmin;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String emailAddress;


    private Map<String, String> metadata;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModified;

    private AdminUser() {
    }

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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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

        private AdminUser _user = new AdminUser();

        public Builder withId(String id) {
            _user.id = id;
            return this;
        }

        public Builder withTenantId(String tenantId) {
            _user.tenantId = tenantId;
            return this;
        }

        public Builder withOrgId(String orgId) {
            _user.orgId = orgId;
            return this;
        }

        public Builder withUserName(String userName) {
            _user.username = userName;
            return this;
        }

        public Builder withPassword(String password) {
            _user.password = password;
            return this;
        }

        public Builder withEmailAddress(String emailAddress) {
            _user.emailAddress = emailAddress;
            return this;
        }

        public Builder withSuperAdmin(boolean superAdmin) {
            _user.superAdmin = superAdmin;
            return this;
        }

        public Builder withMetadata(Map<String, String> metadata) {
            _user.metadata = metadata;
            return this;
        }

        public AdminUser build() {
            return _user;
        }
    }
}