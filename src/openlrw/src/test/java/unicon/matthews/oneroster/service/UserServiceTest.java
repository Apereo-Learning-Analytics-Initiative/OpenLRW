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
import unicon.matthews.oneroster.User;
import unicon.matthews.oneroster.exception.UserNotFoundException;

/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class,FongoConfig.class})
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
    
    User savedUser = unit.save("tenant-1","org-1",user);
    assertThat(savedUser, is(notNullValue()));
    assertThat(savedUser.getSourcedId(), is(notNullValue()));
  }
  
  
  @Test
  public void testFindByTenantAndOrgIdAndSourcedId() throws UserNotFoundException {
    User user =
        new User.Builder()
        .withSourcedId("user-sid")
        .build();
    
    unit.save("tenant-1","org-1",user);

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
