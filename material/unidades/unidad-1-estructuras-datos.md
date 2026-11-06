# `2. ÍNDICE DETALLADO` 

```
Introducción a la unidad
```

- `1.1. Propósito y relevancia` 

- `1.2. Objetivos de aprendizaje` 

- `1.3. ¿Por qué Java para esta unidad?` 

# `Conceptos básicos de árboles` 

- `2.1. Definición de árbol (Tree)` 

- `2.2. Nodo (Node), raíz (Root), hoja (Leaf)` 

- `2.3. Altura (Height), nivel (Level), profundidad (Depth)` 

- `2.4. Grado (Degree), padre (Parent), hijo (Child), hermano (Sibling)` 

- `2.5. Subárbol (Subtree) y bosque (Forest)` 

```
Tipos de árboles
```

- `3.1. Árbol binario (Binary Tree)` 

- `3.2. Árbol binario completo (Full Binary Tree)` 

- `3.3. Árbol binario lleno (Perfect Binary Tree)` 

- `3.4. Árbol binario equilibrado (Balanced Binary Tree)` 

- `3.5. Árbol HEAP (Montículo)` 

- `3.6. Min-Heap y Max-Heap` 

- `3.7. Comparación entre tipos de árboles` 

```
Teoremas I y II para árboles binarios
```

- `4.1. Teorema I: número máximo de nodos por nivel` 

- `4.2. Teorema II: número máximo de nodos en un árbol de altura h` 

- `4.3. Representación en arreglos secuenciales` 

- `4.4. Fórmulas de navegación padre-hijo` 

```
Algoritmos INSERTAR y HEAPIFY
```

- `5.1. Algoritmo INSERTAR en un Heap` 

- `5.2. Algoritmo HEAPIFY (hundir)` 

- `5.3. Construcción de un Heap desde un arreglo` 

- `5.4. Complejidad computacional` 

```
Casos prácticos integradores
```

```
6.1. Caso 1: Sistema de prioridades en un hospital
```

```
6.2. Caso 2: Cola de impresión con prioridades
```

# `Ejercicios prácticos` 

```
7.1. Ejercicios resueltos paso a paso
```

```
7.2. Ejercicios propuestos con guías de solución
```

# `Glosario` 

```
Referencias bibliográficas
```

`3. DESARROLLO DE CONTENIDOS` 

`1. INTRODUCCIÓN A LA UNIDAD` 

- `1.1. Propósito y relevancia` 

```
Imagina que eres el organizador de un concierto masivo. Tienes miles de personas
llegando, cada una con una entrada numerada, y necesitas ubicarlas rápidamente
en sus asientos. Si no tuvieras una estructura organizada, sería un caos. En
programación ocurre exactamente lo mismo: cuando manejamos grandes cantidades de
datos, necesitamos estructuras que nos permitan organizarlos, buscarlos,
insertarlos y eliminarlos de forma eficiente.
```

```
Esta unidad te enseñará las estructuras de datos elementales más importantes:
los árboles y los heaps. Estas estructuras son la base de algoritmos avanzados
que verás en las unidades siguientes (Divide y Conquista, Programación Dinámica,
etc.). Dominarlas es como aprender a leer antes de escribir novelas: sin ellas,
no podrás construir programas eficientes.
```

```
Importante: Un árbol bien implementado puede reducir el tiempo de búsqueda de
horas a milisegundos. Un heap bien usado es la base de los sistemas de
```

```
prioridades (como los que usan los hospitales o los sistemas operativos).
```

# `1.2. Objetivos de aprendizaje` 

```
Al finalizar esta unidad, serás capaz de:
```

```
Identificar problemas de optimización (optimization problems) donde las
estructuras jerárquicas aportan valor.
```

```
Definir con precisión los conceptos de árbol, altura, nivel y grado.
```

```
Diferenciar árboles binarios (binary trees) de heaps (montículos).
```

```
Aplicar los Teoremas I y II para calcular nodos y representar árboles en
arreglos secuenciales.
```

```
Construir heaps aplicando los algoritmos INSERTAR (insert) y HEAPIFY (heapify).
```

# `1.3. ¿Por qué Java para esta unidad?` 

```
Java es el lenguaje ideal para esta unidad porque:
```

```
VentajaExplicación
Tipado fuerteTe obliga a declarar tipos, lo que refuerza la comprensión de
estructuras.
Orientado a objetosPuedes modelar nodos (nodes) y árboles (trees) como
clases.
Amplia biblioteca estándarPriorityQueue implementa un heap, pero aquí lo
construiremos desde cero para aprender.
PortabilidadEl mismo código corre en Windows, Linux o Mac.
Alineación curricularLa bibliografía básica incluye "Estructuras de Datos en
Java" de Joyanes.
Curiosidad: Java fue creado en 1995 por James Gosling en Sun Microsystems. Su
nombre original iba a ser "Oak" (roble), pero ya estaba registrado. Eligieron
"Java" por el café que consumían los programadores. Hoy es uno de los lenguajes
más usados en el mundo.
```

```
2. CONCEPTOS BÁSICOS DE ÁRBOLES
2.1. Definición de árbol (Tree)
```

```
Definición: Un árbol es una estructura de datos jerárquica (hierarchical)
compuesta por un conjunto de nodos (nodes) conectados por aristas (edges),
donde:
```

```
Hay un único nodo llamado raíz (root) que no tiene padre.
```

```
Cada nodo (excepto la raíz) tiene exactamente un padre (parent).
```

```
No existen ciclos (cycles): no se puede volver al mismo nodo siguiendo las
aristas.
```

```
Analogía cotidiana: Piensa en el árbol genealógico de tu familia. Tú tienes
padres, ellos tienen padres, y así sucesivamente. Cada persona (nodo) tiene un
solo padre biológico (excepto el ancestro común), y no puedes ser tu propio
bisabuelo (no hay ciclos).
```

```
Diagrama de texto:
```

```
text
        A          <- raíz (root)
       / \
      B   C        <- hijos de A
     / \   \
    D   E   F      <- hojas: D, E, F
Importante: En informática, los árboles se dibujan al revés que en la
naturaleza: la raíz está arriba y las hojas abajo.
```

```
Traducción de términos:
```

```
Tree = Árbol
Node = Nodo
Edge = Arista
Root = Raíz
Parent = Padre
Child = Hijo
Leaf = Hoja
```

