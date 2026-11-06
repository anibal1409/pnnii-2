#!/usr/bin/env bash
# Ejecuta un programa de consola por su nombre de clase, sin compilar a mano.
# Uso: scripts/consola.sh InsertarMaxHeap      (busca en consola/unidad*/)
set -euo pipefail
cd "$(dirname "$0")/.."

if [ $# -ne 1 ]; then
  echo "Uso: scripts/consola.sh NombreDelPrograma"
  echo "Disponibles:"; find consola -name '*.java' | sort | sed 's#^#  #'
  exit 1
fi

archivo="$(find consola -name "${1%.java}.java" | head -n 1)"
[ -n "$archivo" ] || { echo "No existe ${1%.java}.java en consola/"; exit 1; }
exec java "$archivo"
