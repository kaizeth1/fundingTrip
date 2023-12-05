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

//	//아이디 찾기 처리
//	@PostMapping("serIdProc")
//	public String serIdProc(MemberDto member) {
//		log.info("serIdProc()");
//		String view = mServ.searchIdProc(member);
//		return view;
//	}

	//비밀번호 찾기 화면
	@GetMapping("searchPwForm")
	public String searchPwForm(MemberDto member) {
		log.info("searchPwForm()");

		return "searchPwForm";
	}

//	//비밀번호 찾기 처리
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
		ModelAndView mv = mServ.updateMember(mid);
		return mv;
	}

	@PostMapping("updateProc")
	public String updateProc(MemberDto member,
							 HttpSession session,
							 RedirectAttributes rttr
	) {
		log.info("updateProc()");

		String result = mServ.updateMember(member, session, rttr);

		// 업데이트 결과에 따라 리다이렉트 경로를 설정한다.
		if ("success".equals(result)) {
			// 성공한 경우 마이페이지로 리다이렉트
			return "redirect:/myPage?mid=" + member.getMid();
		} else {
			// 실패한 경우 메인 페이지로 리다이렉트 또는 다른 경로 설정
			return "redirect:/";

		}

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