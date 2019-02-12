package org.apereo.openlrw.tenant;

import org.apereo.openlrw.OpenLRW;
import org.apereo.openlrw.tenant.service.TenantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.apereo.openlrw.FongoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;



/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={OpenLRW.class,FongoConfig.class})
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
