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
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class IngresoManualPesoYAristas extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable vecinas;
	private JTable nombreVecinaYPeso;

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
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("PROVINCIA ACTUAL:");
			lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			lblNewLabel.setBounds(20, 10, 160, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("nombre_provincia");
			lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 18));
			lblNewLabel_1.setBounds(181, 4, 220, 20);
			contentPanel.add(lblNewLabel_1);
		}
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setBounds(22, 35, 390, 160);
		contentPanel.add(splitPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		splitPane.setLeftComponent(scrollPane);
		
		vecinas = new JTable();
		vecinas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"VECINAS"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		vecinas.getColumnModel().getColumn(0).setResizable(false);
		scrollPane.setViewportView(vecinas);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		splitPane.setRightComponent(scrollPane_1);
		
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
		});
		nombreVecinaYPeso.getColumnModel().getColumn(0).setResizable(false);
		nombreVecinaYPeso.getColumnModel().getColumn(0).setPreferredWidth(150);
		nombreVecinaYPeso.getColumnModel().getColumn(1).setResizable(false);
		nombreVecinaYPeso.getColumnModel().getColumn(1).setPreferredWidth(15);
		scrollPane_1.setViewportView(nombreVecinaYPeso);
		splitPane.setDividerLocation(125);
		
		JLabel lblNewLabel_2 = new JLabel("00/00");
		lblNewLabel_2.setFont(new Font("Verdana", Font.BOLD, 16));
		lblNewLabel_2.setBounds(189, 203, 56, 14);
		contentPanel.add(lblNewLabel_2);
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(15);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("BACK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("NEXT");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
