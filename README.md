# Digital Agency Dashboard

<!-- Badges: add CI / license badges once a pipeline and a LICENSE file exist. -->

A B2B SaaS dashboard for digital agencies, made of a Spring Boot REST API (JWT security, MySQL) and a React + Vite frontend. The current code base implements authentication, client management and project management; the data model already contains entities for tasks, time tracking, quotes, invoices, approvals, notifications, chat messages and e-mail templates.

## Main features

Implemented:

- **Authentication**: register, login, refresh token, current user, logout (JWT access token 15 min, refresh token 7 days, BCrypt passwords).
- **Roles**: `ADMIN`, `MANAGER`, `DESIGNER`, `DEVELOPER`, `CLIENT`, enforced with `@PreAuthorize`.
- **Clients**: create, list, read, update, delete.
- **Projects**: create, list, read, list by client, update, delete.
- **Frontend**: login, register, dashboard, clients and projects pages with modals, protected routes.
- **WebSocket (STOMP)** configuration: endpoint `/ws-notifications`, brokers `/topic` and `/queue`.
- **OpenAPI / Swagger UI**, global exception handling.

Modelled but without API yet (entities and repositories only): tasks, time entries, quotes, invoices, approvals, notifications, chat messages, e-mail templates, project team members.

## Screenshots / demo

> To be completed: add screenshots of the login page, dashboard, clients and projects pages (for example in `./docs/screenshots/`).

```markdown
![Dashboard](./docs/screenshots/dashboard.png)
```

## Prerequisites

- **JDK 17** (Spring Boot 3.2 default; the Docker image uses Eclipse Temurin 17)
- **Maven 3.8+**
- **Node.js 18+** and **npm**
- **MySQL 8** (local, e.g. XAMPP) — or **Docker** with Docker Compose for the database

## Installation

```bash
git clone https://github.com/nour-agrebi-web/digital-agency-dashboard.git
cd digital-agency-dashboard
```

Backend:

```bash
cd backend
mvn clean install -DskipTests
```

Frontend:

```bash
cd frontend
npm install
cp .env.example .env
```

Database (local MySQL):

```sql
CREATE DATABASE IF NOT EXISTS digital_agency_db;
```

> Important: `spring.jpa.hibernate.ddl-auto=validate` is set and Flyway is configured to read `classpath:db/migration`, but **no migration scripts are in the repository**. The tables must currently be created manually, or `ddl-auto` changed to `update` for local development (see notes).

## Configuration / environment variables

### Backend — [backend/src/main/resources/application.properties](./backend/src/main/resources/application.properties)

| Property | Current value | Description |
| --- | --- | --- |
| `server.port` | `8080` | HTTP port |
| `server.servlet.context-path` | `/api` | Context path (prefixes every URL) |
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/digital_agency_db?...` | MySQL connection |
| `spring.datasource.username` / `password` | `root` / *(empty)* | MySQL credentials |
| `spring.jpa.hibernate.ddl-auto` | `validate` | Schema validation only |
| `spring.flyway.locations` | `classpath:db/migration` | Flyway scripts location |
| `jwt.secret` | placeholder | JWT signing key |
| `jwt.expiration` | `900000` (15 min) | Access token lifetime |
| `jwt.refreshExpiration` | `604800000` (7 days) | Refresh token lifetime |
| `cors.allowedOrigins` | `http://localhost:5173,http://localhost:3000` | Allowed origins |

With Docker Compose, the backend receives `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `SPRING_JPA_HIBERNATE_DDL_AUTO` and `JWT_SECRET` (see [docker-compose.yml](./docker-compose.yml)).

### Frontend — [frontend/.env.example](./frontend/.env.example)

| Variable | Example | Description |
| --- | --- | --- |
| `VITE_API_URL` | `http://localhost:8080/api` | API base URL used by `src/services/api.js` |
| `VITE_APP_NAME` | `Digital Agency Dashboard` | Application name |
| `VITE_APP_VERSION` | `1.0.0` | Application version |

## Running the project

### Locally

```bash
# Terminal 1 — backend (http://localhost:8080/api)
cd backend
mvn spring-boot:run

# Terminal 2 — frontend (http://localhost:5173)
cd frontend
npm run dev
```

Open <http://localhost:5173>. The frontend routes are `/login`, `/register`, `/dashboard`, `/clients` and `/projects`.

### With Docker Compose

```bash
docker compose up --build
```

`docker-compose.yml` defines three services: `mysql` (MySQL 8.0, port 3306), `backend` (port 8080) and `frontend` (port 5173). See the notes: the frontend `Dockerfile` is missing, so only the `mysql` service can currently be started on its own:

```bash
docker compose up mysql
```

## Usage

### API documentation

Because of the `/api` context path, Swagger UI is served at <http://localhost:8080/api/swagger-ui.html>.

### Endpoints

The controllers are mapped to `/api/...` **and** the application has the context path `/api`, so the effective URLs currently start with `/api/api/` (see notes). Paths below are given as declared in the controllers.

