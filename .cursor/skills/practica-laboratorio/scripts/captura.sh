#!/usr/bin/env bash
# Captura una practica del laboratorio en un paso concreto.
# Uso: captura.sh <oscuro|claro> <indicePractica> <paso> <salida.png>
set -euo pipefail
[ $# -eq 4 ] || { echo "Uso: captura.sh <oscuro|claro> <indicePractica> <paso> <salida.png>"; exit 1; }

aqui="$(cd "$(dirname "$0")" && pwd)"
lab="$aqui/../../../../laboratorio"
cd "$lab"
./mvnw -q -B compile
cp_file="target/classpath.txt"
[ -f "$cp_file" ] || ./mvnw -q -B dependency:build-classpath -Dmdep.outputFile="$cp_file"
salida="$4"
case "$salida" in /*) ;; *) salida="$OLDPWD/$salida" ;; esac
java -cp "target/classes:$(cat "$cp_file")" "$aqui/Captura.java" "$1" "$2" "$3" "$salida"
