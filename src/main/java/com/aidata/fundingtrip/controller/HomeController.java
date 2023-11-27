package com.aidata.fundingtrip.controller;

import com.aidata.fundingtrip.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class HomeController {
	private ModelAndView mv;
	@Autowired
	private HomeService hServ;

	@GetMapping("/")
	public ModelAndView home() {
		log.info("index()");
		//mv=hServ.
		return mv;
	}

	@GetMapping("loginForm")
	public String loginForm(){
		log.info("loginForm()");
		return "loginForm";
	}

	@GetMapping("searchIdForm")
	public String searchIdForm(){
		log.info("searchIdForm()");
		return "searchIdForm";
	}

	@GetMapping("searchPwForm")
	public String searchPwForm(){
		log.info("searchPwForm()");
		return "searchPwForm";
	}

	@GetMapping("myPage")
	public String myPage(){
		log.info("myPage()");
		return "myPage";
	}

	@GetMapping("historyList")
	public String historyList(){
		log.info("historyList()");
		return "historyList";
	}

	@GetMapping("historyDetail")
	public String historyDetail(){
		log.info("historyDetail()");
		return "historyDetail";
	}

	@GetMapping("fundingList")
	public String fundingList(){
		log.info("fundingList()");
		return "fundingList";
	}

	@GetMapping("qnaDetail")
	public String qnaDetail(){
		log.info("qnaDetail()");
		return "qnaDetail";
	}
}