# Programación No Numérica II: programas en Java

Programas de la asignatura **4703337 · Programación No Numérica II** (Ingeniería de Sistemas, VII semestre), organizados por unidad.

Repositorio: <https://github.com/anibal1409/pnnii-2>

Cada algoritmo existe en dos versiones que producen los mismos resultados:

| Versión | Carpeta | Para qué sirve |
|---|---|---|
| **Consola** | `consola/unidadN/` | Programas entregables. Cumplen las restricciones del material: una sola clase con `main` y métodos `static`, uso de arreglos, sin dependencias externas y con comentarios en cada línea. |
| **Laboratorio** | `laboratorio/` | Aplicación gráfica (Swing + FlatLaf) que ejecuta el mismo algoritmo paso a paso. Muestra el código con la línea actual resaltada, el valor de las variables, la salida de consola y una visualización animada del árbol y del arreglo. |

---

## 1. Requisitos

| Herramienta | Versión | Notas |
|---|---|---|
| JDK | 17 o superior | Comprobar con `java -version` y `javac -version`. |
| Maven | No hace falta instalarlo | El wrapper `laboratorio/mvnw` (o `mvnw.cmd` en Windows) descarga Maven la primera vez. |
| Git | Cualquiera | Solo para clonar el repositorio. |
| Editor (opcional) | Cursor o VS Code | Al abrir el proyecto se sugiere el *Extension Pack for Java* (`vscjava.vscode-java-pack`). |

La primera compilación del laboratorio necesita internet para descargar Maven y las librerías (FlatLaf 3.7.2 y las fuentes Inter y JetBrains Mono). Después funciona sin conexión.

## 2. Obtener el proyecto

```bash
git clone git@github.com:anibal1409/pnnii-2.git
cd pnnii-2
```

Con HTTPS: `git clone https://github.com/anibal1409/pnnii-2.git`.

En Linux o macOS, si los scripts no tienen permiso de ejecución: `chmod +x scripts/*.sh laboratorio/mvnw`.

## 3. Programas de consola

Desde Java 11 un archivo `.java` de una sola clase se ejecuta directamente, sin compilar a mano:

```bash
java consola/unidad1/InsertarMaxHeap.java
```

También se puede ejecutar por nombre de clase, sin indicar la unidad:

```bash
scripts/consola.sh InsertarMaxHeap
scripts/consola.sh                 # sin argumentos lista todos los programas disponibles
```

Compilación clásica, si se prefiere generar el `.class`:

```bash
javac -encoding UTF-8 -d /tmp/pnn consola/unidad1/InsertarMaxHeap.java
java -cp /tmp/pnn InsertarMaxHeap
```

Los archivos `.class` están en `.gitignore`: no se versionan.

## 4. Laboratorio gráfico

### Abrirlo

```bash
scripts/laboratorio.sh                                   # Linux / macOS
cd laboratorio && ./mvnw compile exec:java               # equivalente manual
laboratorio\mvnw.cmd compile exec:java                   # Windows (desde la raíz: cd laboratorio primero)
```

Desde el editor: panel *Run and Debug*, configuración **"Laboratorio (interfaz grafica)"**. La configuración **"Programa de consola abierto"** ejecuta el archivo `.java` que esté abierto.

### JAR ejecutable

```bash
cd laboratorio
./mvnw package
java -jar target/laboratorio.jar
```

El JAR (generado con `maven-shade-plugin`) incluye FlatLaf y las fuentes, así que solo necesita Java 17+.

### Cómo se usa

1. En la barra lateral se elige una práctica (agrupadas por unidad).
2. Se escriben los datos de entrada en el formulario de parámetros. Si son inválidos, aparece un mensaje en español explicando el error.
3. Se ejecuta (`F5`) y se recorre la traza con el reproductor.
4. Las pestañas muestran el pseudocódigo resaltado, las variables, la salida y el **programa de consola equivalente**.

