package unicon.matthews.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/adminuser")
public class AdminUserController {
  private static Logger logger = LoggerFactory.getLogger(AdminUserController.class);
  
  private AdminUserService adminUserService;
  
  @Autowired
  public AdminUserController(AdminUserService adminUserService) {
    this.adminUserService = adminUserService;
  }

}
