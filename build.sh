#!/bin/bash

###############################################################################
# PubGateway Dispatcher Build Script
###############################################################################

set -e

echo "=========================================="
echo "Building PubGateway Dispatcher"
echo "=========================================="
echo ""

# Clean previous builds
echo "1. Cleaning previous builds..."
mvn clean

echo ""
echo "2. Running tests..."
mvn test

echo ""
echo "3. Building package..."
mvn package -DskipTests

echo ""
echo "=========================================="
echo "Build Complete!"
echo "=========================================="
echo ""
echo "Output files:"
echo "  - JAR:    target/pubgateway-dispatcher-1.0.0.jar"
echo "  - TAR.GZ: target/pubgateway-dispatcher-1.0.0.tar.gz"
echo ""
echo "To deploy:"
echo "  1. Upload:  scp target/pubgateway-dispatcher-1.0.0.tar.gz user@server:/opt/"
echo "  2. Extract: tar -xzf pubgateway-dispatcher-1.0.0.tar.gz"
echo "  3. Start:   cd pubgateway-dispatcher-1.0.0 && ./bin/start.sh"
echo ""

