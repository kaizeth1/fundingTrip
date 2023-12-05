package com.aidata.fundingtrip.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class HistoryBoardDto {
    private int hisnum;
    private String hisname;
    private String hisloca;
    private String hispic;
    private String hisexplan;
    private String hisinfo;
    private String hissysname;
}
