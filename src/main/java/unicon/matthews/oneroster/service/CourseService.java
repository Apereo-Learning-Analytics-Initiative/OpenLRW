package unicon.matthews.oneroster.service;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicon.matthews.oneroster.Class;
import unicon.matthews.oneroster.Course;
import unicon.matthews.oneroster.service.repository.MongoCourse;
import unicon.matthews.oneroster.service.repository.MongoCourseRepository;

@Service
public class CourseService {
  
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
    
    if (classes != null) {
      for (Class cls : classes) {
        Class updatedClass
          = new Class.Builder()
            .withCourse(saved.getCourse())
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
