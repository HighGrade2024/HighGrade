package com.example.highgrade.repository;

import com.example.highgrade.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {
    Optional<Members> findByEmail(String email);
}
