/**
 * 
 */
package unicon.matthews.oneroster;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ggilbert
 *
 */
public class OrgTest {
  @Test
  public void whenFullyPopulatedJsonContainsEverything() throws JsonProcessingException {
    
    String testUUID = UUID.randomUUID().toString();
    Map<String, String> testMetadata = java.util.Collections.singletonMap("meta", "data");
    String name = "orgname";
    
    Org org
      = new Org.Builder()
        .withName(name)
        .withSourcedId(testUUID)
        .withMetadata(testMetadata)
        .build();
    
    
    ObjectMapper mapper = new ObjectMapper();
    String result = mapper.writeValueAsString(org);
    assertThat(result, containsString(testUUID));
    assertThat(result, containsString("meta"));
    assertThat(result, containsString("data"));
    assertThat(result, containsString(name));
  }

}
