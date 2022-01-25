package algoritmosSubstituicao;

import enumeracoes.ReferenciaAtributoTP;
import estruturasDados.fila.Fila;
import gerenciamentoMemoria.SistemaMemoria;

public class AS_FIFO_SC extends AlgoritmoSubstituicao{
    private Fila filaEstruturas;

    public AS_FIFO_SC(){
        this.filaEstruturas = new Fila();
    }

    public AS_FIFO_SC(SistemaMemoria sPagina, SistemaMemoria sMoldura){
        super(sPagina, sMoldura);

        this.filaEstruturas = new Fila();
    }

    @Override
    public void carregarMolduras(SistemaMemoria sMoldura) {
        for(int[] estrutura : sMoldura.getTabelaPaginas()){
            this.entrada(estrutura);
        }
    }

    @Override
    public void substituir(int numero) throws Exception{
        int[] estruturaNova;

        //busca referencia virtual e atualiza na referencia fisica
        estruturaNova = super.sPagina.buscarEstrutura(numero);
        if (estruturaNova == null) 
            throw new Exception ("Instrução não encontrada em referencia de memoria virtual!");

        int refRetirada = -1;
        int numIteracoes = this.filaEstruturas.getTamanho() + 1;
        for(int i = 0; i < numIteracoes; i++){
            int[] primeiro = super.sMoldura.buscarEstrutura(
                    this.filaEstruturas.getFirst()[ReferenciaAtributoTP.N.ordinal()]
            );

            //segunda chance
            if(primeiro[ReferenciaAtributoTP.R.ordinal()] == 1){
                super.sMoldura.attCampoEstrutura(
                        primeiro[ReferenciaAtributoTP.N.ordinal()],
                        ReferenciaAtributoTP.R.ordinal(),
                        0
                );

            this.filaEstruturas.add(this.filaEstruturas.remover());

            }else {
                refRetirada = this.retirada();
                break;
            }
        }

        super.atualizarMemoria(refRetirada);

        this.sMoldura.attEstrutura(refRetirada, estruturaNova);
        this.entrada(estruturaNova);

        //System.out.println(this.filaEstruturas.toString());
    }

    @Override
    public void entrada(int[] estrutura){
        this.filaEstruturas.add(estrutura);
    }

    @Override
    protected int retirada() throws Exception{
        int[] refEstrutura;

        refEstrutura = this.filaEstruturas.remover();
        if(refEstrutura == null)
            throw new Exception ("Erro no gerenciamento de lista -  algoritmo: AS_FIFO - funcao: remover");

        return (int) refEstrutura[ReferenciaAtributoTP.N.ordinal()];
    }

    @Override
    public void atualizar(){
        //vazio
    }
}
