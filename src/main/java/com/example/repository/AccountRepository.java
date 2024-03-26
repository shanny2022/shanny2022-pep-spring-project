package com.example.repository;

import com.example.entity.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    @Query(value="SELECT a FROM Account a WHERE a.username = ?1")
    public Optional<Account> findUserByUsername(String username);

    @Query(value="SELECT a FROM Account a WHERE a.username = ?1 AND a.password = ?2")
    public Optional<Account> findUserByUsernameAndPassword(String username, String password);

}
