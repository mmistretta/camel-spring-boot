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

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.sample.camel.process.SampleBean;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */

@Component
public class SampleCamelRouter extends RouteBuilder {
	
	@Autowired
	private SampleBean myBean;

    @Override
    public void configure() throws Exception {
        from("timer:hello?period={{timer.period}}")
        	.routeId("hello")
        	.bean(myBean, "sampleMethod")
        	.recipientList(header("sendTo"));
        
        //To show receiving of messages
        from("direct:Joe-Doe")
        	.log("In direct:Joe-Doe")
        	.to("stream:out");
        
        from("direct:Jane-Doe")
        	.log("In direct:Jane-Doe")
        	.to("stream:out");
        
        from("direct:Bob-Smith")
        	.log("In direct:Bob-Smith")
            .to("stream:out");
    }

}
