package org.apereo.openlrw.event.caliper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apereo.openlrw.OpenLRW;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.apereo.openlrw.caliper.Entity;

import java.time.Instant;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={OpenLRW.class})
@WebAppConfiguration
public class EntityTest {
  
  @Autowired private ObjectMapper mapper;
  
  @Test(expected=IllegalStateException.class)
  public void whenMissingDataThrowsException() {
    new Entity.Builder().build();
  }
  
  @Test
  public void whenMinimallyPopulatedJsonContainsEverything() throws JsonProcessingException {
    
    Entity entity = new Entity.Builder().withId("id1").withType("type1").build();
    
    String result = mapper.writeValueAsString(entity);
    assertThat(result, containsString("@id"));
    assertThat(result, containsString("@type"));
    assertThat(result, containsString("type1"));
    assertThat(result, containsString("id1"));
  }
  
  @Test
  public void whenFullyPopulatedJsonContainsEverything() throws JsonProcessingException {
    
    Entity entity
    = new Entity.Builder()
      .withId("id1")
      .withType("type1")
      .withName("name1")
      .withDescription("description1")
      .withExtensions(Collections.singletonMap("foo", "bar"))
      .withDateCreated(Instant.now())
      .withDateModified(Instant.now())
      .build();
    
    String result = mapper.writeValueAsString(entity);
    assertThat(result, containsString("@id"));
    assertThat(result, containsString("@type"));
    assertThat(result, containsString("type1"));
    assertThat(result, containsString("id1"));
    assertThat(result, containsString("name1"));
    assertThat(result, containsString("description1"));
    assertThat(result, containsString("foo"));
    assertThat(result, containsString("bar"));
  }


}
