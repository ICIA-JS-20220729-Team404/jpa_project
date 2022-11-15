package com.jsframe.nf_community.repository;

import com.jsframe.nf_community.entity.Board;
import com.jsframe.nf_community.entity.BoardFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardFileRepository extends CrudRepository<BoardFile, Long> {
    List<BoardFile> findByBfbid(Board board);

    @Transactional
    void deleteByBfbid(Board board);
}