| Atajo | Acción |
|---|---|
| `Espacio` | Reproducir / pausar |
| `←` / `→` | Paso anterior / siguiente |
| `Inicio` / `Fin` | Primer / último paso |
| `F5` | Ejecutar con los datos escritos |
| Botón ☀/☾ | Cambiar entre tema claro y oscuro |

## 5. Verificación

```bash
scripts/verificar.sh            # todas las unidades + compilación del laboratorio
scripts/verificar.sh unidad1    # solo los programas de consola de la unidad 1
```

El script:

1. Compila cada programa de `consola/` con `javac -Xlint:all -encoding UTF-8` (cualquier advertencia queda visible).
2. Lo ejecuta y muestra su salida.
3. Sin argumento, compila además el laboratorio con `./mvnw -q compile`.
4. Termina con `OK: todo compila y ejecuta` o con `FALLOS: N` (el código de salida es el número de fallos).

Ninguna tarea se da por terminada sin que este script termine en `OK`.

## 6. Distribuir el laboratorio a estudiantes

```bash
scripts/distribuir.sh
```

Requiere JDK 17+ con `jdeps`, `jlink` y `jpackage`. Deja en `distribucion/` (carpeta no versionada) tres paquetes, cada uno con un `LEEME.txt` (plantilla en `laboratorio/empaquetado/LEEME.txt`):

| Archivo | Sistema | Cómo se abre | ¿Necesita Java? |
|---|---|---|---|
| `Laboratorio-PNN-II-Windows.zip` | Windows 10/11 de 64 bits | Descomprimir y doble clic en `Abrir Laboratorio.bat` | No, trae un Java reducido |
| `Laboratorio-PNN-II-Linux.tar.gz` | Linux de 64 bits | `./Laboratorio-PNN-II/bin/Laboratorio-PNN-II` | No, trae un Java reducido |
| `Laboratorio-PNN-II.jar` | macOS u otros | `java -jar Laboratorio-PNN-II.jar` | Sí, Java 17+ |

El Java incluido se recorta con `jlink` a los módulos que `jdeps` detecta en el JAR. Hay que volver a ejecutar el script cada vez que se agreguen prácticas.

## 7. Estructura del repositorio

```
material/
  programa/programa-analitico.md      Programa oficial: objetivos y contenidos de las 5 unidades
  unidades/unidad-N-*.md              Material de cada unidad (fuente de verdad); las guías
                                      de las unidades II y III vienen en .docx y su texto
                                      está extraído en unidad-2-*.md y unidad-3-*.md
  unidades/unidad-N-erratas.md        Errores del material y su corrección
consola/
  unidad1/                            Programas de consola de la Unidad I
  unidad2/                            Programas de consola de la Unidad II
  unidad3/                            Programas de consola de la Unidad III
laboratorio/                          Aplicación gráfica (Maven, Java 17)
  pom.xml                             FlatLaf, exec-maven-plugin y maven-shade-plugin
  mvnw, mvnw.cmd, .mvn/               Maven wrapper
  empaquetado/LEEME.txt               Instrucciones incluidas en los paquetes
  src/main/resources/icono.png        Icono de la aplicación
  src/main/java/pnni/laboratorio/
    App.java                          Punto de entrada (clase principal)
    nucleo/                           Modelo independiente de la interfaz
      Practica.java                   Interfaz que implementa cada práctica
      Traza.java, Paso.java           Resultado de ejecutar: lista de pasos
      Escena.java                     Lo que se dibuja en cada paso
      Codigo.java                     Pseudocódigo con líneas numeradas
      Parametro.java, Entrada.java    Datos de entrada del formulario
      Unidad.java                     Las 5 unidades de la asignatura
      Catalogo.java                   Registro de todas las prácticas
    ui/                               Ventana, barra lateral, visor de código,
                                      reproductor, lienzo, tema y paleta
      Tono.java                       Rol visual (actual, comparando, intercambio...)
      Dibujo.java                     Utilidades de pintura: celdas, pastillas, leyenda
      Arbol.java                      Dibuja árboles y bosques (llamadas, fusiones)
    unidad1/                          Prácticas y escenas de la Unidad I (árbol + arreglo)
    unidad2/                          Unidad II: EscenaArreglo (rangos, punteros y árbol
                                      de llamadas recursivas), ArbolLlamadas, Intercalar
    unidad3/                          Unidad III: EscenaCinta, EscenaMochila, EscenaFusion,
                                      EscenaGrafo y Grafo (lectura de aristas u-v:peso)
scripts/
  verificar.sh                        Compila y ejecuta todo
  consola.sh                          Ejecuta un programa de consola por nombre
  laboratorio.sh                      Abre el laboratorio
  distribuir.sh                       Genera los paquetes para estudiantes
.vscode/                              Configuraciones de ejecución y extensiones sugeridas
AGENTS.md, CLAUDE.md                  Reglas de negocio para asistentes de IA
.cursor/rules/                        Convenciones de código (consola y laboratorio)
.cursor/skills/                       Flujos de trabajo: nueva unidad, programa de consola,
                                      práctica de laboratorio
```

