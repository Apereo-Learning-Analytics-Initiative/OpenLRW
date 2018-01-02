package unicon.matthews.admin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration values to create a super admin user
 */
@Configuration
@ConfigurationProperties(prefix = "matthews.users")
public class AdminUserConfig {

    private String adminuser;

    private String password;

    private String emailAddress;

    private boolean encrypted;

    public String getAdminuser() {
        return adminuser;
    }

    public void setAdminuser(String adminuser) {
        this.adminuser = adminuser;
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

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    @Override
    public String toString() {
        return "SuperAdminUser{" +
                "adminuser='" + adminuser + '\'' +
                ", password='" + password + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", encrypted=" + encrypted +
                '}';
    }
}
