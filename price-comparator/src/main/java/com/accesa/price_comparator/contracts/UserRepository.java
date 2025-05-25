package com.accesa.price_comparator.contracts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accesa.price_comparator.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}