## 8. Contenido por unidad

| Unidad | Tema | Contenido | Estado |
|---|---|---|---|
| I | Estructuras de datos elementales | Árbol, altura, nivel, grado; binarios y HEAP; Teoremas I y II; INSERTAR y HEAPIFY | Completa |
| II | Divide y conquista | Búsqueda binaria, máximos y mínimos, Sortmerge, Sortmerge 2, Quicksort | Completa |
| III | Método de adquisición | Cintas magnéticas, mochila, intercalación óptima, árbol de costo mínimo, camino más corto | Completa |
| IV | Programación dinámica | Grafos multietapa, ABB óptimos, vendedor viajero | Pendiente |
| V | Ir hacia atrás | N reinas, suma de subconjuntos | Pendiente |

### Unidad I: Estructuras de datos elementales

| Tema | Programa de consola | Práctica del laboratorio | Qué hace |
|---|---|---|---|
| Teoremas I y II | `TeoremasArbolBinario` | 1.1 Teoremas I y II | Máximo de nodos en el nivel *i* (2^i) y en un árbol de altura *h* (2^(h+1) − 1), comprobado sumando por niveles. |
| Representación en arreglos | `NavegacionPadreHijo` | 1.2 Navegación padre-hijo | Índices del padre y de los hijos de cada posición del arreglo. |
| Altura y nodos (ejercicio resuelto 1) | `VerificarArbol` | — | Para A(B(D,E), C(-,F)) en un arreglo: altura 2, 3 nodos en el nivel 2 y 7 nodos como máximo si fuera lleno. |
| INSERTAR | `InsertarMaxHeap`, `InsertarMinHeap` | 1.3 Algoritmo INSERTAR | Inserta al final y sube el elemento mientras viole la propiedad de heap. O(log n). |
| HEAPIFY | `HeapifyMaxHeap` | 1.4 Algoritmo HEAPIFY | Hunde un elemento hasta restaurar la propiedad de Max-Heap. O(log n). |
| Construcción de un heap | `ConstruirMaxHeap` | 1.5 Construir un Heap | Aplica HEAPIFY desde el último nodo interno hasta la raíz. O(n). |
| Caso 6.1: hospital | `HospitalPrioridades` | 1.6 Caso: Hospital | Min-Heap de códigos de gravedad (1 = crítico … 5 = no urgente): se atiende primero el código más bajo. |
| Caso 6.2: cola de impresión | `ColaImpresion` | 1.7 Caso: Cola de impresión | Max-Heap de prioridades 1 (baja), 2 (normal) y 3 (urgente): se imprime primero la mayor. |

Las erratas del material corregidas están en [`material/unidades/unidad-1-erratas.md`](material/unidades/unidad-1-erratas.md).

### Unidad II: Divide y conquista

Las prácticas recursivas dibujan el **árbol de llamadas**: la llamada activa, las que esperan en la pila y las que ya devolvieron su resultado (escrito debajo de cada nodo). El arreglo atenúa lo que queda fuera del tramo `[inicio, fin]` y señala con flechas las variables `inicio`, `medio`, `fin`, `i`, `j`, `k`, `bajo` y `alto`.

