/**
 * 
 */
package unicon.matthews.oneroster.endpoint;

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
  
  @Autowired
  public ClassController(LineItemService lineItemService, 
      EnrollmentService enrollmentService,
      EventService eventService) {
    this.lineItemService = lineItemService;
    this.enrollmentService = enrollmentService;
    this.eventService = eventService;
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
}
