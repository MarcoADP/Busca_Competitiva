package simulador;

import java.util.ArrayList;
import modelos.*;

public class TreeElement {
    TreeElement pai;
    int profundidade;
    Empresa empresa;
    ArrayList<TreeElement> filhos;
    
    public TreeElement(int profundidade, TreeElement pai, Empresa empresa){
        this.profundidade = profundidade;
        this.pai = pai;
        this.empresa = empresa;
        this.filhos = new ArrayList<>();
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
                    novaEmpresa = new Empresa(this.empresa, 0, 0, 1);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                case 1: //MN PN FM
                    novaEmpresa = new Empresa(this.empresa, 0, 0, 100);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                case 2: //MN PN FC
                    novaEmpresa = new Empresa(this.empresa, 0, 0, 1);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                    
                    
                case 3: //MN PA FM
                    novaEmpresa = new Empresa(this.empresa, 0, 1, 100);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                case 4: //MN PA FC
                    novaEmpresa = new Empresa(this.empresa, 0, 1, 1);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                    
                    
                case 5: //MA PN FM
                    novaEmpresa = new Empresa(this.empresa, 1, 0, 100);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                case 6: //MA PN FC
                    novaEmpresa = new Empresa(this.empresa, 1, 0, 1);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;
                
                    
                case 7: //MA PA FM
                    novaEmpresa = new Empresa(this.empresa, 1, 1, 100);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
                    //filho.empresa.mostraEmpresa();
                    this.filhos.add(filho);
                    filho.gerarFilhos(limite);
                    break;    
                case 8: //MA PA FC
                    novaEmpresa = new Empresa(this.empresa, 1, 1, 1);
                    filho = new TreeElement(novaProfundidade, this, novaEmpresa);
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
    
    /*public TreeElement criarFilho(TreeElement pai, int opInv, int opPreco, int opFunc){
        //int profundidade = pai.profundidade + 1;
        //TreeElement filho = new TreeElement(profundidade, );
        //filho.empresa.
        return filho;
    }*/
                    
    /*public TreeElement gerarNo(Node pai, int opInv, int opPreco, int opFunc){
        TreeElement filho = new T<>();
        
        filho.parent = pai;
        filho.data = pai.data.escolhaAcoes(opInv, opPreco, opFunc);
        filho.children = new ArrayList<>();
        
        return filho;
    }*/
                    
}
