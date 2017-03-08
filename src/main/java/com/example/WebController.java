package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
	
	@RequestMapping("/anonymous")
	public String anonymous() {
		return "Anonymous";
	}
	
	@RequestMapping("/simple")
	public String simple() {
		return "Simple";
	}
}
