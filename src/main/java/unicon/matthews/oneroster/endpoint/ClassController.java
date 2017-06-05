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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import unicon.matthews.caliper.ClassEventStatistics;
import unicon.matthews.caliper.Event;
import unicon.matthews.caliper.service.EventService;
import unicon.matthews.entity.ClassMapping;
import unicon.matthews.entity.MongoClassMappingRepository;
import unicon.matthews.oneroster.Class;
import unicon.matthews.oneroster.Enrollment;
import unicon.matthews.oneroster.LineItem;
import unicon.matthews.oneroster.Result;
import unicon.matthews.oneroster.exception.EnrollmentNotFoundException;
import unicon.matthews.oneroster.exception.LineItemNotFoundException;
import unicon.matthews.oneroster.exception.ResultNotFoundException;
import unicon.matthews.oneroster.service.ClassService;
import unicon.matthews.oneroster.service.EnrollmentService;
import unicon.matthews.oneroster.service.LineItemService;
import unicon.matthews.oneroster.service.ResultService;
import unicon.matthews.security.auth.JwtAuthenticationToken;
import unicon.matthews.security.model.UserContext;

/**
 * @author ggilbert
 *
 */
@RestController
@RequestMapping("/api/classes")
public class ClassController {
  
  private LineItemService lineItemService;
  private EnrollmentService enrollmentService;
  private EventService eventService;
  private ClassService classService;
  private ResultService resultService;
  private MongoClassMappingRepository mongoClassMappingRepository;
  
  @Autowired
  public ClassController(LineItemService lineItemService, 
      EnrollmentService enrollmentService,
      EventService eventService,
      ClassService classService,
      ResultService resultService,
      MongoClassMappingRepository mongoClassMappingRepository) {
    this.lineItemService = lineItemService;
    this.enrollmentService = enrollmentService;
    this.eventService = eventService;
    this.classService = classService;
    this.resultService = resultService;
    this.mongoClassMappingRepository = mongoClassMappingRepository;
  }
  
