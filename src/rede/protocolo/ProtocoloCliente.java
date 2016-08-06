package rede.protocolo;

import controlador.cliente.ControladorCliente;

public class ProtocoloCliente implements Protocolo {

    public static final int ESPERANDO_PARAMETROS_SERVIDOR = 0;
    public static final int ESPERANDO_DEMANDA_RODADA = 1;
    public static final int ESPERANDO_FIM_RODADA = 2;
    
    private int estado;
    
    private final ControladorCliente controlador;
    
    public ProtocoloCliente(ControladorCliente controlador) {
        this.controlador = controlador;
        estado = ESPERANDO_PARAMETROS_SERVIDOR;
    }

    @Override
    public void enviar(String msg) {
        
    }

    @Override
    public void receber(String msg) {
        switch (estado) {
            case ESPERANDO_PARAMETROS_SERVIDOR: // Recebe total de rodadas e investimento inicial
                controlador.receberParametrosServidor(msg);
                estado = ESPERANDO_DEMANDA_RODADA;
                break;
            case ESPERANDO_DEMANDA_RODADA: // Recebe demanda
                break;
            case ESPERANDO_FIM_RODADA: // Recebe informações da rodada e quantos carros vendeu
                break;
        }
    }
    
    public void servidorDesconectado(){
        controlador.servidorDesconectado();
    }
    
}
