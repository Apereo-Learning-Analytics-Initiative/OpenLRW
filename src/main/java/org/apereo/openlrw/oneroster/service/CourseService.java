package org.apereo.openlrw.oneroster.service;

import org.apache.commons.lang3.StringUtils;
import org.apereo.model.oneroster.Class;
import org.apereo.model.oneroster.Course;
import org.apereo.model.oneroster.Link;
import org.apereo.openlrw.oneroster.service.repository.MongoCourse;
import org.apereo.openlrw.oneroster.service.repository.MongoCourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@Service
public class CourseService {
  private static Logger logger = LoggerFactory.getLogger(CourseService.class);

  private MongoCourseRepository mongoCourseRepository;
  private ClassService classService;
  
  @Autowired
  public CourseService(MongoCourseRepository mongoCourseRepository,
      ClassService classService) {
    this.mongoCourseRepository = mongoCourseRepository;
    this.classService = classService;
  }

  public Course findBySourcedId(final String tenantId, final String orgId, final String courseSourcedId) {
    MongoCourse mongoCourse
      =  mongoCourseRepository
        .findByTenantIdAndOrgIdAndCourseSourcedId(tenantId, orgId, courseSourcedId);
    
    if (mongoCourse != null) {
      return mongoCourse.getCourse();
    }
    
    return null;
  }

  public Course save(final String tenantId, final String orgId, Course course) {
    if (StringUtils.isBlank(tenantId) 
        || StringUtils.isBlank(orgId)
        || course == null
        || StringUtils.isBlank(course.getSourcedId())) {
      throw new IllegalArgumentException();
    }
    
    MongoCourse mongoCourse
    =  mongoCourseRepository
      .findByTenantIdAndOrgIdAndCourseSourcedId(tenantId, orgId, course.getSourcedId());
    
    if (mongoCourse == null) {
      mongoCourse 
        = new MongoCourse.Builder()
          .withCourseSourcedId(course.getSourcedId())
          .withOrgId(orgId)
          .withTenantId(tenantId)
          .withCourse(course)
          .build();
    }
    else {
      mongoCourse
        = new MongoCourse.Builder()
          .withId(mongoCourse.getId())
          .withCourseSourcedId(mongoCourse.getCourseSourcedId())
          .withOrgId(mongoCourse.getOrgId())
          .withTenantId(mongoCourse.getTenantId())
          .withCourse(course)
          .build();
    }
    
    MongoCourse saved = mongoCourseRepository.save(mongoCourse);
    
    Collection<Class> classes = classService.findClassesForCourse(tenantId, orgId, saved.getCourseSourcedId());

    Link linkCourse = new Link.Builder()
            .withSourcedId(saved.getCourseSourcedId())
            .withType(Class.class.toString())
            .build();
    
    if (classes != null) {
      for (Class cls : classes) {
        Class updatedClass
          = new Class.Builder()
            .withCourse(linkCourse)
            .withMetadata(cls.getMetadata())
            .withSourcedId(cls.getSourcedId())
            .withStatus(cls.getStatus())
            .withTitle(cls.getTitle())
            .build();
        
        classService.save(tenantId, orgId, updatedClass);
      }
    }
    
   return saved.getCourse(); 

  }

}