| Method | Path | Access |
| --- | --- | --- |
| POST | `/api/auth/register` | public |
| POST | `/api/auth/login` | public |
| POST | `/api/auth/refresh` | public |
| GET | `/api/auth/me` | public route (`/api/auth/**`), uses the bearer token |
| POST | `/api/auth/logout` | public route (`/api/auth/**`) |
| POST | `/api/clients` | ADMIN, MANAGER |
| GET | `/api/clients` | ADMIN, MANAGER, DESIGNER, DEVELOPER |
| GET | `/api/clients/{id}` | ADMIN, MANAGER, DESIGNER, DEVELOPER, CLIENT |
| PUT | `/api/clients/{id}` | ADMIN, MANAGER |
| DELETE | `/api/clients/{id}` | ADMIN |
| POST | `/api/projects` | ADMIN, MANAGER |
| GET | `/api/projects` | ADMIN, MANAGER, DESIGNER, DEVELOPER |
| GET | `/api/projects/{id}` | ADMIN, MANAGER, DESIGNER, DEVELOPER, CLIENT |
| GET | `/api/projects/client/{clientId}` | ADMIN, MANAGER, DESIGNER, DEVELOPER |
| PUT | `/api/projects/{id}` | ADMIN, MANAGER |
| DELETE | `/api/projects/{id}` | ADMIN |

### Example requests (with the current configuration)

```bash
# Register
curl -X POST http://localhost:8080/api/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email": "jane@agency.com", "password": "secret123", "firstName": "Jane", "lastName": "Doe", "role": "MANAGER", "department": "Design"}'

# Login → returns accessToken, refreshToken, tokenType, expiresIn, user
curl -X POST http://localhost:8080/api/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "jane@agency.com", "password": "secret123"}'

# List clients
curl http://localhost:8080/api/api/clients \
  -H "Authorization: Bearer <ACCESS_TOKEN>"
```

## Tests

> To be completed: there is no `backend/src/test` folder and the frontend `package.json` has no `test` script (Vitest is installed as a dev dependency but not configured).

Available checks:

```bash
cd backend && mvn test        # runs, but no tests are defined
cd frontend && npm run lint   # ESLint
```

## Project structure

```text
digital-agency-dashboard/
├── docker-compose.yml           # mysql, backend, frontend services
├── backend/                     # Spring Boot API
│   ├── Dockerfile               # multi-stage build (Maven → Temurin 17 JRE)
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/agency/
│       │   ├── config/          # SecurityConfig, JWT provider & filter, WebSocketConfig
│       │   ├── controller/      # AuthController, ClientController, ProjectController
│       │   ├── dto/             # AuthDto, EntityDtos
│       │   ├── entity/          # User, Client, Project, Task, TimeEntry, Quote, Invoice, ...
│       │   ├── exception/       # GlobalExceptionHandler, ErrorResponse
│       │   ├── repository/      # Spring Data JPA repositories
│       │   └── service/         # AuthService, ClientService, ProjectService, UserService
│       └── resources/application.properties
└── frontend/                    # React 18 + Vite
    ├── .env.example
    ├── package.json
    └── src/
        ├── components/          # ClientModal, ProjectModal, ProtectedRoute
        ├── context/             # AuthContext
        ├── hooks/               # useAuth, useQueries
        ├── pages/               # Login, Register, Dashboard, Clients, Projects
        └── services/api.js      # Axios instance + API calls
```

## Technologies

- **Backend**: Java 17, Spring Boot 3.2.0 (Web, Security, Data JPA, WebSocket, Validation), JJWT 0.12.3, MapStruct 1.5.5, Lombok, Flyway, iText 8, springdoc-openapi 2.0.2, MySQL Connector/J 8.4.0
- **Frontend**: React 18, Vite, React Router 6, Axios, React Query 3, Zustand, Tailwind CSS 3 (+ forms plugin), Headless UI, Recharts, date-fns, react-hot-toast, socket.io-client, ESLint
- **Database**: MySQL 8
- **Containers**: Docker, Docker Compose

## Deployment

A Docker setup is provided ([docker-compose.yml](./docker-compose.yml), [backend/Dockerfile](./backend/Dockerfile)) but is not complete yet (missing frontend Dockerfile, missing Actuator dependency for the backend health check, missing migrations).

> To be completed: target environment, production configuration and secrets management.

## Contributing

> To be completed: no contribution guidelines exist. Suggested minimum: create a feature branch, run `mvn test` and `npm run lint`, open a pull request.

## License

> To be completed: the previous README mentioned the MIT License, but no `LICENSE` file is present in the repository. Add one to make it effective.

## Authors / contacts

- Nour Agrebi — [@nour-agrebi-web](https://github.com/nour-agrebi-web)

## Useful resources

- [Spring Boot reference](https://docs.spring.io/spring-boot/)
- [Spring Security](https://docs.spring.io/spring-security/reference/)
- [Vite](https://vite.dev/)
- [React](https://react.dev/)
- [Tailwind CSS](https://tailwindcss.com/docs)

## Notes to complete

- **Double `/api` prefix**: `server.servlet.context-path=/api` plus `@RequestMapping("/api/...")` gives URLs such as `/api/api/auth/login`, while the frontend calls `http://localhost:8080/api` + `/auth/login`. One of the two prefixes must be removed for the frontend to reach the API.
- **No database migrations**: Flyway is configured and `ddl-auto=validate`, but `backend/src/main/resources/db/migration/` does not exist, so the backend cannot start on an empty database.
- **Docker Compose**: `frontend/Dockerfile` is missing; the backend health check calls `/actuator/health` but `spring-boot-starter-actuator` is not a dependency (and the context path would make it `/api/actuator/health`); the compose file sets `VITE_API_BASE_URL` whereas the code reads `VITE_API_URL`.
- **Real-time**: the backend uses Spring STOMP WebSocket while the frontend depends on `socket.io-client`, which uses a different protocol.
- Features announced previously (invoices/quotes with PDF export, client portal, notifications, team chat, analytics, time tracking) have entities but no endpoints or screens yet.
- No tests, no screenshots, no `LICENSE` file, no contribution guide.
