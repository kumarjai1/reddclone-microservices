package com.example.apigateway.swagger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ServiceDefinitionsContextTest {

    @InjectMocks
    ServiceDefinitionsContext serviceDefinitionsContext;


    private ConcurrentHashMap<String,String> serviceDescriptions;
    private String serviceName;
    private String serviceId;

    @Before
    public void init () {
        serviceName = "swagger";
        serviceId = "1";
        serviceDescriptions = new ConcurrentHashMap<>();
    }

    @Test
    public void addServiceDefinition() {
        serviceDefinitionsContext.addServiceDefinition("swagger","serviceDescriptions");
        assertThat(serviceDescriptions).isNotNull();
    }

    @Test
    public void getSwaggerDefinition() {
        serviceDefinitionsContext.getSwaggerDefinition(serviceId);
        assertThat(serviceDescriptions).isNotNull();
    }

    @Test
    public void getSwaggerDefinitions() {

    }
}
