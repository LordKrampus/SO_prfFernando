package semaforos;

public interface InterfaceSemaforo{

    public int getEstado();
    public void up() throws Exception;
    public void down() throws Exception;

}