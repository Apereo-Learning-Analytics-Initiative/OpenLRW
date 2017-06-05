package unicon.matthews.oneroster.service.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
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
import unicon.matthews.oneroster.Link;
import unicon.matthews.oneroster.Result;
import unicon.matthews.oneroster.Status;
import unicon.matthews.oneroster.TestData;

/**
 * @author stalele
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoResultRepositoryTest {

  @Autowired
  private MongoResultRepository mongoRepository;

  @Before
  public void setUp() throws Exception {
    Map<String, String> resultMetadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
    Result result = 
        new Result.Builder()
        .withResultstatus("Grade B")
        .withScore(70.0)
        .withComment("not bad")
        .withMetadata(resultMetadata)
        .withSourcedId(TestData.RESULT_SOURCED_ID)
        .withDate(LocalDateTime.now())
        .withDateLastModified(LocalDateTime.now())
        .withStatus(Status.active)
        .withLineitem(new Link.Builder().withSourcedId(TestData.LINEITEM_SOURCED_ID).build())
        .withStudent(new Link.Builder().withSourcedId(TestData.USER_SOURCED_ID).build())
        .build();

    MongoResult session = new MongoResult.Builder()
        .withResult(result)
        .withClassSourcedId("classSourcedId")
        .withLineitemSourcedId(TestData.LINEITEM_SOURCED_ID)
        .withOrgId(TestData.ORG_1)
        .withTenantId(TestData.TENANT_1)
        .withUserSourcedId(TestData.USER_SOURCED_ID)
        .build();

    this.mongoRepository.save(session);
  }

  @Test
  public void testFindByTenantIdAndOrgIdAndResultSourcedId() {
    MongoResult mongoResult = mongoRepository
        .findByTenantIdAndOrgIdAndResultSourcedId(TestData.TENANT_1, TestData.ORG_1, TestData.RESULT_SOURCED_ID);
    assertNotNull(mongoResult);
    assertEquals(mongoResult.getResult().getScore(), new Double(70.0));
  }
  
  @Test
  public void testFindByTenantIdAndOrgIdAndLineitemSourcedId() {
    MongoResult mongoResult = mongoRepository
        .findByTenantIdAndOrgIdAndLineitemSourcedId(TestData.TENANT_1, TestData.ORG_1, TestData.LINEITEM_SOURCED_ID);
    assertNotNull(mongoResult);
    assertEquals(mongoResult.getResult().getScore(), new Double(70.0));
  }
  
  @Test
  public void testFindByTenantIdAndOrgIdAndUserSourcedId() {
    MongoResult mongoResult = mongoRepository
        .findByTenantIdAndOrgIdAndUserSourcedId(TestData.TENANT_1, TestData.ORG_1, TestData.USER_SOURCED_ID);
    assertNotNull(mongoResult);
    assertEquals(mongoResult.getResult().getScore(), new Double(70.0));
  }

  @After
  public void tearDown() throws Exception {
    this.mongoRepository.deleteAll();
  }
}
