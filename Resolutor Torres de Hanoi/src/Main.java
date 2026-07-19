import modelo.HanoiModel;
import vista.HanoiView;
import controlador.HanoiController;

public class Main {
    public static void main(String[] args) {
      

        HanoiModel model = new HanoiModel();
        HanoiView view = new HanoiView();
        HanoiController controller = new HanoiController(model, view);
        
        // Hacemos visible la ventana
        controller.iniciar();
    }
}