package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import modelo.Arista;
import modelo.ConstructorDeGrafos;
import modelo.Grafo;
import modelo.PresenterRegiones;
import modelo.Provincia;
import modelo.ProvinciasArgentinas;
import modelo.VistaRegiones;

public class VentanaPrincipal implements VistaRegiones {

    private JFrame frame;
    private PresenterRegiones presenter;
    private JTable tablaAristas;
    private DefaultTableModel modeloTabla;
    private List<Arista> listaAristasActual;
    private JSpinner spinnerRegiones;
    private JLabel lblEstado;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                VentanaPrincipal window = new VentanaPrincipal();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public VentanaPrincipal() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Diseñando Regiones - TP2");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new BorderLayout(10, 10));

        // PANEL SUPERIOR: TÍTULO Y CONTROLES DE CARGA
        JPanel panelSuperior = new JPanel(new BorderLayout(5, 5));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("DISEÑO DE REGIONES (ALGORITMO AGM)", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotonesCarga = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton btnRestablecerArgentina = new JButton("Cargar Mapa Argentina");
        btnRestablecerArgentina.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnRestablecerArgentina.addActionListener(e -> cargarGrafoPorDefecto());

        JButton btnCargarArchivo = new JButton("Cargar desde Archivo");
        btnCargarArchivo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnCargarArchivo.addActionListener(e -> cargarGrafoDesdeArchivo());

        panelBotonesCarga.add(btnRestablecerArgentina);
        panelBotonesCarga.add(btnCargarArchivo);
        panelSuperior.add(panelBotonesCarga, BorderLayout.SOUTH);

        frame.getContentPane().add(panelSuperior, BorderLayout.NORTH);

        // PANEL CENTRAL: TABLA DE ARISTAS Y PESOS
        JPanel panelCentral = new JPanel(new BorderLayout(5, 5));
        panelCentral.setBorder(BorderFactory.createTitledBorder("Aristas limítrofes y Similaridades (Edite el peso directamente en la tabla)"));

        modeloTabla = new DefaultTableModel(new Object[]{"Provincia Origen", "Provincia Destino", "Peso (Similaridad)"}, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Solo la columna de peso es editable
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 2 ? Integer.class : String.class;
            }
        };

        tablaAristas = new JTable(modeloTabla);
        tablaAristas.setRowHeight(22);

        // Escucha cambios en las celdas para actualizar el modelo
        modeloTabla.addTableModelListener(e -> {
            int fila = e.getFirstRow();
            int columna = e.getColumn();
            if (columna == 2 && fila >= 0 && listaAristasActual != null && fila < listaAristasActual.size()) {
                try {
                    Object valor = modeloTabla.getValueAt(fila, columna);
                    int nuevoPeso = Integer.parseInt(valor.toString());
                    if (nuevoPeso < 0) {
                        mostrarError("El peso no puede ser negativo");
                        modeloTabla.setValueAt(listaAristasActual.get(fila).getPeso(), fila, columna);
                        return;
                    }
                    presenter.actualizarPesoLuegoDeEditar(listaAristasActual.get(fila), nuevoPeso);
                } catch (NumberFormatException ex) {
                    mostrarError("El peso debe ser un número entero válido");
                    modeloTabla.setValueAt(listaAristasActual.get(fila).getPeso(), fila, columna);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaAristas);
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(panelCentral, BorderLayout.CENTER);

        // PANEL INFERIOR: CONFIGURACIÓN DE K, ESTADO Y GENERACIÓN
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JLabel lblK = new JLabel("Cantidad de Regiones (k):");
        lblK.setFont(new Font("Tahoma", Font.BOLD, 13));

        spinnerRegiones = new JSpinner(new SpinnerNumberModel(4, 1, 24, 1));
        spinnerRegiones.setFont(new Font("Tahoma", Font.PLAIN, 13));

        JButton btnGenerar = new JButton("GENERAR REGIONES");
        btnGenerar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnGenerar.setBackground(new Color(220, 240, 255));
        btnGenerar.addActionListener(e -> {
            if (tablaAristas.isEditing()) {
                tablaAristas.getCellEditor().stopCellEditing();
            }
            int k = (Integer) spinnerRegiones.getValue();
            presenter.ejecutarAlgoritmo(k);
        });

        panelAcciones.add(lblK);
        panelAcciones.add(spinnerRegiones);
        panelAcciones.add(btnGenerar);

        lblEstado = new JLabel("Listo. Asigne los pesos deseados y haga clic en Generar.", SwingConstants.CENTER);
        lblEstado.setFont(new Font("Tahoma", Font.ITALIC, 12));

        panelInferior.add(panelAcciones, BorderLayout.NORTH);
        panelInferior.add(lblEstado, BorderLayout.SOUTH);

        frame.getContentPane().add(panelInferior, BorderLayout.SOUTH);

        // Carga inicial del grafo
        cargarGrafoPorDefecto();
    }

    private void cargarGrafoPorDefecto() {
        Grafo grafo = ConstructorDeGrafos.construir(ProvinciasArgentinas.obtenerVecindadesArgentina());
        this.presenter = new PresenterRegiones(this, grafo);
        lblEstado.setText("Mapa de Argentina cargado correctamente.");
    }

    private void cargarGrafoDesdeArchivo() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar archivo de vecindades (origen,destino,peso)");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt, *.csv)", "txt", "csv"));

        int seleccion = chooser.showOpenDialog(frame);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            Grafo grafoLeido = new Grafo();
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    linea = linea.trim();
                    if (linea.isEmpty() || linea.startsWith("#")) continue;

                    String[] partes = linea.split("[,;]");
                    if (partes.length >= 2) {
                        Provincia p1 = new Provincia(partes[0].trim());
                        Provincia p2 = new Provincia(partes[1].trim());
                        int peso = (partes.length >= 3) ? Integer.parseInt(partes[2].trim()) : 1;
                        grafoLeido.agregarArista(p1, p2, peso);
                    }
                }

                if (grafoLeido.cantidadProvincias() == 0) {
                    mostrarError("El archivo no contiene relaciones válidas.");
                    return;
                }

                this.presenter = new PresenterRegiones(this, grafoLeido);
                lblEstado.setText("Grafo importado desde: " + archivo.getName());
            } catch (Exception ex) {
                mostrarError("Error al leer el archivo: " + ex.getMessage());
            }
        }
    }

    @Override
    public void mostrarAristasParaCargarPesos(List<Arista> aristas) {
        this.listaAristasActual = aristas;
        modeloTabla.setRowCount(0);

        for (Arista a : aristas) {
            modeloTabla.addRow(new Object[]{
                a.getProvinciaOrigen().getNombre(),
                a.getProvinciaDestino().getNombre(),
                a.getPeso()
            });
        }

        // Actualiza el rango del spinner al total de provincias disponibles
        if (!aristas.isEmpty()) {
            int totalProvincias = (int) aristas.stream()
                .flatMap(a -> java.util.stream.Stream.of(a.getProvinciaOrigen(), a.getProvinciaDestino()))
                .distinct().count();
            spinnerRegiones.setModel(new SpinnerNumberModel(Math.min(4, totalProvincias), 1, totalProvincias, 1));
        }
    }

    @Override
    public void mostrarRegiones(List<Set<Provincia>> regiones) {
        DialogoMostrarRegiones dialogo = new DialogoMostrarRegiones(frame, regiones);
        dialogo.setVisible(true);
    }

    @Override
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Atención", JOptionPane.ERROR_MESSAGE);
    }
}