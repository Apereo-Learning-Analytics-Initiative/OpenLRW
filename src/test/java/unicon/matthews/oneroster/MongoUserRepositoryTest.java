/**
 * 
 */
package unicon.matthews.oneroster;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import unicon.matthews.FongoConfig;
import unicon.matthews.oneroster.service.repository.MongoUser;
import unicon.matthews.oneroster.service.repository.MongoUserRepository;

/**
 * @author ggilbert
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
    User user =
        new User.Builder()
        .withSourcedId("user1")
        .build();
    
    MongoUser mu
      = new MongoUser.Builder()
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
    User user =
        new User.Builder()
        .withSourcedId("user1")
        .build();
    
    MongoUser mu
      = new MongoUser.Builder()
          .withTenantId("tenant-1")
          .withOrgId("org-1")
          .withUser(user)
          .build();
    
    MongoUser saved = unit.save(mu);

    MongoUser found = unit.findOne(saved.getId());
    
    assertThat(found, is(notNullValue()));
  }

}
