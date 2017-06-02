package unicon.matthews.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import unicon.matthews.admin.AdminUser;
import unicon.matthews.admin.AdminUserConfig;
import unicon.matthews.admin.endpoint.input.UserDTO;
import unicon.matthews.admin.service.repository.AdminUserRepository;

import java.util.Optional;

@Service
@ConfigurationProperties(prefix = "matthews.users")
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AdminUserConfig adminUserConfig;

    /**
     * Create a new super admin user.
     *
     * @param superAdminUser
     * @return
     * @
     */
    public AdminUser createUser(final AdminUser superAdminUser) {
        final String encodedPassword = (adminUserConfig.isEncrypted())?
                passwordEncoder.encode(superAdminUser.getPassword()) : superAdminUser.getPassword();
        superAdminUser.setPassword(encodedPassword);
        adminUserRepository.save(superAdminUser);
        return superAdminUser;
    }

    /**
     * Create a new admin user for given tenant and organization
     *
     * @param user
     * @return
     */
    public AdminUser createAdminUser(final UserDTO user) {
        final String encodedPassword = (adminUserConfig.isEncrypted())?
                passwordEncoder.encode(user.getPassword()) : user.getPassword();
        AdminUser adminUser = new AdminUser.Builder()
                .withUserName(user.getUsername())
                .withPassword(encodedPassword)
                .withEmailAddress(user.getEmailAddress())
                .withOrgId(user.getOrgId())
                .withTenantId(user.getTenantId())
                .withSuperAdmin(Boolean.FALSE)
                .build();
        adminUserRepository.save(adminUser);
        return adminUser;
    }

    /**
     * Authenticate any admin user by username and password
     *
     * @param userName
     * @param password
     * @return
     */
    public AdminUser authenticateUser(final String userName, final String password) throws AuthenticationException {
        AdminUser adminUser = adminUserRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username=%s was not found", userName)));
        if (adminUser != null && (adminUserConfig.isEncrypted()) ? passwordEncoder.matches(password, adminUser.getPassword()) : password.equals(adminUser.getPassword())) {
            return adminUser;
        } else {
            throw new BadCredentialsException(String.format("User with the supplied credentials cannot be authenticated", userName));
        }
    }


    /***
     * Find admin user by user name
     * @param userName
     * @return
     */
    public Optional<AdminUser> findByUserName(final String userName) {
        return adminUserRepository.findByUsername(userName);
    }
}
