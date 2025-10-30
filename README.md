# PubGateway Dispatcher

A Spring Boot microservice that provides a unified REST API interface for interacting with multiple publisher gateway plugins (Google and Meta). The dispatcher routes requests to the appropriate plugin service based on the publisher type specified in the URL path.

## Architecture

- **Dispatcher Service**: Acts as a unified gateway that routes requests to publisher-specific plugins
- **Eureka Client**: Discovers publisher plugin services registered in Eureka
- **Feign Clients**: Communicates with publisher plugin services via declarative REST clients
- **Service Factory Pattern**: Dynamically routes requests based on publisher type

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Eureka Server running (default: http://localhost:8761/eureka/)
- Google PubGateway Plugin service registered as `google-pubgateway-plugin`
- Meta PubGateway Plugin service registered as `meta-pubgateway-plugin`

## Project Structure

```
src/main/java/com/pubgateway/dispatcher/
├── client/                  # Feign clients for plugin services
│   ├── GooglePubGatewayClient.java
│   └── MetaPubGatewayClient.java
├── controller/              # REST controllers providing unified API
│   ├── AccountController.java
│   ├── CampaignController.java
│   ├── GroupController.java
│   └── AdsController.java
├── model/                   # Domain models
│   ├── Account.java
│   ├── Campaign.java
│   ├── Group.java
│   └── Ads.java
├── service/                 # Service layer with factory pattern
│   ├── PublisherService.java
│   ├── PublisherServiceFactory.java
│   ├── GooglePublisherService.java
│   └── MetaPublisherService.java
├── enumeration/
│   └── Publisher.java
├── exception/
│   └── GlobalExceptionHandler.java
└── DispatcherApplication.java
```

## Configuration

Update `src/main/resources/application.yml` with your Eureka server URL:

```yaml
eureka:
  client:
    service-url:
      defaultZone: http://your-eureka-server:8761/eureka/
```

The dispatcher runs on port **8082** by default.

## API Endpoints

All endpoints follow the pattern: `/api/v1/publishers/{publisher}/{resource}`

### Supported Publishers
- `google` (case-insensitive, maps to GOOGLE)
- `meta` (case-insensitive, maps to META)

### Accounts

- `GET /api/v1/publishers/{publisher}/accounts` - Get all accounts
- `GET /api/v1/publishers/{publisher}/accounts/{id}` - Get account by ID
- `POST /api/v1/publishers/{publisher}/accounts` - Create new account
- `PUT /api/v1/publishers/{publisher}/accounts/{id}` - Update account
- `DELETE /api/v1/publishers/{publisher}/accounts/{id}` - Delete account

### Campaigns

- `GET /api/v1/publishers/{publisher}/campaigns?accountId={accountId}` - Get campaigns (optionally filtered by accountId)
- `GET /api/v1/publishers/{publisher}/campaigns/{id}` - Get campaign by ID
- `POST /api/v1/publishers/{publisher}/campaigns` - Create new campaign
- `PUT /api/v1/publishers/{publisher}/campaigns/{id}` - Update campaign
- `DELETE /api/v1/publishers/{publisher}/campaigns/{id}` - Delete campaign

### Groups

- `GET /api/v1/publishers/{publisher}/groups?campaignId={campaignId}` - Get groups (optionally filtered by campaignId)
- `GET /api/v1/publishers/{publisher}/groups/{id}` - Get group by ID
- `POST /api/v1/publishers/{publisher}/groups` - Create new group
- `PUT /api/v1/publishers/{publisher}/groups/{id}` - Update group
- `DELETE /api/v1/publishers/{publisher}/groups/{id}` - Delete group

### Ads

- `GET /api/v1/publishers/{publisher}/ads?groupId={groupId}` - Get ads (optionally filtered by groupId)
- `GET /api/v1/publishers/{publisher}/ads/{id}` - Get ad by ID
- `POST /api/v1/publishers/{publisher}/ads` - Create new ad
- `PUT /api/v1/publishers/{publisher}/ads/{id}` - Update ad
- `DELETE /api/v1/publishers/{publisher}/ads/{id}` - Delete ad

## Example Usage

### Get all Google accounts
```bash
curl http://localhost:8082/api/v1/publishers/google/accounts
```

### Create a Meta campaign
```bash
curl -X POST http://localhost:8082/api/v1/publishers/meta/campaigns \
  -H "Content-Type: application/json" \
  -d '{
    "accountId": "123",
    "name": "Summer Campaign",
    "status": "ACTIVE",
    "objective": "CONVERSIONS",
    "budget": "1000"
  }'
```

### Get campaigns for a specific account
```bash
curl "http://localhost:8082/api/v1/publishers/google/campaigns?accountId=acc123"
```

## Building and Running

1. **Build the project:**
   ```bash
   mvn clean install
   ```

2. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

3. **Or run the JAR:**
   ```bash
   java -jar target/pubgateway-dispatcher-1.0.0.jar
   ```

## Service Discovery

The dispatcher uses Eureka service discovery to find the plugin services. Make sure:
1. Eureka Server is running
2. `google-pubgateway-plugin` service is registered with Eureka
3. `meta-pubgateway-plugin` service is registered with Eureka

The Feign clients automatically use the service names registered in Eureka to route requests.

## Customization

### Updating API Paths

If your plugin services use different API paths, update the `path` attribute in the Feign client interfaces:
- `GooglePubGatewayClient.java`
- `MetaPubGatewayClient.java`

### Adding New Publishers

1. Add new publisher enum in `Publisher.java`
2. Create new Feign client interface
3. Implement `PublisherService` for the new publisher
4. Update `PublisherServiceFactory` to include the new publisher

## Notes

- The dispatcher automatically sets the `publisher` field in request bodies when creating/updating resources
- Invalid publisher values will return a 400 Bad Request response
- Ensure the plugin services are running and registered with Eureka before making requests

