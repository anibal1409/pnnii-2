# Unidad III: erratas detectadas en el material

El material trae el código en Python. Se tradujo a Java con las restricciones de la asignatura y se verificaron todas las salidas. Los programas de `consola/unidad3/` dan los valores correctos.

| Sección | Problema en el material | Corrección aplicada |
|---|---|---|
| 7.1 Caso centro de datos (cintas) | Afirma que el orden óptimo de los backups da un tiempo total de 4470 y el orden descendente 6170 («un 38% peor»). | Con T = 10·30 + 9·35 + … + 1·200 el óptimo es **3190** y el descendente **5775** (81% peor). Programa: `CasoCentroDatos.java`. |
| 7.1 Caso centro de datos (logs) | Dice que el orden voraz fusiona «15+8=23, luego 10+7=17» con un costo total de 128 MB. Esas no son las dos menores (las menores son 7 y 8). | Las fusiones son 7+8 = 15, 10+15 = 25, 15+23 = 38 y 25+38 = 63: costo **141**. Programa: `CasoCentroDatos.java`. |
| 7.2 Caso transporte (Prim) | Dice que el árbol pesa 27 km con las conexiones 0-1, 0-2, 1-3, 2-4, 3-5 y 4-6, que en realidad suman 23. Ninguno de los dos valores es el mínimo. | El árbol de costo mínimo pesa **21 km**: 0-2 (3), 0-1 (4), 1-3 (5), 3-5 (2), 5-6 (3), 6-4 (4). Programa: `CasoTransporte.java`. |
| 7.2 Caso transporte (Dijkstra) | Da dist[3] = 7, dist[5] = 12 y dist[6] = 14. | Las distancias correctas son dist = **0, 4, 3, 9, 8, 11, 12**. Por ejemplo, el barrio 3 solo se alcanza por 0→1→3 = 4 + 5 = 9. Programa: `CasoTransporte.java`. |
| 1.3 Cambio de monedas | Define las monedas de 1, 5, 10 y 25 céntimos y luego resuelve 60 céntimos con una moneda de 50, que no está en ese conjunto. | Solo es una nota; no afecta a ningún programa. Con {1, 5, 10, 25}, la elección voraz da 25 + 25 + 10. |
| 4.3 Intercalación (observación) | «El costo total es independiente del orden de los pares, pero el orden de fusión afecta el costo» se contradice. | El costo sí depende del orden: con [4, 3, 2, 6], el voraz cuesta 29 y otro orden puede costar 38. Programa: `IntercalacionOptima.java`. |
| 3.5 Tabla mochila 0/1 | El texto de la fila «Ejemplo donde falla voraz» quedó con dudas a medio resolver («¿no cabe?», «Revisar»). | La conclusión correcta es esta: el voraz por razón toma 60 + 100 = 160 y el óptimo 0/1 es 100 + 120 = 220. |
| 8.1 Ejercicio resuelto 2 | Primero intenta tomar 4/3 del objeto 1 y luego se corrige. | Como los pesos suman 14 ≤ 15, todos los objetos caben completos: valor **37**. Programa: `MochilaEjercicioResuelto.java`. |
| General | El código viene en Python y usa `sorted`, `heapq` y clases (`UnionFind`), que las restricciones de los programas de consola no permiten. | Los ordenamientos son de selección o inserción hechos a mano. La cola de prioridad es un Min-Heap en arreglo (INSERTAR y HEAPIFY de la Unidad I), union-find usa los arreglos `padre[]` y `rango[]`, y Prim y Dijkstra usan la variante con matriz de adyacencia O(V²) que describen las secciones 5.6 y 6.5. |
