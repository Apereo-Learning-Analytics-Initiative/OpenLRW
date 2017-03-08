/**
 * 
 */
package unicon.matthews.event.caliper;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import unicon.matthews.Matthews;
import unicon.matthews.caliper.Agent;
import unicon.matthews.caliper.Entity;
import unicon.matthews.caliper.Event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class})
@WebAppConfiguration
public class EventTest {
  
  @Autowired private ObjectMapper mapper;
  
  @Test
  public void whenMinimallyPopulatedJsonContainsEverything() throws JsonProcessingException {
    
    LocalDateTime lct = LocalDateTime.now();
    
    Agent agent
    = new Agent.Builder()
      .withId("agent_id1")
      .withType("agent_type1")
      .build();
    
    Entity entity
    = new Entity.Builder()
      .withId("entity_id1")
      .withType("entity_type1")
      .build();
    
    Event basicEvent
      = new Event.Builder()
        .withAction("action1")
        .withContext("context1")
        .withType("type1")
        .withEventTime(lct)
        .withAgent(agent)
        .withObject(entity)
        .build();
    
    String result = mapper.writeValueAsString(basicEvent);
    assertThat(result, containsString("action1"));
    assertThat(result, containsString("@context"));
    assertThat(result, containsString("context1"));
    assertThat(result, containsString("type1"));
    assertThat(result, containsString("@id"));
    assertThat(result, containsString("@type"));
    assertThat(result, containsString("type1"));
    assertThat(result, containsString("id1"));
    assertThat(result, containsString("actor"));
    assertThat(result, containsString("agent_id1"));
    assertThat(result, containsString("agent_type1"));
    assertThat(result, containsString("object"));
    assertThat(result, containsString("entity_id1"));
    assertThat(result, containsString("entity_type1"));
  }

}
