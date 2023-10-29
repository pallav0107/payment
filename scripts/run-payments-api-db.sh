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
  sudo docker start payments-api-db
  sleep 2
done

# Execute psql command in the payments-api-db container
sudo docker exec -it payments-api-db psql -U postgres -d payments -a -f /docker-entrypoint-initdb.d/payments-db.sql

# Get the IP address of the PostgreSQL container
PG_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' payments-api-db)

# Use docker exec to run the command inside the container
sudo docker exec -it payments-api-db sh -c "echo 'host    all             all             $PG_IP/32         md5' >> /var/lib/postgresql/data/pg_hba.conf"

# Update postgresql.conf to listen on the PostgreSQL container's IP address
sudo docker exec -it payments-api-db sh -c "echo 'listen_addresses = '\''$PG_IP'\''\n' | tee -a /var/lib/postgresql/data/postgresql.conf"

AUTH_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' authentication-api)

sudo docker exec -it payments-api-db sh -c "echo 'host    all             all             $AUTH_IP/32         md5' >> /var/lib/postgresql/data/pg_hba.conf"

sudo docker exec -it payments-api-db sh -c "echo 'listen_addresses = '\''$PG_IP, $AUTH_IP'\''\n' | tee -a /var/lib/postgresql/data/postgresql.conf"

# Restart the PostgreSQL service for the changes to take effect
sudo docker exec -it payments-api-db service postgresql restart

