package gerenciamentoMemoria;

import algoritmosSubstituicao.AlgoritmoSubstituicao;

public class SistemaMolduras extends SistemaMemoria{
    
    //[5] -> Número de Página (N), Instrução (I), Dado (D), Bit de Acesso (R), Bit de Modificação (M).
    public SistemaMolduras(){
        super(10, 5);
    }

    public SistemaMolduras(int numMolduras, int numAtributos){
        super(numMolduras, numAtributos);
    }

    @Override
    public String getNomeTipoTabela(){
        return "Tabela de Molduras (Referencia de Memoria Fisica)";
    }
}
