package org.apereo.openlrw.oneroster;

import org.apereo.model.oneroster.Org;
import org.apereo.openlrw.oneroster.service.repository.MongoOrg;
import org.apereo.openlrw.oneroster.service.repository.MongoOrgRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.apereo.openlrw.FongoConfig;
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
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FongoConfig.class})
@WebAppConfiguration
public class MongoOrgRepositoryTest {
  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private MongoOrgRepository unit;

  @Test
  public void testSave() {
    Org org = new Org.Builder()
        .withName("test")
        .withSourcedId("org1")
        .build();
    
    MongoOrg mo = new MongoOrg.Builder()
          .withApiKey(UUID.randomUUID().toString())
          .withApiSecret(UUID.randomUUID().toString())
          .withTenantId("tenant-1")
          .withOrg(org)
          .build();
    
    MongoOrg saved = unit.save(mo);
    assertThat(saved, is(notNullValue()));
    assertThat(saved.getId(), is(notNullValue()));
  }
  
  @Test
  public void testFindOne() {
    String randomName = UUID.randomUUID().toString();
    Org org = new Org.Builder()
        .withName("test")
        .withSourcedId("org1")
        .build();
    
    MongoOrg mo = new MongoOrg.Builder()
          .withApiKey(randomName)
          .withApiSecret(UUID.randomUUID().toString())
          .withTenantId("tenant-1")
          .withOrg(org)
          .build();
    
    MongoOrg saved = unit.save(mo);

    MongoOrg found = unit.findById(saved.getId()).orElse(null);
    
    assertThat(found, is(notNullValue()));
    assertThat(found.getApiKey(), is(equalTo(randomName)));
  }
}
