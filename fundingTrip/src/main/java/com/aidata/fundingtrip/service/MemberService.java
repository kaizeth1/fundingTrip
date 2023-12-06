package com.aidata.fundingtrip.service;

import com.aidata.fundingtrip.dao.MemberDao;
import com.aidata.fundingtrip.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
@Slf4j
@Transactional
public class MemberService {

    @Autowired
    private TransactionDefinition definition;

    @Autowired
    private PlatformTransactionManager manager;


    @Autowired
    private MemberDao mDao;

    //비밀번호 암호화 인코더
    private BCryptPasswordEncoder pEncoder = new BCryptPasswordEncoder();

    public String idCheck(String mid) {
        log.info("idCheck()");
        String result = null;

        int mcnt = mDao.selectId(mid);

        if (mcnt == 0) {
            result = "ok";
        } else {
            result = "fail";
        }

        return result;
    }

    public String memberJoin(MemberDto member, RedirectAttributes rttr) {
        log.info("memberJoin()");
        //가입성공 시 첫페이지, 실패 시 가입 페이지로 이동
        String view = null;
        String msg = null;

        String encPwd = pEncoder.encode(member.getMpw());
        //암호화된 비밀번호를 다시 dto 객체에 저장
        member.setMpw(encPwd);


        try {
            mDao.insertMember(member);
            msg = "가입 성공";
            view = "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "가입 실패";
            view = "redirect:joinForm";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    public String loginProc(MemberDto member, HttpSession session, RedirectAttributes rttr) {
        log.info("loginProc()");
        String view = null;
        String msg = null;

        //DB에서 해당 id의 비밀번호(암호문) 가져오기.
        String encPwd = mDao.selectPassword(member.getMid());
        // encPwd에 암호와된 비밀번호가 들어 가거나, - 해당 아이디가 존재
        // 비밀번호가 들어오지 않거나(null) - 해당 아이디가x
        if (encPwd != null) {
            //입력한 비번과 DB에서 가져온 비번 비교(matches)
            if (pEncoder.matches(member.getMpw(), encPwd)) {
                //로그인 성공.
                //회원 정보(아이디, 이름, 이메일등급이름) - from DB
                member = mDao.selectMember(member.getMid());
                //세션ㅇ[ 회원 정보 저장
                session.setAttribute("member", member);
                //로그인 성공 푸게 게시판 목록 페이지로 이동.
                view = "redirect:/";
                msg = "로그인 성공";
            } else {
                //로그이 실패. - 비번을 잘못 입력한 경우
                view = "redirect:loginForm";
                msg = "비밀번호가 틀립니다.";
            }
        } else {
            //아이디가 없는 경우
            view = "redirect:loginForm";
            msg = "존재하지 않는 아이디입니다.";
        }
        //화면으로 메시지 보내기
        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    public String logout(HttpSession session) {
        log.info("logout()");
        session.invalidate();
        return "redirect:/";
    }


//    @Transactional
//    public String updateMember(MemberDto member, HttpSession session, RedirectAttributes rttr) {
//        log.info("UpdateMember()");
//
//        TransactionStatus status = manager.getTransaction(definition);
//        String view = null;
//        String msg = null;
//        String mid = member.getMid();
//        MemberDto sessionMember = (MemberDto) session.getAttribute("member");
//
//
//
//        log.info("세션에서 가져온 회원 정보: {}", sessionMember);
//        log.info("mid: {}, sessionMember.getMid(): {}", mid, sessionMember.getMid());
//        try {
//            if ( mid == null ){
//            //(sessionMember != null && Objects.equals(mid, sessionMember.getMid())) {
//                // 세션에서 가져온 회원 정보의 mid와 전달된 mid가 일치하는지 확인
//                // && mid.equals(sessionMember.getMid())
//                member.setMid(mid); // mid 설정
//                log.info("mDao.memberUpdate 호출 전");
//                mDao.memberUpdate(member,session);
//                log.info("mDao.memberUpdate 호출 후");
//
//                view = "redirect:/myPage?mid=" + mid;
//                msg = "수정성공";
//                log.info("수정 실행됨");
//            } else {
//                // 세션에 로그인 정보가 없거나 mid가 일치하지 않는 경우
//                view = "redirect:/";
//                msg = "수정 실패: 권한이 없습니다.";
//                log.info("수정 실패: 권한이 없습니다.");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            view = "redirect:/";
//            msg = "수정실패";
//            log.info("수정실패");
//        } finally {
//            if (status != null && !status.isCompleted()) {
//                if (status.isRollbackOnly()) {
//                    manager.rollback(status);
//                } else {
//                    manager.commit(status);
//                }
//            }
//        }
//
//        rttr.addFlashAttribute("msg", msg);
//        return view;
//    }


    public ModelAndView selMember(String mid) {
        log.info("updateMember");
        ModelAndView mv = new ModelAndView();
        MemberDto member = mDao.selectMember(mid);

        mv.addObject("member", member);
        mv.setViewName("myPage");
        return mv;
    }


    public String drawMember(HttpSession session, RedirectAttributes rttr) {
        log.info("drawMember()");

        String view = null;
        String msg = null;
        MemberDto member = (MemberDto) session.getAttribute("member");
        String sessionId = (member != null) ? member.getMid() : null;


        try {
            if (sessionId != null) {

                mDao.drawMember(sessionId);
                view = "success"; //성공 시 success 문자열 반환
                msg = "회원 탈퇴 성공";
            } else {
                view = "error"; // 실패 시 error 문자열 반환
                msg = "회원 탈퇴 실패";
            }
        } catch (Exception e) {
            e.printStackTrace();
            view = "error"; // 실패 시 error 문자열 반환
            msg = "회원 탈퇴 실패 (캐치문)";
        }

        rttr.addFlashAttribute("msg", msg);
        return view;
    }


    public void updateMember(MemberDto member) {
        log.info("updateMember");
        mDao.memberUpdate(member);

    }

    public String findIdByEmailAndName(String memail, String mname) {
        String foundId = mDao.serchId(memail, mname);
        return foundId;
    }
}




















