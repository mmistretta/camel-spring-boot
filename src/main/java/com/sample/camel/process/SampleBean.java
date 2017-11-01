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
package com.sample.camel.process;

import java.util.Random;
import org.apache.camel.spi.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import com.sample.camel.model.User;

/**
 * A bean that returns a message when you call the {@link #saySomething()} method.
 * <p/>
 * Uses <tt>@Component("myBean")</tt> to register this bean with the name <tt>myBean</tt>
 * that we use in the Camel route to lookup this bean.
 */
@Component("myBean")
public class SampleBean {

    @Autowired
	CamelContext cc;

    public void sampleMethod(Exchange exchange) {
    	Message m = exchange.getIn();
    	//represents requests from various applications
    	Random r = new Random();
    	Integer i = r.nextInt(3);
    	//look up cooresponding bean and set header
    	Registry registry = cc.getRegistry(); 
    	User user = (User)registry.lookupByName("user" + i);
    	m.setHeader("sendTo", "direct:"+user.getFirstName()+"-"+ user.getLastName());
    	m.setBody(user.toString(),String.class);
    }

}
