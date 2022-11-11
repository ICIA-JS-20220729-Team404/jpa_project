package com.jsframe.nf_community.service;

import com.jsframe.nf_community.entity.Board;
import com.jsframe.nf_community.repository.BoardRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Log
@Service
public class BoardService {
    @Autowired
    BoardRepository bRepo;

    public List<Board> getBoardList(){
        log.info("getBoardList()");

        //게시글 가져와서 담기
        List<Board> boardList = bRepo.findAll();
//        mv.addObject("board", board);
//        mv.addObject("bfList", bfList);

        return boardList;
    }

}
