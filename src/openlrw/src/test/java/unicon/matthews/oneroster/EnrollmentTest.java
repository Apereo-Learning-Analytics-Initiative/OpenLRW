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
public class EnrollmentTest {
  @Test
  public void whenMinimallyPopulatedAllGood() throws JsonProcessingException {
    Map<String, String> etestMetadata = java.util.Collections.singletonMap("meta", "e-data");
    Map<String, String> ctestMetadata = java.util.Collections.singletonMap("meta", "c-data");
    Map<String, String> utestMetadata = java.util.Collections.singletonMap("meta", "u-data");
    
    Class klass
      = new Class.Builder()
        .withSourcedId("c-sid")
        .withMetadata(ctestMetadata)
        .withTitle("some class")
        .withStatus(Status.active)
        .build();

    User user
      = new User.Builder()
        .withSourcedId("u-sid")
        .withMetadata(utestMetadata)
        .build();

    Enrollment enrollment
      = new Enrollment.Builder()
        .withKlass(klass)
        .withMetadata(etestMetadata)
        .withPrimary(false)
        .withRole(Role.student)
        .withStatus(Status.active)
        .withSourcedId("e-sid")
        .withUser(user)
        .build();
    
    ObjectMapper mapper = new ObjectMapper();
    String result = mapper.writeValueAsString(enrollment);
    assertThat(result, containsString("some class"));
    assertThat(result, containsString("c-sid"));
    assertThat(result, containsString("c-data"));
    assertThat(result, containsString("active"));
    assertThat(result, containsString("u-sid"));
    assertThat(result, containsString("u-data"));
    assertThat(result, containsString("e-sid"));
    assertThat(result, containsString("e-data"));
    assertThat(result, containsString("student"));

  }

}
