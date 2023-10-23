#!/bin/bash

sudo docker stop authentication-api

sudo docker rm -f authentication-api

# Build the authentication-api image
sudo docker build -t authentication-api -f /home/pallav/nodejs/payments/authentication-api/.dockerfile /home/pallav/nodejs/payments/authentication-api

# Run the authentication-api container in detached mode
sudo docker run -d -p 8080:80 --name authentication-api authentication-api

