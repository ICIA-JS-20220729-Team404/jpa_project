package com.jsframe.nf_community.repository;

import com.jsframe.nf_community.entity.Board;
import com.jsframe.nf_community.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long> {
    List<Board> findAll();

    Page<Board> findByBnoGreaterThan(long l ,Pageable pb);
}