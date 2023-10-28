# Use the official PostgreSQL image as the base image
FROM postgres:latest

# Set environment variables for PostgreSQL
ENV POSTGRES_DB payments
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres_password12#

# Copy the SQL script to the container
COPY payments-db.sql /docker-entrypoint-initdb.d/

# Optionally, set permissions if needed
RUN chown postgres:postgres /docker-entrypoint-initdb.d/payments-db.sql

## sudo docker build -t payments-api-db -f /home/pallav/nodejs/payments/payments-api-db/.dockerfile /home/pallav/nodejs/payments/payments-api-db
## sudo docker run -d --name payments-api-db payments-api-db
## sudo docker start payments-api-db
## sudo docker exec -it payments-api-db psql -U postgres -d payments -a -f /docker-entrypoint-initdb.d/payments-db.sql
## docker exec -it payments-api-db /bin/bash


## sudo docker exec -it payments-api-db psql -U postgres -d payments    
## SELECT * FROM "user" LIMIT 5;
## psql -h localhost -U postgres -d payments -p 5432
## dotnet ef dbcontext scaffold "Host=localhost;Database=payments;Username=postgres;Password=postgres_password12#" Npgsql.EntityFrameworkCore.PostgreSQL -o Entities
## cat /var/lib/postgresql/data/pg_hba.conf
## echo "host    all             all             172.17.0.1/32         md5" >> /var/lib/postgresql/data/pg_hba.conf

## echo "listen_addresses = '172.17.0.1'" | tee -a /var/lib/postgresql/data/postgresql.conf
## echo "host    all             all             172.17.0.1/32         md5" | tee -a /var/lib/postgresql/data/pg_hba.conf

## docker logs authentication-api     

## docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' payments-api-db 



