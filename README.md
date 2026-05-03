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

### Membership plans (per gym)

Plans are scoped to a gym (`gymId` in the path). Within a gym, **plan names must be unique** (duplicate returns **409**). If `gymId` does not exist, the API returns **404**.

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/api/gyms/{gymId}/plans` | Create a plan for that gym |
| `GET` | `/api/gyms/{gymId}/plans` | List plans for that gym (ordered by name) |

Example (replace `1` with a real gym id from `POST /api/gyms` or `GET /api/gyms`):

```bash
curl -X POST http://localhost:8080/api/gyms/1/plans \
  -H "Content-Type: application/json" \
  -d '{
    "type": "PREMIUM",
    "name": "All inclusive",
    "monthlyPrice": {
      "amount": "199.99",
      "currency": "PLN"
    }
  }'

curl http://localhost:8080/api/gyms/1/plans
```

### HTTP errors handled globally

- **400**: validation failures (request bodies), or an unknown currency code for `monthlyPrice.currency`.
- **404**: referenced gym does not exist (plan endpoints).
- **409**: duplicate gym name, or duplicate plan name within the same gym.

## Project structure

```
src/main/java/sii/GymMembership/
- gym/ (Gym management)
- plan/ (Membership plans)
- member/ (Members)
- report/ (Reports)
- common/ (Shared components, exceptions)
```

