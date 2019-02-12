package org.apereo.openlrw.oneroster.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apereo.model.oneroster.AcademicSession;
import org.apereo.openlrw.OpenLRW;
import org.apereo.openlrw.Vocabulary;
import org.apereo.openlrw.oneroster.TestData;
import org.apereo.openlrw.oneroster.exception.AcademicSessionNotFoundException;
import org.apereo.openlrw.oneroster.service.AcademicSessionService;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author stalele
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenLRW.class)
@WebAppConfiguration
public class AcademicControllerTest {

  @MockBean
  private AcademicSessionService academicSessionService;

  @InjectMocks
  private AcademicSessionController controller;

  @MockBean
  JwtAuthenticationToken jwttoken;

  @Before
  public void setup() throws Exception {
    controller = new AcademicSessionController(academicSessionService);

    when(academicSessionService.findBySourcedId(TestData.TENANT_1, "*", TestData.ACADEMIC_SESSION_1)).thenReturn(academicSession1);
    when(academicSessionService.findBySourcedId(TestData.TENANT_1,"*", TestData.ACADEMIC_SESSION_2)).thenThrow(AcademicSessionNotFoundException.class);
    when(academicSessionService.save(TestData.TENANT_1,"*",academicSession3)).thenReturn(academicSession3);

    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("ROLE_TENANT_ADMIN"));
    UserContext context = UserContext.create(TestData.TENANT_1, "122", authorities);
    when(jwttoken.getPrincipal()).thenReturn(context);
  }

  Map<String, String> academicSession1Metadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
  AcademicSession academicSession1 = new AcademicSession.Builder()
                                    .withTitle("academicSession1")
                                    .withSourcedId(TestData.ACADEMIC_SESSION_1)
                                    .withMetadata(academicSession1Metadata)
                                    .build();

  Map<String, String> academicSession2Metadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_1);
  AcademicSession academicSession2 = new AcademicSession.Builder()
                                    .withTitle("academicSession2")
                                    .withSourcedId(TestData.ACADEMIC_SESSION_2)
                                    .withMetadata(academicSession2Metadata)
                                    .build();

  Map<String, String> academicSession3Metadata = Collections.singletonMap(Vocabulary.TENANT, TestData.TENANT_2);
  AcademicSession academicSession3 = new AcademicSession.Builder()
                                    .withTitle("academicSession3")
                                    .withSourcedId(TestData.ACADEMIC_SESSION_3)
                                    .withMetadata(academicSession3Metadata)
                                    .build();

  @Test
  public void getAcademicSession() throws Exception {
    AcademicSession session = controller.getAcademicSession(jwttoken, TestData.ACADEMIC_SESSION_1);
    assertTrue("academic session title is matching", session.getTitle().equalsIgnoreCase("academicSession1"));
  }

  @Test(expected=AcademicSessionNotFoundException.class)
  public void readTenants() throws Exception {
    controller.getAcademicSession(jwttoken, TestData.ACADEMIC_SESSION_2);
  }

  @Test
  public void createAcademicSession() throws Exception {
    ResponseEntity<AcademicSession> session = (ResponseEntity<AcademicSession>) controller.postAcademicSession(jwttoken, academicSession3);
    org.junit.Assert.assertEquals(session.getStatusCode(), HttpStatus.CREATED);
    org.junit.Assert.assertNotNull(session);
  }

  protected String json(Object o) throws IOException {
    try {
      return new ObjectMapper().writeValueAsString(o);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
