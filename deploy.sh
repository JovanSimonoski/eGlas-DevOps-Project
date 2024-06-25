#!/bin/bash

echo "Rebuilding Docker containers..."
docker compose down
docker compose pull
docker compose up -d
echo "Deployment completed successfully."
