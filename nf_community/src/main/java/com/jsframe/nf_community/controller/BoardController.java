package com.jsframe.nf_community.controller;

import com.jsframe.nf_community.entity.Board;
import com.jsframe.nf_community.service.BoardService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Log
public class BoardController {
    ModelAndView mv;

    @Autowired
    private BoardService bServ;

    @GetMapping("/board/testList")
    @ResponseBody
    public List<Board> GetListTest(Integer pageNum, HttpSession session) {
        List<Board> list = bServ.getBoardList(pageNum, session);
        return list;
    }

    @GetMapping("/board/list")
    public List<Board> GetList(Integer pageNum, HttpSession session) {
        mv = new ModelAndView();
        List<Board> list = bServ.getBoardList(pageNum, session);
        return list;
    }

    @GetMapping("writeFrm")
    public String writeFrm(){
        log.info("writeFrm()");
        return "writeFrm";
    }

    @PostMapping("writeProc")
    public String writeProc(@RequestPart List<MultipartFile> files,
                            Board board, HttpSession session,
                            RedirectAttributes rttr){
        log.info("writeProc()");
        String view = bServ.insertBoard(files, board, session, rttr);

        return view;

    }
}
