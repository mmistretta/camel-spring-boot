package org.apache.camel.example.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class JPAPollingRoute extends RouteBuilder {
	
	   @Override
	    public void configure() throws Exception {
	        from("jpa://org.apache.camel.example.model.Student")
	        	.routeId("jpa-route")
	        	.log(LoggingLevel.INFO,"found student")
	        	.to("log:studentLog");

	    }

}
