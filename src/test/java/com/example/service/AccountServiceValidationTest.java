package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceValidationTest {
    private final AccountRepository repository = mock(AccountRepository.class);
    private final AccountService service = new AccountService(repository);

    @Test
    void rejectsInvalidRegistrationWithoutDatabaseWrites() {
        Account[] invalid = {null, new Account(null, "pass"), new Account("user", null),
            new Account(" \t", "pass"), new Account("user", "abc"),
            new Account("x".repeat(256), "pass"), new Account("user", "x".repeat(256))};
        for (Account account : invalid) {
            assertEquals(HttpStatus.BAD_REQUEST, assertThrows(ResponseStatusException.class,
                () -> service.registerUser(account)).getStatus());
        }
        verifyNoInteractions(repository);
    }

    @Test
    void duplicateUsernameReturnsConflict() {
        when(repository.findUserByUsername("user")).thenReturn(Optional.of(new Account(1, "user", "pass")));
        assertEquals(HttpStatus.CONFLICT, assertThrows(ResponseStatusException.class,
            () -> service.registerUser(new Account("user", "pass"))).getStatus());
        verify(repository, never()).save(any());
    }

    @Test
    void registrationIgnoresSuppliedIdAndAcceptsBoundaryLengths() {
        when(repository.findUserByUsername(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(Account.class))).thenAnswer(call -> call.getArgument(0));
        Account input = new Account(9999, "x".repeat(255), "pass");
        Account saved = service.registerUser(input);
        assertNull(saved.getAccount_id());
        assertEquals(input.getUsername(), saved.getUsername());
        assertEquals(9999, input.getAccount_id());
    }

    @Test
    void incompleteLoginReturnsUnauthorized() {
        Account[] invalid = {null, new Account(null, "pass"), new Account("user", null)};
        for (Account account : invalid) {
            assertEquals(HttpStatus.UNAUTHORIZED, assertThrows(ResponseStatusException.class,
                () -> service.login(account)).getStatus());
        }
        verifyNoInteractions(repository);
    }
}
