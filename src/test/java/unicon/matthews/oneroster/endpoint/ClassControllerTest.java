/**
 * 
 */
package unicon.matthews.oneroster.endpoint;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import unicon.matthews.oneroster.Class;
import unicon.matthews.oneroster.LineItem;
import unicon.matthews.oneroster.exception.LineItemNotFoundException;
import unicon.matthews.oneroster.exception.OrgNotFoundException;
import unicon.matthews.oneroster.service.LineItemService;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ggilbert
 *
 */
public class ClassControllerTest {
  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  private MockMvc mockMvc;

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
  
  public void init() throws OrgNotFoundException, LineItemNotFoundException {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(classController).build();
    
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
  
  protected String json(Object o) throws IOException {
    try {
      return new ObjectMapper().writeValueAsString(o);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
