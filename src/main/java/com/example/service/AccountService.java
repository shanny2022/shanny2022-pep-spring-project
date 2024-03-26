package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElse(null);
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public Account register(Account account) {
        // Implement the method to register an account
        // For now, it just saves the account
        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        // Implement the method to login
        // For now, it just returns the first account
        return accountRepository.findAll().stream().findFirst().orElse(null);
    }

    public Object getMessagesByUser(int account_id) {
        return null;
    }
}
