package semaforos;

public class SemaforoEmpty extends Semaforo {

    public SemaforoEmpty(int delimitacaoMaior){
        super(delimitacaoMaior, 0, delimitacaoMaior);
    }
}
