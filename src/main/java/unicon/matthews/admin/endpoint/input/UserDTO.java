package unicon.matthews.admin.endpoint.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

@ApiModel
public class UserDTO {

    private String id;

    private String tenantId;

    private String orgId;

    private String username;

    private String password = "";

    private String emailAddress;

    @ApiModelProperty(position = 1, value = "User Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ApiModelProperty(position = 2, value = "Tenant Id")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @ApiModelProperty(position = 3, value = "Organization Id")
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @ApiModelProperty(position = 4, value = "User Name")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ApiModelProperty(position = 5, value = "Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ApiModelProperty(position = 6, value = "Email Address")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("tenantId", tenantId)
                .append("orgId", orgId)
                .append("username", username)
                .append("password", password)
                .append("emailAddress", emailAddress)
                .toString();
    }
}
