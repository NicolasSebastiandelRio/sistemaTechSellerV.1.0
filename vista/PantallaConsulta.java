package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import modelo.Hardware;
import modelo.Producto;
import modelo.Software;

public class PantallaConsulta extends JPanel{

    // --- Componentes de Búsqueda ---
    private JPanel panelBusqueda;
    private JLabel lblCodigoBuscar;
    private JTextField txtCodigoBuscar;
    private JButton btnBuscar;

    // --- Componentes para Mostrar Resultados ---
    private JPanel panelResultados;
    // Campos comunes
    private JTextField txtNombreProducto, txtPrecio, txtDescripcion, txtCantidad, txtUbicacion, txtStockMinimo, txtFecha;
    private JComboBox<String> cmbMarca, cmbModelo;
    
    // Paneles para campos específicos de Hardware y Software
    private JPanel panelHardware, panelSoftware;

    // Campos de Hardware
    private JComboBox<String> cmbTipoComponente;
    private JTextField txtGarantiaMeses;
    private JTextField txtPesoEnGramos;
    private JCheckBox chkRequiereInstalacion;

    // Campos de Software
    private JComboBox<String> cmbPlataforma;
    private JTextField txtVersion;
    private JTextField txtTamanioDescarga;
    private JCheckBox chkRequiereDrivers;
    private JList<String> listaLicencia;
    private JScrollPane scrollListaLicencias;


    // --- Componentes de Acciones ---
    private JPanel panelAcciones;
    private JButton btnEditar;
    private JButton btnAnular;
    // Botones que aparecen durante la edición
    private JButton btnGuardarCambios;
    private JButton btnCancelarEdicion;
    
    
	public PantallaConsulta(){
		this.setLayout(new BorderLayout(10,10));
		
		//panel busqueda - arriba
		panelBusqueda = new JPanel();//flowLayout x default
		lblCodigoBuscar = new JLabel("Codigo SKU a buscar:");
		txtCodigoBuscar = new JTextField(10);
		btnBuscar = new JButton("Buscar");
		
		panelBusqueda.add(lblCodigoBuscar);
		panelBusqueda.add(txtCodigoBuscar);
		panelBusqueda.add(btnBuscar);
		

		//panel resultados - centro
		panelResultados = new JPanel(new GridLayout(0,4,10,5));
        panelResultados.setBorder(BorderFactory.createTitledBorder("Datos del Producto Encontrado"));
        
     // Inicializamos los campos de texto para los resultados (NO EDITABLES)
        txtNombreProducto = new JTextField();
        txtPrecio = new JTextField();
        txtDescripcion = new JTextField();
        txtCantidad = new JTextField();
        txtUbicacion = new JTextField();
        txtStockMinimo = new JTextField();
        txtFecha = new JTextField();
        cmbMarca = new JComboBox<>();
        cmbModelo = new JComboBox<>();
        
        // Agregamos los campos comunes
        panelResultados.add(new JLabel("Nombre:"));
        panelResultados.add(txtNombreProducto);
        panelResultados.add(new JLabel("Precio:"));
        panelResultados.add(txtPrecio);
        panelResultados.add(new JLabel("Descripción:"));
        panelResultados.add(txtDescripcion);
        panelResultados.add(new JLabel("Fecha de Alta:"));
        panelResultados.add(txtFecha);
        panelResultados.add(new JLabel("Cantidad Disponible:"));
        panelResultados.add(txtCantidad);
        panelResultados.add(new JLabel("Stock Mínimo:"));
        panelResultados.add(txtStockMinimo);
        panelResultados.add(new JLabel("Ubicación Almacén:"));
        panelResultados.add(txtUbicacion);
        
        // --- Sub-Panel para Hardware ---
        panelHardware = new JPanel(new GridLayout(0, 2, 5, 5));
        panelHardware.setBorder(BorderFactory.createTitledBorder("Hardware"));
        cmbTipoComponente = new JComboBox<>(new String[]{"Placa de video", "Procesador"});
        txtGarantiaMeses = new JTextField();
        txtPesoEnGramos = new JTextField();
        chkRequiereInstalacion = new JCheckBox();
        panelHardware.add(new JLabel("Tipo Componente:"));
        panelHardware.add(cmbTipoComponente);
        panelHardware.add(new JLabel("Garantía (meses):"));
        panelHardware.add(txtGarantiaMeses);
        panelHardware.add(new JLabel("Peso (gramos):"));
        panelHardware.add(txtPesoEnGramos);
        panelHardware.add(new JLabel("Requiere Instalación:"));
        panelHardware.add(chkRequiereInstalacion);
        panelHardware.setVisible(false); // Oculto al inicio
        
        // --- Sub-Panel para Software ---
        panelSoftware = new JPanel(new GridLayout(0, 2, 5, 5));
        panelSoftware.setBorder(BorderFactory.createTitledBorder("Software"));
        cmbPlataforma = new JComboBox<>(new String[]{"Windows", "Linux"});
        txtVersion = new JTextField();
        txtTamanioDescarga = new JTextField();
        chkRequiereDrivers = new JCheckBox();
        listaLicencia = new JList<>(new String[]{"ANUAL", "PERPETUA", "GRATUITA"});
        scrollListaLicencias = new JScrollPane(listaLicencia);
        panelSoftware.add(new JLabel("Plataforma:"));
        panelSoftware.add(cmbPlataforma);
        panelSoftware.add(new JLabel("Versión:"));
        panelSoftware.add(txtVersion);
        panelSoftware.add(new JLabel("Tamaño Descarga (MB):"));
        panelSoftware.add(txtTamanioDescarga);
        panelSoftware.add(new JLabel("Requiere Drivers:"));
        panelSoftware.add(chkRequiereDrivers);
        panelSoftware.add(new JLabel("Licencia:"));
        panelSoftware.add(scrollListaLicencias);
        panelSoftware.setVisible(false); // Oculto al inicio

        // Agregamos los subpaneles al panel de resultados
        panelResultados.add(panelHardware);
        panelResultados.add(panelSoftware);
        
        setCamposEditables(false);
        
        // --- 3. Panel de Acciones (Zona SUR) ---
        panelAcciones = new JPanel();
        btnEditar = new JButton("Editar");
        btnAnular = new JButton("Anular");
        btnGuardarCambios = new JButton("Guardar Cambios");
        btnCancelarEdicion = new JButton("Cancelar Edición");
        
        
		btnEditar.setEnabled(false);
		btnAnular.setEnabled(false);
		
        // Habilitamos y deshabilitamos los botones según el estado
        configurarBotones(false); // false = modo consulta
        
        panelAcciones.add(btnEditar);
        panelAcciones.add(btnAnular);
        panelAcciones.add(btnGuardarCambios);
        panelAcciones.add(btnCancelarEdicion);
        
        // --- 4. Agrego los paneles a la pantalla
        this.add(panelBusqueda, BorderLayout.NORTH);
        this.add(panelResultados,BorderLayout.CENTER);
        this.add(panelAcciones,BorderLayout.SOUTH);
	}
	
