#!/bin/bash

###############################################################################
# PubGateway Dispatcher Restart Script
###############################################################################

# Get the directory where this script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "Restarting PubGateway Dispatcher..."

# Stop the service
"$SCRIPT_DIR/stop.sh"
STOP_EXIT_CODE=$?

# Wait a moment
sleep 2

# Start the service
"$SCRIPT_DIR/start.sh"
START_EXIT_CODE=$?

if [ $START_EXIT_CODE -eq 0 ]; then
    echo "Restart completed successfully"
    exit 0
else
    echo "Restart failed"
    exit 1
fi

