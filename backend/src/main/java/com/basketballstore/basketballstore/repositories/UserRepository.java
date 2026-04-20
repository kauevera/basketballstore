package com.basketballstore.basketballstore.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.basketballstore.basketballstore.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
