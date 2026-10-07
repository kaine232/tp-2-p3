package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.ControladorVecinos;
import modelo.Grafo;
import modelo.Provincia;
import modelo.Vecinos;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.Color;

public class IngresoManualPesoYAristas extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable vecinas;
	private JTable nombreVecinaYPeso;
	private JScrollPane panelVecinas;
	private JScrollPane panelVecinasYPeso;
	private JLabel etiquetaProvinciaActual;
	private JLabel etiquetaNombreProvincia;
	private JLabel etiquetaContador;
	private JButton botonAtras;
	private JButton botonSiguiente;
	private Vecinos vecinos;
	private ControladorVecinos controladorVecinos;
	private int nroProvincias;
	private ArrayList<String> nombresProvincias = new ArrayList<>();
	private int posicionActual;
	private boolean bufferParaTabla;
	private boolean finalizado;
	private JLabel avisoGrafoNoConexo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			IngresoManualPesoYAristas dialog = new IngresoManualPesoYAristas();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public IngresoManualPesoYAristas() {
		setModal(true);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			etiquetaProvinciaActual = new JLabel("PROVINCIA ACTUAL:");
			etiquetaProvinciaActual.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			etiquetaProvinciaActual.setBounds(20, 10, 160, 14);
			contentPanel.add(etiquetaProvinciaActual);
		}
		{
			etiquetaNombreProvincia = new JLabel("nombre_provincia");
			etiquetaNombreProvincia.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 18));
			etiquetaNombreProvincia.setBounds(181, 4, 220, 20);
			contentPanel.add(etiquetaNombreProvincia);
		}
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setBounds(22, 35, 390, 160);
		contentPanel.add(splitPane);
		
		panelVecinas = new JScrollPane();
		panelVecinas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		splitPane.setLeftComponent(panelVecinas);
		
		vecinas = new JTable();
		vecinas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"", "VECINAS"
			}
		) {
			Class[] columnTypes = new Class[] {
				Boolean.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			public boolean isCellEditable(int row, int column) {
				return column == 0;
			}
		});
		vecinas.getColumnModel().getColumn(0).setMaxWidth(25);;
		panelVecinas.setViewportView(vecinas);
		
		panelVecinasYPeso = new JScrollPane();
		panelVecinasYPeso.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		splitPane.setRightComponent(panelVecinasYPeso);
		
		nombreVecinaYPeso = new JTable();
		nombreVecinaYPeso.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"NOMBRE", "PESO"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
		});
		nombreVecinaYPeso.getColumnModel().getColumn(0).setResizable(false);
		nombreVecinaYPeso.getColumnModel().getColumn(0).setPreferredWidth(150);
		nombreVecinaYPeso.getColumnModel().getColumn(1).setResizable(false);
		nombreVecinaYPeso.getColumnModel().getColumn(1).setPreferredWidth(15);
		panelVecinasYPeso.setViewportView(nombreVecinaYPeso);
		splitPane.setDividerLocation(125);
		
		etiquetaContador = new JLabel((posicionActual + 1) + " / " + nroProvincias);
		etiquetaContador.setHorizontalAlignment(SwingConstants.CENTER);
		etiquetaContador.setFont(new Font("Verdana", Font.BOLD, 16));
		etiquetaContador.setBounds(189, 203, 56, 14);
		contentPanel.add(etiquetaContador);
		
		avisoGrafoNoConexo = new JLabel("¡El grafo no es conexo!");
		avisoGrafoNoConexo.setVisible(false);
		avisoGrafoNoConexo.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		avisoGrafoNoConexo.setForeground(new Color(255, 0, 0));
		avisoGrafoNoConexo.setBounds(261, 206, 115, 22);
		contentPanel.add(avisoGrafoNoConexo);
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(15);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				botonAtras = new JButton("BACK");
				botonAtras.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					    if (nombreVecinaYPeso.isEditing()) {
					        nombreVecinaYPeso.getCellEditor().stopCellEditing();
					    }
					    
					    if (posicionActual == 0) {
					    	dispose();
					    } else {
					    posicionActual--;
					    actContador();
					    actBotones();
					    actDatosTablaDeVecinas();
					    actDatosTablaDeVecinasYPeso();
					    }
					}
				});
				botonAtras.setActionCommand("OK");
				buttonPane.add(botonAtras);
				getRootPane().setDefaultButton(botonAtras);
			}
			{
				botonSiguiente = new JButton("NEXT");
				botonSiguiente.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					    if (nombreVecinaYPeso.isEditing()) {
					        nombreVecinaYPeso.getCellEditor().stopCellEditing();
					    }
					    
					    if (posicionActual == nroProvincias - 1) {
					    	finalizado = true;
					    	dispose();
					    } else {
					    posicionActual++;
					    actContador();
					    actBotones();
					    actDatosTablaDeVecinas();
					    actDatosTablaDeVecinasYPeso();
					    }
					}
				});
				botonSiguiente.setActionCommand("Cancel");
				buttonPane.add(botonSiguiente);
			}
		}

		final DefaultTableModel tablaIzquierdaInfo = (DefaultTableModel) vecinas.getModel();
		tablaIzquierdaInfo.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if (bufferParaTabla || e.getColumn() != 0) return;

				int fila = e.getFirstRow();
				boolean provinciaEstaSeleccionada = (Boolean) tablaIzquierdaInfo.getValueAt(fila, 0);
				String otraProvincia = (String) tablaIzquierdaInfo.getValueAt(fila, 1);
				String provinciaActual = nombresProvincias.get(posicionActual);

				if (provinciaEstaSeleccionada) {
					controladorVecinos.unir(provinciaActual, otraProvincia, 1);
				} else {
					controladorVecinos.separar(provinciaActual, otraProvincia);
				}
				actDatosTablaDeVecinasYPeso();
				actBotones();
			}
		});
		
		final DefaultTableModel tablaDerechaInfo = (DefaultTableModel) nombreVecinaYPeso.getModel();
		tablaDerechaInfo.addTableModelListener(new TableModelListener() {
		    public void tableChanged(TableModelEvent e) {
		        if (bufferParaTabla || e.getType() != TableModelEvent.UPDATE) return;

		        int fila = e.getFirstRow();
		        Object valor = tablaDerechaInfo.getValueAt(fila, 1);
		        if (valor == null) return;

		        int peso = (Integer) valor;
		        String vecina = (String) tablaDerechaInfo.getValueAt(fila, 0);
		        String actual = nombresProvincias.get(posicionActual);

		        if (peso <= 0) {
		        	tablaDerechaInfo.setValueAt(vecinos.getPeso(actual, vecina), fila, 1);
		        	return;
		        }
		        
		       controladorVecinos.unir(actual, vecina, peso);
		        actBotones();
		    }
		});
	}
	
	public void setupVentana(ArrayList<String> nombres, int datoCantProvincias) {
		cantidadProvincias(datoCantProvincias);
		setupListaNombres(nombres);
		setupVecinos();
		actContador();
		actBotones();
		actDatosTablaDeVecinas();
		actDatosTablaDeVecinasYPeso();
	}
	
	private void setupListaNombres(ArrayList<String> nombres) {
		nombresProvincias.addAll(nombres);
	}
	
	private void setupVecinos() {
		controladorVecinos = new ControladorVecinos(nombresProvincias);
		vecinos = controladorVecinos.obtenerVecinos();
	}
	
	private void cantidadProvincias(int nro) {
		this.nroProvincias = nro;
	}
	
	private void actContador() {
		etiquetaContador.setText((posicionActual + 1) + " / " + nroProvincias);
	}
	
	private void actBotones() {
		boolean esUltimaPos = (posicionActual == nroProvincias - 1);
		boolean esConexo = vecinos.esConexo();
		
		if (posicionActual == 0) {
			botonAtras.setText("CANCELAR");
		} else {
			botonAtras.setText("ATRÁS");
		}
		
		if (esUltimaPos) {
			botonSiguiente.setText("FINALIZAR");
		} else {
			botonSiguiente.setText("SIGUIENTE");
		}
		
		botonSiguiente.setEnabled(!esUltimaPos || vecinos.esConexo());
		avisoGrafoNoConexo.setVisible(esUltimaPos && !vecinos.esConexo());
		
	}
	
	private void actDatosTablaDeVecinas() {
		String provActual = nombresProvincias.get(posicionActual);
		etiquetaNombreProvincia.setText(provActual);
		
		bufferParaTabla = true;
		DefaultTableModel modelo = (DefaultTableModel) vecinas.getModel();
		modelo.setRowCount(0);
		for (String provincia : nombresProvincias) {
			if (!provincia.equals(provActual)) {
				boolean esVecina = vecinos.sonVecinas(provActual, provincia);
				modelo.addRow(new Object[] { esVecina, provincia });
			}
		}
		bufferParaTabla = false;
	}
	
	private void actDatosTablaDeVecinasYPeso() {
		String provActual = nombresProvincias.get(posicionActual);
		
		DefaultTableModel modelo = (DefaultTableModel) nombreVecinaYPeso.getModel();
		modelo.setRowCount(0);
		bufferParaTabla = true;
		for (Map.Entry<String, Integer> vecina : vecinos.getVecinas(provActual).entrySet()) {
			modelo.addRow(new Object[] { vecina.getKey(), vecina.getValue() });
		}
		bufferParaTabla = false;
	}

	public boolean finalizoConExito() {
		return finalizado;
	}
	
	public Grafo obtenerGrafo() {
		return controladorVecinos.obtenerGrafo();
	}
}