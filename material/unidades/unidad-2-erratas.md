# Unidad II: erratas detectadas en el material

Todas se verificaron ejecutando el código del material. Los programas de `consola/unidad2/` ya usan la versión corregida.

| Sección | Problema en el material | Corrección aplicada |
|---|---|---|
| 5.1 Ejercicio resuelto 2 (peor caso de Quicksort) | El texto dice que el total de comparaciones es n + (n−1) + … + 1 = n(n+1)/2, pero la primera partición compara n−1 elementos con el pivote, no n. El propio código del material imprime n(n−1)/2 = 28. | El total es (n−1) + (n−2) + … + 1 = n(n−1)/2 = 28 para n = 8. Programa: `PeorCasoQuicksort.java`. |
| 4.2 Diagrama de máximos y mínimos | El diagrama divide hasta tener un solo elemento (`[8][3] [15][6]…`), pero el algoritmo se detiene en tramos de dos elementos (caso base 2) y los resuelve con una comparación. Por eso hace 3n/2 − 2 comparaciones. | El programa cuenta las comparaciones: con n = 8 son 10 = 3·8/2 − 2. Programa: `MaxMinDYC.java`. |
| 2.2 Tabla Big O | Pone como ejemplo de O(2ⁿ) el «problema del viajante (solución ingenua)». La solución ingenua prueba todas las rutas y es O(n!); el orden O(n²·2ⁿ) corresponde a la programación dinámica (Unidad IV). | Solo es una nota; no afecta a ningún programa. |
| 6.3 `MergeSortIterativo` | El identificador `tamaño` usa la letra ñ (ver la errata general de la Unidad I). | Se usa `tamano`. Programa: `MergeSortIterativo.java`. |
| Numeración | Los casos prácticos y los ejercicios aparecen como secciones 4 y 5, después de la 7 (el índice los numera 8 y 9). | Los programas citan la numeración del cuerpo del material. |
