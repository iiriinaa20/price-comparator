package com.accesa.price_comparator.contracts;


import org.springframework.data.jpa.repository.JpaRepository;

import com.accesa.price_comparator.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}