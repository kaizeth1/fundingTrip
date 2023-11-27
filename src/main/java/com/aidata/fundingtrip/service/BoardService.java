package com.aidata.fundingtrip.service;

import com.aidata.fundingtrip.repository.HistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
@Slf4j
public class BoardService {
    private ModelAndView mv;

    @Autowired
    private HistoryRepository hRepo;

    private ModelMapper mapper = new ModelMapper();
}