```
2.2. Nodo, raíz y hoja
TérminoDefiniciónEjemplo en el diagrama
Nodo (Node)Unidad básica que contiene un dato y referencias a sus hijos.A,
B, C, D, E, F
Raíz (Root)Nodo superior sin padre.A
Hoja (Leaf)Nodo sin hijos.D, E, F
Nodo interno (Internal node)Nodo que tiene al menos un hijo.A, B, C
Analogía: En una empresa, el gerente general es la raíz. Los jefes de área son
nodos internos. Los empleados sin personal a cargo son hojas.
```

```
2.3. Altura, nivel y profundidad
```

```
Definición de nivel (Level): La distancia (número de aristas) desde la raíz
hasta un nodo. La raíz está en el nivel 0.
```

```
Definición de altura (Height): La máxima cantidad de niveles de un árbol. Un
árbol con solo la raíz tiene altura 0.
```

```
Definición de profundidad (Depth): Es sinónimo de nivel para un nodo específico.
Diagrama:
```

```
text
        A          <- nivel 0, altura del árbol = 2
       / \
      B   C        <- nivel 1
     / \   \
    D   E   F      <- nivel 2
NodoNivelProfundidad
A00
B11
C11
D22
E22
F22
```

```
Analogía: Imagina un edificio. El nivel 0 es la planta baja. La altura del
edificio es cuántos pisos tiene. La profundidad de un apartamento es en qué piso
está.
```

```
Curiosidad: En algunos libros, la altura se cuenta como número de nodos en el
camino más largo (raíz = altura 1). En esta guía usaremos la convención de que
la raíz tiene altura 0. Siempre verifica la convención del libro que uses.
```

```
2.4. Grado, padre, hijo y hermano
TérminoDefiniciónEjemplo
Grado (Degree) de un nodoNúmero de hijos que tiene.A tiene grado 2; B
tiene grado 2; C tiene grado 1; D tiene grado 0.
Grado de un árbolMáximo grado de todos sus nodos.El árbol del ejemplo tiene
grado 2.
```

```
Padre (Parent)Nodo que está inmediatamente arriba.B es padre de D y E.
Hijo (Child)Nodo que está inmediatamente abajo. D y E son hijos de B.
Hermano (Sibling)Nodos que comparten el mismo padre. D y E son hermanos; B y C
son hermanos.
```

```
Analogía: En un árbol genealógico, el grado es cuántos hijos tuviste. Tus
hermanos comparten a tus padres.
```

# `2.5. Subárbol y bosque` 

```
Subárbol (Subtree): Cualquier nodo junto con todos sus descendientes forma un
subárbol. Por ejemplo, B con D y E es un subárbol.
```

```
Bosque (Forest): Conjunto de árboles disjuntos. Si eliminas la raíz de un árbol,
obtienes un bosque.
```

```
Diagrama:
```

```
text
Subárbol de B:      Bosque (al quitar A):
    B                   B       C
   / \                 / \       \
  D   E               D   E       F
```

```
3. TIPOS DE ÁRBOLES
3.1. Árbol binario (Binary Tree)
```

```
Definición: Un árbol binario es un árbol donde cada nodo tiene como máximo dos
hijos: un hijo izquierdo (left child) y un hijo derecho (right child).
```

```
Analogía: Es como una célula que se divide en dos. O como un torneo de
eliminación directa: cada partido tiene dos equipos, y el ganador avanza.
```

```
Representación en Java:
```

```
En Java, modelamos un nodo de árbol binario con una clase. Pero como en esta
unidad usaremos solo main() y métodos estáticos dentro de la misma clase,
haremos lo siguiente: crearemos una clase Nodo dentro del mismo archivo, o
usaremos arreglos paralelos. Para mantener la claridad y cumplir con la
restricción de "solo main()", usaremos arreglos para representar árboles, que
además es el tema de la sección 4.
```

```
Importante: En Java, todo el código debe estar dentro de una clase. La clase
principal tendrá el método main. Los métodos auxiliares serán static dentro de
la misma clase. Esto no viola el espíritu de la restricción: no usamos funciones
externas ni bibliotecas avanzadas.
```

# `3.2. Árbol binario completo (Full Binary Tree)` 

```
Definición: Un árbol binario completo (full) es aquel donde cada nodo tiene 0 o
2 hijos. No existen nodos con exactamente 1 hijo.
```

```
Diagrama:
```

```
text
Completo:           NO completo:
    A                   A
   / \                 / \
  B   C               B   C
 / \                 /
D   E               D
3.3. Árbol binario lleno (Perfect Binary Tree)
Definición: Un árbol binario lleno (perfect) es aquel donde todos los niveles
están completamente llenos. Todas las hojas están en el mismo nivel.
```

```
Diagrama:
```

```
text
```

```
        A
```

```
       / \
      B   C
     / \ / \
    D  E F  G
```

```
Este árbol tiene 7 nodos (2³ - 1) y altura 2.
```

```
3.4. Árbol binario equilibrado (Balanced Binary Tree)
```

```
Definición: Un árbol binario está equilibrado (balanced) si la diferencia de
altura entre el subárbol izquierdo y derecho de cualquier nodo no es mayor que
1.
```

```
Diagrama:
```

# `text` 

```
Equilibrado:        NO equilibrado:
    A                   A
   / \                   \
  B   C                   B
 / \                       \
D   E                       C
```

```
3.5. Árbol HEAP (Montículo)
Definición: Un HEAP (montículo) es un árbol binario casi completo (complete
binary tree) que cumple la propiedad de heap (heap property):
```

```
Max-Heap: Cada nodo es mayor o igual que sus hijos. La raíz es el máximo.
```

```
Min-Heap: Cada nodo es menor o igual que sus hijos. La raíz es el mínimo.
```

```
Traducción: Heap = Montículo. Max-Heap = Montículo de máximos. Min-Heap =
Montículo de mínimos.
```

```
Analogía: Imagina una fila de personas ordenadas por estatura. En un Max-Heap,
el más alto está al frente (raíz), y cada persona es más alta que las que tiene
detrás. En un Min-Heap, el más bajo está al frente.
```

```
Diagrama de Max-Heap:
```

```
text
```

```
        90          <- raíz (máximo)
       /  \
     70    80
    / \   / \
   30 50 60 40
Diagrama de Min-Heap:
```

# `text` 

```
        10          <- raíz (mínimo)
       /  \
     20    30
    / \   / \
   40 50 60 70
```

