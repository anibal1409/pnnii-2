package pnni.laboratorio.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;

import pnni.laboratorio.nucleo.Catalogo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Paso;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.ui.Iconos.Forma;

/**
 * Ventana del laboratorio. Distribución: navegación a la izquierda; a la derecha
 * encabezado, datos de entrada, visualización + explicación, código + inspector y
 * el reproductor de pasos.
 */
public class VentanaPrincipal extends JFrame {

    private final List<Practica> practicas = Catalogo.practicas();
    private final BarraLateral barra = new BarraLateral(practicas, this::seleccionar);

    private final Insignia unidad = new Insignia("", Paleta::acento);
    private final JLabel titulo = new JLabel();
    private final JLabel descripcion = new JLabel();
    private final JPanel complejidades = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
    private final JButton botonTema = new JButton();

    private final FormularioParametros formulario = new FormularioParametros();
    private final LienzoEscena lienzo = new LienzoEscena();
    private final JLabel tituloPaso = new JLabel();
    private final Insignia lineasPaso = new Insignia("", Paleta::info);
    private final JTextArea explicacion = areaTexto(false);

    private final VisorCodigo visorAlgoritmo = new VisorCodigo();
    private final VisorCodigo visorConsola = new VisorCodigo();
    private final DefaultTableModel modeloVariables = new DefaultTableModel(new Object[] {"Variable", "Valor"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tablaVariables = new JTable(modeloVariables);
    private final JTextArea salida = areaTexto(true);
    private final Reproductor reproductor = new Reproductor();

    private Practica practica;
    private List<Paso> pasos = List.of();
    private Map<String, String> variablesAnteriores = Map.of();

    public VentanaPrincipal() {
        super("PNN II · Laboratorio de algoritmos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1180, 760));
        setSize(new Dimension(1500, 920));
        setLocationRelativeTo(null);
        java.net.URL icono = getClass().getResource("/icono.png");
        if (icono != null) {
            setIconImage(new javax.swing.ImageIcon(icono).getImage());
        }

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.add(barra, BorderLayout.WEST);
        raiz.add(crearContenido(), BorderLayout.CENTER);
        setContentPane(raiz);

        formulario.setAlEjecutar(this::ejecutar);
        reproductor.setOyente(this::mostrarPaso);
        registrarAtajos();
        seleccionar(practicas.get(0));
    }

    private JComponent crearContenido() {
        JPanel contenido = new JPanel(new BorderLayout(0, 14));
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 22, 18, 22));

        JPanel superior = new JPanel(new BorderLayout(0, 14));
        superior.setOpaque(false);
        superior.add(crearEncabezado(), BorderLayout.NORTH);
        superior.add(new Tarjeta("Datos de entrada", formulario), BorderLayout.CENTER);
        contenido.add(superior, BorderLayout.NORTH);

        JPanel columnaVisual = new JPanel(new BorderLayout(0, 14));
        columnaVisual.setOpaque(false);
        columnaVisual.add(new Tarjeta("Visualización", lienzo), BorderLayout.CENTER);
        columnaVisual.add(crearExplicacion(), BorderLayout.SOUTH);

        JSplitPane columnaCodigo = new JSplitPane(JSplitPane.VERTICAL_SPLIT, crearCodigo(), crearInspector());
        estilizar(columnaCodigo, 0.62);

        // Tamaños preferidos iguales: así resizeWeight decide el reparto y el código no acapara el ancho.
        columnaVisual.setPreferredSize(new Dimension(100, 100));
        columnaCodigo.setPreferredSize(new Dimension(100, 100));
        columnaVisual.setMinimumSize(new Dimension(420, 300));
        columnaCodigo.setMinimumSize(new Dimension(380, 300));
        JSplitPane centro = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, columnaVisual, columnaCodigo);
        estilizar(centro, 0.55);
        contenido.add(centro, BorderLayout.CENTER);
        contenido.add(new Tarjeta(null, reproductor), BorderLayout.SOUTH);
        return contenido;
    }

    private JComponent crearEncabezado() {
        titulo.setFont(Tema.fuente(Font.BOLD, 24f));
        descripcion.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");
        descripcion.setFont(Tema.fuente(Font.PLAIN, 13.5f));

        JPanel izquierda = new JPanel();
        izquierda.setOpaque(false);
        izquierda.setLayout(new BoxLayout(izquierda, BoxLayout.Y_AXIS));
        unidad.setAlignmentX(Component.LEFT_ALIGNMENT);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        descripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        izquierda.add(unidad);
        izquierda.add(Box.createVerticalStrut(8));
        izquierda.add(titulo);
        izquierda.add(Box.createVerticalStrut(4));
        izquierda.add(descripcion);

        botonTema.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON);
        botonTema.setFocusable(false);
        botonTema.setPreferredSize(new Dimension(38, 38));
        botonTema.addActionListener(e -> alternarTema());
        actualizarBotonTema();

