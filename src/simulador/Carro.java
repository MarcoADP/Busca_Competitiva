package simulador;

public class Carro {
    public final static int MODELO_POPULAR = 1;
    public final static int MODELO_MEDIO = 2;
    public final static int MODELO_LUXO = 3;
    
    public final static int PRECO_POPULAR_NORMAL = 20000;
    public final static int PRECO_MEDIO_NORMAL = 35000;
    public final static int PRECO_LUXO_NORMAL = 20000;
    
    public final static int PRECO_POPULAR_BARATO = (int) (PRECO_POPULAR_NORMAL * 0.8);
    public final static int PRECO_MEDIO_BARATO = (int) (PRECO_MEDIO_NORMAL * 0.8);
    public final static int PRECO_LUXO_BARATO = (int) (PRECO_LUXO_NORMAL * 0.8);
    
    public final static int PRECO_POPULAR_CARO = (int) (PRECO_POPULAR_NORMAL * 1.5);
    public final static int PRECO_MEDIO_CARO = (int) (PRECO_MEDIO_NORMAL * 1.5);
    public final static int PRECO_LUXO_CARO = (int) (PRECO_LUXO_NORMAL * 1.5);
    
    private int preco_venda;
    
    public Carro(int modelo, String preco){
        switch (modelo) {
            case 1:
                if(preco.equals("barato")){
                    preco_venda = PRECO_POPULAR_BARATO;
                } else if (preco.equals("normal")){
                    preco_venda = PRECO_POPULAR_NORMAL;
                } else{
                    preco_venda = PRECO_POPULAR_CARO;
                }   break;
            case 2:
                if(preco.equals("barato")){
                    preco_venda = PRECO_MEDIO_BARATO;
                } else if (preco.equals("normal")){
                    preco_venda = PRECO_MEDIO_NORMAL;
                } else{
                    preco_venda = PRECO_MEDIO_CARO;
                }   break;
            default:
                if(preco.equals("barato")){
                    preco_venda = PRECO_LUXO_BARATO;
                } else if (preco.equals("normal")){
                    preco_venda = PRECO_LUXO_NORMAL;
                } else{
                    preco_venda = PRECO_LUXO_CARO;
                }   break;
        }
    }
    
}
