 /**
 * 
 */
package unicon.matthews.oneroster.endpoint;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import unicon.matthews.oneroster.Class;
import unicon.matthews.oneroster.LineItem;
import unicon.matthews.oneroster.Link;
import unicon.matthews.oneroster.Result;
import unicon.matthews.oneroster.TestData;
import unicon.matthews.oneroster.exception.LineItemNotFoundException;
import unicon.matthews.oneroster.exception.OrgNotFoundException;
import unicon.matthews.oneroster.exception.ResultNotFoundException;
import unicon.matthews.oneroster.service.LineItemService;
import unicon.matthews.oneroster.service.ResultService;
import unicon.matthews.security.auth.JwtAuthenticationToken;
import unicon.matthews.security.model.UserContext;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ClassControllerTest {

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  private MockMvc mockMvc;
  @Mock
  private static ResultService resultService;

  @MockBean
  JwtAuthenticationToken jwttoken;

  @Mock
  private LineItemService lineItemService;
  
  @InjectMocks
  private ClassController classController;
  
  LineItem li 
    = new LineItem.Builder()
      .withTitle("li")
      .withSourcedId("lisid")
      .withClass(new Class.Builder().withSourcedId("class123").build())
      .build();

  Result result = new Result.Builder().withResultstatus("Grade A").withComment("good").withSourcedId("122")
      .withLineitem(new Link.Builder().withSourcedId(TestData.LINEITEM_SOURCED_ID).build())
      .withStudent(new Link.Builder().withSourcedId("999").build()).build();

  @Before
  public void init() throws OrgNotFoundException, LineItemNotFoundException {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(classController).build();
    
    classController = new ClassController(lineItemService,null,null,null,resultService,null);
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("ROLE_TENANT_ADMIN"));
    UserContext context = UserContext.create(TestData.TENANT_1, "122", authorities);
    when(jwttoken.getPrincipal()).thenReturn(context);

    when(lineItemService.getLineItemsForClass("","","class123")).thenReturn(Collections.singletonList(li));
    when(lineItemService.save("","", li)).thenReturn(li);
  }

  //@Test(expected=OrgNotFoundException.class)
  public void test_GetLineItemsForClass() throws Exception {

    mockMvc.perform(get("/api/classes/class123/lineitems")
        .header("Authentication", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZW1vLW9yZy1zb3VyY2VkLWlkIiwic2NvcGVzIjpbIlJPTEVfT1JHX0FETUlOIl0sInRlbmFudCI6ImRlbW8tdGVuYW50LWlkIiwiaXNzIjoiaHR0cDovL3N2bGFkYS5jb20iLCJpYXQiOjE0NzgwMDY2NzksImV4cCI6MTQ3ODAwNzU3OX0.xZWpNroRWnfTCoEq1sFBlGJ-5l_K-4LYOdOSGb6jfB9ut3HT9_aP8LKJSn7EwcewWlt5e6X9PKoAYURn7hhx-w"));
  }

  //@Test
  public void test_PostLineItem() throws Exception {

    String liJson = json(li);
    this.mockMvc
    .perform(post("/api/classes/class123/lineitems")
        .header("Authentication", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZW1vLW9yZy1zb3VyY2VkLWlkIiwic2NvcGVzIjpbIlJPTEVfT1JHX0FETUlOIl0sInRlbmFudCI6ImRlbW8tdGVuYW50LWlkIiwiaXNzIjoiaHR0cDovL3N2bGFkYS5jb20iLCJpYXQiOjE0NzgwMDY2NzksImV4cCI6MTQ3ODAwNzU3OX0.xZWpNroRWnfTCoEq1sFBlGJ-5l_K-4LYOdOSGb6jfB9ut3HT9_aP8LKJSn7EwcewWlt5e6X9PKoAYURn7hhx-w")
        .contentType(contentType).content(liJson)).andExpect(status().isCreated());
  }
  
  @Test
  public void testGetResultForLineitem() throws Exception {
    when(resultService.getResultsForlineItem(TestData.TENANT_1, "*", TestData.LINEITEM_SOURCED_ID)).thenReturn(result);
    Result result = classController.getLineItemsResults(jwttoken, TestData.LINEITEM_SOURCED_ID);
    assertTrue(result.getComment().equalsIgnoreCase("good"));
  }

  @Test(expected = ResultNotFoundException.class)
  public void testGetLineItemsResultsNotFoundException() throws Exception {
    String lineitemSourcedId = "1223";
    when(resultService.getResultsForlineItem(TestData.TENANT_1, "*", lineitemSourcedId)).thenThrow(ResultNotFoundException.class);
    classController.getLineItemsResults(jwttoken, lineitemSourcedId);
  }

  protected String json(Object o) throws IOException {
    try {
      return new ObjectMapper().writeValueAsString(o);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
