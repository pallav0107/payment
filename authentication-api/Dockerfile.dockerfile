# Use the official .NET SDK image for building the application
FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /app

# Copy the .csproj and restore any dependencies (NuGet packages)
COPY *.csproj ./
RUN dotnet restore

# Create a log folder
RUN mkdir -p /app/logs

# Copy the remaining source code and build the application
COPY . ./
RUN dotnet publish -c Release -o out

# Create a runtime image
FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS runtime
WORKDIR /app
COPY --from=build /app/out ./

# Expose the port that your application will run on
EXPOSE 80

# Define the entry point for your application
CMD ["dotnet", "authentication-api.dll"]

## sudo docker build -t authentication-api -f /home/pallav/nodejs/payments/authentication-api/Dockerfile.dockerfile /home/pallav/nodejs/payments/authentication-api
## sudo docker run -d --name authentication-api-container authentication-api
## sudo docker run -d -p 9001:9000 --name=portainer-newer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer-ce
## sudo docker exec -it authentication-api /bin/bash
