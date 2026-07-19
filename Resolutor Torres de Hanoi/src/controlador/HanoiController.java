package controlador;

import modelo.HanoiModel;
import vista.HanoiView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HanoiController {
    private HanoiModel model;
    private HanoiView view;
    
    // Variables para controlar la animación
    private List<String> solucion;
    private int pasoActual;
    private int numDiscosActual;

    public HanoiController(HanoiModel model, HanoiView view) {
        this.model = model;
        this.view = view;
        
        // Enlazamos los 3 botones de la vista
        this.view.addResolverListener(new ResolverListener());
        this.view.addAnteriorListener(new AnteriorListener());
        this.view.addSiguienteListener(new SiguienteListener());
    }

    class ResolverListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            numDiscosActual = view.getNumDiscos();
            String inicio = view.getEstadoInicial();
            String objetivo = view.getEstadoObjetivo();

            // Validaciones
            if (inicio.length() != numDiscosActual || objetivo.length() != numDiscosActual) {
                view.mostrarError("La longitud de los estados debe coincidir con el número de discos.");
                return;
            }
            if (!inicio.matches("[ABC]+") || !objetivo.matches("[ABC]+")) {
                view.mostrarError("Los estados solo pueden contener las letras A, B o C.");
                return;
            }

            // Llamamos al Modelo
            solucion = model.encontrarCaminoMasCorto(inicio, objetivo, numDiscosActual);

            if (solucion.isEmpty()) {
                view.mostrarError("No se encontró una solución válida para esos estados.");
            } else {
                // Configuramos la vista en el Paso 0 (Estado Inicial)
                pasoActual = 0;
                view.actualizarGrafico(solucion.get(pasoActual), numDiscosActual, pasoActual, solucion.size() - 1);
            }
        }
    }

    class AnteriorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (solucion != null && pasoActual > 0) {
                pasoActual--;
                view.actualizarGrafico(solucion.get(pasoActual), numDiscosActual, pasoActual, solucion.size() - 1);
            }
        }
    }

    class SiguienteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (solucion != null && pasoActual < solucion.size() - 1) {
                pasoActual++;
                view.actualizarGrafico(solucion.get(pasoActual), numDiscosActual, pasoActual, solucion.size() - 1);
            }
        }
    }

    public void iniciar() {
        view.setVisible(true);
    }
}