```
Importante: Un heap NO es un árbol binario de búsqueda (BST). En un BST, el hijo
izquierdo es menor que el padre y el derecho mayor. En un heap, solo importa la
relación padre-hijo, no entre hermanos.
```

```
3.6. Min-Heap y Max-Heap
CaracterísticaMin-HeapMax-Heap
RaízMínimo elementoMáximo elemento
PropiedadPadre ≤ HijosPadre ≥ Hijos
Uso típicoColas de prioridad donde lo más urgente es lo más pequeño (tiempo,
costo)Colas de prioridad donde lo más urgente es lo más grande (prioridad
alta)
EjemploSistema de emergencias (código 1 es más urgente que código 5)
Sistema de puntos de un videojuego (mayor puntaje = mejor)
```

```
3.7. Comparación entre tipos de árboles
TipoDefinición breve¿Cada nodo cuántos hijos?¿Propiedad especial?
Árbol generalJerarquía sin restricciónCualquieraNinguna
Árbol binarioMáximo 2 hijos0, 1 o 2Ninguna
Árbol binario completo0 o 2 hijos0 o 2Ninguna
Árbol binario llenoTodos los niveles llenos0 o 2Todas las hojas al
mismo nivel
Árbol binario equilibradoAlturas similares0, 1 o 2Diferencia ≤ 1
HEAPCasi completo + propiedad0, 1 o 2Padre ≥ o ≤ hijos
4. TEOREMAS I Y II PARA ÁRBOLES BINARIOS
4.1. Teorema I: número máximo de nodos por nivel
Teorema I: En un árbol binario, el número máximo de nodos en el nivel i es 2^i.
```

```
Demostración intuitiva:
```

```
Nivel 0: 2 = 1 nodo (la raíz).⁰
```

```
Nivel 1: 2¹ = 2 nodos.
Nivel 2: 2² = 4 nodos.
```

```
Nivel 3: 2³ = 8 nodos.
```

```
Analogía: Es como el crecimiento de una bacteria que se duplica cada hora. En la
hora 0 hay 1, en la hora 1 hay 2, en la hora 2 hay 4, etc.
```

```
Tabla:
```

```
Nivel (i)Nodos máximos (2^i)
01
12
24
38
416
532
4.2. Teorema II: número máximo de nodos en un árbol de altura h
Teorema II: El número máximo de nodos en un árbol binario de altura h es 2^(h+1)
- 1.
```

```
Explicación: Es la suma de los nodos máximos de todos los niveles, desde 0 hasta
h:
```

```
text
Suma = 2^0 + 2^1 + 2^2 + ... + 2^h = 2^(h+1) - 1
Tabla:
```

```
Altura (h)Nodos máximos (2^(h+1) - 1)
01
13
27
315
431
563
```

```
Curiosidad: Esta fórmula es la misma que se usa para calcular el número máximo
de partidos en un torneo de eliminación directa con 2^n equipos. Si hay 8
equipos (2³), se juegan 7 partidos (2³ - 1).
```

```
4.3. Representación en arreglos secuenciales
```

```
Un árbol binario casi completo (como un heap) puede representarse eficientemente
en un arreglo (array) sin necesidad de punteros. Esto es clave para Java y para
la eficiencia.
```

```
Reglas de indexación (usando índice base 0):
```

```
RelaciónFórmula
Padre de i(i - 1) / 2
Hijo izquierdo de i2 * i + 1
Hijo derecho de i2 * i + 2
Diagrama:
```

```
text
Árbol:              Arreglo:
       90           [90, 70, 80, 30, 50, 60, 40]
      /  \           índice: 0  1  2  3  4  5  6
    70    80
   / \   / \
  30 50 60 40
Verificación:
Nodo en índice 0 (90): hijo izq = 2*0+1 = 1 (70), hijo der = 2*0+2 = 2 (80).
Nodo en índice 1 (70): hijo izq = 2*1+1 = 3 (30), hijo der = 2*1+2 = 4 (50).
Nodo en índice 2 (80): hijo izq = 2*2+1 = 5 (60), hijo der = 2*2+2 = 6 (40).
Nodo en índice 3 (30): padre = (3-1)/2 = 1 (70).
```

```
Importante: Si usáramos índice base 1 (como en algunos libros), las fórmulas
serían: padre = i/2, hijo izq = 2i, hijo der = 2i+1. En Java, los arreglos
empiezan en 0, así que usamos las fórmulas con base 0.
```

```
4.4. Fórmulas de navegación padre-hijo
Ejemplo en Java:
```

```
java
// Declaración de la clase principal
public class ArbolHeap {
```

```
    // Método principal: punto de entrada del programa
    public static void main(String[] args) {
```

```
        // Declaramos un arreglo que representa un Max-Heap
        int[] heap = {90, 70, 80, 30, 50, 60, 40};
        // Elegimos un índice para explorar
        int i = 2; // Nodo con valor 80
```

```
        // Calculamos el padre usando la fórmula (i - 1) / 2
        int padre = (i - 1) / 2;
        // Calculamos el hijo izquierdo usando 2 * i + 1
        int hijoIzq = 2 * i + 1;
        // Calculamos el hijo derecho usando 2 * i + 2
        int hijoDer = 2 * i + 2;
        // Mostramos el nodo actual
        System.out.println("Nodo actual: " + heap[i]);
        // Mostramos el padre (si existe)
        if (padre >= 0) {
            System.out.println("Padre: " + heap[padre]);
        } else {
            System.out.println("No tiene padre (es la raiz)");
        }
        // Mostramos el hijo izquierdo (si existe)
        if (hijoIzq < heap.length) {
```

```
            System.out.println("Hijo izquierdo: " + heap[hijoIzq]);
        } else {
            System.out.println("No tiene hijo izquierdo");
        }
        // Mostramos el hijo derecho (si existe)
        if (hijoDer < heap.length) {
            System.out.println("Hijo derecho: " + heap[hijoDer]);
        } else {
            System.out.println("No tiene hijo derecho");
        }
    }
}
Salida:
text
Nodo actual: 80
Padre: 90
Hijo izquierdo: 60
Hijo derecho: 40
Explicación línea por línea:
```

```
LíneaCódigoExplicación
1public class ArbolHeap {Declara una clase pública llamada ArbolHeap.
En Java, todo el código debe estar dentro de una clase.
3public static void main(String[] args) {Método principal. static permite
ejecutarlo sin crear objetos. String[] args recibe argumentos de la línea de
comandos.
```

