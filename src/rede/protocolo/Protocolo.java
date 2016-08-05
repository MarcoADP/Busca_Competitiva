package rede.protocolo;

public interface Protocolo {
    
    abstract void enviar(String msg);
    
    abstract void receber(String msg);
    
}
