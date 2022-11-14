package com.jsframe.nf_community.service;

import com.jsframe.nf_community.entity.Member;
import com.jsframe.nf_community.repository.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Service
@Log
public class MemberService {

    @Autowired
    private MemberRepository mRepo;

    // 비밀번호 암호화 및 확인 객체
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String joinProc(Member member, RedirectAttributes rttr){
        log.info("joinProc()");
        String view = null;
        String msg = null;

        // 비밀번호 암호화 처리
        String cPwd = encoder.encode(member.getMpw());
        member.setMpw(cPwd); // 암호화된 비밀번호로 변경

        try{
            mRepo.save(member);
            msg = "가입 성공";
            view = "redirect:loginFrm";
        }catch (Exception e){
            e.printStackTrace();
            msg = "가입 실패";
            view = "redirect:joinFrm";
        }
        rttr.addFlashAttribute("msg" , msg);

        return view;
    }

    public String loginProc(Member member, HttpSession session, RedirectAttributes rttr) {
        log.info("loginProc");

        String view = null;
        String msg = null;

        String cPwd = mRepo.findMemberByMid(member.getMid()).getMpw();

        if (cPwd != null) {//입력한 회원의 아이디가 있음
            if (encoder.matches(member.getMpw(), cPwd)) {
                member = mRepo.findMemberByMid(member.getMid());
                // 세션에 로그인 성공 정보 저장
                session.setAttribute("mem", member);
                msg = "로그인 성공";
                view = "redirect:/";//-------- 로그인 후 화면
            } else {// 비밀번호가 맞지 않는 경우
                msg = "비밀번호가 올바르지 않습니다.";
                view = "redirect:loginFrm";
            }
        } else {//잘못된 아이디 입력
            msg = "해당 아이디가 없습니다";
            view = "redirect:loginFrm";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    public boolean loginProc(Member member, HttpSession session) {
        log.info("loginProc");
        boolean result = false;
        log.info(member.getMid());

        Member m = mRepo.findMemberByMid(member.getMid());
        if (m != null) {//입력한 회원의 아이디가 있음
            String cPwd = m.getMpw();
            if (encoder.matches(member.getMpw(), cPwd)) {
                member = mRepo.findMemberByMid(member.getMid());
                // 세션에 로그인 성공 정보 저장
                session.setAttribute("mem", member);
                result = true;
            } else {// 비밀번호가 맞지 않는 경우

            }
        } else {//잘못된 아이디 입력
            result = false;
        }
        return result;
    }
}

