# Troubleshooting

- **Port 8080 already in use:** stop the existing app before running tests.
  To run the app on another port, use
  `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081`.
- **Wrong Java version:** check `java -version` and `mvn -version`; use JDK 11.
- **Maven cannot download dependencies:** check your network and Maven proxy
  settings, then rerun `mvn verify`.
- **Unexpected empty response:** missing message GET and DELETE requests
  intentionally return status 200 with an empty body. List endpoints return `[]`.
- **POST or PATCH returns 400:** verify JSON syntax, the content type header,
  required fields, text length, and author existence. Whitespace-only text
  is invalid.
- **Registration returns 409:** choose a username that is not already stored.
- **Data disappears after restart:** the default H2 database is temporary;
  see [database behavior](DATABASE.md).

Test diagnostics are written to `target/surefire-reports/`. Use
`mvn -Dtest=AccountServiceValidationTest,MessageServiceValidationTest test`
for the fast validation regression tests, or `mvn verify` for the full suite.
Run `sh scripts/smoke.sh` after starting the app for a read-only HTTP check.
