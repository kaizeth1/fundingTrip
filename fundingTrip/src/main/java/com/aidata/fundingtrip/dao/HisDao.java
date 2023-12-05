package com.aidata.fundingtrip.dao;

import com.aidata.fundingtrip.dto.HisFileDto;
import com.aidata.fundingtrip.dto.HistoryBoardDto;
import com.aidata.fundingtrip.dto.ListDto;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HisDao {
    void saveHistory(HistoryBoardDto hDto);
    void historyFile(HisFileDto hfd);
    HistoryBoardDto selectHistory(int hisnum);
    //전체 게시글 개수 구하는 메소드
    List<HistoryBoardDto> selectHisList(ListDto ldto);
    //게시글 저장 메소드
    int selectHisCnt(ListDto ldto);
    //게시글 번호에 해당하는 파일 목록을 가져오는 메소드
    List<HisFileDto> selectHisFileList(int hisnum);
}
