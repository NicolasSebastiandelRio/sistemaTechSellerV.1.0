package modeloDAO;

import java.util.ArrayList;

import excepciones.EntidadYaExistenteException;
import modelo.Producto;

public interface IProductoDAO {
	//interfaz con metodos de gestion de productos
	public void guardarProducto(Producto p) throws EntidadYaExistenteException;//Para el ingreso de un nuevo producto.
	public Producto buscarProducto(String codigoSKU);//Para encontrar un producto por su código, para la consulta y actualización.
	public void actualizarProducto(Producto p);//Para modificar la información de un producto.
	public void eliminarProducto(String codigoSKU);//Para anular un producto.
	public ArrayList<Producto> obtenerTodosLosProductos();//Para la consulta masiva.
	public void generarEstadisticasJSON();//Un método para generar el archivo JSON, ya que la consigna lo pide.
	
}
