#!/usr/bin/env bash
# Compila con -Xlint y ejecuta todos los programas de consola; luego compila el laboratorio.
# Uso: scripts/verificar.sh [unidadN]   (sin argumento verifica todas las unidades)
set -uo pipefail
cd "$(dirname "$0")/.."

objetivo="consola/${1:-}"
fallos=0
salida_tmp="$(mktemp -d)"
trap 'rm -rf "$salida_tmp"' EXIT

while IFS= read -r archivo; do
  echo "== $archivo"
  if ! javac -Xlint:all -encoding UTF-8 -d "$salida_tmp" "$archivo"; then
    echo "   ERROR de compilacion"; fallos=$((fallos + 1)); continue
  fi
  if ! java "$archivo"; then
    echo "   ERROR de ejecucion"; fallos=$((fallos + 1))
  fi
done < <(find "$objetivo" -name '*.java' | sort)

if [ -z "${1:-}" ] && [ -d laboratorio ]; then
  echo "== laboratorio (mvn compile)"
  (cd laboratorio && ./mvnw -q compile) || fallos=$((fallos + 1))
fi

echo
[ "$fallos" -eq 0 ] && echo "OK: todo compila y ejecuta" || echo "FALLOS: $fallos"
exit "$fallos"
