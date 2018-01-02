/**
 * 
 */
package unicon.matthews.xapi.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.lang3.StringUtils;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.context.Context;
import org.imsglobal.caliper.entities.DigitalResourceType;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.Type;
import org.imsglobal.caliper.events.EventType;
import org.springframework.stereotype.Component;

import unicon.matthews.caliper.Agent;
import unicon.matthews.caliper.Entity;
import unicon.matthews.caliper.Event;
import unicon.matthews.caliper.Group;
import unicon.matthews.caliper.SubOrganizationOf;
import unicon.matthews.xapi.Statement;
import unicon.matthews.xapi.XApiAccount;
import unicon.matthews.xapi.XApiActor;
import unicon.matthews.xapi.XApiContext;
import unicon.matthews.xapi.XApiContextActivities;
import unicon.matthews.xapi.XApiObject;
import unicon.matthews.xapi.XApiObjectDefinition;
import unicon.matthews.xapi.XApiResult;
import unicon.matthews.xapi.XApiScore;
import unicon.matthews.xapi.XApiVerb;

/**
 * @author ggilbert
 *
 */
@Component
public class DefaultXapiToCaliperConversionService implements XapiConversionService {
  
  private BidiMap<Action, String> verbActionMap;
  private BidiMap<Type, String> objectEntityMap;
  private Map<Action, EventType> actionEventMap;
  
