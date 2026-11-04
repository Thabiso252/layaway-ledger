# Layaway Ledger

## Project Overview

Layaway Ledger is a Java-based reservation and
payment tracking system designed for small retailers.

The system allows shopkeepers to:

- Register customers.
- Manage items available for reservation.
- Create layaway reservations.
- Record deposits and instalment payments.
- Calculate outstanding balances.
- Track overdue reservations.
- Identify reservations ready for collection.

## Technologies

- Java
- Maven
- Javalin
- HTML
- CSS
- JavaScript
- JUnit

## Project Structure

The project uses a layered structure separating
domain models, business logic, REST API and frontend.

## Team

## Team

| Full Name | Student ID |
|---|---|
| Davies Manchishi | 2410093 |
| Loveness Lyaunga | 2410060 |
| Prince Zulu | 2410237 |
| Thabiso Moyo | 2410190 |

## Running the Application

Instructions will be added after implementation.
## Running the Application
1. `mvn clean compile`
2. `mvn exec:java`
3. Open `http://localhost:7000`
4. Login: `admin` / `admin123`

## API Endpoints
- `POST /customers` — Register a customer
- `POST /items` — Add an item
- `POST /reservations` — Create a layaway reservation
- `POST /payments` — Record a payment
- `GET /reservations` — List all
- `GET /overdue` — Active reservations
- `GET /ready` — Fully paid reservations
- `DELETE /reservations/{id}` — Cancel a reservation
- - `GET /schedule/{id}` — Show the payment plan, next due date, and instalment amount for a reservation (demonstrates polymorphism: weekly vs monthly)