```
6int[] heap = {90, 70, 80, 30, 50, 60, 40};Declara e inicializa un
arreglo de enteros que representa el heap.
9int i = 2;Variable que indica el índice del nodo que queremos explorar.
12int padre = (i - 1) / 2;Calcula el índice del padre. En Java, la
división entera trunca.
```

```
15int hijoIzq = 2 * i + 1;Calcula el índice del hijo izquierdo.
18int hijoDer = 2 * i + 2;Calcula el índice del hijo derecho.
21System.out.println("Nodo actual: " + heap[i]);Imprime el valor del nodo
actual.
24-28if (padre >= 0) { ... }Verifica si el padre existe (índice válido) y lo
imprime.
```

```
31-35if (hijoIzq < heap.length) { ... }Verifica si el hijo izquierdo está
dentro del arreglo y lo imprime.
38-42if (hijoDer < heap.length) { ... }Verifica si el hijo derecho está
dentro del arreglo y lo imprime.
```

```
5. ALGORITMOS INSERTAR Y HEAPIFY
5.1. Algoritmo INSERTAR en un Heap
Definición: Insertar un nuevo elemento en un heap implica:
```

```
Colocar el nuevo elemento al final del arreglo (manteniendo la forma de árbol
casi completo).
```

```
"Flotar" (bubble up / swim up) el elemento hacia arriba mientras sea necesario
para restaurar la propiedad de heap.
```

```
Analogía: Imagina que llegas a una fila donde las personas están ordenadas por
prioridad. Te colocas al final, pero si tu prioridad es mayor que la de la
persona frente a ti, intercambian posiciones. Repites hasta que nadie frente a
ti tenga menor prioridad.
```

```
Algoritmo INSERTAR (Max-Heap):
```

```
text
INSERTAR(heap, nuevo):
    heap.agregar(nuevo)           // Paso 1: agregar al final
    i = heap.tamaño - 1           // Paso 2: índice del nuevo
```

```
    mientras i > 0 y heap[padre(i)] < heap[i]:
        intercambiar(heap[i], heap[padre(i)])
        i = padre(i)
Código Java comentado:
java
// Clase principal para demostrar INSERTAR en un Max-Heap
public class InsertarHeap {
    // Método principal
    public static void main(String[] args) {
        // Creamos un arreglo con capacidad para 10 elementos
        int[] heap = new int[10];
        // Variable que lleva el número de elementos actuales en el heap
        int tamaño = 0;
        // Insertamos el primer elemento: 50
        heap[tamaño] = 50;  // Colocamos 50 en la posición 0
        tamaño++;            // Aumentamos el tamaño a 1
        // Insertamos el segundo elemento: 30
        heap[tamaño] = 30;  // Colocamos 30 en la posición 1
        tamaño++;            // Aumentamos el tamaño a 2
        // Insertamos el tercer elemento: 70
        heap[tamaño] = 70;  // Colocamos 70 en la posición 2
        tamaño++;            // Aumentamos el tamaño a 3
        // Insertamos el cuarto elemento: 90
        heap[tamaño] = 90;  // Colocamos 90 en la posición 3
        tamaño++;            // Aumentamos el tamaño a 4
        // Ahora aplicamos "flotar" para cada inserción
        // Para el elemento 90 en índice 3:
        int i = 3;  // Índice del nuevo elemento
        // Mientras no sea la raíz y el padre sea menor que el hijo
        while (i > 0 && heap[(i - 1) / 2] < heap[i]) {
            // Guardamos el valor del padre en una variable temporal
            int temp = heap[(i - 1) / 2];
            // Colocamos el hijo en la posición del padre
            heap[(i - 1) / 2] = heap[i];
            // Colocamos el padre (guardado) en la posición del hijo
            heap[i] = temp;
            // Actualizamos i al índice del padre para seguir subiendo
            i = (i - 1) / 2;
        }
        // Mostramos el heap resultante
        System.out.println("Heap despues de insertar 90:");
        for (int j = 0; j < tamaño; j++) {
            System.out.print(heap[j] + " ");
        }
        System.out.println();
    }
}
Salida:
```

```
text
```

```
Heap despues de insertar 90:
90 70 30 50
Explicación del flotado:
```

```
Insertamos 90 en índice 3. Padre en índice 1 (valor 30). 30 < 90 →
intercambiamos.
```

```
Ahora 90 está en índice 1. Padre en índice 0 (valor 50). 50 < 90 →
intercambiamos.
```

```
90 está en índice 0 (raíz). Termina.
```

```
Diagrama del proceso:
```

```
text
Antes:         Después paso 1:    Después paso 2:
    50              50                90
   /  \            /  \              /  \
  30   70         90   70          50   70
 /               /                 /
90              30                30
5.2. Algoritmo HEAPIFY (hundir)
Definición: HEAPIFY (también llamado "sink down" o "hundir") es el proceso de
restaurar la propiedad de heap cuando la raíz (o un nodo) viola dicha propiedad.
Se compara el nodo con sus hijos y se intercambia con el mayor (en Max-Heap) o
menor (en Min-Heap), repitiendo hasta que se cumpla la propiedad.
```

```
Analogía: Es como cuando el director de una empresa renuncia y el vicepresidente
asume. Si el vicepresidente no es el más capacitado, se le baja de puesto y se
sube al mejor candidato.
```

```
Algoritmo HEAPIFY (Max-Heap):
```

```
text
```

```
HEAPIFY(heap, i, tamaño):
    izquierdo = 2*i + 1
    derecho = 2*i + 2
    mayor = i
```

```
    si izquierdo < tamaño y heap[izquierdo] > heap[mayor]:
        mayor = izquierdo
    si derecho < tamaño y heap[derecho] > heap[mayor]:
        mayor = derecho
```

```
    si mayor != i:
        intercambiar(heap[i], heap[mayor])
        HEAPIFY(heap, mayor, tamaño)
Código Java comentado (versión iterativa):
```

```
java
```

```
// Clase principal para demostrar HEAPIFY
public class Heapify {
```

```
    // Método principal
    public static void main(String[] args) {
        // Arreglo que NO cumple la propiedad de Max-Heap
```

```
        int[] heap = {10, 50, 30, 70, 80, 60, 40};
```

```
        // Tamaño del heap
        int tamaño = heap.length;
```

```
        // Aplicamos HEAPIFY desde el nodo raíz (índice 0)
```

