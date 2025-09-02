package modeloDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase de utilidad con métodos estáticos para leer datos desde archivos de texto,
 * diseñados para poblar componentes de la interfaz gráfica como JComboBox o JList.
 */
public class LecturaArchsDAO {

    /**
     * Lee un archivo de texto simple donde cada línea es un ítem para la lista.
     * Ideal para listas estáticas y simples.
     *
     * @param nombreArchivo El path del archivo a leer (ej: "marcas.txt").
     * @return Un ArrayList<String> con todos los ítems leídos del archivo.
     */
    public static ArrayList<String> leerListaEstatica(String nombreArchivo) {
        ArrayList<String> lista = new ArrayList<>();
        // Usamos try-with-resources para que el Scanner se cierre automáticamente
        try (Scanner scanner = new Scanner(new File(nombreArchivo))) {
            while (scanner.hasNextLine()) {
                lista.add(scanner.nextLine().trim()); // Agregamos cada línea a la lista
            }
        } catch (FileNotFoundException e) {
            // Si el archivo no se encuentra, imprimimos un error en la consola.
            System.err.println("Archivo no encontrado para lista estática: " + nombreArchivo);
        }
        return lista;
    }

    /**
     * Lee un archivo de texto con múltiples columnas separadas por un punto y coma (;)
     * y extrae los datos de una columna específica.
     *
     * @param nombreArchivo El path del archivo a leer (ej: "productos.txt").
     * @param columnaAExtraer El índice de la columna a extraer (0 para la primera, 1 para la segunda, etc.).
     * @return Un ArrayList<String> con los datos de la columna especificada.
     */
    public static ArrayList<String> leerArchivoDeListas(String nombreArchivo, int columnaAExtraer) {
        ArrayList<String> lista = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(nombreArchivo))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] columnas = linea.split(";"); // Separamos la línea por el punto y coma

                // Verificamos que la línea tenga suficientes columnas para evitar errores
                if (columnas.length > columnaAExtraer) {
                    lista.add(columnas[columnaAExtraer].trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado para lista de varias columnas: " + nombreArchivo);
        }
        return lista;
    }

    /**
     * Lee un archivo de formato de ancho fijo y filtra las líneas según un criterio
     * para poblar una lista dependiente.
     *
     * @param nombreArchivo El path del archivo a leer (ej: "modelos.txt").
     * @param criterio El string que debe coincidir en la primera parte de la línea.
     * @return Un ArrayList<String> con los ítems que coinciden con el criterio.
     */
    public static ArrayList<String> leerListaDependiente(String nombreArchivo, String criterio) {
        ArrayList<String> listaDependiente = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(nombreArchivo))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                // Asumimos un ancho fijo de 20 para el criterio y 30 para el item. Ajustar si es necesario.
                if (linea.length() >= 50) { 
                    String criterioEnLinea = linea.substring(0, 20).trim();
                    if (criterioEnLinea.equalsIgnoreCase(criterio)) {
                        // La segunda columna empieza en el carácter 20.
                        String item = linea.substring(20, 50).trim();
                        listaDependiente.add(item);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado para lista dependiente: " + nombreArchivo);
        }
        return listaDependiente;
    }
}