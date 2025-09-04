package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.Producto;
import modeloDAO.IProductoDAO;
import modeloDAO.ProductoDAO;
import vista.PantallaConsulta;

public class PantallaConsultaControlador implements ActionListener{
	private PantallaConsulta vista;
	private IProductoDAO productoDAO;
	
	public PantallaConsultaControlador(PantallaConsulta vista) {
		this.vista=vista;
		this.productoDAO=new ProductoDAO();
		
		// Registramos este controlador como listener para TODOS los botones de la vista
        this.vista.getBtnBuscar().addActionListener(this);
        this.vista.getBtnEditar().addActionListener(this);
        this.vista.getBtnAnular().addActionListener(this);
        this.vista.getBtnGuardarCambios().addActionListener(this);
        this.vista.getBtnCancelarEdicion().addActionListener(this);
        
        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == vista.getBtnBuscar()) {
			manejarBusqueda();
		}else if(source == vista.getBtnEditar()) {
			manejarEditar();
		}else if(source == vista.getBtnAnular()) {
			manejarAnular();
		}else if(source == vista.getBtnGuardarCambios()) {
			manejarGuardarCambios();
		}else if(source == vista.getBtnCancelarEdicion()) {
			manejarBusqueda();
		}
		
	}
	
	private void manejarBusqueda() {
		String sku = vista.getTxtCodigoBuscar().getText().trim();
		if(sku.isEmpty()) {
			JOptionPane.showMessageDialog(vista, "Por favor, ingrese codigo SKU a buscar");
			return;
		}
		
		Producto productoEncontrado = productoDAO.buscarProducto(sku);
		
		if(productoEncontrado != null) {
			vista.mostrarDatos(productoEncontrado);
		}else {
			JOptionPane.showMessageDialog(vista, "No se encontro ningun producto con el sku ingresado");
			vista.blanquearCampos();
		}
		
	}

	private void manejarEditar() {
		vista.setCamposEditables(true);
		vista.configurarBotones(true);//modo edicion
	}
	
	private void manejarAnular() {
		String sku = vista.getTxtCodigoBuscar().getText().trim();
		int confirmacion = JOptionPane.showConfirmDialog(vista, "Seguro que desea eliminar el registro del sku con nro"+sku+"?","confirmar anulacion",
				JOptionPane.YES_NO_OPTION);
		
		if(confirmacion == JOptionPane.YES_OPTION) {
			productoDAO.eliminarProducto(sku);
			JOptionPane.showMessageDialog(vista, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.blanquearCampos();
		}
	}
	
	private void manejarGuardarCambios() {
		
	}
}
