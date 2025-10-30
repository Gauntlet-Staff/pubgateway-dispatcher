package com.pubgateway.dispatcher.client;

import com.pubgateway.dispatcher.model.response.AccountsResponse;
import com.pubgateway.dispatcher.model.response.AdsResponse;
import com.pubgateway.dispatcher.model.response.CampaignResponse;
import com.pubgateway.dispatcher.model.response.AdGroupResponse;
import com.pubgateway.dispatcher.model.response.KeywordsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "google-pubgateway-plugin", path = "/public/api/pubgateway")
public interface GooglePubGatewayClient {

    /**
     * Get all accessible Google Ads accounts
     * @param managerId Optional manager account ID
     * @param authorization OAuth2 Bearer token
     * @param accessToken OAuth2 access token (alternative to Authorization header)
     * @return AccountsResponse containing list of accounts
     */
    @GetMapping("/account")
    AccountsResponse getAccounts(
            @RequestParam(value = "managerId", required = false) String managerId,
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value = "access_token", required = false) String accessToken
    );

    /**
     * Get Google Ads campaigns for a customer
     * @param customerId Google Ads customer ID (required)
     * @param status Campaign status filter (ENABLED, PAUSED, REMOVED)
     * @param nameContains Filter campaigns where name contains this string
     * @param channelType Filter by channel type (SEARCH, DISPLAY, VIDEO, etc.)
     * @param startDateFrom Filter campaigns starting from this date (YYYY-MM-DD)
     * @param startDateTo Filter campaigns starting before this date (YYYY-MM-DD)
     * @return CampaignResponse containing list of campaigns
     */
    @GetMapping("/campaigns")
    CampaignResponse getCampaigns(
            @RequestParam(value = "customerId") String customerId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "nameContains", required = false) String nameContains,
            @RequestParam(value = "channelType", required = false) String channelType,
            @RequestParam(value = "startDateFrom", required = false) String startDateFrom,
            @RequestParam(value = "startDateTo", required = false) String startDateTo
    );

    /**
     * Get Google Ads ad groups for a customer
     * @param customerId Google Ads customer ID (required)
     * @param adGroupName Filter ad groups where name contains this string
     * @param status Ad group status (ENABLED, PAUSED, REMOVED)
     * @param campaignId Filter by parent campaign ID
     * @return AdGroupResponse containing list of ad groups
     */
    @GetMapping("/groups")
    AdGroupResponse getAdGroups(
            @RequestParam(value = "customerId") String customerId,
            @RequestParam(value = "adGroupName", required = false) String adGroupName,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "campaignId", required = false) String campaignId
    );

    /**
     * Get Google Ads ads for a customer
     * @param customerId Google Ads customer ID (required)
     * @param adGroupId Filter by ad group ID
     * @param status Ad status (ENABLED, PAUSED, REMOVED)
     * @param textContains Filter ads where text fields contain this string
     * @return AdsResponse containing list of ads
     */
    @GetMapping("/ads")
    AdsResponse getAds(
            @RequestParam(value = "customerId") String customerId,
            @RequestParam(value = "adGroupId", required = false) String adGroupId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "textContains", required = false) String textContains
    );

    /**
     * Get Google Ads keywords for a customer
     * @param customerId Google Ads customer ID (required)
     * @param adGroupId Filter by ad group ID
     * @param status Keyword status (ENABLED, PAUSED, REMOVED)
     * @param matchType Keyword match type (BROAD, PHRASE, EXACT)
     * @param textContains Filter keywords where text contains this string
     * @return KeywordsResponse containing list of keywords
     */
    @GetMapping("/keywords")
    KeywordsResponse getKeywords(
            @RequestParam(value = "customerId") String customerId,
            @RequestParam(value = "adGroupId", required = false) String adGroupId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "matchType", required = false) String matchType,
            @RequestParam(value = "textContains", required = false) String textContains
    );

    /**
     * Test endpoint
     * @return Hello message
     */
    @GetMapping("/hello")
    String hello();
}

