package ia.antsystem;

import ia.BuscaCompetitiva;
import ia.minimax.TreeElement;
import java.util.ArrayList;
import modelos.Empresa;
import utilitarios.Util;

public class AntSystem implements BuscaCompetitiva {
    
    public final static int LIMITE_PROFUNDIDADE = 5;
    public final static int FEROMONIO_INICIAL = 1000;
    public final static Double TAXA_EVAPORACAO = 0.1;
    public final static Double TAXA_DEPOSITO = 1 + TAXA_EVAPORACAO;
    public final static int CASOS_POSSIVEIS = 9;
    
    private final int profundidadeTotal;
    private int profundidadeAtual;
    
    private final Empresa empresa;
    
    private TreeElement arvoreIA;
    private TreeElement melhorAtual;
    
    private ArrayList<Integer> vetorFeromonio;
    
    public AntSystem(Empresa empresa, int rodadasTotal){
        vetorFeromonio = new ArrayList(CASOS_POSSIVEIS);
        for(int i = 0; i < CASOS_POSSIVEIS; i++){
            vetorFeromonio.add(FEROMONIO_INICIAL);
        }
        
        this.profundidadeAtual = 0;
        this.profundidadeTotal = (int) (rodadasTotal * 0.5);
        this.empresa = empresa;
        this.arvoreIA = new TreeElement(empresa);
    }

    @Override
    public void executar() {
        int limite;
        if (profundidadeTotal - profundidadeAtual > LIMITE_PROFUNDIDADE){
            limite = LIMITE_PROFUNDIDADE;
        } else {
            limite = profundidadeTotal - profundidadeAtual;
        }
        
        gerarArvore(limite);
        arvoreIA.calcularMelhorFolha();
        melhorAtual = arvoreIA.getMelhorFilho();
    }

    @Override
    public int proximaAcao() {
        int acao = 0;
        int maior = Integer.MIN_VALUE;
        int numSorteado;
        int limite;
        
        if(this.profundidadeAtual < this.profundidadeTotal){
            if (profundidadeAtual != 0 && (profundidadeAtual % LIMITE_PROFUNDIDADE) == 0){
                arvoreIA = new TreeElement(empresa);
                executar();
            }

            acao = melhorAtual.getId();
            melhorAtual = melhorAtual.getMelhorFilho();
            
        } else {
            for(int i = 0; i < CASOS_POSSIVEIS; i++){
                limite = vetorFeromonio.get(i);
                numSorteado = Util.getRandomInt(limite);
                
                if(numSorteado > maior){
                    maior = numSorteado;
                    acao = i;
                }
            }
        }
        
        profundidadeAtual++;
        return acao;
    }

    public void atualizaFeromonio(int id, double capital){
        int novoValor;
        for(int i = 0; i < CASOS_POSSIVEIS; i++){
            novoValor = (int) (vetorFeromonio.get(i) * TAXA_EVAPORACAO);
            vetorFeromonio.set(i, novoValor);
        }
        if(capital < 0){
            int retirada = (int) ((vetorFeromonio.get(id) - capital) * TAXA_EVAPORACAO);
            novoValor = vetorFeromonio.get(id) - retirada;
            vetorFeromonio.set(id, novoValor);
        } else {
            novoValor = (int) (vetorFeromonio.get(id) * TAXA_DEPOSITO);
            vetorFeromonio.set(id, novoValor);
        }
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
            Empresa novaEmpresa = new Empresa(atual.getEmpresa());
            novaEmpresa.escolherAcoes(i);
            novaEmpresa.estimarVenda();
            
            TreeElement filho = new TreeElement(atual, i, novaEmpresa);
            atual.addFilho(filho);
            gerarFilhos(filho, limite, novaProfundidade);
        }
    }
    
    public void mostraFormiga() {
        System.out.println("Taxa de Evaporação: " + TAXA_EVAPORACAO);
        System.out.println("Taxa de Deposito: " + TAXA_DEPOSITO);
        System.out.println("Feromonio Atual");
        for(int i = 0; i < CASOS_POSSIVEIS; i++){
            System.out.println("Caso " + i + ": " + vetorFeromonio.get(i));
        }
    }
    
}
