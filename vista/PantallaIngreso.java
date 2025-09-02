package vista;
import modeloDAO.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import controlador.PantallaIngresoControlador;
import excepciones.EntidadYaExistenteException;
import modelo.InformacionDeStock;

public class PantallaIngreso extends JPanel{
	
	//Componentes de la pantalla
	private JLabel lblTituloVentana; //titulo para la ventana
	private JTextArea txtLeyenda;//leyenda o comentario extenso no editable
	
	
	//Componentes de la entidad producto
	private JLabel lblCodigoSKU ;
	private JTextField txtCodigoSKU;
	
	private JLabel lblNombreProducto;
	private JTextField txtNombreProducto;
	
	private JLabel lblPrecio;
	private JTextField txtPrecio;
	
	private JLabel lblDescripcion;
	private JTextField txtDescripcion;
	
	private JComboBox <String> cmbListasDesplegable1;//lista desplegable 1
	private JComboBox <String> cmbListasDesplegable2;//lista desplegable 2 dependiente
	
	//fecha es la del sistema
	
	//InformacionDeStock
	private JLabel lblCantidadDisponible;
	private JTextField txtCantidadDisponible;
	
	private JLabel lblUbicacionAlmacen;
	private JTextField txtUbicacionAlmacen;
	
	private JLabel lblStockMinimo;
	private JTextField txtStockMinimo;
	
	//Componentes para las clases derivadas (Hardware y software)
	private JPanel panelDerivadas;//para alternar entre campos de hardware y software
	private JRadioButton rdbHardware;
	private JRadioButton rdbSoftware;
	private ButtonGroup grupoDerivadas;
	
	private JPanel panelHardware,panelSoftware;//paneles para organizar 
	
	//componentes de hardware
	private JLabel lblTipoComponente;
	private JComboBox <String> cmbTipoComponente;//lista desplegable uno
	private JLabel lblGarantia;
	private JTextField txtGarantiaMeses;
	private JLabel lblPesoEnGramos;
	private JTextField txtPesoEnGramos;
	private JCheckBox chkRequiereInstalacion;
	
	//componentes de software
	private JLabel lblPlataforma;
	private JComboBox <String> cmbPlataforma;//lista desplegable dos dependiente
	private JLabel lblVersion;
	private JTextField txtVersion;
	private JLabel lblTamanioDescarga; 
    private JTextField txtTamanioDescarga; 
	private JCheckBox chkRequiereDrivers;
	private JList <String> listaLicenciaEstatica;
	private JScrollPane scrollListaLicencias;
	
	//listas estatica y dinamica
	private JList <String> listaEstatica ; //lista estatica - ex Marcas
	private JList<String> listaDinamica; //lista dinamica - ex modelos
	private JScrollPane scrollListaEstatica;
	private JScrollPane scrollListaDinamica;
	
	//casillas de verificacion genericas REVISAR
	private JCheckBox chkOpcionActiva;
	private JCheckBox chkOpcionB;
	
	
	//paneles para los botones
	//private JPanel panelBotonesOpciones;
	private JPanel panelBotonesAcciones;
	//private ButtonGroup grupoBotonesOpciones;// Para que solo se pueda seleccionar uno
	
	//botones de accion
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	//objeto DAO para guardar datos
	private IProductoDAO productoDAO = new ProductoDAO(); 
	
