package com.jsframe.nf_community.controller;

import com.jsframe.nf_community.entity.Tiplink;
import com.jsframe.nf_community.service.TiplinkService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@Log
@RestController
public class TiplinkController {
    @Autowired
    private TiplinkService tServ;

    // 팁 링크 작성
    @PostMapping("/tiplink/write")
    @ResponseBody
    public boolean suggestWrite(Tiplink tiplink, HttpSession session){
        log.info("suggestWrite()");
        if (session.getAttribute("mem") == null) return false;
        tServ.insert(tiplink, session);
        return true;
    }

    // 팁 링크 전체 출력(List)
    @GetMapping("/tiplink/list")
    @ResponseBody
    public List<Tiplink> suggestList(){
        return tServ.getList();
    }
}
