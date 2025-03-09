# Stage 1: Generate dev certificate
FROM mcr.microsoft.com/dotnet/sdk:7.0 AS dev-certs
WORKDIR /root/.aspnet/https
ARG CERT_PASSWORD
RUN dotnet dev-certs https -ep aspnetapp.pfx -p $CERT_PASSWORD

# Stage 2: Base image
FROM mcr.microsoft.com/dotnet/aspnet:7.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

# Stage 3: Build the app
FROM mcr.microsoft.com/dotnet/sdk:7.0 AS build
COPY ["sample-app/sample-app.csproj", "sample-app/"]
RUN dotnet restore "sample-app/sample-app.csproj"
COPY . .
WORKDIR "sample-app"
RUN dotnet build "sample-app.csproj" -c Release -o /app/build

# Stage 4: Publish the app
FROM build AS publish
RUN dotnet publish "sample-app.csproj" -c Release -o /app/publish /p:UseAppHost=false

# Stage 5: Final image
FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .

# Copy the development certificate into the Docker image
COPY --from=dev-certs /root/.aspnet/https/aspnetapp.pfx .

# Set environment variables for certificate and ASP.NET Core
ARG CERT_PASSWORD
ENV ASPNETCORE_Kestrel__Certificates__Default__Password=$CERT_PASSWORD
ENV ASPNETCORE_Kestrel__Certificates__Default__Path=/app/aspnetapp.pfx
ENV ASPNETCORE_ENVIRONMENT=Development
ENV ASPNETCORE_URLS=http://+;https://+

ENTRYPOINT ["dotnet", "sample-app.dll"]

