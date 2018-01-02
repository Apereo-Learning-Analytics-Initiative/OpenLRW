/**
 * 
 */
package unicon.matthews.tenant.endpoint;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import unicon.matthews.tenant.Tenant;
import unicon.matthews.tenant.service.TenantService;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ggilbert
 *
 */
public class TenantControllerTest {

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  private MockMvc mockMvc;

  @Mock
  private TenantService tenantService;

  @InjectMocks
  private TenantController controller;

  Tenant tenant1 = new Tenant.Builder().withId("1").withName("tenant 1").withDescription("tenant1 desc").build();

  Tenant tenant2 = new Tenant.Builder().withId("2").withName("tenant 2").withDescription("tenant2 desc").build();

  Tenant tenant3 = new Tenant.Builder().withName("tenant 3").withDescription("tenant3 desc").build();

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    when(tenantService.findAll()).thenReturn(Arrays.asList(tenant1, tenant2));
    when(tenantService.findById("1")).thenReturn(Optional.of(tenant1));
    when(tenantService.findById("2")).thenReturn(Optional.of(tenant2));
    when(tenantService.save(tenant3)).thenReturn(tenant3);

  }

  @Test
  public void readSingleTenant() throws Exception {

    mockMvc.perform(get("/api/tenants/1")).andExpect(status().isOk()).andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.id", is("1"))).andExpect(jsonPath("$.description", is("tenant1 desc")));
  }

  @Test
  public void readTenants() throws Exception {

    mockMvc.perform(get("/api/tenants")).andExpect(status().isOk()).andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  public void createTenant() throws Exception {

    String tenantJson = json(tenant3);
    this.mockMvc.perform(post("/api/tenants").contentType(contentType).content(tenantJson)).andExpect(status().isCreated());
  }

  protected String json(Object o) throws IOException {
    try {
      return new ObjectMapper().writeValueAsString(o);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}