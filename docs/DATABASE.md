# Local database

The app uses an in-memory H2 database named `testdb`. No external database
installation is required. Data is temporary and the startup SQL recreates
both tables, so restarting the app restores the seed records.

`src/main/resources/data.sql` defines the account and message tables, the
unique username constraint, and the message author foreign key. Seed accounts
have IDs 9996–9999 and password `password`. Seed messages have IDs 9996,
9997, and 9999. High seed IDs keep generated test IDs separate.

Hibernate initializes the JPA schema before the deferred SQL script runs.
The H2 browser console is disabled by default. For local debugging only,
start with `mvn spring-boot:run -Dspring-boot.run.arguments=--spring.h2.console.enabled=true`.

This is an educational API: passwords are stored and returned as plain text
under the assignment contract, login does not establish an authenticated
session, and callers provide author IDs. Do not use real credentials.
Production use requires authentication, password hashing, response DTOs,
a durable database, and migrations instead of destructive startup SQL.
