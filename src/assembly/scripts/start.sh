#!/bin/bash

###############################################################################
# PubGateway Dispatcher Startup Script
###############################################################################

# Get the directory where this script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
APP_HOME="$(cd "$SCRIPT_DIR/.." && pwd)"

# Application settings
APP_NAME="pubgateway-dispatcher"
JAR_FILE="$APP_HOME/lib/pubgateway-dispatcher-1.0.0.jar"
CONFIG_DIR="$APP_HOME/config"
LOG_DIR="$APP_HOME/logs"
PID_FILE="$APP_HOME/bin/$APP_NAME.pid"

# JVM settings (can be overridden by environment variables)
JVM_XMX="${JVM_XMX:-512m}"
JVM_XMS="${JVM_XMS:-256m}"
JVM_OPTS="${JVM_OPTS:--server -XX:+UseG1GC -XX:MaxGCPauseMillis=200}"

# Spring Boot settings
SPRING_PROFILES="${SPRING_PROFILES_ACTIVE:-prod}"
SERVER_PORT="${SERVER_PORT:-8082}"

# Check if already running
if [ -f "$PID_FILE" ]; then
    PID=$(cat "$PID_FILE")
    if ps -p "$PID" > /dev/null 2>&1; then
        echo "Error: $APP_NAME is already running (PID: $PID)"
        exit 1
    else
        echo "Removing stale PID file..."
        rm -f "$PID_FILE"
    fi
fi

# Check if JAR file exists
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: JAR file not found: $JAR_FILE"
    exit 1
fi

# Create logs directory if it doesn't exist
mkdir -p "$LOG_DIR"

# Build the Java command
JAVA_CMD="java \
    -Xmx$JVM_XMX \
    -Xms$JVM_XMS \
    $JVM_OPTS \
    -Dspring.profiles.active=$SPRING_PROFILES \
    -Dspring.config.location=file:$CONFIG_DIR/ \
    -Dserver.port=$SERVER_PORT \
    -Dlogging.file.path=$LOG_DIR \
    -jar $JAR_FILE"

# Start the application
echo "Starting $APP_NAME..."
echo "JVM Options: -Xmx$JVM_XMX -Xms$JVM_XMS $JVM_OPTS"
echo "Spring Profile: $SPRING_PROFILES"
echo "Server Port: $SERVER_PORT"
echo "Config Dir: $CONFIG_DIR"
echo "Log Dir: $LOG_DIR"

nohup $JAVA_CMD > "$LOG_DIR/console.log" 2>&1 &
PID=$!

# Save PID to file
echo $PID > "$PID_FILE"

# Wait a bit and check if process is still running
sleep 3
if ps -p $PID > /dev/null 2>&1; then
    echo "$APP_NAME started successfully (PID: $PID)"
    echo "Logs: $LOG_DIR/console.log"
    echo "Check status with: tail -f $LOG_DIR/console.log"
    exit 0
else
    echo "Error: $APP_NAME failed to start"
    echo "Check logs: $LOG_DIR/console.log"
    rm -f "$PID_FILE"
    exit 1
fi

