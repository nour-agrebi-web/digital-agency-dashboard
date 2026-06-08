# Digital Agency Dashboard - B2B SaaS Platform

A comprehensive B2B SaaS platform for digital agencies to manage clients, projects, teams, finances, and real-time collaboration.

## Features (TIER 1 & 2)

### Core Features (Tier 1)
- Authentication & Authorization - JWT-based, 5 roles
- Client Management - CRUD operations
- Project Management - Create, track, assign teams
- Dashboard - Real-time KPIs and statistics
- Time Tracking - Timer and manual entries

### Important Features (Tier 2)
- Invoices & Quotes - Auto-generated, PDF export
- Client Portal - Limited access interface
- Real-Time Notifications - WebSocket-based
- Team Chat - Project-scoped messages, @mentions
- Analytics - ROI, performance, productivity metrics

## Technology Stack

**Backend:** Spring Boot 3, Spring Security (JWT), MySQL 8, Spring Data JPA, WebSocket
**Frontend:** React 18, Vite, TailwindCSS, Recharts, Socket.io-client, Zustand
**Database:** MySQL 8.0 (via XAMPP), Flyway migrations

## Prerequisites

- Java 17+
- Node.js 18+
- MySQL 8.0+ (XAMPP)
- Maven 3.8+
- npm 9+

## Quick Start

### 1. Backend
```bash
cd backend
mvn spring-boot:run
# Runs on http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui.html
```

### 2. Frontend
```bash
cd frontend
npm install
npm run dev
# Runs on http://localhost:5173
```

### 3. Database
Create database and tables:
```sql
CREATE DATABASE IF NOT EXISTS digital_agency_db;
```
Flyway migrations run automatically on app startup.

## Project Structure

```
├── backend/
│   ├── src/main/java/com/agency/
│   │   ├── config/       # Security, WebSocket configs
│   │   ├── controller/   # REST endpoints
│   │   ├── entity/       # JPA entities
│   │   ├── service/      # Business logic
│   │   └── repository/   # Data access
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── pages/
│   │   ├── components/
│   │   ├── hooks/
│   │   └── services/
│   └── package.json
└── README.md
```

## API Endpoints

### Auth
- POST /api/auth/register
- POST /api/auth/login
- POST /api/auth/refresh

### Clients
- GET /api/clients
- POST /api/clients
- GET /api/clients/{id}
- PUT /api/clients/{id}
- DELETE /api/clients/{id}

### Projects
- GET /api/projects
- POST /api/projects
- GET /api/projects/{id}
- PUT /api/projects/{id}

See Swagger for complete documentation.

## Security

- JWT Tokens: 15min access, 7day refresh
- Password: BCrypt hashing
- CORS: localhost:5173, localhost:3000
- SQL Injection: JPA parameterized queries

## Features Implemented

### Phase 1: Project Scaffolding ✅
- GitHub repository created
- Spring Boot 3 project initialized
- React + Vite project initialized
- MySQL schema (14 tables)
- Security configuration (JWT + Spring Security)
- WebSocket configuration
- Error handling
- Swagger API documentation

## Development

```bash
# Backend tests
cd backend && mvn test

# Frontend tests
cd frontend && npm run test

# Build
cd backend && mvn clean package
cd frontend && npm run build
```

## GitHub Repository

Repository: https://github.com/nour-agrebi-web/digital-agency-dashboard

## License

MIT License

## Author

Nour Agrebi - [@nour-agrebi-web](https://github.com/nour-agrebi-web)
