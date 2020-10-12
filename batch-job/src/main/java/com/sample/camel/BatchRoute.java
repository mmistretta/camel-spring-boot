package com.sample.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class BatchRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("quartz2://tab?cron=0+0/2+8-20+?+*+MON-FRI") //every 2 minutes 8am-8pm Mon-Fri
            .setBody().constant("cron event")
            .log("${body}");

        from("timer://foo?fixedRate=true&period=6000") //every 6 seconds
            .setBody().constant("timer event")
            .log("${body}");
    }
}