package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id) {
        return messageRepository.findById(id).orElse(null);
    }

    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }

    public Message postMessage(Message message) {
        // Implement the method to post a message
        // For now, it just saves the message
        return messageRepository.save(message);
    }

    public Message updateMessage(int message_id, Message message) {
        // Implement the method to update a message
        // For now, it just checks if the message exists and then saves the new message
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if (optionalMessage.isPresent()) {
            return messageRepository.save(message);
        } else {
            return null;
        }
    }

    public List<Message> getMessagesByUser(int account_id) {
        // Implement the method to get messages by user
        // For now, it just returns all messages
        return messageRepository.findAll();
    }
}
