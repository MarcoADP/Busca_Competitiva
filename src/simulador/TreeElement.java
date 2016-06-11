package simulador;

import java.util.ArrayList;
import modelos.*;

//Usar a arvore:
//criar a raiz
//chama filhos raiz com 0, numero de rodadas desejadas
//chama melhor folha com 0
//cada elemento tem um id, a partir desse id voce chama empresa.escolherAcoes(id);

public class TreeElement {
    int id;
    TreeElement pai;
    int profundidade;
    Empresa empresa;
    ArrayList<TreeElement> filhos;
    TreeElement melhorFilho;
    
    public TreeElement(int profundidade, TreeElement pai, Empresa empresa){
        this.id = 0;
        this.profundidade = profundidade;
        this.pai = pai;
        this.empresa = empresa;
        this.filhos = new ArrayList<>();
        this.melhorFilho = null;
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
                    novaEmpresa = new Empresa(this.empresa, 0, 0, 0);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 0;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                case 1: //MN PN FM
                    novaEmpresa = new Empresa(this.empresa, 0, 0, 100);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 1;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                case 2: //MN PN FC
                    novaEmpresa = new Empresa(this.empresa, 0, 0, 1);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 2;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                    
                    
                case 3: //MN PA FM
                    novaEmpresa = new Empresa(this.empresa, 0, 1, 100);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 3;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                case 4: //MN PA FC
                    novaEmpresa = new Empresa(this.empresa, 0, 1, 1);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 4;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                    
                    
                case 5: //MA PN FM
                    novaEmpresa = new Empresa(this.empresa, 1, 0, 100);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 5;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                case 6: //MA PN FC
                    novaEmpresa = new Empresa(this.empresa, 1, 0, 1);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 6;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                
                    
                case 7: //MA PA FM
                    novaEmpresa = new Empresa(this.empresa, 1, 1, 100);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 7;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;    
                case 8: //MA PA FC
                    novaEmpresa = new Empresa(this.empresa, 1, 1, 1);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    filho.id = 8;
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
            }
        }
    }
    
    public void mostraArvore(int profundidade){
        if(profundidade > 4){
            return;
        }
        System.out.println("PROFUNDIDADE => " + profundidade);
        this.empresa.mostraEmpresa();
        int tamanho = this.filhos.size();
        for(int i = 0; i < tamanho; i++){
            this.filhos.get(i).mostraArvore(profundidade + 1);
            //System.out.println("PROFUNDIDADE => " + profundidade);
            //this.filhos.get(i).empresa.mostraEmpresa();
        }
    }
    
    public void melhorFolha(int profundidade){
        int tamanho = this.filhos.size();
        int i, a = 0;
        
        if(profundidade == 4){
            this.melhorFilho = this.filhos.get(0);
            for(i = 1; i < tamanho;i++){
                if(this.filhos.get(i).empresa.getCapital() > this.melhorFilho.empresa.getCapital()){
                    //System.out.println(this.filhos.get(i).empresa.getCapital());
                    this.melhorFilho = this.filhos.get(i);
                }
            }
            //System.out.println("aa => " + this.melhorFilho.empresa.getCapital());
            return;
        }
        for(i = 0; i < tamanho; i++){
            this.filhos.get(i).melhorFolha(profundidade+1);
        }
        this.melhorFilho = this.filhos.get(0);
        for(i = 1; i < tamanho; i++){
            if(this.filhos.get(i).melhorFilho.empresa.getCapital() > this.melhorFilho.empresa.getCapital()){
                this.melhorFilho = this.filhos.get(i);
            }
            //System.out.println("bb => " + this.filhos.get(i).melhorFilho.empresa.getCapital());
        }
        

    }
                    
}
