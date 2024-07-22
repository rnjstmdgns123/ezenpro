package com.a.ezn;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/test")
public class TestController {
	
	@RequestMapping(value="/home")
	public String testHome() {
		return "home2";
	}

}
