/**
 * 
 */
package unicon.matthews.tenant;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import unicon.matthews.FongoConfig;
import unicon.matthews.Matthews;
import unicon.matthews.tenant.service.repository.TenantRepository;

/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class,FongoConfig.class})
@WebAppConfiguration
public class TenantRepositoryTest {
  
  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private TenantRepository unit;
  
  @Test
  public void testSave() {
    Tenant tenant =
        new Tenant.Builder()
        .withName("test")
        .withDescription("test")
        .build();
    
    Tenant savedTenant = unit.save(tenant);
    assertThat(savedTenant, is(notNullValue()));
    assertThat(savedTenant.getId(), is(notNullValue()));
  }
  
  @Test
  public void testFindOne() {
    String randomName = UUID.randomUUID().toString();
    Tenant tenant =
        new Tenant.Builder()
        .withName(randomName)
        .withDescription("test")
        .build();
    
    Tenant savedTenant = unit.save(tenant);

    Tenant foundTenant = unit.findOne(savedTenant.getId());
    
    assertThat(foundTenant, is(notNullValue()));
    assertThat(foundTenant.getName(), is(equalTo(randomName)));
  }
}
