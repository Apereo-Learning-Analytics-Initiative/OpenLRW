package org.apereo.openlrw.oneroster.endpoint;

import org.apereo.model.entity.ClassMapping;
import org.apereo.model.oneroster.Class;
import org.apereo.model.oneroster.Enrollment;
import org.apereo.model.oneroster.LineItem;
import org.apereo.model.oneroster.Result;
import org.apereo.openlrw.caliper.service.EventService;
import org.apereo.openlrw.entity.MongoClassMappingRepository;
import org.apereo.openlrw.oneroster.exception.EnrollmentNotFoundException;
import org.apereo.openlrw.oneroster.exception.LineItemNotFoundException;
import org.apereo.openlrw.oneroster.exception.ResultNotFoundException;
import org.apereo.openlrw.oneroster.service.ClassService;
import org.apereo.openlrw.oneroster.service.EnrollmentService;
import org.apereo.openlrw.oneroster.service.LineItemService;
import org.apereo.openlrw.oneroster.service.ResultService;
import org.apereo.openlrw.oneroster.service.repository.MongoClass;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.apereo.openlrw.caliper.ClassEventStatistics;
import org.apereo.openlrw.caliper.Event;

import java.time.Instant;
import java.util.Collection;


/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
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
  
  @RequestMapping(value = "/{classId:.+}", method = RequestMethod.GET)
  public Class getClass(JwtAuthenticationToken token, @PathVariable final String classId) throws LineItemNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return classService.findBySourcedId(userContext.getTenantId(), userContext.getOrgId(), classId);
  }
  
  @RequestMapping(value = "/{classId:.+}/events/stats", method = RequestMethod.GET)
  public ClassEventStatistics getEventStatisticsForClass(JwtAuthenticationToken token, @PathVariable final String classId,
                                                         @RequestParam(name="studentsOnly",required=false,defaultValue="true") String studentsOnly) {
    UserContext userContext = (UserContext) token.getPrincipal();
    
    return eventService.getEventStatisticsForClass(userContext.getTenantId(), userContext.getOrgId(), classId, Boolean.valueOf(studentsOnly));
  }

  @RequestMapping(value = "/{classId}/events/user/{userId:.+}", method = RequestMethod.GET)
  public Collection<Event> getEventForClassAndUser(JwtAuthenticationToken token, @PathVariable final String classId, @PathVariable final String userId) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return eventService.getEventsForClassAndUser(userContext.getTenantId(), userContext.getOrgId(), classId, userId);
  }
  
  @RequestMapping(value = "/{classId}/results/user/{userId}", method = RequestMethod.GET)
  public Collection<Result> getResultsForClassAndUser(JwtAuthenticationToken token, @PathVariable final String classId, @PathVariable final String userId) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return resultService.getResultsForClassAndUser(userContext.getTenantId(), userContext.getOrgId(), classId, userId);
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
  @RequestMapping(value = "/{classId:.+}/lineitems/{lineitemId}/results", method = RequestMethod.GET)
  public Result getLineItemsResults(JwtAuthenticationToken token, @PathVariable final String lineitemId) throws ResultNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return resultService.getResultsForlineItem(userContext.getTenantId(), userContext.getOrgId(), lineitemId);
  }
  
  @RequestMapping(value= "/{classId:.+}/lineitems", method = RequestMethod.POST)
  public ResponseEntity<?> postLineItem(JwtAuthenticationToken token, @RequestBody LineItem lineItem, @RequestParam(value="check", required=false) Boolean check) {
    UserContext userContext = (UserContext) token.getPrincipal();
    LineItem savedLineItem = this.lineItemService.save(userContext.getTenantId(), userContext.getOrgId(), lineItem, (check == null) ? true : check);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedLineItem.getSourcedId()).toUri());
    return new ResponseEntity<>(savedLineItem, httpHeaders, HttpStatus.CREATED);
  }
  
  @RequestMapping(value = "/{classId:.+}/results", method = RequestMethod.GET)
  public Collection<Result> getResultsForClass(JwtAuthenticationToken token, @PathVariable final String classId) throws LineItemNotFoundException, ResultNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return resultService.getResultsForClass(userContext.getTenantId(), userContext.getOrgId(), classId);
  }
  
  @RequestMapping(value= "/{classId:.+}/results", method = RequestMethod.POST)
  public ResponseEntity<?> postResult(JwtAuthenticationToken token, @PathVariable final String classId, @RequestBody Result result, @RequestParam(value="check", required=false) Boolean check) {
    UserContext userContext = (UserContext) token.getPrincipal();
    Result savedResult = this.resultService.save(userContext.getTenantId(), userContext.getOrgId(), classId, result, (check == null) ? true : check);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest()
               .path("/{id}")
               .buildAndExpand(savedResult.getSourcedId())
               .toUri());

    return new ResponseEntity<>(savedResult, httpHeaders, HttpStatus.CREATED);
  }
  
  @RequestMapping(value= "/{classId:.+}/enrollments", method = RequestMethod.POST)
  public ResponseEntity<?> postEnrollment(JwtAuthenticationToken token, @PathVariable final String classId, @RequestBody Enrollment enrollment, @RequestParam(value="check", required=false) Boolean check) {
    UserContext userContext = (UserContext) token.getPrincipal();
    Enrollment savedEnrollment = enrollmentService.save(userContext.getTenantId(), userContext.getOrgId(), classId, enrollment, (check == null) ? true : check);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedEnrollment.getSourcedId()).toUri());
    return new ResponseEntity<>(savedEnrollment, httpHeaders, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{classId:.+}/enrollments", method = RequestMethod.GET)
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
        .withDateLastModified(Instant.now())
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

  @RequestMapping(method = RequestMethod.GET)
  public Collection<MongoClass> getClass(JwtAuthenticationToken token) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return classService.findAll(userContext.getTenantId(), userContext.getOrgId());
  }

  //@RequestMapping(value= "/{classId}", method = RequestMethod.PUT)

  public ResponseEntity<?> putClass(JwtAuthenticationToken token, @PathVariable("classId") final String classId, @RequestBody Class klass) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return null;
  }

}