  @PostConstruct
  private void init() {
    verbActionMap = new DualHashBidiMap<Action, String>();
    verbActionMap.put(Action.ABANDONED, "https://w3id.org/xapi/adl/verbs/abandoned");
    //verbActionMap.put(Action.ACTIVATED, );
    verbActionMap.put(Action.ATTACHED, "http://activitystrea.ms/schema/1.0/attach");    
    verbActionMap.put(Action.BOOKMARKED, "http://id.tincanapi.com/verb/bookmarked");
    //verbActionMap.put(Action.CHANGED_RESOLUTION, );
    //verbActionMap.put(Action.CHANGED_SIZE, );
    //verbActionMap.put(Action.CHANGED_VOLUME, );
    //verbActionMap.put(Action.CLASSIFIED, );
    //verbActionMap.put(Action.CLOSED_POPOUT, );
    verbActionMap.put(Action.COMMENTED, "http://adlnet.gov/expapi/verbs/commented");
    verbActionMap.put(Action.COMPLETED, "http://adlnet.gov/expapi/verbs/completed");
    //verbActionMap.put(Action.DEACTIVATED, );
    //verbActionMap.put(Action.DESCRIBED, );
    verbActionMap.put(Action.DISLIKED, "http://activitystrea.ms/schema/1.0/dislike");
    //verbActionMap.put(Action.DISABLED_CLOSED_CAPTIONING, );
    //verbActionMap.put(Action.ENABLED_CLOSED_CAPTIONING, );
    //verbActionMap.put(Action.ENDED, );
    //verbActionMap.put(Action.ENTERED_FULLSCREEN, );
    //verbActionMap.put(Action.EXITED_FULLSCREEN, );
    //verbActionMap.put(Action.FORWARDED_TO, );
    verbActionMap.put(Action.GRADED, "http://adlnet.gov/expapi/verbs/scored");
    //verbActionMap.put(Action.HID, );
    //verbActionMap.put(Action.HIGHLIGHTED, );
    //verbActionMap.put(Action.JUMPED_TO, );
    //verbActionMap.put(Action.IDENTIFIED, );
    verbActionMap.put(Action.LIKED, "http://activitystrea.ms/schema/1.0/like");
    //verbActionMap.put(Action.LINKED, );
    
    // alternatives
    // https://w3id.org/xapi/adl/verbs/logged-in
    // https://w3id.org/xapi/adl/verbs/logged-out
    
    verbActionMap.put(Action.LOGGED_IN, "https://brindlewaye.com/xAPITerms/verbs/loggedin/");
    verbActionMap.put(Action.LOGGED_OUT, "https://brindlewaye.com/xAPITerms/verbs/loggedout/");
    
    //verbActionMap.put(Action.MUTED, );
    //verbActionMap.put(Action.NAVIGATED_TO, );
    //verbActionMap.put(Action.OPENED_POPOUT, );
    verbActionMap.put(Action.PAUSED, "http://id.tincanapi.com/verb/paused");
    //verbActionMap.put(Action.RANKED, );
    verbActionMap.put(Action.QUESTIONED, "http://adlnet.gov/expapi/verbs/asked");
    //verbActionMap.put(Action.RECOMMENDED, );
    verbActionMap.put(Action.REPLIED, "http://adlnet.gov/expapi/verbs/responded");
    //verbActionMap.put(Action.RESTARTED, );
    verbActionMap.put(Action.RESUMED, "http://adlnet.gov/expapi/verbs/resumed");
    verbActionMap.put(Action.REVIEWED, "http://id.tincanapi.com/verb/reviewed");
    //verbActionMap.put(Action.REWOUND, );
    verbActionMap.put(Action.SEARCHED, "http://activitystrea.ms/schema/1.0/search");
    verbActionMap.put(Action.SHARED, "http://activitystrea.ms/schema/1.0/share");
    //verbActionMap.put(Action.SHOWED, );
    verbActionMap.put(Action.SKIPPED, "http://id.tincanapi.com/verb/skipped");
    verbActionMap.put(Action.STARTED, "http://activitystrea.ms/schema/1.0/start");
    verbActionMap.put(Action.SUBMITTED, "http://activitystrea.ms/schema/1.0/submit");
    //verbActionMap.put(Action.SUBSCRIBED, );
    verbActionMap.put(Action.TAGGED, "http://activitystrea.ms/schema/1.0/tag");
    //verbActionMap.put(Action.TIMED_OUT, );
    verbActionMap.put(Action.VIEWED, "http://id.tincanapi.com/verb/viewed");
    //verbActionMap.put(Action.UNMUTED, );
    
    objectEntityMap = new DualHashBidiMap<Type, String>();
    // TODO support other xapi annotation types
    objectEntityMap.put(EntityType.ANNOTATION, "http://risc-inc.com/annotator/activities/highlight");
    //objectEntityMap.put(EntityType.ATTEMPT, arg1);
    //objectEntityMap.put(EntityType.COURSE_OFFERING, arg1);
    objectEntityMap.put(EntityType.COURSE_SECTION, "http://adlnet.gov/expapi/activities/course");
    objectEntityMap.put(EntityType.DIGITAL_RESOURCE, "http://adlnet.gov/expapi/activities/media");
    //objectEntityMap.put(EntityType.ENTITY, );
    objectEntityMap.put(EntityType.GROUP, "http://activitystrea.ms/schema/1.0/group");
    objectEntityMap.put(EntityType.LEARNING_OBJECTIVE, "http://adlnet.gov/expapi/activities/objective");
    //objectEntityMap.put(EntityType.MEMBERSHIP, arg1);
    objectEntityMap.put(EntityType.PERSON, "http://activitystrea.ms/schema/1.0/person");
    objectEntityMap.put(EntityType.ORGANIZATION, "http://activitystrea.ms/schema/1.0/organization");
    //objectEntityMap.put(EntityType.RESPONSE, arg1);
    //objectEntityMap.put(EntityType.RESULT, arg1);
    //objectEntityMap.put(EntityType.SESSION, arg1);
    objectEntityMap.put(EntityType.SOFTWARE_APPLICATION, "http://activitystrea.ms/schema/1.0/application");
    //objectEntityMap.put(EntityType.VIEW, arg1);
    //objectEntityMap.put(DigitalResourceType.MEDIA_LOCATION, arg1);
    objectEntityMap.put(DigitalResourceType.MEDIA_OBJECT, "http://adlnet.gov/expapi/activities/media");
    //objectEntityMap.put(DigitalResourceType.READING, arg1);
    objectEntityMap.put(DigitalResourceType.WEB_PAGE, "http://activitystrea.ms/schema/1.0/page");
    
    actionEventMap = new HashMap<Action,EventType>();
    actionEventMap.put(Action.ABANDONED, EventType.ASSIGNABLE);
    actionEventMap.put(Action.ACTIVATED, EventType.ASSIGNABLE);
    actionEventMap.put(Action.ATTACHED, EventType.ANNOTATION);    
    actionEventMap.put(Action.BOOKMARKED, EventType.ANNOTATION);
    actionEventMap.put(Action.CHANGED_RESOLUTION, EventType.MEDIA);
    actionEventMap.put(Action.CHANGED_SIZE, EventType.MEDIA);
    actionEventMap.put(Action.CHANGED_VOLUME, EventType.MEDIA);
    actionEventMap.put(Action.CLASSIFIED, EventType.ANNOTATION);
    actionEventMap.put(Action.CLOSED_POPOUT, EventType.MEDIA);
    actionEventMap.put(Action.COMMENTED, EventType.ANNOTATION);
    actionEventMap.put(Action.COMPLETED, EventType.ASSIGNABLE);
    actionEventMap.put(Action.DEACTIVATED, EventType.ASSIGNABLE);
    actionEventMap.put(Action.DESCRIBED, EventType.ANNOTATION);
    actionEventMap.put(Action.DISLIKED, EventType.ANNOTATION);
    actionEventMap.put(Action.DISABLED_CLOSED_CAPTIONING, EventType.MEDIA);
    actionEventMap.put(Action.ENABLED_CLOSED_CAPTIONING, EventType.MEDIA);
    actionEventMap.put(Action.ENDED, EventType.MEDIA);
    actionEventMap.put(Action.ENTERED_FULLSCREEN, EventType.MEDIA);
    actionEventMap.put(Action.EXITED_FULLSCREEN, EventType.MEDIA);
    actionEventMap.put(Action.FORWARDED_TO, EventType.MEDIA);
    actionEventMap.put(Action.GRADED, EventType.OUTCOME);
    actionEventMap.put(Action.HID, EventType.ASSIGNABLE);
    actionEventMap.put(Action.HIGHLIGHTED, EventType.ANNOTATION);
    actionEventMap.put(Action.JUMPED_TO, EventType.MEDIA);
    actionEventMap.put(Action.IDENTIFIED, EventType.ANNOTATION);
    actionEventMap.put(Action.LIKED, EventType.ANNOTATION);
    actionEventMap.put(Action.LINKED, EventType.ANNOTATION);
    actionEventMap.put(Action.LOGGED_IN, EventType.SESSION);
    actionEventMap.put(Action.LOGGED_OUT, EventType.SESSION);   
    actionEventMap.put(Action.MUTED, EventType.MEDIA);
    actionEventMap.put(Action.NAVIGATED_TO, EventType.NAVIGATION);
    actionEventMap.put(Action.OPENED_POPOUT, EventType.MEDIA);
    actionEventMap.put(Action.PAUSED, EventType.MEDIA);
    actionEventMap.put(Action.RANKED, EventType.ANNOTATION);
    actionEventMap.put(Action.QUESTIONED, EventType.ANNOTATION);
    actionEventMap.put(Action.RECOMMENDED, EventType.ANNOTATION);
    actionEventMap.put(Action.REPLIED, EventType.ANNOTATION);
    actionEventMap.put(Action.RESTARTED, EventType.ASSESSMENT);
    actionEventMap.put(Action.RESUMED, EventType.MEDIA);
    actionEventMap.put(Action.REVIEWED, EventType.ASSIGNABLE);
    actionEventMap.put(Action.REWOUND, EventType.MEDIA);
    actionEventMap.put(Action.SEARCHED, EventType.READING);
    actionEventMap.put(Action.SHARED, EventType.ANNOTATION);
    actionEventMap.put(Action.SHOWED, EventType.ASSIGNABLE);
    actionEventMap.put(Action.SKIPPED, EventType.ASSESSMENT_ITEM);
    actionEventMap.put(Action.STARTED, EventType.EVENT);
    actionEventMap.put(Action.SUBMITTED, EventType.EVENT);
    actionEventMap.put(Action.SUBSCRIBED, EventType.ANNOTATION);
    actionEventMap.put(Action.TAGGED, EventType.ANNOTATION);
    actionEventMap.put(Action.TIMED_OUT, EventType.SESSION);
    actionEventMap.put(Action.VIEWED, EventType.EVENT);
    actionEventMap.put(Action.UNMUTED, EventType.MEDIA);

  }

