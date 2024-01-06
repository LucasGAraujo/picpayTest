package com.picpaytest.picpaytest.controller;

import com.picpaytest.picpaytest.Domain.transaction.Transaction;
import com.picpaytest.picpaytest.dtos.TransactionDTO;
import com.picpaytest.picpaytest.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
@Autowired
    private TransactionService transactionService;

@PostMapping
    public ResponseEntity<Transaction> postTransaction(@RequestBody TransactionDTO transaction) throws Exception {
    Transaction newTransaction = this.transactionService.createTransaction(transaction);
    return new ResponseEntity<>(newTransaction, HttpStatus.OK);
}
}
