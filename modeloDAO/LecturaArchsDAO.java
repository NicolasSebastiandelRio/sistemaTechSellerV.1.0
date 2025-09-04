package modeloDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LecturaArchsDAO {
    public static ArrayList<String> leerListaEstatica(String nombreArchivo) {
        ArrayList<String> lista = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(nombreArchivo))) {
            while (scanner.hasNextLine()) {
                lista.add(scanner.nextLine().trim()); // Agregamos cada línea a la lista
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado para lista estática: " + nombreArchivo);
        }
        return lista;
    }

    public static ArrayList<String> leerArchivoDeListas(String nombreArchivo, int columnaAExtraer) {
        ArrayList<String> lista = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(nombreArchivo))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] columnas = linea.split(";"); 
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

 
    public static ArrayList<String> leerListaDependiente(String nombreArchivo, String criterio) {
        ArrayList<String> listaDependiente = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(nombreArchivo))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
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