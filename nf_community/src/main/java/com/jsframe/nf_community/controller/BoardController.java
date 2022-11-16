package com.jsframe.nf_community.controller;

import com.jsframe.nf_community.entity.Board;
import com.jsframe.nf_community.entity.BoardFile;
import com.jsframe.nf_community.entity.BoardPage;
import com.jsframe.nf_community.service.BoardService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@Log
public class BoardController {
    @Autowired
    private BoardService bServ;

    @GetMapping("/board/list")
    @ResponseBody
    public List<Board> getList() {
        return bServ.getBoardList();
    }

    @GetMapping("/board/page")
    @ResponseBody
    public BoardPage getBoardPage(Integer pageNum) {
        BoardPage bp = bServ.getBoardPage(pageNum);
        log.info("bp" + bp.getCurrentPage());
        return bp;
    }

    @PostMapping("/board/write")
    @ResponseBody
    public long writeBoard(Board board, HttpSession session, RedirectAttributes rttr) {
        long result = bServ.insertBoard(board, session);
        return result;
    }

    @PostMapping("/board/file/write")
    @ResponseBody
    public boolean writeBoardFile(@RequestPart List<MultipartFile> files,
                              Board board, HttpSession session) {

        boolean result = bServ.insertFile(files, board, session);
        return result;
    }

    @GetMapping("/board/detail")
    @ResponseBody
    public Board detailBoard(long bno) {

        return bServ.getBoard(bno);
    }

    @GetMapping("/board/delete")
    @ResponseBody
    public boolean deleteBoard(long bno, HttpSession session) {
        return bServ.deleteBoard(bno, session);
    }

    @PostMapping("/board/update")
    @ResponseBody
    public long updateBoard(HttpSession session, Board board){
        long result = bServ.updateBoard(session, board);
        return result;
    }

    @PostMapping("/board/file/update")
    @ResponseBody
    public boolean updateBoardFile(@RequestPart List<MultipartFile> files, long bno, HttpSession session){
        boolean result = bServ.updateFile(files, bno, session);
        return result;
    }

    @GetMapping("/board/file/list")
    @ResponseBody
    public List<BoardFile> getBoardFileList(long bno) {
        return bServ.getFileList(bno);
    }

    @GetMapping("/board/file/download")
    @ResponseBody
    public ResponseEntity<Resource> getBoardFileDownload(long bfnum, HttpSession session) throws IOException {
        BoardFile bf = bServ.getBoardFile(bfnum);
        ResponseEntity<Resource> resp = bServ.fileDownload(bf, session);
        return resp;
    }

    @GetMapping("/board/file/delete")
    @ResponseBody
    public boolean deleteBoardFile(long bfnum) {
        bServ.deleteBoardFile(bfnum);
        return true;
    }
}
