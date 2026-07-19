package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class HanoiView extends JFrame {

    private JSpinner spinDiscos;
    private JTextField txtInicio;
    private JTextField txtObjetivo;
    private JButton btnResolver;
    
    // Controles de navegación visual
    private JButton btnAnterior;
    private JButton btnSiguiente;
    private JLabel lblPasoActual;
    
    // Nuestro canvas personalizado
    private PanelTorres panelTorres;

    public HanoiView() {
        setTitle("Resolutor de Torres de Hanoi - Visualizador Visual");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Panel Superior (Entradas) ---
        JPanel panelEntrada = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelEntrada.setBorder(new EmptyBorder(10, 10, 0, 10));

        panelEntrada.add(new JLabel("Discos:"));
        spinDiscos = new JSpinner(new SpinnerNumberModel(3, 1, 30, 1)); // Límite de 10 visual
        panelEntrada.add(spinDiscos);

        panelEntrada.add(new JLabel("Inicio:"));
        txtInicio = new JTextField("AAA", 9);
        panelEntrada.add(txtInicio);

        panelEntrada.add(new JLabel("Objetivo:"));
        txtObjetivo = new JTextField("CCC", 9);
        panelEntrada.add(txtObjetivo);

        btnResolver = new JButton("Resolver");
        btnResolver.setBackground(new Color(28, 101, 212));
        btnResolver.setForeground(Color.WHITE);
        panelEntrada.add(btnResolver);

        add(panelEntrada, BorderLayout.NORTH);

        // --- Panel Central (Dibujo de Torres) ---
        panelTorres = new PanelTorres();
        panelTorres.setBackground(Color.WHITE);
        add(panelTorres, BorderLayout.CENTER);

        // --- Panel Inferior (Navegación paso a paso) ---
        JPanel panelNavegacion = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelNavegacion.setBorder(new EmptyBorder(0, 10, 10, 10));

        btnAnterior = new JButton("◀ Anterior");
        btnAnterior.setEnabled(false); // Apagado por defecto
        
        lblPasoActual = new JLabel("Paso: 0 / 0");
        lblPasoActual.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnSiguiente = new JButton("Siguiente ▶");
        btnSiguiente.setEnabled(false); // Apagado por defecto

        panelNavegacion.add(btnAnterior);
        panelNavegacion.add(lblPasoActual);
        panelNavegacion.add(btnSiguiente);

        add(panelNavegacion, BorderLayout.SOUTH);
    }

    // --- Panel Interno para Dibujar ---
    class PanelTorres extends JPanel {
        private String estado = "";
        private int numDiscos = 0;

        public void actualizarTorres(String estado, int numDiscos) {
            this.estado = estado;
            this.numDiscos = numDiscos;
            repaint(); // Fuerza a Swing a redibujar el panel
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (estado == null || estado.isEmpty()) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int baseHeight = height - 40;

            // 1. Dibujar los 3 postes y la base
            g2.setColor(Color.DARK_GRAY);
            int[] pegX = {width / 6, width / 2, 5 * width / 6};
            for (int x : pegX) {
                g2.fillRoundRect(x - 5, 80, 10, baseHeight - 80, 5, 5); // Poste
            }
            g2.fillRoundRect(20, baseHeight, width - 40, 15, 10, 10); // Base

            // 2. Dibujar los discos
            int[] conteoPoste = new int[3]; // Cuántos discos hay apilados en cada poste
            
            // Iteramos del disco más grande (numDiscos-1) al más pequeño (0)
            // para dibujarlos de abajo hacia arriba.
            for (int i = numDiscos - 1; i >= 0; i--) {
                int poste = estado.charAt(i) - 'A';
                int count = conteoPoste[poste];
                
                int diskWidth = 40 + (i * 20); // El disco i=0 es el más pequeño
                int diskHeight = 20;
                int x = pegX[poste] - (diskWidth / 2);
                int y = baseHeight - ((count + 1) * diskHeight) - 2; // -2 para pequeña separación

                // Color del disco
                g2.setColor(new Color(100, 150 + (i * 10), 255 - (i * 15))); 
                g2.fillRoundRect(x, y, diskWidth, diskHeight, 15, 15);
                
                g2.setColor(Color.BLACK);
                g2.drawRoundRect(x, y, diskWidth, diskHeight, 15, 15);

                conteoPoste[poste]++;
            }
        }
    }

    // --- Getters y Setters para el Controlador ---

    public int getNumDiscos() { return (int) spinDiscos.getValue(); }
    public String getEstadoInicial() { return txtInicio.getText().trim().toUpperCase(); }
    public String getEstadoObjetivo() { return txtObjetivo.getText().trim().toUpperCase(); }

    public void actualizarGrafico(String estado, int numDiscos, int pasoActual, int totalPasos) {
        lblPasoActual.setText("Paso: " + pasoActual + " / " + totalPasos);
        panelTorres.actualizarTorres(estado, numDiscos);
        
        btnAnterior.setEnabled(pasoActual > 0);
        btnSiguiente.setEnabled(pasoActual < totalPasos);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void addResolverListener(ActionListener listener) { btnResolver.addActionListener(listener); }
    public void addAnteriorListener(ActionListener listener) { btnAnterior.addActionListener(listener); }
    public void addSiguienteListener(ActionListener listener) { btnSiguiente.addActionListener(listener); }
}