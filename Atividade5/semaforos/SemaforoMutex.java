package semaforos;

public class SemaforoMutex extends Semaforo{

    public SemaforoMutex(){
        super(1, 0, 1);
    }
}