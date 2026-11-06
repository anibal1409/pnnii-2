---
name: nueva-unidad
description: Incorpora el material de una nueva unidad de Programación No Numérica II (PNN II) creando sus programas de consola y prácticas del laboratorio gráfico. Usar cuando el usuario entregue o pegue el material de una unidad (Unidad II Divide y conquista, III Método de adquisición, IV Programación dinámica, V Ir hacia atrás), o pida "crear los programas de la unidad N".
---

# Incorporar una unidad nueva

Copia esta lista y ve marcando el progreso:

```
- [ ] 1. Guardar el material
- [ ] 2. Extraer el plan de programas
- [ ] 3. Revisar erratas del material
- [ ] 4. Programas de consola
- [ ] 5. Prácticas del laboratorio
- [ ] 6. Verificar y documentar
```

## 1. Guardar el material
Guarda el texto en `material/unidades/unidad-N-<tema>.md`, con N en número arábigo. Si viene en PDF, extrae el texto con la skill `pdf`. Contrasta su contenido con la unidad correspondiente de `material/programa/programa-analitico.md`.

## 2. Plan de programas
Haz una tabla con estas columnas: tema del contenido → programa de consola → práctica del laboratorio. Reglas:
- Debe haber un programa de consola por cada algoritmo del contenido oficial y por cada ejemplo o caso resuelto del material.
- El laboratorio agrupa los temas en prácticas interactivas. Prioriza los algoritmos cuyo comportamiento se entiende mejor viéndolo paso a paso.
- Los ejercicios *propuestos* del material no se resuelven salvo que el usuario lo pida.
- Muestra el plan al usuario antes de implementar si incluye más de 8 programas o alguna decisión ambigua.

## 3. Erratas
Ejecuta cada ejemplo del material tal como viene (copiándolo a `/tmp`). Si la salida no coincide con la indicada, o el código viola su propia precondición, registra el problema en `material/unidades/unidad-N-erratas.md` con el mismo formato que `unidad-1-erratas.md`.

## 4. Consola
Sigue la skill `programa-consola` para cada programa, en `consola/unidadN/`.

## 5. Laboratorio
Sigue la skill `practica-laboratorio`. Crea el paquete `pnni.laboratorio.unidadN` y una `Escena` adecuada al tema:
- Unidad II: arreglo con rangos (bajo, medio, alto) y un árbol de llamadas recursivas.
- Unidad III: lista de candidatos ordenada, la solución parcial y el costo acumulado (y el grafo si aplica).
- Unidad IV: el grafo por etapas o la tabla de programación dinámica que se va llenando.
- Unidad V: el tablero de N reinas o el árbol de estados con podas.

## 6. Verificar y documentar
- Ejecuta `scripts/verificar.sh`; debe terminar en `OK`.
- Haz capturas de las prácticas nuevas en ambos temas y revísalas.
- Actualiza la sección de la unidad en `README.md`, con sus programas y prácticas.
