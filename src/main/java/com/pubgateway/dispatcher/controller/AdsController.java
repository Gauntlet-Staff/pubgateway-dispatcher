package com.pubgateway.dispatcher.controller;

import com.pubgateway.dispatcher.enumeration.Publisher;
import com.pubgateway.dispatcher.model.Ads;
import com.pubgateway.dispatcher.service.PublisherService;
import com.pubgateway.dispatcher.service.PublisherServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers/{publisher}/ads")
@RequiredArgsConstructor
public class AdsController {

    private final PublisherServiceFactory serviceFactory;

    @GetMapping
    public ResponseEntity<List<Ads>> getAds(
            @PathVariable String publisher,
            @RequestParam(required = true) String accountId,
            @RequestParam(required = false) String groupId) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            List<Ads> ads = service.getAds(accountId, groupId);
            return ResponseEntity.ok(ads);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ads> getAd(
            @PathVariable String publisher,
            @PathVariable String accountId,
            @PathVariable String groupId,
            @PathVariable String id) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Ads ads = service.getAd(id);
            return ads != null ? ResponseEntity.ok(ads) : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Ads> createAd(
            @PathVariable String publisher,
            @PathVariable String accountId,
            @PathVariable String groupId,
            @RequestBody Ads ads) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Ads created = service.createAd(ads);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ads> updateAd(
            @PathVariable String publisher,
            @PathVariable String accountId,
            @PathVariable String groupId,
            @PathVariable String id,
            @RequestBody Ads ads) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Ads updated = service.updateAd(id, ads);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(
            @PathVariable String publisher,
            @PathVariable String accountId,
            @PathVariable String groupId,
            @PathVariable String id) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            service.deleteAd(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

