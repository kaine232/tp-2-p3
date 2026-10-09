package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import controlador.ControladorRegiones;
import controlador.ControladorVecinos;
import controlador.VistaRegiones;
import modelo.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class VentanaPrincipal implements VistaRegiones{

	private JFrame frame;
	private JTextField cantidadDeProvinciasManual;
	private ControladorRegiones controladorRegiones;
	private int manualNumeroProvincias;
	private JButton botonManual;
	private JButton botonArchivo;
	private JButton botonGenerar;
	private JTextField cantidadDeProvinciasArchivo;
	private JTextField cantidadDeRegionesDeseadas;
	private JLabel avisoFaltanDatos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 516, 489);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		botonManual = new JButton("MANUAL");
		botonManual.setEnabled(false);
		botonManual.setFont(new Font("Verdana", Font.PLAIN, 16));
		botonManual.setBounds(75, 50, 150, 100);
		botonManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonManual.setEnabled(false);
				cantidadDeProvinciasManual.setEnabled(false);
				botonArchivo.setEnabled(false);
				
				IngresoManualNombres ventanaIngresoNombres = new IngresoManualNombres();
				ventanaIngresoNombres.setupVentana(manualNumeroProvincias);
				ventanaIngresoNombres.setVisible(true);
				
				ControladorVecinos controladorVecinos = ventanaIngresoNombres.obtenerControlador();
				if (controladorVecinos != null) {
					controladorRegiones = new ControladorRegiones(VentanaPrincipal.this, controladorVecinos);
					botonGenerar.setEnabled(true);
					avisoFaltanDatos.setVisible(false);
					cantidadDeProvinciasArchivo.setText("");
				}
				
				botonManual.setEnabled(true);
				cantidadDeProvinciasManual.setEnabled(true);
				botonArchivo.setEnabled(true);
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(botonManual);
		
		botonArchivo = new JButton("ARCHIVO");
		botonArchivo.setFont(new Font("Verdana", Font.PLAIN, 16));
		botonArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selector = new JFileChooser();
				if (selector.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
					return;   // el usuario canceló
				}
				try {
					controladorRegiones = ControladorRegiones.desdeArchivo(VentanaPrincipal.this, selector.getSelectedFile());
					botonGenerar.setEnabled(true);
					avisoFaltanDatos.setVisible(false);
					cantidadDeProvinciasArchivo.setText(String.valueOf(controladorRegiones.obtenerCantidadProvincias()));
				} catch (IOException ex) {
					mostrarError("No se pudo leer el archivo.");
				} catch (IllegalArgumentException ex) {
					mostrarError(ex.getMessage());
				}
			}
		});
		botonArchivo.setBounds(275, 50, 150, 100);
		frame.getContentPane().add(botonArchivo);
		
		JLabel etiquetaIngDat = new JLabel("INGRESO DE DATOS");
		etiquetaIngDat.setFont(new Font("Times New Roman", Font.BOLD, 16));
		etiquetaIngDat.setBounds(171, 15, 157, 23);
		frame.getContentPane().add(etiquetaIngDat);
		
		botonGenerar = new JButton("GENERAR");
		botonGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int cantidad = Integer.parseInt(cantidadDeRegionesDeseadas.getText().trim());
					controladorRegiones.ejecutarAlgoritmo(cantidad);
				} catch (NumberFormatException ex) {
					mostrarError("Ingrese un número entero de regiones");
				}
			}
		});
		botonGenerar.setEnabled(false);
		botonGenerar.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		botonGenerar.setBounds(150, 300, 200, 100);
		frame.getContentPane().add(botonGenerar);
		
		cantidadDeProvinciasManual = new JTextField();
		cantidadDeProvinciasManual.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					manualNumeroProvincias = Integer.parseInt(cantidadDeProvinciasManual.getText().trim());
					botonManual.setEnabled(manualNumeroProvincias > 0);
				} catch (NumberFormatException ex) {
					botonManual.setEnabled(false);
				}
			}
		});
		cantidadDeProvinciasManual.setBounds(112, 161, 76, 20);
		frame.getContentPane().add(cantidadDeProvinciasManual);
		cantidadDeProvinciasManual.setColumns(10);
		
		JLabel etiquetaCantProvinciasManual = new JLabel("Cantidad Provincias");
		etiquetaCantProvinciasManual.setFont(new Font("Tahoma", Font.BOLD, 10));
		etiquetaCantProvinciasManual.setBounds(99, 185, 99, 14);
		frame.getContentPane().add(etiquetaCantProvinciasManual);
		
		cantidadDeProvinciasArchivo = new JTextField();
		cantidadDeProvinciasArchivo.setEditable(false);
		cantidadDeProvinciasArchivo.setColumns(10);
		cantidadDeProvinciasArchivo.setBounds(312, 161, 76, 20);
		frame.getContentPane().add(cantidadDeProvinciasArchivo);
		
		JLabel etiquetaCantProvinciasArchivo = new JLabel("Cantidad Provincias");
		etiquetaCantProvinciasArchivo.setFont(new Font("Tahoma", Font.BOLD, 10));
		etiquetaCantProvinciasArchivo.setBounds(301, 185, 99, 14);
		frame.getContentPane().add(etiquetaCantProvinciasArchivo);
		
		avisoFaltanDatos = new JLabel("¡Faltan datos por ingresar!");
		avisoFaltanDatos.setForeground(new Color(255, 0, 0));
		avisoFaltanDatos.setHorizontalAlignment(SwingConstants.CENTER);
		avisoFaltanDatos.setBounds(150, 411, 200, 14);
		frame.getContentPane().add(avisoFaltanDatos);
		
		cantidadDeRegionesDeseadas = new JTextField();
		cantidadDeRegionesDeseadas.setBounds(207, 270, 86, 20);
		frame.getContentPane().add(cantidadDeRegionesDeseadas);
		cantidadDeRegionesDeseadas.setColumns(10);
		
		JLabel etiquetaRegiones = new JLabel("REGIONES");
		etiquetaRegiones.setFont(new Font("Tahoma", Font.BOLD, 10));
		etiquetaRegiones.setBounds(224, 252, 52, 14);
		frame.getContentPane().add(etiquetaRegiones);
	} 

	@Override
	public void mostrarRegiones(List<Set<Provincia>> regiones) {
		String texto = "";
		for (int i = 0; i < regiones.size(); i++) {
			texto += "Región " + (i + 1) + ": " + regiones.get(i) + "\n";
		}
		
		JTextArea area = new JTextArea(texto);
		area.setEditable(false);
		JScrollPane scroll = new JScrollPane(area);
		scroll.setPreferredSize(new Dimension(350, 200));
		
		JOptionPane.showMessageDialog(frame, scroll, "Regiones", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
