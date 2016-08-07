package modelos;

public class Empresa {
    
    public final static double INVESTIMENTO_MARKETING_NORMAL = 0.05; // 5% de investimento do capital
    public final static double INVESTIMENTO_MARKETING_ALTO = 0.15;   // 15% de investimento do capital
    
    public final static int MARKETING_NORMAL = 0;
    public final static int MARKETING_ALTO = 1;
    
    public final static int FATOR_FUNCIONARIO_CONTRATAR = 10;
    public final static int FATOR_FUNCIONARIO_DEMITIR = 10;
    
    public final static int SALARIO_FUNCIONARIO = 3000;
    public final static int LIMITE_FUNCIONARIO = 100;      //Número máximo de funcionários por empresa
    
    //CONFIGURAÇÃO INICIAL
    private String nome;
    private double capital;
    
    private Carro carro;
    private Fabrica fabrica;
    
    private int estoqueCarro;
    
    private int numeroFuncionarios;
    
    private int probabilidadeVenda;
    
    private final boolean isBot;          // true se for IA, false se for jogador
            
    //PARÂMETROS VARIÁVEIS
    private int tipoMarketing;
    
    // VARIAVEIS QUE MUDAM NO MÊS
    private double lucrouNoMes;
    private int carrosVendidosNoMes;
    private int funcionariosAContratar;

    public Empresa(String nome, int investimento, boolean isBot){
        this.nome = nome;
        this.capital = investimento;
        this.isBot = isBot;
        if (isBot){
            this.carro = Carro.aleatorio();
            setFabrica(Fabrica.aleatorio());
        } else {
            this.carro = new Carro(Carro.MODELO_POPULAR, Carro.TIPO_PRECO_NORMAL);
            setFabrica(Fabrica.PEQUENA);
        }
        this.funcionariosAContratar = 0;
        this.estoqueCarro = 0;
        this.tipoMarketing = MARKETING_NORMAL;
        this.numeroFuncionarios = this.fabrica.getNumeroFuncionarioInicial();
    }
    
    // Construtor de cópia
    public Empresa(Empresa empresa) {
        //recebe atributos pai
        this.nome = empresa.nome;
        this.capital = empresa.capital;
        this.carro = empresa.carro;
        this.fabrica = empresa.fabrica;
        this.isBot = empresa.isBot;
        this.estoqueCarro = empresa.estoqueCarro;
        this.numeroFuncionarios = empresa.numeroFuncionarios;
    }
    
    public boolean temEstoque(){
        return estoqueCarro > 0;
    }
    
    public double carrosPorDia(){
        double fatorFuncionario;
        if (numeroFuncionarios <= 0){
            fatorFuncionario = 1;
        } else {
            fatorFuncionario = 1 - ((double)numeroFuncionarios) / (4*(double)LIMITE_FUNCIONARIO);
        }
        return (carro.getTempo() / fabrica.getFatorProducao()) * (fatorFuncionario);
    }
    
    public int carrosPorMes(){
        return (int) (30 / carrosPorDia());
    }
    
    public int calcularCarrosProduzidos(){
        int produzidos;
        if (carrosPorMes() + estoqueCarro > fabrica.getCapacidade()){
            produzidos = fabrica.getCapacidade() - estoqueCarro;
        } else {
            produzidos = carrosPorMes();
        }
        return produzidos;
    }
    
    public double calcularGastoProducaoCarros(){
        return calcularCarrosProduzidos() * carro.getCusto();
    }
    
    public double calcularGastoFixo(){
        double gasto = calcularGastoProducaoCarros();
         gasto += this.fabrica.getGastoPorMes();
         return gasto;
     }
     
    public int calcularGastoFuncionario(int numFuncionario){
        return numFuncionario * SALARIO_FUNCIONARIO;
    }
    
    public int calcularGastoFuncionarios(){
        return calcularGastoFuncionario(numeroFuncionarios);
    }
    
