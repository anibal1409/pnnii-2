---
name: practica-laboratorio
description: Crea o modifica una práctica interactiva del laboratorio gráfico de PNN II (Swing + FlatLaf) que traza un algoritmo paso a paso con código resaltado, variables, salida y una visualización animada, y la verifica con capturas. Usar cuando se pida una interfaz gráfica, una práctica de laboratorio, una visualización de un algoritmo o un cambio visual en `laboratorio/`.
---

# Práctica del laboratorio

La arquitectura y las reglas de UI/UX están en `.cursor/rules/laboratorio-gui.mdc`. Toma como referencia `laboratorio/src/main/java/pnni/laboratorio/unidad1/`.

## Pasos

1. **Programa de consola primero**: la práctica reproduce un programa de `consola/unidadN/` (usa la skill `programa-consola` si todavía no existe).
2. **Código mostrado**: escribe un text block con la versión limpia del algoritmo (métodos `static`) y añade la marca ` @@nombre` al final de cada línea que vaya a resaltarse. Varias líneas pueden compartir una marca (por ejemplo, las tres de un intercambio).
3. **Escena**: reutiliza una existente (`EscenaHeap` sirve para árboles en arreglo) o crea `unidadN/Escena<Algo>.java`, que implementa `Escena`. Debe:
   - copiar sus datos (ser inmutable);
   - dibujar con `Paleta p` y los roles de color estándar;
   - mostrar una leyenda y los punteros con los nombres de las variables;
   - animar la transición usando `progreso` (de 0 a 1);
   - adaptarse al ancho y alto disponibles, sin desbordarse con la entrada máxima permitida.
4. **Práctica**: crea `unidadN/Practica<Algo>.java`, que implementa `Practica`:
   - `parametros()`: los datos de ejemplo del material como valores por defecto.
   - `ejecutar()`: valida la entrada, crea `new Traza(new Codigo(CODIGO))`, ejecuta el algoritmo real llamando a `t.var(...)` y `t.paso(marca, titulo, explicacion, escena)` en cada decisión, e imprime con `t.imprimirLinea` lo mismo que el programa de consola.
   - El último paso muestra el resultado final con su insignia de validez.
5. **Registro**: añade la práctica a `nucleo/Catalogo.java`.
6. **Compila**: `cd laboratorio && ./mvnw -q -B compile`. Debe quedar sin errores ni advertencias.
7. **Verifica la traza**: comprueba que la salida del último paso coincide exactamente con la del programa de consola.
8. **Verifica visualmente** con capturas en ambos temas y revisa las imágenes:

```bash
.cursor/skills/practica-laboratorio/scripts/captura.sh oscuro <indicePractica> <paso> /tmp/captura-oscuro.png
.cursor/skills/practica-laboratorio/scripts/captura.sh claro  <indicePractica> <paso> /tmp/captura-claro.png
```

`indicePractica` es la posición (desde 0) de la práctica en `Catalogo.practicas()`, y `paso` es el índice (desde 0) del paso que se quiere capturar. Busca textos cortados, elementos superpuestos, contraste bajo y espacio desaprovechado. Corrige y vuelve a capturar.

## Escribir buenas explicaciones

- **Título**: la acción o la pregunta con su respuesta, por ejemplo "¿Seguir subiendo? Sí".
- **Explicación**: la operación con valores concretos (`heap[1] = 30 < heap[3] = 90 → verdadero`) y su consecuencia.
- Evita repetir lo que ya muestra la escena; aporta el porqué.
