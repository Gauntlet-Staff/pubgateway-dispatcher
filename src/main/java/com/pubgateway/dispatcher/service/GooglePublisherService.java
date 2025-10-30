package com.pubgateway.dispatcher.service;

import com.pubgateway.dispatcher.client.GooglePubGatewayClient;
import com.pubgateway.dispatcher.model.Account;
import com.pubgateway.dispatcher.model.Ads;
import com.pubgateway.dispatcher.model.Campaign;
import com.pubgateway.dispatcher.model.Group;
import com.pubgateway.dispatcher.model.Keyword;
import com.pubgateway.dispatcher.model.response.AccountsResponse;
import com.pubgateway.dispatcher.model.response.AdGroupResponse;
import com.pubgateway.dispatcher.model.response.AdsResponse;
import com.pubgateway.dispatcher.model.response.CampaignResponse;
import com.pubgateway.dispatcher.model.response.KeywordsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GooglePublisherService implements PublisherService {

    private final GooglePubGatewayClient client;

    @Override
    public List<Account> getAccount(String id) {
        // The Google API doesn't support getting a single account by ID
        // We need to fetch all accounts and filter by ID
        AccountsResponse response = client.getAccounts(id, null, null);
        if (response != null && response.getAccounts() != null) {
            // Set publisher field and filter by ID
            return response.getAccounts().stream()
                    .peek(account -> account.setPublisher("GOOGLE"))
                    .collect(java.util.stream.Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Account createAccount(Account account) {
        throw new UnsupportedOperationException("Google PubGateway API does not support creating accounts via API");
    }

    @Override
    public Account updateAccount(String id, Account account) {
        throw new UnsupportedOperationException("Google PubGateway API does not support updating accounts via API");
    }

    @Override
    public void deleteAccount(String id) {
        throw new UnsupportedOperationException("Google PubGateway API does not support deleting accounts via API");
    }

    @Override
    public List<Campaign> getCampaigns(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            return Collections.emptyList();
        }
        CampaignResponse response = client.getCampaigns(customerId, null, null, null, null, null);
        if (response != null && response.getCampaigns() != null) {
            // Set publisher field for each campaign
            response.getCampaigns().forEach(campaign -> {
                campaign.setPublisher("GOOGLE");
                campaign.setAccountId(customerId);
            });
            return response.getCampaigns();
        }
        return Collections.emptyList();
    }

    @Override
    public Campaign getCampaign(String id) {
        // The Google API doesn't support getting a single campaign by ID directly
        throw new UnsupportedOperationException("Use getCampaigns with customerId to retrieve campaigns");
    }

    @Override
    public Campaign createCampaign(Campaign campaign) {
        throw new UnsupportedOperationException("Google PubGateway API does not support creating campaigns via API");
    }

    @Override
    public Campaign updateCampaign(String id, Campaign campaign) {
        throw new UnsupportedOperationException("Google PubGateway API does not support updating campaigns via API");
    }

    @Override
    public void deleteCampaign(String id) {
        throw new UnsupportedOperationException("Google PubGateway API does not support deleting campaigns via API");
    }

    @Override
    public List<Group> getGroups(String customerId, String campaignId) {
        if (customerId == null || customerId.isEmpty()) {
            return Collections.emptyList();
        }
        AdGroupResponse response = client.getAdGroups(customerId, null, null, campaignId);
        if (response != null && response.getAdGroups() != null) {
            // Set publisher field for each ad group
            response.getAdGroups().forEach(group -> group.setPublisher("GOOGLE"));
            return response.getAdGroups();
        }
        return Collections.emptyList();
    }

    @Override
    public Group getGroup(String id) {
        // The Google API doesn't support getting a single ad group by ID directly
        throw new UnsupportedOperationException("Use getGroups with customerId to retrieve ad groups");
    }

    @Override
    public Group createGroup(Group group) {
        throw new UnsupportedOperationException("Google PubGateway API does not support creating ad groups via API");
    }

    @Override
    public Group updateGroup(String id, Group group) {
        throw new UnsupportedOperationException("Google PubGateway API does not support updating ad groups via API");
    }

    @Override
    public void deleteGroup(String id) {
        throw new UnsupportedOperationException("Google PubGateway API does not support deleting ad groups via API");
    }

    @Override
    public List<Ads> getAds(String customerId, String adGroupId) {
        if (customerId == null || customerId.isEmpty()) {
            return Collections.emptyList();
        }
        AdsResponse response = client.getAds(customerId, adGroupId, null, null);
        if (response != null && response.getAds() != null) {
            // Set publisher field for each ad
            response.getAds().forEach(ad -> ad.setPublisher("GOOGLE"));
            return response.getAds();
        }
        return Collections.emptyList();
    }

    @Override
    public Ads getAd(String id) {
        // The Google API doesn't support getting a single ad by ID directly
        throw new UnsupportedOperationException("Use getAds with customerId to retrieve ads");
    }

    @Override
    public Ads createAd(Ads ads) {
        throw new UnsupportedOperationException("Google PubGateway API does not support creating ads via API");
    }

    @Override
    public Ads updateAd(String id, Ads ads) {
        throw new UnsupportedOperationException("Google PubGateway API does not support updating ads via API");
    }

    @Override
    public void deleteAd(String id) {
        throw new UnsupportedOperationException("Google PubGateway API does not support deleting ads via API");
    }

    @Override
    public List<Keyword> getKeywords(String customerId, String adGroupId, String status, String matchType, String textContains) {
        if (customerId == null || customerId.isEmpty()) {
            return Collections.emptyList();
        }
        KeywordsResponse response = client.getKeywords(customerId, adGroupId, status, matchType, textContains);
        if (response != null && response.getKeywords() != null) {
            // Set publisher field for each keyword
            response.getKeywords().forEach(keyword -> keyword.setPublisher("GOOGLE"));
            return response.getKeywords();
        }
        return Collections.emptyList();
    }

    @Override
    public Keyword getKeyword(String id) {
        // The Google API doesn't support getting a single keyword by ID directly
        throw new UnsupportedOperationException("Use getKeywords with customerId to retrieve keywords");
    }

    @Override
    public Keyword createKeyword(Keyword keyword) {
        throw new UnsupportedOperationException("Google PubGateway API does not support creating keywords via API");
    }

    @Override
    public Keyword updateKeyword(String id, Keyword keyword) {
        throw new UnsupportedOperationException("Google PubGateway API does not support updating keywords via API");
    }

    @Override
    public void deleteKeyword(String id) {
        throw new UnsupportedOperationException("Google PubGateway API does not support deleting keywords via API");
    }
}

