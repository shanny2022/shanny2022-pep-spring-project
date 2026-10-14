package com.example.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountService accountService;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService){
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

    public List<Message> findAllMessages(){
        return messageRepository.findAll();
    }

    public Message postMessage(Message message){
        if(message == null || message.getMessage_text() == null
        || message.getMessage_text().isBlank()
        || message.getMessage_text().length() > 255
        || message.getPosted_by() == null
        || accountService.findUser(message.getPosted_by()) == null
        ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Message");
        }else{
            return messageRepository.save(new Message(message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch()));
        }
    }

    public Message findMessageById(int Id) {
        return messageRepository.findById(Id).orElse(null);
    }

    public Integer deleteMessageByMessageId(int Id){
        Optional<Message> messageOptional = messageRepository.findById(Id);
        if(messageOptional.isPresent()){
            messageRepository.deleteById(Id);
            return 1; /* I should make custom sql in repository to return an int (which would give number of updated rows)*/
        }
        return null;
    }

    public List<Message> getAllMessagesByPostedBy(int id){
        return messageRepository.findAllMessagesByPostedBy(id);
    }

    public Integer updateMessageById(int id, Message message){
        Message oldMessage = findMessageById(id);
        if(oldMessage == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Message");
        }

        if(message == null || message.getMessage_text() == null
        || message.getMessage_text().isBlank()
        || message.getMessage_text().length() > 255
        ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Message");
        }else{
            messageRepository.updateMessageById(id, message.getMessage_text());
            return 1;
        }
    }

}
