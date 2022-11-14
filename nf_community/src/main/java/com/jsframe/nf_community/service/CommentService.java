package com.jsframe.nf_community.service;

import com.jsframe.nf_community.entity.Comment;
import com.jsframe.nf_community.repository.CommentRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log
@Service
public class CommentService {
    @Autowired
    CommentRepository cRepo;

    public List<Comment> getList() {
        return cRepo.findAll();
    }

    public String insert(Comment c) {
        cRepo.save(c);
        return "{\"result\":true}";
    }

    public String update(Comment c) {
        cRepo.save(c);
        return "{\"result\":true}";
    }

    public String delete(long cno) {
        cRepo.deleteById(cno);
        return "{\"result\":true}";
    }
}
