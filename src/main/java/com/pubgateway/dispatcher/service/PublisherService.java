package com.pubgateway.dispatcher.service;

import com.pubgateway.dispatcher.model.Account;
import com.pubgateway.dispatcher.model.Ads;
import com.pubgateway.dispatcher.model.Campaign;
import com.pubgateway.dispatcher.model.Group;
import com.pubgateway.dispatcher.model.Keyword;

import java.util.List;

public interface PublisherService {
    // Account operations
    List<Account> getAccount(String id);
    Account createAccount(Account account);
    Account updateAccount(String id, Account account);
    void deleteAccount(String id);

    // Campaign operations
    List<Campaign> getCampaigns(String accountId);
    Campaign getCampaign(String id);
    Campaign createCampaign(Campaign campaign);
    Campaign updateCampaign(String id, Campaign campaign);
    void deleteCampaign(String id);

    // Group operations
    List<Group> getGroups(String customerId, String campaignId);
    Group getGroup(String id);
    Group createGroup(Group group);
    Group updateGroup(String id, Group group);
    void deleteGroup(String id);

    // Ads operations
    List<Ads> getAds(String customerId, String adGroupId);
    Ads getAd(String id);
    Ads createAd(Ads ads);
    Ads updateAd(String id, Ads ads);
    void deleteAd(String id);

    // Keyword operations
    List<Keyword> getKeywords(String customerId, String adGroupId, String status, String matchType, String textContains);
    Keyword getKeyword(String id);
    Keyword createKeyword(Keyword keyword);
    Keyword updateKeyword(String id, Keyword keyword);
    void deleteKeyword(String id);
}

