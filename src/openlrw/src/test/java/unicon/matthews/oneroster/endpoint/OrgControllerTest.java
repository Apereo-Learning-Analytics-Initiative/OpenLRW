/**
 * 
 */
package unicon.matthews.oneroster.endpoint;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import unicon.matthews.Vocabulary;
import unicon.matthews.oneroster.Org;
import unicon.matthews.oneroster.exception.OrgNotFoundException;
import unicon.matthews.oneroster.service.OrgService;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ggilbert
 *
 */
public class OrgControllerTest {
  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  private MockMvc mockMvc;

  @Mock
  private OrgService orgService;
  
  @InjectMocks
  private OrgController orgController;
  
  Map<String, String> org1Metadata = Collections.singletonMap(Vocabulary.TENANT, "tenant-1");
  Org org1 = new Org.Builder().withName("org1").withSourcedId("org1-id").withMetadata(org1Metadata).build();
  
  Map<String, String> org2Metadata = Collections.singletonMap(Vocabulary.TENANT, "tenant-1");
  Org org2 = new Org.Builder().withName("org2").withSourcedId("org2-id").withMetadata(org2Metadata).build();
  
  Map<String, String> org3Metadata = Collections.singletonMap(Vocabulary.TENANT, "tenant-2");
  Org org3 = new Org.Builder().withName("org3").withSourcedId("org3-id").withMetadata(org3Metadata).build();
  
  public void init() throws OrgNotFoundException {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(orgController).build();

    when(orgService.findByTenantIdAndOrgSourcedId("tenant-1","org1-id")).thenReturn(org1);
    when(orgService.findByTenantIdAndOrgSourcedId("tenant-2","org2-id")).thenReturn(org1);
    when(orgService.save("tenant-2",org3)).thenReturn(org3);

  }
  
  //@Test
  public void readSingleOrg() throws Exception {

    mockMvc
    .perform(get("/api/tenants/org1-id")
        .header("Authentication", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZW1vLW9yZy1zb3VyY2VkLWlkIiwic2NvcGVzIjpbIlJPTEVfT1JHX0FETUlOIl0sInRlbmFudCI6InRlbmFudC0xIiwiaXNzIjoiaHR0cDovL3N2bGFkYS5jb20iLCJpYXQiOjE0NzgwMDY2NzksImV4cCI6MTQ3ODAwNzU3OX0.GryYTxAaZIfktauwFZCNERYnhIvyHQ-pgy-l5C7vspc"))
    .andExpect(status().isOk()).andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.sourcedId", is("org1-id"))).andExpect(jsonPath("$.name", is("org1")));
  }

  //@Test(expected=OrgNotFoundException.class)
  public void readTenants() throws Exception {

    mockMvc.perform(get("/api/tenants/org33-id")
        .header("Authentication", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZW1vLW9yZy1zb3VyY2VkLWlkIiwic2NvcGVzIjpbIlJPTEVfT1JHX0FETUlOIl0sInRlbmFudCI6ImRlbW8tdGVuYW50LWlkIiwiaXNzIjoiaHR0cDovL3N2bGFkYS5jb20iLCJpYXQiOjE0NzgwMDY2NzksImV4cCI6MTQ3ODAwNzU3OX0.xZWpNroRWnfTCoEq1sFBlGJ-5l_K-4LYOdOSGb6jfB9ut3HT9_aP8LKJSn7EwcewWlt5e6X9PKoAYURn7hhx-w"));
  }

  //@Test
  public void createOrg() throws Exception {

    String orgJson = json(org3);
    this.mockMvc
    .perform(post("/api/orgs")
        .header("Authentication", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZW1vLW9yZy1zb3VyY2VkLWlkIiwic2NvcGVzIjpbIlJPTEVfT1JHX0FETUlOIl0sInRlbmFudCI6ImRlbW8tdGVuYW50LWlkIiwiaXNzIjoiaHR0cDovL3N2bGFkYS5jb20iLCJpYXQiOjE0NzgwMDY2NzksImV4cCI6MTQ3ODAwNzU3OX0.xZWpNroRWnfTCoEq1sFBlGJ-5l_K-4LYOdOSGb6jfB9ut3HT9_aP8LKJSn7EwcewWlt5e6X9PKoAYURn7hhx-w")
        .contentType(contentType).content(orgJson)).andExpect(status().isCreated());
  }

  protected String json(Object o) throws IOException {
    try {
      return new ObjectMapper().writeValueAsString(o);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
