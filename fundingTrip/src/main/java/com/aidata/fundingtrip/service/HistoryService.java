package com.aidata.fundingtrip.service;

import com.aidata.fundingtrip.dao.BoardDao;
import com.aidata.fundingtrip.dao.HisDao;
import com.aidata.fundingtrip.dao.MemberDao;
import com.aidata.fundingtrip.dto.HisFileDto;
import com.aidata.fundingtrip.dto.HistoryBoardDto;
import com.aidata.fundingtrip.dto.ListDto;
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
public class HistoryService {
    private ModelAndView mv;
    @Autowired
    MemberDao mDao;
    @Autowired
    private HisDao hDao;

    //트랜젝션 관련 객체 선언
    @Autowired
    private PlatformTransactionManager manager;
    @Autowired
    private TransactionDefinition definition;

    private int lCnt = 5;//페이지 기본 값

    public ModelAndView getHisList(ListDto ldto, HttpSession session) {
        log.info("getHisList()");
        ModelAndView mv = new ModelAndView();
        //데이터베이스에서 글 가져오기
        int num = ldto.getPageNum();
        if (ldto.getListCnt() == 0) {
            ldto.setListCnt(lCnt);
        }
        //PageNum을 시작 번호로 변경
        ldto.setPageNum((num - 1) * ldto.getListCnt());
        List<HistoryBoardDto> hList = hDao.selectHisList(ldto);
        mv.addObject("hList", hList);

        //페이징 처리
        ldto.setPageNum(num);
        String pageHtml = getPaging(ldto);
        mv.addObject("paging", pageHtml);


        //페이지 번호와 검색 관련 내용을 세션에 저장
        if(ldto.getColname() != null){
            session.setAttribute("ldto", ldto);
        } else {
            //검색이 아닐 때는 제거
            session.removeAttribute("ldto");
        }
        //페이지 번호 저장
        session.setAttribute("pageNum", num);

        mv.setViewName("hisList");
        return mv;
    }

    private String getPaging(ListDto ldto) {
        String pageHtml = null;

        //전체 글개수 구하기(from DB)
        int maxNum = hDao.selectHisCnt(ldto);
        //페이지에 보여질 번호 개수
        int pageCnt = 4;
        String listName = "hisList?";
        if (ldto.getColname() != null) {
            listName += "colname=" + ldto.getColname()
                    + "&keyword=" + ldto.getKeyword() + "&";
        }

        PagingUtil paging = new PagingUtil(
                maxNum,
                ldto.getPageNum(),
                ldto.getListCnt(),
                pageCnt,
                listName

        );

        pageHtml = paging.makePaging();

        return pageHtml;
    }

    public String hisWrite(List<MultipartFile> files,
                           HistoryBoardDto hdto,
                           HttpSession session,
                           RedirectAttributes rttr) {
        log.info("hisWrite()");

        TransactionStatus status =
                manager.getTransaction(definition);

        String view = null;
        String msg = null;

        try {
            //글 내용 저장.
            hDao.saveHistory(hdto);
            //log.info("게시글 번호 : " + hdto.gethisnum());

            //파일 저장(파일 정보 저장)
            fileUpload(files, session, hdto.getHisnum());

            manager.commit(status);//최종 승인
            view = "redirect:/hisList?pageNum=1";
            msg = "작성 성공";
        } catch (Exception e) {
            e.printStackTrace();
            manager.rollback(status);//취소
            view = "redirect:hisWrite";
            msg = "작성 실패";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    private void fileUpload(List<MultipartFile> files,
                            HttpSession session,
                            int hisnum) throws Exception {
        //이 메소드의 예외처리(파일 저장 실패, 파일 정보 저장 실패)를
        //호출한 메소드에서 처리하도록 throws를 사용.
        log.info("fileUpload()");
        //파일 저장(폴더에...)
        //파일 저장 위치 처리: 세션에서 위치(경로) 정보를 구함.
        String realPath = session.getServletContext()
                .getRealPath("/");
        log.info(realPath);
        realPath += "hisUpload/";//파일 업로드용 폴더
        //업로드용 폴더가 없으면 자동으로 생성하자.
        File folder = new File(realPath);
        if (folder.isDirectory() == false) {
            //isDirectory() 폴더의 유무 확인 메소드
            //폴더가 있으면 true, 없거나 폴더가 아니면 false.
            folder.mkdir();//MaKe DIRectory(폴더)
        }

        for (MultipartFile mf : files) {
            //파일명(원래 이름) 추출
            String oriname = mf.getOriginalFilename();
            if (oriname.equals("")) {
                return;//업로드할 파일 없음. 파일 저장 작업 종료.
            }

            HisFileDto hfd = new HisFileDto();
            hfd.setHf_bnum(hisnum);//게시글 번호 저장.
            hfd.setHf_oriname(oriname);//원래 파일명 저장.
            String sysname = System.currentTimeMillis()
                    + oriname.substring(oriname.lastIndexOf("."));
            //air.jpg -> 1212412413.jpg
            hfd.setHf_sysname(sysname);

            //파일 저장(upload폴더에...)
            File file = new File(realPath + sysname);
            //......./.../.../webapp/upload/1212412413.jpg
            mf.transferTo(file);//하드디스크에 저장.

            //파일 정보 저장(DB에...)
            hDao.historyFile(hfd);
        }
    }

    public ModelAndView getHistory(int hisnum) {
        log.info("getHistory");
        ModelAndView mv = new ModelAndView();

        //게시글 번호로 선택한 게시물 가져오기
        HistoryBoardDto hisboard = hDao.selectHistory(hisnum);
        mv.addObject("hisboard", hisboard);

        //게시글의 파일목록 가져오기
        List<HisFileDto> bfList = hDao.selectHisFileList(hisnum);
        mv.addObject("bfList", bfList);

        mv.setViewName("hisDetail");

        return mv;
    }
}
