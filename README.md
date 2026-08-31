# BillingCounter

BillingCounter is a full-stack billing management web application. It uses a Java Spring Boot backend with PostgreSQL for persistence and a React frontend located in the `ui/` folder.

This README documents everything needed to build, run, and develop the project locally, including detailed API endpoints, configuration, database setup, troubleshooting steps, and testing.

---

## Technology Stack

- Backend: Java 11+, Spring Boot (Gradle)
- Database: PostgreSQL
- Frontend: React (create-react-app), Node/npm (or Yarn)

---

## Repository Layout

- `src/main/java/com/billingcounter/` — Backend source code (controllers, services, models, repositories, config)
- `src/main/resources/application.properties` — Backend runtime configuration
- `ui/` — React frontend app
- `build.gradle`, `gradlew`, `gradlew.bat` — Gradle build files and wrappers

---

## Prerequisites

- Java 11 (or compatible JDK) installed and `JAVA_HOME` configured
- Node.js (14+) and `npm` or `yarn` for the frontend
- PostgreSQL running locally (or use Docker)
- Git (optional, recommended)

---

## Database setup (PostgreSQL)

The backend expects a PostgreSQL database named `billing_counter_db` and a user `postgres` with password `postgres` by default (see `src/main/resources/application.properties`).

1) Quick Docker run (recommended for local dev):

```bash
docker run --name bc-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=billing_counter_db \
  -p 5432:5432 \
  -d postgres:14
```

2) Or create database manually using `psql`:

```sql
-- Connect as a superuser, then run:
CREATE DATABASE billing_counter_db;
-- If you need to create the user (not required if using default postgres user):
CREATE USER postgres WITH PASSWORD 'postgres';
GRANT ALL PRIVILEGES ON DATABASE billing_counter_db TO postgres;
```

3) Connection settings are in `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/billing_counter_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Change these values if you use a different DB name, host, or credentials.

---

## Running locally

Important: start PostgreSQL first.

Backend (Windows):

```powershell
cd C:\Users\Admin\git\BillingCounter
.\gradlew.bat build
.\gradlew.bat bootRun
```

Backend (macOS / Linux):

```bash
cd /path/to/BillingCounter
./gradlew build
./gradlew bootRun
```

The backend runs on `http://localhost:8080` by default (configured in `application.properties`).

Frontend:

```bash
cd ui
npm install      # or `yarn install` if you prefer and have yarn
npm start        # or `yarn start`
```

The React app will run on `http://localhost:3000` by default.

To run both services together: start the DB, then start the backend, then start the frontend.

---

## API Endpoints (summary)

All backend APIs are prefixed with `/api`.

- Authentication (`AuthController`):
  - POST `/api/auth/login` — form-based login endpoint (session cookies)
  - POST `/api/auth/signup` — user registration
  - POST `/api/auth/logout` — logout

- Bills (`BillController`):
  - POST `/api/bills` — create a new bill with items
  - GET `/api/bills` — list all bills
  - GET `/api/bills/{id}` — get bill by id
  - POST `/api/bills/{id}/pay` — make payment for a bill
  - GET `/api/bills/summary/today` — today's summary
  - GET `/api/bills/summary?from=...&to=...` — summary between datetimes

- Bill Items (`BillItemController`):
  - GET `/api/bill-items` — list all bill items
  - GET `/api/bill-items/{id}` — get bill item by id
  - POST `/api/bill-items` — create a bill item
  - PUT `/api/bill-items/{id}` — update bill item
  - DELETE `/api/bill-items/{id}` — delete bill item

- Groceries (`GroceryController`):
  - GET `/api/groceries` — list all groceries
  - GET `/api/groceries/{id}` — get grocery by id
  - POST `/api/groceries` — add grocery item
  - PUT `/api/groceries/{id}` — update grocery
  - DELETE `/api/groceries/{id}` — delete grocery
  - GET `/api/groceries/low-stock` — get low-stock items

- Hotel Tables (`HotelTableController`):
  - GET `/api/hoteltables` — list tables
  - GET `/api/hoteltables/{id}` — get table by id
  - POST `/api/hoteltables` — create table
  - PUT `/api/hoteltables/{id}` — update table
  - DELETE `/api/hoteltables/{id}` — delete table
  - PUT `/api/hoteltables/{id}/occupy` — mark table occupied
  - PUT `/api/hoteltables/{id}/free` — mark table free

- Menu (`MenuItemController`):
  - GET `/api/menu` — list all menu items
  - GET `/api/menu/{id}` — get menu item by id
  - POST `/api/menu` — create menu item
  - PUT `/api/menu/{id}` — update menu item
  - DELETE `/api/menu/{id}` — delete menu item
  - GET `/api/menu/available` — list available items
  - GET `/api/menu/category/{category}` — filter by category

