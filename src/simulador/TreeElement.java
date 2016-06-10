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
    
    public void gerarFilhos(TreeElement pai){
        int i;
        int novaProfundidade = pai.profundidade + 1;
        for(i = 0; i < 9; i++){
            TreeElement filho;
            switch(i){    
                case 0: //MN PN FD
                    Empresa novaEmpresa = new Empresa(pai.empresa, 0, 0, 0);
                    //new TreeElement(novaProfundidade, pai, novaEmpresa);
                    //pai.children.add(filho);
                    break;
                /*case 1: //MN PN FM
                    filho = gerarNo(pai, 0, 0, 100);
                    pai.children.add(filho);
                    break;
                case 2: //MN PN FC
                    filho = gerarNo(pai, 0, 0, 1);
                    pai.children.add(filho);
                    break;
                    
                    
                case 3: //MN PA FM
                    filho = gerarNo(pai, 0, 1, 100);
                    pai.children.add(filho);
                    break;
                case 4: //MN PA FC
                    filho = gerarNo(pai, 0, 0, 1);
                    pai.children.add(filho);
                    break;
                    
                    
                case 5: //MA PN FM
                    filho = gerarNo(pai, 1, 0, 100);
                    pai.children.add(filho);
                    break;
                case 6: //MA PN FC
                    filho = gerarNo(pai, 1, 0, 1);
                    pai.children.add(filho);
                    break;
                
                    
                case 7: //MA PA FM
                    filho = gerarNo(pai, 1, 1, 100);
                    pai.children.add(filho);
                    break;    
                case 8: //MA PA FC
                    filho = gerarNo(pai, 1, 1, 1);
                    pai.children.add(filho);
                    break;*/
            }
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
