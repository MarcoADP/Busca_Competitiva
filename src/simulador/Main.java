package simulador;

import gui.Janela;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Simulador simulador = new Simulador();
                Janela janela = new Janela();
            }
        });
    }
    
}
