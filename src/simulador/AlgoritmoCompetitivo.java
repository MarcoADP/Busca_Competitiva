package simulador;

public class AlgoritmoCompetitivo {
    Tree arvore;
    int maxProfundidade;
    public AlgoritmoCompetitivo(int max){
        //this.arvore = new Tree(empresa);
        this.maxProfundidade = max;
    }
    
    public void criarArvore(Empresa empresa){
        this.arvore = new Tree(empresa);
    }
    
    public void criarFilhos(Empresa original){
        int i;
        for(i = 0; i < 9; i++){
            Empresa filho = original;
            switch(i){
                case 0:
                    filho.investir(0);
                    filho.carro.alterarPreco(0);
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
            }
        }
    }
    
    public double minimax(Empresa no, int profundidade, boolean max){
        if(profundidade == 0 || isFinal(no)){
            return no.getCapital();
        }
        if(max){
            double melhorResultado = Double.MIN_VALUE;
            
        }
        return 0.0;
    }
    
    public boolean isFinal(Empresa empresa){
        return true;
    }
    /*
    function minimax(node, depth, maximizingPlayer)
02     if depth = 0 or node is a terminal node
03         return the heuristic value of node

04     if maximizingPlayer
05         bestValue := −∞
06         for each child of node
07             v := minimax(child, depth − 1, FALSE)
08             bestValue := max(bestValue, v)
09         return bestValue

10     else    (* minimizing player *)
11         bestValue := +∞
12         for each child of node
13             v := minimax(child, depth − 1, TRUE)
14             bestValue := min(bestValue, v)
15         return bestValue
    */
    
    public void Decisao(){ //retorna uma acao (estado) 
        //retornar o maior valor entre os min
    }
    
    public void ValorMax(){
        //se estado final -> retornar utilidade do estado
        
        // v <- inf
        // para cada acao do estado (filhos)
        // v <- max entre v e o menor filho do estado
        // retornar v
    }
    
    public void ValorMin(){ //retornar um valor de utilidade
        //se estado final -> retornar utilidade do estado
        
        // v <- -1 * inf
        // para cada acao do estado (filhos)
        // v <- minimo entre v e o maior filho do estado
        // retornar v
    }
}
