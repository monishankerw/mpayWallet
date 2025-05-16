
⸻


# 💳 Mpay Wallet – Secure Digital Wallet System

## 🧾 Overview

**Mpay Wallet** is a Java + Spring Boot-based digital wallet system that allows users to register, create a wallet, link bank accounts, perform secure transactions, manage beneficiaries, and view transaction history. JWT authentication and password encryption are implemented to protect users and secure API access.

> 🔐 Project built by a team of 5 developers in a 4-day construct week.

---

## 📌 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [System Architecture](#system-architecture)
- [Project Modules](#project-modules)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Security](#security)
- [Screenshots / Swagger](#screenshots--swagger)
- [Contributors](#contributors)

---

## ✅ Features

### 👤 Customer:
- User registration with validation
- Password encryption using BCrypt
- Login with JWT token generation
- Profile details returned after login

### 💼 Wallet:
- Automatically created after registration
- Starts with ₹0 balance
- Linked 1:1 with customer

### 🏦 Bank Account:
- Link bank accounts to wallet
- Store account number, IFSC, balance
- Used to top-up wallet

### 🔁 Transactions:
- Bank ➡ Wallet transfer
- Wallet ➡ Wallet transfer
- View transactions by:
  - Type (CREDIT / DEBIT)
  - Transaction ID
  - Date

### 👥 Beneficiaries:
- Add trusted wallet users by mobile
- Simplify future transactions

---

## 💻 Tech Stack

| Layer           | Tech                                      |
|------------------|--------------------------------------------|
| Language         | Java 17                                   |
| Backend          | Spring Boot 3.4.5                         |
| Database         | MySQL                                     |
| Build Tool       | Gradle                                    |
| Security         | Spring Security + JWT                     |
| Validation       | Hibernate Validator (JSR-380)             |
| Logging          | SLF4J + Logback                           |
| Testing          | JUnit, Mockito                            |
| API Testing Tool | Postman / Swagger                         |
| VCS              | Git + GitHub                              |

---

## 🧱 System Architecture

+———––+       +———––+       +—————–+       +———––+
| Frontend UI | <—> | Spring Boot | <—> | MySQL Database  | <—> | Wallet Flow |
+———––+       +———––+       +—————–+       +———––+

Modules:
	•	Customer → Wallet (1:1)
	•	BankAccount → Customer (Many:1)
	•	Transactions → Wallet (Many:1)
	•	Beneficiary → Customer (Many:1)

---

## 🗂️ Project Modules

### 📁 Customer Module
- Register, Login
- Secure password storage
- JWT token generation

### 📁 Wallet Module
- Created on registration
- Balance update during transactions

### 📁 BankAccount Module
- Add account (accountNumber, IFSC)
- Used for wallet top-up

### 📁 Transaction Module
- Transfer from bank to wallet
- Wallet to wallet transfers
- Transaction history & filtering

### 📁 Beneficiary Module
- Add/Remove beneficiary
- Validate via mobile number

---

## 🚀 Getting Started

### 🔧 Prerequisites
- Java 17
- Gradle
- MySQL
- Postman or Swagger for testing

### 📦 Setup

1. Clone repo:
   ```bash
   git clone https://github.com/your-repo/mpay-wallet.git
   cd mpay-wallet

	2.	Configure DB in application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/mpay_wallet
spring.datasource.username=root
spring.datasource.password=yourpassword


	3.	Run the application:

./gradlew bootRun


	4.	Access Swagger UI (if enabled):

http://localhost:8080/swagger-ui/index.html



⸻

🔐 Security
	•	Passwords encoded using BCryptPasswordEncoder
	•	JWT Token included in Authorization header as:

Authorization: Bearer <token>


	•	Secure endpoints:
	•	/transfer
	•	/add-bank-account
	•	/add-beneficiary
	•	/transactions/*
	•	etc.

⸻

📬 API Endpoints

🔓 Public APIs

Method	Endpoint	Description
POST	/customers/register	Register a new user
POST	/customers/login	Login and get JWT token

🔐 Protected APIs (JWT Required)

Method	Endpoint	Description
POST	/wallets/add-bank-account	Add bank to wallet
POST	/wallets/bank-to-wallet	Transfer from bank to wallet
POST	/wallets/wallet-to-wallet	Send money to another wallet
GET	/transactions/by-id/{id}	Get transaction by ID
GET	/transactions/by-date?date=…	Filter transactions by date
POST	/beneficiaries/add	Add a new beneficiary


⸻

🧪 Sample Request: Registration

POST /customers/register
Content-Type: application/json

{
  "customerName": "Moni Shanker",
  "customerAddress": "Delhi",
  "customerState": "Delhi",
  "mobile": "9876543210",
  "email": "moni@example.com",
  "password": "password123"
}


⸻

🧪 Sample Request: Login

POST /customers/login
Content-Type: application/json

{
  "mobile": "9876543210",
  "password": "password123"
}

Response:

{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}


⸻

🧑‍💻 Contributors

Name	Role
Moni Shanker	Backend Developer
Member 2	Wallet/Bank Flow
Member 3	Transaction APIs
Member 4	JWT + Security
Member 5	Swagger + Testing


⸻

📌 License

This project is built as part of the Construct Week under academic learning. Open for learning and improvement.

⸻


