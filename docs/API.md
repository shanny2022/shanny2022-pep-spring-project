# API reference

Send JSON request bodies with `Content-Type: application/json`.

| Method | Path | Success body | Failure status |
| --- | --- | --- | --- |
| POST | `/register` | Account with generated ID | 400 invalid input; 409 duplicate username |
| POST | `/login` | Matching account | 401 invalid credentials |
| POST | `/messages` | Message with generated ID | 400 invalid input or unknown author |
| GET | `/messages` | Message array | — |
| GET | `/messages/{id}` | Message, or empty body when absent | — |
| PATCH | `/messages/{id}` | Number of updated rows | 400 invalid text or missing message |
| DELETE | `/messages/{id}` | 1, or empty body when absent | — |
| GET | `/accounts/{id}/messages` | Message array, possibly empty | — |

Successful responses use status 200. Usernames and message text must not be
blank. Passwords must contain 4–255 characters; usernames and message text
have a 255-character limit. Input is preserved without trimming.
Creation ignores caller-supplied IDs. PATCH changes only message text.

```sh
curl -i -H 'Content-Type: application/json' \
  -d '{"username":"reader","password":"pass"}' http://localhost:8080/register
curl -i -H 'Content-Type: application/json' \
  -d '{"username":"reader","password":"pass"}' http://localhost:8080/login
curl -i -H 'Content-Type: application/json' \
  -d '{"posted_by":9999,"message_text":"Hello!","time_posted_epoch":1669947792}' \
  http://localhost:8080/messages
curl -i http://localhost:8080/accounts/9999/messages
```

Use the returned message ID for later PATCH and DELETE calls. Epoch values
represent seconds since January 1, 1970 and are supplied by the client.
