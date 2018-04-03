package org.apache.camel.example.processor;

import org.apache.camel.Exchange;
import org.apache.camel.example.model.Student;
import org.apache.camel.example.model.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Processor {
	
	@Autowired
	private StudentRepository studentRepo;
	
	public void saveStudent(Exchange e) {
		Student s = new Student();
		s.setName("Bob");
		s.setPassportNumber("1233432");
		studentRepo.save(s);
	}

}
