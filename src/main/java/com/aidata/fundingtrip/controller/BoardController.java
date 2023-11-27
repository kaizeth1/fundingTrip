package com.aidata.fundingtrip.controller;

import com.aidata.fundingtrip.dto.HistoryBoardDto;
import com.aidata.fundingtrip.dto.ListDto;
import com.aidata.fundingtrip.dto.MemberDto;
import com.aidata.fundingtrip.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class BoardController {
	private ModelAndView mv;
	@Autowired
	private BoardService bServ;



	//마이 페이지
	@GetMapping("myPage")
	public String myPage(MemberDto member, HttpSession session) {
		log.info("myPage()");

		return "myPage";
	}

	//유적지 목록 화면
	@GetMapping("historyList")
	public String historyList(HistoryBoardDto history, HttpSession session) {
		log.info("historyList()");

		return "historyList";
	}

	//유적지 상세 보기
	@GetMapping("historyDetail")
	public ModelAndView historyDetail(int hisnum) {
		log.info("historyDetail()");

		return mv;
	}

	//펀딩 글 목록
	@GetMapping("fundingList")
	public String fundingList() {
		log.info("fundingList()");

		return "fundingList";
	}

	//고객 센터 상세 보기
	@GetMapping("qnaDetail")
	public ModelAndView qnaDetail(int qnum) {
		log.info("qnaDetail()");

		return mv;
	}
}