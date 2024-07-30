package com.example.cardapio_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.cardapio_api.domain.user.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,String>{
    
    Optional<UserDetails> findByEmail(String email);
    Optional<User> findUserByEmail(String email);
}
