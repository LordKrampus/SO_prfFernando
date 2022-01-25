package gerenciamentoMemoria;

import algoritmosSubstituicao.AlgoritmoSubstituicao;

public class SistemaPaginas extends SistemaMemoria{

    //[5] -> Número de Página (N), Instrução (I), Dado (D), Bit de Acesso (R), Bit de Modificação (M).
    public SistemaPaginas(){
        super(100, 5);
    }

    public SistemaPaginas(int numPaginas, int numAtributos){
        super(numPaginas, numAtributos);
    }

    @Override
    public String getNomeTipoTabela(){
        return "Tabela de Paginas (Referencia de Memoria Virtual)";
    }
}