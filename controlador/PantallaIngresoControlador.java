package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import excepciones.EntidadYaExistenteException;
import modelo.Hardware;
import modelo.InformacionDeStock;
import modelo.Producto;
import modelo.Software;
import modelo.TipoLicencia;
import modeloDAO.IProductoDAO;
import modeloDAO.LecturaArchsDAO;
import modeloDAO.ProductoDAO;
import vista.PantallaIngreso;

public class PantallaIngresoControlador implements ActionListener,ItemListener{
	private PantallaIngreso vista;
	private IProductoDAO productoDAO;
	
	public PantallaIngresoControlador(PantallaIngreso vista) {
		this.vista=vista;
		this.productoDAO = new ProductoDAO();
		
        this.vista.getBtnAceptar().addActionListener(this);
        this.vista.getBtnCancelar().addActionListener(this);
        this.vista.getRdbHardware().addActionListener(this);
        this.vista.getRdbSoftware().addActionListener(this);
        this.vista.getCmbMarca().addItemListener(this);
		
	}
	
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == vista.getBtnAceptar()) {
            manejarAceptar();
        } else if (source == vista.getBtnCancelar()) {
            manejarCancelar();
        } else if (source == vista.getRdbHardware()) {
            vista.getPanelHardware().setVisible(true);
            vista.getPanelSoftware().setVisible(false);
        } else if (source == vista.getRdbSoftware()) {
            vista.getPanelHardware().setVisible(false);
            vista.getPanelSoftware().setVisible(true);
        }
    }
    
	@Override
	public void itemStateChanged(ItemEvent e) {
		// logica de listas dependientes.
		if(e.getSource() == vista.getCmbMarca()) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				String criterio = (String) vista.getCmbMarca().getSelectedItem();
				
				
				ArrayList<String> modelos = LecturaArchsDAO.leerListaDependiente("modelos.txt", criterio);
				
				vista.getCmbModelo().removeAllItems();
				for(String modelo : modelos) {
					vista.getCmbModelo().addItem(modelo);
				}
			}
		}
		
		
	}
	
	 private void manejarAceptar() {
	     try {
	            // 1. Validaciones previas a la creación del objeto
	            validarCamposNumericos();

	            if (validarComponentesCompletos() < 3) {
	                vista.mostrarMensaje("Debe informar al menos 3 componentes de distinto tipo para poder guardar.");
	                return;
	            }

	            String codigoSKU = vista.getTxtCodigoSKU().getText();
	            if (productoDAO.buscarProducto(codigoSKU) != null) {
	                throw new EntidadYaExistenteException("Producto con el SKU " + codigoSKU + " ya existe.");
	            }

	            // 2. Creación del objeto de información de stock
	            InformacionDeStock ids = new InformacionDeStock(
	                Integer.parseInt(vista.getTxtCantidadDisponible().getText()),
	                vista.getTxtUbicacionAlmacen().getText(),
	                Integer.parseInt(vista.getTxtStockMinimo().getText())
	            );

	            // 3. Creación del objeto Producto (Hardware o Software)
	            Producto nuevoProducto = null;
	            double precio = Double.parseDouble(vista.getTxtPrecio().getText());

	            if (vista.getRdbHardware().isSelected()) {
	                double pesoGramos = Double.parseDouble(vista.getTxtPesoEnGramos().getText());
	                String tipoComponente = (String) vista.getCmbTipoComponente().getSelectedItem();
	                int garantiaMeses = Integer.parseInt(vista.getTxtGarantiaMeses().getText());
	                boolean requiereInstalacion = vista.getChkRequiereInstalacion().isSelected();
	                
	                nuevoProducto = new Hardware(
	                		codigoSKU,
	                		vista.getTxtNombreProducto().getText(),
	                		precio,
	                		vista.getTxtDescripcion().getText(),
	                		Calendar.getInstance(),
	                		ids,
	                		tipoComponente,
	                		garantiaMeses,
	                		pesoGramos,
	                		requiereInstalacion
	                		);
	                
	            } else if (vista.getRdbSoftware().isSelected()) {
	                // Usamos una lógica de conversión para obtener el enum
	                long tamanioDescarga = Long.parseLong(vista.getTxtTamanioDescarga().getText());
	            	String textoLicencia = vista.getListaEstatica().getSelectedValue();
	                TipoLicencia tipoLicencia = TipoLicencia.valueOf(
	                    vista.getListaEstatica().getSelectedValue().toUpperCase().replace(" ", "_")
	                );

	                nuevoProducto = new Software(
	                    codigoSKU,
	                    vista.getTxtNombreProducto().getText(),
	                    precio,
	                    vista.getTxtDescripcion().getText(),
	                    Calendar.getInstance(),
	                    ids,
	                    (String) vista.getCmbPlataforma().getSelectedItem(),
	                    tipoLicencia,
	                    vista.getTxtVersion().getText(),
	                    tamanioDescarga,
	                    vista.getChkRequiereDrivers().isSelected()
	                );
	            }

	            // 4. Llamada al DAO para guardar el producto
	            if (nuevoProducto != null) {
	                productoDAO.guardarProducto(nuevoProducto);
	                vista.mostrarMensaje("Producto guardado con éxito.");
	                vista.blanquearCampos();
	            }

	        } catch (NumberFormatException ex) {
	            vista.mostrarMensaje("Error: Formato numérico incorrecto. " + ex.getMessage());
	        } catch (EntidadYaExistenteException ex) {
	            vista.mostrarMensaje("Error: " + ex.getMessage());
	        } catch (Exception ex) {
	            vista.mostrarMensaje("Ocurrió un error inesperado: " + ex.getMessage());
	        }	    }
	 
	 private void manejarCancelar() {
		 int confirmacion = JOptionPane.showConfirmDialog(vista, "Esta seguro de cancelar la carga?","Confirmar",JOptionPane.YES_NO_OPTION);
		 if(confirmacion == JOptionPane.YES_OPTION) {
			 vista.blanquearCampos();
			 vista.mostrarMensaje("Operacion cancelada y campos blanqueados");
		 }	 
	 }
	 
	    private int validarComponentesCompletos() {
	        int count = 0;
	        if (!vista.getTxtCodigoSKU().getText().isEmpty()) count++;
	        if (!vista.getTxtNombreProducto().getText().isEmpty()) count++;
	        if (!vista.getTxtPrecio().getText().isEmpty()) count++;
	        if (!vista.getTxtCantidadDisponible().getText().isEmpty()) count++;
	        if (!vista.getTxtUbicacionAlmacen().getText().isEmpty()) count++;
	        if (vista.getRdbHardware().isSelected() || vista.getRdbSoftware().isSelected()) count++;
	        if (vista.getChkRequiereInstalacion().isSelected() || vista.getChkRequiereDrivers().isSelected()) count++;
	        return count;
	    }
	    
	    
	    private void validarCamposNumericos() throws NumberFormatException {
	        Double.parseDouble(vista.getTxtPrecio().getText());
	        Integer.parseInt(vista.getTxtCantidadDisponible().getText());
	        Integer.parseInt(vista.getTxtStockMinimo().getText());
	        if (vista.getRdbHardware().isSelected()) {
	            Integer.parseInt(vista.getTxtGarantiaMeses().getText());
	        } else if (vista.getRdbSoftware().isSelected()) {
	            Long.parseLong(vista.getTxtTamanioDescarga().getText()); 
	            }
	    }

		public void setVista(PantallaIngreso vista) {
			this.vista = vista;
		}
	    
	    
	
}
