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

    public boolean insertLunch(Lunch lunch, HttpSession session) {
        log.info("insertLunch()");
        boolean result = false;
        try {
            Member member = (Member) session.getAttribute("mem");
            if(member == null){
                return false;
            }
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
        return lRepo.findAll(Sort.by(Sort.Direction.DESC, "lno"));
    }

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