| Tema | Programa de consola | Práctica del laboratorio | Qué hace |
|---|---|---|---|
| Búsqueda binaria recursiva (3.3) | `BusquedaBinaria` | 2.1 Búsqueda binaria recursiva | Busca 25 en `3 7 12 19 25 33 42 56 71` y lo halla en el índice 4. O(log n). |
| Búsqueda binaria iterativa (3.4) | `BusquedaBinariaIterativa` | 2.2 Búsqueda binaria iterativa | El mismo algoritmo con un ciclo que ajusta `inicio` y `fin`; 19 está en el índice 3. |
| Ejercicio resuelto 1: orden descendente | `BusquedaBinariaDesc` | — | En `20 15 10 5 0` el mayor está a la izquierda: 10 está en el índice 2. |
| Máximos y mínimos (4) | `MaxMinDYC` | 2.3 Máximos y mínimos | Máximo 15 y mínimo 2 de `8 3 15 6 2 11 7 4` con 10 comparaciones = 3n/2 − 2 (el método ingenuo usa 2n − 2 = 14). |
| Sortmerge (5) | `MergeSort` | 2.4 Sortmerge (recursivo) | Divide hasta tramos de un elemento e intercala con los temporales `izq` y `der`. O(n log n), estable. |
| Sortmerge 2 (6) | `MergeSortIterativo` | 2.5 Sortmerge 2 (iterativo) | Bottom-up: pasadas con bloques de 1, 2, 4… y el resultado de cada pasada. Maneja n que no es potencia de 2. |
| Quicksort (7) | `Quicksort` | 2.6 Quicksort (Lomuto) | Pivote = último elemento; zona ≤ pivote, `i`, `j` e intercambios animados; cuenta comparaciones (15 en el ejemplo). |
| Ejercicio resuelto 2: peor caso | `PeorCasoQuicksort` | 2.6 con `1, 2, 3, 4, 5, 6, 7, 8` | Con el arreglo ya ordenado cada partición deja un lado vacío: 28 = n(n−1)/2 comparaciones. |
| Caso 1: guía telefónica | `GuiaTelefonica` | — | Búsqueda binaria recursiva sobre cadenas con `compareTo`. |
| Caso 2: pedidos de comercio electrónico | `SistemaPedidos` | — | Integra máx/mín por D&C, Quicksort y búsqueda binaria sobre los montos. |

Erratas: [`material/unidades/unidad-2-erratas.md`](material/unidades/unidad-2-erratas.md).

### Unidad III: Método de adquisición (estrategia voraz)

El material trae el código en **Python**; los programas de consola lo traducen a Java con las restricciones de la asignatura (sin colecciones): ordenamiento por selección o inserción, Min-Heap en arreglo para la cola de prioridad, union-find con arreglos `padre[]` y `rango[]`, y Prim y Dijkstra con matriz de adyacencia O(V²), la variante que el material describe en 5.6 y 6.5.

En el laboratorio, los grafos se escriben como `u-v:peso` separados por comas (en Dijkstra `u-v` significa u → v). La tabla lateral muestra `costo/padre` (Prim), las aristas ordenadas y `padre[]` (Kruskal) o `dist/prev` (Dijkstra).

