package simulador;

import gui.TelaPrincipal;

public class Simulador {
    
    TelaPrincipal tela;
    
    public Simulador(){
        novoJogo();
    }
    
    public void novoJogo(){
        tela = new TelaPrincipal(this);
    }
}
