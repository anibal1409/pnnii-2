package pnni.laboratorio.unidad3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.ui.Arbol;
import pnni.laboratorio.ui.Tono;

/** Modelos óptimos de intercalación: fusionar siempre los dos archivos más pequeños (Huffman). */
public final class PracticaIntercalacion implements Practica {

    private static final String CODIGO = """
            static int costoMinimoFusion(int[] tamanos) {
                for (int i = 0; i < tamanos.length; i++) @@cargar
                    n = insertar(heap, n, tamanos[i]);          // Min-Heap (Unidad I) @@cargar
                while (n > 1) { @@while
                    int a = heap[0]; n = extraerMinimo(heap, n); @@a
                    int b = heap[0]; n = extraerMinimo(heap, n); @@b
                    int costo = a + b; costoTotal += costo; @@fusion
                    n = insertar(heap, n, costo); @@insertar
                }
                return costoTotal; @@fin
            }
            """;

    @Override
    public Unidad unidad() {
        return Unidad.III;
    }

    @Override
    public String titulo() {
        return "Intercalación óptima";
    }

    @Override
    public String descripcion() {
        return "Fusionar dos archivos de tamaños a y b cuesta a + b. La elección voraz (la misma de Huffman) fusiona "
                + "siempre los dos más pequeños, que se obtienen de un Min-Heap. Los archivos pequeños quedan más "
                + "profundos en el árbol, porque se vuelven a copiar en cada fusión. Caso 7.1: 15, 8, 23, 10, 7.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(n log n)", "n − 1 fusiones");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(Parametro.texto("tamanos", "Tamaños", "4, 3, 2, 6", "Entre 2 y 10 enteros positivos"));
    }

    @Override
    public String programaConsola() {
        return "IntercalacionOptima.java";
    }

    /** Bosque de fusiones mutable; cada raíz corresponde a un archivo que sigue en el heap. */
    private static final class Bosque {
        final List<Integer> valores = new ArrayList<>();
        final List<Integer> padres = new ArrayList<>();
        final List<Tono> tonos = new ArrayList<>();

        int agregar(int valor, Tono tono) {
            valores.add(valor);
            padres.add(-1);
            tonos.add(tono);
            return valores.size() - 1;
        }

        /** La raíz más antigua con ese valor (con valores repetidos cualquiera da el mismo costo). */
        int raiz(int valor, int excepto) {
            for (int k = 0; k < valores.size(); k++) {
                if (padres.get(k) == -1 && valores.get(k) == valor && k != excepto) {
                    return k;
                }
            }
            throw new IllegalStateException("No hay raíz con valor " + valor);
        }

        void normalizar() {
            for (int k = 0; k < tonos.size(); k++) {
                if (tonos.get(k) == Tono.ACENTO || tonos.get(k) == Tono.AVISO) {
                    tonos.set(k, padres.get(k) == -1 ? Tono.NORMAL : Tono.INFO);
                }
            }
        }

        List<Arbol.Nodo> foto() {
            List<Arbol.Nodo> nodos = new ArrayList<>();
            for (int k = 0; k < valores.size(); k++) {
                nodos.add(new Arbol.Nodo(String.valueOf(valores.get(k)), "", padres.get(k), tonos.get(k)));
            }
            return nodos;
        }
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        int[] tamanos = entrada.enteros("tamanos", 2, 10, 1, 999);
        Traza t = new Traza(new Codigo(CODIGO));
        t.imprimirLinea("Tamanos iniciales: " + texto(tamanos));
        int[] heap = new int[tamanos.length];
        int n = 0;
        Bosque bosque = new Bosque();
        for (int tamano : tamanos) {
            n = insertar(heap, n, tamano);
            bosque.agregar(tamano, Tono.NORMAL);
            t.var("n", n).var("heap", Arrays.toString(Arrays.copyOf(heap, n)));
            t.paso("cargar", "INSERTAR " + tamano + " en el Min-Heap",
                    "Se coloca al final y flota mientras su padre sea mayor: el más pequeño queda siempre en heap[0].",
                    new EscenaFusion(bosque.foto(), heap, n, indiceDe(heap, n, tamano), "0", null, null));
        }
        int costoTotal = 0;
        t.var("costoTotal", 0);
        while (true) {
            boolean sigue = n > 1;
            t.paso("while", "¿Quedan al menos dos archivos? n = " + n + " → " + (sigue ? "Sí" : "No"),
                    sigue ? "Hay que seguir fusionando." : "Queda un único archivo con todos los datos.",
                    new EscenaFusion(bosque.foto(), heap, n, -1, String.valueOf(costoTotal), null, null));
            if (!sigue) {
                break;
            }
            int a = heap[0];
            n = extraerMinimo(heap, n);
            int ra = bosque.raiz(a, -1);
            bosque.tonos.set(ra, Tono.AVISO);
            t.var("a", a).var("n", n).var("heap", Arrays.toString(Arrays.copyOf(heap, n)));
            t.paso("a", "a = " + a + " (el menor)", "Se extrae la raíz del heap y se reacomoda con HEAPIFY.",
                    new EscenaFusion(bosque.foto(), heap, n, -1, String.valueOf(costoTotal), null, null));
            int b = heap[0];
            n = extraerMinimo(heap, n);
            int rb = bosque.raiz(b, ra);
            bosque.tonos.set(rb, Tono.AVISO);
            t.var("b", b).var("n", n).var("heap", Arrays.toString(Arrays.copyOf(heap, n)));
            t.paso("b", "b = " + b + " (el siguiente menor)", "Elección voraz: " + a + " y " + b
                            + " son los dos archivos más pequeños que quedan.",
                    new EscenaFusion(bosque.foto(), heap, n, -1, String.valueOf(costoTotal), null, null));
            int costo = a + b;
            costoTotal += costo;
            int nuevo = bosque.agregar(costo, Tono.ACENTO);
            bosque.padres.set(ra, nuevo);
            bosque.padres.set(rb, nuevo);
            t.var("costo", costo).var("costoTotal", costoTotal);
            t.imprimirLinea("Fusionar " + a + " y " + b + " -> costo parcial " + costo + ", acumulado " + costoTotal);
            t.paso("fusion", "Fusionar " + a + " + " + b + " = " + costo,
                    "Leer ambos archivos y escribir el resultado cuesta " + costo + ". Acumulado: " + costoTotal + ".",
                    new EscenaFusion(bosque.foto(), heap, n, -1, String.valueOf(costoTotal), null, null));
            n = insertar(heap, n, costo);
            t.var("n", n).var("heap", Arrays.toString(Arrays.copyOf(heap, n)));
            t.paso("insertar", "Devolver " + costo + " al heap", "El archivo fusionado vuelve a competir con los demás.",
                    new EscenaFusion(bosque.foto(), heap, n, indiceDe(heap, n, costo), String.valueOf(costoTotal), null, null));
            bosque.normalizar();
        }
        t.quitar("a").quitar("b").quitar("costo");
        t.imprimirLinea("Costo total minimo de fusion: " + costoTotal);
        bosque.tonos.set(bosque.valores.size() - 1, Tono.EXITO);
        t.paso("fin", "Costo mínimo de fusión: " + costoTotal + " ✓",
                "Es la suma de los nodos internos del árbol, que equivale a cada tamaño inicial multiplicado por su profundidad.",
                new EscenaFusion(bosque.foto(), heap, n, -1, String.valueOf(costoTotal), null, "Costo = " + costoTotal));
        return t;
    }

    private static int indiceDe(int[] heap, int n, int valor) {
        for (int k = n - 1; k >= 0; k--) {
            if (heap[k] == valor) {
                return k;
            }
        }
        return -1;
    }

    private static int insertar(int[] heap, int n, int valor) {
        heap[n] = valor;
        int i = n;
        while (i > 0 && heap[(i - 1) / 2] > heap[i]) {
            int padre = (i - 1) / 2;
            int temp = heap[padre];
            heap[padre] = heap[i];
            heap[i] = temp;
            i = padre;
        }
        return n + 1;
    }

    private static int extraerMinimo(int[] heap, int n) {
        heap[0] = heap[n - 1];
        n = n - 1;
        int i = 0;
        while (true) {
            int izquierdo = 2 * i + 1;
            int derecho = 2 * i + 2;
            int menor = i;
            if (izquierdo < n && heap[izquierdo] < heap[menor]) {
                menor = izquierdo;
            }
            if (derecho < n && heap[derecho] < heap[menor]) {
                menor = derecho;
            }
            if (menor == i) {
                return n;
            }
            int temp = heap[i];
            heap[i] = heap[menor];
            heap[menor] = temp;
            i = menor;
        }
    }

    private static String texto(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int v : arr) {
            sb.append(v).append(' ');
        }
        return sb.toString();
    }
}
