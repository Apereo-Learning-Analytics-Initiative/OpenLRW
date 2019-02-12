package org.apereo.openlrw.oneroster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apereo.model.oneroster.*;
import org.apereo.model.oneroster.Class;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
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

    Link classLink = new Link.Builder()
            .withType("Class")
            .withSourcedId("c-sid")
            .build();

    User user
      = new User.Builder()
        .withSourcedId("u-sid")
        .withMetadata(utestMetadata)
        .build();

    Link userLink = new Link.Builder()
            .withType("User")
            .withSourcedId("u-sid")
            .build();

    Enrollment enrollment
      = new Enrollment.Builder()
        .withKlass(classLink)
        .withMetadata(etestMetadata)
        .withPrimary(false)
        .withRole(Role.student)
        .withStatus(Status.active)
        .withSourcedId("e-sid")
        .withUser(userLink)
        .build();
    
    ObjectMapper mapper = new ObjectMapper();
    String result = mapper.writeValueAsString(enrollment);
    assertThat(result, containsString("c-sid"));
    assertThat(result, containsString("active"));
    assertThat(result, containsString("u-sid"));
    assertThat(result, containsString("e-sid"));
    assertThat(result, containsString("student"));

  }

}
