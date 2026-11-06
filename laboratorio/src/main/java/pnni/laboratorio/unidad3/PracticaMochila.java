package pnni.laboratorio.unidad3;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.ui.Tono;

/** Problema de la bolsa de papel (mochila fraccionaria): elección voraz por razón valor/peso. */
public final class PracticaMochila implements Practica {

    private static final String CODIGO = """
            static double mochila(int[] valores, int[] pesos, int capacidad, double[] fracciones) {
                for (int k = 0; k < n - 1; k++) {                // orden por v/w descendente @@sel
                    int mejor = k; @@sel
                    for (int j = k + 1; j < n; j++) @@selCmp
                        if (valores[orden[j]] * pesos[orden[mejor]] > valores[orden[mejor]] * pesos[orden[j]]) mejor = j; @@selCmp
                    int temp = orden[k]; orden[k] = orden[mejor]; orden[mejor] = temp; @@selSwap
                }
                int restante = capacidad; @@ini
                for (int k = 0; k < n && restante > 0; k++) { @@for
                    int i = orden[k]; @@for
                    if (pesos[i] <= restante) { @@cabe
                        fracciones[i] = 1.0; total += valores[i]; restante -= pesos[i]; @@completo
                    } else {
                        fracciones[i] = (double) restante / pesos[i]; @@fraccion
                        total += (double) valores[i] * restante / pesos[i]; restante = 0; @@fraccion
                    }
                }
                return total; @@fin
            }
            """;

    @Override
    public Unidad unidad() {
        return Unidad.III;
    }

    @Override
    public String titulo() {
        return "Bolsa de papel (mochila fraccionaria)";
    }

