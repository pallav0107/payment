#!/bin/bash

# Stop and remove the authentication-api container if it exists
sudo docker stop authentication-api
sudo docker rm -f authentication-api

# Define the log directory on the host machine
LOG_DIR=/home/logs  # Replace with your desired log directory path

# Check if the log directory exists; if not, create it
if [ ! -d "$LOG_DIR" ]; then
    mkdir -p $LOG_DIR
fi

# Add write permissions to the log directory
sudo chmod o+w $LOG_DIR  # This grants write permissions to others (all users)

# Build the authentication-api image
sudo docker build -t authentication-api -f /home/pallav/nodejs/payments/authentication-api/Dockerfile.dockerfile /home/pallav/nodejs/payments/authentication-api

# Run the authentication-api container in detached mode, mapping the log directory as a volume
sudo docker run -d -p 8080:80 --name authentication-api -v $LOG_DIR:/app/container-logs authentication-api
