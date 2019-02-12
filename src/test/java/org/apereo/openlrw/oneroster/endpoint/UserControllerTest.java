package org.apereo.openlrw.oneroster.endpoint;

import org.apereo.model.oneroster.Link;
import org.apereo.model.oneroster.Result;
import org.apereo.openlrw.oneroster.TestData;
import org.apereo.openlrw.oneroster.exception.LineItemNotFoundException;
import org.apereo.openlrw.oneroster.exception.OrgNotFoundException;
import org.apereo.openlrw.oneroster.exception.ResultNotFoundException;
import org.apereo.openlrw.oneroster.service.ResultService;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

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
    userController = new UserController(null, null, null, resultService, null);
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
