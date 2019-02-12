package org.apereo.openlrw;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.apereo.model.oneroster.Org;
import org.apereo.model.oneroster.OrgType;
import org.apereo.model.oneroster.Status;
import org.apereo.openlrw.admin.AdminUser;
import org.apereo.openlrw.admin.AdminUserConfig;
import org.apereo.openlrw.admin.service.AdminUserService;
import org.apereo.openlrw.oneroster.service.OrgService;
import org.apereo.openlrw.tenant.Tenant;
import org.apereo.openlrw.tenant.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author ggilbert
 * @author nisithdash
 * @author scody
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */

@SpringBootApplication
@EnableConfigurationProperties
@EnableMongoRepositories
@EnableSwagger2
@EnableAsync
public class OpenLRW {

    public static void main(String[] args) {
        SpringApplication.run(OpenLRW.class, args);
    }

    @Autowired
    private TenantService tenantService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminUserConfig adminUserConfig;


    @Bean
    public javax.validation.Validator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @PostConstruct
    public void init() {
        Optional<Tenant> maybeDefaultTenant = tenantService.findByName(TenantService.DEFAULT_TENANT_NAME);
        if (!maybeDefaultTenant.isPresent()) {
            Tenant defaultTenant = new Tenant.Builder()
                    .withName(TenantService.DEFAULT_TENANT_NAME)
                    .build();

            defaultTenant = tenantService.save(defaultTenant);

            Org defaultOrg = new Org.Builder()
                    .withDateLastModified(Instant.now())
                    .withMetadata(Collections.singletonMap(Vocabulary.TENANT, defaultTenant.getId()))
                    .withName("DEFAULT_ORG")
                    .withSourcedId(UUID.randomUUID().toString())
                    .withStatus(Status.active)
                    .withType(OrgType.other)
                    .build();

            orgService.save(defaultTenant.getId(), defaultOrg);

            if (adminUserConfig.getAdminuser() != null) {
                AdminUser suAdminUser = new AdminUser.Builder()
                        .withUserName(adminUserConfig.getAdminuser())
                        .withPassword(adminUserConfig.getPassword())
                        .withEmailAddress(adminUserConfig.getEmailAddress())
                        .withTenantId(defaultTenant.getId())
                        .withOrgId(defaultOrg.getSourcedId())
                        .withSuperAdmin(Boolean.TRUE)
                        .build();

                adminUserService.createUser(suAdminUser);
            }
        }
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.setDateFormat(new ISO8601DateFormat());
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
    
    @Bean
    public ExecutorService ExecutorService() {
      return Executors.newFixedThreadPool(3);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Apereo OpenLRW API - Documentation")
                .description("The Open-source standards-focused Learning Records Warehouse.")
                .build();
    }
    
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(50000);
        return loggingFilter;
    }

}
