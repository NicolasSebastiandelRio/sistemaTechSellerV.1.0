package vista;
import modeloDAO.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
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

public class PantallaIngreso extends JPanel {

    // --- Componentes ---
    private JTextField txtCodigoSKU, txtNombreProducto, txtPrecio, txtDescripcion;
    private JTextField txtCantidadDisponible, txtUbicacionAlmacen, txtStockMinimo;
    private JComboBox<String> cmbMarca, cmbModelo;
    private JRadioButton rdbHardware, rdbSoftware;
    private ButtonGroup grupoDerivadas;
    private JPanel panelHardware, panelSoftware;
    private JComboBox<String> cmbTipoComponente;
    private JTextField txtGarantiaMeses, txtPesoEnGramos;
    private JCheckBox chkRequiereInstalacion;
    private JComboBox<String> cmbPlataforma;
    private JTextField txtVersion, txtTamanioDescarga;
    private JCheckBox chkRequiereDrivers;
    private JList<String> listaLicenciaSoftware;
    private JScrollPane scrollListaLicencias;
    private JList<String> listaEstatica;
    private JList<String> listaDinamica;
    private DefaultListModel<String> modeloListaDinamica; // Modelo para la lista dinámica
    private JScrollPane scrollListaEstatica, scrollListaDinamica;
    private JCheckBox chkOpcionA, chkOpcionB;
    private JButton btnAceptar, btnCancelar;

