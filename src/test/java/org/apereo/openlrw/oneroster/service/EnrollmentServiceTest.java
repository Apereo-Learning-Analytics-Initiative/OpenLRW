package org.apereo.openlrw.oneroster.service;

import org.apereo.model.oneroster.*;
import org.apereo.model.oneroster.Class;
import org.apereo.openlrw.OpenLRW;
import org.apereo.openlrw.oneroster.exception.EnrollmentNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.apereo.openlrw.FongoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={OpenLRW.class,FongoConfig.class})
@WebAppConfiguration
public class EnrollmentServiceTest {
  @Autowired
  private EnrollmentService enrollmentService;
  
  @Test
  public void testSave() {
    String tenantId = "t-id";
    String orgId = "o-id";
    String classId = "c-id";
    
    Class klass = new Class.Builder()
        .withSourcedId(classId)
        .build();

    Link classLink = new Link.Builder().withType("Class").withSourcedId(classId).build();

    User user = new User.Builder().withSourcedId("u-id").build();

    Link userLink = new Link.Builder().withType("User").withSourcedId("u-id").build();

    Enrollment enrollment
      = new Enrollment.Builder()
        .withSourcedId("e-id")
        .withKlass(classLink)
        .withRole(Role.student)
        .withStatus(Status.active)
        .withUser(userLink)
        .build();
    
    Enrollment saved = enrollmentService.save(tenantId, classId, orgId, enrollment, true);
    assertThat(saved, is(notNullValue()));
  }
  
  @Test
  public void testFindEnrollmentsForClass() throws EnrollmentNotFoundException {
    String tenantId = "t-id";
    String orgId = "o-id";
    String classId = "c-id";
    
    Class klass
      = new Class.Builder()
        .withSourcedId(classId)
        .build();
    
    User user
      = new User.Builder()
        .withSourcedId("u-id")
        .build();

    Link classLink = new Link.Builder().withType("Class").withSourcedId(classId).build();
    Link userLink = new Link.Builder().withType("User").withSourcedId("u-id").build();

    Enrollment enrollment
      = new Enrollment.Builder()
        .withSourcedId("e-id")
        .withKlass(classLink)
        .withRole(Role.student)
        .withStatus(Status.active)
        .withUser(userLink)
        .build();
    
    enrollmentService.save(tenantId, orgId, classId, enrollment, true);

    Collection<Enrollment> found = enrollmentService.findEnrollmentsForClass(tenantId, orgId, "c-id");
    assertThat(found, is(notNullValue()));
  }

  @Test(expected=EnrollmentNotFoundException.class)
  public void testFindEnrollmentsForClassThrowException() throws EnrollmentNotFoundException {
    String tenantId = "t-id";
    String orgId = "o-id";
    String classId = "c-id";
    
    Class klass
      = new Class.Builder()
        .withSourcedId(classId)
        .build();
    
    User user
      = new User.Builder()
        .withSourcedId("u-id")
        .build();

    Link classLink = new Link.Builder().withType("Class").withSourcedId(classId).build();
    Link userLink = new Link.Builder().withType("User").withSourcedId("u-id").build();

    Enrollment enrollment
      = new Enrollment.Builder()
        .withSourcedId("e-id")
        .withKlass(classLink)
        .withRole(Role.student)
        .withStatus(Status.active)
        .withUser(userLink)
        .build();
    
    enrollmentService.save(tenantId, orgId, classId, enrollment, true);

    enrollmentService.findEnrollmentsForClass(tenantId, orgId, "not real");
  }

  @Test
  public void testFindEnrollmentsForUser() throws EnrollmentNotFoundException {
    String tenantId = "t-id";
    String orgId = "o-id";
    String classId = "c-id";
    
    Class klass
      = new Class.Builder()
        .withSourcedId(classId)
        .build();
    
    User user
      = new User.Builder()
        .withSourcedId("u-id")
        .build();

    Link classLink = new Link.Builder().withType("Class").withSourcedId(classId).build();
    Link userLink = new Link.Builder().withType("User").withSourcedId("u-id").build();
    
    Enrollment enrollment
      = new Enrollment.Builder()
        .withSourcedId("e-id")
        .withKlass(classLink)
        .withRole(Role.student)
        .withStatus(Status.active)
        .withUser(userLink)
        .build();
    
    enrollmentService.save(tenantId, orgId, classId, enrollment, true);

    Collection<Enrollment> found = enrollmentService.findEnrollmentsForUser(tenantId, orgId, "u-id");
    assertThat(found, is(notNullValue()));
  }

  @Test(expected=EnrollmentNotFoundException.class)
  public void testFindEnrollmentsForUserThrowException() throws EnrollmentNotFoundException {
    String tenantId = "t-id";
    String orgId = "o-id";
    String classId = "c-id";
    
    Class klass
      = new Class.Builder()
        .withSourcedId(classId)
        .build();
    
    User user
      = new User.Builder()
        .withSourcedId("u-id")
        .build();

    Link classLink = new Link.Builder().withType("Class").withSourcedId(classId).build();
    Link userLink = new Link.Builder().withType("User").withSourcedId("u-id").build();
    
    Enrollment enrollment
      = new Enrollment.Builder()
        .withSourcedId("e-id")
        .withKlass(classLink)
        .withRole(Role.student)
        .withStatus(Status.active)
        .withUser(userLink)
        .build();
    
    enrollmentService.save(tenantId, orgId, classId, enrollment, true);
    enrollmentService.findEnrollmentsForUser(tenantId, orgId, "not real");
  }

}
