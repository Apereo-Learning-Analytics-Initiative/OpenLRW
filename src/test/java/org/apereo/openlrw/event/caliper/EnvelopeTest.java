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
import org.apereo.openlrw.caliper.Agent;
import org.apereo.openlrw.caliper.Entity;
import org.apereo.openlrw.caliper.Envelope;
import org.apereo.openlrw.caliper.Event;

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
public class EnvelopeTest {
  
  @Autowired private ObjectMapper mapper;

  @Test(expected=IllegalStateException.class)
  public void whenMissingDataThrowsException() {
    new Envelope.Builder().withSensor("sensor1").withSendTime(Instant.now()).build();
  }
  
  @Test
  public void whenFullyPopulatedJsonContainsEverything() throws JsonProcessingException {
    
    Instant instant = Instant.now();
    
    Agent agent = new Agent.Builder()
      .withId("agent_id1")
      .withType("agent_type1")
      .build();
    
    Entity entity = new Entity.Builder()
      .withId("entity_id1")
      .withType("entity_type1")
      .build();
    
    Event basicEvent = new Event.Builder()
        .withAction("action1")
        .withContext("context1")
        .withType("type1")
        .withEventTime(instant)
        .withAgent(agent)
        .withObject(entity)
        .build();
    
    Envelope envelope = new Envelope.Builder()
      .withSensor("sensor1")
      .withSendTime(Instant.now())
      .withData(Collections.singletonList(basicEvent))
      .build();

    
    String result = mapper.writeValueAsString(envelope);
    assertThat(result, containsString("action1"));
    assertThat(result, containsString("context1"));
    assertThat(result, containsString("type1"));
    assertThat(result, containsString("sensor1"));
  }

}
