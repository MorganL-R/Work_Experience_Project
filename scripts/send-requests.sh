#!/usr/bin/env bash
#
# Sends 15 POST requests to the /submitForm endpoint.
#
# Usage:
#   ./scripts/send-requests.sh                              # defaults to http://localhost:8080/submitForm
#   ./scripts/send-requests.sh http://localhost:9090/submitForm
#
# Field rules enforced by FormController:
#   name        -> letters only, max 30 chars
#   phoneNumber -> 10 to 15 digits
#   email       -> valid email address
#   dob         -> ISO date (yyyy-MM-dd), in the past, within 125 years

set -uo pipefail

URL="${1:-http://localhost:8080/submitForm}"

names=(
  "Alice" "Bob" "Charlie" "Diana" "Edward"
  "Fiona" "George" "Hannah" "Isaac" "Julia"
  "Kevin" "Laura" "Michael" "Nadia" "Oliver"
)

phones=(
  "0712345678" "0723456789" "0734567890" "0745678901" "0756789012"
  "0767890123" "0778901234" "0789012345" "0790123456" "0701234567"
  "0711122233" "0722233344" "0733344455" "0744455566" "0755566677"
)

dobs=(
  "1990-01-15" "1985-06-30" "2000-11-02" "1978-03-21" "1995-09-09"
  "1992-04-18" "1988-12-05" "2001-07-23" "1975-02-14" "1999-10-31"
  "1983-08-08" "1997-05-27" "1970-09-12" "2003-03-03" "1993-11-19"
)

OUT_DIR="$(mktemp -d)"

echo "Sending ${#names[@]} requests to $URL"
echo "Responses saved in $OUT_DIR"
echo

failures=0

for i in "${!names[@]}"; do
  name="${names[$i]}"
  phone="${phones[$i]}"
  dob="${dobs[$i]}"
  email="$(echo "$name" | tr '[:upper:]' '[:lower:]')@example.com"

  response="$OUT_DIR/response-$((i + 1)).html"

  status=$(curl -s -o "$response" -w "%{http_code}" \
    -X POST "$URL" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    --data-urlencode "name=$name" \
    --data-urlencode "phoneNumber=$phone" \
    --data-urlencode "email=$email" \
    --data-urlencode "dob=$dob")

  curl_exit=$?

  if [[ $curl_exit -ne 0 ]]; then
    echo "Request $((i + 1)): $name -> curl failed (exit $curl_exit). Is the app running?"
    failures=$((failures + 1))
    continue
  fi

  if [[ "$status" == 2* ]]; then
    echo "Request $((i + 1)): $name ($email, $phone, $dob) -> HTTP $status OK"
  else
    echo "Request $((i + 1)): $name ($email, $phone, $dob) -> HTTP $status FAILED (see $response)"
    failures=$((failures + 1))
  fi
done

echo
if [[ $failures -eq 0 ]]; then
  echo "All requests succeeded."
else
  echo "$failures request(s) failed."
fi

exit $((failures > 0))



