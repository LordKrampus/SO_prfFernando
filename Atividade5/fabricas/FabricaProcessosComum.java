package fabricas;

import estruturas.PCB;
import entidades.Processo;

public class FabricaProcessosComum extends FabricaProcessos{
    
    private static FabricaProcessosComum instancia;
    
    private FabricaProcessosComum(){
    }
    
    public static FabricaProcessosComum getInstancia(){
        if (instancia == null){
            instancia = new FabricaProcessosComum();
        }
        
        return instancia;
    }

    public Processo gerarProcesso(PCB registro){
        return new Processo(registro);
    }
}