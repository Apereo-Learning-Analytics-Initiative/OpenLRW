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

import unicon.matthews.caliper.ClassEventStatistics;
import unicon.matthews.caliper.Event;
import unicon.matthews.caliper.service.EventService;
import unicon.matthews.entity.ClassMapping;
import unicon.matthews.entity.MongoClassMappingRepository;
import unicon.matthews.entity.UserMapping;
import unicon.matthews.oneroster.Enrollment;
import unicon.matthews.oneroster.LineItem;
import unicon.matthews.oneroster.exception.EnrollmentNotFoundException;
import unicon.matthews.oneroster.exception.LineItemNotFoundException;
import unicon.matthews.oneroster.service.EnrollmentService;
import unicon.matthews.oneroster.service.LineItemService;
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
  private MongoClassMappingRepository mongoClassMappingRepository;
  
  @Autowired
  public ClassController(LineItemService lineItemService, 
      EnrollmentService enrollmentService,
      EventService eventService,
      MongoClassMappingRepository mongoClassMappingRepository) {
    this.lineItemService = lineItemService;
    this.enrollmentService = enrollmentService;
    this.eventService = eventService;
    this.mongoClassMappingRepository = mongoClassMappingRepository;
  }
  
  @RequestMapping(value = "/{classId}/events/stats", method = RequestMethod.GET)
  public ClassEventStatistics getEventStatisticsForClass(JwtAuthenticationToken token, @PathVariable final String classId) throws LineItemNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return eventService.getEventStatisticsForClass(userContext.getTenantId(), userContext.getOrgId(), classId);
  }
  
  @RequestMapping(value = "/{classId}/events/user/{userId}", method = RequestMethod.GET)
  public Collection<Event> getEventForClassAndUser(JwtAuthenticationToken token, @PathVariable final String classId, @PathVariable final String userId) throws LineItemNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return eventService.getEventsForClassAndUser(userContext.getTenantId(), userContext.getOrgId(), classId, userId);
  }

  @RequestMapping(value = "/{classId}/lineitems", method = RequestMethod.GET)
  public Collection<LineItem> getLineItemsForClass(JwtAuthenticationToken token, @PathVariable final String classId) throws LineItemNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return lineItemService.getLineItemsForClass(userContext.getTenantId(), userContext.getOrgId(), classId);
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
  public ClassMapping getUserMapping(JwtAuthenticationToken token, @PathVariable("externalClassId") final String externalClassId) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return mongoClassMappingRepository.findByTenantIdAndOrganizationIdAndClassExternalId(userContext.getTenantId(), userContext.getOrgId(), externalClassId);
  }
  
  @RequestMapping(value= "/mapping", method = RequestMethod.POST)
  public ResponseEntity<?> postClassMapping(JwtAuthenticationToken token, @RequestBody ClassMapping cm) {
    UserContext userContext = (UserContext) token.getPrincipal();
    
    ClassMapping classMapping 
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

}
