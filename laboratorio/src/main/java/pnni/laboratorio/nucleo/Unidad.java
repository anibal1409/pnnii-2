package pnni.laboratorio.nucleo;

/** Unidades del programa analítico 4703337 (Programación No Numérica II). */
public enum Unidad {
    I("I", "Estructuras de datos elementales", "Árboles, teoremas y heaps"),
    II("II", "Divide y conquista", "Búsqueda binaria, máx/mín, mergesort, quicksort"),
    III("III", "Método de adquisición", "Estrategia voraz de optimización"),
    IV("IV", "Programación dinámica", "Multietapa, ABB óptimos, vendedor viajero"),
    V("V", "Ir hacia atrás", "N reinas y suma de subconjuntos");

    private final String romano;
    private final String titulo;
    private final String resumen;

    Unidad(String romano, String titulo, String resumen) {
        this.romano = romano;
        this.titulo = titulo;
        this.resumen = resumen;
    }

    public String romano() {
        return romano;
    }

    public String titulo() {
        return titulo;
    }

    public String resumen() {
        return resumen;
    }

    /** Carpeta de los programas de consola: consola/unidadN. */
    public String carpetaConsola() {
        return "unidad" + (ordinal() + 1);
    }
}
