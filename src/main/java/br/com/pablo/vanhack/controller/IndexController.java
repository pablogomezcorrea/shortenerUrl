package br.com.pablo.vanhack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	@GetMapping("/")
	public String indexGet() {
		return "/index"; 
	}
}