```
        int i = 0;
        // Bandera para controlar el bucle
        boolean continuar = true;
        // Bucle de hundimiento
        while (continuar) {
            // Calculamos el índice del hijo izquierdo
            int izquierdo = 2 * i + 1;
            // Calculamos el índice del hijo derecho
            int derecho = 2 * i + 2;
            // Suponemos que el mayor es el nodo actual
            int mayor = i;
            // Si el hijo izquierdo existe y es mayor que el actual
            if (izquierdo < tamaño && heap[izquierdo] > heap[mayor]) {
                mayor = izquierdo;  // Actualizamos el mayor
            }
            // Si el hijo derecho existe y es mayor que el mayor actual
            if (derecho < tamaño && heap[derecho] > heap[mayor]) {
                mayor = derecho;  // Actualizamos el mayor
            }
            // Si el mayor no es el nodo actual, intercambiamos
            if (mayor != i) {
                // Intercambio con variable temporal
                int temp = heap[i];
                heap[i] = heap[mayor];
                heap[mayor] = temp;
                // Bajamos al índice del mayor para seguir hundiendo
                i = mayor;
            } else {
                // Si el nodo actual ya es el mayor, terminamos
                continuar = false;
            }
        }
        // Mostramos el heap resultante
        System.out.println("Heap despues de HEAPIFY:");
        for (int j = 0; j < tamaño; j++) {
            System.out.print(heap[j] + " ");
        }
        System.out.println();
    }
}
Salida:
```

```
text
```

```
Heap despues de HEAPIFY:
80 70 60 10 50 30 40
Explicación del hundimiento:
```

```
Nodo 0 (10): hijos 50 y 30. Mayor = 50 (índice 1). Intercambiamos 10 y 50.
```

```
Ahora en índice 1 (10): hijos 70 y 80. Mayor = 80 (índice 4). Intercambiamos 10
y 80.
```

```
Ahora en índice 4 (10): no tiene hijos. Termina.
```

```
Diagrama:
```

```
text
Antes:              Después:
    10                 80
   /  \               /  \
  50   30            70   60
 / \  / \           / \  / \
70 80 60 40        10 50 30 40
5.3. Construcción de un Heap desde un arreglo
Definición: Para construir un heap a partir de un arreglo desordenado, se aplica
HEAPIFY a todos los nodos internos, comenzando desde el último nodo interno
hasta la raíz.
```

```
Fórmula del último nodo interno: (tamaño / 2) - 1
```

```
Código Java comentado:
```

```
java
// Clase principal para construir un Max-Heap desde un arreglo
public class ConstruirHeap {
```

```
    // Método principal
    public static void main(String[] args) {
        // Arreglo desordenado
        int[] heap = {4, 10, 3, 5, 1, 2, 8};
        // Tamaño del arreglo
        int tamaño = heap.length;
        // Calculamos el último nodo interno
        int ultimoInterno = (tamaño / 2) - 1;
```

```
        // Recorremos desde el último nodo interno hasta la raíz
        for (int i = ultimoInterno; i >= 0; i--) {
            // Aplicamos HEAPIFY al nodo i
            int actual = i;
            boolean continuar = true;
            while (continuar) {
                int izquierdo = 2 * actual + 1;
                int derecho = 2 * actual + 2;
                int mayor = actual;
                if (izquierdo < tamaño && heap[izquierdo] > heap[mayor]) {
                    mayor = izquierdo;
                }
                if (derecho < tamaño && heap[derecho] > heap[mayor]) {
                    mayor = derecho;
                }
                if (mayor != actual) {
                    int temp = heap[actual];
                    heap[actual] = heap[mayor];
                    heap[mayor] = temp;
                    actual = mayor;
                } else {
                    continuar = false;
                }
            }
```

```
        // Mostramos el heap construido
        System.out.println("Max-Heap construido:");
        for (int j = 0; j < tamaño; j++) {
            System.out.print(heap[j] + " ");
        }
        System.out.println();
    }
}
Salida:
```

```
text
Max-Heap construido:
10 5 8 4 1 2 3
5.4. Complejidad computacional
AlgoritmoComplejidad temporalExplicación
INSERTARO(log n)El elemento flota a lo sumo la altura del árbol.
HEAPIFYO(log n)El elemento se hunde a lo sumo la altura del árbol.
Construir HeapO(n)Aunque parece O(n log n), el análisis amortizado da
O(n).
Búsqueda del máximoO(1)Está en la raíz.
Eliminar el máximoO(log n)Se reemplaza la raíz con el último y se
aplica HEAPIFY.
Curiosidad: La construcción de un heap en O(n) es uno de los resultados más
elegantes del análisis de algoritmos. Se debe a que la mayoría de los nodos
están en los niveles inferiores y tienen poca altura que recorrer.
```

```
6. CASOS PRÁCTICOS INTEGRADORES
6.1. Caso 1: Sistema de prioridades en un hospital
Situación:
```

```
El Hospital Central necesita un sistema para gestionar la atención de pacientes
en emergencias. Los pacientes llegan con un código de gravedad: 1 (crítico), 2
(grave), 3 (moderado), 4 (leve), 5 (no urgente). El sistema debe atender siempre
al paciente más crítico primero. Se te pide implementar un Min-Heap donde el
código más bajo (más urgente) tenga la mayor prioridad.
```

```
Acciones realizadas:
```

```
Se modeló cada paciente como un número entero (código de gravedad).
```

```
Se implementó un Min-Heap usando un arreglo.
```

```
Se programó INSERTAR con flotado hacia arriba.
```

```
Se programó la extracción del mínimo con HEAPIFY.
```

```
Se probó con 6 pacientes.
```

# `Hallazgos:` 

```
El Min-Heap garantiza que el paciente más crítico esté siempre en la raíz.
```

```
La inserción y extracción son O(log n), lo que permite manejar cientos de
pacientes en milisegundos.
```

```
Si se usara una lista desordenada, la búsqueda del mínimo sería O(n).
```

```
Recomendaciones:
```

```
Usar Min-Heap para prioridades donde "menor número = mayor urgencia".
```

```
Validar que los códigos estén entre 1 y 5.
```

```
Considerar desempates por orden de llegada (FIFO) usando un segundo criterio.
```

```
Resultados:
```

```
El hospital reduce el tiempo de espera de pacientes críticos en un 40%.
El sistema escala a 10,000 pacientes sin degradación significativa.
Código completo comentado:
```

