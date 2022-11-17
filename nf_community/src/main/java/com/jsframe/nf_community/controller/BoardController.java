package com.jsframe.nf_community.controller;

import com.jsframe.nf_community.entity.Board;
import com.jsframe.nf_community.entity.BoardFile;
import com.jsframe.nf_community.entity.BoardPage;
import com.jsframe.nf_community.entity.ReturnMsg;
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
        return bp;
    }

    @PostMapping("/board/write")
    @ResponseBody
    public ReturnMsg writeBoard(Board board, HttpSession session) {
        ReturnMsg rm = new ReturnMsg();
        if (session.getAttribute("mem") == null) {
            rm.setFlag(false);
            rm.setCode(-1);
            rm.setMsg("로그인이 필요합니다!");
            return rm;
        }
        long code = bServ.insertBoard(board, session);
        rm.setCode(code);
        if (code == -1) {
            rm.setMsg("작성에 실패했습니다!");
        } else {
            rm.setMsg("작성에 성공했습니다!");
        }
        return rm;
    }

    @PostMapping("/board/file/write")
    @ResponseBody
    public ReturnMsg writeBoardFile(@RequestPart List<MultipartFile> files, long bno, HttpSession session) {
        ReturnMsg rm = new ReturnMsg();
        if (session.getAttribute("mem") == null) {
            rm.setFlag(false);
            rm.setCode(-1);
            rm.setMsg("로그인이 필요합니다!");
            return rm;
        }
        boolean flag = bServ.insertFile(files, bno, session);
        rm.setFlag(flag);
        if (flag == false) {
            rm.setMsg("업로드에 실패했습니다!");
        } else {
            rm.setMsg("업로드에 성공했습니다!");
        }
        return rm;
    }

    @GetMapping("/board/detail")
    @ResponseBody
    public Board detailBoard(long bno) {
        return bServ.getBoard(bno);
    }

    @GetMapping("/board/delete")
    @ResponseBody
    public ReturnMsg deleteBoard(long bno, HttpSession session) {
        ReturnMsg rm = new ReturnMsg();
        if (session.getAttribute("mem") == null) {
            rm.setFlag(false);
            rm.setCode(-1);
            rm.setMsg("로그인이 필요합니다!");
            return rm;
        }
        rm = bServ.deleteBoard(bno, session);
        return rm;
    }

    @PostMapping("/board/update")
    @ResponseBody
    public ReturnMsg updateBoard(HttpSession session, Board board) {
        ReturnMsg rm = new ReturnMsg();
        if (session.getAttribute("mem") == null) {
            rm.setFlag(false);
            rm.setCode(-1);
            rm.setMsg("로그인이 필요합니다!");
            return rm;
        }
        rm = bServ.updateBoard(session, board);
        return rm;
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
    public ReturnMsg deleteBoardFile(long bfnum, HttpSession session) {
        ReturnMsg rm = new ReturnMsg();
        if (session.getAttribute("mem") == null) {
            rm.setFlag(false);
            rm.setCode(-1);
            rm.setMsg("로그인이 필요합니다!");
            return rm;
        }
        bServ.deleteBoardFile(bfnum);
        rm.setMsg("삭제에 성공했습니다!");
        return rm;
    }
}
