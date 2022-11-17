package com.jsframe.nf_community.controller;

import com.jsframe.nf_community.entity.Anonymous;
import com.jsframe.nf_community.service.AnonymousService;
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
public class AnonymousController {
    @Autowired
    private AnonymousService aServ;

    // 익명 게시판 작성
    @PostMapping("/anonymous/write")
    @ResponseBody
    public boolean anonymousWrite(Anonymous anonymous, HttpSession session){
        log.info("anonymousWrite()");
        boolean result = aServ.insertAnonymous(anonymous,session);
        return result;

    }
    // 익명 게시판 리스트 출력
    @GetMapping("/anonymous/list")
    @ResponseBody
    public List<Anonymous> anonymousList(){
        return aServ.getAnonymousList();
    }


}
