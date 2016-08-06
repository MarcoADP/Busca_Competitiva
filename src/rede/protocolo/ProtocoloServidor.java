package rede.protocolo;

import controlador.servidor.ControladorServidor;
import gui.AreaLog;
import rede.servidor.Servidor;

public class ProtocoloServidor implements Protocolo {
    
    public static final int ESPERANDO_CONEXOES = 0;
    public static final int ESPERANDO_PARAMETROS = 1;
    public static final int ESPERANDO_DECISAO_RODADA = 2;
    
    private int estado;
    
    private final ControladorServidor controlador;
    private Servidor servidor;
    
    public ProtocoloServidor(ControladorServidor controlador) {
        this.controlador = controlador;
        estado = ESPERANDO_CONEXOES;
    }

    @Override
    public void receber(String msg) {
        switch (estado) {
            case ESPERANDO_CONEXOES: // Recebe id do cliente
                synchronized(controlador){
                    controlador.addCliente(msg);
                }
                
                AreaLog.appendLog("Conexão do cliente ["+msg+"] estabelecida.\n");
                
                if (controlador.todosConectados()){
                    AreaLog.appendLog("Todos os jogadores conectados. Servidor fechado para conexões.\n");
                    estado = ESPERANDO_PARAMETROS;
                    AreaLog.appendLog("\nEsperando parâmetros da empresa dos jogadores...\n");
                    controlador.notificarTodosConectados();
                }
                
                break;
            case ESPERANDO_PARAMETROS: // Recebe nome empresa, tipo de fábrica e tipo de carro
                break;
            case ESPERANDO_DECISAO_RODADA: // Recebe tipo de marketing, funcionários a contratar e tipo de preco de carro
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
