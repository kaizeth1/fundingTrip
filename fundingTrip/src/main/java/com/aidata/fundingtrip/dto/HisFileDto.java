package com.aidata.fundingtrip.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HisFileDto {
    private int hf_num;//boardfile 기본키
    private int hf_bnum;//게시글 번호
    private String hf_oriname;//원래 파일명
    private String hf_sysname;//변경한 파일명
}
