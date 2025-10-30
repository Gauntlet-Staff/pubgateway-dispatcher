package com.pubgateway.dispatcher.controller;

import com.pubgateway.dispatcher.enumeration.Publisher;
import com.pubgateway.dispatcher.model.Keyword;
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
@RequestMapping("/api/v1/publishers/{publisher}/keywords")
@RequiredArgsConstructor
@Tag(name = "Keywords", description = "Keyword management operations for Google Ads")
public class KeywordController {

    private final PublisherServiceFactory serviceFactory;

    @GetMapping
    @Operation(
        summary = "Get keywords",
        description = "Retrieve keywords (ad group criteria) for a Google Ads account with optional filtering",
        responses = {
            @ApiResponse(responseCode = "200", description = "Keywords retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid publisher or parameters"),
            @ApiResponse(responseCode = "500", description = "Operation not supported by this publisher")
        }
    )
    public ResponseEntity<List<Keyword>> getKeywords(
            @Parameter(description = "Publisher platform (google only)", example = "google")
            @PathVariable String publisher,
            @Parameter(description = "Google Ads customer ID (required)", example = "1234567890", required = true)
            @RequestParam(required = true) String customerId,
            @Parameter(description = "Filter by ad group ID", example = "987654321")
            @RequestParam(required = false) String adGroupId,
            @Parameter(description = "Keyword status filter (ENABLED, PAUSED, REMOVED)", example = "ENABLED")
            @RequestParam(required = false) String status,
            @Parameter(description = "Keyword match type (BROAD, PHRASE, EXACT)", example = "EXACT")
            @RequestParam(required = false) String matchType,
            @Parameter(description = "Filter keywords where text contains this string", example = "shoes")
            @RequestParam(required = false) String textContains) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            List<Keyword> keywords = service.getKeywords(customerId, adGroupId, status, matchType, textContains);
            return ResponseEntity.ok(keywords);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get keyword by ID",
        description = "Get a specific keyword by ID (Note: Not directly supported, use getKeywords instead)",
        responses = {
            @ApiResponse(responseCode = "200", description = "Keyword found"),
            @ApiResponse(responseCode = "404", description = "Keyword not found"),
            @ApiResponse(responseCode = "400", description = "Invalid publisher specified"),
            @ApiResponse(responseCode = "501", description = "Operation not supported")
        }
    )
    public ResponseEntity<Keyword> getKeyword(
            @Parameter(description = "Publisher platform (google only)", example = "google")
            @PathVariable String publisher,
            @Parameter(description = "Keyword ID", example = "111222333")
            @PathVariable String id) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Keyword keyword = service.getKeyword(id);
            return keyword != null ? ResponseEntity.ok(keyword) : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }

    @PostMapping
    @Operation(
        summary = "Create keyword",
        description = "Create a new keyword (Note: Not supported by Google API)",
        responses = {
            @ApiResponse(responseCode = "201", description = "Keyword created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid publisher specified"),
            @ApiResponse(responseCode = "501", description = "Operation not supported by this publisher")
        }
    )
    public ResponseEntity<Keyword> createKeyword(
            @Parameter(description = "Publisher platform", example = "google")
            @PathVariable String publisher,
            @RequestBody Keyword keyword) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Keyword created = service.createKeyword(keyword);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update keyword",
        description = "Update an existing keyword (Note: Not supported by Google API)",
        responses = {
            @ApiResponse(responseCode = "200", description = "Keyword updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid publisher specified"),
            @ApiResponse(responseCode = "501", description = "Operation not supported by this publisher")
        }
    )
    public ResponseEntity<Keyword> updateKeyword(
            @Parameter(description = "Publisher platform", example = "google")
            @PathVariable String publisher,
            @Parameter(description = "Keyword ID", example = "111222333")
            @PathVariable String id,
            @RequestBody Keyword keyword) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Keyword updated = service.updateKeyword(id, keyword);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete keyword",
        description = "Delete a keyword (Note: Not supported by Google API)",
        responses = {
            @ApiResponse(responseCode = "204", description = "Keyword deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid publisher specified"),
            @ApiResponse(responseCode = "501", description = "Operation not supported by this publisher")
        }
    )
    public ResponseEntity<Void> deleteKeyword(
            @Parameter(description = "Publisher platform", example = "google")
            @PathVariable String publisher,
            @Parameter(description = "Keyword ID", example = "111222333")
            @PathVariable String id) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            service.deleteKeyword(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }
}

