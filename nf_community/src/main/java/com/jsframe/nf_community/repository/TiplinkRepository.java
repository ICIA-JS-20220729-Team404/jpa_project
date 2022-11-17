package com.jsframe.nf_community.repository;


import com.jsframe.nf_community.entity.Tiplink;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TiplinkRepository extends CrudRepository<Tiplink, Long> {

    List<Tiplink> findAll(Sort tno);
}
