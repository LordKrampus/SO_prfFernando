package algoritmosSubstituicao;

import estruturasDados.fila.Fila;

import enumeracoes.ReferenciaAtributoTP;
import gerenciamentoMemoria.SistemaMemoria;

public class AS_FIFO extends AlgoritmoSubstituicao{
    private Fila filaEstruturas;

    public AS_FIFO(){
        this.filaEstruturas = new Fila();
    }

    public AS_FIFO(SistemaMemoria sPagina, SistemaMemoria sMoldura){
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

        int refRetirada = this.retirada();
        super.atualizarMemoria(refRetirada);

        this.sMoldura.attEstrutura(refRetirada, estruturaNova);
        this.entrada(estruturaNova);
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
        //None
    }
}
