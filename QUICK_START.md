# Quick Start Guide

## 🚀 Build and Deploy in 5 Minutes

### Step 1: Build the Package

```bash
cd /Users/mwu/Documents/git/github/pubgateway-dispatcher
./build.sh
```

This creates: `target/pubgateway-dispatcher-1.0.0.tar.gz`

### Step 2: Deploy to Server

```bash
# Upload
scp target/pubgateway-dispatcher-1.0.0.tar.gz user@your-server:/opt/

# SSH to server
ssh user@your-server

# Extract
cd /opt
tar -xzf pubgateway-dispatcher-1.0.0.tar.gz
cd pubgateway-dispatcher-1.0.0
```

### Step 3: Start the Service

```bash
./bin/start.sh
```

### Step 4: Verify

```bash
# Check status
./bin/status.sh

# Or use curl
curl http://localhost:8082/actuator/health
```

### Step 5: Access Swagger UI

Open in browser: `http://your-server:8082/swagger-ui.html`

## 📝 Common Commands

```bash
# Start
./bin/start.sh

# Stop
./bin/stop.sh

# Restart
./bin/restart.sh

# Check status
./bin/status.sh

# View logs
tail -f logs/console.log
```

## ⚙️ Custom Configuration

### Change JVM Settings
```bash
JVM_XMX=1024m JVM_XMS=512m ./bin/start.sh
```

### Change Port
```bash
SERVER_PORT=8090 ./bin/start.sh
```

### Use Different Profile
```bash
SPRING_PROFILES_ACTIVE=production ./bin/start.sh
```

## 🔧 Manual Start (Like Your Example)

```bash
cd /opt/pubgateway-dispatcher-1.0.0

nohup java \
  -Xmx256m \
  -Xms128m \
  -Dspring.config.location=file:./config/ \
  -Dserver.port=8082 \
  -jar lib/pubgateway-dispatcher-1.0.0.jar \
  > logs/console.log 2>&1 &
```

## 📚 Full Documentation

- **Deployment Guide**: [DEPLOYMENT_GUIDE.md](DEPLOYMENT_GUIDE.md)
- **API Documentation**: [API_INTEGRATION_GUIDE.md](API_INTEGRATION_GUIDE.md)
- **Changes Summary**: [CHANGES_SUMMARY.md](CHANGES_SUMMARY.md)

## 🆘 Troubleshooting

**Service won't start?**
```bash
# Check if port is in use
lsof -i :8082

# Check logs
cat logs/console.log
```

**Can't connect to Eureka?**
```bash
# Test Eureka connectivity
curl http://ec2-54-226-128-157.compute-1.amazonaws.com:8761/
```

**Health check failing?**
```bash
# Check service health
curl http://localhost:8082/actuator/health | jq
```

## ✅ Success Indicators

1. ✅ Process is running: `./bin/status.sh` shows RUNNING
2. ✅ Health check passes: Returns HTTP 200
3. ✅ Eureka connected: Shows services in health check
4. ✅ Swagger accessible: Can open Swagger UI

That's it! You're ready to go! 🎉

