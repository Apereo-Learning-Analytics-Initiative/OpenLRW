/**
 * 
 */
package unicon.matthews.xapi.endpoint;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import unicon.matthews.caliper.Event;
import unicon.matthews.caliper.service.EventService;
import unicon.matthews.oneroster.service.repository.MongoOrg;
import unicon.matthews.oneroster.service.repository.MongoOrgRepository;
import unicon.matthews.security.AuthorizationUtils;
import unicon.matthews.xapi.Statement;
import unicon.matthews.xapi.StatementResult;
import unicon.matthews.xapi.exception.InvalidXAPIRequestException;
import unicon.matthews.xapi.service.XapiConversionService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ggilbert
 *
 */
@RestController
@RequestMapping("/xAPI/statements")
public class XapiApiController {
  private final Logger logger = LoggerFactory.getLogger(XapiApiController.class);
  
  @Autowired private ObjectMapper objectMapper;
  @Autowired private Validator validator;
  @Autowired private XapiConversionService xapiToCaliperConversionService;
  @Autowired private MongoOrgRepository mongoOrgRepository;
  @Autowired private EventService eventService;
  
  @RequestMapping(value = { "", "/" }, 
      method = RequestMethod.POST, 
      consumes = "application/json", produces = "application/json;charset=utf-8")
  public List<String> postStatement(@RequestBody String json, @RequestHeader(value="Authorization") String authorizationHeader)
      throws InvalidXAPIRequestException {
    List<String> ids = null;
    String key = AuthorizationUtils.getKeyFromHeader(authorizationHeader);
    String secret = AuthorizationUtils.getSecretFromHeader(authorizationHeader);
    
    if (StringUtils.isNotBlank(key)) {
      MongoOrg mongoOrg = mongoOrgRepository.findByApiKeyAndApiSecret(key, secret);
      if (mongoOrg != null) { 
        try {
          if (json != null && StringUtils.isNotBlank(json)) {
            ids = new ArrayList<String>();

            List<Statement> statements = null;
            try {
              statements = objectMapper.readValue(json,
                  new TypeReference<List<Statement>>() {
                  });
            } catch (Exception e) {
              throw new InvalidXAPIRequestException(e);
            }

            for (Statement statement : statements) {
              Set<ConstraintViolation<Statement>> violations = validator
                  .validate(statement);
              if (!violations.isEmpty()) {
                StringBuilder msg = new StringBuilder();
                for (ConstraintViolation<Statement> cv : violations) {
                  msg.append(cv.getMessage() + ", ");
                }
                throw new InvalidXAPIRequestException(msg.toString());
              }
              logger.debug(
                  "Statement POST request received with input statement: {}",
                  statement);
              Event event = xapiToCaliperConversionService.fromXapi(statement);
              logger.debug("{}",event);
              if (StringUtils.isNotBlank(event.getId())) {
                Event existingEvent = eventService.getEventForId(mongoOrg.getTenantId(), mongoOrg.getId(), event.getId());
                if (existingEvent != null) {
                  throw new InvalidXAPIRequestException(String.format("Event with ID %s already exists", event.getId()));
                }
              }

              ids.add(eventService.save(mongoOrg.getTenantId(), mongoOrg.getId(), event));
            }
          }
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw new InvalidXAPIRequestException(e.getMessage(), e);
        }
      }
      else {
        throw new InvalidXAPIRequestException(String.format("Unknown Tenant %s",key));
      }
    }
    else {
      throw new InvalidXAPIRequestException("Missing Authorization Header");
    }

    return ids;
  }
  
  @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=utf-8")
  public StatementResult getStatements(
      @RequestHeader(value="Authorization") String authorizationHeader,
      @RequestParam(value = "statementId", required = false) String statementId,
      @RequestParam(value = "page", required = false, defaultValue = "0") String page,
      @RequestParam(value = "limit", required = false, defaultValue = "1000") String limit) throws URISyntaxException {
    StatementResult statementResult = null;
    String key = AuthorizationUtils.getKeyFromHeader(authorizationHeader);
    String secret = AuthorizationUtils.getSecretFromHeader(authorizationHeader);
    
    if (StringUtils.isNotBlank(key)) {
      MongoOrg mongoOrg = mongoOrgRepository.findByApiKeyAndApiSecret(key, secret);
      if (mongoOrg != null) { 
        if (StringUtils.isNotBlank(statementId)) {
          Event event = eventService.getEventForId(mongoOrg.getTenantId(), mongoOrg.getId(), statementId);
          if (event == null) {
            throw new InvalidXAPIRequestException(String.format("No statement with id %s",statementId));
          }
          Statement statement = xapiToCaliperConversionService.toXapi(event);
          statementResult = new StatementResult(Collections.singletonList(statement));
        }
        else {
          Collection<Event> events = eventService.getEvents(mongoOrg.getTenantId(), mongoOrg.getId());
          if (events != null && !events.isEmpty()) {
            List<Statement> statements = new ArrayList<>();
            for (Event e : events) {
              statements.add(xapiToCaliperConversionService.toXapi(e));
            }
            
            statementResult = new StatementResult(statements);
          }
        }
      }
      else {
        throw new InvalidXAPIRequestException(String.format("Unknown Tenant %s",key));
      }
    }
    else {
      throw new InvalidXAPIRequestException("Missing Authorization Header");
    }
    return statementResult;
  }
}
