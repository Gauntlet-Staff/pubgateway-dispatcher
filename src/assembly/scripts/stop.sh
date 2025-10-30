#!/bin/bash

###############################################################################
# PubGateway Dispatcher Stop Script
###############################################################################

# Get the directory where this script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
APP_HOME="$(cd "$SCRIPT_DIR/.." && pwd)"

# Application settings
APP_NAME="pubgateway-dispatcher"
PID_FILE="$APP_HOME/bin/$APP_NAME.pid"

# Check if PID file exists
if [ ! -f "$PID_FILE" ]; then
    echo "$APP_NAME is not running (PID file not found)"
    exit 0
fi

# Read PID
PID=$(cat "$PID_FILE")

# Check if process is running
if ! ps -p "$PID" > /dev/null 2>&1; then
    echo "$APP_NAME is not running (process not found)"
    rm -f "$PID_FILE"
    exit 0
fi

# Stop the process
echo "Stopping $APP_NAME (PID: $PID)..."
kill $PID

# Wait for process to stop (max 30 seconds)
TIMEOUT=30
COUNTER=0
while ps -p $PID > /dev/null 2>&1; do
    if [ $COUNTER -ge $TIMEOUT ]; then
        echo "Warning: Process did not stop gracefully, forcing shutdown..."
        kill -9 $PID
        sleep 2
        break
    fi
    sleep 1
    COUNTER=$((COUNTER + 1))
    echo -n "."
done
echo ""

# Remove PID file
rm -f "$PID_FILE"

# Verify process is stopped
if ps -p $PID > /dev/null 2>&1; then
    echo "Error: Failed to stop $APP_NAME"
    exit 1
else
    echo "$APP_NAME stopped successfully"
    exit 0
fi

