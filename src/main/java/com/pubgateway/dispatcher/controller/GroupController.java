package com.pubgateway.dispatcher.controller;

import com.pubgateway.dispatcher.enumeration.Publisher;
import com.pubgateway.dispatcher.model.Group;
import com.pubgateway.dispatcher.service.PublisherService;
import com.pubgateway.dispatcher.service.PublisherServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers/{publisher}/groups")
@RequiredArgsConstructor
public class GroupController {

    private final PublisherServiceFactory serviceFactory;

    @GetMapping
    public ResponseEntity<List<Group>> getGroups(
            @PathVariable String publisher,
            @RequestParam(required = true) String accountId,
            @RequestParam(required = false) String campaignId) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            List<Group> groups = service.getGroups(accountId, campaignId);
            return ResponseEntity.ok(groups);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroup(
            @PathVariable String publisher,
            @PathVariable String accountId,
            @PathVariable String id) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Group group = service.getGroup(id);
            return group != null ? ResponseEntity.ok(group) : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(
            @PathVariable String publisher,
            @PathVariable String accountId,
            @RequestBody Group group) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Group created = service.createGroup(group);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(
            @PathVariable String publisher,
            @PathVariable String accountId,
            @PathVariable String id,
            @RequestBody Group group) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Group updated = service.updateGroup(id, group);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(
            @PathVariable String publisher,
            @PathVariable String accountId,
            @PathVariable String id) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            service.deleteGroup(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

