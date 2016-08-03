package controlador.servidor;

import gui.servidor.JanelaServidor;
import java.io.IOException;
import rede.protocolo.Protocolo;
import rede.servidor.Servidor;
import simulador.Simulador;

public class ControladorServidor {
    
    private Servidor servidor;
    private Protocolo protocolo;
    
    private final JanelaServidor janela;
    private Simulador simulador;
    
    public ControladorServidor() {
        janela = new JanelaServidor(this);
        //protocolo = new Protocolo(this);
    }
    
    public void iniciarServidor(int porta, int numJogadores, int numRodadas, int investimento) throws IOException{
        servidor = new Servidor(porta, protocolo);
        simulador = new Simulador();
        simulador.iniciarJogo(numJogadores, numRodadas, investimento);
        
        servidor.start();
    }
    
    public void fecharServidor(){
        try {
            servidor.stop();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
    
    public String getEndereco(){
        return servidor.getEndereco();
    }
    
    public int getPorta(){
        return servidor.getPorta();
    }
    
    public int getTotalRodadas(){
        return simulador.getRodadasTotal();
    }
    
    public int getTotalJogadores(){
        return simulador.getNumJogadores();
    }

}
