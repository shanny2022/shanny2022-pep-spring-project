package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.register(account));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.login(account));
    }

    @PostMapping("/messages")
    public ResponseEntity<Object> postMessage(@RequestBody Message message) {
        return ResponseEntity.ok(messageService.postMessage(message));
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
