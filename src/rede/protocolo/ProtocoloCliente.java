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
    public void receber(String msg) {
        String[] cabecalho = Protocolo.getCabecalho(msg);
        msg = Protocolo.decodificarCabecalho(msg);
        
        switch (Protocolo.getTipo(cabecalho)){
            case Protocolo.TIPO_MENSAGEM:
                controlador.mostrarMensagem(msg);
                return;
        }
        
        switch (estado) {
            case ESPERANDO_PARAMETROS_SERVIDOR: // Recebe total de rodadas e investimento inicial
                controlador.receberParametrosServidor(msg);
                estado = ESPERANDO_DEMANDA_RODADA;
                break;
            case ESPERANDO_DEMANDA_RODADA: // Recebe demanda
                controlador.receberDemandaRodada(msg);
                estado = ESPERANDO_FIM_RODADA;
                break;
            case ESPERANDO_FIM_RODADA: // Recebe informações da rodada e quantos carros vendeu
                controlador.receberInformacoesRodada(msg);
                break;
        }
    }
    
    public void servidorDesconectado(){
        controlador.servidorDesconectado();
    }
    
}