	public PantallaIngreso() {
	    // 1. Usamos GridLayout: 0 filas (se ajustan solas), 4 columnas, con espacios de 10px.
	    setLayout(new GridLayout(0, 4, 10, 10));

	    // --- Inicialización de todos los componentes (sin cambios) ---
	    lblTituloVentana = new JLabel("Pantalla de Ingreso");
	    txtLeyenda = new JTextArea("Ingrese los datos del producto.");
	    txtLeyenda.setEditable(false);

	    lblCodigoSKU = new JLabel("Código SKU:");
	    txtCodigoSKU = new JTextField(10);
	    lblNombreProducto = new JLabel("Nombre:");
	    txtNombreProducto = new JTextField(10);
	    lblPrecio = new JLabel("Precio:");
	    txtPrecio = new JTextField(8);
	    lblDescripcion = new JLabel("Descripción:");
	    txtDescripcion = new JTextField(20);

	    lblCantidadDisponible = new JLabel("Cantidad:");
	    txtCantidadDisponible = new JTextField(8);
	    lblUbicacionAlmacen = new JLabel("Ubicación:");
	    txtUbicacionAlmacen = new JTextField(20);
	    lblStockMinimo = new JLabel("Stock Mínimo:");
	    txtStockMinimo = new JTextField(6);

	    btnAceptar = new JButton("Aceptar");
	    btnCancelar = new JButton("Cancelar");
	    panelBotonesAcciones = new JPanel(); // Usará FlowLayout por defecto, perfecto para botones.
	    panelBotonesAcciones.add(btnAceptar);
	    panelBotonesAcciones.add(btnCancelar);

	    rdbHardware = new JRadioButton("Hardware");
	    rdbSoftware = new JRadioButton("Software");
	    grupoDerivadas = new ButtonGroup();
	    grupoDerivadas.add(rdbHardware);
	    grupoDerivadas.add(rdbSoftware);

	    // Panel Hardware
	    panelHardware = new JPanel(new GridLayout(0, 2, 5, 5));
	    panelHardware.add(new JLabel("Tipo de Componente:"));
	    cmbTipoComponente = new JComboBox<>(new String[]{"Placa de video", "Procesador"});
	    panelHardware.add(cmbTipoComponente);
	    panelHardware.add(new JLabel("Garantía (meses):"));
	    txtGarantiaMeses = new JTextField(4);
	    panelHardware.add(txtGarantiaMeses);
	    panelHardware.add(new JLabel("Peso en gramos:"));
	    txtPesoEnGramos = new JTextField(6);
	    panelHardware.add(txtPesoEnGramos);
	    panelHardware.add(new JLabel("Requiere instalación:"));
	    chkRequiereInstalacion = new JCheckBox();
	    panelHardware.add(chkRequiereInstalacion);
	    
	    // Panel Software
	    panelSoftware = new JPanel(new GridLayout(0, 2, 5, 5));
	    panelSoftware.add(new JLabel("Plataforma:"));
	    cmbPlataforma = new JComboBox<>(new String[]{"Windows", "Linux"});
	    panelSoftware.add(cmbPlataforma);
	    panelSoftware.add(new JLabel("Versión:"));
	    txtVersion = new JTextField(8);
	    panelSoftware.add(txtVersion);
	    lblTamanioDescarga = new JLabel("Tamaño (MB):");
	    txtTamanioDescarga = new JTextField(8);
	    panelSoftware.add(lblTamanioDescarga);
	    panelSoftware.add(txtTamanioDescarga);
	    panelSoftware.add(new JLabel("Requiere drivers:"));
	    chkRequiereDrivers = new JCheckBox();
	    panelSoftware.add(chkRequiereDrivers);
	    
	    // ... otros componentes (listas, etc.)
	    listaLicenciaEstatica = new JList<>(new String[]{"Licencia anual", "Licencia perpetua", "Licencia gratuita"});
	    scrollListaLicencias = new JScrollPane(listaLicenciaEstatica);
	    listaLicenciaEstatica.setVisibleRowCount(3);
	    
	    // ...
	    // Inicialización de componentes que faltaban en el constructor original
	    //cmbListasDesplegable1 = new JComboBox<>(LecturaArchsDAO.leerListaEstatica("marcas.txt").toArray(new String[0]));
	    ArrayList<String> marcas = LecturaArchsDAO.leerArchivoDeListas("marcas.txt",1);
	    cmbListasDesplegable1 = new JComboBox<>(marcas.toArray(new String[0]));
	    cmbListasDesplegable2 = new JComboBox<>();
	    chkOpcionActiva = new JCheckBox("Opción A", true);
	    chkOpcionB = new JCheckBox("Opción B");


	    // --- 2. Agregamos los componentes de forma ordenada a la grilla ---
	    
	    // Fila 1
	    add(lblCodigoSKU);
	    add(txtCodigoSKU);
	    add(lblNombreProducto);
	    add(txtNombreProducto);
	    
	    // Fila 2
	    add(lblPrecio);
	    add(txtPrecio);
	    add(lblDescripcion);
	    add(txtDescripcion);
	    
	    // Fila 3
	    add(lblCantidadDisponible);
	    add(txtCantidadDisponible);
	    add(lblStockMinimo);
	    add(txtStockMinimo);
	    
	    // Fila 4
	    add(lblUbicacionAlmacen);
	    add(txtUbicacionAlmacen);
	    add(new JLabel("Marca:")); // Placeholder para JComboBox
	    add(cmbListasDesplegable1);
	    
	    // Fila 5: Radio Buttons
	    JPanel panelRadios = new JPanel(); // Panel auxiliar para los radios
	    panelRadios.add(new JLabel("Tipo Producto:"));
	    panelRadios.add(rdbHardware);
	    panelRadios.add(rdbSoftware);
	    add(panelRadios);
	    
	    add(new JLabel("Modelo:")); // Placeholder para JComboBox
	    add(cmbListasDesplegable2);
	    add(new JLabel("")); // Celda vacía para alinear
	    add(new JLabel("")); // Celda vacía para alinear

	    // Fila 6: Paneles específicos (Hardware/Software)
	    add(panelHardware);
	    add(panelSoftware);
	    panelSoftware.setVisible(false); // Oculto por defecto

	    // Fila 7: Lista de licencias y checkboxes
	    add(new JLabel("Licencia:"));
	    add(scrollListaLicencias);
	    add(chkOpcionActiva);
	    add(chkOpcionB);
	    
	    // Fila 8: Botones de acción
	    add(new JLabel("")); // Celda vacía para alinear
	    add(new JLabel("")); // Celda vacía para alinear
	    add(new JLabel("")); // Celda vacía para alinear
	    add(panelBotonesAcciones);
	}

