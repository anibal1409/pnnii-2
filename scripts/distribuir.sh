#!/usr/bin/env bash
# Genera en distribucion/ los paquetes del laboratorio para compartir con estudiantes:
#   Laboratorio-PNN-II-Windows.zip   Windows x64, trae Java incluido (descomprimir y abrir)
#   Laboratorio-PNN-II-Linux.tar.gz  Linux x64, trae Java incluido
#   Laboratorio-PNN-II.jar           cualquier sistema con Java 17+ (p. ej. macOS)
set -euo pipefail
cd "$(dirname "$0")/.."

nombre="Laboratorio-PNN-II"
dist="$PWD/distribucion"
cache="$dist/.cache"
jar="laboratorio/target/laboratorio.jar"
leeme="laboratorio/empaquetado/LEEME.txt"
icono="laboratorio/src/main/resources/icono.png"
trabajo="$(mktemp -d)"
trap 'rm -rf "$trabajo"' EXIT
mkdir -p "$dist" "$cache"
rm -f "$dist/$nombre"*

echo "== JAR"
(cd laboratorio && ./mvnw -q -B clean package)
cp "$jar" "$dist/$nombre.jar"
modulos="$(jdeps --print-module-deps --ignore-missing-deps --multi-release 17 "$jar")"
echo "   modulos de Java necesarios: $modulos"

echo "== Linux (jpackage con Java reducido via jlink)"
mkdir -p "$trabajo/entrada"
cp "$jar" "$trabajo/entrada/laboratorio.jar"
jpackage --type app-image --name "$nombre" --app-version 1.0.0 \
    --input "$trabajo/entrada" --main-jar laboratorio.jar --main-class pnni.laboratorio.App \
    --icon "$icono" --add-modules "$modulos" \
    --jlink-options "--strip-debug --no-man-pages --no-header-files --compress=2" \
    --dest "$trabajo/linux"
cp "$leeme" "$trabajo/linux/$nombre/LEEME.txt"
tar -C "$trabajo/linux" -czf "$dist/$nombre-Linux.tar.gz" "$nombre"

echo "== Windows (runtime Eclipse Temurin 17 + lanzador .bat)"
jre_zip="$cache/temurin-jre17-windows-x64.zip"
if [ ! -f "$jre_zip" ]; then
    curl -fL --progress-bar -o "$jre_zip" \
        "https://api.adoptium.net/v3/binary/latest/17/ga/windows/x64/jre/hotspot/normal/eclipse"
fi
win="$trabajo/windows/$nombre"
mkdir -p "$win" "$trabajo/jre"
unzip -q "$jre_zip" -d "$trabajo/jre"
mv "$trabajo"/jre/*/ "$win/runtime"
cp "$jar" "$win/laboratorio.jar"
sed 's/$/\r/' "$leeme" > "$win/LEEME.txt"
printf '@echo off\r\nstart "" "%%~dp0runtime\\bin\\javaw.exe" -jar "%%~dp0laboratorio.jar"\r\n' \
    > "$win/Abrir Laboratorio.bat"
(cd "$trabajo/windows" && zip -qr9 "$dist/$nombre-Windows.zip" "$nombre")

echo
ls -lh "$dist" | grep "$nombre"
echo "Listo: comparte los archivos de $dist"
