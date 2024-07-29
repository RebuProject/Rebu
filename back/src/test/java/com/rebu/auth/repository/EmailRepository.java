package com.rebu.auth.repository;

import com.rebu.auth.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {
    Optional<Email> findByEmail(String email);
}
