package rede.servidor;

import gui.AreaLog;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import rede.protocolo.ProtocoloServidor;

public class Servidor implements Runnable {
    
    private ServerSocket server;
    
    private boolean inicializado;
    private boolean executando;
    
    private final List<Atendente> atendentes;
    
    private Thread thread;
    
    private String logMsg;
    
    private final ProtocoloServidor protocolo;
    
    public Servidor(int porta, ProtocoloServidor protocolo) throws IOException{
        this.protocolo = protocolo;
        atendentes = new ArrayList<>();
        logMsg = "";
        
        inicializado = false;
        executando = false;
        
        open(porta);
    }
    
    private void open(int porta) throws IOException{
        server = new ServerSocket(porta);
        inicializado = true;
    }
    
    public void close() {
        /*for (Atendente atendente : atendentes) {
            try {
                atendente.stop();
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }*/
        
        try {
            server.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        server = null;
        inicializado = false;
        executando = false;
        thread = null;
    }
    
    public void start(){
        if (!inicializado || executando){
            return;
        }
        
        executando = true;
        
        thread = new Thread(this);
        thread.start();
    }
    
    public void stop() throws InterruptedException{
        executando = false;
        
        if (thread != null) {
            thread.join();
        }
    }
    
    public void sendAll(String msg){
        for (Atendente atendente : atendentes) {
            atendente.send(msg);
        }
    }
    
    public void removerAtendente(String id){
        for (int i = atendentes.size(); i >= 0; i++){
            if (atendentes.get(i).getID().equals(id)){
                atendentes.remove(i);
            }
        }
    }
    
    private void appendLog(String msg){
        logMsg += msg;
        AreaLog.appendLog(msg);
    }

    @Override
    public void run() {
        appendLog("Aguardando conexões...\n");
        while (executando){
            try {
                server.setSoTimeout(2500);
                Socket socket = server.accept();
                
                Atendente atendente = new Atendente(socket, protocolo);
                atendente.start();
                atendentes.add(atendente);
                
            } catch (SocketTimeoutException ex) {
                // ignorar
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        
        //close();
    }
    
    public String getEndereco(){
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) { }
        
        return "0.0.0.0";
    }
    
    public int getPorta(){
        return server.getLocalPort();
    }
    
    public String getLog(){
        return logMsg;
    }
    
}
