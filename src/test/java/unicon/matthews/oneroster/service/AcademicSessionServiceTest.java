/**
 * 
 */
package unicon.matthews.oneroster.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import unicon.matthews.FongoConfig;
import unicon.matthews.Matthews;
import unicon.matthews.Vocabulary;
import unicon.matthews.oneroster.AcademicSession;
import unicon.matthews.oneroster.TestData;
import unicon.matthews.oneroster.exception.AcademicSessionNotFoundException;

/**
 * @author stalele
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class,FongoConfig.class})
@WebAppConfiguration
public class AcademicSessionServiceTest {

  @Autowired
  private AcademicSessionService unit;

  @Test
  public void testSave() {
    Map<String, String> academicSession1Metadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
    AcademicSession academicSession1 = 
        new AcademicSession.Builder()
        .withTitle("academicSession1")
        .withSourcedId(TestData.ACADEMIC_SESSION_1)
        .withMetadata(academicSession1Metadata)
        .build();

    AcademicSession savedAcademicSession = unit.save(TestData.TENANT_1,"org-1",academicSession1);
    assertThat(savedAcademicSession, is(notNullValue()));
    assertThat(savedAcademicSession.getSourcedId(), is(notNullValue()));
  }


  @Test
  public void testFindByTenantAndOrgIdAndSourcedId() throws AcademicSessionNotFoundException {
    Map<String, String> academicSession1Metadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
    AcademicSession academicSession1 = 
        new AcademicSession.Builder()
        .withTitle("academicSession1")
        .withSourcedId(TestData.ACADEMIC_SESSION_1)
        .withMetadata(academicSession1Metadata)
        .build();
    unit.save(TestData.TENANT_1,"org-1",academicSession1);

    AcademicSession found = unit.findBySourcedId(TestData.TENANT_1,"org-1", TestData.ACADEMIC_SESSION_1);

    assertThat(found, is(notNullValue()));
    assertThat(found.getSourcedId(), is(equalTo("academicSessionId-1")));
  }

  @Test(expected=AcademicSessionNotFoundException.class)
  public void testFindOneThatReturnsNull() throws AcademicSessionNotFoundException {
    String randomId = UUID.randomUUID().toString();
    unit.findBySourcedId(TestData.TENANT_1,"org-1",randomId);
  }

}
