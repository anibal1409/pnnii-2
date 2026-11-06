# Unidad I: erratas detectadas en el material

Todas se verificaron ejecutando el código del material. Los programas de `consola/unidad1/` ya usan la versión corregida.

| Sección | Problema en el material | Corrección aplicada |
|---|---|---|
| 4.4 `ArbolHeap` | Comprueba la raíz con `if (padre >= 0)`, pero en Java `(0 - 1) / 2` da `0` (la división entera trunca hacia cero), así que la raíz se reportaría como su propio padre. | Se pregunta `if (i == 0)`. Programa: `NavegacionPadreHijo.java`. |
| 5.1 `InsertarHeap` | Inserta 50, 30 y 70 sin flotar el 70, así que `[50, 30, 70]` no es un Max-Heap. La salida indicada (`90 70 30 50`) tampoco es lo que imprime ese código (`90 50 70 30`). | Se flota después de cada inserción. La salida correcta es `90 70 50 30`. Programa: `InsertarMaxHeap.java`. |
| 5.2 `Heapify` | La entrada `{10, 50, 30, 70, 80, 60, 40}` no cumple la precondición de HEAPIFY (los subárboles de la raíz deben ser heaps). El código imprime `50 80 30 70 10 60 40`, no `80 70 60 10 50 30 40`. | Se usa la entrada `{10, 80, 60, 70, 50, 30, 40}`, que sí cumple la precondición y produce la salida indicada. Programa: `HeapifyMaxHeap.java`. |
| 6.1 `HospitalPrioridades` | La salida indicada para el heap (`1 2 2 3 4 5`) es incorrecta. | La salida correcta es `1 2 2 5 4 3`; tras atender queda `2 3 2 5 4`. |
| 6.2 `ColaImpresion` | Al insertar el segundo 2 dice "padre 2 = 2, no se intercambia", pero su padre (índice 1) vale 1, así que sí debe subir. El código mostrado imprimiría `3 3 2 2 1`. | Se flota cada inserción con un método `insertar`. La salida correcta es `3 3 2 1 2`. |
| General | El identificador `tamaño` usa la letra ñ. Con JDK menor que 18 en Windows (codificación por defecto cp1252), un archivo UTF-8 no compila. | Los identificadores usan solo ASCII (`tamano`). Las tildes se mantienen en los comentarios. |
