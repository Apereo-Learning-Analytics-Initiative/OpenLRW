/**
 * 
 */
package unicon.matthews.oneroster;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ggilbert
 *
 */
public class UserTest {

  @Test
  public void whenMinimallyPopulatedAllGood() throws JsonProcessingException {
    Map<String, String> testMetadata = java.util.Collections.singletonMap("meta", "data");

    User user
      = new User.Builder()
        .withSourcedId("foo-sid")
        .withMetadata(testMetadata)
        .build();
    
    ObjectMapper mapper = new ObjectMapper();
    String result = mapper.writeValueAsString(user);
    assertThat(result, containsString("foo-sid"));
    assertThat(result, containsString("meta"));
    assertThat(result, containsString("data"));
  }
}
