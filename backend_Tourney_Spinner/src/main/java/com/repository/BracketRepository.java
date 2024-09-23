package com.repository;


import com.model.Bracket;
import com.model.Tournament;
import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;

public interface BracketRepository extends JpaRepository<Bracket, Long> {
    List<Bracket> findByTournament(Tournament tournament);
}
