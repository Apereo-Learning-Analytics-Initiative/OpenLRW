package org.apereo.openlrw.risk;

import org.apereo.model.oneroster.Result;
import org.apereo.openlrw.oneroster.TestData;
import org.apereo.openlrw.oneroster.exception.LineItemNotFoundException;
import org.apereo.openlrw.oneroster.exception.OrgNotFoundException;
import org.apereo.openlrw.oneroster.exception.ResultNotFoundException;
import org.apereo.openlrw.risk.endpoint.RiskController;
import org.apereo.openlrw.risk.service.RiskService;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RiskControllerTest {

  @Mock
  private RiskService riskService;

  @InjectMocks
  private RiskController riskController;

  @MockBean
  JwtAuthenticationToken jwtToken;

  MongoRisk risk = new MongoRisk.Builder().withSourcedId("risk-id")
          .withActive(true)
          .withClassSourcedId(TestData.CLASS_SOURCED_ID)
          .withUserSourcedId(TestData.USER_SOURCED_ID)
          .withVelocity("-1.0")
          .withName("Computer Science - Semester 1 Risk")
          .build();


  @Before
  public void init() throws OrgNotFoundException, LineItemNotFoundException {
    MockitoAnnotations.initMocks(this);
    riskController = new RiskController(null, riskService);
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("ROLE_TENANT_ADMIN"));
    UserContext context = UserContext.create(TestData.TENANT_1, "01", authorities);
    when(jwtToken.getPrincipal()).thenReturn(context);
    riskController.post(jwtToken, risk, true);
  }


  @Test(expected = Exception.class)
  public void testGetResultForUserResultNotFoundException() throws Exception {
    String userSourcedId = "jo zimmerman";
    String classSourcedId = "dead mow cinco";
    when(riskService.getRisksForUserAndClass(TestData.TENANT_1, "*", classSourcedId, userSourcedId, "")).thenThrow(Exception.class);
    riskController.getClassUser(jwtToken, classSourcedId, userSourcedId, "");
  }


}
