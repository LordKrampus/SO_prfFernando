package fabricas;

import estruturas.PCB;
import entidades.ProcessoLE;

public class FabricaProcessosLE extends FabricaProcessos{
    
    private static FabricaProcessosLE instancia;
    
    private FabricaProcessosLE(){
    }
    
    public static FabricaProcessosLE getInstancia(){
        if (instancia == null){
            instancia = new FabricaProcessosLE();
        }
        
        return instancia;
    }

    public ProcessoLE gerarProcesso(PCB registro){
        return new ProcessoLE(registro);
    }
}