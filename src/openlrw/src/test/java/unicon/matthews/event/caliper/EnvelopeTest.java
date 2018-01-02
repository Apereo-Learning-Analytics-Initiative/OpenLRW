/**
 * 
 */
package unicon.matthews.event.caliper;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import unicon.matthews.Matthews;
import unicon.matthews.caliper.Agent;
import unicon.matthews.caliper.Entity;
import unicon.matthews.caliper.Envelope;
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
public class EnvelopeTest {
  
  @Autowired private ObjectMapper mapper;

  @Test(expected=IllegalStateException.class)
  public void whenMissingDataThrowsException() {
    new Envelope.Builder()
        .withSensor("sensor1")
        .withSendTime(LocalDateTime.now())
        .build();
  }
  
  @Test
  public void whenFullyPopulatedJsonContainsEverything() throws JsonProcessingException {
    
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
    
    Envelope envelope
    = new Envelope.Builder()
      .withSensor("sensor1")
      .withSendTime(LocalDateTime.now())
      .withData(Collections.singletonList(basicEvent))
      .build();

    
    String result = mapper.writeValueAsString(envelope);
    assertThat(result, containsString("action1"));
    assertThat(result, containsString("context1"));
    assertThat(result, containsString("type1"));
    assertThat(result, containsString("sensor1"));
  }

}
