# Contributing

Use JDK 11 and Maven. Run `mvn verify` before submitting a change.
The HTTP integration tests start the application on port 8080, so stop any
local instance first. Do not run the integration suite in parallel.

Keep the assignment API response statuses and JSON field names stable.
The provided entity classes and application bootstrap are assignment fixtures;
prefer changes in services, repositories, and controllers.

Add regression tests for behavioral fixes. Use focused commit messages that
explain the change. Do not commit build output, credentials, or editor state.
GitHub Actions runs the full Maven verification on pushes and pull requests.
