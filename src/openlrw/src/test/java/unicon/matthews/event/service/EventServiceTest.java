/**
 * 
 */
package unicon.matthews.event.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.UUID;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import unicon.matthews.FongoConfig;
import unicon.matthews.Matthews;
import unicon.matthews.caliper.ClassEventStatistics;
import unicon.matthews.caliper.Envelope;
import unicon.matthews.caliper.Event;
import unicon.matthews.caliper.service.EventService;
import unicon.matthews.event.caliper.requests.MediaEventTest;
import unicon.matthews.event.caliper.requests.MinimalEventTest;
import unicon.matthews.oneroster.Org;
import unicon.matthews.oneroster.service.repository.MongoOrg;
import unicon.matthews.oneroster.service.repository.MongoOrgRepository;
import unicon.matthews.tenant.Tenant;
import unicon.matthews.tenant.service.repository.TenantRepository;

/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class,FongoConfig.class})
@WebAppConfiguration
public class EventServiceTest {

  @Autowired
  private EventService eventService;
  
  @Autowired
  private TenantRepository tenantRepository;
  
  @Autowired
  private MongoOrgRepository mongoOrgRepository;
  
  private Event mediaEvent;
  private Event event;
  private Tenant savedTenant;
  private MongoOrg mongoOrg;
 
  @Before
  public void init() throws JsonParseException, JsonMappingException, UnsupportedEncodingException, IOException {
    
    if (savedTenant == null) {
      Tenant tenant 
        = new Tenant.Builder()
          .withId("test-tid")
          .withName("test")
          .build();
      
      savedTenant = tenantRepository.save(tenant);
    }
    
    if (mongoOrg == null) {
      Org org
      = new Org.Builder()
          .withSourcedId("org-id")
          .withName("org")
          .build();
      
      mongoOrg
        = new MongoOrg.Builder()
            .withOrg(org)
            .withTenantId(savedTenant.getId())
            .withApiKey(UUID.randomUUID().toString())
            .withApiSecret(UUID.randomUUID().toString())
            .build();
      
      mongoOrgRepository.save(mongoOrg);
    }
    
    if (event == null || mediaEvent == null) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.findAndRegisterModules();
      mapper.setDateFormat(new ISO8601DateFormat());

      Envelope envelope = mapper.readValue(MediaEventTest.MEDIA_EVENT.getBytes("UTF-8"), Envelope.class);
      mediaEvent = envelope.getData().get(0);
      
      Envelope envelope1 = mapper.readValue(MinimalEventTest.MINIMAL_VIEWED_EVENT.getBytes("UTF-8"), Envelope.class);
      event = envelope1.getData().get(0);
    }
    
  }
  
  @Test
  public void testSave() {
    String id = eventService.save(savedTenant.getId(), mongoOrg.getOrg().getSourcedId(), mediaEvent);
    String id2 = eventService.save(savedTenant.getId(), mongoOrg.getOrg().getSourcedId(), event);
    
    assertThat(id, is(notNullValue()));
    assertThat(id2, is(notNullValue()));
  }
  
  @Test
  public void testGetEventsForClassAndUser() {
    Collection<Event> events = eventService.getEventsForClassAndUser(savedTenant.getId(), mongoOrg.getOrg().getSourcedId(), "001", "554433");
    assertThat(events, is(notNullValue()));
    assertTrue(events.size() > 0);
  }
  
//  @Test(expected=RuntimeException.class)
//  public void testGetEventStatisticsForClass() {
//    ClassEventStatistics ces = eventService.getEventStatisticsForClass(savedTenant.getId(), mongoOrg.getOrg().getSourcedId(), "001");
//    
//    assertThat(ces, is(notNullValue()));
//    assertTrue(ces.getClassSourcedId().equals("001"));
//    assertTrue(ces.getTotalEvents() >= 1);
//    assertTrue(ces.getTotalStudentEnrollments() == 1);
//  }
}
