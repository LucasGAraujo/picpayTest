package com.picpaytest.picpaytest.service;

import com.picpaytest.picpaytest.Domain.User.User;
import com.picpaytest.picpaytest.Domain.transaction.Transaction;
import com.picpaytest.picpaytest.dtos.TransactionDTO;
import com.picpaytest.picpaytest.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
@Autowired
    private UserService userService;
@Autowired
    private TransactionRepository repository;
@Autowired
private RestTemplate restTemplate;
@Autowired
private NotificationService notificationService;
public Transaction createTransaction(TransactionDTO transaction) throws Exception{
User sender = this.userService.findUserById(transaction.senderId());
User receiver = this.userService.findUserById(transaction.receiverId());

userService.validateTransaction(sender,transaction.value());

boolean isAuthorized = this.authorizeTransaction(sender,transaction.value());
if(!isAuthorized) {
throw new Exception("Transação não autorizada");
}
Transaction newtransaction = new Transaction();
    newtransaction.setAmount(transaction.value());
    newtransaction.setSender(sender);
    newtransaction.setReceiver(receiver);
    newtransaction.setTimeStant(LocalDateTime.now());
    sender.setBalance(sender.getBalance().subtract(transaction.value()));
    receiver.setBalance(receiver.getBalance().add(transaction.value()));
    this.repository.save(newtransaction);
    this.userService.saveUser(sender);
    this.userService.saveUser(receiver);
    this.notificationService.sendNotification(sender, "TRANSAÇÃO REALIZADA COM SUCESSO ");
    this.notificationService.sendNotification(receiver, "TRANSAÇÃO recebida COM SUCESSO ");

    return  newtransaction;
}
public boolean authorizeTransaction (User user, BigDecimal value) {
    ResponseEntity<Map> authorizationResponse =restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);
if(authorizationResponse.getStatusCode() == HttpStatus.OK )
{
    String message = (String) authorizationResponse.getBody().get("message");
    return "Autorizado".equalsIgnoreCase(message);
}else return false;
}
}