        complejidades.setOpaque(false);
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        derecha.setOpaque(false);
        derecha.add(complejidades);
        derecha.add(botonTema);

        JPanel encabezado = new JPanel(new BorderLayout(20, 0));
        encabezado.setOpaque(false);
        encabezado.add(izquierda, BorderLayout.CENTER);
        encabezado.add(derecha, BorderLayout.EAST);
        return encabezado;
    }

    private JComponent crearExplicacion() {
        tituloPaso.setFont(Tema.fuente(Font.BOLD, 15f));
        explicacion.setFont(Tema.fuente(Font.PLAIN, 13.5f));
        explicacion.setRows(3);

        JPanel cabecera = new JPanel(new BorderLayout(10, 0));
        cabecera.setOpaque(false);
        cabecera.add(tituloPaso, BorderLayout.CENTER);
        cabecera.add(lineasPaso, BorderLayout.EAST);

        JPanel cuerpo = new JPanel(new BorderLayout(0, 8));
        cuerpo.setOpaque(false);
        cuerpo.add(cabecera, BorderLayout.NORTH);
        cuerpo.add(explicacion, BorderLayout.CENTER);
        Tarjeta tarjeta = new Tarjeta("¿Qué está pasando?", cuerpo);
        tarjeta.setPreferredSize(new Dimension(0, 172));
        return tarjeta;
    }

    private JComponent crearCodigo() {
        JTabbedPane pestanas = pestanas();
        pestanas.addTab("Algoritmo en ejecución", desplazable(visorAlgoritmo));
        pestanas.addTab("Programa de consola", desplazable(visorConsola));
        return new Tarjeta(null, pestanas);
    }

    private JComponent crearInspector() {
        tablaVariables.setOpaque(false);
        tablaVariables.setShowGrid(false);
        tablaVariables.setRowHeight(28);
        tablaVariables.setFocusable(false);
        tablaVariables.setRowSelectionAllowed(false);
        tablaVariables.getTableHeader().setReorderingAllowed(false);
        tablaVariables.setDefaultRenderer(Object.class, new RenderVariable());
        tablaVariables.setTableHeader(null);
        tablaVariables.getColumnModel().getColumn(0).setPreferredWidth(140);
        tablaVariables.getColumnModel().getColumn(1).setPreferredWidth(320);
        JScrollPane scrollTabla = desplazable(tablaVariables);

        JTabbedPane pestanas = pestanas();
        pestanas.addTab("Variables", scrollTabla);
        pestanas.addTab("Salida de consola", desplazable(salida));
        return new Tarjeta(null, pestanas);
    }

    // ------------------------------------------------------------------ flujo

    private void seleccionar(Practica nueva) {
        if (nueva == practica) {
            return;
        }
        practica = nueva;
        barra.marcar(nueva);
        unidad.setText("UNIDAD " + nueva.unidad().romano() + "  ·  " + nueva.unidad().titulo().toUpperCase());
        titulo.setText(nueva.titulo());
        descripcion.setText("<html><div style='width:640px'>" + nueva.descripcion() + "</div></html>");
        complejidades.removeAll();
        for (String c : nueva.complejidad()) {
            complejidades.add(new Insignia(c, Paleta::exito));
        }
        complejidades.revalidate();
        visorConsola.setCodigo(leerProgramaConsola(nueva).lines().toList());
        formulario.cargar(nueva.parametros());
        ejecutar();
    }

    private void ejecutar() {
        try {
            Traza traza = practica.ejecutar(new Entrada(practica.parametros(), formulario.valores()));
            formulario.limpiarError();
            pasos = traza.pasos();
            variablesAnteriores = Map.of();
            visorAlgoritmo.setCodigo(traza.codigo().lineas());
            reproductor.cargar(pasos.size());
        } catch (IllegalArgumentException ex) {
            formulario.mostrarError(ex.getMessage());
        }
    }

    private void mostrarPaso(int indice, boolean animar) {
        Paso paso = pasos.get(indice);
        lienzo.mostrar(paso.escena(), animar);
        visorAlgoritmo.setActivas(paso.lineas());
        tituloPaso.setText(paso.titulo());
        explicacion.setText(paso.explicacion());
        explicacion.setCaretPosition(0);
        int[] l = paso.lineas();
        lineasPaso.setText(l.length == 1 ? "Línea " + (l[0] + 1)
                : "Líneas " + (l[0] + 1) + "–" + (l[l.length - 1] + 1));

        variablesAnteriores = indice > 0 ? pasos.get(indice - 1).variables() : Map.of();
        modeloVariables.setRowCount(0);
        paso.variables().forEach((nombre, valor) -> modeloVariables.addRow(new Object[] {nombre, valor}));
        salida.setText(paso.salida());
    }

    private String leerProgramaConsola(Practica p) {
        String ruta = "/consola/" + p.unidad().carpetaConsola() + "/" + p.programaConsola();
        try (InputStream in = getClass().getResourceAsStream(ruta)) {
            if (in == null) {
                return "// No se encontró " + ruta.substring(1);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "// Error al leer " + ruta + ": " + e.getMessage();
        }
    }

    private void alternarTema() {
        Tema.aplicar(!FlatLaf.isLafDark());
        actualizarBotonTema();
        repaint();
    }

    private void actualizarBotonTema() {
        boolean oscuro = Paleta.actual().oscura();
        botonTema.setIcon(Iconos.de(oscuro ? Forma.SOL : Forma.LUNA, 20));
        botonTema.setToolTipText(oscuro ? "Cambiar a tema claro" : "Cambiar a tema oscuro");
    }

    private void registrarAtajos() {
        atajo(KeyEvent.VK_SPACE, "reproducir", reproductor::alternar, true);
        atajo(KeyEvent.VK_RIGHT, "siguiente", reproductor::siguiente, true);
        atajo(KeyEvent.VK_LEFT, "anterior", reproductor::anterior, true);
        atajo(KeyEvent.VK_HOME, "inicio", reproductor::primero, true);
        atajo(KeyEvent.VK_END, "fin", reproductor::ultimo, true);
        atajo(KeyEvent.VK_F5, "ejecutar", this::ejecutar, false);
    }

    /** Si {@code respetarTexto}, el atajo no actúa mientras se escribe en un campo de texto. */
    private void atajo(int tecla, String nombre, Runnable accion, boolean respetarTexto) {
        JComponent raiz = getRootPane();
        raiz.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(tecla, 0), nombre);
        raiz.getActionMap().put(nombre, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component foco = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
                if (respetarTexto && foco instanceof JTextComponent texto && texto.isEditable()) {
                    return;
                }
                accion.run();
            }
        });
    }

    // --------------------------------------------------------------- utilidades

    private static void estilizar(JSplitPane division, double peso) {
        division.setResizeWeight(peso);
        division.setBorder(null);
        division.setOpaque(false);
        division.setDividerSize(14);
        division.setContinuousLayout(true);
    }

    private static JTabbedPane pestanas() {
        JTabbedPane pestanas = new JTabbedPane() {
            @Override
            public java.awt.Color getBackground() {
                return Paleta.actual().superficie();
            }
        };
        pestanas.setOpaque(false);
        pestanas.setFocusable(false);
        pestanas.putClientProperty(FlatClientProperties.STYLE,
                "tabAreaInsets:0,2,8,2; tabInsets:4,12,4,12; tabSelectionHeight:2; contentSeparatorHeight:0;"
                        + "font:bold -1");
        return pestanas;
    }

    private static JScrollPane desplazable(JComponent componente) {
        JScrollPane scroll = new JScrollPane(componente);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        return scroll;
    }

    private static JTextArea areaTexto(boolean monoespaciada) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(!monoespaciada);
        area.setWrapStyleWord(true);
        area.setOpaque(false);
        area.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        if (monoespaciada) {
            area.setFont(Tema.mono(13f));
        }
        return area;
    }

    /** Pinta el nombre en gris y resalta las variables que cambiaron respecto al paso anterior. */
    private final class RenderVariable extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable tabla, Object valor, boolean sel, boolean foco,
                                                       int fila, int columna) {
            super.getTableCellRendererComponent(tabla, valor, false, false, fila, columna);
            Paleta p = Paleta.actual();
            String nombre = String.valueOf(tabla.getValueAt(fila, 0));
            String actual = String.valueOf(tabla.getValueAt(fila, 1));
            boolean cambio = !actual.equals(variablesAnteriores.get(nombre));
            setFont(Tema.mono(13f));
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            setOpaque(cambio);
            setBackground(Paleta.alfa(p.aviso(), p.oscura() ? 0.13 : 0.12));
            setForeground(columna == 0 ? p.textoSuave() : cambio ? p.aviso() : p.texto());
            return this;
        }
    }
}
