package com.basketballstore.basketballstore.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.basketballstore.basketballstore.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
}
