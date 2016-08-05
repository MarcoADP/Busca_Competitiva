package controlador.servidor;

import gui.servidor.JanelaServidor;
import java.io.IOException;
import rede.protocolo.ProtocoloServidor;
import rede.servidor.Servidor;
import simulador.Simulador;

public class ControladorServidor {
    
    private Servidor servidor;
    private ProtocoloServidor protocolo;
    
    private final JanelaServidor janela;
    private Simulador simulador;
    
    private int jogadoresConectados;
    
    public ControladorServidor() {
        janela = new JanelaServidor(this);
        jogadoresConectados = 0;
    }
    
    public void iniciarServidor(int porta, int numJogadores, int numRodadas, int investimento) throws IOException{
        protocolo = new ProtocoloServidor(this);
        servidor = new Servidor(porta, protocolo);
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
    
    public void addCliente(String msg){
        jogadoresConectados++;
        janela.atualizarJogadoresConectados(jogadoresConectados);
    }
    
    public void removeCliente(){
        jogadoresConectados--;
        janela.atualizarJogadoresConectados(jogadoresConectados);
    }
    
    public void notificarTodosConectados() {
        try {
            enviarMensagemConfirmacaoJogadores();

            servidor.stop();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
    
    public void enviarMensagemConfirmacaoJogadores(){
        servidor.sendAll("OK!");
    }
    
    public boolean todosConectados() {
        return simulador.getNumJogadores() == jogadoresConectados;
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
