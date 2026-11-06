package pnni.laboratorio.unidad1;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;

/** Teoremas I y II: máximo de nodos por nivel (2^i) y en un árbol de altura h (2^(h+1) − 1). */
public final class PracticaTeoremas implements Practica {

    private static final String CODIGO = """
            static int maximoNodos(int altura) {
                int suma = 0; @@ini
                for (int nivel = 0; nivel <= altura; nivel++) { @@for
                    int nodosNivel = potenciaDeDos(nivel);   // Teorema I @@nivel
                    suma = suma + nodosNivel; @@suma
                }
                int formula = potenciaDeDos(altura + 1) - 1; // Teorema II @@formula
                return formula;                              // == suma @@ret
            }
            """;

    @Override
    public Unidad unidad() {
        return Unidad.I;
    }

    @Override
    public String titulo() {
        return "Teoremas I y II";
    }

    @Override
    public String descripcion() {
        return "Cuenta nivel por nivel cuántos nodos caben como máximo en un árbol binario (2^i) "
                + "y comprueba que la suma coincide con la fórmula cerrada 2^(h+1) − 1.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(h)");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(Parametro.texto("altura", "Altura h", "3", "Entero de 0 a 5"));
    }

    @Override
    public String programaConsola() {
        return "TeoremasArbolBinario.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        int altura = entrada.entero("altura", 0, 5);
        Traza t = new Traza(new Codigo(CODIGO));
        t.var("altura", altura).var("suma", 0);
        t.imprimirLinea("Nivel  Nodos maximos  Acumulado");
        t.paso("ini", "Inicializar el acumulador",
                "suma = 0. Vamos a sumar el máximo de nodos de cada nivel, desde la raíz (nivel 0) hasta el nivel "
                        + altura + ".",
                new EscenaTeoremas(altura, -1, 0, false));

        int suma = 0;
        for (int nivel = 0; nivel <= altura; nivel++) {
            t.var("nivel", nivel);
            t.paso("for", "Nivel " + nivel + " ≤ " + altura + " → entrar al ciclo",
                    "La condición nivel <= altura es verdadera, procesamos el nivel " + nivel + ".",
                    new EscenaTeoremas(altura, nivel, suma, false));
            int nodos = 1 << nivel;
            t.var("nodosNivel", nodos);
            t.paso("nivel", "Teorema I: 2^" + nivel + " = " + nodos,
                    "Cada nivel puede tener a lo sumo el doble de nodos que el anterior: el nivel " + nivel
                            + " admite 2^" + nivel + " = " + nodos + " nodos.",
                    new EscenaTeoremas(altura, nivel, suma, false));
            suma += nodos;
            t.var("suma", suma);
            t.imprimirLinea(String.format("%5d  %13d  %9d", nivel, nodos, suma));
            t.paso("suma", "Acumular: suma = " + suma,
                    "suma = " + (suma - nodos) + " + " + nodos + " = " + suma + ". Es el máximo de nodos de un árbol de altura "
                            + nivel + ".",
                    new EscenaTeoremas(altura, nivel, suma, false));
        }
        t.var("nivel", altura + 1);
        t.paso("for", "Nivel " + (altura + 1) + " > " + altura + " → salir del ciclo",
                "Ya sumamos todos los niveles. La suma por niveles da " + suma + ".",
                new EscenaTeoremas(altura, altura + 1, suma, false));

        int formula = (1 << (altura + 1)) - 1;
        t.var("formula", formula);
        t.paso("formula", "Teorema II: 2^(" + altura + "+1) − 1 = " + formula,
                "La serie 1 + 2 + 4 + … + 2^h es geométrica y vale 2^(h+1) − 1. Con h = " + altura
                        + " obtenemos " + formula + ".",
                new EscenaTeoremas(altura, altura, suma, true));
        t.imprimirLinea("Teorema II: 2^(" + altura + "+1) - 1 = " + formula);
        t.imprimirLinea("Comprobacion (suma por niveles): " + suma + (suma == formula ? "  -> coincide" : "  -> NO coincide"));
        t.paso("ret", suma == formula ? "La fórmula coincide con la suma ✓" : "No coincide",
                "Un árbol binario de altura " + altura + " tiene como máximo " + formula
                        + " nodos; ese máximo solo se alcanza si el árbol es lleno (perfect).",
                new EscenaTeoremas(altura, altura, suma, true));
        return t;
    }
}
