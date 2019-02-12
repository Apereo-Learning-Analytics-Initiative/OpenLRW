package org.apereo.openlrw.oneroster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apereo.model.oneroster.Status;
import org.junit.Test;
import org.apereo.model.oneroster.Class;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author ggilbert
 *
 */
public class ClassTest {
  @Test
  public void whenMinimallyPopulatedAllGood() throws JsonProcessingException {
    Map<String, String> testMetadata = java.util.Collections.singletonMap("meta", "data");
    
    Class klass
      = new Class.Builder()
        .withSourcedId("c-sid")
        .withMetadata(testMetadata)
        .withTitle("some class")
        .withStatus(Status.active)
        .build();

    
    ObjectMapper mapper = new ObjectMapper();
    String result = mapper.writeValueAsString(klass);
    assertThat(result, containsString("some class"));
    assertThat(result, containsString("c-sid"));
    assertThat(result, containsString("meta"));
    assertThat(result, containsString("data"));
  }

}
