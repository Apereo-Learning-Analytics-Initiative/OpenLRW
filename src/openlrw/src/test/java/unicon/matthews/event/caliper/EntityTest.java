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
import unicon.matthews.caliper.Entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class})
@WebAppConfiguration
public class EntityTest {
  
  @Autowired private ObjectMapper mapper;
  
  @Test(expected=IllegalStateException.class)
  public void whenMissingDataThrowsException() {
    new Entity.Builder()
        .build();
  }
  
  @Test
  public void whenMinimallyPopulatedJsonContainsEverything() throws JsonProcessingException {
    
    Entity entity
    = new Entity.Builder()
      .withId("id1")
      .withType("type1")
      .build();
    
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
      .withDateCreated(LocalDateTime.now())
      .withDateModified(LocalDateTime.now())
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
