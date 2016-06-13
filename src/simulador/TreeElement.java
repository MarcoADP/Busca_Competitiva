package simulador;

import java.util.ArrayList;
import modelos.*;

public class TreeElement {
    
    private int id;
    private final TreeElement pai;
    private final int profundidade;
    private final Empresa empresa;
    private final ArrayList<TreeElement> filhos;
    private TreeElement melhorFilho;
    
    
    public TreeElement(int profundidade, TreeElement pai, Empresa empresa){
        this.id = 0;
        this.profundidade = profundidade;
        this.pai = pai;
        this.empresa = empresa;
        this.filhos = new ArrayList<>();
        this.melhorFilho = null;
    }
    
    public TreeElement(Empresa empresa){
        this(0, null, empresa);
    }
    
    public void gerarFilhos(int limite){
        int i;
        if(this.profundidade == limite){
            return;
        }
        
        int novaProfundidade = this.profundidade + 1;
        for(i = 0; i < 9; i++){
            TreeElement filho;
            Empresa novaEmpresa;
            switch(i){    
                case 0: //Marketing Normal Preço Normal Funcionario Demitido
                    novaEmpresa = new Empresa(this.empresa, Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_NORMAL, -Empresa.FATOR_FUNCIONARIO_DEMITIR);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 0;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                case 1: //MN PN FM
                    novaEmpresa = new Empresa(this.empresa, Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_NORMAL, 0);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 1;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                case 2: //MN PN FC
                    novaEmpresa = new Empresa(this.empresa, Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_NORMAL, Empresa.FATOR_FUNCIONARIO_CONTRATAR);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 2;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                    
                    
                case 3: //MN PA FM
                    novaEmpresa = new Empresa(this.empresa, Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_CARO, 0);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 3;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                case 4: //MN PA FC
                    novaEmpresa = new Empresa(this.empresa, Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_CARO, Empresa.FATOR_FUNCIONARIO_CONTRATAR);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 4;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                    
                    
                case 5: //MA PN FM
                    novaEmpresa = new Empresa(this.empresa, Empresa.MARKETING_ALTO, Carro.TIPO_PRECO_NORMAL, 0);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 5;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                case 6: //MA PN FC
                    novaEmpresa = new Empresa(this.empresa, Empresa.MARKETING_ALTO, Carro.TIPO_PRECO_NORMAL, Empresa.FATOR_FUNCIONARIO_CONTRATAR);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 6;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                
                    
                case 7: //MA PA FM
                    novaEmpresa = new Empresa(this.empresa, Empresa.MARKETING_ALTO, Carro.TIPO_PRECO_CARO, 0);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 7;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;    
                case 8: //MA PA FC
                    novaEmpresa = new Empresa(this.empresa, Empresa.MARKETING_ALTO, Carro.TIPO_PRECO_CARO, Empresa.FATOR_FUNCIONARIO_CONTRATAR);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 8;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
            }
        }
    }
    
    public void mostraArvore(){
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
    
    public void calcularMelhorFolha(){
        int tamanho = this.filhos.size();
        int i;
        
        if (filhos.isEmpty()){
            this.melhorFilho = this;
            return;
        }
        
        for(i = 0; i < tamanho; i++){
            this.filhos.get(i).calcularMelhorFolha();
        }
        
        this.melhorFilho = this.filhos.get(0);
        for(i = 1; i < tamanho; i++){
            if(this.filhos.get(i).melhorFilho.empresa.getCapital() > this.melhorFilho.empresa.getCapital()){
                this.melhorFilho = this.filhos.get(i);
            }
        }
    }

    public int getId() {
        return id;
    }

    public TreeElement getMelhorFilho() {
        return melhorFilho;
    }
                    
}
