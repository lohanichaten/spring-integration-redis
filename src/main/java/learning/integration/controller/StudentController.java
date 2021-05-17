package learning.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learning.integration.inteface.MessageGateway;
import learning.integration.student.Student;

@RestController
@RequestMapping("/api/student")
public class StudentController {

	@Autowired
	private MessageGateway gateway;
	
	@PostMapping
	public void sendStudentInformation(@RequestBody Student student) {
		gateway.sendMessage(student);
	}
}
