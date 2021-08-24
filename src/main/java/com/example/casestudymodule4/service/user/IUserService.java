package com.example.casestudymodule4.service.user;


import com.example.casestudymodule4.model.entity.User;
import com.example.casestudymodule4.service.IGeneralService;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    Optional<User> findByUserName(String username);

    Optional<User> findByFullName(String fullName);

    Iterable<User> findAllByFullNameContaining(String fullName);
}
