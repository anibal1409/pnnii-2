---
name: programa-consola
description: Crea un programa Java de consola para PNN II cumpliendo las restricciones del material (una clase con main y métodos static, arreglos, comentarios línea a línea). Usar cuando se pida un programa de consola, un ejercicio resuelto o la versión entregable de un algoritmo de la asignatura.
---

# Programa de consola

Las convenciones completas están en `.cursor/rules/java-consola.mdc`. Esta skill es el flujo de trabajo.

## Pasos

1. **Nombre**: usa PascalCase en español, que describa el algoritmo (`BusquedaBinaria`, `QuicksortRecursivo`, `NReinas`). Ubícalo en `consola/unidadN/<Nombre>.java`.
2. **Plantilla**: copia la estructura de `consola/unidad1/InsertarMaxHeap.java`: encabezado, `main` con datos de ejemplo, métodos `static` con el algoritmo y `mostrar` para imprimir.
3. **Algoritmo**: impleméntalo desde cero con arreglos. Si es recursivo (unidades II y V), la recursión va en un método `static` de la misma clase.
4. **Comentarios**: escribe uno por línea, explicando la intención.
5. **Calcula a mano** la salida esperada para los datos de `main` antes de ejecutar.
6. **Verifica** con `scripts/verificar.sh unidadN` (compila con `-Xlint:all` y ejecuta). Compara la salida real con tu cálculo. Si difiere, corrige el programa, no la expectativa, salvo que hayas demostrado que el cálculo estaba mal.
7. Si el programa replica un ejemplo del material cuya salida publicada es incorrecta, documéntalo en `material/unidades/unidad-N-erratas.md`.

## Lista de comprobación

- [ ] No tiene `package` y tiene una sola clase pública con `main`.
- [ ] No usa colecciones ni `Arrays.sort`. Solo `System`, `Math`, `String` y, si hace falta, `Scanner`.
- [ ] Identificadores y salida en ASCII; comentarios en español en cada línea.
- [ ] El encabezado incluye unidad, tema, objetivo, complejidad y el comando de ejecución.
- [ ] Compila sin advertencias, ejecuta sin excepciones y la salida está verificada.
- [ ] Si tiene práctica de laboratorio, esa práctica lo referencia en `programaConsola()`.
