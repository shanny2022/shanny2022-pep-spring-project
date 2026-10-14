# Spring Social Media Blog API

## Background

I built this project as a Java backend for a micro-blogging application using Spring Boot. It manages user accounts and messages, supporting registration, login, and creating, viewing, updating, and deleting posts.

I use Spring MVC to handle HTTP requests and Spring Data JPA to manage database operations. My application also allows users to retrieve all messages or view messages posted by a specific account.

## Database Tables

My database initializes at startup using the SQL script and configuration in `application.properties`.

### Account

```sql
account_id integer primary key auto_increment,
username varchar(255) not null unique,
password varchar(255)
```

### Message

```sql
message_id integer primary key auto_increment,
posted_by integer,
message_text varchar(255),
time_posted_epoch long,
foreign key (posted_by) references Account(account_id)
```

## Spring Architecture

I organize the backend into controller, service, and repository layers:

- `SocialMediaController` handles API requests and responses.
- `AccountService` handles account registration and login validation.
- `MessageService` handles message validation and operations.
- `AccountRepository` and `MessageRepository` extend `JpaRepository` for their corresponding entities.

I use Spring annotations and dependency injection to connect these components. The project requirements include functional tests and a `SpringTest` that checks bean configuration, JPA repositories, and Spring MVC’s default error response structure.

## API Endpoints

I use `http://localhost:8080` as the base URL.

### 1. Register an Account

`POST /register`

I accept an account as JSON without an `account_id`. I require a nonblank username, a password containing at least four characters, and a username that is not already registered.

- **200 OK:** I save and return the account, including its generated `account_id`.
- **409 Conflict:** The username already exists.
- **400 Bad Request:** Other registration validation fails.

### 2. Log In

`POST /login`

I check whether the submitted username and password match an existing account.

- **200 OK:** I return the matching account as JSON, including its `account_id`.
- **401 Unauthorized:** The credentials do not match an existing account.

### 3. Create a Message

`POST /messages`

I accept a message as JSON without a `message_id`. I require nonblank `message_text` containing no more than 255 characters and a `posted_by` value that identifies an existing account.

- **200 OK:** I save and return the message, including its generated `message_id`.
- **400 Bad Request:** Message validation fails.

### 4. Retrieve All Messages

`GET /messages`

I return all stored messages as a JSON array with **200 OK**. If no messages exist, I return an empty array.

### 5. Retrieve a Message by ID

`GET /messages/{message_id}`

I return the matching message as JSON with **200 OK**. If the message does not exist, I return an empty response body with **200 OK**.

### 6. Delete a Message

`DELETE /messages/{message_id}`

I remove the matching message from the database.

- **200 OK** with a response body of `1`: I deleted one message.
- **200 OK** with an empty response body: The message did not exist.

Repeating the request leaves the message deleted.

### 7. Update Message Text

`PATCH /messages/{message_id}`

I accept a JSON body containing the replacement `message_text`. I require an existing message ID and nonblank text containing no more than 255 characters. Only the message text is updated.

- **200 OK** with a response body of `1`: I updated one message.
- **400 Bad Request:** The message does not exist or the new text is invalid.

### 8. Retrieve Messages by Account

`GET /accounts/{account_id}/messages`

I return the account’s messages as a JSON array with **200 OK**. If no matching messages exist, I return an empty array.
