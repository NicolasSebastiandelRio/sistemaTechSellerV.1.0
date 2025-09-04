package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PantallaMasiva extends JPanel{
	private JTextField txtFiltroNombre;
    private JTextField txtFiltroDescripcion;
    private JButton btnFiltrar;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JLabel lblRegistrosVisualizados;
    private JLabel lblTotalRegistros;
    
    public PantallaMasiva() {
    	//borderLayout para la estructura principal
    	this.setLayout(new BorderLayout(10,10));
    	
    	//1 - panel superior : filtros de busqueda -
    	JPanel panelFiltros = new JPanel (new FlowLayout(FlowLayout.LEFT,10,5));
    	panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros de busqueda"));
    	
    	
    	panelFiltros.add(new JLabel ("Filtrar por nombre: "));
    	txtFiltroNombre = new JTextField(20);
    	panelFiltros.add(txtFiltroNombre);
    	
    	panelFiltros.add(new JLabel("Filtrar por descripcion"));
    	txtFiltroDescripcion = new JTextField(20);
    	panelFiltros.add(txtFiltroDescripcion);
    	
    	btnFiltrar = new JButton("Filtrar");
    	panelFiltros.add(btnFiltrar);
    	
    	
    	//2- Panel central : Grilla de resultados (JTable) - 
        // Definimos las columnas que pide la consigna (al menos 6)
        String[] columnas = {"Tipo", "SKU", "Nombre", "Precio", "Cantidad", "Ubicación"};
        modeloTabla = new DefaultTableModel(columnas,0) {
        	@Override
        	public boolean isCellEditable(int fila,int columna) {
        		return false;
        	}
        };
        
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);//tabla siempre va en un scrollPane
        
        this.add(scrollPane,BorderLayout.CENTER);
        
        //3- Panel inferior : Contadores - 
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblRegistrosVisualizados = new JLabel("Registros visualizados: 0");
        lblTotalRegistros = new JLabel("Total registros: 0");
        
        panelInfo.add(lblRegistrosVisualizados);
        //agregamos espacio de separacion entre labels
        panelInfo.add(new JLabel("  |  "));
        panelInfo.add(lblTotalRegistros);
        
        this.add(panelInfo, BorderLayout.SOUTH);
    }

	public JTextField getTxtFiltroNombre() {
		return txtFiltroNombre;
	}

	public JTextField getTxtFiltroDescripcion() {
		return txtFiltroDescripcion;
	}

	public JButton getBtnFiltrar() {
		return btnFiltrar;
	}

	public JTable getTablaProductos() {
		return tablaProductos;
	}

	public DefaultTableModel getModeloTabla() {
		return modeloTabla;
	}

	public JLabel getLblRegistrosVisualizados() {
		return lblRegistrosVisualizados;
	}

	public JLabel getLblTotalRegistros() {
		return lblTotalRegistros;
	}
    
    
}
