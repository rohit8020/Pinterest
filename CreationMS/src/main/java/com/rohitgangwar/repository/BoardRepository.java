package com.rohitgangwar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rohitgangwar.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByCreatedBy(Long createdBy);
    @Query("SELECT b FROM Board b WHERE b.id IN :ids")
    List<Board> findAllByIds(@Param("ids") List<Long> ids);
    @Query("SELECT b FROM Board b WHERE LOWER(b.title) = LOWER(:title)")
    Optional<Board> findByTitle(@Param("title") String title);
}

