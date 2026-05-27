# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A Java-based Low-Level Design (LLD) implementation of a parking lot management system. This is a learning/design project — no build system or test framework is currently configured.

## Build & Run

Plain Java project with no Maven/Gradle. Compile manually:

```bash
javac -d bin adapter/*.java controller/*.java domain/*.java repository/*.java service/*.java
```

There is no `main` entry point class yet. The project is instantiated by calling `AdminController.initializeParkingLot()` and then using `EntryController` / `ExitController` programmatically.

## Architecture

The project follows a strict 4-layer architecture. Dependencies only flow downward:

```
controller/ → service/ → repository/ → domain/
adapter/    (external payment gateways, used by service/)
```

**Domain** (`domain/`): Plain Java entities — `Vehicle`, `Ticket`, `ParkingSlot`, `Floor`, `Payment`, `Receipt`, `PricingRule`. No business logic lives here.

**Repository** (`repository/`): In-memory persistence via `ConcurrentHashMap`. No database. Each repository owns one entity type.

**Service** (`service/`): All business logic. Key services:
- `SlotService` — slot allocation/release by vehicle type
- `PricingService` — fee calculation: `min(flatRate, hours × hourlyRate)` per `PricingRule`
- `PaymentService` — processes payment with retry across multiple gateways (Strategy pattern)
- `TicketService`, `ReceiptService` — lifecycle management

**Controller** (`controller/`): Orchestrates services for three operations:
- `EntryController.enterVehicle()` — allocate slot → generate ticket
- `ExitController.exitVehicle()` — calculate fee → process payment → generate receipt → release slot → deactivate ticket
- `AdminController` — set up floors, slots, and default pricing rules

**Adapter** (`adapter/`): `PaymentGatewayAdapter` interface with `StripeAdapter` and `RazorPayAdapter` implementations. Both simulate 90% success rates. `PaymentService` cycles through adapters on failure.

## Key Design Patterns

- **Strategy**: `PaymentGatewayAdapter` — swap payment providers at runtime
- **Repository**: In-memory data access abstraction behind interfaces
- **Adapter**: External payment gateway integration

## Known Issues in the Codebase

- Typos: `isOcuupied` (should be `isOccupied` in `ParkingSlot`), `markAsScuccess` (should be `markAsSuccess` in `Payment`)
- No exception handling for "slot not found" scenarios in `SlotService`
- No input validation at controller layer
- Logging via `System.out`/`System.err` (no logging framework)
