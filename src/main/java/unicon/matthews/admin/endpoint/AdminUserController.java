package unicon.matthews.admin.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import unicon.matthews.admin.AdminUser;
import unicon.matthews.admin.endpoint.input.UserDTO;
import unicon.matthews.admin.service.AdminUserService;

@RestController
@RequestMapping("/api/adminuser")
@Api(value = "/adminuser", description = "Create and manage admin users in the system")
public class AdminUserController {

    private static Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private AdminUserService adminUserService;

    /**
     * Create a new admin user for a tenant and organization
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "Create a new admin user for tenant and organization", notes = "Create a new admin user for tenant and organization", httpMethod = "POST", response = AdminUser.class)
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAdminUser(@RequestBody UserDTO user) {
        try {
            AdminUser adminUser = adminUserService.createAdminUser(user);
            if (adminUser.getId() != null) {
                return new ResponseEntity<>(adminUser, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("Error to create a new admin user account " + user.getUsername() + " exception = " + ex.getMessage(), ex);
        }
        return new ResponseEntity<>("Error creating a user admin account: " + user.getUsername(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
