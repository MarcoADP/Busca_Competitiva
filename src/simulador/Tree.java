package simulador;

import modelos.Empresa;
import java.util.ArrayList;

public class Tree<T> {
    private Node<T> root;

    public Tree(Empresa rootData) {
        root = new Node<>();
        root.data = rootData;
        root.children = new ArrayList<>();
    }

    public static class Node<T> {
        private Empresa data; //dado
        private Node<T> parent; //pai
        private ArrayList children; //filho
    }
    
    public void gerarFilhos(Node pai){
        int i;
        for(i = 0; i < 9; i++){
            Node filho;
            switch(i){    
                case 0: //MN PN FD
                    filho = gerarNo(pai, 0, 0, 0);
                    pai.children.add(filho);
                    break;
                case 1: //MN PN FM
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
                    break;
            }
        }
    }
    
    public Node gerarNo(Node pai, int opInv, int opPreco, int opFunc){
        Node filho = new Node<>();
        
        filho.parent = pai;
        filho.data = pai.data.escolhaAcoes(opInv, opPreco, opFunc);
        filho.children = new ArrayList<>();
        
        return filho;
    }
    
    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }
    
    
}