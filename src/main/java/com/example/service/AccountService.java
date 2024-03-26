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
    public AccountRepository accountRepository;

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
        Account possibleDuplicate = findUserByUsername(account.getUsername());

        if(possibleDuplicate == null && account.getPassword().length()>=4 && !account.getUsername().isEmpty()){
            return accountRepository.save(account);
        }
        else if(account.getPassword().length() < 4 || account.getUsername().isEmpty()){
            System.out.print("Inside empty/short");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 4 characters long");
        }
        else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Password must be at least 4 characters long");
        }
    }

    public Account login(Account account){
        Account logged = findUserByUsernameAndPassword(account.getUsername(), account.getPassword());
        if( logged != null){
            return logged;
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}
