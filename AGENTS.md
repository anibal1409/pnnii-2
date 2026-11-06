# PNN II: reglas de negocio del proyecto

Proyecto de programas Java para la asignatura **Programación No Numérica II** (código 4703337, Ingeniería de Sistemas, VII semestre). Hay dos tipos de entregable para cada unidad:

1. **Programas de consola** (`consola/unidadN/`): los que se entregan o evalúan. Siguen las restricciones del material.
2. **Laboratorio gráfico** (`laboratorio/`): una aplicación Swing que ejecuta los mismos algoritmos paso a paso, mostrando el código, las variables, la salida y una visualización animada.

## Fuentes de verdad

- `material/programa/programa-analitico.md`: el programa oficial, con objetivos y contenidos de las 5 unidades.
- `material/unidades/unidad-N-*.md`: el material de cada unidad. El usuario lo va entregando unidad por unidad.
- `material/unidades/unidad-N-erratas.md`: los errores encontrados en el material y cómo se corrigieron.

| Unidad | Tema | Contenido obligatorio |
|---|---|---|
| I | Estructuras de datos elementales | Árbol, altura, nivel, grado; binarios y HEAP; Teoremas I y II; INSERTAR y HEAPIFY |
| II | Divide y conquista | Búsqueda binaria, máximos y mínimos, Sortmerge, Sortmerge 2, Quicksort |
| III | Método de adquisición | Cintas magnéticas, bolsa de papel (mochila), intercalación óptima, árbol de costo mínimo, camino más corto desde un origen |
| IV | Programación dinámica | Grafos multietapa (hacia delante y hacia atrás), ABB óptimos, vendedor viajero |
| V | Ir hacia atrás | N reinas, suma de subconjuntos |

## Reglas que siempre aplican

- **Idioma**: todo en español (código, identificadores, comentarios, interfaz y documentación). Las respuestas al usuario también van en español.
- **Fidelidad al material**: cada programa corresponde a un tema del contenido de su unidad. No se añaden temas ajenos al programa analítico sin que el usuario lo pida.
- **Corrección ante todo**: cada programa se compila y se ejecuta, y su salida se verifica a mano antes de darlo por terminado. Si el material trae un ejemplo con errores, se implementa la versión correcta y se documenta en `unidad-N-erratas.md`. Nunca se copia una salida esperada sin comprobarla.
- **Java 17** (el JDK instalado). Los programas de consola no usan dependencias externas.
- **Paridad consola–laboratorio**: cada práctica del laboratorio declara su programa de consola equivalente (`programaConsola()`), y el algoritmo que traza debe ser el mismo, con los mismos resultados.
- **Verificación**: `scripts/verificar.sh` compila y ejecuta todos los programas de consola y compila el laboratorio. Debe terminar en `OK` antes de cerrar cualquier tarea.

## Convenciones detalladas

- Programas de consola: `.cursor/rules/java-consola.mdc`
- Laboratorio gráfico (arquitectura y UI/UX): `.cursor/rules/laboratorio-gui.mdc`
- Flujos de trabajo (skills en `.cursor/skills/`):
  - `nueva-unidad`: incorporar el material de una unidad nueva.
  - `programa-consola`: crear un programa de consola.
  - `practica-laboratorio`: crear una práctica del laboratorio y verificarla visualmente.

## Comandos

```bash
scripts/verificar.sh            # compila y ejecuta todo
scripts/verificar.sh unidad1    # solo los programas de consola de una unidad
scripts/consola.sh InsertarMaxHeap
scripts/laboratorio.sh          # abre la interfaz gráfica
scripts/distribuir.sh           # paquetes para estudiantes en distribucion/ (Windows, Linux, JAR)
```
