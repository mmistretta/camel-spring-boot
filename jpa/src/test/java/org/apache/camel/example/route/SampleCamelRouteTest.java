package org.apache.camel.example.route;
import static org.assertj.core.api.Assertions.assertThat;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.example.model.Student;
import org.apache.camel.example.processor.Processor;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SampleCamelRouteTest extends CamelTestSupport {
	
    @Autowired
    private TestRestTemplate restTemplate;
    
    /**
     * Mock Endpoints
     */
    @Override
    public String isMockEndpoints() {
        //return "direct:jpa-route";
        return "*";
    }
    
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new JPAPollingRoute();
    }

    @Override
    public void setUp() throws Exception {
        replaceRouteFromWith("jpa-route", "direct:jpa-route");
        super.setUp();
    }
  
    @Test
    public void sayHelloTest() {
        // Call the REST API
        ResponseEntity<String> response = restTemplate.getForEntity("/camel/hello", String.class);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
        String s = response.getBody();
        log.info("S is: " + s);
        Assert.assertTrue(s.contains("Hello World"));
    }
    
    @Test
    public void testConsumeMessageRoute() throws Exception {
        // Get Mocks
        MockEndpoint incomingMsg = getMockEndpoint("mock:direct:jpa-route");
        MockEndpoint logEndpoint = getMockEndpoint("mock:log:studentLog");

        Student s = new Student();
        s.setId(1l);
        s.setName("Bob");
        s.setPassportNumber("123456");

        // Set Assertions
        incomingMsg.expectedMessageCount(1);
        incomingMsg.expectedBodiesReceived(s);
        logEndpoint.expectedMessageCount(1);
        logEndpoint.expectedBodiesReceived(s);
        
        // Send Test Message to Endpoint that replaced the JMS Queue
        template.sendBody("direct:jpa-route", s);

        // Set Mocks Satisfied
        incomingMsg.assertIsSatisfied();
        logEndpoint.assertIsSatisfied();
    }

}
