/**
 * 
 */
package unicon.matthews.oneroster.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import unicon.matthews.oneroster.User;
import unicon.matthews.oneroster.exception.UserNotFoundException;
import unicon.matthews.oneroster.service.UserService;
import unicon.matthews.security.auth.JwtAuthenticationToken;
import unicon.matthews.security.model.UserContext;

/**
 * @author ggilbert
 *
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
  
  private UserService userService;
  
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }
  
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> post(JwtAuthenticationToken token, @RequestBody User user) {
    UserContext userContext = (UserContext) token.getPrincipal();
    User savedUser = this.userService.save(userContext.getTenantId(), userContext.getOrgId(), user);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedUser.getSourcedId()).toUri());
    return new ResponseEntity<>(savedUser, httpHeaders, HttpStatus.CREATED);
  }
  
  @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
  public User getUser(JwtAuthenticationToken token, @PathVariable("userId") final String userId) throws UserNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return userService.findBySourcedId(userContext.getTenantId(), userContext.getTenantId(), userId);
  }

}
