package org.apache.camel.example.route;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.flatpack.DataSetList;
import org.apache.camel.dataformat.flatpack.FlatpackDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.camel.dataformat.FlatpackDataFormat;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRouteTwo extends RouteBuilder {
	
	private static final Logger logger = LoggerFactory.getLogger(ConsumerRouteTwo.class);

    @Override
    public void configure() throws Exception {
    	 FlatpackDataFormat fp = new FlatpackDataFormat();
    	// fp.
    	 fp.setDefinition("fixedwidth.pzmap.xml");
    	 fp.setFixed(true);
    	 fp.setIgnoreFirstRecord(false);
    	 fp.isIgnoreExtraColumns();
         fp.setAllowShortLines(true);
    	logger.info("Definition: " +fp.getDefinition() + ", Data format name:" + fp.getDataFormatName());
        from("file:/Users/marycochran/git/camel-spring-boot/file-formats/src/main/resources/fixed/")
        	.routeId("consumer2")
        	.log(LoggingLevel.INFO, "Received message body: ${body}")
        	.convertBodyTo(String.class)
        	.log(LoggingLevel.INFO, "Received String message body: ${body}")
        	//.split().tokenize("\r\n|\n").streaming().parallelProcessing()
        	.unmarshal(fp)
        	.log(LoggingLevel.INFO, "Received message body after unmarshall: ${body}")
        	.to("seda:queue:process");
        	
        from("seda:queue:process")
        	.bean(this, "process")
        	//.convertBodyTo(String.class)
        	.log(LoggingLevel.INFO, "************ Message Body is now: ${body}");
    }
    
    public void process(Exchange exchange) {
    	 logger.info("Exchange body: " + exchange.getIn().getBody());
    	 DataSetList list = exchange.getIn().getBody(DataSetList.class);
         logger.info("Length of list is: " + list.size()); 
    }
}
