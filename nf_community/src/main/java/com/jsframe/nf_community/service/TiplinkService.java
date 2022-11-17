package com.jsframe.nf_community.service;

import com.jsframe.nf_community.entity.Member;
import com.jsframe.nf_community.entity.Tiplink;
import com.jsframe.nf_community.repository.TiplinkRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Log
@Service
public class TiplinkService {

    @Autowired
    TiplinkRepository tRepo;

    public boolean insert(Tiplink tiplink, HttpSession session) {
        boolean result = false;
        try {
            Member member = (Member) session.getAttribute("mem");
            if(member == null){
                return false;
            }
            tiplink.setTmid(member);
            tRepo.save(tiplink);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public List<Tiplink> getList() {
        return tRepo.findAll(Sort.by(Sort.Direction.DESC, "tno"));
    }
}
