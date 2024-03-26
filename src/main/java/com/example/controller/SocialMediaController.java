package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SocialMediaController {

    @Autowired
    public AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        Account account = accountService.login(username, password);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

@PostMapping("/register")
public ResponseEntity<Account> register(@RequestBody Account account) {
    Account registeredAccount = accountService.register(account);
    if (registeredAccount != null) {
        return ResponseEntity.ok(registeredAccount);
    } else {
        return ResponseEntity.badRequest().build();
    }
}

@Autowired
public MessageService messageService;


    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        Message postedMessage = messageService.postMessage(message);
        if (postedMessage != null) {
            return ResponseEntity.ok(postedMessage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
        return ResponseEntity.ok(messageService.getMessageById(message_id));
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int message_id) {
        messageService.deleteMessage(message_id);
        return ResponseEntity.ok(message_id);
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Object> updateMessage(@PathVariable int message_id, @RequestBody Message message) {
        return ResponseEntity.ok(messageService.updateMessage(message_id, message));
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<Object> getMessagesByUser(@PathVariable int account_id) {
        return ResponseEntity.ok(messageService.getMessagesByUser(account_id));
    }
}
