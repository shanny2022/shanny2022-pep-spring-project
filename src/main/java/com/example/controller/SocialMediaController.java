package com.example.controller;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.service.MessageService;
import com.example.service.AccountService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class SocialMediaController {

    private final MessageService messageService;
    private final AccountService accountService;

    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService){
        this.messageService = messageService;
        this.accountService = accountService;
    }


    @GetMapping(path = "/messages")
    public List<Message> getAllMessages(){
        return messageService.findAllMessages();
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> findMessageById(@PathVariable int message_id){
        return ResponseEntity.ok().body(messageService.findMessageById(message_id));
    }

    @DeleteMapping("/messages/{message_id}")
    public Integer deleteMessageByMessageId(@PathVariable int message_id){
        return messageService.deleteMessageByMessageId(message_id);
    }

    @PostMapping(path= "/messages")
    public Message postMessage(@RequestBody Message message){
        return messageService.postMessage(message);
    }

    @GetMapping(path= "/accounts/{account_id}/messages")
    public List<Message> getAllMessagesFromUser(@PathVariable int account_id){
        return messageService.getAllMessagesByPostedBy(account_id);
    }

    @PatchMapping(path= "/messages/{message_id}")
    public Integer editMessageById(@PathVariable int message_id, @RequestBody Message message){
        return messageService.updateMessageById(message_id, message);
    }

    @PostMapping(path= "/register")
    public Account registerUser(@RequestBody Account account){
        return accountService.registerUser(account);
    }

    @PostMapping(path= "/login")
    public Account login(@RequestBody Account account){
        return accountService.login(account);
    }

}
