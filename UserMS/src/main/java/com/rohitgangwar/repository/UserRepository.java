package com.rohitgangwar.repository;

import com.rohitgangwar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.id IN :ids")
    List<User> findAllByIds(@Param("ids") List<Long> ids);
    Optional<User> findById(Long id);
}
