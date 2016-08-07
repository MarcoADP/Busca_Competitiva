package rede.protocolo;

import controlador.servidor.ControladorServidor;
import gui.AreaLog;

public class ProtocoloServidor implements Protocolo {
    
    public static final int ESPERANDO_CONEXOES = 0;
    public static final int ESPERANDO_PARAMETROS = 1;
    public static final int ESPERANDO_DECISAO_RODADA = 2;
    
    private int estado;
    
    private final ControladorServidor controlador;
    
    private int esperandoJogador;
    
    public ProtocoloServidor(ControladorServidor controlador) {
        this.controlador = controlador;
        estado = ESPERANDO_CONEXOES;
        esperandoJogador = 0;
    }

    @Override
    public void receber(String msg) {
        String[] cabecalho = Protocolo.getCabecalho(msg);
        msg = Protocolo.decodificarCabecalho(msg);
        
        switch (estado) {
            case ESPERANDO_CONEXOES: // Recebe id do cliente
                synchronized(controlador){
                    controlador.addCliente(msg);
                }
                
                if (controlador.todosConectados()){
                    AreaLog.appendLog("Todos os jogadores conectados. Servidor fechado para conexões.\n");
                    AreaLog.appendLog("\nEsperando parâmetros da empresa dos jogadores...\n");
                    estado = ESPERANDO_PARAMETROS;
                    controlador.notificarTodosConectados();
                }
                
                break;
            case ESPERANDO_PARAMETROS: // Recebe nome empresa, tipo de fábrica e tipo de carro
                controlador.receberParametrosJogador(msg);
                
                esperandoJogador++;
                if (esperandoJogador == controlador.getJogadoresConectados()){
                    AreaLog.appendLog("Todos os jogadores enviaram seus parâmetros.\n");
                    AreaLog.appendLog("\nO jogo começou.\nAguardando decisões dos jogadores.\n");
                    estado = ESPERANDO_DECISAO_RODADA;
                    controlador.notificarRecebimentoParametros();
                    esperandoJogador = 0;
                }
                break;
            case ESPERANDO_DECISAO_RODADA: // Recebe tipo de marketing, funcionários a contratar e tipo de preco de carro
                controlador.receberDecisaoRodadaJogador(msg);
                
                esperandoJogador++;
                if (esperandoJogador == controlador.getJogadoresConectados()){
                    AreaLog.appendLog("Todos os jogadores realizaram suas jogadas.\n");
                    controlador.notificarRecebimentoDecisaoRodada();
                    esperandoJogador = 0;
                }
                break;
        }
    }
    
    public void disconectarCliente(String id){
        AreaLog.appendLog("Cliente [" + id + "] desconectado.\n");

        synchronized (controlador) {
            controlador.removeCliente(id);
        }
    }
    
}
