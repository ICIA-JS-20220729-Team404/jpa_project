package com.jsframe.nf_community.repository;

import com.jsframe.nf_community.entity.Board;
import com.jsframe.nf_community.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long> {

//    List<Board> findAll();

    //페이지 처리된 목록을 가져오는 메소드
//    Page<Board> findByBnoGreaterThan(long bno, Pageable pageable);
}