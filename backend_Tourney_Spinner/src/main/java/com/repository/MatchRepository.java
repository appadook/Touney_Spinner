package com.repository;



import com.model.Bracket;
import com.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByBracket(Bracket bracket);
}

