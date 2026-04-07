package auth_service.repository;

import auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}

/*
Optional<User> findByEmail(String email)

Gera SELECT * FROM user WHERE email = ? e retorna o primeiro resultado (ou empty).

boolean existsByEmail(String email)

Gera SELECT COUNT(*) > 0 FROM user WHERE email = ?, retorna true/false sem carregar a entidade (mais eficiente)
 */