package com.jsframe.nf_community.service;

import com.jsframe.nf_community.entity.Lunch;
import com.jsframe.nf_community.entity.Member;
import com.jsframe.nf_community.repository.LunchRepository;
import com.jsframe.nf_community.repository.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.ExtendedBeanInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Log
@Service
public class LunchService {
    @Autowired
    private LunchRepository lRepo;

    @Autowired
    private MemberRepository mRepo;

    public boolean insertLunch(Lunch lunch, HttpSession session) {
        log.info("insertLunch()");
        boolean result = false;
        try {
            Member member = (Member) session.getAttribute("mem");
            lunch.setLid(member);
            lunch.setLcount(0);
            lRepo.save(lunch);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public List<Lunch> getLunchList() {
        log.info("getLunchList()");
        return lRepo.findAll();
    }

//    public Lunch getLunchPage(Integer pageNum) {
//        log.info("getLunchPage()");
//        if (pageNum == null) {
//            pageNum = 1;
//        }
//        // 페이징 조건 생성
//        int listCnt = 5;
//        Pageable pb = PageRequest.of((pageNum - 1), listCnt, Sort.Direction.DESC, "lno");
//        Page<Lunch> result = lRepo.findByLnoGreaterThan(0L,pb);
//
//        // 페이지 정보 객체 생성
//        Lunch lp = new Lunch();
//        lp.setNumList(listCnt);
//        lp.setCurrentPage(pageNum);
//        lp.setTotalPage(result.getTotalPages());
//        lp.setLunchList(result.getContent());
//
//        return lp;
//    }

    public Lunch getLunch(long lno) {
        log.info("getLunch()");
        try {
            Lunch lunch = lRepo.findById(lno).get();
            lunch.setLcount(lunch.getLcount() + 1);
            lRepo.save(lunch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lRepo.findById(lno).get();
    }
}
