package com.picpaytest.picpaytest.dtos;

import com.picpaytest.picpaytest.Domain.User.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName , String lastName, String document, BigDecimal balance, String email, String password,
                      UserType userType) {
}
