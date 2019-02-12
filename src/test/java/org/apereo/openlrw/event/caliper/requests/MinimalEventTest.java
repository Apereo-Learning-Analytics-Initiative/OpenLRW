package org.apereo.openlrw.event.caliper.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apereo.openlrw.OpenLRW;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.apereo.openlrw.caliper.Envelope;
import org.apereo.openlrw.caliper.Event;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={OpenLRW.class})
@WebAppConfiguration
public class MinimalEventTest {
  
  @Autowired private ObjectMapper mapper;
  
  public static String MINIMAL_VIEWED_EVENT =
    "{"+
      "\"sensor\": \"https://example.edu/sensor/001\","+
      "\"sendTime\": \"2015-09-15T11:05:01.000Z\","+
      "\"data\": ["+
      "{"+
      "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
      "\"@type\": \"http://purl.imsglobal.org/caliper/v1/Event\","+
      "\"actor\": {"+
      "\"@id\": \"https://example.edu/user/554433\","+
      "\"@type\": \"http://purl.imsglobal.org/caliper/v1/lis/Person\""+
      "},"+
      "\"action\": \"http://purl.imsglobal.org/vocab/caliper/v1/action#Viewed\","+
      "\"eventTime\": \"2015-09-15T10:15:00.000Z\","+
      "\"object\": {"+
      "\"@id\": \"https://example.com/viewer/book/34843#epubcfi(/4/3)\","+
      "\"@type\": \"http://www.idpf.org/epub/vocab/structure/#volume\""+
      "}}]}";
  
  @Test
  public void whenSerializeAllGood() throws Exception {
    Envelope envelope = mapper.readValue(MINIMAL_VIEWED_EVENT.getBytes("UTF-8"), Envelope.class);
    Event event = envelope.getData().get(0);

    assertNotNull(envelope);
    assertEquals("https://example.edu/sensor/001", envelope.getSensor());
    assertEquals(1, envelope.getData().size());
    assertEquals("http://purl.imsglobal.org/caliper/v1/Event", event.getType());
    assertEquals("https://example.edu/user/554433", event.getAgent().getId());
    assertEquals("http://purl.imsglobal.org/caliper/v1/lis/Person", event.getAgent().getType());
    assertEquals("http://purl.imsglobal.org/vocab/caliper/v1/action#Viewed", event.getAction());
    assertEquals("https://example.com/viewer/book/34843#epubcfi(/4/3)", event.getObject().getId());
    assertEquals("http://www.idpf.org/epub/vocab/structure/#volume", event.getObject().getType());
  }
  
  @Test
  public void withDeserializeAllGood() throws Exception {
    Envelope envelope = mapper.readValue(MINIMAL_VIEWED_EVENT.getBytes("UTF-8"), Envelope.class);

    String json = mapper.writeValueAsString(envelope);

    assertNotNull(json);
    assertTrue(StringUtils.isNotBlank(json));
    
    assertThat(json, containsString("http://purl.imsglobal.org/caliper/v1/Event"));
    assertThat(json, containsString("https://example.edu/user/554433"));
    assertThat(json, containsString("http://purl.imsglobal.org/caliper/v1/lis/Person"));
    assertThat(json, containsString("http://purl.imsglobal.org/vocab/caliper/v1/action#Viewed"));
    assertThat(json, containsString("https://example.com/viewer/book/34843#epubcfi(/4/3)"));
    assertThat(json, containsString("http://www.idpf.org/epub/vocab/structure/#volume"));

  }
  
}
