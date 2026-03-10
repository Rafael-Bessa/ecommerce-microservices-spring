<div align="center">

# 🛒 ecommerce-microservices-spring

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2025-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![SQL Server](https://img.shields.io/badge/SQL_Server-CC2927?style=for-the-badge&logo=microsoftsqlserver&logoColor=white)
![Resilience4j](https://img.shields.io/badge/Resilience4j-Circuit_Breaker-important?style=for-the-badge)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)

> E-commerce backend built with Java, Spring Boot and Microservices architecture.

</div>

---

## 📐 Architecture

```
Client
  │
  ▼
┌─────────────────────┐
│    API Gateway       │  ← Spring Cloud Gateway (port 8080)
└─────────┬───────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
┌────────┐  ┌────────┐
│Product │  │ Order  │
│Service │  │Service │
│ :8081  │  │ :8082  │
└────────┘  └────────┘
    │            │
  MySQL      SQL Server

          ◎ Eureka Server (port 8761)
          All services registered
```

---

## 🧩 Services

| Service | Port | Database | Description |
|---|---|---|---|
| `eureka-server` | 8761 | — | Service Discovery |
| `api-gateway` | 8080 | — | API Gateway & routing |
| `product-service` | 8081 | MySQL | Product catalog management |
| `order-service` | 8082 | SQL Server | Order processing |

---

## 🛠️ Tech Stack

- **Java 17** + **Spring Boot 4** + **Spring Framework 7**
- **Spring Cloud Gateway** — API Gateway with load balancing
- **Netflix Eureka** — Service Discovery
- **OpenFeign** — Declarative HTTP client for inter-service communication
- **Resilience4j** — Circuit Breaker pattern
- **Flyway** — Database migrations
- **MySQL** — Product Service database
- **SQL Server** — Order Service database

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL running on port `3306`
- SQL Server running on port `1433`

### Running the services

Start in this exact order:

```bash
# 1. Eureka Server
cd eureka-server && mvn spring-boot:run

# 2. Product Service
cd product-service && mvn spring-boot:run

# 3. Order Service
cd order-service && mvn spring-boot:run

# 4. API Gateway
cd api-gateway && mvn spring-boot:run
```

### Eureka Dashboard
```
http://localhost:8761
```

---

## 📡 API Endpoints

All requests go through the **API Gateway** at `http://localhost:8080`

### Products
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/products` | List all products |
| `GET` | `/products/{id}` | Get product by ID |
| `POST` | `/products` | Create product |
| `PUT` | `/products/{id}` | Update product |
| `DELETE` | `/products/{id}` | Delete product |

### Orders
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/orders` | List all orders |
| `GET` | `/orders/{id}` | Get order by ID |
| `POST` | `/orders` | Create order |

---

## 🔁 Inter-service Communication

The **Order Service** communicates with the **Product Service** via **Feign Client**.
Errors are translated into domain exceptions by a custom `ErrorDecoder`:

- `404` → `ProductNotFoundException` → returns `404` to the client
- `503` → `ProductServiceUnavailableException` → returns `503` to the client

---

## ⚡ Resilience

- **Circuit Breaker** via Resilience4j protects inter-service calls
- Custom `GlobalExceptionHandler` ensures consistent error responses across all services

---

## 🗂️ Error Response Format

```json
{
  "error": "Produto não encontrado com o id informado",
  "status": 404,
  "timestamp": "2026-03-10T09:54:00.000"
}
```

---

## 🗺️ Roadmap

- [x] Service Discovery with Eureka
- [x] API Gateway with Spring Cloud Gateway
- [x] Inter-service communication with Feign Client
- [x] Circuit Breaker with Resilience4j
- [x] Database migrations with Flyway
- [ ] Redis caching
- [ ] Async messaging with RabbitMQ
- [ ] Docker Compose setup
- [ ] Distributed tracing with Zipkin

---

<div align="center">

Made with ❤️ by **Rafael Bessa**

</div>
