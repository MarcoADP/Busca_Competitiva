package rede.servidor;

import controlador.servidor.ControladorServidor;
import gui.AreaLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Atendente implements Runnable {
    
    private Socket socket;
    
    private BufferedReader in;
    private PrintStream out;
    
    private boolean inicializado;
    private boolean executando;
    
    private Thread thread;
    
    private final ControladorServidor controlador;
    
    private String mensagem;
    
    String id;
    
    public Atendente(Socket socket, ControladorServidor controlador) throws IOException{
        this.controlador = controlador;
        this.socket = socket;
        
        this.inicializado = false;
        this.executando = false;
        
        open();
        
        id = socket.getInetAddress().getHostName() + ":" + socket.getPort();
    }
    
    private void open() throws IOException{
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream());
            inicializado = true;
        } catch (Exception ex){
            close();
            throw ex;
        }
    }
    
    private void close(){
        if (in != null){
            try {
                in.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        
        if (out != null){
            try {
                out.close();
            } catch (Exception ex){
                System.out.println(ex);
            }
        }
        
        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        in = null;
        out = null;
        socket = null;
        
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
        
        if (thread != null){
            thread.join();
        }
    }
    
    public void send(String msg){
        //protocolo.processar(msg) -> send
        out.println(msg);
    }
    
    private void appendLog(String msg){
        AreaLog.appendLog(msg);
    }

    @Override
    public void run() {
        while (executando){
            try {
                socket.setSoTimeout(2500);
                mensagem = in.readLine();
                
                if (mensagem == null){
                    break;
                }
                
                appendLog("Mensagem recebida do cliente ["+id+"]: "+ mensagem+"\n");
                
                if (mensagem.equals("FIM")) {
                    break;
                }
                
                out.println(mensagem);
                
            } catch (SocketTimeoutException ex){
                // ignorar
            } catch (IOException ex) {
                break;
            }
        }
        appendLog("Cliente ["+id+"] desconectado.\n");
        
        synchronized (controlador) {
            controlador.removeCliente();
        }

        close();
    }

    public String getMensagem() {
        return mensagem;
    }
}
