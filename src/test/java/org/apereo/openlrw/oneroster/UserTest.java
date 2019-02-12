package org.apereo.openlrw.oneroster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apereo.model.oneroster.User;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author ggilbert
 *
 */
public class UserTest {

  @Test
  public void whenMinimallyPopulatedAllGood() throws JsonProcessingException {
    Map<String, String> testMetadata = java.util.Collections.singletonMap("meta", "data");

    User user = new User.Builder()
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
