package com.aidata.fundingtrip.controller;

import com.aidata.fundingtrip.dto.MemberDto;
import com.aidata.fundingtrip.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class MemberController {
	private ModelAndView mv;
	@Autowired
	private MemberService mServ;

	@GetMapping("/")
	public String home() {
		log.info("home()");
		return "index";
	}

	//회원 가입 화면
	@GetMapping("joinForm")
	public String joinForm() {
		log.info("joinForm()");
		return "joinForm";
	}

	//회원 가입 처리
	@PostMapping("joinProc")
	public String joinProc(MemberDto member, RedirectAttributes rttr) {
		log.info("joinProc()");
		String view = mServ.memberJoin(member, rttr);
		return view;
	}

	//로그인 화면
	@GetMapping("loginForm")
	public String loginForm() {
		log.info("loginForm()");
		return "loginForm";
	}

	//로그인 처리
	@PostMapping("loginProc")
	public String loginProc(MemberDto member, HttpSession session, RedirectAttributes rttr) {
		log.info("loginProc");
		String view = mServ.loginProc(member, session, rttr);
		return view;
	}

	//아이디 찾기
	@GetMapping("searchIdForm")
	public ModelAndView searchIdForm(MemberDto member) {
		log.info("searchIdForm()");

		return mv;
	}

	//아이디 찾기 처리
	@PostMapping("searchIdProc")
	public String searchIdProc(MemberDto member) {
		log.info("searchIdProc()");
		String view = mServ.searchIdProc(member);
		return view;
	}

	//비밀번호 찾기
	@GetMapping("searchPwForm")
	public ModelAndView searchPwForm(MemberDto member) {
		log.info("searchPwForm()");

		return mv;
	}

	//비밀번호 찾기 처리
	@PostMapping("searchPwProc")
	public String searchPwProc(MemberDto member) {
		log.info("searchPwProc()");
		String view = mServ.searchPwProc(member);
		return view;
	}

	//로그아웃
	@GetMapping("logout")
	public String logout(HttpSession session) {
		log.info("logout()");
		String view = mServ.logout(session);
		return view;
	}
}