    public PantallaIngreso() {
        this.setLayout(new BorderLayout(10, 10));

        // --- PANEL SUPERIOR: Datos principales del producto ---
        JPanel panelDatosPrincipales = new JPanel(new GridLayout(0, 4, 10, 5));
        panelDatosPrincipales.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        
        txtCodigoSKU = new JTextField();
        txtNombreProducto = new JTextField();
        txtPrecio = new JTextField();
        txtDescripcion = new JTextField();
        txtCantidadDisponible = new JTextField();
        txtUbicacionAlmacen = new JTextField();
        txtStockMinimo = new JTextField();
        
        panelDatosPrincipales.add(new JLabel("Código SKU:"));
        panelDatosPrincipales.add(txtCodigoSKU);
        panelDatosPrincipales.add(new JLabel("Nombre:"));
        panelDatosPrincipales.add(txtNombreProducto);
        panelDatosPrincipales.add(new JLabel("Precio:"));
        panelDatosPrincipales.add(txtPrecio);
        panelDatosPrincipales.add(new JLabel("Descripción:"));
        panelDatosPrincipales.add(txtDescripcion);
        panelDatosPrincipales.add(new JLabel("Cantidad:"));
        panelDatosPrincipales.add(txtCantidadDisponible);
        panelDatosPrincipales.add(new JLabel("Stock Mínimo:"));
        panelDatosPrincipales.add(txtStockMinimo);
        panelDatosPrincipales.add(new JLabel("Ubicación:"));
        panelDatosPrincipales.add(txtUbicacionAlmacen);
        
        this.add(panelDatosPrincipales, BorderLayout.NORTH);

        // --- PANEL CENTRAL: Contendrá el resto de los campos ---
        JPanel panelCentral = new JPanel(new GridLayout(2, 2, 10, 10)); // Grilla 2x2 para organizar
        
        // --- SECCIÓN TIPO DE PRODUCTO (Hardware/Software) ---
        JPanel panelTipoProducto = new JPanel(new BorderLayout());
        panelTipoProducto.setBorder(BorderFactory.createTitledBorder("Tipo de Producto"));
        
        JPanel panelRadios = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rdbHardware = new JRadioButton("Hardware");
        rdbSoftware = new JRadioButton("Software");
        grupoDerivadas = new ButtonGroup();
        grupoDerivadas.add(rdbHardware);
        grupoDerivadas.add(rdbSoftware);
        panelRadios.add(rdbHardware);
        panelRadios.add(rdbSoftware);
        
        // --- Panel Hardware ---
        panelHardware = new JPanel(new GridLayout(0, 2, 5, 5));
        panelHardware.add(new JLabel("Tipo de Componente:"));
        cmbTipoComponente = new JComboBox<>(new String[]{"Placa de video", "Procesador"});
        panelHardware.add(cmbTipoComponente);
        panelHardware.add(new JLabel("Garantía (meses):"));
        txtGarantiaMeses = new JTextField();
        panelHardware.add(txtGarantiaMeses);
        panelHardware.add(new JLabel("Peso en gramos:"));
        txtPesoEnGramos = new JTextField();
        panelHardware.add(txtPesoEnGramos);
        panelHardware.add(new JLabel("Requiere instalación:"));
        chkRequiereInstalacion = new JCheckBox();
        panelHardware.add(chkRequiereInstalacion);
        
        // --- Panel Software ---
        panelSoftware = new JPanel(new GridLayout(0, 2, 5, 5));
        panelSoftware.add(new JLabel("Plataforma:"));
        cmbPlataforma = new JComboBox<>(new String[]{"Windows", "Linux"});
        panelSoftware.add(cmbPlataforma);
        panelSoftware.add(new JLabel("Versión:"));
        txtVersion = new JTextField();
        panelSoftware.add(txtVersion);
        panelSoftware.add(new JLabel("Tamaño (MB):"));
        txtTamanioDescarga = new JTextField();
        panelSoftware.add(txtTamanioDescarga);
        panelSoftware.add(new JLabel("Requiere drivers:"));
        chkRequiereDrivers = new JCheckBox();
        panelSoftware.add(chkRequiereDrivers);
        panelSoftware.add(new JLabel("Licencia:"));
        listaLicenciaSoftware = new JList<>(new String[]{"ANUAL", "PERPETUA", "GRATUITA"});
        scrollListaLicencias = new JScrollPane(listaLicenciaSoftware);
        panelSoftware.add(scrollListaLicencias);
        
        panelTipoProducto.add(panelRadios, BorderLayout.NORTH);
        panelTipoProducto.add(panelHardware, BorderLayout.WEST);
        panelTipoProducto.add(panelSoftware, BorderLayout.EAST);
        panelHardware.setVisible(false);
        panelSoftware.setVisible(false);
        
        panelCentral.add(panelTipoProducto);

        // --- SECCIÓN MARCA Y MODELO (Listas Desplegables) ---
        JPanel panelMarcas = new JPanel(new GridLayout(0, 2, 10, 5));
        panelMarcas.setBorder(BorderFactory.createTitledBorder("Marca y Modelo"));
        
        ArrayList<String> marcas = LecturaArchsDAO.leerArchivoDeListas("marcas.txt", 1);
        cmbMarca = new JComboBox<>(marcas.toArray(new String[0]));
        cmbModelo = new JComboBox<>();
        
        panelMarcas.add(new JLabel("Marca:"));
        panelMarcas.add(cmbMarca);
        panelMarcas.add(new JLabel("Modelo:"));
        panelMarcas.add(cmbModelo);
        
        panelCentral.add(panelMarcas);
        
        // --- SECCIÓN LISTAS (Estática y Dinámica) ---
        JPanel panelListas = new JPanel(new GridLayout(1, 2, 10, 5));
        panelListas.setBorder(BorderFactory.createTitledBorder("Selección Múltiple"));
        
        // Lista Estática
        listaEstatica = new JList<>(LecturaArchsDAO.leerListaEstatica("lista_estatica.txt").toArray(new String[0]));
        listaEstatica.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        scrollListaEstatica = new JScrollPane(listaEstatica);
        scrollListaEstatica.setBorder(BorderFactory.createTitledBorder("Lista Estática"));
        
        // Lista Dinámica (inicia vacía)
        modeloListaDinamica = new DefaultListModel<>();
        listaDinamica = new JList<>(modeloListaDinamica);
        listaDinamica.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        scrollListaDinamica = new JScrollPane(listaDinamica);
        scrollListaDinamica.setBorder(BorderFactory.createTitledBorder("Lista Dinámica"));
        
        panelListas.add(scrollListaEstatica);
        panelListas.add(scrollListaDinamica);
        
        panelCentral.add(panelListas);
        
        // --- SECCIÓN OPCIONES (chks) ---
        JPanel panelOpciones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelOpciones.setBorder(BorderFactory.createTitledBorder("Opciones Adicionales"));
        
        chkOpcionA = new JCheckBox("Opción A", true);
        chkOpcionB = new JCheckBox("Opción B", false);
        
        panelOpciones.add(chkOpcionA);
        panelOpciones.add(chkOpcionB);
        
        panelCentral.add(panelOpciones);
        
        this.add(panelCentral, BorderLayout.CENTER);
        
        // --- PANEL INFERIOR: Botones de Acción ---
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Alineados a la derecha
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        panelAcciones.add(btnAceptar);
        panelAcciones.add(btnCancelar);
        
        this.add(panelAcciones, BorderLayout.SOUTH);
    }

