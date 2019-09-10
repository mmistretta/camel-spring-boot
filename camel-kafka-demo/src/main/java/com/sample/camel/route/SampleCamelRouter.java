/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sample.camel.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.sample.camel.model.User;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */

@Component
public class SampleCamelRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
    	restConfiguration()
        .component("servlet")
        .apiContextPath("api")
        .apiProperty("api.title", "Goodbye World")
        .apiProperty("api.version", "1.0.0");

    rest().description("Service to say Goodbye to you")
        .get("/goodbye").produces("text/plain")
        .responseMessage().code(200).message("OK").endResponseMessage()
        .route().routeId("say-goodbye")
        .to("direct:goodbye");
    
   from("direct:goodbye")
        .routeId("log-goodbye")
        .log(LoggingLevel.INFO, "Goodbye World")
        .transform().simple("Goodbye World");
    }

}
