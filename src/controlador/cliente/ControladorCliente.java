package controlador.cliente;

import gui.cliente.JanelaCliente;
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
        cliente = new Cliente(endereco, porta);
        cliente.start();
    }
    
    public void fecharCliente(){
        try {
            if (cliente != null){
                cliente.stop();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
