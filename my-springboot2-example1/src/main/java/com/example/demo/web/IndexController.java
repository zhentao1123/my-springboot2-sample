package com.example.demo.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController{

	@Value("${spring.profiles.active}")
	private String env;
	
	@RequestMapping()
	public String index(ModelMap mm) {
		mm.put("env", env);
		return "index";
	}
	
	@RequestMapping(value = "doc")
    public String swagger() {
        return "redirect:swagger-ui.html";
    }
}
