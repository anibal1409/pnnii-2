# Unidad III: Método de adquisición (estrategia voraz)

> Texto extraído de `Guia UNIDAD III MÉTODO DE ADQUISICIÓN.docx` (fuente original en esta carpeta). El material trae el código en Python; los programas de `consola/unidad3/` son su versión en Java. Las erratas detectadas están en `unidad-3-erratas.md`.

```text
ÍNDICE
1. Introducción al Método de Adquisición (Estrategia Voraz)   1.1. Definición y origen del término “Adquisición” (greedy)   1.2. Características fundamentales de los algoritmos voraces   1.3. Condiciones necesarias para su aplicación: subestructura óptima y propiedad de elección voraz   1.4. Comparación con otras técnicas (backtracking, programación dinámica)   1.5. Analogía cotidiana: “El chef que prepara el buffet más variado con poco tiempo”
2. Almacenamiento Óptimo en Cintas Magnéticas   2.1. Contexto histórico y problema real   2.2. Definición del problema: minimizar el tiempo total de recuperación de archivos   2.3. Demostración de la optimalidad del ordenamiento por longitud creciente (algoritmo de ordenación)   2.4. Implementación en Python con código comentado   2.5. Complejidad temporal y espacial   2.6. Analogía: “Organizar los platos en un lavavajillas industrial”
3. El Problema de la Bolsa de Papel (Problema de la Mochila Fraccionaria)   3.1. Origen del nombre y variantes (0/1 vs fraccionaria)   3.2. Definición formal: maximizar valor con capacidad limitada, permitiendo fracciones   3.3. Demostración de la estrategia voraz por ratio valor/peso   3.4. Implementación en Python con ordenamiento personalizado   3.5. Comparación con la mochila 0/1 (no voraz)   3.6. Analogía: “Llenar una maleta de mano con objetos preciosos”
4. Modelos Óptimos de Intercalación   4.1. El problema de la fusión de archivos (también conocido como “costo de fusión mínimo”)   4.2. Relación con el algoritmo de Huffman para codificación óptima   4.3. Definición: minimizar el costo total de intercalar secuencias ordenadas   4.4. Estrategia voraz: siempre fusionar las dos más pequeñas (cola de prioridad)   4.5. Implementación en Python usando heap (priority queue)   4.6. Complejidad y demostración de optimalidad   4.7. Analogía: “Combinar montones de hojas de cálculo en una oficina”
5. Luces de Árbol de Costo Mínimo (Árbol de Expansión Mínima – Minimum Spanning Tree, MST)   5.1. ¿Por qué “Luces”?: explicación del término y su origen (iluminación de ciudades)   5.2. Definición del problema: conectar todos los nodos de una red con el menor costo total   5.3. Algoritmo de Prim (estrategia voraz de crecimiento de árbol)   5.4. Algoritmo de Kruskal (ordenación de aristas y uso de unión-búsqueda)   5.5. Implementación en Python de ambos algoritmos   5.6. Comparación de complejidades y elección según densidad del grafo   5.7. Analogía: “Tender cables de internet en un pueblo” y “Construcción de carreteras interestatales”
6. Paso Más Corto desde un Inicio Único (Algoritmo de Dijkstra)   6.1. Definición: camino de costo mínimo desde un nodo origen a todos los demás en un grafo con pesos no negativos   6.2. Condición de voracidad: siempre seleccionar el nodo no visitado con distancia mínima   6.3. Demostración de optimalidad y límites (grafos con pesos negativos -> Bellman-Ford)   6.4. Implementación en Python con heap (cola de prioridad)   6.5. Variantes: matriz de adyacencia vs lista de adyacencia   6.6. Analogía: “Navegación GPS para llegar al trabajo” y “Reparto de pizza a múltiples casas”
7. Casos Prácticos Integradores   7.1. Caso 1: Optimización de procesos en un centro de datos (mezcla de almacenamiento en cintas y fusión de registros)   7.2. Caso 2: Planificación de redes de transporte público (árbol de costo mínimo + caminos más cortos)
8. Ejercicios Prácticos   8.1. Ejercicios resueltos paso a paso (2 ejercicios)   8.2. Ejercicios propuestos con distintos niveles (5 ejercicios)   8.3. Guías de solución para los propuestos (pistas y respuestas)
9. Glosario de Términos y Acrónimos (más de 20 términos)
10. Referencias Bibliográficas (Formato APA 7ª edición)
1. INTRODUCCIÓN AL MÉTODO DE ADQUISICIÓN (ESTRATEGIA VORAZ)
1.1. Definición y origen del término “Adquisición” (greedy)
Método de Adquisición, también conocido como algoritmo voraz (del inglés greedy), es una técnica de resolución de problemas de optimización que toma decisiones paso a paso, seleccionando en cada etapa la opción que parece más prometedora en el momento, sin mirar hacia atrás ni reconsiderar elecciones previas. El nombre “adquisición” proviene de la idea de que el algoritmo va “adquiriendo” la mejor pieza disponible en cada paso, como un comprador que elige el producto de mejor relación calidad-precio inmediata.
Analogía de la vida diaria: Imagina que tienes que cambiar una rueda pinchada. El método voraz sería: “saca el gato, levanta el coche, quita los tornillos, cambia la rueda”. Nunca reconsideras si era mejor levantar primero el coche después de quitar los tornillos; sigues una secuencia que parece óptima localmente.
1.2. Características fundamentales de los algoritmos voraces
Característica | Explicación
Decisiones irreversibles | Una vez elegida una opción, no se deshace.
Elección localmente óptima | En cada paso se escoge la alternativa que maximiza o minimiza una función de costo/beneficio inmediato.
Construcción iterativa | Se parte de una solución vacía y se agregan componentes.
Verificación de factibilidad | Cada elección debe mantener la posibilidad de alcanzar una solución completa.
No garantiza la solución global en todos los problemas | Solo funciona para problemas con subestructura óptima y propiedad de elección voraz.
1.3. Condiciones necesarias para su aplicación
Para que un problema pueda resolverse correctamente con un algoritmo voraz, debe cumplir dos propiedades fundamentales:
a) Propiedad de elección voraz (greedy choice property)
Una elección localmente óptima a nivel global nunca impide encontrar la solución óptima global.
Significa que siempre existe una solución óptima que comienza con la elección voraz. Por ejemplo, en el problema de dar cambio con monedas de curso legal (1, 5, 10, 25 céntimos), tomar siempre la moneda de mayor valor que no exceda la cantidad restante lleva al número mínimo de monedas.
b) Subestructura óptima (optimal substructure)
Una solución óptima global contiene dentro de ella soluciones óptimas para los subproblemas.
Si resolvemos el problema paso a paso, el estado resultante después de una elección voraz sigue siendo un problema del mismo tipo, y la solución óptima de ese subproblema combinada con la elección inicial produce la solución óptima global.
Ejemplo concreto (cambio de monedas):Para devolver 60 céntimos, la elección voraz es 50 céntimos (moneda máxima ≤ 60). El subproblema es devolver 10 céntimos, cuya solución óptima es una moneda de 10. Solución global: 1 moneda de 50 + 1 de 10 = 2 monedas (óptimo).
1.4. Comparación con otras técnicas
Técnica | Enfoque | Pros | Contras
Voraz (greedy) | Decisiones locales sin retroceso | Muy rápida (polinomial, generalmente O(n log n) o menos) | No siempre da óptimo global
Programación dinámica | Considera todas las combinaciones mediante tablas, resuelve subproblemas una sola vez | Garantiza óptimo global | Mayor consumo de memoria y tiempo (polinomial pero de mayor orden)
Backtracking (vuelta atrás) | Explora todo el espacio de búsqueda, deshaciendo decisiones | Encuentra la solución exacta (si existe) | Exponencial en el peor caso
Ramificación y poda (branch & bound) | Similar a backtracking pero con estimaciones para podar | Más eficiente que backtracking puro | Sigue siendo exponencial en el peor caso
Importante: El método de adquisición es el más eficiente en tiempo, pero solo aplica cuando se cumplen las dos condiciones anteriores. Muchos problemas clásicos (como la mochila 0/1) no se resuelven con voraz óptimo, mientras que la mochila fraccionaria sí.
1.5. Analogía cotidiana: “El chef que prepara el buffet más variado con poco tiempo”
Un chef debe llenar una bandeja con porciones de 10 platos diferentes para un buffet. Dispone de 2 minutos para servir. Cada plato tarda un tiempo diferente en servirse (ej: ensalada 5s, sopa 20s, carne 45s). El chef quiere maximizar la variedad (número de platos distintos) en la bandeja. La estrategia voraz es: servir primero los platos que tardan menos (para poder incluir más). Eso coincide con la solución óptima: orden ascendente por tiempo de servicio. Si sirve primero la carne (45s), solo alcanzará para 2 o 3 platos. Con la estrategia voraz obtendrá hasta 8 platos. ¡Eso es el método de adquisición!
2. ALMACENAMIENTO ÓPTIMO EN CINTAS MAGNÉTICAS
2.1. Contexto histórico y problema real
En la década de 1960-1980, las cintas magnéticas eran el medio principal para almacenar grandes volúmenes de datos (backups, archivos de mainframes). Una cinta es un dispositivo de acceso secuencial: para leer un archivo que está al final, se deben rebobinar o avanzar todos los anteriores. El tiempo de recuperación de un archivo depende de su longitud y de la posición que ocupa en la cinta.
Problema formal:Dada una colección de archivos de longitudes L1​,L2​,…,Ln​, debemos ordenarlos en la cinta (una secuencia lineal) de manera que se minimice el tiempo total esperado de acceso si todos los archivos tienen la misma probabilidad de ser solicitados. El tiempo de acceso para el archivo en posición k es la suma de las longitudes de los archivos anteriores más la mitad de su propia longitud (tiempo de posicionamiento + lectura). Tradicionalmente se simplifica como:
Tiempo total=∑k=1n(∑j=1kLj)Tiempo total=k=1∑n​(j=1∑k​Lj​)
Minimizar esa suma equivale a minimizar el tiempo medio de búsqueda.
2.2. Definición clara y notación
Archivo i: longitud Li​ (unidades de tiempo o bytes).
Orden: una permutación π de los índices.
Costo total para una permutación:
T(π)=∑i=1n∑j=1iLπ(j)T(π)=i=1∑n​j=1∑i​Lπ(j)​
(Equivalente a: para cada archivo, se suma su longitud multiplicada por el número de archivos que están desde él hasta el final, o en otra forma: T=nLπ(1)+(n−1)Lπ(2)+⋯+1⋅Lπ(n)T=nLπ(1)​+(n−1)Lπ(2)​+⋯+1⋅Lπ(n)​).
Objetivo: encontrar ππ que minimice TT.
2.3. Demostración de la optimalidad del ordenamiento por longitud creciente
Estrategia voraz: ordenar los archivos por longitud ascendente (de menor a mayor).
Intuición: Los archivos más pequeños deben ir al principio, porque se suman más veces (cada archivo se cuenta en el tiempo de todos los que le siguen). Si dos archivos adyacentes están desordenados (uno largo antes que uno corto), intercambiarlos reduce el costo total.
Demostración simple: Supongamos dos archivos aa y bb con longitudes La≤LbLa​≤Lb​. Si en el orden actual va primero bb y luego aa, su contribución parcial al costo es: 2Lb+1La2Lb​+1La​. Si los intercambiamos (primero aa, luego bb), la contribución es: 2La+1Lb2La​+1Lb​. La diferencia:
(2Lb+La)−(2La+Lb)=Lb−La≥0.(2Lb​+La​)−(2La​+Lb​)=Lb​−La​≥0.
Por tanto, el orden a,ba,b es mejor o igual. Aplicando burbuja, el orden óptimo global es el orden creciente.
2.4. Implementación en Python con código comentado
python
# almacenamiento_cintas.py
# Problema: Almacenamiento óptimo en cintas magnéticas
# Estrategia voraz: ordenar archivos por longitud ascendente.
def minimizar_tiempo_acceso(longitudes):
    """
    Calcula el orden óptimo y el tiempo total mínimo de acceso.
    Parámetros:
    longitudes: lista de números (enteros o floats) que representan
                las longitudes de los archivos en unidades de tiempo.
    Retorna:
    orden_optimo: lista con las longitudes ordenadas ascendentemente.
    tiempo_total: valor del costo total según la fórmula.
    """
    # Paso 1: Ordenar las longitudes de menor a mayor (estrategia voraz)
    orden_optimo = sorted(longitudes)  # sorted retorna una nueva lista ordenada
    # Paso 2: Calcular tiempo total usando la fórmula
    # T = n*L1 + (n-1)*L2 + ... + 1*Ln
    n = len(orden_optimo)
    tiempo_total = 0
    for i, long in enumerate(orden_optimo):
        # i = 0 para el primer elemento (factor n), i=1 factor (n-1), etc.
        factor = n - i
        tiempo_total += factor * long
        # Nota: también podría sumarse con la fórmula alternativa:
        # sum(L[j] for j in range(i+1)) pero el método con factores es más directo.
    return orden_optimo, tiempo_total
# Ejemplo de uso y verificación
if __name__ == "__main__":
    archivos = [15, 3, 8, 20, 5]  # longitudes en segundos (ejemplo)
    orden, tiempo = minimizar_tiempo_acceso(archivos)
    print("Longitudes originales:", archivos)
    print("Orden óptimo (menor a mayor):", orden)
    print("Tiempo total mínimo (unidades):", tiempo)
    # Explicación paso a paso para el ejemplo:
    # orden = [3, 5, 8, 15, 20]
    # T = 5*3 + 4*5 + 3*8 + 2*15 + 1*20 = 15 + 20 + 24 + 30 + 20 = 109
Comentarios del código:
sorted() implementa el ordenamiento voraz.
El cálculo del tiempo total es directo según la fórmula.
Complejidad temporal: O(nlog⁡n)O(nlogn) debido al ordenamiento.
Complejidad espacial: O(n)O(n) para la lista ordenada (si creamos copia).
2.5. Complejidad temporal y espacial
Medida | Valor | Explicación
Tiempo (peor caso) | O(n log n) | Por el algoritmo de ordenamiento (Timsort en Python).
Tiempo (mejor caso) | O(n) | Si ya está ordenado, algunos ordenamientos lineales, pero sorted sigue siendo O(n log n) en promedio.
Espacio adicional | O(n) | Para la copia ordenada. Se puede hacer in-place con .sort() pero la función retorna una nueva lista para no modificar la original.
2.6. Analogía: “Organizar los platos en un lavavajillas industrial”
Imagina un lavavajillas de tipo túnel por donde pasan bandejas. Cada plato necesita un tiempo de lavado diferente. El lavavajillas procesa en orden, y cada plato se queda dentro hasta que todos los que están después salen. El tiempo total que pasa la vajilla dentro del sistema es la suma de los tiempos de cada plato multiplicados por el número de platos que quedan detrás. Si pones los platos más pequeños (los que se lavan rápido) al principio, el sistema se vacía antes. ¡Eso es exactamente el problema de las cintas!
3. EL PROBLEMA DE LA BOLSA DE PAPEL (PROBLEMA DE LA MOCHILA FRACCIONARIA)
3.1. Origen del nombre y variantes
El nombre “bolsa de papel” (en inglés paper bag problem) a veces se usa como sinónimo de problema de la mochila (knapsack problem). La imagen mental: tienes una bolsa de papel que puede soportar un peso máximo W y quieres meter objetos (cada uno con un peso wi​ y un valor vi​) de manera que maximices el valor total, pero puedes romper los objetos (tomar fracciones). Esto es la mochila fraccionaria. La variante 0/1 (no fraccionaria) no es resoluble con un algoritmo voraz óptimo.
3.2. Definición formal
Capacidad máxima: C (número real positivo).
Objetos: para i=1…n, cada uno tiene:
Peso wi​>0
Valor vi​≥0
Variable de decisión: fracción xi​∈[0,1] de cada objeto que se introduce.
Restricción: ∑i=1n​xi​⋅wi​≤C
Función objetivo: maximizar ∑i=1n​xi​⋅vi​.
La solución óptima voraz consiste en ordenar los objetos por ratio valor/peso descendente (vi​/wi​) y tomar todo lo posible de los objetos con mayor ratio hasta llenar la capacidad.
3.3. Demostración de la estrategia voraz
Propiedad de elección voraz: Siempre existe una solución óptima que incluye la mayor cantidad posible del objeto con mayor ratio valor/peso.
Esbozo de demostración: Si en una solución óptima no se toma completamente el objeto de mayor ratio, podemos reemplazar una fracción de otro objeto de menor ratio por el de mayor ratio sin disminuir el valor total y manteniendo la capacidad.
Subestructura óptima: Después de tomar una fracción del objeto de mayor ratio, el problema restante es una mochila fraccionaria con capacidad reducida y los objetos restantes.
3.4. Implementación en Python
python
# mochila_fraccionaria.py
# Problema de la bolsa de papel (mochila fraccionaria)
# Estrategia voraz: ordenar por valor/peso descendente
def mochila_fraccionaria(valores, pesos, capacidad):
    """
    Resuelve el problema de la mochila fraccionaria.
    Parámetros:
    valores: lista de valores (beneficios) de cada objeto.
    pesos: lista de pesos de cada objeto.
    capacidad: número real con la capacidad total.
    Retorna:
    valor_total: valor máximo alcanzable.
    fracciones: lista de fracciones (x_i) para cada objeto.
    """
    # Crear lista de tuplas (valor, peso, indice, ratio) para ordenar
    n = len(valores)
    objetos = []
    for i in range(n):
        ratio = valores[i] / pesos[i]  # valor por unidad de peso
        objetos.append((ratio, valores[i], pesos[i], i))
    # Ordenar por ratio descendente (mayor a menor)
    objetos.sort(reverse=True, key=lambda x: x[0])
    valor_total = 0.0
    fracciones = [0.0] * n
    capacidad_restante = capacidad
    for ratio, valor, peso, idx in objetos:
        if peso <= capacidad_restante:
            # Tomar el objeto completo
            fracciones[idx] = 1.0
            valor_total += valor
            capacidad_restante -= peso
        else:
            # Tomar solo una fracción para llenar la capacidad
            fraccion = capacidad_restante / peso
            fracciones[idx] = fraccion
            valor_total += valor * fraccion
            break  # La mochila está llena, no se consideran más objetos
    return valor_total, fracciones
# Ejemplo de uso
if __name__ == "__main__":
    valores = [60, 100, 120]   # valores de los objetos
    pesos  = [10,  20,  30]    # pesos respectivos
    capacidad = 50
    valor, frac = mochila_fraccionaria(valores, pesos, capacidad)
    print("Valores:", valores)
    print("Pesos:", pesos)
    print("Capacidad:", capacidad)
    print("Valor máximo obtenido:", valor)
    print("Fracciones tomadas:", frac)
    # Salida esperada:
    # Objeto 0: ratio 6, peso 10 -> toma completo (1)
    # Objeto 1: ratio 5, peso 20 -> toma completo (1)
    # Objeto 2: ratio 4, peso 30 -> capacidad restante 20 -> fracción 20/30=0.666...
    # Valor = 60 + 100 + 120*0.666... = 60+100+80 = 240
Comentarios del código:
objetos.sort(reverse=True, key=lambda x: x[0]) implementa la elección voraz.
El bucle recorre en orden descendente de ratio.
Cuando la capacidad no alcanza para el objeto completo, se toma la fracción exacta y se termina.
Complejidad: O(nlog⁡n)O(nlogn) por el ordenamiento.
3.5. Comparación con la mochila 0/1 (no voraz)
Característica | Mochila Fraccionaria | Mochila 0/1
¿Permite fracciones? | Sí | No
Estrategia voraz óptima? | Sí | No (requiere programación dinámica)
Complejidad óptima | O(n logn)  | O(n⋅C) pseudo-polinomial
Ejemplo donde falla voraz | No aplica | Objetos: valor[60,100,120], peso[10,20,30], cap 50 -> voraz da 220 (toma 100+120? no cabe; voraz ordenado por valor/peso daría 60+100+? = 160; pero el óptimo es 220? Revisar: El óptimo 0/1 es 100+120? 20+30=50, valor=220. La estrategia voraz por valor/peso seleccionaría primero 60 (ratio 6), luego 100 (5), luego no cabe 120, total 160. ¡Voraz falla!
Nota importante: Este problema es de los pocos donde el método de adquisición sí produce el óptimo global porque se permite fraccionar. Es un ejemplo didáctico perfecto para entender la propiedad de elección voraz.
3.6. Analogía: “Llenar una maleta de mano con objetos preciosos”
Vas a viajar y tu maleta de mano tiene un límite de 10 kg. Tienes oro en polvo (valor muy alto por gramo), plata (menor valor) y arena (valor nulo). Claramente quieres llenar la maleta primero con oro, luego con plata, y si sobra espacio, con arena. Como puedes fraccionar (el oro es polvo), la estrategia voraz es óptima. En cambio, si tuvieras lingotes (objetos indivisibles), podrías tener que elegir entre un lingote de oro de 9 kg (valor 9000) y uno de plata de 2 kg (valor 1000) con capacidad 10 kg: tomar el lingote de oro te da 9000 pero desperdicias 1 kg; tomar el de plata más otro pequeño podría dar 9500. Ahí el voraz por valor/peso (oro es mejor) fallaría.
4. MODELOS ÓPTIMOS DE INTERCALACIÓN
4.1. El problema de la fusión de archivos (costo de fusión mínimo)
Tenemos varios archivos (o listas ordenadas) de diferentes tamaños. Queremos fusionarlos en un solo archivo ordenado. La operación de fusión (merge) de dos archivos de tamaños a y b tiene un costo proporcional a a+b (porque se deben leer ambos y escribir el resultado). El problema consiste en decidir el orden de las fusiones (usando fusión binaria) para minimizar el costo total.
Este problema es idéntico al de la codificación óptima de Huffman (construcción de un árbol binario óptimo para códigos de longitud variable). La estrategia voraz es: siempre fusionar los dos archivos más pequeños.
4.2. Relación con el algoritmo de Huffman
En 1952, David A. Huffman desarrolló un algoritmo para construir códigos prefijo óptimos. El problema de minimizar el costo de fusión es equivalente si consideramos cada archivo como una hoja con peso igual a su tamaño, y el costo total de fusión es la suma de los pesos de los nodos internos (que es igual a la suma de cada hoja multiplicada por su profundidad). La analogía: códigos más cortos para símbolos más frecuentes ↔ archivos pequeños se fusionan primero.
4.3. Definición formal
Entrada: lista de longitudes (tamaños) L1​,L2​,…,Ln​.
Operación permitida: tomar dos elementos x,y, eliminarlos de la lista e insertar x+y (costo x+y).
Objetivo: minimizar la suma de todos los costos de las operaciones hasta que quede un solo elemento.
Observación: El costo total es independiente del orden de los pares, pero el orden de fusión afecta el costo.
4.4. Estrategia voraz: usar una cola de prioridad (min-heap)
Algoritmo:
Insertar todos los tamaños en una cola de prioridad mínima (heap).
Mientras haya más de un elemento:
Extraer los dos más pequeños (a y b).
Sumar costo_total += a + b.
Insertar (a+b) en el heap.
El costo_total es el costo mínimo de fusión.
Justificación: Fusionar los dos más pequeños en cada paso produce un árbol de Huffman óptimo.
4.5. Implementación en Python usando heapq
python
# fusion_optima.py
# Modelo óptimo de intercalación (costo mínimo de fusión)
# Usa el algoritmo de Huffman con cola de prioridad.
import heapq
def costo_minimo_fusion(tamanios):
    """
    Calcula el costo mínimo para fusionar todos los archivos.
    Parámetros:
    tamanios: lista de enteros positivos (tamaños de archivos).
    Retorna:
    costo_total: número entero con el costo total mínimo.
    """
    # Convertir la lista en un heap (montículo) de mínimos
    heapq.heapify(tamanios)  # Operación O(n)
    costo_total = 0
    # Mientras haya al menos dos elementos
    while len(tamanios) > 1:
        # Extraer los dos más pequeños
        a = heapq.heappop(tamanios)  # el mínimo
        b = heapq.heappop(tamanios)  # el segundo mínimo
        costo = a + b
        costo_total += costo
        # Insertar la suma de nuevo al heap
        heapq.heappush(tamanios, costo)
        # Opcional: imprimir el paso
        print(f"Fusionar {a} y {b} -> costo parcial {costo}, acumulado {costo_total}")
    return costo_total
# Ejemplo de uso
if __name__ == "__main__":
    archivos = [4, 3, 2, 6]  # tamaños de archivos a intercalar
    print("Tamaños iniciales:", archivos)
    costo = costo_minimo_fusion(archivos.copy())  # copia para no modificar original
    print("Costo total mínimo de fusión:", costo)
    # Explicación del ejemplo:
    # Paso1: heap = [2,3,4,6] -> extrae 2 y 3, fusiona (5), costo=5, heap=[4,5,6]
    # Paso2: extrae 4 y 5, fusiona (9), costo=5+9=14, heap=[6,9]
    # Paso3: extrae 6 y 9, fusiona (15), costo=14+15=29, heap=[15]
    # Total = 29.
    # Si hubiera fusionado en otro orden (ej: 4+6=10, luego 3+10=13, luego 2+13=15 total 38) es peor.
4.6. Complejidad y demostración de optimalidad
Complejidad temporal: O(nlogn) porque cada operación de heap (pop y push) es O(logn) y se realizan n−1 fusiones.
Espacio: O(n) para el heap.
Demostración de optimalidad (idea): El problema tiene subestructura óptima (el costo mínimo de fusionar n elementos es igual a la suma de los dos más pequeños más el costo óptimo de fusionar los n-1 elementos restantes, donde se reemplazan los dos menores por su suma). La propiedad de elección voraz se demuestra por intercambio: si en una solución óptima los dos elementos fusionados primero no son los dos más pequeños, se puede reemplazar por una fusión de los más pequeños sin aumentar el costo.
4.7. Analogía: “Combinar montones de hojas de cálculo en una oficina”
Eres el encargado de unir varios informes en uno solo. Cada informe tiene un número de páginas. Para unir dos informes, debes fotocopiarlos (costo = suma de páginas) y luego graparlos. La idea es que si juntas primero los informes más pequeños, evitas tener que fotocopiar muchas veces los informes grandes. En la vida real: ordena las tareas por duración creciente y combina las más cortas primero para minimizar el tiempo total de espera en una cola de procesos.
5. LUCES DE ÁRBOL DE COSTO MÍNIMO (ÁRBOL DE EXPANSIÓN MÍNIMA – MST)
5.1. ¿Por qué “Luces”? Explicación del término
El nombre “Luces de árbol de costo mínimo” (en inglés minimum spanning tree) proviene de un problema clásico: conectar varias poblaciones mediante un tendido eléctrico (o de alumbrado público) de manera que todas queden iluminadas (conectadas) pero con la menor longitud total de cable. “Luces” hace referencia a las luces de las farolas. Así que el árbol de expansión mínima es la red de cables que conecta todos los nodos (postes de luz) sin ciclos y con mínimo peso total.
5.2. Definición del problema
Dado un grafo no dirigido, conexo y ponderado (con pesos positivos en las aristas), se desea encontrar un subconjunto de aristas que forme un árbol que incluya todos los vértices y cuya suma de pesos sea mínima. Ese subconjunto se llama árbol de expansión mínima (MST).
Propiedades:
Tiene exactamente ∣V∣−1∣V∣−1 aristas.
Si los pesos son todos distintos, el MST es único.
No tiene ciclos.
5.3. Algoritmo de Prim (estrategia voraz de crecimiento de árbol)
El algoritmo de Prim comienza desde un vértice arbitrario y va agregando la arista de menor peso que conecta un vértice ya en el árbol con un vértice fuera del árbol. Es voraz porque en cada paso elige la arista más barata que extiende el árbol actual.
Pseudocódigo:
Inicializar un conjunto visitados vacío y una cola de prioridad (min-heap) con aristas desde el nodo inicial.
Marcar el nodo inicial como visitado.
Mientras visitados no contenga todos los vértices:
Extraer la arista de menor peso que tenga un extremo en visitados y el otro no.
Agregar el nuevo vértice a visitados y acumular el peso.
Agregar al heap todas las aristas que salen del nuevo vértice hacia no visitados.
5.4. Algoritmo de Kruskal (ordenación de aristas y unión-búsqueda)
Kruskal ordena todas las aristas de menor a mayor peso y las recorre, añadiendo una arista al MST si no forma un ciclo con las ya añadidas. Para detectar ciclos eficientemente se usa una estructura de datos de conjuntos disjuntos (union-find).
Pasos:
Ordenar las aristas por peso ascendente.
Inicializar cada vértice como un conjunto separado.
Para cada arista en orden:
Si los vértices de la arista pertenecen a conjuntos diferentes, unirlos y añadir la arista al MST.
Parar cuando se tengan ∣V∣−1∣V∣−1 aristas.
5.5. Implementación en Python de ambos algoritmos
Algoritmo de Prim (usando heap)
python
# prim_mst.py
# Árbol de expansión mínima - Algoritmo de Prim
import heapq
def prim(grafo, inicio=0):
    """
    grafo: lista de adyacencia donde grafo[u] = [(v, peso), ...]
    inicio: vértice desde donde empezar.
    Retorna: (peso_total, aristas_del_mst)
    """
    n = len(grafo)
    visitado = [False] * n
    # cola de prioridad: (peso, vertice_origen, vertice_destino)
    heap = []
    # Empezar desde el nodo 'inicio'
    visitado[inicio] = True
    for vecino, peso in grafo[inicio]:
        heapq.heappush(heap, (peso, inicio, vecino))
    peso_total = 0
    aristas_mst = []
    while heap and len(aristas_mst) < n - 1:
        peso, u, v = heapq.heappop(heap)
        if not visitado[v]:
            visitado[v] = True
            peso_total += peso
            aristas_mst.append((u, v, peso))
            # Agregar todas las aristas desde v hacia no visitados
            for vecino, p in grafo[v]:
                if not visitado[vecino]:
                    heapq.heappush(heap, (p, v, vecino))
    return peso_total, aristas_mst
# Ejemplo de uso
if __name__ == "__main__":
    # Grafo con 4 vértices (0..3)
    # Representación como lista de adyacencia
    grafo_ejemplo = [
        [(1, 1), (2, 3)],   # vértice 0 conectado a 1 (peso1) y a 2 (peso3)
        [(0, 1), (2, 1), (3, 4)],  # vértice 1
        [(0, 3), (1, 1), (3, 2)],  # vértice 2
        [(1, 4), (2, 2)]           # vértice 3
    ]
    peso, aristas = prim(grafo_ejemplo, 0)
    print("Peso total MST (Prim):", peso)
    print("Aristas del MST (u,v,peso):", aristas)
    # Salida esperada: aristas (0-1:1), (1-2:1), (2-3:2) peso total=4
Algoritmo de Kruskal (con union-find)
python
# kruskal_mst.py
# Árbol de expansión mínima - Algoritmo de Kruskal
class UnionFind:
    def __init__(self, n):
        self.parent = list(range(n))
        self.rank = [0] * n
    def find(self, x):
        # Búsqueda con compresión de camino
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])
        return self.parent[x]
    def union(self, x, y):
        # Unión por rango
        xr, yr = self.find(x), self.find(y)
        if xr == yr:
            return False
        if self.rank[xr] < self.rank[yr]:
            self.parent[xr] = yr
        elif self.rank[xr] > self.rank[yr]:
            self.parent[yr] = xr
        else:
            self.parent[yr] = xr
            self.rank[xr] += 1
        return True
def kruskal(n, aristas):
    """
    n: número de vértices.
    aristas: lista de tuplas (peso, u, v).
    Retorna: (peso_total, lista de aristas del MST)
    """
    aristas.sort()  # ordenar por peso ascendente
    uf = UnionFind(n)
    peso_total = 0
    mst = []
    for peso, u, v in aristas:
        if uf.union(u, v):
            peso_total += peso
            mst.append((u, v, peso))
            if len(mst) == n - 1:
                break
    return peso_total, mst
# Ejemplo
if __name__ == "__main__":
    # Para el mismo grafo anterior, las aristas con sus pesos:
    aristas = [
        (1, 0, 1),
        (3, 0, 2),
        (1, 1, 2),
        (4, 1, 3),
        (2, 2, 3)
    ]
    peso, mst = kruskal(4, aristas)
    print("Peso total MST (Kruskal):", peso)
    print("Aristas del MST:", mst)
5.6. Comparación de complejidades
Algoritmo | Complejidad temporal | Estructura de datos clave
Prim (con heap) | O((V+E) logV) | Cola de prioridad (binary heap)
Prim (con matriz de adyacencia) | O(V2) | Array simple, bueno para grafos densos
Kruskal (con union-find) | O(E logE)  | Ordenamiento + union-find casi lineal
Elección: Si el grafo es denso (E≈V2E≈V2), Prim con matriz (O(V2)O(V2)) puede ser más rápido que Kruskal (O(V2log⁡V)O(V2logV)). Si es disperso (E∼VE∼V), ambos son similares.
5.7. Analogías
Cables de internet en un pueblo: Un pueblo con varias casas. El tendido de fibra óptica más barato que conecte a todos es un MST. La empresa tiende cables por las rutas más económicas sin hacer bucles inútiles.
Construcción de carreteras interestatales: Conectar todas las capitales de provincia con la menor longitud de asfalto posible.
6. PASO MÁS CORTO DESDE UN INICIO ÚNICO (ALGORITMO DE DIJKSTRA)
6.1. Definición
El problema de caminos mínimos desde un origen único (Single-Source Shortest Paths, SSSP) consiste en encontrar, dado un grafo dirigido o no dirigido con aristas ponderadas (pesos no negativos), las distancias más cortas desde un vértice fuente ss a todos los demás vértices. El algoritmo de Dijkstra (1959) resuelve este problema con estrategia voraz.
Condición fundamental: Todos los pesos de las aristas deben ser no negativos. Si hay pesos negativos, Dijkstra puede fallar; se usa Bellman-Ford.
6.2. Condición de voracidad y pseudocódigo
El algoritmo mantiene un conjunto de vértices ya procesados (cuya distancia mínima se conoce) y una cola de prioridad (min-heap) de candidatos. En cada paso, extrae el vértice con la distancia tentativa más pequeña (el que aún no ha sido procesado) y lo relaja (actualiza las distancias a sus vecinos). Esto es voraz porque siempre elige el vértice más cercano no procesado.
Pseudocódigo:
text
distancia[s] = 0
para cada v != s: distancia[v] = INF
cola_prioridad = [(0, s)]
mientras cola no vacía:
    (d, u) = extraer_min()
    si d > distancia[u]: continuar
    para cada (v, peso) en adyacencias[u]:
        if distancia[u] + peso < distancia[v]:
            distancia[v] = distancia[u] + peso
            insertar (distancia[v], v) en cola
6.3. Demostración de optimalidad
La demostración se basa en que al extraer un vértice uu de la cola con distancia dd, esa distancia es la mínima definitiva, porque cualquier otro camino hacia uu tendría que pasar por algún vértice con distancia mayor (debido a la propiedad de que los pesos son no negativos). Es una aplicación de la propiedad de elección voraz.
6.4. Implementación en Python con heap
python
# dijkstra.py
# Algoritmo de Dijkstra para caminos mínimos desde un origen único
import heapq
def dijkstra(grafo, origen):
    """
    grafo: lista de adyacencia donde grafo[u] = [(v, peso), ...]
    origen: vértice fuente (entero)
    Retorna:
        dist: lista con la distancia mínima desde origen a cada vértice.
        prev: lista con el vértice predecesor en el camino mínimo (para reconstruir rutas).
    """
    n = len(grafo)
    INF = float('inf')
    dist = [INF] * n
    dist[origen] = 0
    prev = [-1] * n  # para reconstrucción de caminos
    # cola de prioridad: (distancia, vertice)
    heap = [(0, origen)]
    while heap:
        d_actual, u = heapq.heappop(heap)
        # Si la distancia en el heap es mayor que la almacenada, ignorar
        if d_actual > dist[u]:
            continue
        # Explorar vecinos
        for v, peso in grafo[u]:
            nueva_dist = dist[u] + peso
            if nueva_dist < dist[v]:
                dist[v] = nueva_dist
                prev[v] = u
                heapq.heappush(heap, (nueva_dist, v))
    return dist, prev
def reconstruir_camino(prev, destino):
    """Devuelve la lista de vértices desde el origen hasta destino."""
    camino = []
    v = destino
    while v != -1:
        camino.append(v)
        v = prev[v]
    return camino[::-1]  # invertir para orden origen->destino
# Ejemplo de uso
if __name__ == "__main__":
    # Grafo dirigido con 5 vértices (0..4)
    # Representación: (vecino, peso)
    grafo_ejemplo = [
        [(1, 4), (2, 1)],   # desde 0
        [(3, 1)],           # desde 1
        [(1, 2), (3, 5)],   # desde 2
        [(4, 3)],           # desde 3
        []                  # desde 4
    ]
    dist, prev = dijkstra(grafo_ejemplo, 0)
    print("Distancias desde vértice 0:", dist)
    # Salida esperada: dist[0]=0, dist[1]=3 (0->2->1), dist[2]=1, dist[3]=4 (0->2->1->3), dist[4]=7
    print("Camino a 4:", reconstruir_camino(prev, 4))
6.5. Variantes: matriz de adyacencia vs lista de adyacencia
Lista de adyacencia (como en el ejemplo): eficiente para grafos dispersos, O((V+E)log⁡V)O((V+E)logV).
Matriz de adyacencia: implementación sencilla pero O(V2)O(V2); útil para grafos densos pequeños.
6.6. Analogías
Navegación GPS para llegar al trabajo: El GPS calcula la ruta más rápida considerando el tráfico (pesos = tiempo). En cada intersección, elige el camino que parece más corto hasta el momento, pero no significa que siempre sea el óptimo global si hay que considerar atascos (pero con pesos no negativos funciona).
Reparto de pizza a múltiples casas: Un repartidor sale de la pizzería y debe entregar pedidos. Dijkstra le da las distancias mínimas a cada casa, pero no el orden de entrega (eso es otro problema: el viajante).
7. CASOS PRÁCTICOS INTEGRADORES
7.1. Caso 1: Optimización de procesos en un centro de datos
Situación (contexto realista):Una empresa de servicios en la nube dispone de un centro de datos con un sistema de almacenamiento en cinta para backups (problema de almacenamiento óptimo). Además, para procesar grandes volúmenes de logs, deben fusionar varios archivos de registros ordenados por fecha (modelo de intercalación óptima). El director de operaciones quiere minimizar el tiempo total de acceso a las cintas y, por separado, el costo de fusión de logs. Se te pide aplicar la estrategia de adquisición para ambos subproblemas y presentar un informe.
Acciones realizadas (pasos técnicos):
Se recopilaron las longitudes de 10 archivos de backup: [120, 45, 30, 200, 75, 60, 110, 90, 35, 50] GB.
Se aplicó el algoritmo de almacenamiento óptimo: ordenar ascendente.
Se calcularon los tiempos totales de acceso (fórmula de factores).
Para el problema de fusión: se tenían 5 archivos de logs con tamaños [15, 8, 23, 10, 7] MB. Se aplicó el algoritmo de Huffman (fusionar siempre los dos más pequeños) con una cola de prioridad.
Se implementó el código en Python (ver abajo) para simular ambos procesos.
Hallazgos (observaciones, problemas detectados):
El orden óptimo para backups fue [30, 35, 45, 50, 60, 75, 90, 110, 120, 200] produciendo un tiempo total de 10*30 + 9*35 + ... = 4470 (unidades). Si se hubiera ordenado descendentemente, el tiempo total sería 10*200 + 9*120 + ... = 6170, un 38% peor.
Para la fusión de logs, el orden voraz dio costo total de 15+8=23, luego 10+7=17, etc., resultando un costo total de 128 MB procesados. Un orden ingenuo (fusionar el más grande con el más pequeño) habría dado 177 MB, un 38% más costoso.
Se detectó que el algoritmo de fusión con heap es muy sensible a la implementación: usar heapq fue eficiente.
Recomendaciones (buenas prácticas):
Para el almacenamiento en cinta, siempre ordenar por longitud ascendente. Si los archivos tienen diferentes probabilidades de acceso, usar el orden por ratio (tiempo de acceso / probabilidad) – generalización del problema.
En la fusión, utilizar una cola de prioridad (min-heap) para garantizar eficiencia O(nlog⁡n)O(nlogn).
Documentar el código con comentarios que expliquen la estrategia voraz.
En entornos reales, si los tamaños cambian dinámicamente, re-ejecutar los algoritmos periódicamente.
Resultados (beneficios obtenidos):
Reducción del tiempo medio de recuperación de backups en un 38% respecto a un orden aleatorio.
Reducción del costo de procesamiento de logs en un 35% respecto a una estrategia no planificada.
Ahorro estimado de 200 horas de procesamiento al mes.
Código completo comentado (Python) – solo parte relevante:
python
# caso_centro_datos.py
# Integración de almacenamiento en cintas y fusión óptima
import heapq
def almacenamiento_cintas(longitudes):
    """Ordena ascendente y calcula tiempo total (fórmula factor)"""
    ordenado = sorted(longitudes)
    n = len(ordenado)
    tiempo = 0
    for i, long in enumerate(ordenado):
        tiempo += (n - i) * long
    return ordenado, tiempo
def costo_fusion_minimo(tamanios):
    """Algoritmo de Huffman para fusión"""
    heapq.heapify(tamanios)
    costo = 0
    while len(tamanios) > 1:
        a = heapq.heappop(tamanios)
        b = heapq.heappop(tamanios)
        suma = a + b
        costo += suma
        heapq.heappush(tamanios, suma)
    return costo
# Datos del caso
backups = [120, 45, 30, 200, 75, 60, 110, 90, 35, 50]
logs = [15, 8, 23, 10, 7]
orden_backups, tiempo_backups = almacenamiento_cintas(backups)
costo_logs = costo_fusion_minimo(logs.copy())
print("=== RESULTADOS CENTRO DE DATOS ===")
print("Orden óptimo de backups (GB):", orden_backups)
print("Tiempo total de acceso:", tiempo_backups)
print("Costo mínimo de fusión de logs (MB):", costo_logs)
7.2. Caso 2: Planificación de redes de transporte público
Situación:Una municipalidad desea conectar 7 barrios mediante un sistema de ciclovías (árbol de costo mínimo) y además necesita conocer la ruta más corta desde la estación central (nodo 0) hasta cada uno de los barrios (Dijkstra). Las distancias entre barrios (en km) se muestran en la siguiente tabla. Aplicar ambos algoritmos voraces.
Acciones realizadas:
Construir grafo no dirigido con 7 vértices (0 a 6).
Aplicar algoritmo de Prim para obtener el MST (ciclovías que conecten todos con mínimo kilometraje).
Aplicar Dijkstra desde el barrio 0 para obtener las distancias mínimas a cada barrio (para planificar rutas de emergencia).
Implementar en Python con listas de adyacencia.
Hallazgos:
El MST tuvo un peso total de 27 km (conexiones: 0-1, 0-2, 1-3, 2-4, 3-5, 4-6). Esto ahorra un 30% de longitud comparado con un árbol aleatorio.
Las distancias de Dijkstra desde 0 fueron: dist[0]=0, dist[1]=4, dist[2]=3, dist[3]=7, dist[4]=8, dist[5]=12, dist[6]=14.
Se observó que Dijkstra y Prim usan estructuras similares (heap) pero con diferentes criterios.
Recomendaciones:
Para redes grandes, usar heapq en Python para eficiencia.
Almacenar el grafo como lista de adyacencia para acceso rápido.
Verificar que no haya pesos negativos antes de usar Dijkstra.
Resultados:
Se construyó la red de ciclovías con el menor costo posible, aprobada por el presupuesto.
Se establecieron tiempos de respuesta máximos desde la central para ambulancias.
Código completo comentado (Python):
python
# caso_transporte.py
# Planificación de ciclovías (MST con Prim) y caminos mínimos (Dijkstra)
import heapq
def prim_mst(grafo):
    """Retorna peso total y aristas del MST usando Prim (heap)"""
    n = len(grafo)
    visitado = [False]*n
    heap = [(0, 0, -1)]  # (peso, vertice_actual, padre)
    peso_total = 0
    aristas = []
    while heap:
        peso, u, padre = heapq.heappop(heap)
        if visitado[u]:
            continue
        visitado[u] = True
        if padre != -1:
            aristas.append((padre, u, peso))
            peso_total += peso
        for v, p in grafo[u]:
            if not visitado[v]:
                heapq.heappush(heap, (p, v, u))
    return peso_total, aristas
def dijkstra(grafo, origen):
    n = len(grafo)
    INF = float('inf')
    dist = [INF]*n
    dist[origen] = 0
    heap = [(0, origen)]
    while heap:
        d, u = heapq.heappop(heap)
        if d > dist[u]:
            continue
        for v, p in grafo[u]:
            nd = d + p
            if nd < dist[v]:
                dist[v] = nd
                heapq.heappush(heap, (nd, v))
    return dist
# Grafo de 7 barrios (lista de adyacencia no dirigida)
# Formato: (vecino, distancia_km)
grafo = [
    [(1,4), (2,3)],          # barrio 0
    [(0,4), (3,5), (4,6)],   # barrio 1
    [(0,3), (4,5)],          # barrio 2
    [(1,5), (5,2)],          # barrio 3
    [(1,6), (2,5), (6,4)],   # barrio 4
    [(3,2), (6,3)],          # barrio 5
    [(4,4), (5,3)]           # barrio 6
]
peso_mst, aristas_mst = prim_mst(grafo)
distancias_desde_0 = dijkstra(grafo, 0)
print("=== RED DE CICLOVÍAS (MST) ===")
print("Peso total del árbol:", peso_mst, "km")
print("Conexiones:", aristas_mst)
print("\n=== DISTANCIAS MÍNIMAS DESDE BARRIO 0 ===")
for i, d in enumerate(distancias_desde_0):
    print(f"Barrio {i}: {d} km")
8. EJERCICIOS PRÁCTICOS
8.1. Ejercicios resueltos paso a paso
Ejercicio resuelto 1: Almacenamiento en cintas con probabilidades
Enunciado:Se tienen 4 archivos de longitudes: 10, 2, 8, 5. Pero ahora las probabilidades de acceso son diferentes: 0.1, 0.5, 0.3, 0.1 respectivamente. El tiempo de acceso esperado es ∑ipi⋅Ti∑i​pi​⋅Ti​ donde TiTi​ es la suma de longitudes hasta el archivo i (incluyéndolo). Encuentra el orden óptimo (estrategia voraz generalizada: ordenar por Lipipi​Li​​ ascendente o descendente? Demostramos: el criterio es ordenar por ratio longitud/probabilidad ascendente).
Solución paso a paso:
Identificar la fórmula exacta. Para un orden ππ, el tiempo esperado es ∑k=1npπ(k)∑j=1kLπ(j)∑k=1n​pπ(k)​∑j=1k​Lπ(j)​. Se puede reescribir como ∑j=1nLπ(j)∑k=jnpπ(k)∑j=1n​Lπ(j)​∑k=jn​pπ(k)​. Para minimizar, se aplica el criterio de Smith: ordenar por Lipipi​Li​​ ascendente (o equivalentemente, piLiLi​pi​​ descendente). Más sencillo: ordenar por la relación pi/Lipi​/Li​ (beneficio por unidad de longitud) de mayor a menor.
Calcular ratios pi/Lipi​/Li​:
Archivo A (L=10, p=0.1) → 0.1/10 = 0.01
Archivo B (L=2, p=0.5) → 0.5/2 = 0.25
Archivo C (L=8, p=0.3) → 0.3/8 = 0.0375
Archivo D (L=5, p=0.1) → 0.1/5 = 0.02
Ordenar por ratio descendente (mayor a menor):B (0.25), C (0.0375), D (0.02), A (0.01) → orden: [B, C, D, A].
Calcular tiempo esperado para ese orden:Tiempo acumulado por archivo:
B: L=2, tiempo total hasta B = 2 → contribución esperada = 0.5 * 2 = 1.0
C: suma longitudes anteriores+actual = 2+8=10 → contribución = 0.3*10 = 3.0
D: suma = 2+8+5=15 → contribución = 0.1*15 = 1.5
A: suma = 2+8+5+10=25 → contribución = 0.1*25 = 2.5Tiempo esperado total = 1+3+1.5+2.5 = 8.0.
Comparar con un orden arbitrario (ej. A,B,C,D):A: L=10, tiempo=10, contrib=0.1*10=1.0B: 10+2=12, contrib=0.5*12=6.0C: 12+8=20, contrib=0.3*20=6.0D: 20+5=25, contrib=0.1*25=2.5Total = 15.5, mucho peor.
Respuesta: El orden óptimo es [B, C, D, A] con tiempo esperado 8.0.
Ejercicio resuelto 2: Mochila fraccionaria con objetos múltiples
Enunciado:Capacidad C=15. Objetos: (valor, peso) = (10,2), (5,3), (15,5), (7,4). Resolver con algoritmo voraz.
Solución:
Calcular ratios valor/peso:
Obj0: 10/2 = 5.0
Obj1: 5/3 ≈ 1.667
Obj2: 15/5 = 3.0
Obj3: 7/4 = 1.75
Ordenar descendente: Obj0 (5.0), Obj2 (3.0), Obj3 (1.75), Obj1 (1.667).
Capacidad restante = 15.
Tomar Obj0 completo: peso=2, valor+=10, resto=13.
Tomar Obj2 completo: peso=5, valor+=15, resto=8.
Tomar Obj3 completo: peso=4, valor+=7, resto=4.
Tomar fracción de Obj1: fracción = resto/peso = 4/3 ≈ 1.333? Pero no puede ser >1; en realidad capacidad restante 4, peso de Obj1=3, se puede tomar completo? 4>=3, así que tomar completo Obj1: valor+=5, resto=1. Obj1 completo ya fue, pero ya no quedan objetos. Espera, orden: después de Obj3, sigue Obj1, capacidad restante 4, peso 3 → tomar completo, resto 1. Luego ya no hay más. Valor total = 10+15+7+5 = 37. ¿Se puede mejorar? Si tomamos fracción de algún otro? No, porque ya todos los objetos se tomaron completos porque la capacidad era suficiente para todos (suma de pesos = 2+5+4+3=14 ≤15). Entonces la solución voraz toma todos completos, valor = 37. Efectivamente es óptimo porque cabe todo.
Código de verificación:
python
valores = [10,5,15,7]
pesos = [2,3,5,4]
cap = 15
# Usar función mochila_fraccionaria anterior
print(mochila_fraccionaria(valores,pesos,cap))  # (37.0, fracciones [1,1,1,1])
8.2. Ejercicios propuestos con diferentes niveles de dificultad
Nivel básico (1-2)
Ejercicio 1: Dadas las longitudes [20, 10, 5, 15, 25], encuentra el orden óptimo para minimizar el tiempo de acceso total en una cinta magnética (sin probabilidades). Calcula el tiempo total mínimo.
Ejercicio 2: Una mochila fraccionaria con capacidad 10. Objetos: (valor, peso) = (4,5), (3,4), (6,6). ¿Cuál es el valor máximo? ¿Qué fracciones se toman?
Nivel intermedio (3-4)
Ejercicio 3: Fusiona los siguientes archivos de manera óptima (costo mínimo): tamaños [6, 5, 12, 10, 8]. Dibuja el árbol de Huffman y calcula el costo total.
Ejercicio 4: Aplica el algoritmo de Prim al siguiente grafo (vértices A,B,C,D,E) con aristas: A-B=2, A-C=3, B-C=1, B-D=4, C-D=5, C-E=6, D-E=2. Obtén el MST y su peso.
Nivel avanzado (5)
Ejercicio 5: Diseña un algoritmo voraz para el problema de "selección de actividades" (cada actividad tiene inicio y fin, maximizar número de actividades no solapadas). Demuestra que es óptimo. Implementa en Python. Aplica a: actividades [(1,4),(3,5),(0,6),(5,7),(3,9),(5,9),(6,10),(8,11),(8,12),(2,14),(12,16)].
8.3. Guías de solución para los propuestos
Solución ejercicio 1:Orden ascendente: [5,10,15,20,25]. Tiempo = 5*5 + 4*10 + 3*15 + 2*20 + 1*25 = 25+40+45+40+25 = 175.
Solución ejercicio 2:Ratios: 4/5=0.8, 3/4=0.75, 6/6=1.0. Orden: Obj2 (ratio1), Obj0, Obj1. Tomar Obj2 completo (peso6, valor6), resto 4. Luego Obj0 (peso5 >4) → fracción 4/5=0.8 → valor adicional 4*0.8=3.2. Total valor = 9.2. Fracciones: [0.8, 0, 1].
Solución ejercicio 3:Usar heap: [5,6,8,10,12] → fusionar 5+6=11 (costo11), heap [8,10,11,12] → 8+10=18 (costo29), heap [11,12,18] → 11+12=23 (costo52), heap [18,23] → 41 (costo93). Total 93. Árbol: hojas 5,6,8,10,12; nodos internos 11,18,23,41.
Solución ejercicio 4:Prim desde A: selecciona A-B=2, luego desde {A,B} arista min a otros: B-C=1 (añade C), ahora {A,B,C} arista B-D=4 o C-E=6, min es 4 (D), luego {A,B,C,D} arista D-E=2 (E). MST: A-B(2), B-C(1), B-D(4), D-E(2) peso total 9.
Solución ejercicio 5 (pseudocódigo):Ordenar actividades por tiempo de fin ascendente. Seleccionar la primera, luego repetir: elegir la siguiente que comience después o igual al fin de la última seleccionada. Implementación:
python
def seleccion_actividades(actividades):
    actividades.sort(key=lambda x: x[1])  # ordenar por fin
    seleccion = []
    ultimo_fin = -1
    for inicio, fin in actividades:
        if inicio >= ultimo_fin:
            seleccion.append((inicio, fin))
            ultimo_fin = fin
    return seleccion
Para las actividades dadas, resultado: (1,4), (5,7), (8,11), (12,16) → 4 actividades.
9. GLOSARIO DE TÉRMINOS Y ACRÓNIMOS
A continuación se listan alfabéticamente los términos clave utilizados en la unidad, con sus definiciones claras y accesibles.
Adquisición (método de): Sinónimo de algoritmo voraz (greedy). Estrategia que toma decisiones localmente óptimas sin reconsiderarlas.
Algoritmo: Secuencia finita de pasos bien definidos para resolver un problema.
Backtracking: Técnica de búsqueda sistemática que prueba opciones y retrocede cuando no conducen a una solución.
Capacidad (C): En el problema de la mochila, peso máximo que puede soportar la bolsa.
Cinta magnética: Dispositivo de almacenamiento secuencial usado históricamente; el tiempo de acceso a un archivo depende de su posición.
Cola de prioridad (heap): Estructura de datos que permite extraer siempre el elemento de menor (o mayor) prioridad de forma eficiente.
Complejidad temporal: Medida del tiempo que tarda un algoritmo en función del tamaño de la entrada.
Dijkstra (algoritmo de): Algoritmo voraz para encontrar caminos más cortos desde un origen en grafos con pesos no negativos.
Estrategia voraz (greedy): Véase Adquisición.
Grafo: Conjunto de vértices (nodos) y aristas (conexiones) que pueden tener pesos.
Heap: Estructura de datos tipo montículo; en Python, el módulo heapq implementa un min-heap.
Huffman (codificación de): Algoritmo voraz para construir códigos prefijo óptimos, equivalente al problema de fusión mínima.
Intercalación (modelos óptimos de): Problema de fusionar listas ordenadas minimizando el costo de las operaciones de mezcla.
Kruskal (algoritmo de): Algoritmo voraz para encontrar el MST ordenando aristas y usando unión-búsqueda.
Mochila 0/1: Variante del problema donde los objetos no se pueden fraccionar; no resoluble con voraz óptimo.
Mochila fraccionaria: Variante que sí permite fracciones y se resuelve por ratio valor/peso.
MST (Minimum Spanning Tree): Árbol de expansión mínima, subgrafo que conecta todos los vértices con mínima suma de pesos.
Óptimo global: Solución que minimiza o maximiza la función objetivo en todo el espacio de búsqueda.
Óptimo local: Solución mejor que sus vecinas inmediatas, pero no necesariamente global.
Prim (algoritmo de): Algoritmo voraz para MST que va creciendo un árbol desde un vértice inicial.
Propiedad de elección voraz: Condición que garantiza que una decisión localmente óptima no impide alcanzar la solución global óptima.
Ratio valor/peso: Medida utilizada en la mochila fraccionaria para ordenar objetos; indica el “valor por unidad de peso”.
Relajación (de arista): Operación en Dijkstra que actualiza la distancia a un vértice si se encuentra un camino mejor.
Subestructura óptima: Propiedad por la cual la solución óptima de un problema contiene soluciones óptimas de sus subproblemas.
Unión-búsqueda (union-find): Estructura de datos para manejar conjuntos disjuntos, usada en Kruskal.
Voraz: Adjetivo que describe un algoritmo que toma la mejor opción inmediata.
10. REFERENCIAS BIBLIOGRÁFICAS (FORMATO APA 7ª EDICIÓN)
Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2009). Introduction to algorithms (3rd ed.). MIT Press.
Dijkstra, E. W. (1959). A note on two problems in connexion with graphs. Numerische Mathematik, 1(1), 269–271.
Huffman, D. A. (1952). A method for the construction of minimum-redundancy codes. Proceedings of the IRE, 40(9), 1098–1101.
Kruskal, J. B. (1956). On the shortest spanning subtree of a graph and the traveling salesman problem. Proceedings of the American Mathematical Society, 7(1), 48–50.
Prim, R. C. (1957). Shortest connection networks and some generalizations. Bell System Technical Journal, 36(6), 1389–1401.
Sedgewick, R., & Wayne, K. (2011). Algorithms (4th ed.). Addison-Wesley.
Weiss, M. A. (2013). Data structures and algorithm analysis in Java (3rd ed.). Pearson. (Sección sobre algoritmos voraces).
Recursos en línea (documentación oficial):
Python Software Foundation. (2026). heapq — Heap queue algorithm. Recuperado de https://docs.python.org/3/library/heapq.html
```
