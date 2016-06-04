package simulador;

import gui.TelaPrincipal;
import java.util.ArrayList;
import java.util.Random;

public class Simulador {
    
    private TelaPrincipal tela;
    private ArrayList<Empresa> listaEmpresa;
    private int maximoRodadas;
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
