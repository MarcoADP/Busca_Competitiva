package controlador.cliente;

import gui.cliente.JanelaCliente;
import modelos.Empresa;
import rede.cliente.Cliente;
import rede.protocolo.Protocolo;
import rede.protocolo.ProtocoloCliente;
import simulador.SimuladorCliente;

public class ControladorCliente {

    private Cliente cliente;
    private ProtocoloCliente protocolo;

    private final JanelaCliente janela;
    private SimuladorCliente simulador;
    
    String clienteID;

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
        
        if (verificaAcabou(parametros)){
            return;
        }
        
        clienteID = parametros[0];
        int rodadasTotal = Integer.parseInt(parametros[1]);
        int investimento = Integer.parseInt(parametros[2]);
        
        simulador.iniciarSimulador(rodadasTotal, investimento);
        
        janela.habilitarBotaoContinuar();
    }
    
    public void receberDemandaRodada(String mensagem){
        String[] parametros = mensagem.split("\\|");
        
        if (verificaAcabou(parametros)){
            return;
        }
        
        int demanda = Integer.parseInt(parametros[0]);
        simulador.setDemandaPorRodada(demanda);
        
        janela.habilitarBotaoContinuar();
    }
    
    public void receberInformacoesRodada(String mensagem){
        String[] parametros = mensagem.split("\\|");
        
        if (verificaAcabou(parametros)){
            return;
        }
        
        if (parametros[0].equals("ACABOU")){
            String idVencedor = parametros[1];
            String infoRodada = parametros[2];
            janela.mostrarInfoRodada(infoRodada);
            if (idVencedor.equals(clienteID)){
                janela.mostrarMsgVenceu();
            } else {
                janela.mostrarMsgPerdeu(idVencedor);
            }
            return;
        }
        
        int carrosVendidos = Integer.parseInt(parametros[0]);
        int demanda = Integer.parseInt(parametros[1]);
        String infoRodada = parametros[2];
            
        janela.mostrarInfoRodada(infoRodada);
        janela.habilitarBotaoContinuarRodada();
            
        simulador.proximaRodada(demanda, carrosVendidos);
    }
    
    private boolean verificaAcabou(String parametros[]){
        if (parametros[0].equals("PERDEU")){
            perdeu();
            return true;
        }
        
        if (parametros[0].equals("VENCEU")){
            venceu();
            return true;
        }
        return false;
    }
    
    public void enviarMensagemParametros(String nome){
        simulador.getEmpresa().setNome(nome);
        String msg = criarMensagemParametros();
        cliente.send(Protocolo.adicionarCabecalho(msg, Protocolo.TIPO_DADOS));
    }

    public String criarMensagemParametros() {
        //NOME DA EMPRESA, TIPO FABRICA, MODELO CARRO
        String mensagem = "";
        mensagem += clienteID+"|";
        mensagem += simulador.getEmpresa().getNome()+"|";
        mensagem += simulador.getEmpresa().getFabrica().getNome().toUpperCase()+"|";
        mensagem += simulador.getEmpresa().getCarro().getModelo()+"|";
        return mensagem;
    }
    
    public void enviarMensagemRodada(){
        String mensagem = criarMensagemRodada();
        cliente.send(Protocolo.adicionarCabecalho(mensagem, Protocolo.TIPO_DADOS));
    }

    public String criarMensagemRodada() {
        //FUNCIONARIOS A CONTRATAR, TIPO CARRO, TIPO MARKENTING
        String mensagem = "";
        mensagem += clienteID+"|";
        mensagem += simulador.getEmpresa().getFuncionariosAContratar()+"|";
        mensagem += simulador.getEmpresa().getCarro().getTipoPreco()+"|";
        mensagem += simulador.getEmpresa().getTipoMarketing()+"|";
        return mensagem;
    }
    
    public void mostrarMensagem(String msg){
        janela.mostrarMsgAviso(msg);
    }
    
    public void servidorDesconectado(){
        janela.mostrarMsgErro("ERRO: Servidor desconectado.");
        janela.novoJogo();
    }
    
    public void perdeu(){
        janela.mostrarMsgPerdeu(null);
        janela.novoJogo();
    }
    
    public void venceu(){
        janela.mostrarMsgVenceu();
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
