package unicon.matthews.oneroster.service.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import unicon.matthews.Vocabulary;
import unicon.matthews.oneroster.AcademicSession;
import unicon.matthews.oneroster.TestData;

/**
 * @author stalele
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoAcademicSessionRepositoryTest {

  @Autowired
  private MongoAcademicSessionRepository mongoRepository;

  @Before
  public void setUp() throws Exception {
    Map<String, String> academicSession1Metadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
    AcademicSession academicSession1 = new AcademicSession.Builder().withTitle("academicSession1")
        .withSourcedId(TestData.ACADEMIC_SESSION_1).withMetadata(academicSession1Metadata).build();

    MongoAcademicSession session = new MongoAcademicSession.Builder().withAcademicSession(academicSession1)
        .withAcademicSessionSourcedId(TestData.ACADEMIC_SESSION_SOURCED_ID_1).withOrgId(TestData.ORG_1).withTenantId(TestData.TENANT_1).build();

    this.mongoRepository.save(session);
  }

  @Test
  public void testFindByTenantIdAndOrgIdAndAcademicSessionSourcedId() {
    MongoAcademicSession mongoAcademicSession = mongoRepository
        .findByTenantIdAndOrgIdAndAcademicSessionSourcedId(TestData.TENANT_1, TestData.ORG_1, TestData.ACADEMIC_SESSION_SOURCED_ID_1);
    assertNotNull(mongoAcademicSession);
    assertEquals(TestData.ACADEMIC_SESSION_SOURCED_ID_1, mongoAcademicSession.getAcademicSessionSourcedId());
  }

  @After
  public void tearDown() throws Exception {
    this.mongoRepository.deleteAll();
  }
}
