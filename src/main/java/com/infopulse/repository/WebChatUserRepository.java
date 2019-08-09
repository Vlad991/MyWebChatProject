package com.infopulse.repository;

import com.infopulse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebChatUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

}