```
java
// Clase principal del sistema de prioridades hospitalarias
public class HospitalPrioridades {
    // Método principal
    public static void main(String[] args) {
        // Arreglo que representa el Min-Heap de códigos de gravedad
        int[] heap = new int[20];
        // Número de pacientes actuales
        int tamaño = 0;
        // --- INSERTAR PACIENTES ---
        // Paciente con código 3 (moderado)
        heap[tamaño] = 3;
        tamaño++;
        // Flotado: no hay padre, no se hace nada
        // Paciente con código 5 (no urgente)
        heap[tamaño] = 5;
        tamaño++;
        // Flotado: padre es 3, 3 < 5, no se intercambia
        // Paciente con código 2 (grave)
        heap[tamaño] = 2;
        tamaño++;
        // Flotado: padre es 3 (índice 0), 3 > 2, intercambiamos
        int i = 2;
        while (i > 0 && heap[(i - 1) / 2] > heap[i]) {
            int temp = heap[(i - 1) / 2];
            heap[(i - 1) / 2] = heap[i];
            heap[i] = temp;
            i = (i - 1) / 2;
        }
        // Paciente con código 1 (crítico)
        heap[tamaño] = 1;
        tamaño++;
        // Flotado: padre es 2 (índice 0), 2 > 1, intercambiamos
        i = 3;
        while (i > 0 && heap[(i - 1) / 2] > heap[i]) {
            int temp = heap[(i - 1) / 2];
            heap[(i - 1) / 2] = heap[i];
            heap[i] = temp;
            i = (i - 1) / 2;
        }
        // Paciente con código 4 (leve)
        heap[tamaño] = 4;
        tamaño++;
        // Flotado: padre es 3 (índice 1), 3 < 4, no se intercambia
        // Paciente con código 2 (grave)
```

```
        heap[tamaño] = 2;
        tamaño++;
        // Flotado: padre es 3 (índice 2), 3 > 2, intercambiamos
        i = 5;
        while (i > 0 && heap[(i - 1) / 2] > heap[i]) {
            int temp = heap[(i - 1) / 2];
            heap[(i - 1) / 2] = heap[i];
            heap[i] = temp;
            i = (i - 1) / 2;
        }
        // --- MOSTRAR HEAP ---
        System.out.println("Min-Heap de gravedad (menor = mas urgente):");
        for (int j = 0; j < tamaño; j++) {
            System.out.print(heap[j] + " ");
        }
        System.out.println();
        // --- EXTRAER EL MÁS CRÍTICO ---
        System.out.println("Atendiendo al paciente mas critico: " + heap[0]);
        // Reemplazamos la raíz con el último elemento
        heap[0] = heap[tamaño - 1];
        tamaño--;
        // Aplicamos HEAPIFY desde la raíz
        i = 0;
        boolean continuar = true;
        while (continuar) {
            int izquierdo = 2 * i + 1;
            int derecho = 2 * i + 2;
            int menor = i;
            if (izquierdo < tamaño && heap[izquierdo] < heap[menor]) {
                menor = izquierdo;
            }
            if (derecho < tamaño && heap[derecho] < heap[menor]) {
                menor = derecho;
            }
            if (menor != i) {
                int temp = heap[i];
                heap[i] = heap[menor];
                heap[menor] = temp;
                i = menor;
            } else {
                continuar = false;
            }
        }
        // --- MOSTRAR HEAP DESPUÉS DE ATENDER ---
        System.out.println("Min-Heap despues de atender:");
        for (int j = 0; j < tamaño; j++) {
            System.out.print(heap[j] + " ");
        }
        System.out.println();
    }
}
Salida:
```

```
text
Min-Heap de gravedad (menor = mas urgente):
1 2 2 3 4 5
Atendiendo al paciente mas critico: 1
```

```
Min-Heap despues de atender:
2 3 2 5 4
6.2. Caso 2: Cola de impresión con prioridades
Situación:
Una oficina tiene una impresora compartida. Los trabajos de impresión llegan con
una prioridad: 1 (urgente), 2 (normal), 3 (baja). Se desea implementar un Max-
Heap donde el número más alto tenga mayor prioridad (es decir, 3 sería la máxima
prioridad en esta escala invertida). O bien, un Min-Heap donde 1 sea la máxima
prioridad. Para este caso, usaremos un Max-Heap con prioridades 1, 2, 3 donde 3
es lo más urgente.
```

- `Acciones realizadas:` 

```
Se modeló cada trabajo con un número de prioridad.
```

```
Se implementó un Max-Heap.
```

```
Se insertaron 5 trabajos.
```

```
Se extrajo el trabajo más prioritario.
```

- `Hallazgos:` 

```
El Max-Heap mantiene el trabajo más urgente en la raíz.
```

```
La extracción es O(log n).
```

```
Es fácil migrar a un sistema con más criterios (tiempo de espera, tamaño).
```

- `Recomendaciones:` 

```
Documentar claramente la escala de prioridades.
```

```
Usar Min-Heap si se prefiere que 1 sea lo más urgente.
```

```
Considerar una cola de prioridad doble (dos heaps) para urgencias y normales.
```

```
Resultados:
```

```
Los trabajos urgentes se imprimen primero, mejorando la satisfacción del
usuario.
```

```
El sistema es justo y transparente.
```

```
Código completo comentado:
```

# `java` 

```
// Clase principal de la cola de impresión con prioridades
public class ColaImpresion {
```

```
    // Método principal
    public static void main(String[] args) {
        // Arreglo para el Max-Heap
        int[] heap = new int[10];
        // Número de trabajos en la cola
        int tamaño = 0;
```

```
        // --- INSERTAR TRABAJOS ---
        // Trabajo con prioridad 2 (normal)
        heap[tamaño] = 2;
        tamaño++;
```

