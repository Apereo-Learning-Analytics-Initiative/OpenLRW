package unicon.matthews;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import unicon.matthews.oneroster.Org;
import unicon.matthews.oneroster.OrgType;
import unicon.matthews.oneroster.Status;
import unicon.matthews.oneroster.service.OrgService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

/**
 * Sample application for demonstrating security with JWT Tokens
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableMongoRepositories
public class Matthews {
	public static void main(String[] args) {
		SpringApplication.run(Matthews.class, args);
	}
	
	@Autowired OrgService orgService;
	
	@PostConstruct
	public void loadDemoData() {
	  
    Org org 
      = new Org.Builder()
        .withDateLastModified(LocalDateTime.now())
        .withIdentifier("identifier-1")
        .withName("demo org")
        .withSourcedId("demo-org-sourced-id")
        .withStatus(Status.active)
        .withType(OrgType.institution)
        .build();
    
    orgService.save("demo-tenant-id",org);
	}
	
	@Bean
	public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.setDateFormat(new ISO8601DateFormat());

    return mapper;
	}
}
