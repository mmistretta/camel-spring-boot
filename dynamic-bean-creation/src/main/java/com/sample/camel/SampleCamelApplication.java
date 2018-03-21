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
package com.sample.camel;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import com.sample.camel.model.BeanListFactory;
import com.sample.camel.model.User;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//CHECKSTYLE:OFF
/**
 * A sample Spring Boot application that starts the Camel routes.
 */
@SpringBootApplication
@Configuration
@ComponentScan("com.sample.camel")
public class SampleCamelApplication {
	
	 @Autowired
	 private ConfigurableBeanFactory beanFactory;

    /**
     * A main method to start this application.
     */
    public static void main(String[] args) {
        SpringApplication.run(SampleCamelApplication.class, args);
    }
    
    //this creates all the user objects
    @Bean
    public BeanListFactory userFactory() throws Exception{
    	BeanListFactory blf = new BeanListFactory();
    	blf.setPropertyPrefix("user");
    	blf.setTargetType(User.class);
    	Map<String,String> common = new HashMap<String,String>();
    	common.put("favoriteNumber","10");
    	blf.setCommonProperties(common);
    	final Properties properties = new Properties();
    	try (final InputStream stream =
    	           this.getClass().getResourceAsStream("/application.properties")) {
    	    properties.load(stream);
    	}
    	blf.setProperties(properties);
    	return blf;
    }
    
    //adds all user beans to registry
    @PostConstruct
    public void registerUsers() throws Exception{
    	List l = userFactory().getObject();
    	for(int i = 0; i< l.size(); i++){
    		User u = (User)l.get(i);
    		beanFactory.registerSingleton("user"+i, u);
    	}
    }

}
//CHECKSTYLE:ON
