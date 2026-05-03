# Gym-membership-mgmt

A Spring Boot REST API for managing gym memberships, plans, and revenue reports.

## Build & Run

```bash
./mvnw clean package
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080`.

## Useful URLs

- **API Documentation (Swagger UI)**: http://localhost:8080/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8080/v3/api-docs
- **H2 Console**: http://localhost:8080/h2-console (username: sa, password: empty)

## API Endpoints

### Gyms

- `POST /api/gyms` - Create a new gym
- `GET /api/gyms` - Get all gyms

Example request:
```bash
curl -X POST http://localhost:8080/api/gyms \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Zdrofit",
    "address": "Krakowska 12",
    "phoneNumber": "123456789"
  }'
```

## Project Structure

```
src/main/java/sii/GymMembership/
- gym/ (Gym management)
- plan/ (Membership plans)
- member/ (Members)
- report/ (Reports)
- common/ (Shared components, exceptions)
```

