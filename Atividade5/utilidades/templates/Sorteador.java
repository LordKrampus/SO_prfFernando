package utilidades.templates;

import utilidades.GeradorAleatorio;

/**
 * Delimita uma probabilidade e deve sortear um valor delimitando-o se atinge a probabilidade.
 */
public abstract class Sorteador {
    private int valor;
    
    public boolean sortear(){
        valor = GeradorAleatorio.gerarInt(0, 99);
        return valor < getProbabilidade();
    }
    
    public abstract int getProbabilidade();
    
    public int getValor(){
        return this.valor;
    }
}
