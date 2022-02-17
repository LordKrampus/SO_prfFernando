package Funcional;

import java.util.Random;

public class GeradorAleatorio {
    public static Random gerador = new Random();

    /**
     * Gera um valor inteiro aleatorio entre um intervalo delimitado por menor e maior (argumentos da invocação).
     * @param menor valor inicial do intervalo, o valor base possível;
     * @param maior valor final do intervalo, o maior valor possível.
     * @return o valor aleatorio gerado.
     */
    public static int gerarInt(int menor, int maior){

        int valor = menor + gerador.nextInt(maior - menor + 1);

        //if(valor <= 1 || valor >= 100)
            //System.out.println(valor);

        return valor;
    }
}