  @RequestMapping(value = "/{classId}", method = RequestMethod.GET)
  public Class getClass(JwtAuthenticationToken token, @PathVariable final String classId) throws LineItemNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return classService.findBySourcedId(userContext.getTenantId(), userContext.getOrgId(), classId);
  }
  
  @RequestMapping(value = "/{classId}/events/stats", method = RequestMethod.GET)
  public ClassEventStatistics getEventStatisticsForClass(JwtAuthenticationToken token, @PathVariable final String classId, 
      @RequestParam(name="studentsOnly",required=false,defaultValue="true") String studentsOnly) {
    UserContext userContext = (UserContext) token.getPrincipal();
    
    return eventService.getEventStatisticsForClass(userContext.getTenantId(), userContext.getOrgId(), classId, Boolean.valueOf(studentsOnly));
  }
  
  @RequestMapping(value = "/{classId}/events/user/{userId}", method = RequestMethod.GET)
  public Collection<Event> getEventForClassAndUser(JwtAuthenticationToken token, @PathVariable final String classId, @PathVariable final String userId) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return eventService.getEventsForClassAndUser(userContext.getTenantId(), userContext.getOrgId(), classId, userId);
  }

  @RequestMapping(value = "/{classId}/lineitems", method = RequestMethod.GET)
  public Collection<LineItem> getLineItemsForClass(JwtAuthenticationToken token, @PathVariable final String classId) throws LineItemNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return lineItemService.getLineItemsForClass(userContext.getTenantId(), userContext.getOrgId(), classId);
  }
  
  /** Retrieves the result of lineitem of class 
   * @param token
   * @param lineitemId
   * @return Result
   * @throws ResultNotFoundException
   */
  @RequestMapping(value = "/{classId}/lineitems/{lineitemId}/results", method = RequestMethod.GET)
  public Result getLineItemsResults(JwtAuthenticationToken token, @PathVariable final String lineitemId) throws ResultNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return resultService.getResultsForlineItem(userContext.getTenantId(), userContext.getOrgId(), lineitemId);
  }
  
  @RequestMapping(value= "/{classId}/lineitems", method = RequestMethod.POST)
  public ResponseEntity<?> postLineItem(JwtAuthenticationToken token, @RequestBody LineItem lineItem) {
    UserContext userContext = (UserContext) token.getPrincipal();
    LineItem savedLineItem = this.lineItemService.save(userContext.getTenantId(), userContext.getOrgId(), lineItem);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedLineItem.getSourcedId()).toUri());
    return new ResponseEntity<>(savedLineItem, httpHeaders, HttpStatus.CREATED);
  }
  
  @RequestMapping(value = "/{classId}/results", method = RequestMethod.GET)
  public Collection<Result> getResultsForClass(JwtAuthenticationToken token, @PathVariable final String classId) throws LineItemNotFoundException, ResultNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return resultService.getResultsForClass(userContext.getTenantId(), userContext.getOrgId(), classId);
  }
  
  @RequestMapping(value= "/{classId}/results", method = RequestMethod.POST)
  public ResponseEntity<?> postResult(JwtAuthenticationToken token, @PathVariable final String classId, @RequestBody Result result) {
    UserContext userContext = (UserContext) token.getPrincipal();
    Result savedResult = this.resultService.save(userContext.getTenantId(), userContext.getOrgId(), classId, result);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedResult.getSourcedId()).toUri());
    return new ResponseEntity<>(savedResult, httpHeaders, HttpStatus.CREATED);
  }
  
  @RequestMapping(value= "/{classId}/enrollments", method = RequestMethod.POST)
  public ResponseEntity<?> postEnrollment(JwtAuthenticationToken token, @RequestBody Enrollment enrollment) {
    UserContext userContext = (UserContext) token.getPrincipal();
    Enrollment savedEnrollment = enrollmentService.save(userContext.getTenantId(), userContext.getOrgId(), enrollment);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedEnrollment.getSourcedId()).toUri());
    return new ResponseEntity<>(savedEnrollment, httpHeaders, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{classId}/enrollments", method = RequestMethod.GET)
  public Collection<Enrollment> getEnrollmentsForClass(JwtAuthenticationToken token, @PathVariable final String classId) throws EnrollmentNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return enrollmentService.findEnrollmentsForClass(userContext.getTenantId(), userContext.getOrgId(), classId);
  }
  
  @RequestMapping(value = "/mapping/{externalClassId}", method = RequestMethod.GET)
  public ClassMapping getClassMapping(JwtAuthenticationToken token, @PathVariable("externalClassId") final String externalClassId) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return mongoClassMappingRepository.findByTenantIdAndOrganizationIdAndClassExternalId(userContext.getTenantId(), userContext.getOrgId(), externalClassId);
  }
  
  @RequestMapping(value= "/mapping", method = RequestMethod.POST)
  public ResponseEntity<?> postClassMapping(JwtAuthenticationToken token, @RequestBody ClassMapping cm) {
    UserContext userContext = (UserContext) token.getPrincipal();
    
    ClassMapping classMapping = null;
    
    ClassMapping existingClassMapping = mongoClassMappingRepository
      .findByTenantIdAndOrganizationIdAndClassExternalId(userContext.getTenantId(), userContext.getOrgId(), cm.getClassExternalId());
    
    if (existingClassMapping == null) {
      classMapping 
      = new ClassMapping.Builder()
        .withClassExternalId(cm.getClassExternalId())
        .withClassSourcedId(cm.getClassSourcedId())
        .withDateLastModified(LocalDateTime.now(ZoneId.of("UTC")))
        .withOrganizationId(userContext.getOrgId())
        .withTenantId(userContext.getTenantId())
        .build();
      
      ClassMapping saved = mongoClassMappingRepository.save(classMapping);
      
      return new ResponseEntity<>(saved, null, HttpStatus.CREATED);
    }
    
    return new ResponseEntity<>(existingClassMapping, null, HttpStatus.NOT_MODIFIED);
  }
  
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> postClass(JwtAuthenticationToken token, @RequestBody Class klass) {
    UserContext userContext = (UserContext) token.getPrincipal();
    Class saved = classService.save(userContext.getTenantId(), userContext.getOrgId(), klass);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(saved.getSourcedId()).toUri());
    return new ResponseEntity<>(saved, httpHeaders, HttpStatus.CREATED);
  } 
  
  //@RequestMapping(value= "/{classId}", method = RequestMethod.PUT)
  public ResponseEntity<?> putClass(JwtAuthenticationToken token, @PathVariable("classId") final String classId, @RequestBody Class klass) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return null;
  }

}
