package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceValidationTest {
    private final MessageRepository repository = mock(MessageRepository.class);
    private final AccountService accounts = mock(AccountService.class);
    private final MessageService service = new MessageService(repository, accounts);

    @Test
    void rejectsMissingBlankOversizedAndUnknownAuthorMessages() {
        Message[] invalid = {null, new Message(1, null, 0L), new Message(1, " \n", 0L),
            new Message(1, "x".repeat(256), 0L), new Message(null, "hello", 0L),
            new Message(123, "hello", 0L)};
        for (Message message : invalid) {
            assertEquals(HttpStatus.BAD_REQUEST, assertThrows(ResponseStatusException.class,
                () -> service.postMessage(message)).getStatus());
        }
        verifyNoInteractions(repository);
    }

    @Test
    void creationAccepts255CharactersAndIgnoresSuppliedId() {
        when(accounts.findUser(1)).thenReturn(new Account(1, "user", "pass"));
        when(repository.save(any(Message.class))).thenAnswer(call -> call.getArgument(0));
        Message input = new Message(9999, 1, "x".repeat(255), 123L);
        Message saved = service.postMessage(input);
        assertNull(saved.getMessage_id());
        assertEquals(input.getMessage_text(), saved.getMessage_text());
        assertEquals(123L, saved.getTime_posted_epoch());
        assertEquals(9999, input.getMessage_id());
    }

    @Test
    void deleteReturnsActualCountOrEmptyResult() {
        when(repository.deleteMessageById(1)).thenReturn(1, 0);
        assertEquals(1, service.deleteMessageByMessageId(1));
        assertNull(service.deleteMessageByMessageId(1));
        verify(repository, times(2)).deleteMessageById(1);
    }

    @Test
    void updateRejectsMissingTextAndConcurrentDeletion() {
        when(repository.findById(1)).thenReturn(java.util.Optional.of(new Message(1, 1, "old", 0L)));
        for (Message invalid : new Message[]{null, new Message(1, null, 0L), new Message(1, " ", 0L)}) {
            assertEquals(HttpStatus.BAD_REQUEST, assertThrows(ResponseStatusException.class,
                () -> service.updateMessageById(1, invalid)).getStatus());
        }
        verify(repository, never()).updateMessageById(anyInt(), anyString());
        when(repository.updateMessageById(1, "new")).thenReturn(0);
        assertEquals(HttpStatus.BAD_REQUEST, assertThrows(ResponseStatusException.class,
            () -> service.updateMessageById(1, new Message(1, "new", 0L))).getStatus());
    }
}