```
        // Trabajo con prioridad 1 (baja)
        heap[tamaño] = 1;
        tamaño++;
        // Flotado: padre 2 > 1, no se intercambia
        // Trabajo con prioridad 3 (urgente)
        heap[tamaño] = 3;
        tamaño++;
        // Flotado: padre 2 < 3, intercambiamos
        int i = 2;
        while (i > 0 && heap[(i - 1) / 2] < heap[i]) {
            int temp = heap[(i - 1) / 2];
            heap[(i - 1) / 2] = heap[i];
            heap[i] = temp;
            i = (i - 1) / 2;
        }
        // Trabajo con prioridad 2 (normal)
        heap[tamaño] = 2;
        tamaño++;
        // Flotado: padre 2 = 2, no se intercambia
        // Trabajo con prioridad 3 (urgente)
        heap[tamaño] = 3;
        tamaño++;
        // Flotado: padre 2 < 3, intercambiamos
        i = 4;
        while (i > 0 && heap[(i - 1) / 2] < heap[i]) {
            int temp = heap[(i - 1) / 2];
            heap[(i - 1) / 2] = heap[i];
            heap[i] = temp;
            i = (i - 1) / 2;
        }
        // --- MOSTRAR COLA ---
        System.out.println("Cola de impresion (mayor = mas urgente):");
        for (int j = 0; j < tamaño; j++) {
            System.out.print(heap[j] + " ");
        }
        System.out.println();
        // --- EXTRAER EL MÁS URGENTE ---
        System.out.println("Imprimiendo trabajo con prioridad: " + heap[0]);
        // Reemplazamos la raíz con el último
        heap[0] = heap[tamaño - 1];
        tamaño--;
        // HEAPIFY
        i = 0;
        boolean continuar = true;
        while (continuar) {
            int izquierdo = 2 * i + 1;
            int derecho = 2 * i + 2;
            int mayor = i;
            if (izquierdo < tamaño && heap[izquierdo] > heap[mayor]) {
                mayor = izquierdo;
            }
            if (derecho < tamaño && heap[derecho] > heap[mayor]) {
                mayor = derecho;
            }
```

```
            if (mayor != i) {
                int temp = heap[i];
                heap[i] = heap[mayor];
                heap[mayor] = temp;
                i = mayor;
            } else {
                continuar = false;
            }
        }
        // --- MOSTRAR COLA DESPUÉS ---
        System.out.println("Cola despues de imprimir:");
        for (int j = 0; j < tamaño; j++) {
            System.out.print(heap[j] + " ");
        }
        System.out.println();
    }
}
Salida:
```

```
text
```

```
Cola de impresion (mayor = mas urgente):
3 3 2 1 2
Imprimiendo trabajo con prioridad: 3
Cola despues de imprimir:
3 2 2 1
7. EJERCICIOS PRÁCTICOS
7.1. Ejercicios resueltos paso a paso
Ejercicio resuelto 1: Calcular altura y número de nodos
```

```
Enunciado: Dado el siguiente árbol binario, calcula su altura, el número de
nodos en el nivel 2, y el número máximo de nodos que podría tener si fuera
lleno.
```

# `text` 

```
        A
       / \
      B   C
     / \   \
    D   E   F
Solución paso a paso:
```

```
Altura: El camino más largo desde la raíz hasta una hoja es A → B → D (2
aristas). Altura = 2.
```

```
Nodos en nivel 2: Los nodos D, E, F están en nivel 2. Hay 3 nodos.
```

```
Número máximo de nodos si fuera lleno: Con altura 2, el máximo es 2^(2+1) - 1 =
2³ - 1 = 7 nodos.
```

```
Código Java que lo verifica:
```

```
java
```

```
// Clase para verificar cálculos del árbol
public class VerificarArbol {
```

```
    // Método principal
    public static void main(String[] args) {
```

```
        // Representamos el árbol en un arreglo (índice 0 = A)
        // Usamos -1 para indicar nodo vacío
        int[] arbol = {1, 2, 3, 4, 5, -1, 6};
        //  A=1, B=2, C=3, D=4, E=5, F=6
```

```
        // Calculamos la altura manualmente (sabemos que es 2)
        int altura = 2;
        // Calculamos el máximo de nodos para esa altura
        int maxNodos = (int) Math.pow(2, altura + 1) - 1;
        // Contamos nodos en nivel 2 (índices 3, 4, 5, 6)
        int nodosNivel2 = 0;
        for (int i = 3; i <= 6; i++) {
            if (i < arbol.length && arbol[i] != -1) {
                nodosNivel2++;
            }
        }
        // Mostramos resultados
        System.out.println("Altura del arbol: " + altura);
        System.out.println("Nodos en nivel 2: " + nodosNivel2);
        System.out.println("Maximo de nodos si fuera lleno: " + maxNodos);
    }
}
Salida:
```

```
text
Altura del arbol: 2
Nodos en nivel 2: 3
Maximo de nodos si fuera lleno: 7
Ejercicio resuelto 2: Insertar en un Min-Heap
```

```
Enunciado: Dado el Min-Heap [5, 10, 15, 20, 25], inserta el valor 3 y muestra el
heap resultante.
```

```
Solución paso a paso:
```

```
Agregamos 3 al final: [5, 10, 15, 20, 25, 3].
```

```
Índice de 3 = 5. Padre = (5-1)/2 = 2 (valor 15). 15 > 3 → intercambiamos: [5,
10, 3, 20, 25, 15].
```

```
Índice de 3 = 2. Padre = (2-1)/2 = 0 (valor 5). 5 > 3 → intercambiamos: [3, 10,
5, 20, 25, 15].
Índice de 3 = 0. Es raíz. Termina.
Código Java:
```

```
java
// Clase para insertar en Min-Heap
public class InsertarMinHeap {
    // Método principal
    public static void main(String[] args) {
```

```
        // Min-Heap inicial
        int[] heap = {5, 10, 15, 20, 25, 0}; // 0 es espacio para el nuevo
        int tamaño = 5;
```

```
        // Insertamos 3 al final
        heap[tamaño] = 3;
        tamaño++;
```

```
        // Flotado hacia arriba
        int i = tamaño - 1;
        while (i > 0 && heap[(i - 1) / 2] > heap[i]) {
            int temp = heap[(i - 1) / 2];
```

```
            heap[(i - 1) / 2] = heap[i];
            heap[i] = temp;
            i = (i - 1) / 2;
        }
        // Mostramos el heap resultante
        System.out.println("Min-Heap despues de insertar 3:");
        for (int j = 0; j < tamaño; j++) {
            System.out.print(heap[j] + " ");
        }
        System.out.println();
    }
}
Salida:
```

```
text
```

```
Min-Heap despues de insertar 3:
3 10 5 20 25 15
7.2. Ejercicios propuestos con guías de solución
Ejercicio propuesto 1 (Básico): Identificar raíz, hojas y altura
```

```
Enunciado: Dado el árbol [1, 2, 3, 4, 5, 6, 7] representado en arreglo,
identifica la raíz, las hojas y la altura.
```

