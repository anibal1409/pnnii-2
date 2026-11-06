package pnni.laboratorio.unidad1;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.unidad1.EscenaHeap.Rol;

/**
 * Casos prácticos integradores 6.1 y 6.2: una cola de prioridad con heap donde se
 * insertan elementos (INSERTAR) y se atiende siempre la raíz (EXTRAER + HEAPIFY).
 */
public final class PracticaColaPrioridad implements Practica {

    private static final String CODIGO_EXTRAER = """
            static int extraerRaiz(int[] heap, int tamano) {
                System.out.println("Atendiendo: " + heap[0]); @@atender
                heap[0] = heap[tamano - 1];      // el ultimo pasa a la raiz @@mover
                tamano--; @@reducir
                heapify(heap, 0, tamano); @@llamada
                return tamano; @@retExtraer
            }

            """;

    private final String titulo;
    private final String descripcion;
    private final boolean max;
    private final int minimo;
    private final int maximo;
    private final String porDefecto;
    private final String elemento;
    private final String programa;

    private PracticaColaPrioridad(String titulo, String descripcion, boolean max, int minimo, int maximo,
                                  String porDefecto, String elemento, String programa) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.max = max;
        this.minimo = minimo;
        this.maximo = maximo;
        this.porDefecto = porDefecto;
        this.elemento = elemento;
        this.programa = programa;
    }

    public static PracticaColaPrioridad hospital() {
        return new PracticaColaPrioridad("Caso: Hospital",
                "Emergencias con códigos de gravedad 1 (crítico) a 5 (no urgente). Un Min-Heap mantiene al paciente "
                        + "más crítico en la raíz: se registra con INSERTAR y se atiende con EXTRAER + HEAPIFY.",
                false, 1, 5, "3, 5, 2, 1, 4, 2", "paciente", "HospitalPrioridades.java");
    }

    public static PracticaColaPrioridad impresora() {
        return new PracticaColaPrioridad("Caso: Cola de impresión",
                "Trabajos con prioridad 1 (baja), 2 (normal) y 3 (urgente). Un Max-Heap deja el trabajo más urgente "
                        + "en la raíz para imprimirlo primero.",
                true, 1, 3, "2, 1, 3, 2, 3", "trabajo", "ColaImpresion.java");
    }

    @Override
    public Unidad unidad() {
        return Unidad.I;
    }

    @Override
    public String titulo() {
        return titulo;
    }

    @Override
    public String descripcion() {
        return descripcion;
    }

    @Override
    public List<String> complejidad() {
        return List.of("INSERTAR O(log n)", "EXTRAER O(log n)");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(
                Parametro.texto("llegadas", "Llegadas (" + minimo + "–" + maximo + ")", porDefecto,
                        "Prioridades en orden de llegada"),
                Parametro.texto("atenciones", "Atender", "1", "Cuántos elementos extraer"));
    }

    @Override
    public String programaConsola() {
        return programa;
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        int[] llegadas = entrada.enteros("llegadas", 1, 15, minimo, maximo);
        int atenciones = entrada.entero("atenciones", 0, llegadas.length);
        String tipo = max ? "Max-Heap" : "Min-Heap";
        Traza t = new Traza(new Codigo(TrazadorHeap.adaptar(
                TrazadorHeap.CODIGO_INSERTAR + "\n" + CODIGO_EXTRAER + TrazadorHeap.CODIGO_HEAPIFY, max)));

        int[] heap = new int[llegadas.length];
        int tamano = 0;
        for (int valor : llegadas) {
            int llegada = valor;
            tamano = TrazadorHeap.insertar(t, heap, tamano, valor, max,
                    c -> c.nota("Llega " + elemento + " con prioridad " + llegada));
        }
        t.imprimirLinea(tipo + " (" + (max ? "mayor" : "menor") + " = mas urgente): " + TrazadorHeap.texto(heap, tamano));

        for (int a = 0; a < atenciones; a++) {
            int raiz = heap[0];
            t.var("tamano", tamano).quitar("valor").quitar("i").quitar("padre").quitar("temp");
            t.imprimirLinea("Atendiendo: " + raiz);
            t.paso("atender", "Atender al " + elemento + " de la raíz: " + raiz,
                    "En un " + tipo + " el elemento más urgente siempre está en heap[0], así que encontrarlo cuesta O(1).",
                    EscenaHeap.de(heap, tamano).rol(0, Rol.ACTUAL).puntero(0, "raíz").nota("Atendiendo: " + raiz).crear());
            heap[0] = heap[tamano - 1];
            t.paso("mover", "Mover el último elemento a la raíz",
                    "heap[0] = heap[" + (tamano - 1) + "] = " + heap[0] + ". Así el árbol conserva su forma casi completa, "
                            + "pero la raíz probablemente viola la propiedad de heap.",
                    EscenaHeap.de(heap, tamano).rol(0, Rol.ACTUAL).rol(tamano - 1, Rol.ATENUADO)
                            .puntero(0, "raíz").puntero(tamano - 1, "último").nota("Atendido: " + raiz).crear());
            tamano--;
            t.var("tamano", tamano);
            t.paso("reducir", "Reducir el tamaño: tamano = " + tamano,
                    "La última posición deja de pertenecer al heap.",
                    EscenaHeap.de(heap, tamano).rol(0, Rol.ACTUAL).puntero(0, "raíz").nota("Atendido: " + raiz).crear());
            if (tamano > 0) {
                t.paso("llamada", "Llamar a heapify(heap, 0, " + tamano + ")",
                        "Hundimos la nueva raíz para restaurar la propiedad de " + tipo + ".",
                        EscenaHeap.de(heap, tamano).rol(0, Rol.ACTUAL).puntero(0, "i").nota("Atendido: " + raiz).crear());
                TrazadorHeap.heapify(t, heap, 0, tamano, max, c -> c.nota("Atendido: " + raiz));
                t.quitar("izquierdo").quitar("derecho").quitar(max ? "mayor" : "menor").quitar("continuar").quitar("i");
            }
            t.imprimirLinea("Cola despues de atender: " + TrazadorHeap.texto(heap, tamano));
            t.paso("retExtraer", "Cola reorganizada ✓",
                    tamano == 0 ? "La cola quedó vacía."
                            : "El siguiente " + elemento + " en ser atendido es " + heap[0] + ", ya ubicado en la raíz.",
                    EscenaHeap.de(heap, tamano).todos(Rol.LISTO).insignia(tipo + " válido ✓", true)
                            .nota("Atendido: " + raiz).crear());
        }
        return t;
    }
}
