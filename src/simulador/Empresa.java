package simulador;

public class Empresa {
    
    //CONFIGURAÇÃO INICIAL
    private double capital; //Começa com um valor de investimento inicial e vai modificando com o decorre das jogadas
    private Carro carro; //Produto
    private int tamanhoFabrica;
    private int quantidadeCarro; //Depende do tamanho da fábrica
    private int tempoFabricacao; //Tempo de fabricação do carro de acordo com a fábrica
    private int custoFabricacao;
    private int numeroFuncionarios;
    private double salarioFuncionario;
    private double gastoFuncionarios;
    private int limiteFuncionarios;
    private double gastosTotais;
            
    //PARÂMETROS VARIÁVEIS
    private double investimentoMarketing;   
    
    public final static int FABRICA_PEQUENO = 1;
    public final static int FABRICA_MEDIO = 2;
    public final static int FABRICA_GRANDE = 3;
    //1 -> Pequena (mais barata, produz menos)
    //2 -> Media (preço médio, produz mais)
    //3 -> Grande (mais cara, produz muito mais)

    public Empresa(double investimentoInicial, int tamanhoFabrica, int numeroFuncionarios, double salarioFuncionario, int modelo, int tipo_preco, int limiteFuncionarios) {
        this.carro = new Carro(modelo, tipo_preco);
        this.capital = investimentoInicial;
        this.tamanhoFabrica = tamanhoFabrica;
        this.numeroFuncionarios = numeroFuncionarios;
        this.limiteFuncionarios = limiteFuncionarios;
        this.salarioFuncionario = salarioFuncionario;
        
        this.gastoFuncionarios = this.salarioFuncionario * this.numeroFuncionarios;
        
        switch (this.tamanhoFabrica) {
            case FABRICA_PEQUENO:
                this.quantidadeCarro = 500;
                this.custoFabricacao = (int) (this.carro.getCusto() * 0.9);
                this.tempoFabricacao = (int) (this.carro.getTempo() * 1.5);
                break;
            case FABRICA_MEDIO:
                this.quantidadeCarro = 800;
                this.custoFabricacao = this.carro.getCusto();
                this.tempoFabricacao = this.carro.getTempo();
                break;
            default:
                this.quantidadeCarro = 1000;
                this.custoFabricacao = (int) (this.carro.getCusto() * 1.2);
                this.tempoFabricacao = (int) (this.carro.getTempo() * 0.8);
                break;
        }
        this.gastosTotais = calcularGasto();
    }

    private double calcularGasto(){
        double total;
        int custoCarro = this.carro.getCusto() * this.quantidadeCarro;
        
        total = this.gastoFuncionarios + custoCarro + this.gastoFuncionarios + this.investimentoMarketing;
        
        return total;
    }
    
    public void investir(double valor){
        //Aqui deve retornar alguns compradores de carro que virão a propaganda
        //e decidiram comprar o carro
    }
    
    public void fabricarCarro(int quantidade){
        this.quantidadeCarro += quantidade;
        
    }
    
    public void atualizarFuncionarios(int opcao){
        // 1 -> contrata 10
        // CC -> demite 10 ou o restante
        if(opcao == 1 && this.limiteFuncionarios >= 10){
            this.numeroFuncionarios += 10;
            this.limiteFuncionarios -= 10;
        }
        else{
            if(this.numeroFuncionarios < 10){
                this.limiteFuncionarios += this.numeroFuncionarios;
                this.numeroFuncionarios = 0;
            } else {
                this.limiteFuncionarios += 10;
                this.numeroFuncionarios -= 10;
            }
        }
        this.gastoFuncionarios = this.salarioFuncionario * this.numeroFuncionarios;
    }
    
    
    
            
    /*GET SET*/
    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public int getTamanhoFabrica() {
        return tamanhoFabrica;
    }

    public void setTamanhoFabrica(int tamanhoFabrica) {
        this.tamanhoFabrica = tamanhoFabrica;
    }

    public int getQuantidadeCarro() {
        return quantidadeCarro;
    }

    public void setQuantidadeCarro(int quantidadeCarro) {
        this.quantidadeCarro = quantidadeCarro;
    }

    public int getTempoFabricacao() {
        return tempoFabricacao;
    }

    public void setTempoFabricacao(int tempoFabricacao) {
        this.tempoFabricacao = tempoFabricacao;
    }

    public int getNumeroFuncionarios() {
        return numeroFuncionarios;
    }

    public void setNumeroFuncionarios(int numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }

    public double getSalarioFuncionario() {
        return salarioFuncionario;
    }

    public void setSalarioFuncionario(double salarioFuncionario) {
        this.salarioFuncionario = salarioFuncionario;
    }

    public double getGastoFuncionarios() {
        return gastoFuncionarios;
    }

    public void setGastoFuncionarios(double gastoFuncionarios) {
        this.gastoFuncionarios = gastoFuncionarios;
    }

    public double getInvestimentoMarketing() {
        return investimentoMarketing;
    }

    public void setInvestimentoMarketing(double investimentoMarketing) {
        this.investimentoMarketing = investimentoMarketing;
    }    
}
