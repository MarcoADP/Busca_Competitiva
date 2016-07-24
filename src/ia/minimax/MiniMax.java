package ia.minimax;

import ia.BuscaCompetitiva;
import modelos.Carro;
import modelos.Empresa;

public class MiniMax implements BuscaCompetitiva{
    
    public final static int LIMITE_PROFUNDIDADE = 5;
    
    private final Empresa empresa;
    
    private TreeElement arvoreIA;       // raiz da árvore
    private TreeElement melhorAtual;
    
    private final int profundidadeTotal;
    private int profundidadeAtual;
    
    public MiniMax(Empresa empresa, int profundidadeTotal){
        this.empresa = empresa;
        this.arvoreIA = new TreeElement(empresa);
        this.profundidadeTotal = profundidadeTotal;
        profundidadeAtual = 0;
    }
    
    @Override
    public void executar(){
        int limite;
        if (profundidadeTotal - profundidadeAtual > LIMITE_PROFUNDIDADE){
            limite = LIMITE_PROFUNDIDADE;
        } else {
            limite = profundidadeTotal - profundidadeAtual;
        }
        
        gerarArvore(limite);
        arvoreIA.calcularMelhorFolha();
        melhorAtual = arvoreIA.getMelhorFilho();
        
        System.out.println("\n\n");
        TreeElement m = arvoreIA;
         for (int i = 0; i < 6; i++) {
             System.out.print(m.getId()+" ");
             m = m.getMelhorFilho();
         }
         System.out.println("\n\n");
    }
    
    @Override
    public int proximaAcao(){
        if (profundidadeAtual != 0 && (profundidadeAtual % LIMITE_PROFUNDIDADE) == 0){
            arvoreIA = new TreeElement(empresa);
            executar();
        }
        
        profundidadeAtual++;
        int acao = melhorAtual.getId();
        melhorAtual = melhorAtual.getMelhorFilho();
        
        return acao;
    }
    
    private void gerarArvore(int limite){
        gerarFilhos(arvoreIA, limite, 0);
    }
    
    private void gerarFilhos(TreeElement atual, int limite, int profundidade){
        if(profundidade == limite){
            return;
        }
        
        int novaProfundidade = profundidade + 1;
        for(int i = 0; i < 9; i++){
            TreeElement filho;
            Empresa novaEmpresa = null;
            switch(i) { 
                case 0: //Marketing Normal Preço Normal Funcionario Demitido
                    novaEmpresa = new Empresa(atual.getEmpresa(), Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_NORMAL, -Empresa.FATOR_FUNCIONARIO_DEMITIR);
                    break;
                case 1: //MN PN FM
                    novaEmpresa = new Empresa(atual.getEmpresa(), Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_NORMAL, 0);
                    break;
                case 2: //MN PN FC
                    novaEmpresa = new Empresa(atual.getEmpresa(), Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_NORMAL, Empresa.FATOR_FUNCIONARIO_CONTRATAR);
                    break;
                    
                    
                case 3: //MN PA FM
                    novaEmpresa = new Empresa(atual.getEmpresa(), Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_CARO, 0);
                    break;
                case 4: //MN PA FC
                    novaEmpresa = new Empresa(atual.getEmpresa(), Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_CARO, Empresa.FATOR_FUNCIONARIO_CONTRATAR);
                    break;
                    
                    
                case 5: //MA PN FM
                    novaEmpresa = new Empresa(atual.getEmpresa(), Empresa.MARKETING_ALTO, Carro.TIPO_PRECO_NORMAL, 0);
                    break;
                case 6: //MA PN FC
                    novaEmpresa = new Empresa(atual.getEmpresa(), Empresa.MARKETING_ALTO, Carro.TIPO_PRECO_NORMAL, Empresa.FATOR_FUNCIONARIO_CONTRATAR);
                    break;
                
                    
                case 7: //MA PA FM
                    novaEmpresa = new Empresa(atual.getEmpresa(), Empresa.MARKETING_ALTO, Carro.TIPO_PRECO_CARO, 0);
                    break;    
                case 8: //MA PA FC
                    novaEmpresa = new Empresa(atual.getEmpresa(), Empresa.MARKETING_ALTO, Carro.TIPO_PRECO_CARO, Empresa.FATOR_FUNCIONARIO_CONTRATAR);
                    break;
            }
            filho = new TreeElement(atual, i, novaEmpresa);
            atual.addFilho(filho);
            gerarFilhos(filho, limite, novaProfundidade);
        }
    }

}
