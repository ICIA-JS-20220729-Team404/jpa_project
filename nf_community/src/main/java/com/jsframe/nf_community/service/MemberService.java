package com.jsframe.nf_community.service;

import com.jsframe.nf_community.entity.Member;
import com.jsframe.nf_community.repository.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


@Service
@Log
public class MemberService {
    @Autowired
    private MemberRepository mRepo;

    // 비밀번호 암호화 및 확인 객체
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean joinProc(Member member){
        log.info("joinProc()");
        boolean result = false;

        // 비밀번호 암호화 처리
        String cPwd = encoder.encode(member.getMpw());
        member.setMpw(cPwd); // 암호화된 비밀번호로 변경

        try{
            mRepo.save(member);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
            result = false;
        }
        return result;
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

    public int checkId(String id){
        log.info("checkId");

        return mRepo.countMemberByMid(id);
    }
}
