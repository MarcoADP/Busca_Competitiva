package controlador.servidor;

import gui.AreaLog;
import gui.servidor.JanelaServidor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import rede.protocolo.Protocolo;
import rede.protocolo.ProtocoloServidor;
import rede.servidor.Servidor;
import simulador.SimuladorServidor;

public class ControladorServidor {
    
    private Servidor servidor;
    private ProtocoloServidor protocolo;
    
    private final JanelaServidor janela;
    private SimuladorServidor simulador;
    
    private int jogadoresConectados;
    
    private final List<String> listaID;
    
    private String logFinal;
    
    public ControladorServidor() {
        listaID = new ArrayList<>();
        janela = new JanelaServidor(this);
        jogadoresConectados = 0;
        logFinal = "======================================\n";
        logFinal += "          RELATÓRIO FINAL:";
        logFinal += "\n======================================\n";
    }
    
    public void iniciarServidor(int porta, int numJogadores, int numRodadas, int investimento) throws IOException{
        protocolo = new ProtocoloServidor(this);
        servidor = new Servidor(porta, protocolo);
        simulador = new SimuladorServidor(this);
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
    
    public void receberParametrosJogador(String mensagem){
        String[] parametros = mensagem.split("\\|");
        String id = parametros[0];
        String nomeEmpresa = parametros[1];
        String fabrica = parametros[2];
        String modeloCarro = parametros[3];
        
        AreaLog.appendLog("Parâmetros do cliente["+id+"] recebido\n");
        AreaLog.appendLog("\tNome da Empresa: "+nomeEmpresa+"\n");
        AreaLog.appendLog("\tNome da Empresa: "+fabrica+"\n");
        
        simulador.addEmpresa(id, nomeEmpresa, fabrica, modeloCarro);
    }
    
    public void receberDecisaoRodadaJogador(String mensagem){
        String[] parametros = mensagem.split("\\|");
        
        String id = parametros[0];
        int funcionariosContratar = Integer.parseInt(parametros[1]);
        int tipoPrecoCarro = Integer.parseInt(parametros[2]);
        int tipoMarketing = Integer.parseInt(parametros[3]);
        
        simulador.realizarAcaoEmpresa(id, funcionariosContratar, tipoPrecoCarro, tipoMarketing);
    }
    
    public void receberMensagemChat(String mensagem){
        servidor.sendAll(Protocolo.adicionarCabecalho(mensagem, Protocolo.TIPO_MSG_CHAT));
    }
    
    public void addCliente(String id){
        jogadoresConectados++;
        janela.atualizarJogadoresConectados(jogadoresConectados);
        
        AreaLog.appendLog("Conexão do cliente ["+id+"] estabelecida.\n");
        listaID.add(id);
    }
    
    public void removeCliente(String id){
        jogadoresConectados--;
        janela.atualizarJogadoresConectados(jogadoresConectados);
        servidor.removerAtendente(id);
        simulador.removerJogador(id);
        
        for (int i = listaID.size() - 1; i >= 0; i--){
            if (listaID.get(i).equals(id)){
                listaID.remove(i);
                break;
            }
        }
        
        if (jogadoresConectados == 1 && !simulador.acabou()){
            jogadorGanhou(listaID.get(0));
        }
    }
    
    public void jogadorPerdeu(String id, String nomeEmpresa){
        servidor.sendAll(Protocolo.adicionarCabecalho("Jogador ["+id+"] dono da empresa "+nomeEmpresa+" perdeu.", Protocolo.TIPO_MENSAGEM));
        AreaLog.appendLog("\nJogador ["+id+"] dono da empresa "+nomeEmpresa+" perdeu.\n");
        
        removeCliente(id);
        
        String mensagem = "PERDEU|";
        
        servidor.send(id, Protocolo.adicionarCabecalho(mensagem, Protocolo.TIPO_DADOS));
    }
    
    public void jogadorGanhou(String id) {
        AreaLog.appendLog("\nJogador ["+id+"] venceu!\n");
        
        String mensagem = "VENCEU|";
        
        servidor.send(id, Protocolo.adicionarCabecalho(mensagem, Protocolo.TIPO_DADOS));
    }
    
    public void notificarTodosConectados() {
        enviarMensagemConfirmacaoJogadores();
        fecharServidor();
    }
    
    public void enviarMensagemConfirmacaoJogadores(){
        String mensagem;
        mensagem = simulador.getRodadasTotal() + "|";
        mensagem += simulador.getInvestimento() + "|";
        
        for (String id : listaID) {
            servidor.send(id, Protocolo.adicionarCabecalho(id+"|"+mensagem, Protocolo.TIPO_DADOS));
        }
    }
    
    public void notificarRecebimentoParametros(){
        enviarMensagemDemanda();
    }
    
    public void enviarMensagemDemanda(){
        simulador.calcularDemanda();
        int demanda = simulador.getDemandaPorRodada();
        String mensagem = demanda+"|";
        
        AreaLog.appendLog("\nDemanda da rodada "+simulador.getRodadaAtual()+": "+demanda+"\n");
        
        servidor.sendAll(Protocolo.adicionarCabecalho(mensagem, Protocolo.TIPO_DADOS));
    }
    
    public void notificarRecebimentoDecisaoRodada(){
        simulador.proximaRodada();
        janela.atualizarRodadaAtual(simulador.getRodadaAtual());
        AreaLog.appendLog(simulador.getInfoRodada());
        logFinal += simulador.getInfoRodada();
        
        if (simulador.acabou()){
            enviarMensagemAcabou();
        } else {
            enviarMensagemInfoRodada();
        }
    }
    
    public void enviarMensagemInfoRodada(){
        String mensagem = "";
        mensagem += simulador.getDemandaPorRodada() + "|";
        mensagem += simulador.getInfoRodada() + "|";
        
        for (String id : listaID) {
            String msg = simulador.getEmpresa(id).getCarrosVendidosNoMes()+"|";
            servidor.send(id, Protocolo.adicionarCabecalho(msg+mensagem, Protocolo.TIPO_DADOS));
        }
    }
    
    public void enviarMensagemAcabou(){
        AreaLog.appendLog("\nJogador ["+simulador.getIdEmpresa(simulador.verificaVencedor())+"] dono da empresa "+simulador.verificaVencedor().getNome()+" venceu!\n");
        logFinal += "\nJogador ["+simulador.getIdEmpresa(simulador.verificaVencedor())+"] dono da empresa "+simulador.verificaVencedor().getNome()+" venceu!\n";
        
        String mensagem = "ACABOU|";
        mensagem += simulador.getIdEmpresa(simulador.verificaVencedor())+"|";
        mensagem += logFinal+"|";
        
        servidor.sendAll(Protocolo.adicionarCabecalho(mensagem, Protocolo.TIPO_DADOS));
    }
    
    public boolean todosConectados() {
        return simulador.getNumJogadores() == jogadoresConectados;
    }
    
    public int getJogadoresConectados(){
        return jogadoresConectados;
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
