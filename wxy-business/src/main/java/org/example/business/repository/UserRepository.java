package org.example.business.repository;

import org.example.business.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    
    Optional<User> findByUsername(String username);
    
    boolean existsByUsername(String username);
    
    Optional<User> findByEmail(String email);
}
