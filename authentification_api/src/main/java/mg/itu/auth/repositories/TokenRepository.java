package mg.itu.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.itu.auth.models.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    
}