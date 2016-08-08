package rede.protocolo;

import java.io.BufferedReader;

public interface Protocolo {
    
    static final short TIPO_MENSAGEM = 0;
    static final short TIPO_DADOS = 1;
    static final short TIPO_MSG_CHAT = 2;
    
    abstract void receber(String msg);
    
    static String adicionarCabecalho(String mensagem, short tipo){
        short tamanho = (short) mensagem.length();
        String cabecalho = tamanho + "|" + tipo + "|#";
        String msg = cabecalho + mensagem;
        return msg;
    }
    
    static String decodificarCabecalho(String mensagem){
        return mensagem.substring(mensagem.indexOf('#')+1);
    }
    
    static String[] getCabecalho(String mensagem){
        String cabecalho = mensagem.substring(0, mensagem.indexOf("#"));
        return cabecalho.split("\\|");
    }
    
    static short getTamanhoMensagem(String[] cabecalho){
        short tamanho = Short.parseShort(cabecalho[0]);
        return tamanho;
    }
    
    static short getTipo(String[] cabecalho){
        short tipo = Short.parseShort(cabecalho[1]);
        return tipo;
    }
    
    static String lerMensagem(BufferedReader in) throws Exception {
        String msg = in.readLine();
        
        if (msg == null){
            return null;
        }
        
        String[] cabecalho = getCabecalho(msg);
        
        short tamanho = getTamanhoMensagem(cabecalho);
        short tipo = getTipo(cabecalho);
        
        msg = Protocolo.decodificarCabecalho(msg);
        
        while (tamanho > msg.length()){
            msg += "\n";
            msg += in.readLine();
        }
        
        msg = adicionarCabecalho(msg, tipo);
        
        return msg;
    }
    
}
