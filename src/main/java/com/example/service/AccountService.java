package com.example.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.List;

@Service
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public List<Account> findAllAccounts(){
        return accountRepository.findAll();
    }

    public Account findUser(int id){
        return accountRepository.findById(id).orElse(null);
    }

    public Account findUserByUsername(String username){
        return accountRepository.findUserByUsername(username).orElse(null);
    }

    public Account findUserByUsernameAndPassword(String username, String password){
        return accountRepository.findUserByUsernameAndPassword(username, password).orElse(null);
    }

    public Account registerUser(Account account){
        if (account == null || account.getUsername() == null || account.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username and password are required");
        }
        if (account.getUsername().length() > 255 || account.getPassword().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credentials must not exceed 255 characters");
        }
        if (account.getPassword().length() < 4 || account.getUsername().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must not be blank and password must have at least 4 characters");
        }
        if (findUserByUsername(account.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        return accountRepository.save(new Account(account.getUsername(), account.getPassword()));
    }

    public Account login(Account account){
        if (account == null || account.getUsername() == null || account.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Account logged = findUserByUsernameAndPassword(account.getUsername(), account.getPassword());
        if( logged != null){
            return logged;
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}
