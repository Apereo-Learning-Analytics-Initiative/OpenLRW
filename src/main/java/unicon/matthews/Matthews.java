package unicon.matthews;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import unicon.matthews.oneroster.Org;
import unicon.matthews.oneroster.OrgType;
import unicon.matthews.oneroster.Status;
import unicon.matthews.oneroster.service.OrgService;
import unicon.matthews.tenant.Tenant;
import unicon.matthews.tenant.service.TenantService;

import com.fasterxml.jackson.databind.DeserializationFeature;
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
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired 
	private OrgService orgService;
	
	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
	  return new LocalValidatorFactoryBean();
	}
	
	@PostConstruct
	public void init() {
	  Optional<Tenant> maybeDefaultTenant 
	    = tenantService.findByName(TenantService.DEFAULT_TENANT_NAME);
System.out.println("*************************");	  
System.out.println(maybeDefaultTenant.isPresent());
	  if (!maybeDefaultTenant.isPresent()) {
	    Tenant defaultTenant
	      = new Tenant.Builder()
	        .withName(TenantService.DEFAULT_TENANT_NAME)
	        .build();
	    
	    defaultTenant = tenantService.save(defaultTenant);
	    
	    Org defaultOrg
	      = new Org.Builder()
	        .withDateLastModified(LocalDateTime.now())
	        .withMetadata(Collections.singletonMap(Vocabulary.TENANT, defaultTenant.getId()))
	        .withName("DEFAULT_ORG")
	        .withSourcedId(UUID.randomUUID().toString())
	        .withStatus(Status.active)
	        .withType(OrgType.other)
	        .build();
	    
	    orgService.save(defaultTenant.getId(), defaultOrg);
	  }
	}
	
	@Bean
	public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.setDateFormat(new ISO8601DateFormat());
    mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    return mapper;
	}
}
