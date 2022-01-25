package gerenciamentoMemoria;

import algoritmosSubstituicao.AS_FIFO;
import algoritmosSubstituicao.AlgoritmoSubstituicao;
import enumeracoes.ReferenciaAtributoTP;

public class GerenciadorMemoria {
    private SistemaMemoria sPaginas; //sistema de Páginas
    private SistemaMemoria sMolduras; //sistema de Molduras
    private AlgoritmoSubstituicao as;

    /**
     * Inicializa um sistema de gerenciamento de referencia de memoria - entre virtual e física,
     *  Por meio do sistema de memoria por referencias de paginas e molduras.
     * @param numPaginas Configuração da inicialização do sistema de Paginas, 
     *  quanto ao número de estruturas de página.
     * @param numMolduras Configuração da inicialização do sistema de Molduras, 
     *  quanto ao número de estruturas de moldura.
     * @param numAtributos Configuração da inicialização das estruturas de memoria,
     *  quanto ao número de atributos relacionados.
     */
    public GerenciadorMemoria(int numPaginas, int numMolduras, int numAtributos){
        this.sPaginas = new SistemaPaginas(numPaginas, numAtributos);
        this.sMolduras = new SistemaMolduras(numMolduras, numAtributos);

        //configuracao do algoritmo de substituicao
        this.as = new AS_FIFO(this.sPaginas, this.sMolduras);

        this.sPaginas.configAS(as);
        this.sMolduras.configAS(as);
    }

    public GerenciadorMemoria(int[][] tabelaPaginas, int[][] tabelaMolduras, AlgoritmoSubstituicao as) 
        throws IndexOutOfBoundsException
    {
        this.sPaginas = new SistemaPaginas(tabelaPaginas.length, tabelaPaginas[0].length);
        this.sMolduras = new SistemaMolduras(tabelaMolduras.length, tabelaPaginas[0].length);

        //this.sPaginas.configAS(as);
        //this.sMolduras.configAS(as);

        //configura valores nas estruturas
        try {
            this.sPaginas.preInicializarEstruturas(tabelaPaginas);
            this.sMolduras.preInicializarEstruturas(tabelaMolduras);
        }catch(IndexOutOfBoundsException e){
            throw e;
        }

        //configuracao do algoritmo de substituicao
        this.as = as;
        this.as.configSM(this.sPaginas, this.sMolduras);
    }

    public GerenciadorMemoria(int[][] tabelaPaginas, int[][] tabelaMolduras) throws IndexOutOfBoundsException{
        //inicializa as estruturas
        this(tabelaPaginas.length, tabelaMolduras.length, tabelaPaginas[0].length);

        //configura valores nas estruturas
        this.sPaginas.preInicializarEstruturas(tabelaPaginas);
        this.sMolduras.preInicializarEstruturas(tabelaMolduras);
    }

    public void notificarAS(){
        this.as.atualizar();
    }

    private void substituir(int numero) throws Exception{
        this.as.substituir(numero);
    }

    public int[] buscarInstrucao(int instrucao){
        int[] busca;

        int[] buscaMoldura = {0, this.sMolduras.buscarInstrucao(instrucao)};
        
        if(buscaMoldura[1] == -1){
            int[] buscaPagina = {1, this.sPaginas.buscarInstrucao(instrucao)};

            if(buscaPagina[1] == -1)
                busca = new int[] {-1, -1};
            else 
                busca = buscaPagina;

        } else{
            busca = buscaMoldura;
        }

        return busca;
    }

    public int[] buscarEstrutura(int numero){
        return this.sMolduras.buscarEstrutura(numero);
    }

    public void acessar(int instrucao) throws Exception{
        int[] busca;
        int numero;

        busca = this.buscarInstrucao(instrucao);
        numero = busca[1];
        if(numero == -1) throw new Exception("Instrução não encontrada - " + instrucao); // não existe a instrucao

        //transporta referencia de pagina virtual para a referencia de memoria fisica (sistema de moldura) 
        if(busca[0] == 1)
            this.substituir(numero);

        //marcar acesso
        this.sMolduras.attCampoEstrutura(numero, ReferenciaAtributoTP.R.ordinal(), 1);
    }

    public void modificar(int instrucao, int incrementador) throws Exception{
        int[] busca, estrutura;
        int numero, dadoNovo;

        busca = this.buscarInstrucao(instrucao);
        numero = busca[1];
        if(numero == -1) throw new Exception("Instrução não encontrada - " + instrucao); // não existe a instrucao

        //transporta referencia de pagina virtual para a referencia de memoria fisica (sistema de moldura) 
        if(busca[0] == 1)
            this.substituir(numero);

        //marca modificacao
        this.sMolduras.attCampoEstrutura(numero, ReferenciaAtributoTP.R.ordinal(), 1);

        //prepara mmodificacao do dado
        estrutura = this.buscarEstrutura(numero);
        if(estrutura == null) 
            throw new Exception("Erro de localização da estrutura de pagina (na moldura).");

        dadoNovo = estrutura[ReferenciaAtributoTP.D.ordinal()] + incrementador;

        //opera modificacao
        this.sMolduras.attCampoEstrutura(numero, ReferenciaAtributoTP.D.ordinal(), dadoNovo);

        //marca modificacao
        this.sMolduras.attCampoEstrutura(numero, ReferenciaAtributoTP.M.ordinal(), 1);
    }

    public String toStringSPaginas(){
        return this.sPaginas.toString();
    }

    public String toStringSMolduras(){
        return this.sMolduras.toString();
    }
}
