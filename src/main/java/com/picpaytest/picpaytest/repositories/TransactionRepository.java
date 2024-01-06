package com.picpaytest.picpaytest.repositories;

import com.picpaytest.picpaytest.Domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
