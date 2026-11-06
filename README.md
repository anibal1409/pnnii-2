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
  unidades/unidad-N-*.md              Material de cada unidad (fuente de verdad)
  unidades/unidad-N-erratas.md        Errores del material y su corrección
consola/
  unidad1/                            Programas de consola de la Unidad I
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
    unidad1/                          Prácticas y escenas de la Unidad I
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
| II | Divide y conquista | Búsqueda binaria, máximos y mínimos, Sortmerge, Sortmerge 2, Quicksort | Pendiente |
| III | Método de adquisición | Cintas magnéticas, mochila, intercalación óptima, árbol de costo mínimo, camino más corto | Pendiente |
| IV | Programación dinámica | Grafos multietapa, ABB óptimos, vendedor viajero | Pendiente |
| V | Ir hacia atrás | N reinas, suma de subconjuntos | Pendiente |

### Unidad I: Estructuras de datos elementales

| Tema | Programa de consola | Práctica del laboratorio | Qué hace |
|---|---|---|---|
| Teoremas I y II | `TeoremasArbolBinario` | 1.1 Teoremas I y II | Máximo de nodos en el nivel *i* (2^i) y en un árbol de altura *h* (2^(h+1) − 1), comprobado sumando por niveles. |
| Representación en arreglos | `NavegacionPadreHijo` | 1.2 Navegación padre-hijo | Índices del padre y de los hijos de cada posición del arreglo. |
| Altura y nodos (ejercicio resuelto 1) | `VerificarArbol` | — | Altura, nodos por nivel y máximo de nodos de un árbol dado. |
| INSERTAR | `InsertarMaxHeap`, `InsertarMinHeap` | 1.3 Algoritmo INSERTAR | Inserta al final y sube el elemento mientras viole la propiedad de heap. O(log n). |
| HEAPIFY | `HeapifyMaxHeap` | 1.4 Algoritmo HEAPIFY | Hunde un elemento hasta restaurar la propiedad de Max-Heap. O(log n). |
| Construcción de un heap | `ConstruirMaxHeap` | 1.5 Construir un Heap | Aplica HEAPIFY desde el último nodo interno hasta la raíz. O(n). |
| Caso 6.1: hospital | `HospitalPrioridades` | 1.6 Caso: Hospital | Cola de prioridad de pacientes según gravedad. |
| Caso 6.2: cola de impresión | `ColaImpresion` | 1.7 Caso: Cola de impresión | Cola de prioridad de trabajos de impresión. |

Las erratas del material corregidas están en [`material/unidades/unidad-1-erratas.md`](material/unidades/unidad-1-erratas.md).

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
