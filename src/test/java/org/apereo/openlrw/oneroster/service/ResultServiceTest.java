/**
 * 
 */
package org.apereo.openlrw.oneroster.service;

import org.apereo.model.oneroster.Link;
import org.apereo.model.oneroster.Result;
import org.apereo.openlrw.OpenLRW;
import org.apereo.openlrw.Vocabulary;
import org.apereo.openlrw.oneroster.TestData;
import org.apereo.openlrw.oneroster.exception.ResultNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.apereo.openlrw.FongoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author stalele
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={OpenLRW.class,FongoConfig.class})
@WebAppConfiguration
public class ResultServiceTest {

  @Autowired
  private ResultService unit;
  
  @Test
  public void testSave() {
    Map<String, String> resultMetadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
    Result result =
        new Result.Builder()
        .withResultstatus("Grade A")
        .withComment("good")
        .withMetadata(resultMetadata)
        .withSourcedId(TestData.RESULT_SOURCED_ID)
        .withLineitem(new Link.Builder().withSourcedId(TestData.LINEITEM_SOURCED_ID).build())
        .withStudent(new Link.Builder().withSourcedId(TestData.USER_SOURCED_ID).build())
        .build();
    
    Result savedResult = unit.save(TestData.TENANT_1, TestData.ORG_1, TestData.CLASS_SOURCED_ID, result, true);
    assertThat(savedResult, is(notNullValue()));
    assertThat(savedResult.getSourcedId(), is(notNullValue()));
  }
  
  
  @Test
  public void testFindByTenantAndOrgIdAndClassSourcedId() throws ResultNotFoundException {
    Map<String, String> resultMetadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
    Result result = 
        new Result.Builder()
        .withResultstatus("Grade A")
        .withComment("good")
        .withMetadata(resultMetadata)
        .withSourcedId(TestData.RESULT_SOURCED_ID)
        .withLineitem(new Link.Builder().withSourcedId(TestData.LINEITEM_SOURCED_ID).build())
        .withStudent(new Link.Builder().withSourcedId(TestData.USER_SOURCED_ID).build())
        .build();
    unit.save(TestData.TENANT_1, TestData.ORG_1, "CLASS_SOURCED_ID-2", result, true);

    Collection<Result> found = unit.getResultsForClass(TestData.TENANT_1,TestData.ORG_1, "CLASS_SOURCED_ID-2");
    
    assertThat(found, is(notNullValue()));
    assertEquals(found.size(), 1);
  }
  
  @Test
  public void testFindByTenantAndOrgIdAndUserSourcedId() throws ResultNotFoundException {
    Map<String, String> resultMetadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
    Result result = 
        new Result.Builder()
        .withResultstatus("Grade B")
        .withScore(70.0)
        .withComment("not bad")
        .withMetadata(resultMetadata)
        .withSourcedId("resultsourcedId-2")
        .withLineitem(new Link.Builder().withSourcedId("lineitemsourcedId-3").build())
        .withStudent(new Link.Builder().withSourcedId("USER_SOURCED_ID-3").build())
        .build();
    unit.save(TestData.TENANT_1, TestData.ORG_1, TestData.CLASS_SOURCED_ID, result, true);

    Result found = unit.getResultsForUser(TestData.TENANT_1,TestData.ORG_1, "USER_SOURCED_ID-3");
    
    assertThat(found, is(notNullValue()));
    org.junit.Assert.assertEquals(new Double(70.0),result.getScore());
  }
  
  @Test
  public void testFindByTenantAndOrgIdAndLineitemSourcedId() throws ResultNotFoundException {
    Map<String, String> resultMetadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
    Result result = 
        new Result.Builder()
        .withResultstatus("Grade C")
        .withScore(40.0)
        .withComment("bad")
        .withMetadata(resultMetadata)
        .withSourcedId("resultsourcedId-2")
        .withLineitem(new Link.Builder().withSourcedId("lineitemsourcedId-4").build())
        .withStudent(new Link.Builder().withSourcedId("USER_SOURCED_ID-4").build())
        .build();
    unit.save(TestData.TENANT_1, TestData.ORG_1, TestData.CLASS_SOURCED_ID, result, true);

    Result found = unit.getResultsForlineItem(TestData.TENANT_1,TestData.ORG_1, "lineitemsourcedId-4");
    
    assertThat(found, is(notNullValue()));
    org.junit.Assert.assertEquals(new Double(40.0),result.getScore());
  }
  
  @Test(expected=ResultNotFoundException.class)
  public void testFindOneThatReturnsNull() throws ResultNotFoundException {
    String randomId = UUID.randomUUID().toString();
    unit.getResultsForClass(TestData.TENANT_1, TestData.ORG_1, randomId);
  }
  
  @Test(expected=ResultNotFoundException.class)
  public void testFindOneThatReturnsNullForUser() throws ResultNotFoundException {
    String randomId = UUID.randomUUID().toString();
    unit.getResultsForUser(TestData.TENANT_1, TestData.ORG_1, randomId);
  }
  
  @Test(expected=ResultNotFoundException.class)
  public void testFindOneThatReturnsNullForLineitem() throws ResultNotFoundException {
    String randomId = UUID.randomUUID().toString();
    unit.getResultsForlineItem(TestData.TENANT_1, TestData.ORG_1, randomId);
  }

}
