/**
 * 
 */
package unicon.matthews.tenant;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import unicon.matthews.FongoConfig;
import unicon.matthews.Matthews;
import unicon.matthews.tenant.service.TenantService;

/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class,FongoConfig.class})
@WebAppConfiguration
public class TenantServiceTest {
  @Autowired
  private TenantService unit;
  
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
  
  public void testFindAll() {
    Tenant tenant1 =
        new Tenant.Builder()
        .withName("test")
        .withDescription("test")
        .build();
    
    unit.save(tenant1);
    
    Tenant tenant2 =
        new Tenant.Builder()
        .withName("test")
        .withDescription("test")
        .build();
    
    unit.save(tenant2);
    
    Collection<Tenant> all = unit.findAll();
    
    assertTrue(all.size() >= 2);
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

    Optional<Tenant> foundTenant = unit.findById(savedTenant.getId());
    
    assertThat(foundTenant, is(notNullValue()));
    assertThat(foundTenant.get().getName(), is(equalTo(randomName)));
  }
  
  @Test(expected=IllegalStateException.class)
  public void testFindOneThatReturnsNull() {
    String randomId = UUID.randomUUID().toString();
    Optional<Tenant> foundTenant = unit.findById(randomId);
    foundTenant.orElseThrow(IllegalStateException::new);
  }
}
