package com.aidata.fundingtrip.controller;

import com.aidata.fundingtrip.service.TripBoardService;
import com.aidata.fundingtrip.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BoardREstController {

    @Autowired
    private MemberService mServ;
    @Autowired
    private TripBoardService bServ;

    @GetMapping("idCheck")
    public String idCheck(String mid){
        log.info("idCheck");
        return mServ.idCheck(mid);
    }
}
