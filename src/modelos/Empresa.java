package modelos;

public class Empresa {
    
    public final static int INVESTIMENTO_MARKETING_NORMAL = 1;
    public final static int INVESTIMENTO_MARKETING_ALTO = 2;
    
    public final static int MARKETING_NORMAL = 1;
    public final static int MARKETING_ALTO = 2;
    
    //CONFIGURAÇÃO INICIAL
    private String nome;
    private double capital;             //Começa com um valor de investimento inicial e vai modificando com o decorrer das jogadas
    
    private Carro carro;                //Produto
    private Fabrica fabrica;
    
    private int estoqueCarro;
    
    private int numeroFuncionarios;
    private double salarioFuncionario;
    private double gastoFuncionarios;
    private int limiteFuncionarios;
    
    private double gastosTotais;
    private int probabilidadeVenda;
    
    private final boolean isBot;          // true se for IA, false se for jogador
            
    //PARÂMETROS VARIÁVEIS
    private double investimentoMarketing;

    public Empresa(String nome, int investimento, boolean isBot, Fabrica fabrica){
        this.nome = nome;
        this.capital = investimento;
        this.carro = new Carro(Carro.MODELO_POPULAR, Carro.TIPO_PRECO_NORMAL);        
        this.isBot = isBot;
        this.estoqueCarro = 0;
<<<<<<< HEAD
        this.investimentoMarketing = 0;
=======
        this.limiteFuncionarios = 100; //Número máximo de funcionários por empresa
        if(fabrica == Fabrica.PEQUENA){
            this.fabrica = Fabrica.PEQUENA;
            this.numeroFuncionarios = this.fabrica.getNumeroFuncionarioInicial();
        } else if(fabrica == Fabrica.MEDIA){
            this.fabrica = Fabrica.MEDIA;
            this.numeroFuncionarios = this.fabrica.getNumeroFuncionarioInicial();
        } else{
            this.fabrica = Fabrica.GRANDE;
            this.numeroFuncionarios = this.fabrica.getNumeroFuncionarioInicial();
        }
        this.numeroFuncionarios = 0;
>>>>>>> 7542c8546181e6933108f2bd7615bbec627499de
        // atributos padrões
    }

    public Empresa(Empresa empresa, int opInv, int opPreco, int opFunc) {
        this.nome = empresa.nome;
        
        //recebe atributos pai
        this.capital = empresa.capital;
        this.carro = empresa.carro;
        this.fabrica = empresa.fabrica;
        this.isBot = empresa.isBot;
        this.estoqueCarro = empresa.estoqueCarro;
        
        //alterar Atributos Variáveis
        this.carro.alterarPreco(opPreco);
        this.investir(opInv);
        this.calcularProbabilidade(opPreco, opInv);
        
        //atualizar
        int vendaEstimada = this.estoqueCarro * this.probabilidadeVenda;
        int ganhoVenda = vendaEstimada * this.carro.getPrecoVenda();
        this.capital += ganhoVenda;
        this.estoqueCarro -= vendaEstimada;
        double custo = this.fabricarCarro();
        this.estoqueCarro += this.carrosPorMes();
        this.atualizarFuncionarios(opFunc);  
        //Acho que tudo foi atualizado. Agora falta a logica do jogo.
    }
    

    public boolean atualizaEstoque(){
        if(this.estoqueCarro > 0){
            this.estoqueCarro = this.estoqueCarro - 1;
            this.capital = this.capital + this.carro.getPrecoVenda();
            return true;
        }
        return false;
    }
    
    public double carrosPorDia(){
        return carro.getTempo() / fabrica.getFatorProducao();
    }
    
    public int carrosPorMes(){
        return (int) (30 / carrosPorDia());
    }
    
    public double gastoFixo(){
        double gasto = fabricarCarro();
        gasto += this.fabrica.getGastoPorMes();
        return gasto;
    }
    
    public double fabricarCarro(){
        int auxiliar = this.estoqueCarro + carrosPorMes();
        if (auxiliar <= fabrica.getCapacidade()){ //NÃO POSSO PRODUZIR MAIS CARROS, ENTÃO RETORNO O CAPITAL INVESTIDO
            this.estoqueCarro += auxiliar;            
            return (carrosPorMes()*this.carro.getCusto());
            
        } else {
            auxiliar = fabrica.getCapacidade() - this.estoqueCarro; //QTD MÁXIMA DE CARROS QUE POSSO PRODUZIR
            this.estoqueCarro = fabrica.getCapacidade();
            return (auxiliar*this.carro.getCusto());
        }        
    }
    
