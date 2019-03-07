package org.apereo.openlrw.oneroster;

import org.apereo.model.oneroster.User;
import org.apereo.openlrw.oneroster.service.repository.MongoUser;
import org.apereo.openlrw.oneroster.service.repository.MongoUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.apereo.openlrw.FongoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FongoConfig.class})
@WebAppConfiguration
public class MongoUserRepositoryTest {
  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private MongoUserRepository unit;

  @Test
  public void testSave() {
    User user = new User.Builder()
        .withSourcedId("user1")
        .build();
    
    MongoUser mu = new MongoUser.Builder()
          .withTenantId("tenant-1")
          .withOrgId("org-1")
          .withUser(user)
          .build();
    
    MongoUser saved = unit.save(mu);
    assertThat(saved, is(notNullValue()));
    assertThat(saved.getId(), is(notNullValue()));
  }
  
  @Test
  public void testFindOne() {
    User user = new User.Builder()
        .withSourcedId("user1")
        .build();
    
    MongoUser mu = new MongoUser.Builder()
          .withTenantId("tenant-1")
          .withOrgId("org-1")
          .withUser(user)
          .build();
    
    MongoUser saved = unit.save(mu);

    MongoUser found = unit.findById(saved.getId()).orElse(null);
    
    assertThat(found, is(notNullValue()));
  }

}
