package com.jsframe.nf_community.controller;

import com.jsframe.nf_community.entity.Lunch;
import com.jsframe.nf_community.service.LunchService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Log
@Controller
public class LunchController {
    @Autowired
    private LunchService lServ;

    // 점심 메뉴 작성
    @PostMapping("/lunch/write")
    @ResponseBody
    public boolean lunchWrite(Lunch lunch, HttpSession session){
        log.info("lunchWrite()");
        boolean result = lServ.insertLunch(lunch,session);
        return result;

    }
    // 점심 메뉴 전체 출력(List)
    @PostMapping("/lunch/list")
    @ResponseBody
    public List<Lunch> LunchList(Lunch lunch, HttpSession session){
        return lServ.getLunchList(lunch, session);
    }

    // 점심 메뉴 디테일 출력(Detail)
    @GetMapping("/lunch/detail")
    @ResponseBody
    public Lunch DetailLunch(long lno) {
        return lServ.getLunch(lno);
    }

}
