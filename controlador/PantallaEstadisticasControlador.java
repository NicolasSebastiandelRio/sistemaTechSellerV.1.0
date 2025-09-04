package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modeloDAO.IProductoDAO;
import modeloDAO.ProductoDAO;
import vista.PantallaEstadisticas;

public class PantallaEstadisticasControlador implements ActionListener {

    private PantallaEstadisticas vista;
    private IProductoDAO productoDAO;

    public PantallaEstadisticasControlador(PantallaEstadisticas vista) {
        this.vista = vista;
        this.productoDAO = new ProductoDAO();

        // Registramos el listener para el único botón de la vista
        this.vista.getBtnGenerar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnGenerar()) {
            calcularYMostrarEstadisticas();
        }
    }

    
    private void calcularYMostrarEstadisticas() {
        try {
            // --- 1. Llama a los métodos del DAO para obtener cada estadística ---
            
            // Estadística 1: Valor total de Hardware reciente
            double valorTotal = productoDAO.calcularValorTotalHardwareReciente();
            String textoEstadistica1 = String.format("El valor total de los productos de Hardware que requieren instalación y fueron ingresados en los últimos 6 meses es: $%.2f", valorTotal);
            vista.getAreaEstadistica1().setText(textoEstadistica1);

            // Estadística 2: Productos que no contienen un criterio
            String criterio = JOptionPane.showInputDialog(vista, "Ingrese una palabra para excluir de la búsqueda de nombres de productos:", "Filtro de Estadística", JOptionPane.QUESTION_MESSAGE);
            if (criterio != null) { // Si el usuario no cancela
                String infoProductos = productoDAO.obtenerInfoProductosSinCriterio(criterio);
                vista.getAreaEstadistica2().setText(infoProductos);
            }

            // Estadística 3: Conteo vs. número aleatorio
            String conteo = productoDAO.contarProductosSumaMenorAleatorio();
            vista.getAreaEstadistica3().setText(conteo);

            // --- 2. Llama al método del DAO para generar el archivo JSON ---
            productoDAO.generarEstadisticasJSON();

            // --- 3. Notifica al usuario ---
            JOptionPane.showMessageDialog(vista, "Estadísticas calculadas y archivo 'estadisticas.json' actualizado correctamente.", "Proceso Completado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Ocurrió un error al generar las estadísticas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Útil para depurar
        }
    }
}