# PubGateway Dispatcher - Deployment Guide

## 📦 Building the Deployment Package

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build Steps

1. **Navigate to project directory:**
```bash
cd /Users/mwu/Documents/git/github/pubgateway-dispatcher
```

2. **Clean and build the project:**
```bash
mvn clean package
```

This will create:
- `target/pubgateway-dispatcher-1.0.0.jar` - Executable JAR
- `target/pubgateway-dispatcher-1.0.0.tar.gz` - Deployment package

### What's in the tar.gz?

```
pubgateway-dispatcher-1.0.0/
├── bin/
│   ├── start.sh         # Start the service
│   ├── stop.sh          # Stop the service
│   ├── restart.sh       # Restart the service
│   └── status.sh        # Check service status
├── config/
│   └── application.yml  # Configuration file
├── lib/
│   └── pubgateway-dispatcher-1.0.0.jar
├── logs/                # Log files (created at runtime)
└── docs/                # Documentation
```

## 🚀 Deployment

### 1. Upload to Server

```bash
scp target/pubgateway-dispatcher-1.0.0.tar.gz user@your-server:/opt/
```

### 2. Extract

```bash
cd /opt
tar -xzf pubgateway-dispatcher-1.0.0.tar.gz
cd pubgateway-dispatcher-1.0.0
```

### 3. Configure (Optional)

Edit the configuration file if needed:
```bash
vi config/application.yml
```

Key configurations to check:
- `server.port` - Default is 8082
- `eureka.client.service-url.defaultZone` - Eureka server URL
- `logging.level` - Logging levels

### 4. Start the Service

**Basic startup:**
```bash
./bin/start.sh
```

**With custom JVM settings:**
```bash
JVM_XMX=1024m JVM_XMS=512m ./bin/start.sh
```

**With custom port:**
```bash
SERVER_PORT=8090 ./bin/start.sh
```

**With custom profile:**
```bash
SPRING_PROFILES_ACTIVE=production ./bin/start.sh
```

### 5. Check Status

```bash
./bin/status.sh
```

### 6. View Logs

```bash
tail -f logs/console.log
```

## 🛠️ Management Commands

### Start Service
```bash
./bin/start.sh
```

### Stop Service
```bash
./bin/stop.sh
```

### Restart Service
```bash
./bin/restart.sh
```

### Check Status
```bash
./bin/status.sh
```

## ⚙️ Environment Variables

You can override default settings using environment variables:

| Variable | Description | Default |
|----------|-------------|---------|
| `JVM_XMX` | Maximum heap size | 512m |
| `JVM_XMS` | Initial heap size | 256m |
| `JVM_OPTS` | Additional JVM options | -server -XX:+UseG1GC |
| `SPRING_PROFILES_ACTIVE` | Spring profile | prod |
| `SERVER_PORT` | Server port | 8082 |

### Example with Custom Settings:

```bash
# Set environment variables
export JVM_XMX=1024m
export JVM_XMS=512m
export SERVER_PORT=8090
export SPRING_PROFILES_ACTIVE=production

# Start service
./bin/start.sh
```

## 🔧 Manual Start (Alternative)

If you prefer to run the service manually like your example:

```bash
cd /opt/pubgateway-dispatcher-1.0.0

nohup java \
  -Xmx512m \
  -Xms256m \
  -Dspring.profiles.active=prod \
  -Dspring.config.location=file:./config/ \
  -Dserver.port=8082 \
  -jar lib/pubgateway-dispatcher-1.0.0.jar \
  > logs/console.log 2>&1 &

# Save PID for later
echo $! > bin/pubgateway-dispatcher.pid
```

## 📊 Health Check

### Using curl:
```bash
curl http://localhost:8082/actuator/health
```

### Using status script:
```bash
./bin/status.sh
```

### Expected Response:
```json
{
  "status": "UP",
  "components": {
    "discoveryComposite": {
      "status": "UP",
      "components": {
        "eureka": {
          "status": "UP",
          "details": {
            "applications": {
              "GOOGLE-PUBGATEWAY-PLUGIN": 1
            }
          }
        }
      }
    }
  }
}
```