Refer to the controller classes under `src/main/java/com/billingcounter/controller/` for request/response DTOs and details.

---

## API Examples and DTO shapes

Below are example request/response bodies, common DTO shapes, and cURL examples showing how to authenticate (session-based) and call protected endpoints.

### Common DTO shapes

- `SignupRequest` (JSON body)

```json
{
  "username": "alice",
  "password": "s3cret",
  "role": "admin"
}
```

- `BillRequestDTO` (JSON body for creating a bill)

```json
{
  "items": [
    { "menuItemId": 12, "quantity": 2, "price": 50.0 },
    { "menuItemId": 7, "quantity": 1, "price": 30.0 }
  ],
  "taxPercentage": 5.0
}
```

- `BillPaymentRequest` (JSON body for paying a bill)

```json
{
  "amount": 130.0,
  "method": "CASH"  
}
```

### cURL examples (session/cookie-based auth)

1) Sign up (optional):

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"s3cret","role":"staff"}' \
  http://localhost:8080/api/auth/signup
```

2) Login (store cookies to `cookies.txt`):

```bash
curl -X POST -c cookies.txt \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=alice&password=s3cret" \
  http://localhost:8080/api/auth/login
```

3) Create a bill (use saved cookies):

```bash
curl -X POST -b cookies.txt \
  -H "Content-Type: application/json" \
  -d '{"items":[{"menuItemId":12,"quantity":2,"price":50.0}],"taxPercentage":5}' \
  http://localhost:8080/api/bills
```

4) Fetch bills:

```bash
curl -X GET -b cookies.txt http://localhost:8080/api/bills
```

5) Pay bill:

```bash
curl -X POST -b cookies.txt \
  -H "Content-Type: application/json" \
  -d '{"amount":130.0,"method":"CASH"}' \
  http://localhost:8080/api/bills/1/pay
```

6) Create grocery item (example):

```bash
curl -X POST -b cookies.txt \
  -H "Content-Type: application/json" \
  -d '{"name":"Rice","price":40.0,"quantity":20}' \
  http://localhost:8080/api/groceries
```

Notes:
- The backend uses session-based authentication. Use `-c` to create a cookie jar and `-b` to send cookies on subsequent requests.
- Some endpoints return `201 Created` (POSTs) and some `204 No Content` (DELETEs). Check the controller method javadocs for exact response behavior.

---

## Frontend

- The React app entry is `ui/src/App.js`. Routes include `/login` and `/dashboard`.
- API client configuration is in `ui/src/services/api.js` (base URL `http://localhost:8080`, `withCredentials` enabled for session auth).
- UI pages and components are under `ui/src/pages` and `ui/src/components`.

---

## Configuration

- `src/main/resources/application.properties` controls server port and DB connection.
- To change server port, edit `server.port`.
- CORS is configured in `SecurityConfig` to allow `http://localhost:3000` by default — update if your frontend host differs.

---

## Tests

Backend:

```bash
./gradlew test       # or .\\gradlew.bat test on Windows
```

Frontend:

```bash
cd ui
npm test
```

---

## Troubleshooting

- `yarn: command not found` — install Yarn globally (`npm install -g yarn`) or use `npm install` instead.
- Backend fails to start with DB errors — confirm Postgres is running and credentials in `application.properties` match your DB.
- CORS or authentication issues — ensure frontend uses `credentials: 'include'` (already configured in `ui/src/services/api.js`) and `SecurityConfig` allows `http://localhost:3000`.

If you see specific errors, paste the stack trace and I can help debug.

### CI / GHCR publishing when repo Actions permissions are restricted

If GitHub Actions cannot publish Docker images to GitHub Container Registry (GHCR) due to repository or organization-level restrictions, use a Personal Access Token (PAT) and add it as a repository secret named `GHCR_TOKEN`.

1) Create a PAT (classic) at https://github.com/settings/tokens/new with these scopes:
  - `write:packages`
  - `read:packages`
  - `repo` (only if this repository is private)

2) Add the token as a repository secret:
  - Repository → Settings → Secrets and variables → Actions → New repository secret
  - Name: `GHCR_TOKEN`
  - Value: the PAT you created

3) The CI workflow will use `GHCR_TOKEN` for login and publishing. After adding the secret, re-run the failing workflow from the Actions page.


---

## Contribution & Development notes

- Code style: Java code follows Spring conventions; keep controllers thin and move logic to services.
- Add unit tests in `src/test/java` and component tests in `ui/src`.

---

## License

This repository does not include a license file. Add a `LICENSE` if you want to open-source this project.

---

If you'd like, I can also:
- add a short top-level `ui/README.md` summary or link to this file
- generate a `docker-compose.yml` for Postgres + backend
- create a `Makefile` with common commands

Enjoy working on BillingCounter — tell me which extra artifacts you'd like next.
