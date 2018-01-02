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
public class LineItemTest {

  @Test
  public void whenMinimallyPopulatedAllGood() throws JsonProcessingException {
    Map<String, String> testMetadata = java.util.Collections.singletonMap("meta", "data");
    
    Class klass
      = new Class.Builder()
        .withSourcedId("c-sid")
        .build();

    LineItem li
      = new LineItem.Builder()
        .withSourcedId("li-sid")
        .withMetadata(testMetadata)
        .withClass(klass)
        .build();
    
    ObjectMapper mapper = new ObjectMapper();
    String result = mapper.writeValueAsString(li);
    assertThat(result, containsString("li-sid"));
    assertThat(result, containsString("c-sid"));
    assertThat(result, containsString("meta"));
    assertThat(result, containsString("data"));
  }

}