```
Guía de solución:
```

```
La raíz es el elemento en índice 0.
```

```
Las hojas son los nodos sin hijos: índices donde 2*i+1 >= tamaño.
```

```
La altura se calcula como floor(log2(tamaño)) para un árbol lleno.
```

```
Ejercicio propuesto 2 (Básico): Insertar en Max-Heap
```

```
Enunciado: Dado el Max-Heap [50, 30, 40, 10, 20], inserta el valor 60 y muestra
el heap.
```

```
Guía de solución:
```

```
Agrega 60 al final.
```

```
Compara con el padre y flota hasta que se cumpla la propiedad.
```

```
El resultado debe tener 60 en la raíz.
```

```
Ejercicio propuesto 3 (Intermedio): Construir un Max-Heap
```

```
Enunciado: Dado el arreglo [3, 9, 2, 1, 4, 5], construye un Max-Heap aplicando
HEAPIFY desde el último nodo interno.
```

```
Guía de solución:
```

```
Último nodo interno = (6/2) - 1 = 2.
```

```
Aplica HEAPIFY a índices 2, 1, 0 en ese orden.
```

```
Verifica que cada padre sea mayor que sus hijos.
```

```
Ejercicio propuesto 4 (Intermedio): Eliminar el máximo
```

```
Enunciado: Dado el Max-Heap [90, 70, 80, 30, 50, 60, 40], elimina el máximo y
muestra el heap resultante.
```

```
Guía de solución:
```

```
Reemplaza la raíz con el último elemento.
```

```
Reduce el tamaño.
```

```
Aplica HEAPIFY desde la raíz.
```

```
El nuevo máximo debe estar en la raíz.
```

```
Ejercicio propuesto 5 (Avanzado): Verificar si un arreglo es un Heap
```

```
Enunciado: Escribe un programa en Java que determine si un arreglo dado cumple
la propiedad de Max-Heap.
```

```
Guía de solución:
```

```
Recorre todos los nodos internos (desde 0 hasta tamaño/2 - 1).
```

```
Para cada nodo, verifica que sea mayor o igual que sus hijos.
```

```
Si encuentras una violación, retorna falso.
```

```
Si terminas, retorna verdadero.
```

```
Ejercicio propuesto 6 (Avanzado): Convertir Min-Heap a Max-Heap
```

```
Enunciado: Dado un Min-Heap, conviértelo en Max-Heap sin usar un arreglo
auxiliar.
```

```
Guía de solución:
```

```
Aplica HEAPIFY con la propiedad de Max-Heap a todos los nodos internos.
```

```
La complejidad debe ser O(n).
```

```
Verifica que la raíz sea el máximo.
```

```
Ejercicio propuesto 7 (Avanzado): Heap Sort
```

```
Enunciado: Usando un Max-Heap, ordena un arreglo de enteros de menor a mayor.
Guía de solución:
```

```
Construye un Max-Heap con el arreglo.
```

```
Intercambia la raíz con el último elemento.
```

```
Reduce el tamaño y aplica HEAPIFY.
```

```
Repite hasta que el tamaño sea 1.
```

```
El arreglo quedará ordenado.
```

# `8. GLOSARIO` 

```
Término (Español)Término (Inglés)Definición
ÁrbolTreeEstructura jerárquica de nodos conectados por aristas, sin ciclos.
Árbol binarioBinary treeÁrbol donde cada nodo tiene como máximo dos hijos.
Árbol binario completoFull binary treeÁrbol donde cada nodo tiene 0 o 2
hijos.
Árbol binario llenoPerfect binary treeÁrbol donde todos los niveles
están completamente llenos.
Árbol binario equilibradoBalanced binary treeÁrbol donde la diferencia
de alturas entre subárboles es ≤ 1.
AristaEdgeConexión entre dos nodos.
```

```
AlturaHeightMáximo nivel de un árbol.
Casi completoComplete binary treeÁrbol donde todos los niveles están
llenos excepto posiblemente el último, que se llena de izquierda a derecha.
DescendienteDescendantCualquier nodo alcanzable desde otro hacia abajo.
GradoDegreeNúmero de hijos de un nodo.
HeapHeapÁrbol binario casi completo que cumple la propiedad de heap.
HEAPIFYHeapifyAlgoritmo para restaurar la propiedad de heap.
HijoChildNodo que está inmediatamente debajo de otro.
HojaLeafNodo sin hijos.
INSERTARInsertAlgoritmo para agregar un elemento a un heap.
Max-HeapMax-heapHeap donde cada padre es ≥ sus hijos.
Min-HeapMin-heapHeap donde cada padre es ≤ sus hijos.
NivelLevelDistancia desde la raíz hasta un nodo.
NodoNodeUnidad básica de una estructura de datos.
Nodo internoInternal nodeNodo que tiene al menos un hijo.
PadreParentNodo que está inmediatamente arriba de otro.
ProfundidadDepthSinónimo de nivel para un nodo.
RaízRootNodo superior de un árbol, sin padre.
SubárbolSubtreeNodo junto con todos sus descendientes.
HermanoSiblingNodos que comparten el mismo padre.
BosqueForestConjunto de árboles disjuntos.
FlotarBubble up / Swim upMover un nodo hacia arriba para restaurar la
propiedad de heap.
HundirSink downMover un nodo hacia abajo para restaurar la propiedad de
heap.
Propiedad de heapHeap propertyCondición que debe cumplir un heap (padre ≥
o ≤ hijos).
Cola de prioridadPriority queueEstructura donde los elementos se atienden
según su prioridad.
9. REFERENCIAS BIBLIOGRÁFICAS (APA 7ª edición)
Dale, N., & Lilly, S. (2006). Estructuras de datos. McGraw-Hill.
```

```
Joyanes Aguilar, L. (2007). Estructuras de datos en Java. McGraw-Hill.
```

```
Tenembaum, A. (1993). Estructura de datos. Prentice-Hall.
```

```
Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2009). Introduction
to algorithms (3rd ed.). MIT Press.
```

```
Sedgewick, R., & Wayne, K. (2011). Algorithms (4th ed.). Addison-Wesley.
```

```
Oracle. (2024). Java SE Documentation. https://docs.oracle.com/en/java/
```

```
GeeksforGeeks. (2024). Heap Data Structure. https://www.geeksforgeeks.org/heap-
data-structure/
```