    @Override
    public String descripcion() {
        return "Los objetos se pueden partir. La elección voraz ordena por valor por unidad de peso (v/w) y toma cada "
                + "objeto completo mientras quepa; del primero que no cabe toma la fracción que llena la bolsa. "
                + "Ejercicio resuelto 2: valores 10, 5, 15, 7; pesos 2, 3, 5, 4; C = 15.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(n log n) ordenando", "O(n²) con selección");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(
                Parametro.texto("valores", "Valores", "60, 100, 120", "Hasta 8 enteros"),
                Parametro.texto("pesos", "Pesos", "10, 20, 30", "Uno por objeto, mayores que 0"),
                Parametro.texto("capacidad", "Capacidad C", "50", "Entero positivo"));
    }

    @Override
    public String programaConsola() {
        return "MochilaFraccionaria.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        int[] valores = entrada.enteros("valores", 1, 8, 0, 999);
        int[] pesos = entrada.enteros("pesos", 1, 8, 1, 999);
        if (pesos.length != valores.length) {
            throw entrada.error("pesos", "debe tener un peso por cada valor (" + valores.length + ")");
        }
        int capacidad = entrada.entero("capacidad", 1, 9999);
        int n = valores.length;
        int[] orden = new int[n];
        for (int i = 0; i < n; i++) {
            orden[i] = i;
        }
        double[] fracciones = new double[n];
        Traza t = new Traza(new Codigo(CODIGO));
        t.var("capacidad", capacidad);

        for (int k = 0; k < n - 1; k++) {
            int mejor = k;
            for (int j = k + 1; j < n; j++) {
                if (valores[orden[j]] * pesos[orden[mejor]] > valores[orden[mejor]] * pesos[orden[j]]) {
                    mejor = j;
                }
            }
            t.var("k", k).var("mejor", mejor);
            Tono[] tonos = EscenaMochila.tonos(n);
            for (int x = 0; x < k; x++) {
                tonos[x] = Tono.EXITO;
            }
            for (int x = k; x < n; x++) {
                tonos[x] = Tono.AVISO;
            }
            tonos[mejor] = Tono.ACENTO;
            int obj = orden[mejor];
            t.paso("selCmp", "Turno " + k + ": mayor razón restante → obj " + obj,
                    "Entre los objetos aún no ordenados, obj " + obj + " tiene la mayor razón " + valores[obj] + "/" + pesos[obj]
                            + " = " + String.format("%.2f", (double) valores[obj] / pesos[obj])
                            + ". Las razones se comparan sin dividir: v_j·w_m > v_m·w_j.",
                    new EscenaMochila(valores, pesos, orden, tonos, fracciones, capacidad, 0, "Ordenando por v/w", null));
            int temp = orden[k];
            orden[k] = orden[mejor];
            orden[mejor] = temp;
            Tono[] despues = EscenaMochila.tonos(n);
            for (int x = 0; x <= k; x++) {
                despues[x] = Tono.EXITO;
            }
            t.paso("selSwap", "obj " + obj + " pasa a la posición " + k, "El orden de consideración se va fijando de mayor a menor razón.",
                    new EscenaMochila(valores, pesos, orden, despues, fracciones, capacidad, 0, "Ordenando por v/w", null));
        }
        t.quitar("k").quitar("mejor");

        int restante = capacidad;
        double total = 0;
        t.var("restante", restante).var("total", "0.00");
        t.paso("ini", "restante = " + capacidad, "La bolsa está vacía; se llena en orden de razón descendente.",
                new EscenaMochila(valores, pesos, orden, EscenaMochila.tonos(n), fracciones, capacidad, 0, null, null));
        for (int k = 0; k < n && restante > 0; k++) {
            int i = orden[k];
            t.var("k", k).var("i", i);
            Tono[] tonos = EscenaMochila.tonos(n);
            for (int x = 0; x < k; x++) {
                tonos[x] = Tono.ATENUADO;
            }
            tonos[k] = Tono.AVISO;
            boolean cabe = pesos[i] <= restante;
            t.paso("cabe", "¿Cabe obj " + i + " completo? " + pesos[i] + " ≤ " + restante + " → " + (cabe ? "Sí" : "No"),
                    "Es el de mayor razón entre los que quedan (" + String.format("%.2f", (double) valores[i] / pesos[i]) + ").",
                    new EscenaMochila(valores, pesos, orden, tonos, fracciones, capacidad, total, null, null));
            tonos[k] = Tono.ACENTO;
            String razon = String.format("%.2f", (double) valores[i] / pesos[i]);
            if (cabe) {
                fracciones[i] = 1.0;
                total += valores[i];
                restante -= pesos[i];
                t.imprimirLinea("Tomar objeto " + i + " completo (razon " + razon + "), queda " + restante);
                t.var("restante", restante).var("total", String.format("%.2f", total));
                t.paso("completo", "Tomar obj " + i + " completo",
                        "valor += " + valores[i] + " → " + String.format("%.2f", total) + "; quedan " + restante + " de capacidad.",
                        new EscenaMochila(valores, pesos, orden, tonos, fracciones, capacidad, total, null, null));
            } else {
                fracciones[i] = (double) restante / pesos[i];
                total += (double) valores[i] * restante / pesos[i];
                t.imprimirLinea("Tomar " + restante + "/" + pesos[i] + " del objeto " + i + " (razon " + razon + "), bolsa llena");
                t.var("total", String.format("%.2f", total));
                t.paso("fraccion", "Tomar " + restante + "/" + pesos[i] + " de obj " + i,
                        "Solo caben " + restante + " unidades: fracción " + String.format("%.3f", fracciones[i]) + ", que aporta "
                                + valores[i] + "·" + restante + "/" + pesos[i] + " = " + String.format("%.2f", (double) valores[i] * restante / pesos[i])
                                + ". La bolsa queda llena.",
                        new EscenaMochila(valores, pesos, orden, tonos, fracciones, capacidad, total, null, null));
                restante = 0;
                t.var("restante", 0);
            }
        }
        t.quitar("k").quitar("i");
        t.imprimirLinea("Valor maximo obtenido: " + String.format("%.2f", total));
        StringBuilder fr = new StringBuilder("Fracciones tomadas: ");
        for (double f : fracciones) {
            fr.append(String.format("%.3f", f)).append(' ');
        }
        t.imprimirLinea(fr.toString());
        Tono[] fin = EscenaMochila.tonos(n);
        t.paso("fin", "Valor máximo: " + String.format("%.2f", total) + " ✓",
                restante > 0 ? "Todos los objetos cupieron completos y sobran " + restante + " unidades de capacidad."
                        : "Como se permiten fracciones, la bolsa queda exactamente llena con el mayor valor posible.",
                new EscenaMochila(valores, pesos, orden, fin, fracciones, capacidad, total, null,
                        "Valor = " + String.format("%.2f", total)));
        return t;
    }
}
