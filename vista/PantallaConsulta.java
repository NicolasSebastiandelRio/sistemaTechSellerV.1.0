package vista;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PantallaConsulta extends JPanel{
	private JLabel lblCodigoBuscar;
	private JTextField txtCodigoBuscar;
	private JButton btnBuscar;
	
	
	private JButton btnEditar;
	private JButton btnAnular;
	
	public PantallaConsulta(){
		setLayout(new FlowLayout());
		
		lblCodigoBuscar = new JLabel("Codigo SKU a buscar:");
		txtCodigoBuscar = new JTextField(6);
		
		
		
		btnEditar.setEnabled(false);
		btnAnular.setEnabled(false);
	}
}
