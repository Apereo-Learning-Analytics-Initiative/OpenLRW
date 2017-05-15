package unicon.matthews.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {
  private AdminUserRepository adminUserRepository;
  
  @Autowired
  public AdminUserService(AdminUserRepository adminUserRepository) {
    this.adminUserRepository = adminUserRepository;
  }
}
