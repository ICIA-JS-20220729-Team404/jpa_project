package com.jsframe.nf_community.repository;

import com.jsframe.nf_community.entity.Anonymous;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnonymousRepository extends CrudRepository<Anonymous, Long> {

    List<Anonymous> findAll(Sort id);
}
