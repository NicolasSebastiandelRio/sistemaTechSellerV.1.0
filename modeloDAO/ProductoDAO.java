package modeloDAO;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import excepciones.EntidadYaExistenteException;
import modelo.Hardware;
import modelo.InformacionDeStock;
import modelo.Producto;
import modelo.Software;
import modelo.TipoLicencia;

public class ProductoDAO implements IProductoDAO{
	private static final String NOMBRE_ARCHIVO = "productos.txt";
	private static final String DELIMITADOR = ";";
	
	@Override
	public void guardarProducto(Producto p) throws EntidadYaExistenteException {
		// Validacion de existencia, antes de guardar
		if(buscarProducto(p.getCodigoSKU()) != null) {
			throw new EntidadYaExistenteException("Producto con SKU = "+p.getCodigoSKU()+"ya existe");
		}
		
		PrintWriter pw = null;
		try {
			//abre arch en modo append, para agregar al final
			pw = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO,true));
			pw.println(serializarProducto(p));
		}catch(IOException e) {
			System.err.println("Error al guardar producto"+e.getMessage());
		}finally {
			if(pw != null) {
				pw.close();
			}
		}
	}

	@Override
	public Producto buscarProducto(String codigoSKU) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(NOMBRE_ARCHVIVO));
			while(scanner.hasNextLine()) {
				String linea = scanner.nextLine();
				Producto p = deserializarProducto(linea);
				if(p.getCodigoSKU().equals(codigoSKU)) {
					return p;
				}
			}
		}catch(FileNotFoundException e) {
			System.err.println("el arch no existe "+e.getMessage());
		}finally {
			if(scanner != null) {
				scanner.close();
			}
		}
	}

	@Override
	public void actualizarProducto(Producto p) {
		// uso lista temporal para guardar prods, para posteriormente reescribir el archivo
		ArrayList<Producto> todosLosProductos = obtenerTodosLosProductos();
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO));
			for(Producto prod : todosLosProductos) {
				if(prod.getCodigoSKU().equals(p.getCodigoSKU())) {
					pw.println(serializarProducto(p));
				}else {
					pw.println(serializarProducto(prod));
				}
			}
		}catch(IOException e) {
			System.err.println("error al actualizar producto "+e.getMessage());
		}finally {
			if(pw != null) {
				pw.close();
			}
		}
		
	}

	@Override
	public void eliminarProducto(String codigoSKU) {
        // Similar a actualizar, pero simplemente no se escribe la línea del producto a eliminar		
		ArrayList<Producto> todosLosProductos = obtenerTodosLosProductos();
		PrintWriter pw = null;
		try {
			pw = new PrintWriter (new FileWriter (NOMBRE_ARCHIVO));
			for(Producto prod : todosLosProductos) {
				if(!prod.getCodigoSKU().equals(codigoSKU)) {
					pw.println(serializarProducto(prod));
				}
			}
		}catch(IOException e) {
			System.err.println("error al eliminar el producto"+e.getMessage());
		}finally {
			if(pw != null) {
				pw.close();
			}
		}
	
	}

	@Override
	public ArrayList<Producto> obtenerTodosLosProductos() {
		// leer el archivo productos.txt y reconstruir una lista de objetos.
		ArrayList<Producto> listaProductos = new ArrayList<>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(NOMBRE_ARCHIVO));
			while(scanner.hasNextLine()) {
				String linea = scanner.nextLine();
				listaProductos.add(deserializarProducto(linea));
			}
		}catch(FileNotFoundException e) {
			System.err.println("Archivo de prods no existe");
		}finally {
			if(scanner != null) {
				scanner.close();
			}
		}
	}
	
	private String serializarProducto(Producto p) {
		StringBuilder sb = new StringBuilder();
		//atrs de clase padre producto
		sb.append(p.getCodigoSKU()).append(DELIMITADOR);
		sb.append(p.getNombreProducto()).append(DELIMITADOR);
		sb.append(p.getPrecio()).append(DELIMITADOR);
		sb.append(p.getDescripcion()).append(DELIMITADOR);
		//conversion de calendar a string
		if(p.getFechaIngreso() !=null) {
			sb.append(p.getFechaIngreso().get(Calendar.DAY_OF_MONTH)).append("/");
			sb.append(p.getFechaIngreso().get(Calendar.MONTH)+1).append("/");
			sb.append(p.getFechaIngreso().get(Calendar.YEAR)).append(DELIMITADOR);
		}else {
			sb.append("null").append(DELIMITADOR);
		}
        // Atributos de la clase compuesta InformacionDeStock
        InformacionDeStock ids = p.getIds();
        sb.append(ids.getCantidadDisponible()).append(DELIMITADOR);
        sb.append(ids.getUbicacionAlmacen()).append(DELIMITADOR);
        sb.append(ids.getStockMinimo()).append(DELIMITADOR);
        
        //identifico el tipo de obj para serializar los atributos correctos
        if(p instanceof Hardware) {
        	Hardware h = (Hardware)p;
        	sb.append("Hardware").append(DELIMITADOR);
        	sb.append(h.getTipoComponente()).append(DELIMITADOR);
        	sb.append(h.getGarantiaMeses()).append(DELIMITADOR);
        	sb.append(h.getPesoGramos()).append(DELIMITADOR);
        	sb.append(h.isRequiereInstalacion()).append(DELIMITADOR);
        }else if(p instanceof Software) {
        	Software s = (Software) p;
            sb.append("Software").append(DELIMITADOR);
            sb.append(s.getPlataformaCompatible()).append(DELIMITADOR);
            sb.append(s.getTipoLicencia().toString()).append(DELIMITADOR);
            sb.append(s.getVersion()).append(DELIMITADOR);
            sb.append(s.getTamanioDescarga()).append(DELIMITADOR);
            sb.append(s.isRequiereDrivers()).append(DELIMITADOR);
        }
        
        return sb.toString();
	}
	private Producto deserializarProducto(String linea) {
        String[] datos = linea.split(DELIMITADOR);
        
        // Validar si la línea tiene suficientes datos
        if (datos.length < 9) { // Atributos mínimos para un producto
            return null;
        }

        // Deserializar los atributos de la clase padre Producto
        String codigoSKU = datos[0];
        String nombreProducto = datos[1];
        double precio = Double.parseDouble(datos[2]);
        String descripcion = datos[3];
        Calendar fechaIngreso = null;
        try {
             // Formato de fecha dd/mm/yyyy
            String[] fechaPartes = datos[4].split("/");
            if (fechaPartes.length == 3) {
                 fechaIngreso = Calendar.getInstance();
                 fechaIngreso.set(Integer.parseInt(fechaPartes[2]), Integer.parseInt(fechaPartes[1]) - 1, Integer.parseInt(fechaPartes[0]));
            }
        } catch (NumberFormatException e) {
            System.err.println("Error en el formato de fecha de la línea: " + linea);
        }

        // Deserializar los atributos de la clase compuesta InformacionDeStock
        int cantidadDisponible = Integer.parseInt(datos[5]);
        String ubicacionAlmacen = datos[6];
        int stockMinimo = Integer.parseInt(datos[7]);
        InformacionDeStock ids = new InformacionDeStock(cantidadDisponible, ubicacionAlmacen, stockMinimo);
        
        // El tipo de producto es el dato[8]
        String tipoProducto = datos[8];
        
        // Deserializar los atributos de las clases hijas
        if ("Hardware".equals(tipoProducto)) {
            // Se asegura de que la línea tiene los datos de Hardware
            if (datos.length < 13) return null;
            String tipoComponente = datos[9];
            int garantiaMeses = Integer.parseInt(datos[10]);
            double pesoGramos = Double.parseDouble(datos[11]);
            boolean requiereInstalacion = Boolean.parseBoolean(datos[12]);
            return new Hardware(codigoSKU, nombreProducto, precio, descripcion, fechaIngreso, ids, 
                                tipoComponente, garantiaMeses, pesoGramos, requiereInstalacion);
        } else if ("Software".equals(tipoProducto)) {
            // Se asegura de que la línea tiene los datos de Software
            if (datos.length < 14) return null;
            String plataformaCompatible = datos[9];
            TipoLicencia tipoLicencia = TipoLicencia.valueOf(datos[10]);
            String version = datos[11];
            long tamanioDescarga = Long.parseLong(datos[12]);
            boolean requiereDrivers = Boolean.parseBoolean(datos[13]);
            return new Software(codigoSKU, nombreProducto, precio, descripcion, fechaIngreso, ids,
                                plataformaCompatible, tipoLicencia, version, tamanioDescarga, requiereDrivers);
        }
        
        return null; // En caso de que el tipo de producto no sea reconocido
    }
	
	
	
	@Override
	public void generarEstadisticasJSON() {
		// TODO Auto-generated method stub
		
	}

}
