package com.jsframe.nf_community.controller;

import com.jsframe.nf_community.entity.Comment;
import com.jsframe.nf_community.entity.Member;
import com.jsframe.nf_community.service.CommentService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@Log
@RestController
public class CommentController {
    @Autowired
    CommentService cServ;

    @GetMapping("/comment/list")
    public List<Comment> getList() {
        return cServ.getList();
    }

    @PostMapping("/comment/write")
    public String insert(Comment c, @RequestParam("writer") String writer, HttpSession session) {
        log.info("/comment/write");
//        Member member = (Member)session.getAttribute("mem");
        Member member = new Member();
        member.setMid(writer);
        c.setCid(member);

        return cServ.insert(c);
    }

    @PostMapping("/comment/update")
    public String update(Comment c) {
        return cServ.update(c);
    }

    @GetMapping("/comment/delete")
    public String delete(long cno) {
        return cServ.delete(cno);
    }
}
