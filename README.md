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
