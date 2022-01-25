package algoritmosSubstituicao;

import enumeracoes.ReferenciaAtributoTP;
import gerenciamentoMemoria.SistemaMemoria;

public abstract class AlgoritmoSubstituicao {
    protected SistemaMemoria sPagina;
    protected SistemaMemoria sMoldura;

    public AlgoritmoSubstituicao(SistemaMemoria sPagina, SistemaMemoria sMoldura){
        this.configSM(sPagina, sMoldura);
    }

    public AlgoritmoSubstituicao(){
        this.configSM(null, null);
    }

    public void configSM(SistemaMemoria sPagina, SistemaMemoria sMoldura){
        this.sPagina = sPagina;
        this.sMoldura = sMoldura;

        if(this.sMoldura != null)
            this.carregarMolduras(this.sMoldura);
    }

    public abstract void carregarMolduras(SistemaMemoria sMoldura);

    protected void atualizarMemoria(int refPagina){
        int[] valoresMoldura = this.sMoldura.buscarEstrutura(refPagina);

        // sincroniza alteração de valor do dado da referencia de
        //      moldura de pagina (temporaria) para a armazenada (virtual)
        if(valoresMoldura[ReferenciaAtributoTP.M.ordinal()] == 1)
            this.sPagina.attCampoEstrutura(
                    refPagina,
                    ReferenciaAtributoTP.D.ordinal(),
                    valoresMoldura[ReferenciaAtributoTP.D.ordinal()]
                );
    }

    public abstract void substituir(int numero) throws Exception;

    public abstract void entrada(int[] numero);

    protected abstract int retirada() throws Exception;

    public abstract void atualizar();
}
