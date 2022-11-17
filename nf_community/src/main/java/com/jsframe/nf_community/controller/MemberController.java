package com.jsframe.nf_community.controller;

import com.jsframe.nf_community.entity.Member;
import com.jsframe.nf_community.service.MemberService;
import com.sun.net.httpserver.HttpsServer;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@Log
public class MemberController {
    @Autowired
    private MemberService mServ;

    @PostMapping("/member/login")
    @ResponseBody
    public boolean login(Member member, HttpSession session) {
        session.setAttribute("mem", member);
        boolean result = mServ.loginProc(member, session);
        return result;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public boolean logout(HttpSession session) {
        session.removeAttribute("mem");
        return true;
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
