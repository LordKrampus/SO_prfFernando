package fabricas;

import estruturas.PCB;
import entidades.Processo;

public class FabricaProcessos {
    
    private static FabricaProcessos instancia;
    
    private FabricaProcessos(){
    }
    
    public static FabricaProcessos getInstancia(){
        if (instancia == null){
            instancia = new FabricaProcessos();
        }
        
        return instancia;
    }
    
    public Processo gerarProcesso(int PID, int tamanho){
        PCB registro;
        Processo processo;
        
        registro = new PCB(PID);
        processo = new Processo(registro, tamanho);
        
        return processo;
    }   
}
