package unicon.matthews;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import unicon.matthews.admin.AdminUser;
import unicon.matthews.admin.AdminUserConfig;
import unicon.matthews.admin.service.AdminUserService;
import unicon.matthews.oneroster.Org;
import unicon.matthews.oneroster.OrgType;
import unicon.matthews.oneroster.Status;
import unicon.matthews.oneroster.service.OrgService;
import unicon.matthews.tenant.Tenant;
import unicon.matthews.tenant.service.TenantService;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

@SpringBootApplication
@EnableConfigurationProperties
@EnableMongoRepositories
@EnableSwagger2
@EnableAsync
public class Matthews {


    public static void main(String[] args) {
        SpringApplication.run(Matthews.class, args);
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
        Optional<Tenant> maybeDefaultTenant
                = tenantService.findByName(TenantService.DEFAULT_TENANT_NAME);
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

            if (adminUserConfig.getAdminuser() != null) {
                AdminUser suAdminUser
                        = new AdminUser.Builder()
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
        ApiInfo apiInfo
                = new ApiInfoBuilder()
                .title("Apereo LRW (Matthews) API")
                .description("")
                .build();
        return apiInfo;
    }

}
