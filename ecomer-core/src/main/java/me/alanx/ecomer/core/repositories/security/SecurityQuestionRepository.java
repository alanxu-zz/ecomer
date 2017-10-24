package me.alanx.ecomer.core.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.auth.SecurityQuestion;

public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion, Long> {

}
