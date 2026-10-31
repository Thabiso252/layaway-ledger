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

Davis Manchishi    2410093
Loveness Lyaunga   2410060
Prince Zulu        2410237
Thabiso Moyo       2410190

## Running the Application

Instructions will be added after implementation.

## Running the Application

1. Make sure Java 17+ and Maven are installed.
2. Build the project: `mvn clean compile`
3. Run the server: `mvn exec:java`
4. Open your browser to `http://localhost:7000`
5. Log in with:
   - Username: `admin`
   - Password: `admin123`

## REST API Endpoints

- `POST /customers` — Add a customer
- `POST /items` — Add an item
- `POST /reservations` — Create a layaway reservation
- `POST /payments` — Record an instalment payment
- `GET /reservations` — List all reservations
- `GET /overdue` — List overdue reservations
- `GET /ready` — List items ready for collection
- `DELETE /reservations/{id}` — Delete a reservation

## Demo Access Gate

The front end includes a simple sign-in screen (demo only):
- Username: `admin`
- Password: `admin123`

Full authentication (password hashing, sessions, roles) was listed as out of scope in the approved proposal.
