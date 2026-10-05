package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import modelo.*;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaPrincipal implements VistaRegiones{

	private JFrame frame;
	private JTextField cantidadDeProvinciasManual;
	private PresenterRegiones presenter;
	private int manualNumeroProvincias;
	private JButton botonManual;
	private JButton botonArchivo;
	private JButton botonGenerar;
	private JTextField cantidadDeProvinciasArchivo;
	private JTextField cantidadDeRegionesDeseadas;

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
				ventanaIngresoNombres.actContador();
				ventanaIngresoNombres.setVisible(true);
				ventanaIngresoNombres.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						botonManual.setEnabled(true);
						cantidadDeProvinciasManual.setEnabled(true);
						botonArchivo.setEnabled(true);
					}
				});
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(botonManual);
		
		botonArchivo = new JButton("ARCHIVO");
		botonArchivo.setFont(new Font("Verdana", Font.PLAIN, 16));
		botonArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		botonArchivo.setBounds(275, 50, 150, 100);
		frame.getContentPane().add(botonArchivo);
		
		JLabel lblNewLabel = new JLabel("INGRESO DE DATOS");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel.setBounds(171, 15, 157, 23);
		frame.getContentPane().add(lblNewLabel);
		
		botonGenerar = new JButton("GENERAR");
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
		
		JLabel lblNewLabel_1 = new JLabel("Cantidad Provincias");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_1.setBounds(99, 185, 99, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		cantidadDeProvinciasArchivo = new JTextField();
		cantidadDeProvinciasArchivo.setEditable(false);
		cantidadDeProvinciasArchivo.setColumns(10);
		cantidadDeProvinciasArchivo.setBounds(312, 161, 76, 20);
		frame.getContentPane().add(cantidadDeProvinciasArchivo);
		
		JLabel lblNewLabel_1_1 = new JLabel("Cantidad Provincias");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_1_1.setBounds(301, 185, 99, 14);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("¡Faltan datos por ingresar!");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(150, 411, 200, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		cantidadDeRegionesDeseadas = new JTextField();
		cantidadDeRegionesDeseadas.setBounds(207, 270, 86, 20);
		frame.getContentPane().add(cantidadDeRegionesDeseadas);
		cantidadDeRegionesDeseadas.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("REGIONES");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_3.setBounds(224, 252, 52, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		Grafo grafo = ConstructorDeGrafos.construir(ProvinciasArgentinas.obtenerVecindadesArgentina());
		this.presenter = new PresenterRegiones(this, grafo);
	}

	private void iniciarVentanaIngresoDeNombres() {
		
	}
	
	@Override
	public void mostrarAristasParaCargarPesos(List<Arista> aristas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarRegiones(List<Set<Provincia>> regiones) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarError(String mensaje) {
		// TODO Auto-generated method stub
		
	}
}