| Tema | Programa de consola | Práctica del laboratorio | Qué hace |
|---|---|---|---|
| Almacenamiento óptimo en cintas (2) | `AlmacenamientoCintas` | 3.1 Almacenamiento en cintas | Graba primero el archivo más corto: `15 3 8 20 5` pasa de T = 156 a T = 109. La cinta se dibuja con segmentos proporcionales y el tiempo de llegada de cada archivo. |
| Ejercicio resuelto 1: cintas con probabilidades | `CintasProbabilidades` | — | Ordena por p/L descendente (regla de Smith): orden B C D A con tiempo esperado 8.00, frente a 15.50 del orden A B C D. |
| Bolsa de papel / mochila fraccionaria (3) | `MochilaFraccionaria` | 3.2 Bolsa de papel | Toma objetos por razón v/w descendente; con C = 50 obtiene 240 (el tercer objeto entra en 2/3). La bolsa se llena por colores. |
| Ejercicio resuelto 2: mochila con 4 objetos | `MochilaEjercicioResuelto` | 3.2 con sus datos | Con C = 15 todos caben completos: valor 37. |
| Modelos óptimos de intercalación (4) | `IntercalacionOptima` | 3.3 Intercalación óptima | Fusiona siempre los dos menores (Huffman) con un Min-Heap: `4 3 2 6` → costo 29. Dibuja el árbol de fusiones y el heap. |
| Árbol de costo mínimo: Prim (5.3) | `PrimMST` | 3.4 Árbol de costo mínimo: Prim | Hace crecer el árbol con la arista más barata hacia afuera; peso 4 en el ejemplo. Detecta grafos no conexos. |
| Árbol de costo mínimo: Kruskal (5.4) | `KruskalMST` | 3.5 Árbol de costo mínimo: Kruskal | Aristas de menor a mayor; acepta si no forma ciclo (union-find con compresión de camino y unión por rango). |
| Camino más corto desde un origen (6) | `Dijkstra` | 3.6 Camino más corto: Dijkstra | Distancias `0 3 1 4 7` desde 0 y camino `0 → 2 → 1 → 3 → 4`, reconstruido con `prev`. |
| Caso 7.1: centro de datos | `CasoCentroDatos` | 3.1 y 3.3 con sus datos | Backups en cinta (T = 3190 frente a 5775 en orden descendente) y fusión de logs (costo 141). |
| Caso 7.2: red de transporte | `CasoTransporte` | 3.4 y 3.6 con sus datos (en 3.6, cada calle en ambos sentidos) | Ciclovías de 21 km con Prim y distancias `0 4 3 9 8 11 12` desde el barrio 0 con Dijkstra. |

Erratas: [`material/unidades/unidad-3-erratas.md`](material/unidades/unidad-3-erratas.md). Entre ellas, los resultados numéricos de los dos casos prácticos, que no coinciden con los datos del propio material.

## 9. Agregar contenido nuevo

### Un programa de consola

1. Crear `consola/unidadN/NombreEnPascalCase.java` con una sola clase pública del mismo nombre, `main` y métodos `static`.
2. Todo en español: identificadores, comentarios y salida. Comentar cada línea.
3. Verificar la salida a mano contra el material. Si el material trae un error, implementar la versión correcta y documentarlo en `material/unidades/unidad-N-erratas.md`.
4. Ejecutar `scripts/verificar.sh unidadN`.

Convenciones completas: `.cursor/rules/java-consola.mdc`.

### Una práctica del laboratorio

1. Crear una clase en `laboratorio/src/main/java/pnni/laboratorio/unidadN/` que implemente `nucleo.Practica`:
   - `unidad()`, `titulo()`, `descripcion()`, `complejidad()` y `parametros()` describen la práctica.
   - `programaConsola()` devuelve la ruta del programa de consola equivalente.
   - `ejecutar(Entrada)` corre el algoritmo instrumentado y devuelve una `Traza`; ante datos inválidos lanza `IllegalArgumentException` con un mensaje en español.
2. Registrarla en `nucleo/Catalogo.java`.
3. Comprobar que produce los mismos resultados que su programa de consola.
4. Ejecutar `scripts/verificar.sh` y revisar la práctica visualmente.

Convenciones completas: `.cursor/rules/laboratorio-gui.mdc`.

## 10. Reglas del proyecto

- **Idioma:** todo en español.
- **Fidelidad al material:** cada programa corresponde a un tema del programa analítico.
- **Corrección:** cada salida se comprueba a mano; nunca se copia una salida esperada sin verificarla.
- **Java 17**, sin dependencias externas en los programas de consola.
- **Paridad consola–laboratorio:** mismo algoritmo, mismos resultados.

Detalle en [`AGENTS.md`](AGENTS.md).
