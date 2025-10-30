package com.pubgateway.dispatcher.controller;

import com.pubgateway.dispatcher.enumeration.Publisher;
import com.pubgateway.dispatcher.model.Campaign;
import com.pubgateway.dispatcher.service.PublisherService;
import com.pubgateway.dispatcher.service.PublisherServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers/{publisher}/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final PublisherServiceFactory serviceFactory;

    @GetMapping
    public ResponseEntity<List<Campaign>> getCampaigns(
            @PathVariable String publisher,
            @RequestParam(required = false) String accountId) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            List<Campaign> campaigns = service.getCampaigns(accountId);
            return ResponseEntity.ok(campaigns);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaign(
            @PathVariable String publisher,
            @PathVariable String id) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Campaign campaign = service.getCampaign(id);
            return campaign != null ? ResponseEntity.ok(campaign) : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Campaign> createCampaign(
            @PathVariable String publisher,
            @RequestBody Campaign campaign) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Campaign created = service.createCampaign(campaign);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(
            @PathVariable String publisher,
            @PathVariable String id,
            @RequestBody Campaign campaign) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            Campaign updated = service.updateCampaign(id, campaign);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(
            @PathVariable String publisher,
            @PathVariable String id) {
        try {
            Publisher pub = Publisher.valueOf(publisher.toUpperCase());
            PublisherService service = serviceFactory.getService(pub);
            service.deleteCampaign(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

