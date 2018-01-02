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

import unicon.matthews.oneroster.Course;
import unicon.matthews.oneroster.exception.LineItemNotFoundException;
import unicon.matthews.oneroster.service.CourseService;
import unicon.matthews.security.auth.JwtAuthenticationToken;
import unicon.matthews.security.model.UserContext;

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
