package org.apache.camel.example.route;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.flatpack.DataSetList;
import org.apache.camel.dataformat.flatpack.FlatpackDataFormat;
import org.apache.camel.example.model.JsonPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRouteTwo extends RouteBuilder {

	@Autowired
	private ProducerTemplate producerTemplate;

    @Override
    public void configure() throws Exception {
    	 FlatpackDataFormat fp = new FlatpackDataFormat();
    	 fp.setDefinition("fixedwidth.pzmap.xml");
    	 fp.setFixed(true);
    	 fp.setIgnoreFirstRecord(false);
    	 fp.isIgnoreExtraColumns();
         fp.setAllowShortLines(true);
    	 
         from("file:/Users/marycochran/git/camel-spring-boot/file-formats/src/main/resources/fixed/")
        	.routeId("consumer2")
        	.log(LoggingLevel.INFO, "Received message body: ${body}")
        	.unmarshal(fp)
        	.bean(this, "process");
    }
    
    public void process(Exchange exchange) {
    	 DataSetList list = exchange.getIn().getBody(DataSetList.class);
         for(Map<String, Object> map : list) {
        	 JsonPojo jp = new JsonPojo();
        	 jp.setFirstWord((String) map.get("FIRSTWORD"));
        	 jp.setSecondWord((String) map.get("SECONDWORD"));
        	 producerTemplate.sendBody("direct:processRecord", jp);
         }
    }
}
