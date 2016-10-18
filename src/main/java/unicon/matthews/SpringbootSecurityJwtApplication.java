package unicon.matthews;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import unicon.matthews.entity.Role;
import unicon.matthews.entity.User;
import unicon.matthews.user.repository.UserRepository;

/**
 * Sample application for demonstrating security with JWT Tokens
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
@SpringBootApplication
@EnableConfigurationProperties
public class SpringbootSecurityJwtApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityJwtApplication.class, args);
	}
	
	@Autowired UserRepository userRepository;
	
	@PostConstruct
	public void loadDemoData() {
	  List<Role> roles = new ArrayList<>();
	  roles.add(Role.ADMIN);
	  roles.add(Role.PREMIUM_MEMBER);
	  User user1 = new User(new Long(1), "svlada@gmail.com", "$2a$10$bnC26zz//2cavYoSCrlHdecWF8tkGfPodlHcYwlACBBwJvcEf0p2G", roles);
	  userRepository.save(user1);
	}
}