    public double calcularInvestimentoMarketing() {
        return investimentoMarketing(tipoMarketing);
    }
    
    public double calcularGastoMensal(){
        return calcularGastoFixo() + calcularGastoFuncionario(numeroFuncionarios) + calcularInvestimentoMarketing();
    }
    
    public void fecharMes(){
        lucrouNoMes = 0;
        carrosVendidosNoMes = 0;
        this.contratarFuncionarios();
        double gastoMensal = calcularGastoMensal();
        this.capital -= gastoMensal; 
        this.probabilidadeVenda = calcularProbabilidade();
        this.fabricarCarros();
        setFuncionariosAContratar(0);
    }
    
    private void contratarFuncionarios(){
        int num = numeroFuncionarios + funcionariosAContratar;
        if (num > LIMITE_FUNCIONARIO){
            numeroFuncionarios = LIMITE_FUNCIONARIO;
        } else if (num < 0){
            numeroFuncionarios = 0;
        } else {
            numeroFuncionarios = num;
        }
    }
    
    public void venderCarros(int quantidade){
        carrosVendidosNoMes = quantidade;
        lucrouNoMes += carro.getPrecoVenda()*quantidade;
        capital += lucrouNoMes;
        this.estoqueCarro -= quantidade;
    }
    
    public void venderCarro() {
        carrosVendidosNoMes++;
        lucrouNoMes += carro.getPrecoVenda();
        this.capital += carro.getPrecoVenda();
        this.estoqueCarro--;
    }
    
    public double fabricarCarros(){
        this.estoqueCarro += calcularCarrosProduzidos();
        return calcularGastoProducaoCarros();
    }

    public int getFuncionariosAContratar() {
        return funcionariosAContratar;
    }
            
    public void setFuncionariosAContratar(int numFuncionarios){
        funcionariosAContratar = numFuncionarios;
    }
    
    private int calcularProbPreco(int opcao){
        int prob = 0;
        switch(opcao){
            case Carro.TIPO_PRECO_NORMAL: 
                prob += 35;
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
                prob += 45;
        }
        
        return prob;
    }
    
    private int calcularProbabilidade(int preco, int marketing){
        int probabilidade = this.calcularProbPreco(preco) + this.calcularProbMarketing(marketing);        
        return probabilidade;
    }
    
    public int calcularProbabilidade(){
        return calcularProbabilidade(this.carro.getTipoPreco(), this.tipoMarketing);
    }
    
    public Double investimentoMarketing(int opcao){
        switch(opcao){
            case MARKETING_NORMAL:
                return this.capital * INVESTIMENTO_MARKETING_NORMAL;
            case MARKETING_ALTO:
                return this.capital * INVESTIMENTO_MARKETING_ALTO;
        }
        return -1.0;
    }
    
    /* 
        Métodos para Inteligência Artificial
    */
    
    // Heurística
    public void estimarVenda(){
        //Vender Carros
        //System.out.println("Prob => " + this.probabilidadeVenda + "Estoque => " + this.estoqueCarro);
        int vendaEstimada = this.estoqueCarro * this.probabilidadeVenda/100;
        //System.out.println("Venda => " + vendaEstimada + "Preco => " + this.carro.getPrecoVenda());
        int ganhoVenda = vendaEstimada * this.carro.getPrecoVenda();
        //System.out.println("ganho Venda => "+ ganhoVenda);
        this.capital += ganhoVenda;
        //System.out.println("Capital => " + this.capital);
        this.estoqueCarro -= vendaEstimada;
    }
    
    private void realizarAcoes(int opMarketing, int opPreco, int opFunc) {
        //Atualizar Preço Carro
        this.carro.alterarPreco(opPreco);
        
        //Atualizar Marketing
        setTipoMarketing(opMarketing);
        
        //Atualizar Funcionários
        setFuncionariosAContratar(opFunc);
        
        this.fecharMes();
    }
    
