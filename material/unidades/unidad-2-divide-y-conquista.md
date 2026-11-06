# Unidad II: Divide y conquista

> Texto extraído de `Guia Unidad II  Divide y Conquista 2026-2.docx` (fuente original en esta carpeta). Las erratas detectadas están en `unidad-2-erratas.md`.

```text
GUÍA UNIDAD II: DIVIDE Y CONQUISTA
ÍNDICE DETALLADO
Introducción a la unidad1.1. Propósito y relevancia1.2. Objetivos de aprendizaje1.3. ¿Por qué Java para esta unidad?1.4. ¿Qué es la estrategia Divide y Conquista?1.5. Condiciones para aplicar Divide y Conquista1.6. Ejemplo cotidiano
Notación de complejidad temporal (Big O)2.1. ¿Qué es Big O?2.2. Tabla de notaciones comunes2.3. Cómo se calcula2.4. ¿Por qué es útil?
Tema 1: Búsqueda Binaria (Binary Search)3.1. Concepto fundamental3.2. Complejidad temporal3.3. Versión recursiva en Java3.4. Versión iterativa en Java3.5. Ejercicios del tema
Tema 2: Máximos y Mínimos4.1. Problema general4.2. Estrategia Divide y Conquista4.3. Análisis de eficiencia4.4. Implementación en Java4.5. Ejercicios del tema
Tema 3: Sortmerge (Merge Sort)5.1. Concepto de ordenamiento por intercalación5.2. Características fundamentales5.3. Implementación recursiva en Java5.4. Ejercicios del tema
Tema 4: Sortmerge 2 (Merge Sort iterativo)6.1. ¿Qué es Sortmerge 2?6.2. Diferencias con Sortmerge recursivo6.3. Implementación iterativa en Java6.4. Ejercicios del tema
Tema 5: Quicksort (Ordenamiento rápido)7.1. Concepto fundamental7.2. Complejidad7.3. Partición de Lomuto7.4. Mediana de tres7.5. Implementación en Java7.6. Ejercicios del tema
Casos prácticos integradores8.1. Caso 1: Sistema de búsqueda en guía telefónica8.2. Caso 2: Optimización de pedidos en comercio electrónico
Ejercicios prácticos9.1. Ejercicios resueltos paso a paso9.2. Ejercicios propuestos con guías de solución
Glosario
Referencias bibliográficas
DESARROLLO DE CONTENIDOS
1. INTRODUCCIÓN A LA UNIDAD
1.1. Propósito y relevancia
Imagina que tienes una pila de 1,000 fotografías desordenadas y necesitas encontrar la foto de tu amigo Juan. ¿Revisarías una por una desde el inicio? Probablemente no. En lugar de eso, podrías separar la pila en dos mitades, ver si Juan está en alguna de ellas, y repetir el proceso con la mitad que lo contiene. Eso es exactamente la estrategia de divide y conquista (del inglés divide and conquer).
Esta unidad te enseñará a aplicar esta estrategia para resolver problemas de búsqueda, ordenamiento e intercalación de manera eficiente. Los algoritmos que aprenderás —búsqueda binaria, merge sort, quicksort— son la base de prácticamente todo el software moderno: desde bases de datos hasta videojuegos, desde sistemas operativos hasta inteligencia artificial.
Importante: Un algoritmo O(n²) puede tardar horas en ordenar un millón de datos. Un algoritmo O(n log n) tarda segundos. La diferencia entre un programa usable y uno inusable está, muchas veces, en la estrategia algorítmica.
1.2. Objetivos de aprendizaje
Al finalizar esta unidad, serás capaz de:
Resolver problemas de construcción de programas usando recursividad (recursion).
Entender los algoritmos recursivos que aplican la estrategia divide y conquista.
Aplicar divide y conquista en algoritmos de búsqueda (search), ordenamiento (sorting) e intercalación (merge).
Analizar la complejidad temporal de cada algoritmo usando notación Big O.
Seleccionar el algoritmo adecuado según el contexto y las restricciones de memoria.
1.3. ¿Por qué Java para esta unidad?
Ventaja | Explicación
Recursividad clara | Java permite métodos recursivos con sintaxis explícita.
Tipado fuerte | Refuerza la comprensión de tipos y estructuras.
Arreglos nativos | int[], double[], etc. son eficientes y fáciles de usar.
Biblioteca estándar | Arrays.sort() usa quicksort internamente, pero aquí lo construiremos desde cero.
Portabilidad | El mismo código corre en cualquier sistema operativo.
Nota sobre las restricciones: En Java, para implementar recursividad es necesario definir métodos. Dado que la recursión es el corazón de esta unidad, usaremos métodos estáticos dentro de la misma clase, sin crear clases auxiliares ni usar bibliotecas externas más allá de java.util.Scanner para entrada de datos. Esto mantiene el espíritu de "solo main()" adaptado a Java.
1.4. ¿Qué es la estrategia Divide y Conquista?
Definición: Divide y conquista es una estrategia algorítmica que consiste en:
Dividir (Divide): descomponer el problema en subproblemas más pequeños del mismo tipo.
Conquistar (Conquer): resolver los subproblemas recursivamente. Si son lo suficientemente pequeños, resolverlos directamente (caso base).
Combinar (Combine): unir las soluciones de los subproblemas para formar la solución del problema original.
Traducción:
Divide and Conquer = Divide y Conquista
Divide = Dividir
Conquer = Conquistar
Combine = Combinar
Base case = Caso base
Recursion = Recursividad
1.5. Condiciones para aplicar Divide y Conquista
Un problema es candidato a esta técnica si cumple:
Puede dividirse en subproblemas más pequeños de la misma naturaleza.
Los subproblemas son independientes (no requieren información entre sí).
Existe un caso base lo suficientemente pequeño para resolverse directamente.
Las soluciones de los subproblemas pueden combinarse para formar la solución del problema original.
1.6. Ejemplo cotidiano
Escenario: Organizar una fiesta con 32 invitados y debes asignarles asientos en 4 mesas de 8 personas cada una, de modo que los amigos cercanos queden juntos.
Aplicación de divide y conquista:
Dividir: Separa los 32 invitados en 4 grupos de 8, según afinidades.
Conquistar: Dentro de cada grupo de 8, organiza las sillas de esa mesa.
Combinar: Junta las mesas ya organizadas en el salón. No necesitas reordenar entre mesas porque los grupos son independientes.
2. NOTACIÓN DE COMPLEJIDAD TEMPORAL (BIG O)
2.1. ¿Qué es Big O?
Antes de estudiar los algoritmos, debes entender cómo medimos su eficiencia. Usamos la notación Big O (llamada así por la letra "O" mayúscula, que significa "orden de"). Indica cómo crece el tiempo de ejecución (o el uso de memoria) de un algoritmo cuando el tamaño de la entrada aumenta.
2.2. Tabla de notaciones comunes
Notación | Se lee | Significado | Ejemplo de algoritmo
O(1) | "O de uno" | Tiempo constante, no depende de n. | Acceder a un elemento de un arreglo por índice.
O(log n) | "O de logaritmo de n" | Crece muy lentamente. Cada duplicación de n añade una operación. | Búsqueda binaria.
O(n) | "O de n" | Tiempo lineal, proporcional a n. | Búsqueda secuencial, recorrer un arreglo.
O(n log n) | "O de n log n" | Típico de ordenamientos eficientes. | Merge sort, Quicksort (promedio).
O(n²) | "O de n al cuadrado" | Tiempo cuadrático. Para n=1000, ~1,000,000 operaciones. | Ordenamiento burbuja, Quicksort (peor caso).
O(2ⁿ) | "O de 2 elevado a n" | Tiempo exponencial, inútil para n grandes. | Problema del viajante (solución ingenua).
2.3. Cómo se calcula
Cuando analizamos un algoritmo, contamos las operaciones fundamentales (comparaciones, intercambios, etc.) y nos quedamos con el término que más crece cuando n es grande.
Ejemplos:
Si un algoritmo hace 3n² + 5n + 2 operaciones, decimos que es O(n²) porque n² domina a n.
Si hace 2n log n + 4n, decimos que es O(n log n).
Regla mnemotécnica: Big O describe la forma de la curva de crecimiento, no el tiempo exacto.
2.4. ¿Por qué es útil?
Comparar algoritmos por su Big O nos permite elegir el más adecuado sin necesidad de programarlos. Por ejemplo, para ordenar 1 millón de números:
Un algoritmo O(n²) haría ~1 billón de operaciones (demasiado).
Un algoritmo O(n log n) haría ~20 millones de operaciones (viable).
Curiosidad: El nombre "Big O" fue popularizado por el matemático Paul Bachmann en 1894 y luego por Edmund Landau. Por eso también se le llama "notación de Landau".
3. TEMA 1: BÚSQUEDA BINARIA (BINARY SEARCH)
3.1. Concepto fundamental
La búsqueda binaria es un algoritmo eficiente para encontrar un elemento dentro de un arreglo ordenado. Su principio es:
Compara el elemento buscado con el elemento central del arreglo.
Si son iguales, termina.
Si el buscado es menor, busca en la mitad izquierda.
Si es mayor, busca en la mitad derecha.
Repite hasta encontrar el elemento o determinar que no existe.
Condición obligatoria: El arreglo debe estar previamente ordenado (ascendente o descendentemente).
Analogía: Es como buscar una palabra en un diccionario. Abres por la mitad, ves si la palabra está antes o después, y repites con la mitad correspondiente. No lees todas las páginas.
Traducción:
Binary Search = Búsqueda Binaria
Sorted array = Arreglo ordenado
Middle = Medio
Target = Objetivo
3.2. Complejidad temporal
Peor caso: O(log n) — si duplicas el tamaño del arreglo, solo realizas una comparación adicional.
Mejor caso: O(1) — el elemento está justo en el medio.
Caso promedio: O(log n).
Ejemplo: Para un arreglo de 1,000,000 elementos, la búsqueda binaria hace como máximo ~20 comparaciones. La búsqueda secuencial haría hasta 1,000,000.
3.3. Versión recursiva en Java
// Importamos Scanner para posible entrada de datos
import java.util.Scanner;
// Clase principal de la guía
public class BusquedaBinaria {
    // Método recursivo de búsqueda binaria
    // arr: arreglo ordenado
    // inicio: índice izquierdo del rango
    // fin: índice derecho del rango
    // objetivo: valor a buscar
    static int busquedaBinariaRec(int[] arr, int inicio, int fin, int objetivo) {
        // Caso base: si el rango está vacío, el objetivo no existe
        if (inicio > fin) {
            return -1; // -1 significa "no encontrado"
        }
        // Calculamos el índice medio del rango actual
        int medio = (inicio + fin) / 2;
        // Caso base de éxito: encontramos el objetivo
        if (arr[medio] == objetivo) {
            return medio; // devolvemos la posición
        }
        // Si el objetivo es menor, buscamos en la mitad izquierda
        if (objetivo < arr[medio]) {
            return busquedaBinariaRec(arr, inicio, medio - 1, objetivo);
        }
        // Si el objetivo es mayor, buscamos en la mitad derecha
        return busquedaBinariaRec(arr, medio + 1, fin, objetivo);
    }
    // Método principal
    public static void main(String[] args) {
        // Creamos un arreglo ordenado (requisito indispensable)
        int[] datos = {3, 7, 12, 19, 25, 33, 42, 56, 71};
        // Valor que queremos buscar
        int objetivo = 25;
        // Llamamos a la función con el arreglo completo
        int indice = busquedaBinariaRec(datos, 0, datos.length - 1, objetivo);
        // Mostramos el resultado
        if (indice != -1) {
            System.out.println("El elemento " + objetivo + " se encuentra en el indice " + indice + ".");
        } else {
            System.out.println("El elemento " + objetivo + " no esta en el arreglo.");
        }
    }
}
Salida:
text
El elemento 25 se encuentra en el indice 4.
Explicación línea por línea:
Línea | Código | Explicación
1 | import java.util.Scanner; | Importa la clase Scanner (no se usa aquí, pero se incluye por si se necesita entrada).
4 | public class BusquedaBinaria { | Declara la clase principal.
10 | static int busquedaBinariaRec(...) | Método recursivo. static permite llamarlo desde main sin crear objetos.
13 | if (inicio > fin) | Caso base: rango vacío.
14 | return -1; | Devuelve -1 (convención para "no encontrado").
18 | int medio = (inicio + fin) / 2; | Calcula el punto medio. En Java, la división entera trunca.
21 | if (arr[medio] == objetivo) | Compara el elemento medio con el objetivo.
22 | return medio; | Si son iguales, devuelve la posición.
26 | if (objetivo < arr[medio]) | Si el objetivo es menor, busca a la izquierda.
27 | return busquedaBinariaRec(arr, inicio, medio - 1, objetivo); | Llamada recursiva con el rango izquierdo.
31 | return busquedaBinariaRec(arr, medio + 1, fin, objetivo); | Llamada recursiva con el rango derecho.
38 | int[] datos = {3, 7, 12, 19, 25, 33, 42, 56, 71}; | Arreglo ordenado de ejemplo.
41 | int objetivo = 25; | Valor a buscar.
44 | int indice = busquedaBinariaRec(datos, 0, datos.length - 1, objetivo); | Llamada inicial con todo el arreglo.
47-51 | if (indice != -1) { ... } | Muestra el resultado según si se encontró o no.
3.4. Versión iterativa en Java
// Clase principal para búsqueda binaria iterativa
public class BusquedaBinariaIterativa {
    // Método iterativo de búsqueda binaria
    static int busquedaBinariaIter(int[] arr, int objetivo) {
        // Inicializamos los límites del rango de búsqueda
        int inicio = 0;
        int fin = arr.length - 1;
        // Mientras el rango no esté vacío
        while (inicio <= fin) {
            // Calculamos el punto medio
            int medio = (inicio + fin) / 2;
            // Si encontramos el objetivo, devolvemos la posición
            if (arr[medio] == objetivo) {
                return medio;
            }
            // Si el objetivo es menor, descartamos la mitad derecha
            if (objetivo < arr[medio]) {
                fin = medio - 1;
            } else {
                // Si es mayor, descartamos la mitad izquierda
                inicio = medio + 1;
            }
        }
        // Si salimos del bucle, el objetivo no existe
        return -1;
    }
    // Método principal
    public static void main(String[] args) {
        // Arreglo ordenado
        int[] datos = {3, 7, 12, 19, 25, 33, 42, 56, 71};
        // Objetivo a buscar
        int objetivo = 19;
        // Llamada al método iterativo
        int indice = busquedaBinariaIter(datos, objetivo);
        // Mostramos el resultado
        System.out.println("Resultado iterativo: indice " + indice);
    }
}
Salida:
text
Resultado iterativo: indice 3
3.5. Ejercicios del tema
Ejercicio 1.1 (Reflexión aplicada):Si tienes un arreglo de 500,000 elementos ordenados, ¿cuántas comparaciones hará la búsqueda binaria en el peor caso?Ayuda: log₂(500,000) ≈ 19, por lo tanto unas 19-20 comparaciones. Explica por qué es O(log n) y no O(n).
Ejercicio 1.2 (Análisis de escenario):Un estudiante intenta aplicar búsqueda binaria a un arreglo [20, 15, 10, 5, 0] (orden descendente). ¿Funcionará correctamente con el algoritmo mostrado? Explica qué modificación necesitarías.
Ejercicio 1.3 (Elaboración de código):Escribe un programa en Java que, dada una lista ordenada y un número, devuelva la posición donde debería insertarse ese número si no existe, manteniendo el orden. Usa búsqueda binaria.
4. TEMA 2: MÁXIMOS Y MÍNIMOS
4.1. Problema general
Dado un arreglo de n elementos (no necesariamente ordenados), encontrar simultáneamente el valor máximo y el valor mínimo. La solución ingenua (recorrido lineal) requiere 2n - 2 comparaciones en el peor caso (comparar cada elemento con el máximo actual y con el mínimo actual).
Traducción:
Maximum = Máximo
Minimum = Mínimo
Comparisons = Comparaciones
4.2. Estrategia Divide y Conquista
Principio:
Dividir el arreglo en dos mitades.
Encontrar el máximo y mínimo de cada mitad recursivamente.
Combinar:
Máximo global = max(máximo_izquierda, máximo_derecha)
Mínimo global = min(mínimo_izquierda, mínimo_derecha)
Diagrama:
text
Arreglo: [8, 3, 15, 6, 2, 11, 7, 4]
Dividir:
[8, 3, 15, 6]        [2, 11, 7, 4]
[8, 3] [15, 6]       [2, 11] [7, 4]
[8][3] [15][6]       [2][11] [7][4]
Conquistar:
[8,3] -> max=8, min=3    [15,6] -> max=15, min=6
[2,11] -> max=11, min=2  [7,4] -> max=7, min=4
Combinar:
[8,3,15,6] -> max=15, min=3
[2,11,7,4] -> max=11, min=2
Global: max=15, min=2
4.3. Análisis de eficiencia
Número de comparaciones: Aproximadamente 3n/2 - 2 en el peor caso.
Complejidad temporal: O(n) — tiempo lineal, con una constante mejor que el método ingenuo.
4.4. Implementación en Java
// Clase principal para máximos y mínimos por divide y conquista
public class MaxMinDYC {
    // Método recursivo que devuelve un arreglo de dos posiciones:
    // posicion 0 = maximo, posicion 1 = minimo
    static int[] maxMinDYC(int[] arr, int inicio, int fin) {
        // CASO BASE 1: un solo elemento
        if (inicio == fin) {
            // El único elemento es a la vez máximo y mínimo
            return new int[]{arr[inicio], arr[inicio]};
        }
        // CASO BASE 2: dos elementos
        if (inicio + 1 == fin) {
            // Comparamos directamente
            if (arr[inicio] > arr[fin]) {
                return new int[]{arr[inicio], arr[fin]}; // max, min
            } else {
                return new int[]{arr[fin], arr[inicio]}; // max, min
            }
        }
        // CASO GENERAL: más de dos elementos
        // DIVIDIR: calcular el punto medio
        int medio = (inicio + fin) / 2;
        // CONQUISTAR: resolver la mitad izquierda
        int[] izq = maxMinDYC(arr, inicio, medio);
        // CONQUISTAR: resolver la mitad derecha
        int[] der = maxMinDYC(arr, medio + 1, fin);
        // COMBINAR: máximo global y mínimo global
        int maxGlobal = Math.max(izq[0], der[0]);
        int minGlobal = Math.min(izq[1], der[1]);
        // Retornar el resultado combinado
        return new int[]{maxGlobal, minGlobal};
    }
    // Método principal
    public static void main(String[] args) {
        // Arreglo de ejemplo sin ordenar
        int[] datos = {8, 3, 15, 6, 2, 11, 7, 4};
        // Llamada al método con todo el arreglo
        int[] resultado = maxMinDYC(datos, 0, datos.length - 1);
        // Mostramos los resultados
        System.out.println("Arreglo: ");
        for (int i = 0; i < datos.length; i++) {
            System.out.print(datos[i] + " ");
        }
        System.out.println();
        System.out.println("Maximo: " + resultado[0]);
        System.out.println("Minimo: " + resultado[1]);
    }
}
Salida:
text
Arreglo:
8 3 15 6 2 11 7 4
Maximo: 15
Minimo: 2
Explicación línea por línea:
Línea | Código | Explicación
6 | static int[] maxMinDYC(...) | Método recursivo que devuelve un arreglo de 2 enteros.
9 | if (inicio == fin) | Caso base: un solo elemento.
11 | return new int[]{arr[inicio], arr[inicio]}; | Devuelve el mismo valor como max y min.
15 | if (inicio + 1 == fin) | Caso base: dos elementos.
17-21 | if (arr[inicio] > arr[fin]) { ... } | Compara y devuelve en orden (max, min).
26 | int medio = (inicio + fin) / 2; | Calcula el punto medio.
29 | int[] izq = maxMinDYC(arr, inicio, medio); | Resuelve la mitad izquierda.
32 | int[] der = maxMinDYC(arr, medio + 1, fin); | Resuelve la mitad derecha.
35 | int maxGlobal = Math.max(izq[0], der[0]); | Combina los máximos.
36 | int minGlobal = Math.min(izq[1], der[1]); | Combina los mínimos.
39 | return new int[]{maxGlobal, minGlobal}; | Devuelve el resultado.
4.5. Ejercicios del tema
Ejercicio 2.1 (Aplicación de procedimiento):Aplica manualmente el algoritmo divide y conquista para encontrar máximos y mínimos del arreglo: [45, 22, 89, 12, 37, 61, 5, 73, 28]. Dibuja el árbol de recursión.
Ejercicio 2.2 (Análisis de escenario):¿Por qué el algoritmo divide y conquista para máximos y mínimos usa exactamente 3n/2 - 2 comparaciones en el peor caso? Explica la razón matemática.
Ejercicio 2.3 (Reflexión aplicada):Si el arreglo ya está ordenado ascendentemente, ¿el algoritmo podría optimizarse aún más? ¿Cuántas comparaciones necesitarías ahora?
5. TEMA 3: SORTMERGE (MERGE SORT)
5.1. Concepto de ordenamiento por intercalación
Sortmerge (también llamado Merge Sort) es un algoritmo de ordenamiento que aplica divide y conquista. Funciona así:
Divide: Si el arreglo tiene más de un elemento, se divide en dos mitades.
Conquista (ordenar): Se ordena recursivamente cada mitad.
Combina (intercala/merge): Se mezclan las dos mitades ordenadas en un solo arreglo ordenado.
Analogía: Imagina que tienes dos pilas de exámenes ya calificados por nota. Para formar una sola pila ordenada, tomas el examen con menor nota de la parte superior de cualquiera de las dos pilas y lo colocas en la nueva pila. Repites hasta agotar ambas pilas.
Traducción:
Merge Sort = Ordenamiento por mezcla/intercalación
Merge = Intercalar, mezclar
Stable = Estable (mantiene orden relativo)
5.2. Características fundamentales
Característica | Valor
Complejidad temporal | O(n log n) en todos los casos
Complejidad espacial | O(n) — requiere memoria adicional
Estable | Sí, los elementos iguales mantienen su orden relativo
Adecuado para | Listas enlazadas y archivos grandes
Diagrama del proceso:
text
[38, 27, 43, 3, 9, 82, 10]
Dividir:
[38, 27, 43, 3]    [9, 82, 10]
[38, 27] [43, 3]   [9, 82] [10]
[38][27] [43][3]   [9][82] [10]
Ordenar y combinar:
[27, 38] [3, 43]   [9, 82] [10]
[3, 27, 38, 43]    [9, 10, 82]
[3, 9, 10, 27, 38, 43, 82]
5.3. Implementación recursiva en Java
// Clase principal para Merge Sort
public class MergeSort {
    // Método recursivo de Merge Sort
    static void mergeSort(int[] arr, int inicio, int fin) {
        // Caso base: si el rango tiene 0 o 1 elemento, ya está ordenado
        if (inicio >= fin) {
            return;
        }
        // DIVIDIR: calcular el punto medio
        int medio = (inicio + fin) / 2;
        // CONQUISTAR: ordenar la mitad izquierda
        mergeSort(arr, inicio, medio);
        // CONQUISTAR: ordenar la mitad derecha
        mergeSort(arr, medio + 1, fin);
        // COMBINAR: intercalar las dos mitades
        merge(arr, inicio, medio, fin);
    }
    // Método que intercala dos subarreglos ordenados
    static void merge(int[] arr, int inicio, int medio, int fin) {
        // Calculamos los tamaños de las dos mitades
        int n1 = medio - inicio + 1;
        int n2 = fin - medio;
        // Creamos arreglos temporales
        int[] izq = new int[n1];
        int[] der = new int[n2];
        // Copiamos los datos a los arreglos temporales
        for (int i = 0; i < n1; i++) {
            izq[i] = arr[inicio + i];
        }
        for (int j = 0; j < n2; j++) {
            der[j] = arr[medio + 1 + j];
        }
        // Índices para recorrer izq, der y el arreglo original
        int i = 0;
        int j = 0;
        int k = inicio;
        // Intercalamos mientras ambas mitades tengan elementos
        while (i < n1 && j < n2) {
            if (izq[i] <= der[j]) {
                arr[k] = izq[i];
                i++;
            } else {
                arr[k] = der[j];
                j++;
            }
            k++;
        }
        // Copiamos los elementos restantes de izq (si quedan)
        while (i < n1) {
            arr[k] = izq[i];
            i++;
            k++;
        }
        // Copiamos los elementos restantes de der (si quedan)
        while (j < n2) {
            arr[k] = der[j];
            j++;
            k++;
        }
    }
    // Método principal
    public static void main(String[] args) {
        // Arreglo de ejemplo
        int[] datos = {38, 27, 43, 3, 9, 82, 10};
        // Mostramos el arreglo original
        System.out.println("Arreglo original:");
        for (int i = 0; i < datos.length; i++) {
            System.out.print(datos[i] + " ");
        }
        System.out.println();
        // Llamamos a Merge Sort
        mergeSort(datos, 0, datos.length - 1);
        // Mostramos el arreglo ordenado
        System.out.println("Arreglo ordenado:");
        for (int i = 0; i < datos.length; i++) {
            System.out.print(datos[i] + " ");
        }
        System.out.println();
    }
}
Salida:
text
Arreglo original:
38 27 43 3 9 82 10
Arreglo ordenado:
3 9 10 27 38 43 82
Explicación línea por línea:
Línea | Código | Explicación
6 | static void mergeSort(...) | Método recursivo principal.
9 | if (inicio >= fin) | Caso base: 0 o 1 elemento.
14 | int medio = (inicio + fin) / 2; | Punto medio.
17 | mergeSort(arr, inicio, medio); | Ordena mitad izquierda.
20 | mergeSort(arr, medio + 1, fin); | Ordena mitad derecha.
23 | merge(arr, inicio, medio, fin); | Intercala las mitades.
30-31 | int n1 = ...; int n2 = ...; | Tamaños de las mitades.
34-35 | int[] izq = ...; int[] der = ...; | Arreglos temporales.
38-43 | Bucles de copia | Copian datos a los temporales.
46-48 | int i = 0; int j = 0; int k = inicio; | Índices.
51-59 | Bucle de intercalación | Compara y coloca el menor.
62-66 | Bucle de sobrantes izq | Copia lo que quede.
69-73 | Bucle de sobrantes der | Copia lo que quede.
5.4. Ejercicios del tema
Ejercicio 3.1 (Aplicación de procedimiento):Aplica merge sort manualmente al arreglo: [5, 2, 9, 1, 7, 6, 4, 8]. Dibuja cada paso de división y combinación.
Ejercicio 3.2 (Análisis de escenario):¿Qué sucede con la eficiencia de merge sort si el arreglo ya está completamente ordenado? ¿Se comporta igual o mejor? Explica.
Ejercicio 3.3 (Elaboración de código):Modifica la implementación de merge sort para que ordene en orden descendente.
6. TEMA 4: SORTMERGE 2 (MERGE SORT ITERATIVO O BOTTOM-UP)
6.1. ¿Qué es Sortmerge 2?
Sortmerge 2 (también conocido como Merge Sort Optimizado o Bottom-up Merge Sort) es una variante iterativa (no recursiva) del algoritmo merge sort. En lugar de dividir recursivamente, comienza considerando subarreglos de tamaño 1 y los va fusionando en tamaños progresivamente mayores: 1, 2, 4, 8, ..., hasta n.
Traducción:
Bottom-up = De abajo hacia arriba
Top-down = De arriba hacia abajo
Iterative = Iterativo
Analogía: En lugar de dividir un problema grande en partes pequeñas (top-down), empiezas con las partes más pequeñas y las vas uniendo (bottom-up). Es como construir un muro: primero colocas un ladrillo, luego dos, luego cuatro, etc.
6.2. Diferencias con Sortmerge recursivo
Aspecto | Sortmerge recursivo (top-down) | Sortmerge 2 iterativo (bottom-up)
Enfoque | Divide primero hasta tamaño 1, luego combina | Combina desde tamaño 1 hacia arriba
Implementación | Usa recursión (pila de llamadas) | Usa bucles anidados
Memoria auxiliar | O(n) + O(log n) por pila | O(n) sin pila de recursión
Complejidad temporal | O(n log n) | O(n log n)
Riesgo de desbordamiento de pila | Sí para n muy grande | No
6.3. Implementación iterativa en Java
// Clase principal para Merge Sort iterativo (Bottom-up)
public class MergeSortIterativo {
    // Método iterativo de Merge Sort
    static void mergeSortIterativo(int[] arr) {
        int n = arr.length;
        // Tamaño actual de los subarreglos a fusionar
        int tamaño = 1;
        // Mientras el tamaño sea menor que n, seguimos fusionando
        while (tamaño < n) {
            // Recorremos el arreglo con saltos de 2*tamaño
            for (int inicio = 0; inicio < n; inicio += 2 * tamaño) {
                // Último índice del primer subarreglo
                int medio = Math.min(inicio + tamaño - 1, n - 1);
                // Último índice del segundo subarreglo (sin salirse)
                int fin = Math.min(inicio + 2 * tamaño - 1, n - 1);
                // Solo fusionar si el segundo subarreglo no está vacío
                if (medio < fin) {
                    merge(arr, inicio, medio, fin);
                }
            }
            // Duplicamos el tamaño para la siguiente pasada
            tamaño *= 2;
        }
    }
    // Método merge (mismo que en Merge Sort recursivo)
    static void merge(int[] arr, int inicio, int medio, int fin) {
        int n1 = medio - inicio + 1;
        int n2 = fin - medio;
        int[] izq = new int[n1];
        int[] der = new int[n2];
        for (int i = 0; i < n1; i++) {
            izq[i] = arr[inicio + i];
        }
        for (int j = 0; j < n2; j++) {
            der[j] = arr[medio + 1 + j];
        }
        int i = 0, j = 0, k = inicio;
        while (i < n1 && j < n2) {
            if (izq[i] <= der[j]) {
                arr[k] = izq[i];
                i++;
            } else {
                arr[k] = der[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = izq[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = der[j];
            j++;
            k++;
        }
    }
    // Método principal
    public static void main(String[] args) {
        // Arreglo de ejemplo
        int[] datos = {6, 5, 3, 1, 8, 7, 2, 4};
        // Mostramos el arreglo original
        System.out.println("Arreglo original:");
        for (int i = 0; i < datos.length; i++) {
            System.out.print(datos[i] + " ");
        }
        System.out.println();
        // Llamamos a Merge Sort iterativo
        mergeSortIterativo(datos);
        // Mostramos el arreglo ordenado
        System.out.println("Arreglo ordenado (bottom-up):");
        for (int i = 0; i < datos.length; i++) {
            System.out.print(datos[i] + " ");
        }
        System.out.println();
    }
}
Salida:
text
Arreglo original:
6 5 3 1 8 7 2 4
Arreglo ordenado (bottom-up):
1 2 3 4 5 6 7 8
Explicación del algoritmo:
Pasada 1 (tamaño=1): Fusiona pares: [6,5]→[5,6], [3,1]→[1,3], [8,7]→[7,8], [2,4]→[2,4]. Resultado: [5,6,1,3,7,8,2,4].
Pasada 2 (tamaño=2): Fusiona bloques de 2: [5,6] y [1,3] → [1,3,5,6]; [7,8] y [2,4] → [2,4,7,8]. Resultado: [1,3,5,6,2,4,7,8].
Pasada 3 (tamaño=4): Fusiona bloques de 4: [1,3,5,6] y [2,4,7,8] → [1,2,3,4,5,6,7,8]. Termina.
6.4. Ejercicios del tema
Ejercicio 4.1 (Aplicación de procedimiento):Simula manualmente el proceso de Sortmerge 2 para el arreglo: [9, 4, 7, 2, 5, 1, 8, 3, 6]. Indica cada pasada y el resultado intermedio.
Ejercicio 4.2 (Análisis de escenario):¿Qué ocurre si n no es potencia de 2 en Sortmerge 2? Explica cómo maneja el algoritmo los elementos sobrantes.
Ejercicio 4.3 (Elaboración de código):Modifica la implementación de mergeSortIterativo para que cuente el número total de comparaciones realizadas y lo retorne.
7. TEMA 5: QUICKSORT (ORDENAMIENTO RÁPIDO)
7.1. Concepto fundamental
Quicksort es un algoritmo de ordenamiento divide y conquista. A diferencia de merge sort que divide el arreglo por la mitad, Quicksort divide el arreglo basado en un pivote: todos los elementos menores que el pivote van a la izquierda, los mayores a la derecha, y luego se ordena recursivamente cada partición.
Analogía: Imagina que organizas una fila de personas por estatura. Eliges a una persona como referencia (pivote). Todas las más bajas se van a la izquierda, las más altas a la derecha. Luego repites el proceso en cada grupo.
Traducción:
Quicksort = Ordenamiento rápido
Pivot = Pivote
Partition = Partición
Lomuto = Método de partición con último elemento como pivote
7.2. Complejidad
Caso | Complejidad | Cuándo ocurre
Mejor | O(n log n) | El pivote divide en mitades iguales
Peor | O(n²) | El pivote es siempre el mínimo o máximo
Promedio | O(n log n) | Con pivote aleatorio o mediana de tres
7.3. Partición de Lomuto
El método de Lomuto elige el último elemento como pivote y reorganiza el arreglo de modo que:
Todos los elementos ≤ pivote quedan a la izquierda.
Todos los elementos > pivote quedan a la derecha.
El pivote queda en su posición final.
Diagrama:
text
Arreglo: [10, 7, 8, 9, 1, 5, 2, 4]  pivote = 4
i = -1
j=0: 10 > 4, no se hace nada
j=1: 7 > 4, no se hace nada
j=2: 8 > 4, no se hace nada
j=3: 9 > 4, no se hace nada
j=4: 1 <= 4, i=0, swap arr[0] y arr[4] -> [1, 7, 8, 9, 10, 5, 2, 4]
j=5: 5 > 4, no se hace nada
j=6: 2 <= 4, i=1, swap arr[1] y arr[6] -> [1, 2, 8, 9, 10, 5, 7, 4]
Final: swap arr[i+1] y arr[alto] -> [1, 2, 4, 9, 10, 5, 7, 8]
Pivote en posición 2
7.4. Mediana de tres
La mediana de tres es una técnica para elegir un pivote más robusto: toma el primer, medio y último elemento, los ordena, y usa la mediana como pivote. Esto reduce la probabilidad del peor caso O(n²).
7.5. Implementación en Java
// Clase principal para Quicksort
public class Quicksort {
    // Método recursivo de Quicksort
    static void quicksort(int[] arr, int bajo, int alto) {
        // Solo continuar si el subarreglo tiene al menos dos elementos
        if (bajo < alto) {
            // Obtenemos el índice del pivote después de particionar
            int indicePivote = particionLomuto(arr, bajo, alto);
            // Ordenamos recursivamente la parte izquierda
            quicksort(arr, bajo, indicePivote - 1);
            // Ordenamos recursivamente la parte derecha
            quicksort(arr, indicePivote + 1, alto);
        }
    }
    // Método de partición de Lomuto
    static int particionLomuto(int[] arr, int bajo, int alto) {
        // Seleccionamos el pivote (último elemento)
        int pivote = arr[alto];
        // i delimita la zona de elementos <= pivote
        int i = bajo - 1;
        // Recorremos desde bajo hasta alto-1
        for (int j = bajo; j < alto; j++) {
            // Si el elemento actual es menor o igual al pivote
            if (arr[j] <= pivote) {
                i++;
                // Intercambiamos arr[i] y arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // Colocamos el pivote en su posición correcta
        int temp = arr[i + 1];
        arr[i + 1] = arr[alto];
        arr[alto] = temp;
        // Retornamos la nueva posición del pivote
        return i + 1;
    }
    // Método principal
    public static void main(String[] args) {
        // Arreglo de ejemplo
        int[] datos = {3, 8, 2, 5, 1, 4, 7, 6};
        // Mostramos el arreglo original
        System.out.println("Arreglo original:");
        for (int i = 0; i < datos.length; i++) {
            System.out.print(datos[i] + " ");
        }
        System.out.println();
        // Llamamos a Quicksort
        quicksort(datos, 0, datos.length - 1);
        // Mostramos el arreglo ordenado
        System.out.println("Arreglo ordenado con Quicksort:");
        for (int i = 0; i < datos.length; i++) {
            System.out.print(datos[i] + " ");
        }
        System.out.println();
    }
}
Salida:
text
Arreglo original:
3 8 2 5 1 4 7 6
Arreglo ordenado con Quicksort:
1 2 3 4 5 6 7 8
Explicación línea por línea:
Línea | Código | Explicación
6 | static void quicksort(...) | Método recursivo.
9 | if (bajo < alto) | Caso base: subarreglo de 0 o 1 elemento.
12 | int indicePivote = particionLomuto(arr, bajo, alto); | Particiona y obtiene posición del pivote.
15 | quicksort(arr, bajo, indicePivote - 1); | Ordena la parte izquierda.
18 | quicksort(arr, indicePivote + 1, alto); | Ordena la parte derecha.
25 | int pivote = arr[alto]; | El pivote es el último elemento.
28 | int i = bajo - 1; | i empieza antes del primer elemento.
31 | for (int j = bajo; j < alto; j++) | Recorre todos los elementos excepto el pivote.
34 | if (arr[j] <= pivote) | Si el elemento es menor o igual al pivote.
35-40 | i++; swap... | Incrementa i e intercambia.
44-47 | swap arr[i+1] y arr[alto] | Coloca el pivote en su lugar.
50 | return i + 1; | Devuelve la posición del pivote.
7.6. Ejercicios del tema
Ejercicio 5.1 (Aplicación de procedimiento):Aplica el algoritmo de partición Lomuto manualmente al arreglo: [10, 7, 8, 9, 1, 5, 2, 4] usando como pivote el último elemento. Muestra el estado del arreglo después de cada intercambio.
Ejercicio 5.2 (Análisis de escenario):¿Por qué el peor caso de Quicksort es O(n²)? Da un ejemplo concreto de arreglo que produce ese comportamiento si siempre se elige el último elemento como pivote.
Ejercicio 5.3 (Elaboración de código):Implementa una variante de Quicksort que use el método de partición Hoare (investiga su pseudocódigo) y compara su eficiencia con Lomuto para arreglos de tamaño 10,000 generados aleatoriamente.
4. CASOS PRÁCTICOS INTEGRADORES
4.1. Caso 1: Sistema de búsqueda en una guía telefónica
Situación:Una empresa de telecomunicaciones mantiene una guía telefónica con 500,000 nombres y números de teléfono, ordenada alfabéticamente por apellido. Reciben 10,000 consultas por segundo. Actualmente usan búsqueda secuencial (O(n)), lo que es insostenible.
Acciones realizadas:
Implementación de búsqueda binaria (O(log n)) sobre la guía ordenada.
Para mantener el orden al agregar nuevos contactos, usaron Merge Sort (O(n log n)) nocturno para reordenar la guía completa.
Durante el día, las inserciones se almacenan en una lista temporal; cada noche se fusionan con la guía principal mediante intercalación (merge).
Hallazgos:
La búsqueda binaria redujo el tiempo de respuesta de 250 ms a menos de 0.02 ms por consulta.
El merge sorting nocturno de 500,000 registros toma ~0.5 segundos.
El sistema maneja ahora 10,000 consultas/segundo sin saturarse.
Recomendaciones:
Implementar árboles binarios de búsqueda para inserciones en tiempo real.
Usar Quicksort en lugar de Merge Sort si la memoria es limitada.
Resultados:La empresa mejoró su SLA de 500 ms a 10 ms.
Preguntas de análisis:
¿Por qué la búsqueda binaria no funcionaría si la guía no estuviera ordenada?
¿Cuál es la complejidad de insertar un nuevo contacto en medio de un arreglo ordenado? ¿Cómo lo resolvieron?
¿Qué ventaja tendría usar un árbol binario de búsqueda en lugar del enfoque nocturno?
Código completo comentado (simulación simplificada):
// Clase principal para simular el sistema de búsqueda telefónica
public class GuiaTelefonica {
    // Método de búsqueda binaria recursiva
    static int busquedaBinaria(String[] guia, int inicio, int fin, String objetivo) {
        // Caso base: rango vacío
        if (inicio > fin) {
            return -1;
        }
        // Punto medio
        int medio = (inicio + fin) / 2;
        // Comparamos cadenas con compareTo
        int comparacion = guia[medio].compareTo(objetivo);
        // Si son iguales, encontramos el contacto
        if (comparacion == 0) {
            return medio;
        }
        // Si el objetivo es menor alfabéticamente, buscamos a la izquierda
        if (comparacion > 0) {
            return busquedaBinaria(guia, inicio, medio - 1, objetivo);
        }
        // Si es mayor, buscamos a la derecha
        return busquedaBinaria(guia, medio + 1, fin, objetivo);
    }
    // Método principal
    public static void main(String[] args) {
        // Guía telefónica simulada (ordenada alfabéticamente)
        String[] guia = {
            "Alvarez, Juan: 0414-1111111",
            "Blanco, Maria: 0414-2222222",
            "Castillo, Pedro: 0414-3333333",
            "Diaz, Ana: 0414-4444444",
            "Estevez, Luis: 0414-5555555",
            "Fernandez, Carla: 0414-6666666",
            "Garcia, Jose: 0414-7777777",
            "Hernandez, Lucía: 0414-8888888",
            "Iglesias, Mario: 0414-9999999",
            "Jimenez, Sofia: 0414-0000000"
        };
        // Contacto a buscar
        String objetivo = "Garcia, Jose: 0414-7777777";
        // Llamada a búsqueda binaria
        int indice = busquedaBinaria(guia, 0, guia.length - 1, objetivo);
        // Mostramos el resultado
        if (indice != -1) {
            System.out.println("Contacto encontrado en indice " + indice + ":");
            System.out.println(guia[indice]);
        } else {
            System.out.println("Contacto no encontrado.");
        }
        // Simulamos una búsqueda fallida
        String objetivo2 = "Perez, Juan: 0414-1234567";
        int indice2 = busquedaBinaria(guia, 0, guia.length - 1, objetivo2);
        if (indice2 != -1) {
            System.out.println("Contacto encontrado: " + guia[indice2]);
        } else {
            System.out.println("Contacto '" + objetivo2 + "' no encontrado.");
        }
    }
}
Salida:
text
Contacto encontrado en indice 6:
Garcia, Jose: 0414-7777777
Contacto 'Perez, Juan: 0414-1234567' no encontrado.
4.2. Caso 2: Optimización de un sistema de pedidos de comercio electrónico
Situación:Una tienda en línea necesita encontrar el pedido con el monto mínimo y máximo del día, ordenar los pedidos por hora y buscar un pedido por ID.
Acciones realizadas:
Para encontrar el monto mínimo y máximo de 100,000 pedidos, se implementó el algoritmo de máximos y mínimos por divide y conquista.
Para ordenar por hora, se evaluó Quicksort vs Sortmerge 2. Como el orden relativo entre pedidos de la misma hora era irrelevante, se eligió Quicksort con pivote aleatorio.
Para búsqueda por ID, se aplicó búsqueda binaria sobre un arreglo auxiliar de IDs ordenados (actualizado cada 5 minutos mediante Sortmerge 2).
Hallazgos:
Quicksort ordena 100,000 pedidos en 0.03 segundos (promedio).
La búsqueda binaria encuentra un pedido en ~17 comparaciones.
El algoritmo de máximos y mínimos por D&C mejoró la constante en ~15%.
Recomendaciones:
Para volúmenes pequeños (<1000 elementos), usar algoritmo lineal simple.
Implementar índices en la base de datos para búsqueda por ID.
Resultados:El tiempo del reporte diario se redujo de 45 segundos a 1.2 segundos.
Preguntas de análisis:
¿Por qué el algoritmo de máximos y mínimos por D&C no dio una mejora espectacular?
¿En qué situación priorizarías Merge Sort sobre Quicksort aunque sea más lento en promedio?
Propón una estrategia para buscar un pedido por ID sin reordenar todo el arreglo cada 5 minutos.
Código completo comentado (simulación):
// Clase principal para simular el sistema de pedidos
public class SistemaPedidos {
    // Método de máximos y mínimos por divide y conquista
    static int[] maxMinDYC(int[] montos, int inicio, int fin) {
        // Caso base: un elemento
        if (inicio == fin) {
            return new int[]{montos[inicio], montos[inicio]};
        }
        // Caso base: dos elementos
        if (inicio + 1 == fin) {
            if (montos[inicio] > montos[fin]) {
                return new int[]{montos[inicio], montos[fin]};
            } else {
                return new int[]{montos[fin], montos[inicio]};
            }
        }
        // Dividir
        int medio = (inicio + fin) / 2;
        // Conquistar
        int[] izq = maxMinDYC(montos, inicio, medio);
        int[] der = maxMinDYC(montos, medio + 1, fin);
        // Combinar
        int max = Math.max(izq[0], der[0]);
        int min = Math.min(izq[1], der[1]);
        return new int[]{max, min};
    }
    // Método Quicksort con partición Lomuto
    static void quicksort(int[] arr, int bajo, int alto) {
        if (bajo < alto) {
            int p = particion(arr, bajo, alto);
            quicksort(arr, bajo, p - 1);
            quicksort(arr, p + 1, alto);
        }
    }
    // Partición de Lomuto
    static int particion(int[] arr, int bajo, int alto) {
        int pivote = arr[alto];
        int i = bajo - 1;
        for (int j = bajo; j < alto; j++) {
            if (arr[j] <= pivote) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[alto];
        arr[alto] = temp;
        return i + 1;
    }
    // Búsqueda binaria iterativa
    static int busquedaBinaria(int[] arr, int objetivo) {
        int inicio = 0;
        int fin = arr.length - 1;
        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            if (arr[medio] == objetivo) {
                return medio;
            } else if (objetivo < arr[medio]) {
                fin = medio - 1;
            } else {
                inicio = medio + 1;
            }
        }
        return -1;
    }
    // Método principal
    public static void main(String[] args) {
        // Montos de pedidos simulados
        int[] montos = {250, 120, 890, 45, 670, 310, 150, 980, 60, 430};
        // Encontrar máximo y mínimo por D&C
        int[] resultado = maxMinDYC(montos, 0, montos.length - 1);
        System.out.println("Monto maximo: " + resultado[0]);
        System.out.println("Monto minimo: " + resultado[1]);
        // Ordenar los montos con Quicksort
        quicksort(montos, 0, montos.length - 1);
        System.out.println("Montos ordenados:");
        for (int i = 0; i < montos.length; i++) {
            System.out.print(montos[i] + " ");
        }
        System.out.println();
        // Buscar un monto específico
        int objetivo = 670;
        int indice = busquedaBinaria(montos, objetivo);
        if (indice != -1) {
            System.out.println("Monto " + objetivo + " encontrado en indice " + indice);
        } else {
            System.out.println("Monto " + objetivo + " no encontrado.");
        }
    }
}
Salida:
text
Monto maximo: 980
Monto minimo: 45
Montos ordenados:
45 60 120 150 250 310 430 670 890 980
Monto 670 encontrado en indice 7
5. EJERCICIOS PRÁCTICOS
5.1. Ejercicios resueltos paso a paso
Ejercicio resuelto 1: Búsqueda binaria en orden descendente
Enunciado: Adaptar la búsqueda binaria para un arreglo ordenado descendentemente [20, 15, 10, 5, 0] y buscar el valor 10.
Solución paso a paso:
En un arreglo descendente, los elementos mayores están a la izquierda.
Si el objetivo es mayor que el elemento medio, buscamos en la izquierda.
Si el objetivo es menor, buscamos en la derecha.
Código Java:
// Clase para búsqueda binaria descendente
public class BusquedaBinariaDesc {
    // Método de búsqueda binaria para arreglo descendente
    static int busquedaBinariaDesc(int[] arr, int objetivo) {
        int inicio = 0;
        int fin = arr.length - 1;
        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            if (arr[medio] == objetivo) {
                return medio;
            } else if (objetivo > arr[medio]) {
                // En orden descendente, los mayores están a la izquierda
                fin = medio - 1;
            } else {
                inicio = medio + 1;
            }
        }
        return -1;
    }
    // Método principal
    public static void main(String[] args) {
        int[] datos = {20, 15, 10, 5, 0};
        int objetivo = 10;
        int indice = busquedaBinariaDesc(datos, objetivo);
        System.out.println("Indice de " + objetivo + ": " + indice);
    }
}
Salida:
text
Indice de 10: 2
Ejercicio resuelto 2: Peor caso de Quicksort
Enunciado: Demostrar que Quicksort con pivote = último elemento tiene complejidad O(n²) para un arreglo ya ordenado ascendentemente [1, 2, 3, 4, 5, 6, 7, 8].
Solución paso a paso:
Primera partición: pivote = 8. Todos los elementos son menores, así que la partición deja 7 elementos a la izquierda y 0 a la derecha.
Segunda partición: pivote = 7. Quedan 6 a la izquierda.
Tercera partición: pivote = 6. Quedan 5 a la izquierda.
...
Total de comparaciones: n + (n-1) + (n-2) + ... + 1 = n(n+1)/2 = O(n²).
Código Java que lo evidencia:
// Clase para demostrar el peor caso de Quicksort
public class PeorCasoQuicksort {
    // Contador de comparaciones
    static int comparaciones = 0;
    // Quicksort con partición Lomuto
    static void quicksort(int[] arr, int bajo, int alto) {
        if (bajo < alto) {
            int p = particion(arr, bajo, alto);
            quicksort(arr, bajo, p - 1);
            quicksort(arr, p + 1, alto);
        }
    }
    // Partición de Lomuto
    static int particion(int[] arr, int bajo, int alto) {
        int pivote = arr[alto];
        int i = bajo - 1;
        for (int j = bajo; j < alto; j++) {
            comparaciones++; // Contamos cada comparación
            if (arr[j] <= pivote) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[alto];
        arr[alto] = temp;
        return i + 1;
    }
    // Método principal
    public static void main(String[] args) {
        int[] datos = {1, 2, 3, 4, 5, 6, 7, 8};
        quicksort(datos, 0, datos.length - 1);
        System.out.println("Comparaciones en peor caso: " + comparaciones);
        System.out.println("n(n-1)/2 = " + (8 * 7 / 2));
    }
}
Salida:
text
Comparaciones en peor caso: 28
n(n-1)/2 = 28
5.2. Ejercicios propuestos con guías de solución
Ejercicio propuesto 1 (Básico): Búsqueda binaria iterativa
Enunciado: Implementa en Java una búsqueda binaria iterativa que busque un número en un arreglo ordenado de 20 elementos. Muestra el índice o -1.
Guía de solución:
Usa un bucle while (inicio <= fin).
Calcula medio = (inicio + fin) / 2.
Compara arr[medio] con el objetivo.
Ajusta inicio o fin según corresponda.
Ejercicio propuesto 2 (Básico): Máximos y mínimos lineal
Enunciado: Encuentra el máximo y mínimo de un arreglo de 15 elementos usando un solo recorrido (sin divide y conquista).
Guía de solución:
Inicializa max y min con el primer elemento.
Recorre desde el segundo elemento.
Si arr[i] > max, actualiza max.
Si arr[i] < min, actualiza min.
Compara con el resultado de D&C.
Ejercicio propuesto 3 (Intermedio): Merge Sort descendente
Enunciado: Modifica Merge Sort para ordenar en orden descendente.
Guía de solución:
En el método merge, cambia la condición izq[i] <= der[j] por izq[i] >= der[j].
El resto del algoritmo permanece igual.
Ejercicio propuesto 4 (Intermedio): Sortmerge 2 con contador
Enunciado: Modifica Merge Sort iterativo para contar el número total de comparaciones y retornarlo.
Guía de solución:
Usa una variable estática contador.
Incrementa contador cada vez que compares izq[i] con der[j].
Al final, imprime el valor.
Ejercicio propuesto 5 (Avanzado): Quicksort con mediana de tres
Enunciado: Implementa Quicksort con la técnica de mediana de tres para elegir el pivote.
Guía de solución:
Calcula medio = (bajo + alto) / 2.
Ordena arr[bajo], arr[medio], arr[alto] de menor a mayor.
Coloca la mediana en arr[alto].
Llama a la partición Lomuto normal.
Ejercicio propuesto 6 (Avanzado): Comparación de algoritmos
Enunciado: Genera arreglos aleatorios de tamaños 100, 1,000, 10,000 y 100,000. Mide el tiempo de ejecución de Merge Sort, Merge Sort iterativo y Quicksort. Elabora una tabla comparativa.
Guía de solución:
Usa System.nanoTime() para medir tiempos.
Genera arreglos con Math.random().
Ejecuta cada algoritmo en arreglos copia para no afectar los datos.
Compara y concluye.
Ejercicio propuesto 7 (Avanzado): Detección de anomalías
Enunciado: Dado un arreglo de 1,000,000 de temperaturas, encuentra máximo y mínimo, elimina esos extremos, ordena el resto y busca una temperatura específica.
Guía de solución:
Usa máximos y mínimos por D&C.
Filtra los valores extremos en un nuevo arreglo.
Aplica Merge Sort o Quicksort.
Usa búsqueda binaria para encontrar la temperatura.
GLOSARIO
Término (Español) | Término (Inglés) | Definición
Algoritmo | Algorithm | Conjunto ordenado de pasos para resolver un problema.
Arreglo | Array | Estructura de datos que almacena elementos del mismo tipo en posiciones contiguas.
Big O | Big O | Notación que describe el crecimiento asintótico de un algoritmo.
Búsqueda binaria | Binary search | Algoritmo de búsqueda en O(log n) sobre arreglos ordenados.
Caso base | Base case | Condición que detiene la recursión.
Combinar | Combine | Tercer paso de divide y conquista: unir soluciones.
Complejidad temporal | Time complexity | Medida del tiempo de ejecución en función del tamaño de entrada.
Conquistar | Conquer | Segundo paso de divide y conquista: resolver subproblemas.
Divide y conquista | Divide and conquer | Estrategia que divide un problema en subproblemas independientes.
Dividir | Divide | Primer paso de divide y conquista: descomponer el problema.
Estabilidad | Stability | Propiedad de un ordenamiento que preserva el orden relativo de elementos iguales.
Intercalación | Merge | Proceso de combinar dos secuencias ordenadas en una sola.
Iterativo | Iterative | Que usa bucles en lugar de recursión.
Mediana de tres | Median of three | Técnica para elegir un pivote robusto en Quicksort.
Merge Sort | Merge Sort | Algoritmo de ordenamiento O(n log n) basado en intercalación.
Partición | Partition | Reorganización de un arreglo alrededor de un pivote.
Pivote | Pivot | Elemento de referencia en Quicksort.
Quicksort | Quicksort | Algoritmo de ordenamiento rápido con complejidad promedio O(n log n).
Recursividad | Recursion | Técnica donde una función se llama a sí misma.
Sortmerge 2 | Bottom-up Merge Sort | Variante iterativa de Merge Sort.
Subproblema | Subproblem | Problema más pequeño generado al dividir.
REFERENCIAS BIBLIOGRÁFICAS
Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2009). Introduction to algorithms (3rd ed.). MIT Press.
Joyanes Aguilar, L. (2007). Estructuras de datos en Java. McGraw-Hill.
Knuth, D. E. (1998). The art of computer programming, Vol. 3: Sorting and searching (2nd ed.). Addison-Wesley.
Sedgewick, R., & Wayne, K. (2011). Algorithms (4th ed.). Addison-Wesley.
Oracle. (2024). Java SE Documentation. https://docs.oracle.com/en/java/
GeeksforGeeks. (2024). Divide and Conquer Algorithm. https://www.geeksforgeeks.org/divide-and-conquer/
```
