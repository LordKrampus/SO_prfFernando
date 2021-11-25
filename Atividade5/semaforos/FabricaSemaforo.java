package semaforos;

import enumeracoes.TipoSemaforo;

public class FabricaSemaforo{

    private static FabricaSemaforo instancia;

    private FabricaSemaforo(){
    }

    public static FabricaSemaforo getIsntancia(){
        if(instancia == null)
            instancia = new FabricaSemaforo();

        return instancia;
    }

    public Semaforo gerarSemaforo(TipoSemaforo tipo, int delimitacao){
        if(tipo == TipoSemaforo.FULL)
            return new SemaforoFull(delimitacao);
        else if(tipo == TipoSemaforo.EMPTY)
            return new SemaforoEmpty(delimitacao);
        else if(tipo == TipoSemaforo.MUTEX)
            return this.gerarSemaforoMutex();

        return null;
    }

    public Semaforo gerarSemaforoMutex(){
        return new SemaforoMutex();
    }

}
