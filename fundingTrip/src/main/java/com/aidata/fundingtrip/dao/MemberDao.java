package com.aidata.fundingtrip.dao;

import com.aidata.fundingtrip.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    int selectId(String mid);
    void insertMember(MemberDto mDto);
    String selectPassword(String mid);
    MemberDto selectMember(String mid);
}
