package com.example.apigateway.controller;

import com.example.apigateway.swagger.ServiceDefinitionsContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@WebMvcTest(ServiceDefinitionController.class)
public class ServiceDefinitionControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    ServiceDefinitionController serviceDefinitionController;

    @Mock
    ServiceDefinitionsContext definitionsContext;

    private String serviceName;
    private ObjectMapper objectMapper;

    public ServiceDefinitionControllerTest () {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void init () {
        mockMvc = MockMvcBuilders.standaloneSetup(serviceDefinitionController)
                .build();
        serviceName = "swagger";
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getServiceDefinition_ServiceNameProvided_Success () throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/service/"+serviceName);

        when(definitionsContext.getSwaggerDefinition(any())).thenReturn(serviceName);
        String returnedDefinitionContext = serviceDefinitionController.getServiceDefinition("swagger");
        System.out.println(returnedDefinitionContext);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(returnedDefinitionContext));
    }

}