    public void fecharMes(){ //BASTA CHAMAR ESSA FUNÇÃO PRA RETORNAR O GASTO MENSAL E ATUALIZAR O CAPITAL
        double gastoMensal = this.gastoFuncionarios  + this.investimentoMarketing + gastoFixo();
        this.capital -= gastoMensal; 
    }
    
    private int calcularProbPreco(int opcao){
        int prob = 0;
        switch(opcao){
            case Carro.TIPO_PRECO_NORMAL: 
                prob += 25;
                break;
            case Carro.TIPO_PRECO_CARO:
                prob += 15;
        }
        return prob;
    }
    
    private int calcularProbMarketing(int opcao){
        int prob = 0;
        switch(opcao){
            case MARKETING_NORMAL: 
                prob += 35;
                break;
            case MARKETING_ALTO: 
                prob += 55;
        }
        
        return prob;
    }
    
    private int calcularProbabilidade(int preco, int marketing){
        int probabilidade = this.calcularProbPreco(preco) + this.calcularProbMarketing(marketing);        
        return probabilidade;
    }
    
    public void investir(int opcao){
        //this.probabilidadeVenda = this.calcularProbabilidade(this.carro.getTipoPreco(), opcao);
        switch(opcao){
            case INVESTIMENTO_MARKETING_NORMAL:
                this.investimentoMarketing = this.capital * 0.25;
                this.capital = this.capital * 0.75;
                break;
            case INVESTIMENTO_MARKETING_ALTO:
                this.investimentoMarketing = this.capital * 0.4;
                this.capital = this.capital * 0.6;
                break;
        }
    }
    
    public void atualizarFuncionarios(int opcao){
        // 1 -> contrata 10
        // 0 -> demite 10 ou o restante
        if(opcao == 1 && this.limiteFuncionarios >= 10){
            this.numeroFuncionarios += 10;
            this.limiteFuncionarios -= 10;
            return;
        }
        if(opcao == 2 && this.numeroFuncionarios < 10){
                this.limiteFuncionarios += this.numeroFuncionarios;
                this.numeroFuncionarios = 0;
            } else {
                this.limiteFuncionarios += 10;
                this.numeroFuncionarios -= 10;
        }
        this.gastoFuncionarios = this.salarioFuncionario * this.numeroFuncionarios;
    }
    
    public void venderCarro(){
        this.capital += carro.getPrecoVenda();
        this.estoqueCarro--;
    }
    
    public Empresa escolhaAcoes(int opMarketing, int opPreco, int opFuncionario) {
        Empresa novaEmpresa = this;
        novaEmpresa.investir(opMarketing);
        novaEmpresa.carro.alterarPreco(opPreco);
        novaEmpresa.atualizarFuncionarios(opFuncionario);
        return novaEmpresa;
    }
            
    public void mostraEmpresa(){
        System.out.println("Nome => " + this.nome);
        System.out.println("Capital => " + this.capital);
        System.out.println("Gastos Funcionários =>" + this.gastoFuncionarios);
        System.out.println("Investimento => " + this.investimentoMarketing);
        System.out.println("Gastos Totais => " + this.gastosTotais);
        System.out.println("");
    }
    
    /*GET SET*/

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isIsBot() {
        return isBot;
    }
    
    public double getCapital() {
        return capital;
    }

    public Carro getCarro() {
        return carro;
    }
    
    public void setCarro(int modelo){
        this.carro = new Carro(modelo, carro.getTipoPreco());
    }

    public Fabrica getFabrica() {
        return fabrica;
    }
    
    public void setFabrica(Fabrica fabrica){
        this.fabrica = fabrica;
    }

    public int getEstoqueCarro() {
        return estoqueCarro;
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

    public int getLimiteFuncionarios() {
        return limiteFuncionarios;
    }

    public void setLimiteFuncionarios(int limiteFuncionarios) {
        this.limiteFuncionarios = limiteFuncionarios;
    }

    public double getGastosTotais() {
        return gastosTotais;
    }

    public void setGastosTotais(double gastosTotais) {
        this.gastosTotais = gastosTotais;
    }

    public int getProbabilidadeVenda() {
        return probabilidadeVenda;
    }

    public void setProbabilidadeVenda(int probabilidadeVenda) {
        this.probabilidadeVenda = probabilidadeVenda;
    }
    
    
    
}
