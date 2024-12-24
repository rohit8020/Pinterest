package com.rohitgangwar.repository;

import com.rohitgangwar.entity.Business;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface BusinessRepository extends CrudRepository<Business, Long> {
    List<Business> findByUserId(Long userId);
}