package com.pubgateway.dispatcher.controller;

import com.pubgateway.dispatcher.enumeration.Publisher;
import com.pubgateway.dispatcher.model.Account;
import com.pubgateway.dispatcher.service.PublisherService;
import com.pubgateway.dispatcher.service.PublisherServiceFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers/{publisher}/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Account management operations")
public class AccountController {

    private final PublisherServiceFactory serviceFactory;

    @GetMapping("/{id}")
    @Operation(
        summary = "Get account by ID",
        description = "Retrieve a specific account by its ID for the specified publisher platform",
        responses = {
            @ApiResponse(responseCode = "200", description = "Account found"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "400", description = "Invalid publisher specified")
        }
    )
    public ResponseEntity<List<Account>> getAccount(
            @Parameter(description = "Publisher platform (google, meta)", example = "google")
            @PathVariable String publisher,
            @Parameter(description = "Account ID", example = "1234567890")
            @PathVariable String id) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            List<Account> accounts = service.getAccount(id);
            return accounts != null ? ResponseEntity.ok(accounts) : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @Operation(
        summary = "Create account",
        description = "Create a new account (Note: Not supported by Google API)",
        responses = {
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid publisher specified"),
            @ApiResponse(responseCode = "500", description = "Operation not supported by this publisher")
        }
    )
    public ResponseEntity<Account> createAccount(
            @Parameter(description = "Publisher platform (google, meta)", example = "meta")
            @PathVariable String publisher,
            @RequestBody Account account) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Account created = service.createAccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update account",
        description = "Update an existing account (Note: Not supported by Google API)",
        responses = {
            @ApiResponse(responseCode = "200", description = "Account updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid publisher specified"),
            @ApiResponse(responseCode = "500", description = "Operation not supported by this publisher")
        }
    )
    public ResponseEntity<Account> updateAccount(
            @Parameter(description = "Publisher platform (google, meta)", example = "meta")
            @PathVariable String publisher,
            @Parameter(description = "Account ID", example = "1234567890")
            @PathVariable String id,
            @RequestBody Account account) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Account updated = service.updateAccount(id, account);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete account",
        description = "Delete an account (Note: Not supported by Google API)",
        responses = {
            @ApiResponse(responseCode = "204", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid publisher specified"),
            @ApiResponse(responseCode = "500", description = "Operation not supported by this publisher")
        }
    )
    public ResponseEntity<Void> deleteAccount(
            @Parameter(description = "Publisher platform (google, meta)", example = "meta")
            @PathVariable String publisher,
            @Parameter(description = "Account ID", example = "1234567890")
            @PathVariable String id) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            service.deleteAccount(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

