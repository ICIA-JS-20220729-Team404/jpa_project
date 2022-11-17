package com.jsframe.nf_community.service;

import com.jsframe.nf_community.entity.*;
import com.jsframe.nf_community.repository.BoardFileRepository;
import com.jsframe.nf_community.repository.BoardRepository;
import com.jsframe.nf_community.repository.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.prefs.BackingStoreException;

@Log
@Service
public class BoardService {
    @Autowired
    private BoardRepository bRepo;

    @Autowired
    private BoardFileRepository bfRepo;

    public long insertBoard(Board board, HttpSession session) {
        log.info("insertBoard()");
        long result = -1;
        Member member = (Member)session.getAttribute("mem");
        if (member == null) return result;
        try {
            log.info(member.getMid());
            log.info(member.getMname());
            board.setBid(member);
            board.setBcount(0);
            bRepo.save(board);
            result = board.getBno();
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }

    public boolean insertFile(List<MultipartFile> files, long bno, HttpSession session) {
        log.info("insertFile()");
        boolean result = false;
        try {
            Board board = bRepo.findById(bno).get();
            fileUpLoad(files, board, session);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    private void fileUpLoad(List<MultipartFile> files, Board board, HttpSession session) throws Exception{
        log.info("fileUpLoad()");
        String realPath = session.getServletContext().getRealPath("/") + "upload\\";
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
            String sysname = System.currentTimeMillis() + orname.substring(orname.lastIndexOf("."));
            bf.setBfsysname(sysname);

            File file = new File(realPath + sysname);
            mf.transferTo(file);

            // DB 에 File 정보를 저장
            bfRepo.save(bf);
        }
    }

    public Board getBoard(long bno) {
        log.info("getBoard()");
        try{
            Board board = bRepo.findById(bno).get();
            board.setBcount(board.getBcount() +1);
            bRepo.save(board);
        }catch(Exception e){
            e.printStackTrace();
        }
        return bRepo.findById(bno).get();
    }

    private void deleteFile(String filePath) {
        File file = new File(filePath);
        //파일이 있으면 삭제
        if(file.exists()) {
            file.delete();
        }
    }

    public ReturnMsg deleteBoard(long bno, HttpSession session) {
        ReturnMsg rm = new ReturnMsg();
        try {
            Member member = (Member)session.getAttribute("mem");
            Board board = bRepo.findById(bno).get();
            if (member.getMid().equals(board.getBid().getMid()) == false) {
                rm.setFlag(false);
                rm.setMsg("작성자가 아닙니다!");
                return rm;
            }
            List<BoardFile> bfList = bfRepo.findByBfbid(board);
            String realPath = session.getServletContext().getRealPath("/") + "upload\\";
            //파일 삭제
            for(BoardFile bf : bfList){
                deleteFile(realPath + bf.getBfsysname());
            }
            bfRepo.deleteByBfbid(board);
            bRepo.deleteById(bno);
            rm.setFlag(true);
            rm.setMsg("삭제에 성공했습니다!");
        }
        catch (Exception e) {
            rm.setFlag(false);
            rm.setMsg("삭제에 실패했습니다!");
            log.info(e.getMessage());
        }
        return rm;
    }

    public List<Board> getBoardList() {
        log.info("getBoardList()");
        return bRepo.findAll();
    }

    public BoardPage getBoardPage(Integer pageNum) {
        log.info("getBoardPage()");
        // 페이지 null 체크
        pageNum = pageNum == null ? 1 : pageNum;

        // 페이징 조건 생성
        int listCnt = 5;
        Pageable pb = PageRequest.of((pageNum - 1), listCnt, Sort.Direction.DESC, "bno");
        Page<Board> result = bRepo.findByBnoGreaterThan(0L, pb);

        // 페이지 정보 객체 생성
        BoardPage bp = new BoardPage();
        bp.setNumList(listCnt);
        bp.setCurrentPage(pageNum);
        bp.setTotalPage(result.getTotalPages());
        bp.setBoardList(result.getContent());

        return bp;
    }

    public ReturnMsg updateBoard(HttpSession session, Board board) {
        log.info("updateBoard()");
        ReturnMsg rm = new ReturnMsg();
        rm.setCode(-1);
        try {
            Member member = (Member)session.getAttribute("mem");
            Board bd = bRepo.findById(board.getBno()).get();
            if (member.getMid().equals(bd.getBid().getMid()) == false) {
                rm.setCode(-1);
                rm.setMsg("작성자가 아닙니다!");
                return rm;
            }
            bd.setBcontent(board.getBcontent());
            bd.setBdate(Timestamp.valueOf(LocalDateTime.now()));
            bRepo.save(bd);
            rm.setCode(board.getBno());
            rm.setMsg("수정에 성공했습니다!");
        } catch (Exception e) {
            e.printStackTrace();
            rm.setCode(-1);
            rm.setMsg("수정에 실패했습니다!");
        }
        return rm;
    }

    public List<BoardFile> getFileList(long bno) {
        Board board = new Board();
        board.setBno(bno);
        return bfRepo.findByBfbid(board);
    }

    public ResponseEntity<Resource> fileDownload(BoardFile bfile, HttpSession session) throws IOException {
            log.info("fileDownload()");
            //파일 저장 경로 구하기
            String realpath = session.getServletContext().getRealPath("/");
            realpath += "upload\\" + bfile.getBfsysname();
            InputStreamResource fResource = new InputStreamResource(new FileInputStream(realpath));

            //파일명이 한글인 경우의 처리(UTF-8로 인코딩 처리)
            String fileName = URLEncoder.encode(bfile.getBforiname(), "UTF-8");

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .cacheControl(CacheControl.noCache())
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + fileName)
                    .body(fResource);
    }

    public BoardFile getBoardFile(long bfnum) {
        return bfRepo.findById(bfnum).get();
    }

    public void deleteBoardFile(long bfnum) {
        BoardFile bf = bfRepo.findById(bfnum).get();
        deleteFile(bf.getBfsysname());
        bfRepo.deleteById(bfnum);
    }
}
