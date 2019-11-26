package com.example.apigateway.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceDescriptionUpdater {

    static class Service{
        String name,url;

        public Service(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(ServiceDescriptionUpdater.class);

    private static final String DEFAULT_SWAGGER_URL = "/v2/api-docs";

    public static final List<Service> services = Arrays.asList(
                                                                new Service("users-api", "http://users-api:2121"),
                                                                new Service("posts-api", "http://posts-api:2122"),
                                                                new Service("comments-api","http://comments-api:2123")
    );
    @Autowired
    private DiscoveryClient discoveryClient;

    private final RestTemplate template;

    public ServiceDescriptionUpdater() {
        this.template = new RestTemplate();
    }

    @Autowired
    private ServiceDefinitionsContext definitionContext;

    @Scheduled(fixedDelayString = "${swagger.config.refreshrate}")
    public void refreshSwaggerConfigurations() {

        services.forEach(service -> {
            String swaggerURL = service.url + DEFAULT_SWAGGER_URL;
            System.out.println(swaggerURL);
            Optional<Object> jsonData = getSwaggerDefinitionForAPI(service.name, swaggerURL);
            if (jsonData.isPresent()) {
                String content = getJSON(jsonData.get());
                definitionContext.addServiceDefinition(service.name, content);
            } else {
                logger.error("Skipping service id : {} Error : Could not get Swagegr definition from API ", service);
            }

            logger.info("Service Definition Context Refreshed at :  {}", LocalDate.now());
        });
    }

    private Optional<Object> getSwaggerDefinitionForAPI(String serviceName, String url) {
        logger.debug("Accessing the SwaggerDefinition JSON for Service : {} : URL : {} ", serviceName, url);
        try {
            Object jsonData = template.getForObject(url, Object.class);
            return Optional.of(jsonData);
        } catch (RestClientException ex) {
            logger.error("Error while getting service definition for service : {} Error : {} ", serviceName, ex.getMessage());
            return Optional.empty();
        }

    }

    public String getJSON(Object jsonData) {
        try {
            return new ObjectMapper().writeValueAsString(jsonData);
        } catch (JsonProcessingException e) {
            logger.error("Error : {} ", e.getMessage());
            return "";
        }
    }
}
