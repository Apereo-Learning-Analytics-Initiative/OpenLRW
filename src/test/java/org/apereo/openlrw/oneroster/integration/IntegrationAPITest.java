package org.apereo.openlrw.oneroster.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import org.apereo.model.oneroster.*;
import org.apereo.model.oneroster.Class;
import org.apereo.openlrw.OpenLRW;
import org.apereo.openlrw.Vocabulary;
import org.apereo.openlrw.oneroster.service.repository.MongoClass;
import org.apereo.openlrw.oneroster.service.repository.MongoClassRepository;
import org.apereo.openlrw.oneroster.service.repository.MongoOrg;
import org.apereo.openlrw.oneroster.service.repository.MongoOrgRepository;
import org.apereo.openlrw.security.auth.ajax.LoginRequest;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.apereo.openlrw.FongoConfig;
import org.apereo.openlrw.oneroster.TestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.junit.Assert.*;

/**
 * @author stalele
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes={OpenLRW.class,FongoConfig.class})
public class IntegrationAPITest {

  @Autowired 
  TestRestTemplate restTemplate;

  @ClassRule
  public static InMemoryMongoDb inMemoryMongoDb = newInMemoryMongoDbRule().build();

  @Rule
  public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("demo-test");

  /**
   * nosql-unit requirement
   */
  @Autowired
  private ApplicationContext applicationContext;

  @Autowired 
  private MongoOrgRepository orgRepository;
  
  @Autowired 
  private MongoClassRepository classRepository;

  private String username;
  private String password;
  private String token;

  /**
   * Initialize the org collection with random api key and api secret, class data
   */
  @Before
  public void init() {
    username = UUID.randomUUID().toString();
    password =UUID.randomUUID().toString();

    Org defaultOrg
    = new Org.Builder()
    .withDateLastModified(Instant.now())
    .withMetadata(Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1))
    .withName("DEFAULT_ORG")
    .withSourcedId(UUID.randomUUID().toString())
    .withStatus(Status.active)
    .withType(OrgType.other)
    .build();
    MongoOrg mongoOrgToSave =  new MongoOrg.Builder()
        .withApiKey(username)
        .withApiSecret(password)
        .withTenantId(TestData.TENANT_1)
        .withOrg(defaultOrg)
        .build();
    orgRepository.save(mongoOrgToSave);
    
    
    //initialize class data for result APIs
    Map<String, String> classMetadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
    Class c
    = new Class.Builder()
    .withSourcedId(TestData.CLASS_SOURCED_ID)
    .withCourse(new Course.Builder()
        .withSourcedId("courseSourcedId")
        .withCourseCode("CS101")
        .withMetadata(classMetadata)
        .withTitle("Spring")
        .withGrade("Point")
        .withSchoolYear("2017")
        .withSourcedId("1222")
        .withStatus(Status.active)
        .withSubjects(new ArrayList<String>(Arrays.asList("graphdb", "mongodb")))
        .build())
    .withStatus(Status.active)
    .withMetadata(classMetadata)
    .withTitle("Computer Science")
    .build();
    
    MongoClass klass = new MongoClass.Builder()
                            .withClassSourcedId(TestData.CLASS_SOURCED_ID)
                            .withKlass(c)
                            .withOrgId(TestData.ORG_1)
                            .withTenantId(TestData.TENANT_1)
                            .build();
    classRepository.save(klass);
    
  }

  /**This method executes following apis
   *<code>/api/auth/login</code>
   *<code>/api/classes/{classId}/results<code>
   *<code>/api/academicsessions</code>
   *<code>/api/academicsessions/{academicsessionId}</code>
   *<code>/api/classes/{classId}/lineitems/{lineitemid}/results</code>
   *<code>/api/classes/{classId}/results
   *<code>/api/users/{userId}/results</code>
   * @throws IOException
   * @throws URISyntaxException
   */
  @Test
  public void executeAcademicSessionAPIs() throws IOException, URISyntaxException {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("X-Requested-With", "XMLHttpRequest");
    headers.add("Cache-Control", "no-cache");

    LoginRequest request = new LoginRequest(username,password);
    String requestbody = json(request);
    HttpEntity<Object> entity = new HttpEntity<Object>(requestbody,headers);
    Token auth=
        restTemplate.postForObject("/api/auth/login", entity, Token.class);
    token = auth.token;
//    String sourcedId = executeSaveClassAPI();
    String resultSourcedId = saveResultForClass(TestData.CLASS_SOURCED_ID);
    executeGetResultForLineitemAPI();
    executeGetResultForClassAPI();
    executeGetResultForUserAPI();
    String sourcedId = saveAcademicSession(token);
    getAcademicSession(sourcedId);
  }


  private String saveAcademicSession(String token) {
    Map<String, String> academicSessionMetadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
    
    AcademicSession academicSession1 = 
        new AcademicSession.Builder()
        .withTitle("academicSession1")
        .withSourcedId(TestData.ACADEMIC_SESSION_1)
        .withMetadata(academicSessionMetadata)
        .withAcademicSessionType(AcademicSessionType.semester)
        .withDateLastModified(Instant.now())
        .withStartDate(LocalDate.of(2017, 8, 21))
        .withEndDate(LocalDate.of(2018, 6, 3))
        .withStatus(Status.active)
        .build();

    HttpHeaders headers1 = getHeaders();
    HttpEntity<Object> entity = new HttpEntity<Object>(academicSession1, headers1);
    ResponseEntity<AcademicSession> responseEntity =
        restTemplate.exchange("/api/academicsessions", HttpMethod.POST, entity, AcademicSession.class);
    
    assertNotNull(responseEntity.getBody());
    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    AcademicSession returnSession = responseEntity.getBody();
    return returnSession.getSourcedId();
  }


  private void getAcademicSession(String sourcedId) throws URISyntaxException {
    HttpHeaders headers1 = getHeaders();
    HttpEntity<Object> entity = new HttpEntity<Object>(headers1);
    String url = String.format("/api/academicsessions/%s",sourcedId);
    ResponseEntity<AcademicSession> responseEntity =
        restTemplate.exchange(url, HttpMethod.GET, entity, AcademicSession.class);
    AcademicSession session = responseEntity.getBody();
    System.out.print(session.toString());
    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals("academicSession1", session.getTitle());
  }


  private HttpHeaders getHeaders() {
    HttpHeaders headers1 = new HttpHeaders();
    headers1.setContentType(MediaType.APPLICATION_JSON);
    headers1.add("X-Requested-With", "XMLHttpRequest");
    headers1.add("Cache-Control", "no-cache");
    headers1.set("Authorization", "Bearer "+ token);
    return headers1;
  }
  
  /**TODO: this method does not work because ClassService tries to find lineitems and enrollment information for the newly created class.
   * Need to discuss with Gary
   *  
   * @return String
   */
  private String executeSaveClassAPI() {
    Map<String, String> classMetadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
    Class c
    = new Class.Builder()
    .withSourcedId("c1")
    .withCourse(new Course.Builder()
        .withSourcedId("courseSourcedId")
        .withCourseCode("CS101")
        .withMetadata(classMetadata)
        .withTitle("Spring")
        .withGrade("Point")
        .withSchoolYear("2017")
        .withSourcedId("1222")
        .withStatus(Status.active)
        .withSubjects(new ArrayList<String>(Arrays.asList("graphdb", "mongodb")))
        .build())
    .withStatus(Status.active)
    .withMetadata(classMetadata)
    .withTitle("Computer Science")
    .build();
    
    HttpHeaders headers1 = getHeaders();
    HttpEntity<Object> entity = new HttpEntity<Object>(c, headers1);
    ResponseEntity<Class> responseEntity =
        restTemplate.exchange("/api/classes", HttpMethod.POST, entity, Class.class);
    
    Class klass = responseEntity.getBody();
    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals("Computer Science", klass.getTitle());
    return responseEntity.getBody().getSourcedId();
  }
  
  private String saveResultForClass(String sourcedId) {
    Map<String, String> resultMetadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
    Result result = 
        new Result.Builder()
        .withResultstatus("Grade B")
        .withScore(70.0)
        .withComment("not bad")
        .withMetadata(resultMetadata)
        .withSourcedId(TestData.RESULT_SOURCED_ID)
        .withDate(Instant.now())
        .withDateLastModified(Instant.now())
        .withStatus(Status.active)
        .withLineitem(new Link.Builder().withSourcedId(TestData.LINEITEM_SOURCED_ID).build())
        .withStudent(new Link.Builder().withSourcedId(TestData.USER_SOURCED_ID).build())
        .build();
    HttpHeaders headers1 = getHeaders();
    HttpEntity<Object> entity = new HttpEntity<Object>(result, headers1);
    ResponseEntity<Result> responseEntity =
        restTemplate.exchange(String.format("/api/classes/%s/results",sourcedId), HttpMethod.POST, entity, Result.class);
    
    Result responseResult = responseEntity.getBody();
    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals(new Double(70.0), responseResult.getScore());
    return responseEntity.getBody().getSourcedId();
  }

  private void executeGetResultForLineitemAPI() {
    HttpHeaders headers1 = getHeaders();
    HttpEntity<Object> entity = new HttpEntity<Object>(headers1);
    ResponseEntity<Result> responseEntity =
        restTemplate.exchange(String.format("/api/classes/%s/lineitems/%s/results",TestData.CLASS_SOURCED_ID,TestData.LINEITEM_SOURCED_ID), HttpMethod.GET, entity, Result.class);
    
    Result responseResult = responseEntity.getBody();
    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals(new Double(70.0), responseResult.getScore());
  }
  
  private void executeGetResultForClassAPI() {
    HttpHeaders headers1 = getHeaders();
    HttpEntity<Object> entity = new HttpEntity<Object>(headers1);
    ResponseEntity<ArrayList> responseEntity =
        restTemplate.exchange(String.format("/api/classes/%s/results",TestData.CLASS_SOURCED_ID), HttpMethod.GET, entity, ArrayList.class);
    
    ArrayList<Result> responseResults = responseEntity.getBody();
    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals(1, responseResults.size());
  }
  
  private void executeGetResultForUserAPI() {
    HttpHeaders headers1 = getHeaders();
    HttpEntity<Object> entity = new HttpEntity<Object>(headers1);
    ResponseEntity<Result> responseEntity =
        restTemplate.exchange(String.format("/api/users/%s/results",TestData.USER_SOURCED_ID), HttpMethod.GET, entity, Result.class);
    
    Result responseResult = responseEntity.getBody();
    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals(new Double(70.0), responseResult.getScore());
  }
  

  protected String json(Object o) throws IOException {
    try {
      return new ObjectMapper().writeValueAsString(o);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
