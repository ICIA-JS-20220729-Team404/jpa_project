package com.jsframe.nf_community.repository;

import com.jsframe.nf_community.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findMemberByMid(String mid) ;

    int countMemberByMid(String mid);
}
