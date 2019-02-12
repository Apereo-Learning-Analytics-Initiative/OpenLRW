package org.apereo.openlrw.oneroster;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
/**
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FongoConfig.class})
@WebAppConfiguration
public class MongoEnrollmentRepositoryTest {
  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private MongoEnrollmentRepository unit;

  @Test
  public void testSave() {
    String tenantId = "t-id";
    String orgId = "o-id";
    
    Class klass
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
    
    MongoEnrollment mongoEnrollment
      = new MongoEnrollment.Builder()
        .withClassSourcedId("c-id")
        .withEnrollment(enrollment)
        .withOrgId(orgId)
        .withTenantId(tenantId)
        .withUserSourcedId("u-id")
        .build();
    
    MongoEnrollment saved = unit.save(mongoEnrollment);
    assertThat(saved, is(notNullValue()));
  }
  
  @Test
  public void testFindEnrollmentsForClass() throws EnrollmentNotFoundException {
    String tenantId = "t-id";
    String orgId = "o-id";
    
    Class klass
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
    
    MongoEnrollment mongoEnrollment
      = new MongoEnrollment.Builder()
        .withClassSourcedId("c-id")
        .withEnrollment(enrollment)
        .withOrgId(orgId)
        .withTenantId(tenantId)
        .withUserSourcedId("u-id")
        .build();
    
    unit.save(mongoEnrollment);

    Collection<MongoEnrollment> found = unit.findByTenantIdAndOrgIdAndClassSourcedIdAndEnrollmentStatus(tenantId, orgId, "c-id", Status.active);
    assertThat(found, is(notNullValue()));
  }

  @Test
  public void testFindEnrollmentsForUser() throws EnrollmentNotFoundException {
    String tenantId = "t-id";
    String orgId = "o-id";
    
    org.apereo.openlrw.oneroster.Class klass
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
    
    MongoEnrollment mongoEnrollment
    = new MongoEnrollment.Builder()
      .withClassSourcedId("c-id")
      .withEnrollment(enrollment)
      .withOrgId(orgId)
      .withTenantId(tenantId)
      .withUserSourcedId("u-id")
      .build();

    unit.save(mongoEnrollment);

    Collection<MongoEnrollment> found = unit.findByTenantIdAndOrgIdAndUserSourcedIdIgnoreCaseAndEnrollmentStatus(tenantId, orgId, "u-id", Status.active);
    assertThat(found, is(notNullValue()));
  }

}
*/