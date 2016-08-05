package rede.servidor;

import controlador.servidor.ControladorServidor;
import gui.AreaLog;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Servidor implements Runnable {
    
    private ServerSocket server;
    
    private boolean inicializado;
    private boolean executando;
    
    private final List<Atendente> atendentes;
    
    private Thread thread;
    
    private String logMsg;
    
    private final ControladorServidor controlador;
    
    public Servidor(int porta, ControladorServidor controlador) throws IOException{
        this.controlador = controlador;
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
    
    private void close() {
        for (Atendente atendente : atendentes) {
            try {
                atendente.stop();
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
        
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
        appendLog("Todos os jogadores conectados. Servidor fechado para conexões.\n");
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
                appendLog("Conexão do cliente ["+
                        socket.getInetAddress().getHostAddress() + ":"+socket.getPort()+"] estabelecida.\n");
                
                Atendente atendente = new Atendente(socket, controlador);
                atendente.start();
                atendentes.add(atendente);
                
                synchronized(controlador){
                    controlador.addCliente();
                }
                
            } catch (SocketTimeoutException ex) {
                // ignorar
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        close();
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
