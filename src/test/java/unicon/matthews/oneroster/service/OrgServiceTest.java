/**
 * 
 */
package unicon.matthews.oneroster.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import unicon.matthews.FongoConfig;
import unicon.matthews.Matthews;
import unicon.matthews.oneroster.Org;
import unicon.matthews.oneroster.OrgType;
import unicon.matthews.oneroster.Status;
import unicon.matthews.oneroster.exception.OrgNotFoundException;


/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class,FongoConfig.class})
@WebAppConfiguration
public class OrgServiceTest {
  @Autowired
  private OrgService unit;
  
  @Test
  public void testSave() {
    Org org =
        new Org.Builder()
        .withName("test")
        .withSourcedId("sid")
        .withStatus(Status.active)
        .withType(OrgType.institution)
        .build();
    
    Org savedOrg = unit.save("tenant-1",org);
    assertThat(savedOrg, is(notNullValue()));
    assertThat(savedOrg.getSourcedId(), is(notNullValue()));
  }
  
  
  @Test
  public void testFindByTenantAndSourcedId() throws OrgNotFoundException {
    String randomId = UUID.randomUUID().toString();
    Org org =
        new Org.Builder()
        .withName("test")
        .withSourcedId(randomId)
        .withStatus(Status.active)
        .withType(OrgType.institution)
        .build();
    
    Org savedOrg = unit.save("tenant-1", org);

    Org foundOrg = unit.findByTenantIdAndOrgSourcedId("tenant-1",savedOrg.getSourcedId());
    
    assertThat(foundOrg, is(notNullValue()));
    assertThat(foundOrg.getSourcedId(), is(equalTo(randomId)));
  }
  
  @Test(expected=OrgNotFoundException.class)
  public void testFindOneThatReturnsNull() throws OrgNotFoundException {
    String randomId = UUID.randomUUID().toString();
    unit.findByTenantIdAndOrgSourcedId("tenant-1",randomId);
  }
}
