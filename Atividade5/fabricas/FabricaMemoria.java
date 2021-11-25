package fabricas;

import enumeracoes.TipoMemoria;
import memorias.BufferDados;
import memorias.FilaProcessos;
import memorias.ListaInstrucoes;
import memorias.Memoria;

public class FabricaMemoria{

    private static FabricaMemoria instancia;

    private FabricaMemoria(){
    }

    public static FabricaMemoria getInstancia(){
        if(instancia == null)
            instancia = new FabricaMemoria();

        return instancia;
    }

    public Memoria gerarMemoria(TipoMemoria tipo){
        if(tipo == TipoMemoria.BUFFER_DADOS)
            return new BufferDados();
        else if(tipo == TipoMemoria.LISTA_INSTRUCOES)
            return new ListaInstrucoes();
        else if(tipo == TipoMemoria.FILA_PROCESSOS)
            return new FilaProcessos();
        
        return null;
    }
} 
