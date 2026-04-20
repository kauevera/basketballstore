package com.basketballstore.basketballstore.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.basketballstore.basketballstore.models.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    Optional<PaymentMethod> findByTitle(String title);  
}
