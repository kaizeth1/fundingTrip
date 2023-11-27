package com.aidata.fundingtrip.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripBoardDto {
    private int tnum;
    private String tmid;
    private String ttitle;
    private String tcontents;
    private int tpeople;
    private int tstart;
    private int tend;
    private String tstatus;
    private String tpic;
    private String tsysname;
}