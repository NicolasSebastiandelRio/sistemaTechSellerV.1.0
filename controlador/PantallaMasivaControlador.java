package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modeloDAO.IProductoDAO;
import modeloDAO.ProductoDAO;
import vista.PantallaMasiva;

public class PantallaMasivaControlador implements ActionListener {

    private PantallaMasiva vista;
    private IProductoDAO productoDAO;
    private ArrayList<Producto> todosLosProductos; // Caché para no leer el archivo a cada rato

    public PantallaMasivaControlador(PantallaMasiva vista) {
        this.vista = vista;
        this.productoDAO = new ProductoDAO();

        this.vista.getBtnFiltrar().addActionListener(this);

        // Carga Inicial de Datos
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        this.todosLosProductos = productoDAO.obtenerTodosLosProductos();
        actualizarTablaYContadores(this.todosLosProductos);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnFiltrar()) {
            filtrarYActualizarTabla();
        }
    }

   
    private void filtrarYActualizarTabla() {
        String filtroNombre = vista.getTxtFiltroNombre().getText().trim().toLowerCase();
        String filtroDescripcion = vista.getTxtFiltroDescripcion().getText().trim().toLowerCase();

        // Si ambos filtros están vacíos, mostramos todos los productos.
        if (filtroNombre.isEmpty() && filtroDescripcion.isEmpty()) {
            actualizarTablaYContadores(this.todosLosProductos);
            return;
        }

        // Creamos una nueva lista para guardar solo los resultados que coincidan.
        ArrayList<Producto> productosFiltrados = new ArrayList<>();

        // Recorremos la lista completa de productos uno por uno.
        for (Producto p : this.todosLosProductos) {
            boolean nombreCoincide = true;
            boolean descCoincide = true;

            // Si el filtro de nombre no está vacío, verificamos si hay coincidencia parcial.
            if (!filtroNombre.isEmpty()) {
                nombreCoincide = p.getNombreProducto().toLowerCase().contains(filtroNombre);
            }

            // Si el filtro de descripción no está vacío, verificamos si hay coincidencia parcial.
            if (!filtroDescripcion.isEmpty()) {
                descCoincide = p.getDescripcion().toLowerCase().contains(filtroDescripcion);
            }

            // Si el producto actual cumple con AMBOS filtros, lo agregamos a la lista de resultados.
            if (nombreCoincide && descCoincide) {
                productosFiltrados.add(p);
            }
        }

        // Finalmente, actualizamos la tabla con la lista que acabamos de filtrar.
        actualizarTablaYContadores(productosFiltrados);
    }

    /**
     * Limpia la tabla y la vuelve a llenar con la lista de productos proporcionada.
     * También actualiza los contadores.
     * listaParaMostrar La lista de productos a mostrar en la tabla.
     */
    private void actualizarTablaYContadores(ArrayList<Producto> listaParaMostrar) {
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0); // Limpiamos la tabla

        for (Producto p : listaParaMostrar) {
            Object[] fila = new Object[6];
            
            fila[0] = (p instanceof modelo.Hardware) ? "Hardware" : "Software";
            fila[1] = p.getCodigoSKU();
            fila[2] = p.getNombreProducto();
            fila[3] = p.getPrecio();
            fila[4] = p.getIds().getCantidadDisponible();
            fila[5] = p.getIds().getUbicacionAlmacen();
            
            modelo.addRow(fila);
        }

        vista.getLblRegistrosVisualizados().setText("Registros visualizados: " + listaParaMostrar.size());
        vista.getLblTotalRegistros().setText("Total de registros: " + this.todosLosProductos.size());
    }
}