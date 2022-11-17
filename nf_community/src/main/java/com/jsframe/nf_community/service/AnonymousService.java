package com.jsframe.nf_community.service;

import com.jsframe.nf_community.entity.Anonymous;
import com.jsframe.nf_community.entity.Member;
import com.jsframe.nf_community.repository.AnonymousRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Log
@Service
public class AnonymousService {
    @Autowired
    private AnonymousRepository aRepo;

    public boolean insertAnonymous(Anonymous anonymous, HttpSession session) {
        log.info("insertAnonymous()");
        boolean result = false;
        try {
            Member member = (Member) session.getAttribute("mem");
            if(member == null){
                return false;
            }
            anonymous.setAid(member);
            aRepo.save(anonymous);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public List<Anonymous> getAnonymousList() {
        log.info("getAnonymousList()");
        return aRepo.findAll(Sort.by(Sort.Direction.DESC, "ano"));
    }

}
