#!/usr/bin/env bash
set -euo pipefail
BASE_URL="${BASE_URL:-http://localhost:8080}"
curl -fsS "$BASE_URL/health" || true
curl -fsS -X POST "$BASE_URL/api/v1/accounts" \
  -H 'Content-Type: application/json' \
  -d '{"customerId":"CUST1001","accountId":"ACC5001","accountType":"INVESTMENT","status":"OPEN","assetValue":500000}'