  public Event fromXapi(Statement statement) {
    // EVENT TIME
    LocalDateTime eventTime = null;    
    String timestamp = statement.getTimestamp(); 
    if (StringUtils.isNotBlank(timestamp)) {
      if (timestamp.endsWith("Z")) {
        Instant instant = Instant.parse(timestamp);
        eventTime = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));
      }
      else {
        ZonedDateTime zdt = ZonedDateTime.parse(timestamp);
        ZonedDateTime utc_zone = zdt.toLocalDateTime().atZone(ZoneOffset.UTC);
        eventTime = utc_zone.toLocalDateTime();
      }
      
    }
    else {
      eventTime = LocalDateTime.now(ZoneId.of(ZoneOffset.UTC.getId()));
    }
    // EVENT TIME END
    
    // ACTOR
    Agent caliperActor = null;
    String actorId = null;
    XApiActor xapiActor = statement.getActor();
    if (xapiActor != null) {
      
      String actorType = null; 
      Map<String, String> actorExtensions = new HashMap<>();
      String actorName = xapiActor.getName();
      
      String openId = xapiActor.getOpenid();
      String mbox = xapiActor.getMbox();
      XApiAccount xapiAccount = xapiActor.getAccount();
      
      if (StringUtils.isNotBlank(openId)) {
        actorId = openId;
        actorType = ACTOR_TYPE_OPENID;
      }
      else if (StringUtils.isNotBlank(mbox)) {
        actorId = mbox;
        actorType = ACTOR_TYPE_MBOX;
      }
      else if (xapiAccount != null) {
        String accountName = xapiAccount.getName();
        String homePage = xapiAccount.getHomePage();
        
        if (StringUtils.isNotBlank(homePage)) {
          
          if (StringUtils.isNotBlank(accountName)) {
            actorId = accountName;
            actorExtensions.put("HOMEPAGE", homePage);
          }
          else {
            actorId = homePage;
          }
        }
        else if (StringUtils.isNotBlank(accountName)) {
          actorId = accountName;
        }
        
        actorType = ACTOR_TYPE_ACCOUNT;
      }
      
      caliperActor 
        = new Agent.Builder()
          .withContext(Context.CONTEXT.getValue())
          .withName(actorName)
          .withId(actorId)
          .withType(actorType)
          .withExtensions(actorExtensions)
          .build();
    }
    // ACTOR END
    
    // RESULT
    Entity caliperResult = null;
    XApiResult xapiResult = statement.getResult();
    if (xapiResult != null) {
      Map<String,String> resultExtensions = null;
      Map<URI,java.lang.Object> xapiResultExtensions = xapiResult.getExtensions();
      if (xapiResultExtensions != null && !xapiResultExtensions.isEmpty()) {
        resultExtensions = new HashMap<>();
        for (Map.Entry<URI,java.lang.Object> entry : xapiResultExtensions.entrySet()) {
          resultExtensions.put(entry.getKey().toString(),entry.getValue().toString());
        }
      }
      
      Double score = null;
      XApiScore xapiScore = xapiResult.getScore();
      if (xapiScore != null) {
        score = xapiScore.getRaw();
      }
      
      caliperResult 
        = new Entity.Builder()
          .withId(UUID.randomUUID().toString())
          .withContext(Context.CONTEXT.getValue())
          .withExtensions(resultExtensions)
          .withActor(actorId)
          .withType("http://purl.imsglobal.org/caliper/v1/Result")
          .withTotalScore(score)
          .build();
    }
    // END Result
    
    // ACTION
    String caliperAction = null;
    XApiVerb xapiVerb = statement.getVerb();
    if (xapiVerb != null) {
      String verbId = xapiVerb.getId();
      caliperAction = xApiVerbToCaliperAction(verbId);
    }
    // ACTION END
    
    // OBJECT
    Entity caliperObject = null;
    XApiObject xapiObject = statement.getObject();
    if (xapiObject != null) {
      
      String objectType = xapiObjectTypeToCaliperEntityType(null);
      String objectName = null;
      String objectDescription = null;
      Map<String,String> objectExtensions = null;
      String objectId = xapiObject.getId();
      
      XApiObjectDefinition xapiObjectDefinition = xapiObject.getDefinition();
      if (xapiObjectDefinition != null) {
        String xapiObjectDefinitionType = xapiObjectDefinition.getType();
        if (StringUtils.isNotBlank(xapiObjectDefinitionType)) {
          objectType = xapiObjectTypeToCaliperEntityType(xapiObjectDefinitionType);
        }
        
        Map<String,String> names = xapiObjectDefinition.getName();
        if (names != null) {
          if (names.size() == 1) {
            objectName = CollectionUtils.get(names, 0).getValue();
          }
          else {
            // default to en?
            objectName = names.get("en");
          }
        }
        
        Map<String,String> descriptions = xapiObjectDefinition.getDescription();
        if (descriptions != null) {
          if (descriptions.size() == 1) {
            objectDescription = CollectionUtils.get(descriptions, 0).getValue();
          }
          else {
            // default to en?
            objectDescription = descriptions.get("en");
          }
        }
        
        Map<URI,java.lang.Object> extensions = xapiObjectDefinition.getExtensions();
        if (extensions != null && !extensions.isEmpty()) {
          objectExtensions = new HashMap<String,String>(extensions.size());
          for (URI key : extensions.keySet()) {
            objectExtensions.put(key.toString(), extensions.get(key).toString());
          }
        }
      }
           
      caliperObject 
        = new Entity.Builder()
          .withId(objectId)
          .withContext(Context.CONTEXT.getValue())
          .withType(objectType)
          .withName(objectName)
          .withDescription(objectDescription)
          .withExtensions(objectExtensions)
          .build();
    }
    // OBJECT END
    
    Group caliperGroup = null;
    XApiContext xapiContext = statement.getContext();
    if (xapiContext != null) {
      Map<String,String> contextExtensions = null;
      Map<URI,java.lang.Object> extensions = xapiContext.getExtensions();
      if (extensions != null && !extensions.isEmpty()) {
        contextExtensions = new HashMap<String,String>(extensions.size());
        for (URI key : extensions.keySet()) {
          contextExtensions.put(key.toString(), extensions.get(key).toString());
        }
      }

      XApiContextActivities xapiContextActivities = xapiContext.getContextActivities();
      if (xapiContextActivities != null) {       
        List<XApiObject> contextActivityParents = xapiContextActivities.getParent();
        
        if (contextActivityParents != null && contextActivityParents.size() == 1
            && contextActivityParents.get(0).getId().contains("portal/site")) {
          caliperGroup 
            = new Group.Builder()
              .withId(StringUtils.substringAfterLast(contextActivityParents.get(0).getId(), "/"))
              .withContext(Context.CONTEXT.getValue())
              .withType("http://purl.imsglobal.org/caliper/v1/lis/CourseSection")
              .build();
        }

        if (caliperGroup == null) {
          List<XApiObject> groupings = xapiContextActivities.getGrouping();
          if (groupings != null && !groupings.isEmpty()) {
            XApiObject grouping = null;
            String id = null;
            String type = null;

            if (groupings.size() == 1) {
              grouping = groupings.get(0);
              
              if (extensions != null) {
                Object paramMap = null;
                try {
                  paramMap = extensions.get(new URI("http://lrs.learninglocker.net/define/extensions/moodle_logstore_standard_log"));
                } 
                catch (URISyntaxException e) {
                  // TODO
                }
                if (paramMap != null && paramMap instanceof Map) {
                  Map<String, String> groupExtMap = (Map<String, String>)paramMap;
                  id = groupExtMap.get("courseid");
                  type = "http://purl.imsglobal.org/caliper/v1/lis/CourseSection";
                }
              }
            }
            else {
              for (XApiObject xo : groupings) {
                grouping = xo;
                XApiObjectDefinition xapiObjectDefinition = xo.getDefinition();
                if (xapiObjectDefinition != null) {
                  if ("http://lrs.learninglocker.net/define/type/moodle/course".equals(xapiObjectDefinition.getType())) {               
                    type = "http://purl.imsglobal.org/caliper/v1/lis/CourseSection";                 
                    Map<URI, Object> groupExt = xapiObjectDefinition.getExtensions();
                    if (groupExt != null) {
                      try {
                        Object paramMap = groupExt.get(new URI("http://lrs.learninglocker.net/define/extensions/moodle_course"));
                        if (paramMap instanceof Map) {
                          Map<String, String> groupExtMap = (Map<String, String>)paramMap;
                          id = groupExtMap.get("id");
                        }
                        
                      } 
                      catch (URISyntaxException e) {
                        //TODO
                      }
                    }
                    
                    break;
                  }
                }
              }
            }
            
            if (grouping != null) {
              String name = null;
              String description = null;
              XApiObjectDefinition xapiObjectDefinition = grouping.getDefinition();
              if (xapiObjectDefinition != null) {
                
                if (StringUtils.isBlank(type) && StringUtils.isNoneBlank(xapiObjectDefinition.getType())) {
                  type = xapiObjectDefinition.getType();
                }
                
                Map<String,String> names =xapiObjectDefinition.getName();
                if (names != null) {
                  if (names.size() == 1) {
                    name = CollectionUtils.get(names, 0).getValue();
                  }
                  else {
                    // default to en?
                    name = names.get("en");
                  }
                }

                Map<String,String> descriptions = xapiObjectDefinition.getDescription();
                if (descriptions != null) {
                  if (descriptions.size() == 1) {
                    description = CollectionUtils.get(descriptions, 0).getValue();
                  }
                  else {
                    // default to en?
                    description = descriptions.get("en");
                  }
                }
              }
              
              List<XApiObject> parents = xapiContextActivities.getParent();
              SubOrganizationOf subOrganizationOf = null;
              if (parents != null && parents.size() == 1) {
                XApiObject parent = parents.get(0);
                String parentId = parent.getId();
                String parentType = "http://purl.imsglobal.org/caliper/v1/lis/CourseOffering";
                String parentName = null;
                String parentDescription = null;
                XApiObjectDefinition parentXapiObjectDefinition = parent.getDefinition();
                if (parentXapiObjectDefinition != null) {
                  
                  if (StringUtils.isNoneBlank(parentXapiObjectDefinition.getType())) {
                    parentType = parentXapiObjectDefinition.getType();
                  }
                  
                  Map<String,String> names = parentXapiObjectDefinition.getName();
                  if (names != null) {
                    if (names.size() == 1) {
                      parentName = CollectionUtils.get(names, 0).getValue();
                    }
                    else {
                      // default to en?
                      parentName = names.get("en");
                    }
                  }

                  Map<String,String> descriptions = parentXapiObjectDefinition.getDescription();
                  if (descriptions != null) {
                    if (descriptions.size() == 1) {
                      parentDescription = CollectionUtils.get(descriptions, 0).getValue();
                    }
                    else {
                      // default to en?
                      parentDescription = descriptions.get("en");
                    }
                  }

                  subOrganizationOf 
                    = new SubOrganizationOf.Builder()
                      .withId(parentId)
                      .withContext(Context.CONTEXT.getValue())
                      .withType(parentType)
                      .withName(parentName)
                      .withDescription(parentDescription)
                      .build();
                }
              }
              
              caliperGroup 
                = new Group.Builder()
                  .withId(id)
                  .withContext(Context.CONTEXT.getValue())
                  .withType(type)
                  .withName(name)
                  .withDescription(description)
                  .withExtensions(contextExtensions)
                  .withSubOrganizationOf(subOrganizationOf)
                  .build();
            }
            else if (xapiContextActivities.getParent() != null) {
              XApiObject parent = xapiContextActivities.getParent().get(0);
              String name = null;
              String description = null;
              XApiObjectDefinition xapiObjectDefinition = parent.getDefinition();
              if (xapiObjectDefinition != null) {
                if (StringUtils.isBlank(type) && StringUtils.isNoneBlank(xapiObjectDefinition.getType())) {
                  type = xapiObjectDefinition.getType();
                }
                
                Map<String,String> names = xapiObjectDefinition.getName();
                if (names != null) {
                  if (names.size() == 1) {
                    name = CollectionUtils.get(names, 0).getValue();
                  }
                  else {
                    // default to en?
                    name = names.get("en");
                  }
                }

                Map<String,String> descriptions = xapiObjectDefinition.getDescription();
                if (descriptions != null) {
                  if (descriptions.size() == 1) {
                    description = CollectionUtils.get(descriptions, 0).getValue();
                  }
                  else {
                    // default to en?
                    description = descriptions.get("en");
                  }
                }
              }

              caliperGroup 
                = new Group.Builder()
                  .withId(parent.getId())
                  .withContext(Context.CONTEXT.getValue())
                  .withType(type)
                  .withName(name)
                  .withDescription(description)
                  .withExtensions(contextExtensions)
                  .build();
            }
          }
        }
      }
    }
    
    return
        new Event.Builder()
        .withAction(caliperAction)
        .withAgent(caliperActor)
        .withObject(caliperObject)
        .withEventTime(eventTime)
        .withContext(xapiToCaliperType(statement))
        .withGroup(caliperGroup)
        .withGenerated(caliperResult)
        .build();
    
  }
  
  public Statement toXapi(Event event) throws URISyntaxException {
    Statement statement = new Statement();
    
    // ID
    statement.setId(event.getId());
    // END ID
    
    // EVENT TIME
    LocalDateTime eventTime = event.getEventTime();    
    if (eventTime != null) {
      Instant instant = eventTime.toInstant(ZoneOffset.UTC);
      //DateTimeFormatter fmt = DateTimeFormatter.ISO_INSTANT;
      statement.setTimestamp(instant.toString());
    }
    // END EVENT TIME
    
    // ACTOR
    Agent actor = event.getAgent();
    if (actor != null) {
      Map<String,String> actorExtensions = actor.getExtensions();
      XApiActor xapiActor = new XApiActor();
      
      String actorId = actor.getId();
      String actorType = actor.getType(); 
      xapiActor.setName(actor.getName());
      
      if (ACTOR_TYPE_OPENID.equals(actorType)) {
        xapiActor.setOpenid(actorId);
      }
      else if (ACTOR_TYPE_MBOX.equals(actorType)) {
        xapiActor.setMbox(actorId);
      }
      else {
        XApiAccount xapiAccount = new XApiAccount();
        
        if (actorExtensions != null && !actorExtensions.isEmpty()) {
          String homePage = actorExtensions.get("HOMEPAGE");
          if (StringUtils.isNotBlank(homePage)) {
            xapiAccount.setHomePage(homePage);
          }
        }
        
        xapiAccount.setName(actorId);
        xapiActor.setAccount(xapiAccount);
      }
      statement.setActor(xapiActor);
    }
    // ACTOR END
    
    // RESULT
    Entity caliperGeneratable = event.getGenerated();
    if (caliperGeneratable != null) {
      Map<String,String> caliperResultExtensions = caliperGeneratable.getExtensions();
      
      Map<URI,java.lang.Object> xapiResultExtensions = null;
      if (caliperResultExtensions != null && !caliperResultExtensions.isEmpty()) {
        xapiResultExtensions = new HashMap<>();
        for (Map.Entry<String,String> entry : caliperResultExtensions.entrySet()) {
          xapiResultExtensions.put(new URI(entry.getKey()), entry.getValue());
        }
        
        XApiResult xapiResult = new XApiResult();
        xapiResult.setExtensions(xapiResultExtensions);
        statement.setResult(xapiResult);
      }
    }
    // END Result


    // ACTION
    String action = event.getAction();
    if (StringUtils.isNotBlank(action)) {
      XApiVerb xapiVerb = new XApiVerb();
      xapiVerb.setId(caliperActionToXapiVerb(action));
      statement.setVerb(xapiVerb);
    }
    // ACTION END
    
    // OBJECT
    Entity object = event.getObject();
    if (object != null) {
      
      XApiObject xapiObject = new XApiObject();
      XApiObjectDefinition xapiObjectDefinition = new XApiObjectDefinition();
      
      String name = object.getName();
      if (StringUtils.isNotBlank(name)) {
        xapiObjectDefinition.setName(Collections.singletonMap("en", name));
      }
      
      String description = object.getDescription();
      if (StringUtils.isNotBlank(description)) {
        xapiObjectDefinition.setDescription(Collections.singletonMap("en", description));
      }
      
      xapiObjectDefinition.setType(caliperEntityTypeToXapiObjectType(object.getType()));
      
      Map<String,String> extensions = object.getExtensions();
      if (extensions != null && !extensions.isEmpty()) {
        Map<URI, java.lang.Object> xapiExtensions = new HashMap<>();
        for (String key : extensions.keySet()) {
          xapiExtensions.put(new URI(key), extensions.get(key));
        }
        xapiObjectDefinition.setExtensions(xapiExtensions);
      }
      
      xapiObject.setDefinition(xapiObjectDefinition);
      xapiObject.setId(object.getId());
      statement.setObject(xapiObject);
    }
    // OBJECT END
    
    Group group = event.getGroup();
    if (group != null) {
      XApiContext xapiContext = new XApiContext();
      
      Map<String,String> contextExtensions = group.getExtensions();
      if (contextExtensions != null && !contextExtensions.isEmpty()) {
        Map<URI,java.lang.Object> extensions = new HashMap<>();
        for (String key : contextExtensions.keySet()) {
          extensions.put(new URI(key), extensions.get(key));
        }
        xapiContext.setExtensions(extensions);
      }
      
      XApiContextActivities xapiContextActivities = new XApiContextActivities();
      XApiObject grouping = new XApiObject();
      grouping.setId(group.getId());      
      xapiContextActivities.setGrouping(Collections.singletonList(grouping));
      
      xapiContext.setContextActivities(xapiContextActivities);
      statement.setContext(xapiContext);
      
    }

    return statement;
  }
  
  private String xApiVerbToCaliperAction(String xapiVerbId) {
    Action caliperAction = verbActionMap.getKey(xapiVerbId);
    if (caliperAction == null) {
      return xapiVerbId;
    }
    
    return caliperAction.getValue();
  }
  
  private String caliperActionToXapiVerb(String caliperAction) {
    String verb = null;
    try {
      verb = verbActionMap.get(Action.valueOf(caliperAction));
      if (StringUtils.isBlank(verb)) {
        return DEFAULT_XAPI_VERB;
      }

    }
    catch (IllegalArgumentException e) {
      return DEFAULT_XAPI_VERB;
    }
    
    return verb;
  }
  
  private String xapiObjectTypeToCaliperEntityType(String xapiType) {
    
    if (StringUtils.isBlank(xapiType)) {
      return EntityType.DIGITAL_RESOURCE.getValue();
    }
    
    Type caliperType = objectEntityMap.getKey(xapiType);
    if (caliperType == null) {
      return xapiType;
    }
    
    return caliperType.getValue();
  }
  
  private String caliperEntityTypeToXapiObjectType(String caliperType) {
    String xapiType = objectEntityMap.get(caliperType);
    if (StringUtils.isBlank(xapiType)) {
      return caliperType;
    }
    
    return xapiType;
  }
  
  private String xapiToCaliperType(Statement statement) {
    // default to event
    String type = EventType.EVENT.getValue();
    // change if there is something more specific
    XApiVerb xapiVerb = statement.getVerb();
    if (xapiVerb != null) {
      String verbId = xapiVerb.getId();
      Action caliperAction = verbActionMap.getKey(verbId);
      EventType eventType = actionEventMap.get(caliperAction);
      if (eventType != null) {
        type = eventType.getValue();
      }
    }
    
    return type;
  }
  
  private static final String ACTOR_TYPE_MBOX = "foaf:mbox";
  private static final String ACTOR_TYPE_OPENID = "http://openid.net/";
  private static final String ACTOR_TYPE_ACCOUNT = "https://github.com/adlnet/xAPI-Spec/blob/master/xAPI.md#agentaccount";
  
  private static final String DEFAULT_XAPI_VERB = "http://adlnet.gov/expapi/verbs/experienced";
}
