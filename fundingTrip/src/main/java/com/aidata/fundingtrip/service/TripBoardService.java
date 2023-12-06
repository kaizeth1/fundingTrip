package com.aidata.fundingtrip.service;

import com.aidata.fundingtrip.dao.BoardDao;
import com.aidata.fundingtrip.dao.MemberDao;
import com.aidata.fundingtrip.dto.*;
import com.aidata.fundingtrip.util.PagingUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class TripBoardService {

    @Autowired
    private BoardDao bDao;

    @Autowired
    private PlatformTransactionManager manager;
    @Autowired
    private TransactionDefinition definition;

    private int lCnt = 5;//한 화면(페이지)에 보여질 글 개수
    private ModelAndView mv;

    public ModelAndView getFundList(SearchDto sDto, HttpSession session) {
        log.info("getFundList()");
        ModelAndView mv = new ModelAndView();
        //DB에서 게시글 가져오기
        int num = sDto.getPageNum();
        if (sDto.getListCnt() == 0){
            sDto.setListCnt(lCnt);
        }
        //pageNum을 LIMIT 시작 번호로 변경
        sDto.setPageNum((num - 1) * sDto.getListCnt());
        List<TripBoardDto> tList = bDao.selectTripBoardList(sDto);
        //DB에서 가져온 데이터를 mv에 담기
        mv.addObject("tList", tList);

        //페이징 처리
        sDto.setPageNum(num);//원래 페이지 번호로 환원
        String pageHtml = getTPaging(sDto);
        mv.addObject("paging", pageHtml);

        // 페이지 번호와 검색 관련 내용을 세션에 저장
        if (sDto.getColname() != null){
            session.setAttribute("sDto", sDto);
        } else {
            //검색이 아닐 때는 제거
            session.removeAttribute("sDto");
        }
        // 별개로 페이지 번호도 저장
        session.setAttribute("pageNum", num);

        mv.setViewName("fundList");
        return mv;
    }

    private String getTPaging(SearchDto sDto) {
        String pageHtml = null;
        int maxNum = bDao.selectTripBoardCnt(sDto);//전체 글 개수
        int pageCnt = 5;//페이지 번호 5개
        String listname = "fundList?";

        //검색 시의 url
        if (sDto.getColname() != null)
            listname += "colname" + sDto.getColname() + "&keyword" + sDto.getKeyword() + "&";

        //페이징 처리용 객체 생성
        PagingUtil paging = new PagingUtil(maxNum, sDto.getPageNum(), sDto.getListCnt(), pageCnt, listname);
        pageHtml = paging.makePaging();

        return pageHtml;
    }

    public String writeFundList(List<MultipartFile> files, TripBoardDto tDto, HttpSession session, RedirectAttributes rttr) {
        log.info("writeFundList()");

        //트랜잭션 상태처리 객체 (각 메서드 마다 넣어줘야함)
        TransactionStatus status = manager.getTransaction(definition);

        String view = null;
        String msg = null;

        try {
            //글 내용 저장.
            bDao.insertFundBoard(tDto);

            //파일 저장(파일 정보 저장)
            fundFileUpload(files, session, tDto.getTnum());

            manager.commit(status);//최종승인
            view = "redirect:fundList";
            msg = "작성 완료";

        }catch (Exception e){
            e.printStackTrace();
            manager.rollback(status);//취소
            view = "redirect:fundWrite";
            msg = "작성 실패";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    private void fundFileUpload(List<MultipartFile> files, HttpSession session, int tnum) throws Exception{
        log.info("fileUpload()");
        String realPath = session.getServletContext().getRealPath("/");
        log.info(realPath);
        realPath += "fundUpload/";
        File folder = new File(realPath);
        if (folder.isDirectory() == false){
            //isDirectory() 폴더의 유무 확인 메소드
            //폴더가 있으면 true, 없거나 폴더가 아니면 false.
            folder.mkdir();//MaKe DIRectory(폴더)
        }

        for (MultipartFile mf : files){
            // 파일명 (원래 이름) 추출
            String oriname = mf.getOriginalFilename();
            if (oriname.equals("")){
                return;
            }
            TripBoardFileDto tfDto = new TripBoardFileDto();
            tfDto.setTftnum(tnum);//게시글 번호 저장.
            tfDto.setToriname(oriname);//원래 파일명 저장.
            String sysname = System.currentTimeMillis() + oriname.substring(oriname.lastIndexOf("."));
            tfDto.setTsysname(sysname);

            File file = new File(realPath + sysname);
            mf.transferTo(file);

            bDao.insertFundFile(tfDto);
        }
    }
    public ModelAndView getFund(int tnum, HttpSession session, TripBoardDto tDto) {
        log.info("getFund");
        ModelAndView mv = new ModelAndView();

        //게시글 번호로 선택한 게시물 가져오기
        tDto = bDao.selectTripBoard(tnum);
        mv.addObject("tDto", tDto);

        //게시글의 파일목록 가져오기
        List<TripBoardFileDto> tfList = bDao.selectTripFileList(tnum);
        mv.addObject("tfList", tfList);

        //게시글의 댓글목록 가져오기
        List<TripReplyDto> trList = bDao.selectTripReplyList(tnum);
        mv.addObject("trList", trList);

        mv.setViewName("detailFund");

        return mv;
    }

    public String deleteTripBoard(int tnum, HttpSession session, RedirectAttributes rttr){
        log.info("deleteBoard()");

        //트랜젝션
        TransactionStatus status = manager.getTransaction(definition);

        String view = null;
        String msg = null;

        try {
            //파일 삭제 목록 구하기
            List<String> fList = bDao.selectTFnameList(tnum);

            bDao.deleteTripFiles(tnum);
            bDao.deleteTreplays(tnum);
            bDao.deleteTripBoard(tnum);

            //파일 삭제 처리
            if (fList.size() != 0){
                deleteTripFiles(fList, session);
            }

            manager.commit(status);

            view = "redirect:fundList?pageNum=1";
            msg = "삭제 성공";
        }catch (Exception e){
            e.printStackTrace();

            manager.rollback(status);

            view = "redirect:detailFund?tnum=" + tnum;
            msg = "삭제 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    private void deleteTripFiles(List<String> fList, HttpSession session) throws Exception{
        log.info("deleteTripFiles()");
        //파일 위치
        String realPath = session.getServletContext().getRealPath("/");
        realPath += "FundUpload/";

        for (String sn : fList){
            File file = new File(realPath + sn);
            if (file.exists() == true){
                file.delete();
            }
        }
    }

    public TripReplyDto treplyInsert(TripReplyDto treply) {
        log.info("replyInsert()");

        try {
            bDao.insertTreply(treply);
            treply = bDao.selectLastTreply(treply.getTrnum());
        }catch (Exception e){
            e.printStackTrace();
            treply = null;
        }
        return treply;
    }
}
