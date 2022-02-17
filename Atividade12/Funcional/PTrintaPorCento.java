package Funcional;

/**
 * Implementa um sorteador para corresponder a validade de probabilidade de trinta por cento.
 */
public class PTrintaPorCento extends Sorteador{
    public PTrintaPorCento(){
        super();
    }

    @Override
    public int getProbabilidade(){
        return 30;
    }
}