    public void escolherAcoes(int id){
        switch(id){
            case 0: //Marketing Normal Preço Normal Funcionario Demitido
                this.realizarAcoes(Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_NORMAL, -Empresa.FATOR_FUNCIONARIO_DEMITIR);
                break;
            case 1: //MN PN FM
                this.realizarAcoes(Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_NORMAL, 0);
                break;
            case 2: //MN PN FC
                this.realizarAcoes(Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_NORMAL, Empresa.FATOR_FUNCIONARIO_CONTRATAR);
                break;
                
                
            case 3: //MN PA FM
                this.realizarAcoes(Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_CARO, 0);
                break;
            case 4: //MN PA FC
                this.realizarAcoes(Empresa.MARKETING_NORMAL, Carro.TIPO_PRECO_CARO, Empresa.FATOR_FUNCIONARIO_CONTRATAR);
                break;
                
            case 5: //MA PN FM
                this.realizarAcoes(Empresa.MARKETING_ALTO, Carro.TIPO_PRECO_NORMAL, 0);
                break;
            case 6: //MA PN FC
                this.realizarAcoes(Empresa.MARKETING_ALTO, Carro.TIPO_PRECO_NORMAL, Empresa.FATOR_FUNCIONARIO_CONTRATAR);
                break;
                
            case 7: //MA PA FM
                this.realizarAcoes(Empresa.MARKETING_ALTO, Carro.TIPO_PRECO_CARO, 0);
                break;
            case 8: //MA PA FC
                this.realizarAcoes(Empresa.MARKETING_ALTO, Carro.TIPO_PRECO_CARO, Empresa.FATOR_FUNCIONARIO_CONTRATAR);
                break;
        }
    }
    
    public void mostraEmpresa(){
        System.out.println("====================================");
        System.out.println("Nome => " + this.nome);
        System.out.println("Capital => " + this.capital);
        System.out.println("Probabilidade = > " + this.probabilidadeVenda);
        System.out.println("");
        
        System.out.println("Carro");
        System.out.println("Estoque => " + this.estoqueCarro);
        System.out.println("Modelo => " + this.carro.getStringModelo());
        System.out.println("Tipo Preco => " + this.carro.getTipoPreco());
        System.out.println("Preco de Venda =>" + this.carro.getPrecoVenda());
        System.out.println("");
        
        System.out.println("Fabrica");
        System.out.println("Nome => " + this.fabrica.getNome());
        System.out.println("Numero Funcionarios => " + this.numeroFuncionarios);
        System.out.println("Salario Funcionarios => " + SALARIO_FUNCIONARIO);
        System.out.println("Gastos Funcionarios => " + this.calcularGastoFuncionarios());
        System.out.println("");
        
        System.out.println("Investimento => " + this.investimentoMarketing(tipoMarketing));
        //System.out.println("Gastos Totais => " + this.gastosTotais);
        System.out.println("Probabilidade Venda => " + this.probabilidadeVenda);
        System.out.println("");
    }
    
    /*GET SET*/

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isBot() {
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
        if (this.fabrica != null)
            this.capital += this.fabrica.getPreco();    // devolve dinheiro de compra da fábrica
        this.fabrica = fabrica;
        this.capital -= this.fabrica.getPreco();    // desconta dinheiro de compra da nova fabrica
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

    public int getProbabilidadeVenda() {
        return probabilidadeVenda;
    }

    public void setProbabilidadeVenda(int probabilidadeVenda) {
        this.probabilidadeVenda = probabilidadeVenda;
    }

    public int getTipoMarketing() {
        return tipoMarketing;
    }

    public void setTipoMarketing(int tipoMarketing) {
        this.tipoMarketing = tipoMarketing;
    }

    public double getLucrouNoMes() {
        return lucrouNoMes;
    }

    public int getCarrosVendidosNoMes() {
        return carrosVendidosNoMes;
    }
    
}