package com.jsframe.nf_community.controller;

import com.jsframe.nf_community.entity.Member;
import com.jsframe.nf_community.service.MemberService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@Log
public class MemberController {

    @Autowired
    private MemberService mServ;
//--------------------------- 페이지 처리 시 ---------------------------------
    @GetMapping("joinFrm")
    public String joinFrm(){
        log.info("joinFrm()");
        return "joinFrm";
    }

    @PostMapping("joinProc")
    public String joinProc(Member member, RedirectAttributes rttr){
        log.info("joinProc()");
        String view = mServ.joinProc(member, rttr);

        return view;
    }

    @GetMapping("loginFrm")
    public String loginFrm(){
        log.info("loginFrm()");
        return "loginFrm";
    }

    @PostMapping("loginProc")
    public String loginProc(Member member, HttpSession session, RedirectAttributes rttr){
        log.info("loginProc()");
        String view = mServ.loginProc(member, session, rttr);

        return view;
    }
//--------------------------- modal 사용 ---------------------------------

    @GetMapping("login")
    public String login(){
        log.info("login()");
        return "login";
    }

    @PostMapping("/member/login")
    @ResponseBody
    public boolean login(Member member, HttpSession session) {
        boolean result = mServ.loginProc(member, session);
        return result;
    }

    @GetMapping("join")
    public String join(){
        log.info("join()");
        return "join";
    }

    @PostMapping("/member/join")
    @ResponseBody
    public boolean join(Member member) {
        boolean result = mServ.joinProc(member);
        return result;
    }

    @GetMapping ("/member/checkId")
    @ResponseBody
    public int checkId (@RequestParam String id) {
        return mServ.checkId(id);
    }

}
