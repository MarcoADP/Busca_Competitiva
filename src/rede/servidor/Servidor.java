package rede.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class Servidor implements Runnable{
    
    private ServerSocket server;
    
    private boolean inicializado;
    private boolean executando;
    
    private List<Atendente> atendentes;
    
    private Thread thread;
    
    public Servidor(int porta) throws IOException{
        atendentes = new ArrayList<>();
        
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

    @Override
    public void run() {
        //janela.append("Aguardando conexão\n");
        while (executando){
            try {
                server.setSoTimeout(2500);
                
                Socket socket = server.accept();
                //janela.append("Conexão estabelecida\n");
                
                Atendente atendente = new Atendente(socket);
                atendente.start();
                atendentes.add(atendente);
                
                
            } catch (SocketTimeoutException ex) {
                //System.out.println("EXCEÇÃO TIMEOUT");
                //System.out.println(ex);
            } catch (IOException ex) {
                //janela.append("EXCEÇÃO IO\n");
                System.out.println(ex);
            }
        }
        close();
    }
    
}
