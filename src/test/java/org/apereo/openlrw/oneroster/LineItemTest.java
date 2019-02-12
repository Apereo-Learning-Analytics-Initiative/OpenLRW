package org.apereo.openlrw.oneroster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apereo.model.oneroster.LineItem;
import org.apereo.model.oneroster.Link;
import org.junit.Test;

import java.util.Map;
import org.apereo.model.oneroster.Class;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
public class LineItemTest {

  @Test
  public void whenMinimallyPopulatedAllGood() throws JsonProcessingException {
    Map<String, String> testMetadata = java.util.Collections.singletonMap("meta", "data");
    
    Class klass = new Class.Builder()
        .withSourcedId("c-sid")
        .build();

    Link classLink = new Link.Builder().withType("Class").withSourcedId("c-sid").build();

    LineItem li
      = new LineItem.Builder()
        .withSourcedId("li-sid")
        .withMetadata(testMetadata)
        .withClass(classLink)
        .build();
    
    ObjectMapper mapper = new ObjectMapper();
    String result = mapper.writeValueAsString(li);
    assertThat(result, containsString("li-sid"));
    assertThat(result, containsString("c-sid"));
    assertThat(result, containsString("meta"));
    assertThat(result, containsString("data"));
  }

}
