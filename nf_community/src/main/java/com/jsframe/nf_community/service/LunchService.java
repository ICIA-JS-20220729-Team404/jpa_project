package com.jsframe.nf_community.service;

import com.jsframe.nf_community.entity.Lunch;
import com.jsframe.nf_community.entity.Member;
import com.jsframe.nf_community.repository.LunchRepository;
import com.jsframe.nf_community.repository.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.ExtendedBeanInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//            Member member = (Member)session.getAttribute("mem");
//            member.getMid();
            Member member = mRepo.findMemberByMid("goguma");

            lunch.setLid(member);
            lunch.setLcount(0);
            lRepo.save(lunch);
            result = true;
        }catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public List<Lunch> getLunchList(Lunch lunch, HttpSession session) {
        log.info("getLunchList()");
        return lRepo.findAll();
    }

    public Lunch getLunch(long lno) {
        log.info("getLunch()");
        try{
            Lunch lunch = lRepo.findById(lno).get();
            lunch.setLcount(lunch.getLcount() +1);
            lRepo.save(lunch);
        }catch (Exception e){
            e.printStackTrace();
        }
        return lRepo.findById(lno).get();
    }
}
