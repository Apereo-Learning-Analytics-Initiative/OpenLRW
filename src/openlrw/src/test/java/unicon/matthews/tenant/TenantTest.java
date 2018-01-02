/**
 * 
 */
package unicon.matthews.tenant;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TenantTest {

  @Test
  public void whenFullyPopulatedJsonContainsEverything() throws JsonProcessingException {
    
    String testUUID = UUID.randomUUID().toString();
    Map<String, String> testMetadata = java.util.Collections.singletonMap("meta", "data");
    String name = "tname";
    String desc = "tdesc";
    
    Tenant tenant
      = new Tenant.Builder()
        .withId(testUUID)
        .withName(name)
        .withDescription(desc)
        .withMetadata(testMetadata)
        .build();
    
    
    ObjectMapper mapper = new ObjectMapper();
    String result = mapper.writeValueAsString(tenant);
    assertThat(result, containsString(testUUID));
    assertThat(result, containsString("meta"));
    assertThat(result, containsString("data"));
    assertThat(result, containsString(name));
    assertThat(result, containsString(desc));
  }

}
