package com.jsframe.nf_community.controller;

import com.jsframe.nf_community.service.BoardService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@Log
public class MainController {
    @Autowired
    BoardService bServ;

    @GetMapping("/")
    public ModelAndView home(HttpSession session){
        ModelAndView mv = new ModelAndView();
        mv.addObject("member", session.getAttribute("mem"));
        mv.addObject("boardPage", bServ.getBoardPage(1));
        mv.setViewName("home");
        return mv;
    }
}
