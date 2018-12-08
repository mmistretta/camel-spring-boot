package org.apache.camel.example.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.example.model.JsonPojo;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
    	DataFormat csvToPojo = new BindyCsvDataFormat(JsonPojo.class);
		
        from("file:/Users/marycochran/git/camel-spring-boot/file-formats/src/main/resources/csv/")
        	.routeId("consumer1")
        	.convertBodyTo(String.class)
        	.split().tokenize("\r\n|\n").streaming().parallelProcessing()
        	.unmarshal(csvToPojo)
        	.convertBodyTo(String.class)
        	.log(LoggingLevel.INFO, "************ Message Body is now: ${body}");
    }

}
