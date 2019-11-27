package org.apereo.openlrw.oneroster.service;

import org.apereo.model.oneroster.User;
import org.apereo.openlrw.MongoServerConfig;
import org.apereo.openlrw.OpenLRW;
import org.apereo.openlrw.oneroster.exception.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(classes={OpenLRW.class, MongoServerConfig.class})
@WebAppConfiguration
public class UserServiceTest {
  @Autowired
  private UserService unit;
  
  @Test
  public void testSave() {
    User user =
        new User.Builder()
        .withSourcedId("user-sid")
        .build();
    
    User savedUser = unit.save("tenant-1","org-1",user, true);
    assertThat(savedUser, is(notNullValue()));
    assertThat(savedUser.getSourcedId(), is(notNullValue()));
  }
  
  
  @Test
  public void testFindByTenantAndOrgIdAndSourcedId() throws UserNotFoundException {
    User user =
        new User.Builder()
        .withSourcedId("user-sid")
        .build();
    
    unit.save("tenant-1","org-1",user, true);

    User found = unit.findBySourcedId("tenant-1","org-1", "user-sid");
    
    assertThat(found, is(notNullValue()));
    assertThat(found.getSourcedId(), is(equalTo("user-sid")));
  }
  
  @Test(expected=UserNotFoundException.class)
  public void testFindOneThatReturnsNull() throws UserNotFoundException {
    String randomId = UUID.randomUUID().toString();
    unit.findBySourcedId("tenant-1","org-1",randomId);
  }

}
