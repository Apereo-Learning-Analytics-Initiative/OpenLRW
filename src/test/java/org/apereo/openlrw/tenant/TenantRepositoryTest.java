package org.apereo.openlrw.tenant;

import org.apereo.openlrw.FongoConfig;
import org.apereo.openlrw.OpenLRW;
import org.apereo.openlrw.tenant.service.repository.TenantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={OpenLRW.class,FongoConfig.class})
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

    Tenant foundTenant = unit.findById(savedTenant.getId()).orElse(null);
    
    assertThat(foundTenant, is(notNullValue()));
    assertThat(foundTenant.getName(), is(equalTo(randomName)));
  }
}
