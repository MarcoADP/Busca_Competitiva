package controlador.servidor;

import gui.servidor.JanelaServidor;
import java.io.IOException;
import rede.protocolo.Protocolo;
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
                servidor.close();
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
    
    public void addCliente(String id){
        jogadoresConectados++;
        janela.atualizarJogadoresConectados(jogadoresConectados);
    }
    
    public void removeCliente(String id){
        jogadoresConectados--;
        janela.atualizarJogadoresConectados(jogadoresConectados);
        servidor.removerAtendente(id);
        
        if (jogadoresConectados < 2){
            servidor.sendAll(Protocolo.adicionarCabecalho("Todos os jogadores foram desconectados.", Protocolo.TIPO_MENSAGEM));
        }
    }
    
    public void notificarTodosConectados() {
        enviarMensagemConfirmacaoJogadores();
        fecharServidor();
    }
    
    public void enviarMensagemConfirmacaoJogadores(){
        String mensagem;
        mensagem = simulador.getRodadasTotal() + "|";
        mensagem += simulador.getInvestimento() + "|";
        
        servidor.sendAll(Protocolo.adicionarCabecalho(mensagem, Protocolo.TIPO_DADOS));
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
