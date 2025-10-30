#!/bin/bash

###############################################################################
# PubGateway Dispatcher Status Script
###############################################################################

# Get the directory where this script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
APP_HOME="$(cd "$SCRIPT_DIR/.." && pwd)"

# Application settings
APP_NAME="pubgateway-dispatcher"
PID_FILE="$APP_HOME/bin/$APP_NAME.pid"

# Check if PID file exists
if [ ! -f "$PID_FILE" ]; then
    echo "$APP_NAME is NOT running (PID file not found)"
    exit 1
fi

# Read PID
PID=$(cat "$PID_FILE")

# Check if process is running
if ps -p "$PID" > /dev/null 2>&1; then
    echo "$APP_NAME is RUNNING (PID: $PID)"
    
    # Get process info
    echo ""
    echo "Process Information:"
    ps -p $PID -o pid,ppid,pcpu,pmem,etime,cmd | tail -n +2
    
    # Check if service is responding
    echo ""
    echo "Health Check:"
    HEALTH_URL="http://localhost:8082/actuator/health"
    if command -v curl > /dev/null 2>&1; then
        HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" "$HEALTH_URL" 2>/dev/null)
        if [ "$HTTP_CODE" = "200" ]; then
            echo "✓ Service is healthy (HTTP 200)"
            curl -s "$HEALTH_URL" 2>/dev/null | head -20
        else
            echo "✗ Service health check failed (HTTP $HTTP_CODE)"
        fi
    else
        echo "curl not available, skipping health check"
    fi
    
    exit 0
else
    echo "$APP_NAME is NOT running (process not found)"
    echo "Removing stale PID file..."
    rm -f "$PID_FILE"
    exit 1
fi

