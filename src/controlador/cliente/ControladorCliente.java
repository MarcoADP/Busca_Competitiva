package controlador.cliente;

import gui.cliente.JanelaCliente;
import java.net.MalformedURLException;
import modelos.Empresa;
import rede.cliente.Cliente;
import rede.protocolo.Protocolo;

public class ControladorCliente {

    private Cliente cliente;
    private Protocolo protocolo;

    private Empresa empresa;

    private final JanelaCliente janela;

    public ControladorCliente() {
        janela = new JanelaCliente(this);
    }

    public void iniciarCliente(String endereco, int porta, String tipoJogador) throws Exception {
        cliente = new Cliente(this, endereco, porta);
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
    
    public void receberMensagemServidor(String mensagem) throws MalformedURLException{
        if(mensagem.equals("OK!")){
            janela.habilitarBotaoContinuar();
        }
    }
    
    public void enviarMensagemServidor(){
        cliente.send(criarMensagemRodada());
    }

    public String criarMensagemInicial() {
        //NÚMERO DE CAMPOS, NOME DA EMPRESA, TIPO FABRICA, MODELO CARRO
        String mensagem = "3|";
        mensagem += empresa.getNome();
        mensagem += empresa.getFabrica().getNome();
        mensagem += empresa.getCarro().getStringModelo();
        return mensagem;
    }

    public String criarMensagemRodada() {
        //NÚMERO DE CAMPOS, FUNCIONARIOS A CONTRATAR, TIPO CARRO, TIPO MARKENTING
        String mensagem = "3|";
        mensagem += empresa.getFuncionariosAContratar();
        mensagem += empresa.getCarro().getTipoCarro();
        mensagem += empresa.getTipoMarketing();
        return mensagem;
    }

}
