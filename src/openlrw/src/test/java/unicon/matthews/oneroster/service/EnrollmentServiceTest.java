/**
 * 
 */
package unicon.matthews.oneroster.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import unicon.matthews.FongoConfig;
import unicon.matthews.Matthews;
import unicon.matthews.oneroster.Class;
import unicon.matthews.oneroster.Enrollment;
import unicon.matthews.oneroster.Role;
import unicon.matthews.oneroster.Status;
import unicon.matthews.oneroster.User;
import unicon.matthews.oneroster.exception.EnrollmentNotFoundException;

/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class,FongoConfig.class})
@WebAppConfiguration
public class EnrollmentServiceTest {
  @Autowired
  private EnrollmentService enrollmentService;
  
  @Test
  public void testSave() {
    String tenantId = "t-id";
    String orgId = "o-id";
    
    unicon.matthews.oneroster.Class klass
      = new Class.Builder()
        .withSourcedId("c-id")
        .build();
    
    User user
      = new User.Builder()
        .withSourcedId("u-id")
        .build();
    
    Enrollment enrollment
      = new Enrollment.Builder()
        .withSourcedId("e-id")
        .withKlass(klass)
        .withRole(Role.student)
        .withStatus(Status.active)
        .withUser(user)
        .build();
    
    Enrollment saved = enrollmentService.save(tenantId, orgId, enrollment);
    assertThat(saved, is(notNullValue()));
  }
  
  @Test
  public void testFindEnrollmentsForClass() throws EnrollmentNotFoundException {
    String tenantId = "t-id";
    String orgId = "o-id";
    
    unicon.matthews.oneroster.Class klass
      = new Class.Builder()
        .withSourcedId("c-id")
        .build();
    
    User user
      = new User.Builder()
        .withSourcedId("u-id")
        .build();
    
    Enrollment enrollment
      = new Enrollment.Builder()
        .withSourcedId("e-id")
        .withKlass(klass)
        .withRole(Role.student)
        .withStatus(Status.active)
        .withUser(user)
        .build();
    
    enrollmentService.save(tenantId, orgId, enrollment);

    Collection<Enrollment> found = enrollmentService.findEnrollmentsForClass(tenantId, orgId, "c-id");
    assertThat(found, is(notNullValue()));
  }

  @Test(expected=EnrollmentNotFoundException.class)
  public void testFindEnrollmentsForClassThrowException() throws EnrollmentNotFoundException {
    String tenantId = "t-id";
    String orgId = "o-id";
    
    unicon.matthews.oneroster.Class klass
      = new Class.Builder()
        .withSourcedId("c-id")
        .build();
    
    User user
      = new User.Builder()
        .withSourcedId("u-id")
        .build();
    
    Enrollment enrollment
      = new Enrollment.Builder()
        .withSourcedId("e-id")
        .withKlass(klass)
        .withRole(Role.student)
        .withStatus(Status.active)
        .withUser(user)
        .build();
    
    enrollmentService.save(tenantId, orgId, enrollment);

    enrollmentService.findEnrollmentsForClass(tenantId, orgId, "not real");
  }

  @Test
  public void testFindEnrollmentsForUser() throws EnrollmentNotFoundException {
    String tenantId = "t-id";
    String orgId = "o-id";
    
    unicon.matthews.oneroster.Class klass
      = new Class.Builder()
        .withSourcedId("c-id")
        .build();
    
    User user
      = new User.Builder()
        .withSourcedId("u-id")
        .build();
    
    Enrollment enrollment
      = new Enrollment.Builder()
        .withSourcedId("e-id")
        .withKlass(klass)
        .withRole(Role.student)
        .withStatus(Status.active)
        .withUser(user)
        .build();
    
    enrollmentService.save(tenantId, orgId, enrollment);

    Collection<Enrollment> found = enrollmentService.findEnrollmentsForUser(tenantId, orgId, "u-id");
    assertThat(found, is(notNullValue()));
  }

  @Test(expected=EnrollmentNotFoundException.class)
  public void testFindEnrollmentsForUserThrowException() throws EnrollmentNotFoundException {
    String tenantId = "t-id";
    String orgId = "o-id";
    
    unicon.matthews.oneroster.Class klass
      = new Class.Builder()
        .withSourcedId("c-id")
        .build();
    
    User user
      = new User.Builder()
        .withSourcedId("u-id")
        .build();
    
    Enrollment enrollment
      = new Enrollment.Builder()
        .withSourcedId("e-id")
        .withKlass(klass)
        .withRole(Role.student)
        .withStatus(Status.active)
        .withUser(user)
        .build();
    
    enrollmentService.save(tenantId, orgId, enrollment);

    enrollmentService.findEnrollmentsForUser(tenantId, orgId, "not real");
  }

}
