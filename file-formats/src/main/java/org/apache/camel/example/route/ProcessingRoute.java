package org.apache.camel.example.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class ProcessingRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
    	from("direct:processRecord")
    		.convertBodyTo(String.class)
    		.log(LoggingLevel.INFO, "************ Processing Record: ${body}");
    }

}
