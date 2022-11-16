package com.jsframe.nf_community.repository;

import com.jsframe.nf_community.entity.Lunch;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LunchRepository extends CrudRepository<Lunch, Long> {
    List<Lunch> findAll();

}
