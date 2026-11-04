# Layaway Ledger

## Problem Statement

Small shop owners in Zambia (hardware stores, furniture shops, second-hand clothing traders) let customers reserve items they cannot afford immediately — the shop holds the item while the customer pays in instalments, and the customer collects it only once fully paid. This layaway process is tracked in paper notebooks, so deposits get lost, customers dispute what they have paid, and shop owners cannot tell at a glance which items are reserved, part-paid, or ready for collection.

Layaway Ledger digitises the layaway workflow. Instead of a paper notebook, the shop owner records customers, items, reservations, and payments in a single system that shows outstanding balances, overdue reservations, and items ready for collection in real time.

## Project Overview

Layaway Ledger is a Java-based reservation and payment tracking system designed for small retailers. Each reservation has a status that updates automatically:
- **ACTIVE** — the reservation is in progress and payments are still being made
- **COMPLETED** — the full amount has been paid; the item is ready for collection

The system allows shopkeepers to:
- Register customers
- Manage items available for reservation
- Create layaway reservations with a weekly or monthly payment plan
- Record instalment payments
- Calculate outstanding balances automatically
- Track overdue reservations
- Identify reservations ready for collection
- Cancel a reservation if needed

## Technologies

- Java 17
- Maven
- Javalin 6.7
- Jackson (JSON serialization, including `JavaTimeModule` for `LocalDate`)
- HTML / CSS / JavaScript
- JUnit 5

## Project Structure

The project uses a layered structure separating domain models, business logic, the REST API, and the frontend.
src/main/java/zm/ac/layaway/
model/ Customer, Item, Reservation, LayawayReservation,
DepositReservation, Payment, PaymentPlan,
WeeklyInstalment, MonthlyInstalment,
ItemStatus, ReservationStatus
exception/ ReservationException
server/ ReservationServer (Javalin)
src/main/resources/public/
index.html, app.js, styles.css


## OOP Concepts Demonstrated

- **Inheritance:** `Reservation` (abstract) → `LayawayReservation` and `DepositReservation`
- **Interface:** `PaymentPlan` implemented by `WeeklyInstalment` and `MonthlyInstalment`
- **Polymorphism:** `Reservation` holds a `PaymentPlan` and calls `nextDueDate()` without knowing the concrete type — weekly returns +7 days, monthly returns +30 days (demonstrated live via `GET /schedule/{id}`)
- **Encapsulation:** private fields with getters/setters and validation
- **Custom exception:** `ReservationException`, thrown on invalid operations and returned as HTTP 400
- **Collections:** `ArrayList` of customers, items, and reservations

## Team

| Full Name | Student ID |
|---|---|
| Davies Manchishi | 2410093 |
| Loveness Lyaunga | 2410060 |
| Prince Zulu | 2410237 |
| Thabiso Moyo | 2410190 |

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
- `GET /reservations` — List all reservations
- `GET /overdue` — List active (unpaid) reservations
- `GET /ready` — List fully paid reservations ready for collection
- `GET /schedule/{id}` — Show payment plan, next due date, and instalment amount (demonstrates polymorphism: weekly vs monthly)
- `DELETE /reservations/{id}` — Cancel a reservation

## Demo Access Gate

The front end includes a simple sign-in screen for demonstration:
- Username: `admin`
- Password: `admin123`

Full authentication (password hashing, sessions, role-based access) was listed as out of scope in the approved proposal.

## Future Work

- File-based persistence (data currently stored in memory; survives until the server restarts)
- User accounts and role-based access
- SMS reminders for upcoming due dates
- Stock/inventory management
