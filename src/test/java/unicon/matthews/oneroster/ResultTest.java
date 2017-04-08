package unicon.matthews.oneroster;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author stalele
 *
 */
public class ResultTest {
  @Test
  public void whenMinimallyPopulatedAllGood() throws JsonProcessingException {
    Map<String, String> testMetadata = java.util.Collections.singletonMap("meta", "data");

    Result result
    = new Result.Builder()
    .withSourcedId("result-sid")
    .withMetadata(testMetadata)
    .withStatus(Status.active)
    .withDateLastModified(LocalDateTime.now())
    .withComment("good job")
    .withLineitem(new Link.Builder().withSourcedId("122").withType("assessment").build())
    .withResultstatus("passed")
    .withScore(90.0)
    .withStudent(new Link.Builder().withSourcedId("user-111").withType("student").build())
    .build();


    ObjectMapper mapper = new ObjectMapper();
    String mapperResult = mapper.writeValueAsString(result);
    assertThat(mapperResult, containsString("passed"));
    assertThat(mapperResult, containsString("result-sid"));
    assertThat(mapperResult, containsString("90.0"));
    assertThat(mapperResult, containsString("meta"));
    assertThat(mapperResult, containsString("data"));
  }
}