	 public void configurarBotones(boolean enEdicion) {
	        btnEditar.setVisible(!enEdicion);
	        btnAnular.setVisible(!enEdicion);
	        btnGuardarCambios.setVisible(enEdicion);
	        btnCancelarEdicion.setVisible(enEdicion);

	        // Los botones de acción solo se habilitan si hay un producto encontrado
	        if (txtNombreProducto.getText().isEmpty()) {
	            btnEditar.setEnabled(false);
	            btnAnular.setEnabled(false);
	        } else {
	            btnEditar.setEnabled(true);
	            btnAnular.setEnabled(true);
	        }
	    }
	
	
	    public void setCamposEditables(boolean editable) {
	        // Campos comunes
	        txtNombreProducto.setEditable(editable);
	        txtPrecio.setEditable(editable);
	        txtDescripcion.setEditable(editable);
	        txtCantidad.setEditable(editable);
	        txtUbicacion.setEditable(editable);
	        txtStockMinimo.setEditable(editable);
	        txtFecha.setEditable(false); // La fecha de alta no se edita

	        // Campos Hardware
	        cmbTipoComponente.setEnabled(editable);
	        txtGarantiaMeses.setEditable(editable);
	        txtPesoEnGramos.setEditable(editable);
	        chkRequiereInstalacion.setEnabled(editable);
	        
	        // Campos Software
	        cmbPlataforma.setEnabled(editable);
	        txtVersion.setEditable(editable);
	        txtTamanioDescarga.setEditable(editable);
	        chkRequiereDrivers.setEnabled(editable);
	        listaLicencia.setEnabled(editable);
	    }
	    
