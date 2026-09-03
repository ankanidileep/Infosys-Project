#!/usr/bin/env bash
set -euo pipefail
for d in services/*; do
  echo "Building $d"
  (cd "$d" && mvn -B clean test package)
done