	public void blanquearCampos() {
		txtCodigoSKU.setText("");
		txtNombreProducto.setText("");
	    txtPrecio.setText("");
	    txtDescripcion.setText("");
	    txtCantidadDisponible.setText("");
	    txtUbicacionAlmacen.setText("");
	    txtStockMinimo.setText("");

	    cmbTipoComponente.setSelectedIndex(0);
	    txtGarantiaMeses.setText("");
        txtPesoEnGramos.setText("");
	    chkRequiereInstalacion.setSelected(false);

	    cmbPlataforma.setSelectedIndex(0);
	    txtVersion.setText("");
        txtTamanioDescarga.setText("");
	    chkRequiereDrivers.setSelected(false);
        listaLicenciaSoftware.clearSelection();

	    listaEstatica.clearSelection();
	    modeloListaDinamica.clear(); // Se limpia el modelo, no la lista
	    
	    chkOpcionA.setSelected(true);
	    chkOpcionB.setSelected(false);
	    
	    grupoDerivadas.clearSelection();

	    panelHardware.setVisible(false);
	    panelSoftware.setVisible(false);
	}

	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
	}
	
    public JTextField getTxtCodigoSKU() { return txtCodigoSKU; }
    public JButton getBtnAceptar() { return btnAceptar; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JRadioButton getRdbHardware() { return rdbHardware; }
    public JRadioButton getRdbSoftware() { return rdbSoftware; }
    public JPanel getPanelHardware() { return panelHardware; }
    public JPanel getPanelSoftware() { return panelSoftware; }
    public JComboBox<String> getCmbMarca() { return cmbMarca; }
    public JComboBox<String> getCmbModelo() { return cmbModelo; }
    public JTextField getTxtNombreProducto() { return txtNombreProducto; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtDescripcion() { return txtDescripcion; }
    public JTextField getTxtCantidadDisponible() { return txtCantidadDisponible; }
    public JTextField getTxtUbicacionAlmacen() { return txtUbicacionAlmacen; }
    public JTextField getTxtStockMinimo() { return txtStockMinimo; }
    public JComboBox<String> getCmbTipoComponente() { return cmbTipoComponente; }
    public JTextField getTxtGarantiaMeses() { return txtGarantiaMeses; }
    public JTextField getTxtPesoEnGramos() { return txtPesoEnGramos; }
    public JCheckBox getChkRequiereInstalacion() { return chkRequiereInstalacion; }
    public JComboBox<String> getCmbPlataforma() { return cmbPlataforma; }
    public JTextField getTxtVersion() { return txtVersion; }
    public JTextField getTxtTamanioDescarga() { return txtTamanioDescarga; }
    public JCheckBox getChkRequiereDrivers() { return chkRequiereDrivers; }
    public JList<String> getListaLicenciaSoftware() { return listaLicenciaSoftware; }
    public JList<String> getListaEstatica() { return listaEstatica; }
    public JList<String> getListaDinamica() { return listaDinamica; }

    
}