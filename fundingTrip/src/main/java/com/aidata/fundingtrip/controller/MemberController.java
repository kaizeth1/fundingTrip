package com.aidata.fundingtrip.controller;

import com.aidata.fundingtrip.dto.MemberDto;
import com.aidata.fundingtrip.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j

public class MemberController {
	@Autowired
	private MemberService mServ;

	//첫 화면
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

	//아이디 찾기 화면
	@GetMapping("searchIdForm")
	public String searchIdForm(MemberDto member) {
		log.info("searchIdForm()");
		return "searchIdForm";
	}


	//아이디 찾기 처리
	@PostMapping("/serIdProc")
	public String serIdProc(@RequestParam String memail, @RequestParam String mname, Model model) {
		// 이메일과 이름을 기반으로 아이디를 찾는 비즈니스 로직
		String foundId = mServ.findIdByEmailAndName(memail, mname);

		if (foundId != null) {
			// 아이디를 찾은 경우
			model.addAttribute("msg", "회원님의 아이디는 " + foundId + "입니다.");
		} else {
			// 아이디를 찾지 못한 경우
			model.addAttribute("msg", "일치하는 정보가 없습니다.");
		}

		return "searchIdForm";
	}



	//비밀번호 찾기 화면
	@GetMapping("searchPwForm")
	public String searchPwForm(MemberDto member) {
		log.info("searchPwForm()");

		return "searchPwForm";
	}

	//비밀번호 찾기 처리
//	@PostMapping("serPwForm")
//	public String serPwForm(MemberDto member) {
//		log.info("serPwForm()");
//		String view = mServ.searchPwProc(member);
//		return view;
//	}

	//로그아웃
	@GetMapping("logout")
	public String logout(HttpSession session) {
		log.info("logout()");
		String view = mServ.logout(session);
		return view;

	}

	//	마이 페이지
	@GetMapping("myPage")
	public ModelAndView myPage(String mid) {
		log.info("myPage()");
		ModelAndView mv = mServ.selMember(mid);
		return mv;
	}

	@PostMapping("updateProc")
	public String updateProc(MemberDto member,
							 HttpSession session) {
		log.info("updateProc");

		//String sessionId = (String) session.getAttribute("mid"); // 세션에서 아이디 가져오기

		mServ.updateMember(member);


		return "redirect:/myPage";
	}


	@PostMapping("/dropInfo")
	public String dropInfo(HttpSession session, RedirectAttributes rttr) {
		log.info("dropInfo()");

		String result = mServ.drawMember(session, rttr);

		// 회원 탈퇴 결과에 따라 리다이렉트 경로를 설정한다.
		if ("success".equals(result)) {
			// 성공한 경우 로그아웃 후 메인 페이지로 리다이렉트 또는 다른 경로 설정
			session.invalidate(); // 세션 무효화 (로그아웃)
			return "redirect:/";
		} else {
			// 실패한 경우 마이페이지로 리다이렉트 또는 다른 경로 설정
			return "redirect:/myPage";
		}
	}

}



