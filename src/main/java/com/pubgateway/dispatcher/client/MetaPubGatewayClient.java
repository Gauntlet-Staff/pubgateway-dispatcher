package com.pubgateway.dispatcher.client;

import com.pubgateway.dispatcher.model.Account;
import com.pubgateway.dispatcher.model.Ads;
import com.pubgateway.dispatcher.model.Campaign;
import com.pubgateway.dispatcher.model.Group;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "meta-pubgateway-plugin", path = "/api")
public interface MetaPubGatewayClient {

    @GetMapping("/accounts/{id}")
    Account getAccount(@PathVariable("id") String id);

    @PostMapping("/accounts")
    Account createAccount(@RequestBody Account account);

    @PutMapping("/accounts/{id}")
    Account updateAccount(@PathVariable("id") String id, @RequestBody Account account);

    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable("id") String id);

    // Campaign endpoints
    @GetMapping("/campaigns")
    List<Campaign> getCampaigns(@RequestParam(value = "accountId", required = false) String accountId);

    @GetMapping("/campaigns/{id}")
    Campaign getCampaign(@PathVariable("id") String id);

    @PostMapping("/campaigns")
    Campaign createCampaign(@RequestBody Campaign campaign);

    @PutMapping("/campaigns/{id}")
    Campaign updateCampaign(@PathVariable("id") String id, @RequestBody Campaign campaign);

    @DeleteMapping("/campaigns/{id}")
    void deleteCampaign(@PathVariable("id") String id);

    // Group endpoints
    @GetMapping("/groups")
    List<Group> getGroups(@RequestParam(value = "customerId", required = false) String customerId, @RequestParam(value = "campaignId", required = false) String campaignId);

    @GetMapping("/groups/{id}")
    Group getGroup(@PathVariable("id") String id);

    @PostMapping("/groups")
    Group createGroup(@RequestBody Group group);

    @PutMapping("/groups/{id}")
    Group updateGroup(@PathVariable("id") String id, @RequestBody Group group);

    @DeleteMapping("/groups/{id}")
    void deleteGroup(@PathVariable("id") String id);

    // Ads endpoints
    @GetMapping("/ads")
    List<Ads> getAds(@RequestParam(value = "customerId", required = false) String customerId, @RequestParam(value = "adGroupId", required = false) String adGroupId);

    @GetMapping("/ads/{id}")
    Ads getAd(@PathVariable("id") String id);

    @PostMapping("/ads")
    Ads createAd(@RequestBody Ads ads);

    @PutMapping("/ads/{id}")
    Ads updateAd(@PathVariable("id") String id, @RequestBody Ads ads);

    @DeleteMapping("/ads/{id}")
    void deleteAd(@PathVariable("id") String id);
}

