# Project Report — Hotel Reservation System

## Objective
Build a console-based Java system that manages rooms and customer reservations.

## Functional Requirements
1. Display available rooms.
2. Reserve an available room.
3. Calculate total charges.
4. View and search reservations.
5. Cancel reservations and release rooms.
6. Display basic hotel statistics.

## OOP Concepts
**Encapsulation:** Room and Reservation fields are private and accessed through methods.

**Classes and Objects:** `Room` represents a hotel room; `Reservation` represents a booking.

**Methods:** Separate methods handle booking, cancellation, search, statistics, display, and validation.

## Data Structures
`ArrayList<Room>` stores rooms and `ArrayList<Reservation>` stores bookings.

## Validation
The program validates non-empty names, 10-digit phone numbers, valid room numbers, positive nights, and room availability.

## Testing
Tested room listing, booking, reservation search, statistics, cancellation, and invalid input handling.

## Limitations
Data is stored only while the application runs. A production system could add a database, authentication, date-based availability, payment integration, and a web/GUI interface.

## Conclusion
The project demonstrates practical Java skills including OOP, collections, methods, loops, conditions, validation, and business logic.
