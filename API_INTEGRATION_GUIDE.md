# Google PubGateway API Integration Guide

## Summary of Changes

I've updated the dispatcher service to correctly integrate with the actual Google PubGateway Plugin API endpoints.

### What Changed

1. **Updated API Path**: Changed from `/api` to `/public/api/pubgateway`
2. **Updated Response Models**: Created wrapper classes (`AccountsResponse`, `CampaignResponse`, `AdGroupResponse`, `AdsResponse`)
3. **Updated Request Parameters**: Changed from path parameters to query parameters with `customerId`
4. **Read-Only API**: The Google API only supports GET operations (POST/PUT/DELETE will throw `UnsupportedOperationException`)
5. **Updated Models**: Enhanced `Account`, `Campaign`, `Group`, and `Ads` models to match the actual API response structure

### Files Modified

- ✅ `GooglePubGatewayClient.java` - Updated to match actual API endpoints
- ✅ `GooglePublisherService.java` - Adapted to work with new client interface
- ✅ `Account.java`, `Campaign.java`, `Group.java`, `Ads.java` - Enhanced with additional fields
- ✅ Created response wrappers in `model/response/` package
- ✅ Created `TestController.java` for testing

## API Endpoints Reference

Based on the [official API documentation](http://ec2-54-226-128-157.compute-1.amazonaws.com:8080/v3/api-docs):

### 1. Get Accounts
```http
GET /api/v1/publishers/google/accounts
```

**Description**: Retrieves all accessible Google Ads accounts

**Example**:
```bash
curl http://localhost:8082/api/v1/publishers/google/accounts
```

### 2. Get Campaigns
```http
GET /api/v1/publishers/google/campaigns?accountId={customerId}
```

**Description**: Retrieves campaigns for a specific Google Ads customer

**Parameters**:
- `accountId` (required): Google Ads customer ID

**Example**:
```bash
curl "http://localhost:8082/api/v1/publishers/google/campaigns?accountId=1234567890"
```

### 3. Get Ad Groups
```http
GET /api/v1/publishers/google/groups?campaignId={customerId}
```

**Description**: Retrieves ad groups for a specific Google Ads customer

**Parameters**:
- `campaignId` (required): Google Ads customer ID (note: despite the name, this is the customer ID)

**Example**:
```bash
curl "http://localhost:8082/api/v1/publishers/google/groups?campaignId=1234567890"
```

### 4. Get Ads
```http
GET /api/v1/publishers/google/ads?groupId={customerId}
```

**Description**: Retrieves ads for a specific Google Ads customer

**Parameters**:
- `groupId` (required): Google Ads customer ID (note: despite the name, this is the customer ID)

**Example**:
```bash
curl "http://localhost:8082/api/v1/publishers/google/ads?groupId=1234567890"
```

### 5. Test Endpoint
```http
GET /api/v1/test/google/hello
```

**Description**: Simple hello endpoint to test connectivity

**Example**:
```bash
curl http://localhost:8082/api/v1/test/google/hello
```

## Testing the Integration

### 1. Restart the Dispatcher Service

After making these changes, restart the service:

```bash
# Kill the current process
kill <PID>

# Restart
cd /Users/mwu/Documents/git/github/pubgateway-dispatcher
mvn spring-boot:run
```

### 2. Verify Eureka Connection

Check that the dispatcher is connected to Eureka:

```bash
curl http://localhost:8082/actuator/health | jq '.components.discoveryComposite.components.eureka'
```

You should see services registered.

### 3. Test the Hello Endpoint

```bash
curl http://localhost:8082/api/v1/test/google/hello
```

Expected response: `"Hello from PubGateway!"` (or similar)

### 4. Test Getting Accounts

```bash
curl http://localhost:8082/api/v1/publishers/google/accounts | jq
```

Expected response: A list of Google Ads accounts with their details.

### 5. Test Getting Campaigns

```bash
# Replace 1234567890 with an actual customer ID from step 4
curl "http://localhost:8082/api/v1/publishers/google/campaigns?accountId=1234567890" | jq
```

## Important Notes

### Read-Only API

The Google PubGateway API is **read-only**. The following operations will throw `UnsupportedOperationException`:

- ❌ `POST /api/v1/publishers/google/accounts` - Creating accounts
- ❌ `PUT /api/v1/publishers/google/accounts/{id}` - Updating accounts
- ❌ `DELETE /api/v1/publishers/google/accounts/{id}` - Deleting accounts
- ❌ Same for campaigns, groups, and ads

### Customer ID Required

Most endpoints require a `customerId` parameter (Google Ads customer ID). This is passed as:
- `accountId` for campaigns
- `campaignId` for ad groups (despite the name)
- `groupId` for ads (despite the name)

### Response Structure

All responses from the Google API are wrapped:
- Accounts: `{ "accounts": [...] }`
- Campaigns: `{ "campaigns": [...] }`
- Ad Groups: `{ "adGroups": [...] }`
- Ads: `{ "ads": [...] }`

The dispatcher automatically unwraps these for you.

## Troubleshooting

### 503 Service Unavailable Error

If you get a 503 error, check:
1. Eureka connection: `curl http://localhost:8082/actuator/health`
2. Google plugin is registered: `curl http://ec2-54-226-128-157.compute-1.amazonaws.com:8761/eureka/apps`

### 404 Not Found Error

If you get a 404 error from the Google plugin:
- ✅ The dispatcher is working correctly
- ✅ The request reached the Google plugin
- ❌ The resource doesn't exist (check your customer ID)

### UnsupportedOperationException

If you get this error, you're trying to use a POST/PUT/DELETE operation which the Google API doesn't support.

## Reference

- Google PubGateway API Docs: http://ec2-54-226-128-157.compute-1.amazonaws.com:8080/v3/api-docs
- Eureka Server: http://ec2-54-226-128-157.compute-1.amazonaws.com:8761/
- Dispatcher Health: http://localhost:8082/actuator/health

