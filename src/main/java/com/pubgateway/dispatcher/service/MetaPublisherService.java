package com.pubgateway.dispatcher.service;

import com.pubgateway.dispatcher.client.MetaPubGatewayClient;
import com.pubgateway.dispatcher.model.Account;
import com.pubgateway.dispatcher.model.Ads;
import com.pubgateway.dispatcher.model.Campaign;
import com.pubgateway.dispatcher.model.Group;
import com.pubgateway.dispatcher.model.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetaPublisherService implements PublisherService {

    private final MetaPubGatewayClient client;

    @Override
    public List<Account> getAccount(String id) {
        return Collections.singletonList(client.getAccount(id));
    }

    @Override
    public Account createAccount(Account account) {
        account.setPublisher("META");
        return client.createAccount(account);
    }

    @Override
    public Account updateAccount(String id, Account account) {
        account.setPublisher("META");
        return client.updateAccount(id, account);
    }

    @Override
    public void deleteAccount(String id) {
        client.deleteAccount(id);
    }

    @Override
    public List<Campaign> getCampaigns(String accountId) {
        return client.getCampaigns(accountId);
    }

    @Override
    public Campaign getCampaign(String id) {
        return client.getCampaign(id);
    }

    @Override
    public Campaign createCampaign(Campaign campaign) {
        campaign.setPublisher("META");
        return client.createCampaign(campaign);
    }

    @Override
    public Campaign updateCampaign(String id, Campaign campaign) {
        campaign.setPublisher("META");
        return client.updateCampaign(id, campaign);
    }

    @Override
    public void deleteCampaign(String id) {
        client.deleteCampaign(id);
    }

    @Override
    public List<Group> getGroups(String customerId, String campaignId) {
        return client.getGroups(customerId, campaignId);
    }

    @Override
    public Group getGroup(String id) {
        return client.getGroup(id);
    }

    @Override
    public Group createGroup(Group group) {
        group.setPublisher("META");
        return client.createGroup(group);
    }

    @Override
    public Group updateGroup(String id, Group group) {
        group.setPublisher("META");
        return client.updateGroup(id, group);
    }

    @Override
    public void deleteGroup(String id) {
        client.deleteGroup(id);
    }

    @Override
    public List<Ads> getAds(String customerId, String adGroupId) {
        return client.getAds(customerId, adGroupId);
    }

    @Override
    public Ads getAd(String id) {
        return client.getAd(id);
    }

    @Override
    public Ads createAd(Ads ads) {
        ads.setPublisher("META");
        return client.createAd(ads);
    }

    @Override
    public Ads updateAd(String id, Ads ads) {
        ads.setPublisher("META");
        return client.updateAd(id, ads);
    }

    @Override
    public void deleteAd(String id) {
        client.deleteAd(id);
    }

    @Override
    public List<Keyword> getKeywords(String customerId, String adGroupId, String status, String matchType, String textContains) {
        // Meta API doesn't support keywords endpoint yet
        throw new UnsupportedOperationException("Meta PubGateway API does not support keywords endpoint");
    }

    @Override
    public Keyword getKeyword(String id) {
        throw new UnsupportedOperationException("Meta PubGateway API does not support keywords endpoint");
    }

    @Override
    public Keyword createKeyword(Keyword keyword) {
        throw new UnsupportedOperationException("Meta PubGateway API does not support keywords endpoint");
    }

    @Override
    public Keyword updateKeyword(String id, Keyword keyword) {
        throw new UnsupportedOperationException("Meta PubGateway API does not support keywords endpoint");
    }

    @Override
    public void deleteKeyword(String id) {
        throw new UnsupportedOperationException("Meta PubGateway API does not support keywords endpoint");
    }
}

