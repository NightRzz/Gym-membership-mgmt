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
- **H2 Console**: http://localhost:8080/h2-console — use JDBC URL **`jdbc:h2:mem:gymmem`**, username **`sa`**, password **empty**

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
    },
    "durationMonths": 12,
    "maxMembers": 50
  }'

curl http://localhost:8080/api/gyms/1/plans
```

### Members

- `GET /api/members` - List **all** members (every gym), ordered by gym name then member full name. Each item includes **gym name**, **membership plan name**, and **status**, along with ids and other member fields.

### Members (per gym)

Each member is subscribed to **exactly one** membership plan belonging to that gym. Registration stores **full name**, **email**, and a **membership start date** (current date in the system default time zone). Active members are counted against the plan’s `maxMembers`; when the limit is reached, new subscriptions return **409**. The same **email** cannot be reused for another **ACTIVE** member in the same gym; cancelled members no longer count toward capacity or that email rule.

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/api/members` | List all members (all gyms); includes plan name, gym name, status |
| `POST` | `/api/gyms/{gymId}/members` | Enrol a member on a plan (`planId` must belong to that gym) |
| `POST` | `/api/gyms/{gymId}/members/{memberId}/cancel` | Set membership status to `CANCELLED` (idempotent if already cancelled) |
| `GET` | `/api/gyms/{gymId}/members` | List members for the gym (via their plans), ordered by full name |

Example:

```bash
curl -X POST http://localhost:8080/api/gyms/1/members \
  -H "Content-Type: application/json" \
  -d '{
    "planId": 1,
    "fullName": "Jan Kowalski",
    "email": "jan.kowalski@example.com"
  }'

curl http://localhost:8080/api/members

curl http://localhost:8080/api/gyms/1/members

curl -X POST http://localhost:8080/api/gyms/1/members/1/cancel
```

### Revenue Report

- `GET /api/reports/revenue` - Get revenue per gym and grouped by currency

Example:
```bash
curl http://localhost:8080/api/reports/revenue
```

Response:
```json
[
  {
    "gymName": "Zdrofit",
    "amount": 1499.98,
    "currency": "EUR"
  },
  {
    "gymName": "Zdrofit",
    "amount": 2999.96,
    "currency": "PLN"
  }
]
```

### HTTP errors handled globally

- **400**: validation failures (request bodies), malformed JSON / invalid enum values, or an unknown currency code for `monthlyPrice.currency`.
- **404**: referenced gym does not exist, membership plan id does not exist for that gym, or member id does not exist for that gym.
- **409**: duplicate gym name, duplicate plan name within the same gym, plan at maximum active members, or duplicate **active** member email within the same gym.

## Project structure

```
src/main/java/sii/GymMembership/
- gym/ (Gym management)
- plan/ (Membership plans)
- member/ (Members)
- report/ (Reports)
- common/ (Shared components, exceptions)
```

