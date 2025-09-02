package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controlador.PantallaIngresoControlador;

public class Menu extends JFrame implements ActionListener {
	//1 declaro
	private JMenuBar barra = new JMenuBar();
	
	//menu operaciones
	private JMenu menuOperaciones = new JMenu("Operaciones");
	private JMenuItem itemIngreso = new JMenuItem("Ingreso");
	private JMenuItem itemConsultaActualizacion = new JMenuItem("Consulta y actualizacion");
	private JMenuItem itemMasiva = new JMenuItem("Consulta Masiva");
	
	//menuEstadisticas
	private JMenu menuEstadisticas = new JMenu("Estadisticas");
	private JMenuItem itemEstadisticas = new JMenuItem("ver estadisticas");
	
	//menu sistema
	private JMenu menuSistema = new JMenu("Sistema");
	private JMenuItem itemAcercaDe = new JMenuItem("Acerca de");
	
	//menu salir
	private JMenu menuSalir = new JMenu("Salir");
	private JMenuItem itemSalir = new JMenuItem("Salir del sistema");

	
	public Menu() {
		//config basica del jFrame
		setTitle("Sistema techSeller");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//agrego items a sus menues respectivos
		menuOperaciones.add(itemIngreso);
		menuOperaciones.add(itemConsultaActualizacion);
		menuOperaciones.add(itemMasiva);
		
		menuEstadisticas.add(itemEstadisticas);
		
		//agrego menuItems a la barra del menu bar
		menuSistema.add(itemAcercaDe);
		
		menuSalir.add(itemSalir);
		
		//agregar los JMenu a la JMenuBar

		barra.add(menuOperaciones);
		barra.add(menuEstadisticas);
		barra.add(menuSistema);
		barra.add(menuSalir);
		
		//Establecer la JMenuBar al JFrame
		setJMenuBar(barra);


		
		//Registrar la clase como escuchador de eventos para cada JMenuItem
		itemIngreso.addActionListener(this);
		itemConsultaActualizacion.addActionListener(this);
		itemMasiva.addActionListener(this);
		
		itemEstadisticas.addActionListener(this);
		
		itemAcercaDe.addActionListener(this);
		
		itemSalir.addActionListener(this);
		
		//hacerlo visible
		setVisible(true);
	}
	
	//implementar logica del manejador de eventos
	@Override
	public void actionPerformed(ActionEvent e) {//reacciona a los clics de los menus
		
		Object source = e.getSource();
		if(source == itemIngreso) {
	       PantallaIngreso pantallaIngreso = new PantallaIngreso();
	       PantallaIngresoControlador controlador = new PantallaIngresoControlador(pantallaIngreso);
	        mostrarPanel(pantallaIngreso);
		}else if(source ==itemConsultaActualizacion){
			//usuario ingresa codigo para buscar procucto,
			//al darle buscar, el sistema muestra toda la info en formato no editable (o msj error si no existe)
			//luego puede elegir con 2 botones si editar o anular, si edita vuelve todos los campos editables para modifciarlos, con dos botones mas
			//de aplicar cambios o cancelar
			// boton anular elimina a ese registro del txt
		}else if(source == itemMasiva) {
			//consulta masiva
		} else if(source == itemEstadisticas) {
			//aca iria la logica para mostrar pantalla de estadisticas
		}else if (source ==  itemAcercaDe) {
			//muestro cuadro de dialogo acerca de
			JOptionPane.showMessageDialog(this, "Sistema techSeller\nVersion: 1.0\nProduced by Nicolas del Rio - 2025",
					"Acerca de",
					JOptionPane.INFORMATION_MESSAGE);
		}else if (source == itemSalir) {
			//mostrar cuadro dialogo de conf
			int confirmar = JOptionPane.showConfirmDialog(this, "Esta seguro que desea salir?","Confirmacion",JOptionPane.YES_NO_OPTION);
			if(confirmar == JOptionPane.YES_OPTION) {System.exit(0);}
		}
		
	}
	
	//metodo aux para cambiar de panel en ventana ppal
	private void mostrarPanel(JPanel panel) {
		this.setContentPane(panel);
		
		this.revalidate();
		this.repaint();
	}

}
