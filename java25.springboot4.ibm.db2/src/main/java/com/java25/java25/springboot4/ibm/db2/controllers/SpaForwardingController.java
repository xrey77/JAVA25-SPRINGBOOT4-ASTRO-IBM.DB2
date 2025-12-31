package com.java25.java25.springboot4.ibm.db2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaForwardingController {
   @RequestMapping(value = "/{path:[^\\.]*}")
   public String redirect() {
       return "forward:/index.html";
   }
}
