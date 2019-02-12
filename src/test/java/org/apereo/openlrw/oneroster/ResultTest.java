package org.apereo.openlrw.oneroster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apereo.model.oneroster.Link;
import org.apereo.model.oneroster.Result;
import org.apereo.model.oneroster.Status;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author stalele
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
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
    .withDateLastModified(Instant.now())
    .withComment("that's awesome let me hire you at Unicon")
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
