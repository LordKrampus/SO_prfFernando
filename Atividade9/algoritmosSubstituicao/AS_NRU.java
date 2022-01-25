package algoritmosSubstituicao;

import enumeracoes.ReferenciaAtributoTP;
import gerenciamentoMemoria.SistemaMemoria;

public class AS_NRU extends AlgoritmoSubstituicao{
    private int limiteClock;
    private int contagem;
    int[][] classes;

    public AS_NRU(){
        this.limiteClock = 5;
        this.contagem = 0;
        this.classes = null;
    }

    public AS_NRU(SistemaMemoria sPagina, SistemaMemoria sMoldura){
        super(sPagina, sMoldura);

        this.limiteClock = 5;
        this.contagem = 0;
        this.classes = null;
    }

    @Override
    public void carregarMolduras(SistemaMemoria sMoldura) {
        //vazio
    }

    @Override
    public void substituir(int numero) throws Exception{
        int[] estruturaNova;

        //busca referencia virtual e atualiza na referencia fisica
        estruturaNova = super.sPagina.buscarEstrutura(numero);
        if (estruturaNova == null)
            throw new Exception ("Instrução não encontrada em referencia de memoria virtual!");

        int refRetirada = this.retirada();
        super.atualizarMemoria(refRetirada);

        this.sMoldura.attEstrutura(refRetirada, estruturaNova);
        //this.entrada(estruturaNova);
    }

    @Override
    public void entrada(int[] estrutura){
        //vazia
    }

    @Override
    protected int retirada() throws Exception{
        int[][] tabelaPaginas = super.sMoldura.getTabelaPaginas();
        int numPAginas = tabelaPaginas.length;

        this.classes = new int[4][numPAginas];
        int[] contagemClasses = new int[4];
        for(int[] pagina : tabelaPaginas){
            if(pagina[ReferenciaAtributoTP.R.ordinal()] == 0){
                if(pagina[ReferenciaAtributoTP.M.ordinal()] == 0){
                    this.classes[0][contagemClasses[0]] = pagina[ReferenciaAtributoTP.N.ordinal()];
                    contagemClasses[0]++;
                }else{
                    this.classes[1][contagemClasses[1]] = pagina[ReferenciaAtributoTP.N.ordinal()];
                    contagemClasses[1]++;
                }
            }else{
                if(pagina[ReferenciaAtributoTP.M.ordinal()] == 0){
                    this.classes[2][contagemClasses[2]] = pagina[ReferenciaAtributoTP.N.ordinal()];
                    contagemClasses[2]++;
                }else{
                    this.classes[3][contagemClasses[3]] = pagina[ReferenciaAtributoTP.N.ordinal()];
                    contagemClasses[3]++;
                }
            }
        }

        /*
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < numPAginas; j++){
                System.out.println(this.classes[i][j]);
            }
        }
        //*/

        for(int i = 0; i < 4; i++){
            for(int e = 0; e < contagemClasses[i]; e++){
                return classes[i][e];
            }
        }

        return -1;
    }

    private void atualizarAcesso(){
        super.sMoldura.attAllCampoEstrutura(ReferenciaAtributoTP.R.ordinal(), 0);
    }

    @Override
    public void atualizar(){
        this.contagem++;

        if(this.contagem >= this.limiteClock) {
            this.contagem = 0;
            this.atualizarAcesso();
        }
    }
}
