#!/usr/bin/env bash
# Compila y abre el laboratorio grafico (la primera vez descarga Maven y FlatLaf).
set -euo pipefail
cd "$(dirname "$0")/../laboratorio"
exec ./mvnw -q -B compile exec:java
