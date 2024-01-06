package com.picpaytest.picpaytest.service;

import com.picpaytest.picpaytest.Domain.User.User;
import com.picpaytest.picpaytest.Domain.User.UserType;
import com.picpaytest.picpaytest.dtos.UserDTO;
import com.picpaytest.picpaytest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
@Autowired
    private UserRepository repository;
        public void validateTransaction(User sender, BigDecimal amount) throws Exception {
    if(sender.getUsertype() == UserType.MERCHANT) {
        throw new Exception("Usuario do tipo logista não esta autorizado");
    }
    if (sender.getBalance().compareTo(amount)< 0){
        throw new Exception("Saldo menor que 0");
    }
        }
    public User findUserById(Long id) throws  Exception{
          return this.repository.findUserById(id).orElseThrow(() -> new Exception("usuario não encontrado"));
    }
    public User createUser(UserDTO data) {
            User newUser = new User(data);
            this.saveUser(newUser);
            return newUser;
    }
    public List<User> getAllUsers(){
            return  this.repository.findAll();
    }
    public void saveUser(User user) {
            this.repository.save(user);
    }
}
