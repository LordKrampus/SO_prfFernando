package gerenciamentoMemoria;

import algoritmosSubstituicao.AlgoritmoSubstituicao;
import enumeracoes.ReferenciaAtributoTP;

public abstract class SistemaMemoria{
    private int[][] tabelaEstruturas;
    private final int numEstruturas;
    private final int numAtributos;

    //private AlgoritmoSubstituicao as;
    
    public SistemaMemoria(int numEstruturas, int numAtributos){
        this.tabelaEstruturas = new int[numEstruturas][numAtributos];
        this.numEstruturas = numEstruturas;
        this.numAtributos = numAtributos;

        //this.as = null;
    }

    public void configAS(AlgoritmoSubstituicao as){
        //this.as = as;
    }

    private void notificarEntrada(int[] estrutura){
        //this.as.entrada(estrutura);
    }

    protected void preInicializarEstruturas(int[][] tabela) throws IndexOutOfBoundsException{
        boolean notificar;
        if(this instanceof SistemaMolduras)
            notificar = true;
        else
            notificar = false;

        int[][] tabelaEstruturas = this.tabelaEstruturas;

        try{
            int i, j;
            for(i = 0; i < this.numEstruturas; i++){
                for(j = 0; j < this.numAtributos; j++){
                        tabelaEstruturas[i][j] = tabela[i][j];
                }

                if(notificar)
                    //this.notificarEntrada(tabelaEstruturas[i])
                    ;
            }
        }catch(IndexOutOfBoundsException e){
            throw e;
        }

        //se fora devidamente percorrida, sua copia;
        this.tabelaEstruturas = tabelaEstruturas;
    }

    public int[][] getTabelaPaginas(){
        return this.tabelaEstruturas;
    }

    public int getEstruturaPagina(int numero){
        return -1;
    }

    public int buscarInstrucao(int instrucao){
        for(int[] estrutura : this.tabelaEstruturas){
            if(estrutura[ReferenciaAtributoTP.I.ordinal()] == instrucao)
                return estrutura[ReferenciaAtributoTP.N.ordinal()];
        }

        return -1;
    }

    public int[] buscarEstrutura(int refPagina){
        for(int[] estrutura : this.tabelaEstruturas){
            if(estrutura[ReferenciaAtributoTP.N.ordinal()] == refPagina)
                return estrutura;
        }

        return null;
    }

    public void attCampoEstrutura(int refPagina, int indiceModif, int valorNovo){
        for(int i = 0; i < numEstruturas; i++){
            if(this.tabelaEstruturas[i][ReferenciaAtributoTP.N.ordinal()] == refPagina){
                this.tabelaEstruturas[i][indiceModif] = valorNovo;
            }
        }
    }

    public void attAllCampoEstrutura(int indiceModif, int valorNovo){
        for(int i = 0; i < numEstruturas; i++){
            this.tabelaEstruturas[i][indiceModif] = valorNovo;
        }
    }

    public void attEstrutura(int refPagina, int[] valoresNovos){
        for(int i = 0; i < numEstruturas; i++){
            if(this.tabelaEstruturas[i][ReferenciaAtributoTP.N.ordinal()] == refPagina){
                this.tabelaEstruturas[i] = valoresNovos.clone();
            }
        }
    }

    public abstract String getNomeTipoTabela();

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getNomeTipoTabela() + "\n");

        int i, j;
        for(i = 0; i < this.numEstruturas; i++){
            sb.append("[");
            for(j = 0; j < this.numAtributos; j++){
                sb.append(String.format("%7d", this.tabelaEstruturas[i][j]));

                if(j < (this.numAtributos - 1))
                    sb.append(", ");
            }
            
            sb.append("]\n");
        }

        return sb.toString();
    }
}