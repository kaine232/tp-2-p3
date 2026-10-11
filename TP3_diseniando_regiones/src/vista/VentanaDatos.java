package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.ControladorRegiones;

public class VentanaDatos extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTable tabla;
	private DefaultTableModel modelo;
	private ControladorRegiones controlador;

	public VentanaDatos(JFrame padre, ControladorRegiones controlador) {
		super(padre, "Datos cargados", true);
		this.controlador = controlador;
		setSize(450, 350);
		setResizable(false);
		setLocationRelativeTo(padre);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		modelo = new DefaultTableModel(new String[] { "PROVINCIA", "VECINA", "PESO" }, 0) {
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int columna) {
				return columna == 2 ? Integer.class : String.class;
			}

			public boolean isCellEditable(int fila, int columna) {
				return columna == 2;
			}
		};
		for (Object[] union : controlador.obtenerUniones()) {
			modelo.addRow(union);
		}
		tabla = new JTable(modelo);
		getContentPane().add(new JScrollPane(tabla), BorderLayout.CENTER);

		JButton botonGuardar = new JButton("GUARDAR");
		botonGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardar();
			}
		});
		JButton botonCerrar = new JButton("CERRAR");
		botonCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
		botones.add(botonGuardar);
		botones.add(botonCerrar);
		getContentPane().add(botones, BorderLayout.SOUTH);
	}

	private void guardar() {
		if (tabla.isEditing()) {
			tabla.getCellEditor().stopCellEditing();
		}
		for (int fila = 0; fila < modelo.getRowCount(); fila++) {
			Object peso = modelo.getValueAt(fila, 2);
			if (peso == null || (Integer) peso <= 0) {
				JOptionPane.showMessageDialog(this, "Todos los pesos deben ser enteros mayores a 0.");
				return;
			}
		}
		for (int fila = 0; fila < modelo.getRowCount(); fila++) {
			controlador.actualizarPeso((String) modelo.getValueAt(fila, 0),
					(String) modelo.getValueAt(fila, 1), (Integer) modelo.getValueAt(fila, 2));
		}
		dispose();
	}
}