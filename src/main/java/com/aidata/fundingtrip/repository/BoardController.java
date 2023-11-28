package com.aidata.fundingtrip.controller;

import com.aidata.fundingtrip.dto.HistoryBoardDto;
import com.aidata.fundingtrip.dto.MemberDto;
import com.aidata.fundingtrip.dto.QnABoardDto;
import com.aidata.fundingtrip.service.BoardService;
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
	@GetMapping("hisList")
	public String hisList(HistoryBoardDto history, HttpSession session) {
		log.info("historyList()");

		return "hisList";
	}

	//유적지 상세 보기
	@GetMapping("hisDetail")
	public ModelAndView historyDetail(int hisnum) {
		log.info("historyDetail()");
		mv = bServ.getHistory(hisnum);
		return mv;
	}

	//유적지 정보 작성
	@GetMapping("hisWrite")
	public String hisWrite() {
		log.info("hisWrite()");
		return "hisWrite";
	}

	//유적지 정보 작성 처리
	@PostMapping("hisWriteProc")
	public String hisWriteProc(HistoryBoardDto hbDto, HttpSession session, RedirectAttributes rttr) {
		log.info("hisWriteProc()");
		String view = bServ.;
		return view;
	}

	//유적지 정보 수정
	@GetMapping("hisUpdate")
	public ModelAndView hisUpdate(int hisnum) {
		log.info("hisUpdate()");
		mv = bServ.getHistory(hisnum);
		mv.setViewName("hisUpdate");
		return mv;
	}

	//유적지 정보 수정 처리
	@PostMapping("hisUpdateProc")
	public String hisUpdateProc(HistoryBoardDto hbDto, HttpSession session, RedirectAttributes rttr) {
		log.info("hisUpdateProc()");
		String view = bServ.hisBoardUpdate(hbDto, session, rttr);
		return view;
	}

	//펀딩 글 목록
	@GetMapping("fundList")
	public String fundList() {
		log.info("fundList()");

		return "fundList";
	}

	//펀딩 글 상세 보기
	@GetMapping("detailFund")
	public ModelAndView detailFund() {
		log.info("detailFund");
		//mv=bServ.
		return mv;
	}

	//고객 센터 글 목록
	@GetMapping("qnaList")
	public String qnaList() {
		log.info("qnaList()");
		return "qnaList";
	}

	//고객 센터 글쓰기
	@GetMapping("qnaWrite")
	public String qnaWrite() {
		log.info("qnaWrite()");
		return "qnaWrite";
	}

	//고객 센터 글쓰기 처리
	@PostMapping("qnaWriteProc")
	public String qnaWriteProc() {
		log.info("qnaWriteProc()");
		String view = bServ.;
		return view;
	}

	//고객 센터 상세 보기
	@GetMapping("qnaDetail")
	public ModelAndView qnaDetail(int qnum) {
		log.info("qnaDetail()");

		return mv;
	}

	//고객 센터 글 수정
	@GetMapping("qnaUpdate")
	public ModelAndView qnaUpdate() {
		log.info("qnaUpdate()");

		return mv;
	}

	//고객 센터 글 수정 처리
	@PostMapping("qnaUpdateProc")
	public String qnaUpdateProc(QnABoardDto qDto, HttpSession session, RedirectAttributes rttr) {
		log.info("qnaUpdateProc()");
		String view = bServ.qnaUpdateBoard(qDto, session, rttr);
		return view;
	}
}