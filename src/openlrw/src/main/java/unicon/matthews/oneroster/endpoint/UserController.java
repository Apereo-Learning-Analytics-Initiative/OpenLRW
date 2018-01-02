/**
 * 
 */
package unicon.matthews.oneroster.endpoint;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;

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

import unicon.matthews.entity.MongoUserMappingRepository;
import unicon.matthews.entity.UserMapping;
import unicon.matthews.oneroster.Enrollment;
import unicon.matthews.oneroster.Result;
import unicon.matthews.oneroster.User;
import unicon.matthews.oneroster.exception.EnrollmentNotFoundException;
import unicon.matthews.oneroster.exception.ResultNotFoundException;
import unicon.matthews.oneroster.exception.UserNotFoundException;
import unicon.matthews.oneroster.service.EnrollmentService;
import unicon.matthews.oneroster.service.ResultService;
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
  private EnrollmentService enrollmentService;
  private MongoUserMappingRepository mongoUserMappingRepository;
  private ResultService resultService;
  
  @Autowired
  public UserController(UserService userService, EnrollmentService enrollmentService, MongoUserMappingRepository mongoUserMappingRepository, ResultService resultService) {
    this.userService = userService;
    this.enrollmentService = enrollmentService;
    this.mongoUserMappingRepository = mongoUserMappingRepository;
    this.resultService = resultService;
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
    return userService.findBySourcedId(userContext.getTenantId(), userContext.getOrgId(), userId);
  }
  
  @RequestMapping(value = "/{userId}/enrollments", method = RequestMethod.GET)
  public Collection<Enrollment> getEnrollmentsForUser(JwtAuthenticationToken token, @PathVariable("userId") final String userId) throws EnrollmentNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return enrollmentService.findEnrollmentsForUser(userContext.getTenantId(), userContext.getOrgId(), userId);
  }
  
  @RequestMapping(value = "/mapping/{externalUserId}", method = RequestMethod.GET)
  public UserMapping getUserMapping(JwtAuthenticationToken token, @PathVariable("externalUserId") final String externalUserId) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return mongoUserMappingRepository.findByTenantIdAndOrganizationIdAndUserExternalIdIgnoreCase(userContext.getTenantId(), userContext.getOrgId(), externalUserId);
  }
  
  @RequestMapping(value= "/mapping", method = RequestMethod.POST)
  public ResponseEntity<?> postUserMapping(JwtAuthenticationToken token, @RequestBody UserMapping um) {
    UserContext userContext = (UserContext) token.getPrincipal();
        
    UserMapping existingUserMapping = mongoUserMappingRepository
      .findByTenantIdAndOrganizationIdAndUserExternalIdIgnoreCase(userContext.getTenantId(), userContext.getOrgId(), um.getUserExternalId());
    
    if (existingUserMapping == null) {
      UserMapping userMapping 
        = new UserMapping.Builder()
        .withUserExternalId(um.getUserExternalId())
        .withUserSourcedId(um.getUserSourcedId())
        .withDateLastModified(LocalDateTime.now(ZoneId.of("UTC")))
        .withOrganizationId(userContext.getOrgId())
        .withTenantId(userContext.getTenantId())
        .build();
    
      UserMapping saved = mongoUserMappingRepository.save(userMapping);
    
      return new ResponseEntity<>(saved, null, HttpStatus.CREATED);
    }
    
    return new ResponseEntity<>(existingUserMapping, null, HttpStatus.NOT_MODIFIED);

  }
  
  /** Returns the Result for user
   * @param token
   * @param userId
   * @return Result 
   * @throws ResultNotFoundException
   */
  @RequestMapping(value = "/{userId}/results", method = RequestMethod.GET)
  public Result getResultsForUser(JwtAuthenticationToken token, @PathVariable final String userId) throws ResultNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return resultService.getResultsForUser(userContext.getTenantId(), userContext.getOrgId(), userId);
  }

}
