# Auth Service – (Spring Boot)

A production-ready **Authentication microservice** built with **Spring Boot**, **Hexagonal (Ports & Adapters)**, and **DDD-friendly** principles.

This service is designed to be **framework-independent, testable, and scalable**, suitable for microservice systems.

---

## ✨ Features

- Login & credential management
- Password verification (BCrypt)
- JWT token issuance
- External Identity Service integration
- Clear separation of concerns
- Environment-based configuration (dev / stag / prod)

---

## 🧱 Architecture Overview

This project follows **Clean Architecture** with **Hexagonal design**:

┌───────────────┐
│ API │ → Controllers, DTOs, HTTP, Filters
└───────▲───────┘
│
┌───────┴───────┐
│ Application │ → Use cases, ports, orchestration
└───────▲───────┘
│
┌───────┴───────┐
│ Domain │ → Business rules, entities
└───────▲───────┘
│
┌───────┴───────────────┐
│ Infrastructure │ → JWT, DB, HTTP clients, security
└───────────────────────┘


### Dependency Rule

> **Dependencies always point inward.**  
> Domain and Application layers never depend on Infrastructure or API.

---

## 📁 Project Structure

```text
auth-service
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.auth
│   │   │       ├── api
│   │   │       │   ├── AuthController
│   │   │       │   ├── dto
│   │   │       │   ├── error
│   │   │       │   └── filter
│   │   │       │
│   │   │       ├── application
│   │   │       │   ├── exception
│   │   │       │   ├── port
│   │   │       │   └── service
│   │   │       │
│   │   │       ├── domain
│   │   │       │   ├── Credential
│   │   │       │   ├── CredentialRepository
│   │   │       │   ├── CredentialStatus
│   │   │       │   └── IdentityStatus
│   │   │       │
│   │   │       ├── infrastructure
│   │   │       │   ├── config
│   │   │       │   ├── identity
│   │   │       │   ├── persistence
│   │   │       │   ├── security
│   │   │       │   └── token
│   │   │       │
│   │   │       └── AuthServiceApplication
│   │   │
│   │   └── resources
│   │       ├── application.properties
│   │       ├── application-dev.yml
│   │       ├── application-stag.yml
│   │       └── application-prod.yml
│   │
│   └── test
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
└── README.md
