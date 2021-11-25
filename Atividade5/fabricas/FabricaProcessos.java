package fabricas;

import estruturas.PCB;
import entidades.Processo;
import enumeracoes.TipoProcesso;

public abstract class FabricaProcessos {
    
    public static FabricaProcessos getInstancia(TipoProcesso tipo) throws NullPointerException{
        if(tipo == null) throw new NullPointerException(".Exception - \tTipo de processo não declarado!");

        if(tipo == TipoProcesso.COMUM)
            return FabricaProcessosComum.getInstancia();
        else if(tipo == TipoProcesso.LE)
            return FabricaProcessosLE.getInstancia();
        else throw new NullPointerException(".Exception - \tTipo de processo não especifico!");
    }

    public abstract Processo gerarProcesso(PCB registro);
}