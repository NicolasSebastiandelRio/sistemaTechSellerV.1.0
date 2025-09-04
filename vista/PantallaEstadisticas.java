package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PantallaEstadisticas extends JPanel {

    // --- Componentes de la Vista ---
    private JTextArea areaEstadistica1;
    private JTextArea areaEstadistica2;
    private JTextArea areaEstadistica3;
    private JButton btnGenerar;

    public PantallaEstadisticas() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- 1. PANEL CENTRAL: Contenedor de todas las estadísticas ---
        JPanel panelCentral = new JPanel(new GridLayout(0, 1, 10, 10)); // Una columna para apilar las estadísticas

        // --- Estadística 1: Valor total de atributo numérico ---
        areaEstadistica1 = new JTextArea("Aquí se mostrará el valor total de un atributo numérico...");
        configurarTextArea(areaEstadistica1, "Valor Total (Hardware con Check activo, últimos 6 meses)");

        // --- Estadística 2: Entidades que cumplen una condición ---
        areaEstadistica2 = new JTextArea("Aquí se mostrarán los 4 datos de entidades que cumplen una condición...");
        configurarTextArea(areaEstadistica2, "Entidades sin un atributo específico");

        // --- Estadística 3: Cantidad de entidades con suma de atributos ---
        areaEstadistica3 = new JTextArea("Aquí se mostrará la cantidad de entidades cuya suma de atributos es menor a un número al azar...");
        configurarTextArea(areaEstadistica3, "Conteo de Entidades vs. Valor Aleatorio");

        // Agregamos las áreas con su scroll al panel central
        panelCentral.add(new JScrollPane(areaEstadistica1));
        panelCentral.add(new JScrollPane(areaEstadistica2));
        panelCentral.add(new JScrollPane(areaEstadistica3));
        
        this.add(panelCentral, BorderLayout.CENTER);

        // --- 2. PANEL INFERIOR: Botón de Acción ---
        JPanel panelBoton = new JPanel(); // FlowLayout por defecto
        btnGenerar = new JButton("Calcular Estadísticas y Guardar JSON");
        btnGenerar.setFont(new Font("Arial", Font.BOLD, 14));
        panelBoton.add(btnGenerar);

        this.add(panelBoton, BorderLayout.SOUTH);
    }
    
    /**
     * Método de utilidad para configurar la apariencia de los JTextArea.
     */
    private void configurarTextArea(JTextArea textArea, String titulo) {
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBorder(BorderFactory.createTitledBorder(titulo));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    }

    // --- Getters para que el Controlador pueda interactuar con la vista ---

    public JTextArea getAreaEstadistica1() {
        return areaEstadistica1;
    }

    public JTextArea getAreaEstadistica2() {
        return areaEstadistica2;
    }

    public JTextArea getAreaEstadistica3() {
        return areaEstadistica3;
    }

    public JButton getBtnGenerar() {
        return btnGenerar;
    }
}