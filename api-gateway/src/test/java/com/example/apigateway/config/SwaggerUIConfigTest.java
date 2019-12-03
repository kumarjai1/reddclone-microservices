package com.example.apigateway.config;

import com.example.apigateway.swagger.ServiceDefinitionsContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SwaggerUIConfigTest {

    @InjectMocks
    SwaggerUIConfig swaggerUIConfig;

    @Mock
    ServiceDefinitionsContext definitionsContext;

    @Mock
    RestTemplate temp;

    @Mock
    InMemorySwaggerResourcesProvider defaultResourceProvider;

    private List<SwaggerResource> resourceList;

    @Test
    public void configureTemplate() {
        RestTemplate reRest = swaggerUIConfig.configureTempalte();
        assertThat(reRest).isNotNull();
    }

    @Test
    public void swaggerResourcesProvider() {
        when(defaultResourceProvider.get()).thenReturn(resourceList);
        when(definitionsContext.getSwaggerDefinitions()).thenReturn(resourceList);
        SwaggerResourcesProvider resourceListTemp  =  swaggerUIConfig.swaggerResourcesProvider(defaultResourceProvider, temp);

        assertThat(resourceListTemp).isNotNull();

    }
}
