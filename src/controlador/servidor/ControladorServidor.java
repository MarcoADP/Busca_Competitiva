package controlador.servidor;

import gui.servidor.JanelaServidor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rede.protocolo.Protocolo;
import rede.servidor.Atendente;
import rede.servidor.Servidor;
import simulador.Simulador;

public class ControladorServidor {
    
    private Servidor servidor;
    private Protocolo protocolo;
    
    private final JanelaServidor janela;
    private Simulador simulador;
    
    private int jogadoresConectados;
    
    public ControladorServidor() {
        janela = new JanelaServidor(this);
        //protocolo = new Protocolo(this);
        jogadoresConectados = 0;
    }
    
    public void iniciarServidor(int porta, int numJogadores, int numRodadas, int investimento) throws IOException{
        servidor = new Servidor(porta, this);
        simulador = new Simulador();
        simulador.iniciarJogo(numJogadores, numRodadas, investimento);
        
        servidor.start();
    }
    
    public void fecharServidor(){
        try {
            if (servidor != null){
                servidor.stop();
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
    
    public void addCliente(){
        jogadoresConectados++;
        janela.atualizarJogadoresConectados(jogadoresConectados);
        
        if (jogadoresConectados == simulador.getNumJogadores()){
            try {
                servidor.stop();
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }
    
    public void removeCliente(){
        jogadoresConectados--;
        janela.atualizarJogadoresConectados(jogadoresConectados);
    }
    
    public void enviarMensagemConfirmaçaoJogadores(){
        servidor.sendAll("OK!");
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
