package com.repository;


import com.model.Bracket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BracketRepository extends JpaRepository<Bracket, Long> {
}
