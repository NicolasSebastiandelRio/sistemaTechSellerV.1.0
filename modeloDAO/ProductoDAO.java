package modeloDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import excepciones.EntidadYaExistenteException;
import modelo.Hardware;
import modelo.InformacionDeStock;
import modelo.Producto;
import modelo.Software;
import modelo.TipoLicencia;
import modelo.Vendedor;

public class ProductoDAO implements IProductoDAO {
    private static final String NOMBRE_ARCHIVO = "productos.txt";
    private static final String ARCHIVO_JSON = "estadisticas.json";
    private static final String DELIMITADOR = ";";

    @Override
    public void guardarProducto(Producto p) throws EntidadYaExistenteException {
        if (buscarProducto(p.getCodigoSKU()) != null) {
            throw new EntidadYaExistenteException("Producto con SKU = " + p.getCodigoSKU() + " ya existe");
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            pw.println(serializarProducto(p));
        } catch (IOException e) {
            System.err.println("Error al guardar producto: " + e.getMessage());
        }
    }

    @Override
    public Producto buscarProducto(String codigoSKU) {
        try (Scanner scanner = new Scanner(new File(NOMBRE_ARCHIVO))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                if (linea.trim().isEmpty() || linea.startsWith("#")) continue;
                Producto p = deserializarProducto(linea);
                if (p != null && p.getCodigoSKU().equalsIgnoreCase(codigoSKU)) {
                    return p;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado, se creará al guardar el primer producto.");
        }
        return null;
    }

    @Override
    public void actualizarProducto(Producto p) {
        ArrayList<Producto> todos = obtenerTodosLosProductos();
        try (PrintWriter pw = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO, false))) {
            for (Producto prod : todos) {
                if (prod.getCodigoSKU().equals(p.getCodigoSKU())) {
                    pw.println(serializarProducto(p));
                } else {
                    pw.println(serializarProducto(prod));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminarProducto(String codigoSKU) {
        ArrayList<Producto> todos = obtenerTodosLosProductos();
        try (PrintWriter pw = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO, false))) {
            for (Producto prod : todos) {
                if (!prod.getCodigoSKU().equals(codigoSKU)) {
                    pw.println(serializarProducto(prod));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Producto> obtenerTodosLosProductos() {
        ArrayList<Producto> lista = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(NOMBRE_ARCHIVO))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                if (linea.trim().isEmpty() || linea.startsWith("#")) continue;
                Producto p = deserializarProducto(linea);
                if (p != null) {
                    lista.add(p);
                }
            }
        } catch (FileNotFoundException e) {
            e.getLocalizedMessage();
        }
        return lista;
    }

    // --- MÉTODOS DE ESTADÍSTICAS ---

    /**
     * **Estadística 1:** Calcula el valor total de un atributo numérico (precio) de productos Hardware
     * que requieran instalación y hayan sido ingresados en los últimos 6 meses.
     */
    @Override
    public double calcularValorTotalHardwareReciente() {
        double total = 0.0;
        Calendar haceSeisMeses = Calendar.getInstance();
        haceSeisMeses.add(Calendar.MONTH, -6);

        for (Producto p : obtenerTodosLosProductos()) {
            if (p instanceof Hardware) {
                Hardware h = (Hardware) p;
                if (h.isRequiereInstalacion() && h.getFechaIngreso().after(haceSeisMeses)) {
                    total += h.getPrecio();
                }
            }
        }
        return total;
    }

    /**
     * **Estadística 2:** Devuelve una cadena con 4 datos de los productos cuyo nombre NO contenga
     * el criterio de búsqueda.
     */
    @Override
    public String obtenerInfoProductosSinCriterio(String criterio) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-30s %-10s %-15s\n", "SKU", "Nombre", "Precio", "Ubicación"));
        sb.append("------------------------------------------------------------------\n");
        
        int contador = 0;
        for (Producto p : obtenerTodosLosProductos()) {
            if (!p.getNombreProducto().toLowerCase().contains(criterio.toLowerCase())) {
                sb.append(String.format("%-10s %-30s %-10.2f %-15s\n", 
                    p.getCodigoSKU(), 
                    p.getNombreProducto(), 
                    p.getPrecio(), 
                    p.getIds().getUbicacionAlmacen()));
                contador++;
            }
        }
        if (contador == 0) return "No se encontraron productos que cumplan la condición.";
        return sb.toString();
    }

    /**
     * **Estadística 3:** Cuenta cuántos productos tienen una suma de precio + stock mínimo
     * menor a un número generado al azar.
     */
    @Override
    public String contarProductosSumaMenorAleatorio() {
        Random rand = new Random();
        int valorAleatorio = rand.nextInt(500) + 50; // Un número al azar entre 50 y 549
        int contador = 0;

        for (Producto p : obtenerTodosLosProductos()) {
            if ((p.getPrecio() + p.getIds().getStockMinimo()) < valorAleatorio) {
                contador++;
            }
        }
        return "Valor aleatorio generado: " + valorAleatorio + "\n" +
               "Cantidad de productos cuya suma (Precio + Stock Mínimo) es menor: " + contador;
    }

    
    @Override
    public void generarEstadisticasJSON() {
        double valorTotal = calcularValorTotalHardwareReciente();
        String conteoVsAleatorio = contarProductosSumaMenorAleatorio();

        try (OutputStream fos = new FileOutputStream(ARCHIVO_JSON, true); // true para modo append
        		JsonGenerator gen = Json.createGenerator(fos)) {
            
            Calendar hoy = Calendar.getInstance();
            String fechaHoy = String.format("%1$tY-%1$tm-%1$td", hoy);

            gen.writeStartObject(); // {
            gen.writeStartObject(fechaHoy); // "2025-09-02": {
                gen.write("valorTotalHardwareReciente", valorTotal);
                gen.write("conteoVsAleatorio", conteoVsAleatorio);
            gen.writeEnd(); // }
            gen.writeEnd(); // }
            gen.write("\n"); // Agrega un salto de línea para separar entradas

        } catch (IOException e) {
            System.err.println("Error al generar el archivo JSON: " + e.getMessage());
        }
    }


    private String serializarProducto(Producto p) {
        StringBuilder sb = new StringBuilder();
        // 1. Tipo de Producto
        if (p instanceof Hardware) {
            sb.append("Hardware").append(DELIMITADOR);
        } else if (p instanceof Software) {
            sb.append("Software").append(DELIMITADOR);
        }

        // 2. Atributos de Producto
        sb.append(p.getCodigoSKU()).append(DELIMITADOR);
        sb.append(p.getNombreProducto()).append(DELIMITADOR);
        sb.append(p.getPrecio()).append(DELIMITADOR);
        sb.append(p.getDescripcion()).append(DELIMITADOR);
        if (p.getFechaIngreso() != null) {
            sb.append(String.format("%1$td/%1$tm/%1$tY", p.getFechaIngreso())).append(DELIMITADOR);
        } else {
            sb.append("null").append(DELIMITADOR);
        }
        
        // 3. Atributos de InformacionDeStock
        InformacionDeStock ids = p.getIds();
        sb.append(ids.getCantidadDisponible()).append(DELIMITADOR);
        sb.append(ids.getUbicacionAlmacen()).append(DELIMITADOR);
        sb.append(ids.getStockMinimo()).append(DELIMITADOR);

        // 4. Atributos específicos
        if (p instanceof Hardware) {
            Hardware h = (Hardware) p;
            sb.append(h.getTipoComponente()).append(DELIMITADOR);
            sb.append(h.getGarantiaMeses()).append(DELIMITADOR);
            sb.append(h.getPesoGramos()).append(DELIMITADOR);
            sb.append(h.isRequiereInstalacion()); // Último de Hardware
        } else if (p instanceof Software) {
            Software s = (Software) p;
            sb.append(s.getPlataformaCompatible()).append(DELIMITADOR);
            sb.append(s.getTipoLicencia().toString()).append(DELIMITADOR);
            sb.append(s.getVersion()).append(DELIMITADOR);
            sb.append(s.getTamanioDescarga()).append(DELIMITADOR);
            sb.append(s.isRequiereDrivers()); // Último de Software
        }
        
        // 5. NUEVO: Serialización de la lista de Vendedores
        sb.append(DELIMITADOR); // Separador para la nueva "columna"
        if (p.getVendedores() != null && !p.getVendedores().isEmpty()) {
            for (int i = 0; i < p.getVendedores().size(); i++) {
                sb.append(p.getVendedores().get(i).getNumLegajo());
                if (i < p.getVendedores().size() - 1) {
                    sb.append(","); // Separamos los legajos por coma
                }
            }
        } else {
            sb.append("N/A"); // Si no hay vendedores asignados
        }

        return sb.toString();
    }

    private Producto deserializarProducto(String linea) {
        String[] datos = linea.split(DELIMITADOR);
        Producto producto = null;

        String tipoProducto = datos[0];
        String codigoSKU = datos[1];
        String nombreProducto = datos[2];
        double precio = Double.parseDouble(datos[3]);
        String descripcion = datos[4];
        Calendar fechaIngreso = null;
        if (!datos[5].equals("null")) {
            String[] fechaPartes = datos[5].split("/");
            if (fechaPartes.length == 3) {
                fechaIngreso = Calendar.getInstance();
                fechaIngreso.set(Integer.parseInt(fechaPartes[2]), Integer.parseInt(fechaPartes[1]) - 1, Integer.parseInt(fechaPartes[0]));
            }
        }
        int cantidadDisponible = Integer.parseInt(datos[6]);
        String ubicacionAlmacen = datos[7];
        int stockMinimo = Integer.parseInt(datos[8]);
        InformacionDeStock ids = new InformacionDeStock(cantidadDisponible, ubicacionAlmacen, stockMinimo);

        if ("Hardware".equals(tipoProducto)) {
            String tipoComponente = datos[9];
            int garantiaMeses = Integer.parseInt(datos[10]);
            double pesoGramos = Double.parseDouble(datos[11]);
            boolean requiereInstalacion = Boolean.parseBoolean(datos[12]);
            producto = new Hardware(codigoSKU, nombreProducto, precio, descripcion, fechaIngreso, ids, tipoComponente, garantiaMeses, pesoGramos, requiereInstalacion);
        } else if ("Software".equals(tipoProducto)) {
            String plataformaCompatible = datos[9];
            TipoLicencia tipoLicencia = TipoLicencia.valueOf(datos[10]);
            String version = datos[11];
            long tamanioDescarga = Long.parseLong(datos[12]);
            boolean requiereDrivers = Boolean.parseBoolean(datos[13]);
            producto = new Software(codigoSKU, nombreProducto, precio, descripcion, fechaIngreso, ids, plataformaCompatible, tipoLicencia, version, tamanioDescarga, requiereDrivers);
        }

        // NUEVO: Deserialización de la lista de Vendedores
        if (producto != null) {
            int indiceVendedores = ("Hardware".equals(tipoProducto)) ? 13 : 14;
            if (datos.length > indiceVendedores && !datos[indiceVendedores].equals("N/A")) {
                String[] legajos = datos[indiceVendedores].split(",");
                for (String legajoStr : legajos) {
                    Vendedor v = new Vendedor();
                    v.setNumLegajo(Integer.parseInt(legajoStr.trim()));
                    producto.getVendedores().add(v);
                }
            }
        }

        return producto;
    }
}