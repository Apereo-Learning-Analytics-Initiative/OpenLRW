package org.apereo.openlrw.risk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
public class RiskTest {
    @Test
    public void whenMinimallyPopulatedAllGood() throws JsonProcessingException {
        Map<String, String> testMetadata = java.util.Collections.singletonMap("meta", "data");

        MongoRisk risk = new MongoRisk.Builder()
                .withSourcedId("risk-id")
                .withMetadata(testMetadata)
                .withActive(true)
                .withClassSourcedId("class-id")
                .withUserSourcedId("foobar")
                .withVelocity("-1.0")
                .withName("Computer Science - Semester 1 Risk")
                .build();


        ObjectMapper mapper = new ObjectMapper();
        String mapperResult = mapper.writeValueAsString(risk);
        assertThat(mapperResult, containsString("risk-id"));
        assertThat(mapperResult, containsString("class-id"));
        assertThat(mapperResult, containsString("foobar"));
        assertThat(mapperResult, containsString("-1.0"));
        assertThat(mapperResult, containsString("Computer Science - Semester 1 Risk"));
        assertThat(mapperResult, containsString("meta"));
        assertThat(mapperResult, containsString("data"));
    }
}
