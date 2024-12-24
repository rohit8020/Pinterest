package com.rohitgangwar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rohitgangwar.entity.Pin;

public interface PinRepository extends JpaRepository<Pin, Long> {
    Page<Pin> findAll(Specification<Pin> specification, Pageable pageable);
    List<Pin> findByBoardId(Long boardId);
}