## 🌐 Access Points

After deployment, you can access:

- **Swagger UI**: http://your-server:8082/swagger-ui.html
- **API Docs**: http://your-server:8082/v3/api-docs
- **Health Check**: http://your-server:8082/actuator/health
- **Metrics**: http://your-server:8082/actuator/metrics

## 🔥 Troubleshooting

### Service won't start

1. **Check if port is already in use:**
```bash
lsof -i :8082
```

2. **Check Java version:**
```bash
java -version
# Should be 17 or higher
```

3. **Check logs:**
```bash
cat logs/console.log
```

### Service is running but not responding

1. **Check if Eureka is accessible:**
```bash
curl http://ec2-54-226-128-157.compute-1.amazonaws.com:8761/
```

2. **Check health endpoint:**
```bash
curl http://localhost:8082/actuator/health
```

3. **Check firewall rules:**
```bash
# Make sure port 8082 is open
sudo firewall-cmd --list-ports
```

### Can't connect to downstream services

1. **Verify Eureka registration:**
```bash
curl http://ec2-54-226-128-157.compute-1.amazonaws.com:8761/eureka/apps
```

2. **Check dispatcher health:**
```bash
curl http://localhost:8082/actuator/health | jq '.components.discoveryComposite'
```

## 🔄 Upgrading

1. **Stop the current version:**
```bash
cd /opt/pubgateway-dispatcher-1.0.0
./bin/stop.sh
```

2. **Upload new version:**
```bash
scp target/pubgateway-dispatcher-1.0.1.tar.gz user@your-server:/opt/
```

3. **Extract new version:**
```bash
cd /opt
tar -xzf pubgateway-dispatcher-1.0.1.tar.gz
```

4. **Copy configuration (if needed):**
```bash
cp pubgateway-dispatcher-1.0.0/config/application.yml \
   pubgateway-dispatcher-1.0.1/config/
```

5. **Start new version:**
```bash
cd pubgateway-dispatcher-1.0.1
./bin/start.sh
```

## 📝 Systemd Service (Optional)

For production, you may want to create a systemd service:

### Create service file:
```bash
sudo vi /etc/systemd/system/pubgateway-dispatcher.service
```

### Content:
```ini
[Unit]
Description=PubGateway Dispatcher Service
After=network.target

[Service]
Type=forking
User=pubgateway
Group=pubgateway
WorkingDirectory=/opt/pubgateway-dispatcher-1.0.0
ExecStart=/opt/pubgateway-dispatcher-1.0.0/bin/start.sh
ExecStop=/opt/pubgateway-dispatcher-1.0.0/bin/stop.sh
PIDFile=/opt/pubgateway-dispatcher-1.0.0/bin/pubgateway-dispatcher.pid
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

### Enable and start:
```bash
sudo systemctl daemon-reload
sudo systemctl enable pubgateway-dispatcher
sudo systemctl start pubgateway-dispatcher
sudo systemctl status pubgateway-dispatcher
```

## 📋 Quick Reference

| Command | Description |
|---------|-------------|
| `mvn clean package` | Build deployment package |
| `tar -xzf pubgateway-dispatcher-1.0.0.tar.gz` | Extract package |
| `./bin/start.sh` | Start service |
| `./bin/stop.sh` | Stop service |
| `./bin/restart.sh` | Restart service |
| `./bin/status.sh` | Check status |
| `tail -f logs/console.log` | View logs |

## 🔐 Security Notes

1. **Don't run as root** - Create a dedicated user:
```bash
sudo useradd -r -s /bin/false pubgateway
sudo chown -R pubgateway:pubgateway /opt/pubgateway-dispatcher-1.0.0
```

2. **Firewall** - Only open required ports
3. **Eureka Security** - Configure authentication if needed
4. **HTTPS** - Use a reverse proxy (nginx/Apache) for SSL

## 📞 Support

- Swagger UI: http://localhost:8082/swagger-ui.html
- API Documentation: See `docs/API_INTEGRATION_GUIDE.md`
- Changes: See `docs/CHANGES_SUMMARY.md`

