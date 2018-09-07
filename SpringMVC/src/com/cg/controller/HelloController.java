package com.cg.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.model.Message;

@RestController
public class HelloController {
	@RequestMapping(value="/")
	public String sayHello() {
		
		return "Hello from spring back controller";
	}
	
	@RequestMapping(value="/message")
	public Message sendMessage() {
		Message m= new Message();
		m.setText("Hello this is shuchita!!!!");
		return m;
	}
	
}
