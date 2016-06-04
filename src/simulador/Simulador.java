package simulador;

import gui.TelaPrincipal;
import java.util.ArrayList;
import java.util.Random;

public class Simulador {
    
    public final static int INVESTIMENTO_INICIAL_BAIXO = 1000000;    // R$ 1.000.000
    public final static int INVESTIMENTO_INICIAL_MEDIO = 3000000;    // R$ 3.000.000
    public final static int INVESTIMENTO_INICIAL_ALTO = 5000000;     // R$ 5.000.000
    
    private TelaPrincipal tela;
    private ArrayList<Empresa> listaEmpresa;
    private int numRodadas;
    private int maximoCarros;
    
    public Simulador(){
        novoJogo();
    }
    
    public void novoJogo(){
        tela = new TelaPrincipal(this);
        listaEmpresa = new ArrayList<>();
    }
    
    public void iniciarJogo(int numPessoas, int numIA, int numRodadas){
        
    }
    
    
    public void atenderDemanda(){
       ArrayList<Integer> listaProb = new ArrayList<>();
       int totalProb = 0;
       Empresa empresaCompradora = null;
       for(Empresa empresa : listaEmpresa){
           totalProb += empresa.getProbabilidadeVenda();
           listaProb.add(totalProb);
       }
       Random random = new Random();
       int i, j;
       int totalEmpresas = this.listaEmpresa.size();
       for(i = 0; i < this.maximoCarros; i++){
           int num = random.nextInt(totalProb);
           for(j = 0; j < totalEmpresas; j++){
               if(listaProb.get(j) < num){
                   empresaCompradora = listaEmpresa.get(j);
                   break;
               }
           }
           empresaCompradora.venderCarro();
       }
       
   }
}
