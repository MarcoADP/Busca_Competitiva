package simulador;

import gui.TelaPrincipal;
import java.util.ArrayList;
import java.util.Random;

public class Simulador {
    
    TelaPrincipal tela;
    ArrayList<Empresa> listaEmpresa;
    int maximoRodadas;
    int maximoCarros;
    
    public Simulador(){
        novoJogo();
    }
    
    public void novoJogo(){
        tela = new TelaPrincipal(this);
        listaEmpresa = new ArrayList<>();
    }
    
   public void atenderDemanda(){
       ArrayList<Integer> listaProb = new ArrayList<>();
       int totalProb = 0;
       Empresa empresaCompradora = null;
       for(Empresa empresa : listaEmpresa){
           totalProb += empresa.getProbabilidade_venda();
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