	public void blanquearCampos() {
		txtCodigoSKU.setText("");
		txtNombreProducto.setText("");
	    txtPrecio.setText("");
	    txtDescripcion.setText("");

	    // Campos de InformacionDeStock
	    txtCantidadDisponible.setText("");
	    txtUbicacionAlmacen.setText("");
	    txtStockMinimo.setText("");

	    // Componentes de Hardware
	    cmbTipoComponente.setSelectedIndex(0); // Selecciona el primer ítem
	    txtGarantiaMeses.setText("");
	    chkRequiereInstalacion.setSelected(false); // Desmarca la casilla

	    // Componentes de Software
	    cmbPlataforma.setSelectedIndex(0);
	    txtVersion.setText("");
	    chkRequiereDrivers.setSelected(false);

	    // Listas estáticas y dinámicas
	    listaEstatica.clearSelection(); // Desselecciona todos los ítems
	    listaDinamica.clearSelection();
	    
	    // Casillas de verificación genéricas
	    chkOpcionActiva.setSelected(true); // Vuelve a su estado inicial (true)
	    chkOpcionB.setSelected(false);
	    
	    // Botones de opción
	    grupoDerivadas.clearSelection(); // Deselecciona los JRadioButton

	    // Lógica para alternar la visibilidad de los paneles de Hardware y Software,
	    // en este caso, se pueden ocultar ambos o mostrar uno por defecto.
	    panelHardware.setVisible(false);
	    panelSoftware.setVisible(false);
	    


	}


	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje,"info",JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	
public JLabel getLblTituloVentana() {
	return lblTituloVentana;
}

public JTextArea getTxtLeyenda() {
	return txtLeyenda;
}

public JLabel getLblCodigoSKU() {
	return lblCodigoSKU;
}

public JTextField getTxtCodigoSKU() {
	return txtCodigoSKU;
}

public JLabel getLblNombreProducto() {
	return lblNombreProducto;
}

public JTextField getTxtNombreProducto() {
	return txtNombreProducto;
}

public JLabel getLblPrecio() {
	return lblPrecio;
}

public JTextField getTxtPrecio() {
	return txtPrecio;
}

public JLabel getLblDescripcion() {
	return lblDescripcion;
}

public JTextField getTxtDescripcion() {
	return txtDescripcion;
}

public JComboBox<String> getCmbListasDesplegable1() {
	return cmbListasDesplegable1;
}

public JComboBox<String> getCmbListasDesplegable2() {
	return cmbListasDesplegable2;
}

public JLabel getLblCantidadDisponible() {
	return lblCantidadDisponible;
}

public JTextField getTxtCantidadDisponible() {
	return txtCantidadDisponible;
}

public JLabel getLblUbicacionAlmacen() {
	return lblUbicacionAlmacen;
}

public JTextField getTxtUbicacionAlmacen() {
	return txtUbicacionAlmacen;
}

public JLabel getLblStockMinimo() {
	return lblStockMinimo;
}

public JTextField getTxtStockMinimo() {
	return txtStockMinimo;
}

public JPanel getPanelDerivadas() {
	return panelDerivadas;
}

public JRadioButton getRdbHardware() {
	return rdbHardware;
}

public JRadioButton getRdbSoftware() {
	return rdbSoftware;
}

public ButtonGroup getGrupoDerivadas() {
	return grupoDerivadas;
}

public JPanel getPanelHardware() {
	return panelHardware;
}
public JPanel getPanelSoftware() {
	return panelSoftware;
}
public JLabel getLblTipoComponente() {
	return lblTipoComponente;
}
public JComboBox<String> getCmbTipoComponente() {
	return cmbTipoComponente;
}
public JLabel getLblGarantia() {
	return lblGarantia;
}

public JTextField getTxtGarantiaMeses() {
	return txtGarantiaMeses;
}

public JCheckBox getChkRequiereInstalacion() {
	return chkRequiereInstalacion;
}

public JLabel getLblPlataforma() {
	return lblPlataforma;
}

public JComboBox<String> getCmbPlataforma() {
	return cmbPlataforma;
}
public JLabel getLblVersion() {
	return lblVersion;
}

public JTextField getTxtVersion() {
	return txtVersion;
}

public JCheckBox getChkRequiereDrivers() {
	return chkRequiereDrivers;
}
public JList<String> getListaLicenciaEstatica() {
	return listaLicenciaEstatica;
}
public JScrollPane getScrollListaLicencias() {
	return scrollListaLicencias;
}
public JList<String> getListaEstatica() {
	return listaEstatica;
}
public JList<String> getListaDinamica() {
	return listaDinamica;
}
public JScrollPane getScrollListaEstatica() {
	return scrollListaEstatica;
}
public JScrollPane getScrollListaDinamica() {
	return scrollListaDinamica;
}
public JCheckBox getChkOpcionActiva() {
	return chkOpcionActiva;
}
public JCheckBox getChkOpcionB() {
	return chkOpcionB;
}
public JPanel getPanelBotonesAcciones() {
	return panelBotonesAcciones;
}
public JButton getBtnAceptar() {
	return btnAceptar;
}
public JButton getBtnCancelar() {
	return btnCancelar;
}

public IProductoDAO getProductoDAO() {
	return productoDAO;
}

public JLabel getLblPesoEnGramos() {
	return lblPesoEnGramos;
}

public JTextField getTxtPesoEnGramos() {
	return txtPesoEnGramos;
}

public JLabel getLblTamanioDescarga() {
	return lblTamanioDescarga;
}

public JTextField getTxtTamanioDescarga() {
	return txtTamanioDescarga;
}



}
