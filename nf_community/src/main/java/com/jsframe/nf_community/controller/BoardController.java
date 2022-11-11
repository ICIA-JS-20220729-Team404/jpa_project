package com.jsframe.nf_community.controller;

import com.jsframe.nf_community.entity.Board;
import com.jsframe.nf_community.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BoardController {
    @Autowired
    BoardService bServ;

    @GetMapping("/board/list")
    public ModelAndView GetList() {
        ModelAndView mv = new ModelAndView();
        List<Board> list = bServ.getBoardList();

        mv.setViewName("home");
        mv.addObject("boardList", list);

        return mv;
    }
}
