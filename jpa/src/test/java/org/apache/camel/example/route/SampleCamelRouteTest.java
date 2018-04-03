package org.apache.camel.example.route;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.camel.CamelContext;
import org.apache.camel.example.processor.Processor;
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
public class SampleCamelRouteTest {
	
    @Autowired
    private TestRestTemplate restTemplate;
//    
//    @Autowired
//    private CamelContext camelContext;
//    
//    @Bean
//    public Processor processor() {
//    	return new Processor();
//    }
  
    @Test
    public void sayHelloTest() {
        // Call the REST API
        ResponseEntity<String> response = restTemplate.getForEntity("/camel/hello", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String s = response.getBody();
        assertThat(s.equals("Hello World"));
    }

}
