#!/bin/sh
# Run against a locally running app; all requests are read-only.
set -eu
base_url=${1:-http://localhost:8080}
for path in /messages /messages/9999 /accounts/9999/messages; do
    curl --fail --silent --show-error "$base_url$path"
    printf '\n'
done
