package com.example.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

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
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();
            return account;
        }
        return null;
    }

    public Account findUserByUsername(String username){
        Optional<Account> accountOptional = accountRepository.findUserByUsername(username);

        if(accountOptional.isPresent()){
            Account account = accountOptional.get();
            return account;
        }
        return null;
    }

    public Account findUserByUsernameAndPassword(String username, String password){
        Optional<Account> accountOptional = accountRepository.findUserByUsernameAndPassword(username, password);

        if(accountOptional.isPresent()){
            Account account = accountOptional.get();
            return account;
        }
        return null;
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
