package com.jsframe.nf_community.service;

import com.jsframe.nf_community.entity.Board;
import com.jsframe.nf_community.entity.BoardFile;
import com.jsframe.nf_community.entity.Member;
import com.jsframe.nf_community.repository.BoardFileRepository;
import com.jsframe.nf_community.repository.BoardRepository;
import com.jsframe.nf_community.repository.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Objects;

@Log
@Service
public class BoardService {
    @Autowired
    private BoardRepository bRepo;

    @Autowired
    private BoardFileRepository bfRepo;

    @Autowired
    private MemberRepository mRepo;


    private ModelAndView mv;

    //게시글 저장 메소드
    @Transactional
    public String insertBoard(List<MultipartFile> files,
                              Board board, HttpSession session,
                              RedirectAttributes rttr) {
        log.info("insertBoard()");
        String msg = null;
        String view = null;
        try {
            Member member = (Member) session.getAttribute("mem");
            member.getMid();
            board.setBid(member);
            bRepo.save(board);
            log.info("bno : " + board.getBno());

            //파일 업로드를 위한 메소드
            fileUpLoad(files, session, board);

            msg = "저장 성공";
            view = "redirect:/";

        } catch (Exception e) {
            e.printStackTrace();
            msg = "저장 실패";
            view = "redirect:writeFrm";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    private void fileUpLoad(List<MultipartFile> files, HttpSession session, Board board)
            throws Exception{
        log.info("fileUpLoad()");
        String realPath = session.getServletContext().getRealPath("/");
        log.info("realPath : " + realPath);
        realPath += "upload/";
        File folder = new File(realPath);
        if(folder.isDirectory() == false){
            folder.mkdir();
        }
        for(MultipartFile mf : files){
            String orname = mf.getOriginalFilename();
            if(orname.equals("")){
                return;
            }

            // upload 폴더에 File 저장
            BoardFile bf = new BoardFile();
            bf.setBfbid(board);
            bf.setBforiname(orname);
            String sysname = System.currentTimeMillis()
                    + orname.substring(orname.lastIndexOf("."));
            bf.setBfsysname(sysname);

            File file = new File(realPath + sysname);
            mf.transferTo(file);

            // DB 에 File 정보를 저장
            bfRepo.save(bf);
        }

    }

    public List<Board> getBoardList(Integer pageNum, HttpSession session) {
        log.info("getBoardList()");
        // 페이지 null 체크
        pageNum = pageNum == null ? 1 : pageNum;

        //페이징 조건 생성
        int listCnt = 5;
        Pageable pb = PageRequest.of((pageNum - 1), listCnt, Sort.Direction.DESC, "bno");
        Page<Board> result = bRepo.findByBnoGreaterThan(0L, pb);
        List<Board> boardList = result.getContent();
        int totalPage = result.getTotalPages();
        log.info("totalPage: " + totalPage);

        return boardList;
    }

}
