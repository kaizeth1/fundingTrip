package com.aidata.fundingtrip.dao;

import com.aidata.fundingtrip.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    int selectId(String mid);
    void insertMember(MemberDto mDto);
    String selectPassword(String mid);
    MemberDto selectMember(String mid);

    String memberUpdate(MemberDto member, HttpSession session);//회원정보 수정

    void drawMember(String sessionId);//회원탈퇴
}
