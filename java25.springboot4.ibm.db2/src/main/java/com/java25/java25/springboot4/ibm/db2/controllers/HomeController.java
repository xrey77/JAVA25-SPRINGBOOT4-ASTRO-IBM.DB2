package com.java25.java25.springboot4.ibm.db2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

   @GetMapping(value = "/{path:[^\\.]*}")
	public String index() {
       return "forward:/index.html";
	}
}