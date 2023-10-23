#!/bin/bash

# Stop and remove the payments-api-db container if it exists
sudo docker stop payments-api-db
sudo docker rm -f payments-api-db

# Build the payments-api-db image
sudo docker build -t payments-api-db -f /home/pallav/nodejs/payments/payments-api-db/.dockerfile /home/pallav/nodejs/payments/payments-api-db

# Run the payments-api-db container in detached mode, exposing the PostgreSQL port
sudo docker run -d --name payments-api-db -p 5432:5432 payments-api-db

# Wait for PostgreSQL to start
until sudo docker exec payments-api-db pg_isready -q -h localhost -U postgres; do
  echo "Waiting for PostgreSQL to start..."
  sleep 2
done

# Execute psql command in the payments-api-db container
sudo docker exec -it payments-api-db psql -U postgres -d payments -a -f /docker-entrypoint-initdb.d/payments-db.sql
