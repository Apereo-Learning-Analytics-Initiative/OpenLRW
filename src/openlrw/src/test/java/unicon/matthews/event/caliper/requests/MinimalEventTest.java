/**
 * 
 */
package unicon.matthews.event.caliper.requests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import unicon.matthews.Matthews;
import unicon.matthews.caliper.Envelope;
import unicon.matthews.caliper.Event;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class})
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
    
    assertTrue(envelope != null);
    assertTrue(envelope.getSensor().equals("https://example.edu/sensor/001"));
    assertTrue(envelope.getData().size() == 1);
    assertTrue(event.getType().equals("http://purl.imsglobal.org/caliper/v1/Event"));
    assertTrue(event.getAgent().getId().equals("https://example.edu/user/554433"));
    assertTrue(event.getAgent().getType().equals("http://purl.imsglobal.org/caliper/v1/lis/Person"));
    assertTrue(event.getAction().equals("http://purl.imsglobal.org/vocab/caliper/v1/action#Viewed"));
    assertTrue(event.getObject().getId().equals("https://example.com/viewer/book/34843#epubcfi(/4/3)"));
    assertTrue(event.getObject().getType().equals("http://www.idpf.org/epub/vocab/structure/#volume"));
  }
  
  @Test
  public void withDeserializeAllGood() throws Exception {
    Envelope envelope = mapper.readValue(MINIMAL_VIEWED_EVENT.getBytes("UTF-8"), Envelope.class);

    String json = mapper.writeValueAsString(envelope);
    
    assertTrue(json != null);
    assertTrue(StringUtils.isNotBlank(json));
    
    assertThat(json, containsString("http://purl.imsglobal.org/caliper/v1/Event"));
    assertThat(json, containsString("https://example.edu/user/554433"));
    assertThat(json, containsString("http://purl.imsglobal.org/caliper/v1/lis/Person"));
    assertThat(json, containsString("http://purl.imsglobal.org/vocab/caliper/v1/action#Viewed"));
    assertThat(json, containsString("https://example.com/viewer/book/34843#epubcfi(/4/3)"));
    assertThat(json, containsString("http://www.idpf.org/epub/vocab/structure/#volume"));

  }
  
}
