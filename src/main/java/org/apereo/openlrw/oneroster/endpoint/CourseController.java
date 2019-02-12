package org.apereo.openlrw.oneroster.endpoint;

import org.apereo.model.oneroster.Course;
import org.apereo.openlrw.oneroster.exception.LineItemNotFoundException;
import org.apereo.openlrw.oneroster.service.CourseService;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
  
  private CourseService courseService;

  @Autowired
  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }
  
  @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
  public Course getClass(JwtAuthenticationToken token, @PathVariable final String courseId) throws LineItemNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return courseService.findBySourcedId(userContext.getTenantId(), userContext.getOrgId(), courseId);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> postCourse(JwtAuthenticationToken token, @RequestBody Course course) {
    UserContext userContext = (UserContext) token.getPrincipal();
    Course saved = courseService.save(userContext.getTenantId(), userContext.getOrgId(), course);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(saved.getSourcedId()).toUri());
    return new ResponseEntity<>(saved, httpHeaders, HttpStatus.CREATED);
  } 

}
