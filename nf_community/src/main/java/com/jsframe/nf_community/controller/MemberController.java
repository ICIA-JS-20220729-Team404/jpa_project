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

import javax.servlet.http.HttpSession;

@Controller
@Log
public class MemberController {
    @Autowired
    private MemberService mServ;

    @PostMapping("/member/login")
    @ResponseBody
    public boolean login(Member member, HttpSession session) {
        boolean result = mServ.loginProc(member, session);
        if (result) {
            session.setAttribute("mem", member);
        }
        return result;
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
