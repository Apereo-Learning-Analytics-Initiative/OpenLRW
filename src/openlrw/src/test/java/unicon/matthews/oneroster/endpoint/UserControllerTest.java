/**
 * 
 */
package unicon.matthews.oneroster.endpoint;

import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import unicon.matthews.oneroster.Link;
import unicon.matthews.oneroster.Result;
import unicon.matthews.oneroster.TestData;
import unicon.matthews.oneroster.exception.LineItemNotFoundException;
import unicon.matthews.oneroster.exception.OrgNotFoundException;
import unicon.matthews.oneroster.exception.ResultNotFoundException;
import unicon.matthews.oneroster.service.ResultService;
import unicon.matthews.security.auth.JwtAuthenticationToken;
import unicon.matthews.security.model.UserContext;

/**
 * @author stalele
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserControllerTest {

  @Mock
  private ResultService resultService;

  @InjectMocks
  private UserController userController;

  @MockBean
  JwtAuthenticationToken jwttoken;

  Result result = new Result.Builder().withResultstatus("Grade A").withComment("good").withSourcedId("122")
      .withLineitem(new Link.Builder().withSourcedId("333").build())
      .withStudent(new Link.Builder().withSourcedId(TestData.USER_SOURCED_ID).build()).build();

  @Before
  public void init() throws OrgNotFoundException, LineItemNotFoundException {
    MockitoAnnotations.initMocks(this);
    userController = new UserController(null, null, null, resultService);
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("ROLE_TENANT_ADMIN"));
    UserContext context = UserContext.create(TestData.TENANT_1, "122", authorities);
    when(jwttoken.getPrincipal()).thenReturn(context);
  }

  @Test
  public void testGetResultForUser() throws Exception {
    when(resultService.getResultsForUser(TestData.TENANT_1, "*", TestData.USER_SOURCED_ID)).thenReturn(result);
    Result result = userController.getResultsForUser(jwttoken, TestData.USER_SOURCED_ID);
    assertTrue(result.getComment().equalsIgnoreCase("good"));
  }

  @Test(expected = ResultNotFoundException.class)
  public void testGetResultForUserResultNotFoundException() throws Exception {
    String userSourcedId = "1223";
    when(resultService.getResultsForUser(TestData.TENANT_1, "*", userSourcedId)).thenThrow(ResultNotFoundException.class);
    userController.getResultsForUser(jwttoken, userSourcedId);
  }
}
