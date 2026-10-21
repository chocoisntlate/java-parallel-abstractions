#!/usr/bin/env bash
# Builds the project and runs JMH, writing results to results/<timestamp>.json.
# Extra arguments are passed to JMH (e.g. a benchmark name regex).
set -euo pipefail
cd "$(dirname "$0")/.."
mvn -q package -DskipTests
java -jar target/benchmarks.jar -rf json -rff "results/$(date +%Y%m%d-%H%M%S).json" "$@"