	    public void blanquearCampos() {
	        // Búsqueda
	        txtCodigoBuscar.setText("");
	        // Resultados
	        txtNombreProducto.setText("");
	        txtPrecio.setText("");
	        txtDescripcion.setText("");
	        txtFecha.setText("");
	        txtCantidad.setText("");
	        txtUbicacion.setText("");
	        txtStockMinimo.setText("");
	        // Hardware
	        txtGarantiaMeses.setText("");
	        txtPesoEnGramos.setText("");
	        chkRequiereInstalacion.setSelected(false);
	        // Software
	        txtVersion.setText("");
	        txtTamanioDescarga.setText("");
	        chkRequiereDrivers.setSelected(false);
	        listaLicencia.clearSelection();
	        // Ocultamos los paneles específicos
	        panelHardware.setVisible(false);
	        panelSoftware.setVisible(false);
	        // Reseteamos los botones
	        configurarBotones(false);
	    }

	    
	    public void mostrarDatos(Producto p) {
	        blanquearCampos(); // Limpiamos por si había una búsqueda anterior
	        
	        // Llenamos los campos comunes
	        txtNombreProducto.setText(p.getNombreProducto());
	        txtPrecio.setText(String.valueOf(p.getPrecio()));
	        txtDescripcion.setText(p.getDescripcion());
	        // Formatear la fecha para mostrarla
	        txtFecha.setText(String.format("%1$tY/%1$tm/%1$td", p.getFechaIngreso())); 
	        txtCantidad.setText(String.valueOf(p.getIds().getCantidadDisponible()));
	        txtUbicacion.setText(p.getIds().getUbicacionAlmacen());
	        txtStockMinimo.setText(String.valueOf(p.getIds().getStockMinimo()));

	        // Verificamos si es Hardware o Software y mostramos el panel correspondiente
	        if (p instanceof Hardware) {
	            Hardware h = (Hardware) p;
	            txtGarantiaMeses.setText(String.valueOf(h.getGarantiaMeses()));
	            txtPesoEnGramos.setText(String.valueOf(h.getPesoGramos()));
	            chkRequiereInstalacion.setSelected(h.isRequiereInstalacion());
	            cmbTipoComponente.setSelectedItem(h.getTipoComponente());
	            
	            panelHardware.setVisible(true);
	            panelSoftware.setVisible(false);
	        } else if (p instanceof Software) {
	            Software s = (Software) p;
	            txtVersion.setText(s.getVersion());
	            txtTamanioDescarga.setText(String.valueOf(s.getTamanioDescarga()));
	            chkRequiereDrivers.setSelected(s.isRequiereDrivers());
	            cmbPlataforma.setSelectedItem(s.getPlataformaCompatible());
	            listaLicencia.setSelectedValue(s.getTipoLicencia().toString(), true);
	            
	            panelSoftware.setVisible(true);
	            panelHardware.setVisible(false);
	        }
	        
	        configurarBotones(false); // Ponemos botones en modo consulta
	    }

	public JPanel getPanelBusqueda() {
		return panelBusqueda;
	}

	public JTextField getTxtCodigoBuscar() {
		return txtCodigoBuscar;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public JPanel getPanelResultados() {
		return panelResultados;
	}

	public JTextField getTxtNombreProducto() {
		return txtNombreProducto;
	}

	public JTextField getTxtPrecio() {
		return txtPrecio;
	}

	public JTextField getTxtDescripcion() {
		return txtDescripcion;
	}

	public JTextField getTxtCantidad() {
		return txtCantidad;
	}

	public JTextField getTxtUbicacion() {
		return txtUbicacion;
	}

	public JTextField getTxtStockMinimo() {
		return txtStockMinimo;
	}

	public JPanel getPanelAcciones() {
		return panelAcciones;
	}

	public JButton getBtnEditar() {
		return btnEditar;
	}

	public JButton getBtnAnular() {
		return btnAnular;
	}

	public JButton getBtnGuardarCambios() {
		return btnGuardarCambios;
	}

	public JButton getBtnCancelarEdicion() {
		return btnCancelarEdicion;
	}
    
    
}
