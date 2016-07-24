package ia.minimax;

import java.util.ArrayList;
import modelos.*;

public class TreeElement {
    
    private int id;
    private final TreeElement pai;
    private final ArrayList<TreeElement> filhos;
    private TreeElement melhorFilho;
    
    private final Empresa empresa;
    
    public TreeElement(TreeElement pai, int id, Empresa empresa){
        this.id = id;
        this.pai = pai;
        this.empresa = empresa;
        this.filhos = new ArrayList<>();
        this.melhorFilho = null;
    }
    
    public TreeElement(Empresa empresa){
        this(null, 0, empresa);
    }
    
    public void addFilho(TreeElement filho){
        filhos.add(filho);
    }
    
    public void calcularMelhorFolha() {
        int tamanho = this.filhos.size();
        
        if (isFolha()){
            this.melhorFilho = this;
            return;
        }
        
        for(int i = 0; i < tamanho; i++){
            this.filhos.get(i).calcularMelhorFolha();
        }
        
        this.melhorFilho = this.filhos.get(0);
        for(int i = 1; i < tamanho; i++){
            if(this.filhos.get(i).melhorFilho.empresa.getCapital() > this.melhorFilho.empresa.getCapital()){
                this.melhorFilho = this.filhos.get(i);
            }
        }
    }
    
    public void mostraArvore() {
        if(filhos.isEmpty()){
            System.out.println("\nFOLHA:");
            empresa.mostraEmpresa();
            return;
        }
        this.empresa.mostraEmpresa();
        for (TreeElement filho : filhos) {
            filho.mostraArvore();
        }
    }
    
    public boolean isFolha(){
        return filhos.isEmpty();
    }

    public int getId() {
        return id;
    }

    public TreeElement getMelhorFilho() {
        return melhorFilho;
    }
    
    public Empresa getEmpresa() {
        return empresa;
    }
                    
}
