 package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class IngresoManualNombres extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private int totalProvincias;
	private ArrayList<String> nombresProvincias = new ArrayList<String>();
	private int posicionActual;
	private JButton cancelButton;
	private JButton okButton;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_2;

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
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 220);
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
			textField = new JTextField();
			textField.setBounds(97, 59, 240, 30);
			textField.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(textField);
			textField.setColumns(10);
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
			lblNewLabel_2.setBounds(219, 134, 88, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setToolTipText("\r\n");
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
			{
				cancelButton = new JButton("CANCELAR");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (posicionActual == 0) {
							dispose();
						} else {
							posicionActual--;
							actNombreBotones();
							actContador();
							okButton.setEnabled(true);
							lblNewLabel_2.setVisible(false);
						}
					}
				});
				cancelButton.setHorizontalAlignment(SwingConstants.LEFT);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				okButton = new JButton("SIGUIENTE");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (posicionActual == totalProvincias - 1 && todosLosDatosCompletados()) {
							dispose();
						}
						if (posicionActual != totalProvincias - 1) {
								posicionActual++;
								if(posicionActual == totalProvincias - 1 && !todosLosDatosCompletados()) {
									okButton.setEnabled(false);
									lblNewLabel_2.setVisible(true);
								}
								actNombreBotones();
								actContador();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	public void setupVentana(int pro) {
		datoCantidadDeProvincias(pro);
		iniciarArregloNombres();
	}
	
	private void datoCantidadDeProvincias(int pro) {
		this.totalProvincias = pro;
	}

	private boolean todosLosDatosCompletados() {
		boolean estaTodo = true;
		for (String nombre : nombresProvincias) {
			estaTodo = estaTodo && nombre != "";
		}
		return estaTodo;
	}
	
	private void iniciarArregloNombres() {
		for (int pos = 0; pos < totalProvincias; pos++) {
			nombresProvincias.add("");
		}
	}
	
	private void actNombreBotones() {
		if (posicionActual == 0) {
			cancelButton.setText("CANCELAR");
		} else {
			cancelButton.setText("ATRÁS");
		}
		
		if (posicionActual == totalProvincias - 1) {
			okButton.setText("FINALIZAR");
		} else {
			okButton.setText("SIGUIENTE");
		}
	}
	
	public void actContador() {
		lblNewLabel_1.setText((posicionActual + 1) + " / " + totalProvincias);
	}
}
