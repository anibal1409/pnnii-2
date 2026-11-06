package pnni.laboratorio.nucleo;

import java.util.List;
import java.util.Map;

/** Valores escritos en el formulario, con conversión y validación que reporta errores en español. */
public final class Entrada {

    private final List<Parametro> parametros;
    private final Map<String, String> valores;

    public Entrada(List<Parametro> parametros, Map<String, String> valores) {
        this.parametros = parametros;
        this.valores = valores;
    }

    public String texto(String clave) {
        return valores.getOrDefault(clave, "").trim();
    }

    public int entero(String clave, int min, int max) {
        String bruto = texto(clave);
        int valor;
        try {
            valor = Integer.parseInt(bruto);
        } catch (NumberFormatException e) {
            throw error(clave, "debe ser un número entero (escribiste \"" + bruto + "\")");
        }
        if (valor < min || valor > max) {
            throw error(clave, "debe estar entre " + min + " y " + max);
        }
        return valor;
    }

    /** Lista de enteros separados por coma o espacio. */
    public int[] enteros(String clave, int minCantidad, int maxCantidad, int minValor, int maxValor) {
        String bruto = texto(clave);
        String[] partes = bruto.isEmpty() ? new String[0] : bruto.split("[,;\\s]+");
        if (partes.length < minCantidad || partes.length > maxCantidad) {
            throw error(clave, "debe tener entre " + minCantidad + " y " + maxCantidad + " valores");
        }
        int[] numeros = new int[partes.length];
        for (int k = 0; k < partes.length; k++) {
            try {
                numeros[k] = Integer.parseInt(partes[k]);
            } catch (NumberFormatException e) {
                throw error(clave, "contiene \"" + partes[k] + "\", que no es un entero");
            }
            if (numeros[k] < minValor || numeros[k] > maxValor) {
                throw error(clave, "solo admite valores entre " + minValor + " y " + maxValor);
            }
        }
        return numeros;
    }

    public IllegalArgumentException error(String clave, String detalle) {
        String etiqueta = parametros.stream()
                .filter(p -> p.clave().equals(clave))
                .map(Parametro::etiqueta)
                .findFirst()
                .orElse(clave);
        return new IllegalArgumentException("«" + etiqueta + "» " + detalle + ".");
    }
}
