package controlador.cliente;

import gui.cliente.JanelaCliente;
import modelos.Empresa;
import rede.cliente.Cliente;
import rede.protocolo.ProtocoloCliente;
import simulador.SimuladorCliente;

public class ControladorCliente {

    private Cliente cliente;
    private ProtocoloCliente protocolo;

    private final JanelaCliente janela;
    private SimuladorCliente simulador;

    public ControladorCliente() {
        janela = new JanelaCliente(this);
    }

    public void iniciarCliente(String endereco, int porta, String tipoJogador) throws Exception {
        simulador = new SimuladorCliente(tipoJogador);
        protocolo = new ProtocoloCliente(this);
        cliente = new Cliente(endereco, porta, protocolo);
        cliente.start();
    }

    public void fecharCliente() {
        try {
            if (cliente != null) {
                cliente.stop();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void receberParametrosServidor(String mensagem) {
        String[] parametros = mensagem.split("\\|");
        
        int rodadasTotal = Integer.parseInt(parametros[0]);
        int investimento = Integer.parseInt(parametros[1]);
        
        simulador.iniciarSimulador(rodadasTotal, investimento);

        while (true) {
            try {
                janela.habilitarBotaoContinuar();
                break;
            } catch (NullPointerException e) {

            }
        }
    }
    
    public void enviarMensagemServidor(){
        cliente.send(criarMensagemRodada());
    }

    public String criarMensagemInicial() {
        //NÚMERO DE CAMPOS, NOME DA EMPRESA, TIPO FABRICA, MODELO CARRO
        String mensagem = "3|";
        mensagem += simulador.getEmpresa().getNome()+"|";
        mensagem += simulador.getEmpresa().getFabrica().getNome()+"|";
        mensagem += simulador.getEmpresa().getCarro().getModelo()+"|";
        return mensagem;
    }

    public String criarMensagemRodada() {
        //NÚMERO DE CAMPOS, FUNCIONARIOS A CONTRATAR, TIPO CARRO, TIPO MARKENTING
        String mensagem = "3|";
        mensagem += simulador.getEmpresa().getFuncionariosAContratar()+"|";
        mensagem += simulador.getEmpresa().getCarro().getTipoPreco()+"|";
        mensagem += simulador.getEmpresa().getTipoMarketing()+"|";
        return mensagem;
    }
    
    public void servidorDesconectado(){
        janela.mostrarMsgErro("ERRO: Servidor desconectado.");
        janela.novoJogo();
    }
    
    public Empresa getEmpresa(){
        return simulador.getEmpresa();
    }
    
    public int getInvestimentoInicial(){
        return simulador.getInvestimentoInicial();
    }
    
    public SimuladorCliente getSimulador(){
        return simulador;
    }

}
