 package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Grafo;

import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IngresoManualNombres extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField campoDeIngresoNombres;
	private int totalProvincias;
	private ArrayList<String> nombresProvincias = new ArrayList<>();
	private int posicionActual;
	private JButton botonAtras;
	private JButton botonSiguiente;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_2;
	private Grafo grafoSolicitado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			IngresoManualNombres dialog = new IngresoManualNombres();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public IngresoManualNombres() {
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 220);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			lblNewLabel = new JLabel("INGRESE EL NOMBRE DE LA PROVINCIA");
			lblNewLabel.setBounds(64, 15, 306, 18);
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
			contentPanel.add(lblNewLabel);
		}
		{
			campoDeIngresoNombres = new JTextField();
			campoDeIngresoNombres.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					nombresProvincias.set(posicionActual, campoDeIngresoNombres.getText().trim());
					actPosibleFinalizar();
				}
			});
			campoDeIngresoNombres.setBounds(97, 59, 240, 30);
			campoDeIngresoNombres.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(campoDeIngresoNombres);
			campoDeIngresoNombres.setColumns(10);
		}
		
		
		{
			lblNewLabel_1 = new JLabel((posicionActual + 1) + " / " + totalProvincias);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 16));
			lblNewLabel_1.setBounds(177, 100, 80, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			lblNewLabel_2 = new JLabel("¡FALTAN NOMBRES!");
			lblNewLabel_2.setVisible(false);
			lblNewLabel_2.setForeground(new Color(255, 0, 0));
			lblNewLabel_2.setFont(new Font("Trebuchet MS", Font.PLAIN, 10));
			lblNewLabel_2.setBounds(219, 134, 180, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setToolTipText("\r\n");
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
			{
				botonAtras = new JButton("CANCELAR");
				botonAtras.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (posicionActual == 0) {
							dispose();
						} else {
							posicionActual--;
							actPosibleFinalizar();
							actNombreBotones();
							actContador();
							botonSiguiente.setEnabled(true);
							lblNewLabel_2.setVisible(false);
							campoDeIngresoNombres.setText(nombresProvincias.get(posicionActual));
						}
					}
				});
				botonAtras.setHorizontalAlignment(SwingConstants.LEFT);
				botonAtras.setActionCommand("Cancel");
				buttonPane.add(botonAtras);
			}
			{
				botonSiguiente = new JButton("SIGUIENTE");
				botonSiguiente.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (posicionActual == totalProvincias - 1 && todosLosDatosCompletados()) {
							IngresoManualPesoYAristas ventanaIngresoPesoAristas = new IngresoManualPesoYAristas();
							ventanaIngresoPesoAristas.setupVentana(nombresProvincias, totalProvincias);
							ventanaIngresoPesoAristas.setVisible(true);
							
							if (ventanaIngresoPesoAristas.finalizoConExito()) {
								grafoSolicitado = ventanaIngresoPesoAristas.obtenerGrafo();
							}
							dispose();
						}
						if (posicionActual != totalProvincias - 1) {
								posicionActual++;
								actPosibleFinalizar();
								actNombreBotones();
								actContador();
								campoDeIngresoNombres.setText(nombresProvincias.get(posicionActual));
						}
					}
				});
				botonSiguiente.setActionCommand("OK");
				buttonPane.add(botonSiguiente);
				getRootPane().setDefaultButton(botonSiguiente);
			}
		}
	}
	
	public void setupVentana(int pro) {
		datoCantidadDeProvincias(pro);
		iniciarArregloNombres();
		actContador();
	}
	
	private void datoCantidadDeProvincias(int pro) {
		this.totalProvincias = pro;
	}

	private boolean todosLosDatosCompletados() {
		for (String nombre : nombresProvincias) {
			if (nombre.trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	private boolean hayNombresRepetidos() {
	    HashSet<String> vistos = new HashSet<>();
	    for (String nombre : nombresProvincias) {
	        if (!vistos.add(nombre.toLowerCase())) {
	            return true;   // add devuelve false si ya estaba
	        }
	    }
	    return false;
	}
	
	private void iniciarArregloNombres() {
		for (int pos = 0; pos < totalProvincias; pos++) {
			nombresProvincias.add("");
		}
	}
	
	private void actPosibleFinalizar() {
	    boolean datosLlenos = todosLosDatosCompletados();
	    boolean repetidos = hayNombresRepetidos();
	    boolean esLaUltimaPos = (posicionActual == totalProvincias - 1);

	    botonSiguiente.setEnabled(!esLaUltimaPos || (datosLlenos && !repetidos));

	    if (esLaUltimaPos && !datosLlenos) {
	        lblNewLabel_2.setText("¡FALTAN NOMBRES!");
	        lblNewLabel_2.setVisible(true);
	    } else if (esLaUltimaPos && repetidos) {
	        lblNewLabel_2.setText("¡HAY NOMBRES REPETIDOS!");
	        lblNewLabel_2.setVisible(true);
	    } else {
	        lblNewLabel_2.setVisible(false);
	    }
	}
	
	private void actNombreBotones() {
		if (posicionActual == 0) {
			botonAtras.setText("CANCELAR");
		} else {
			botonAtras.setText("ATRÁS");
		}
		
		if (posicionActual == totalProvincias - 1) {
			botonSiguiente.setText("FINALIZAR");
		} else {
			botonSiguiente.setText("SIGUIENTE");
		}
	}
	
	private void actContador() {
		lblNewLabel_1.setText((posicionActual + 1) + " / " + totalProvincias);
	}
	
	public Grafo getGrafo() {
	    return grafoSolicitado;
	}
}
