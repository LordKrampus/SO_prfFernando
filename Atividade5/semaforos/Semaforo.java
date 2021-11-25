package semaforos;

/**
 * Util no Gerenciamento de acesso a regiao crítica, para o armazenamento ou acesso a dados.
 */
public abstract class Semaforo implements InterfaceSemaforo{

    private final int delimitacaoMenor;
    private final int delimitacaoMaior;

    private int estado;

    public Semaforo(int inicia, int delimitacaoMenor, int delimitacaoMaior){
        this.delimitacaoMenor = delimitacaoMenor;
        this.delimitacaoMaior = delimitacaoMaior;

        this.estado = inicia;
    }

    public int getEstado(){
        return this.estado;
    }

    public void up() throws Exception{
        if(this.estado < this.delimitacaoMaior)
            this.estado++;
        else 
            throw this.upError();
    }

    public void down() throws Exception{
        if(this.estado > this.delimitacaoMenor)
            this.estado--;
        else 
            throw this.downException();
    }

    private Exception upError(){
        return new Exception(".Exception - \tSemaforo Cheio!");
    }

    private Exception downException(){
        return new Exception(".Exception - \tSemaforo Vazio!");
    }
}