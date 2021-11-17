package utilidades;

import java.util.Random;


public class GeradorAleatorio {
    private static Random gerador = new Random();
    
    public static int gerarInt(int inicial, int num){
        //*
        int valor = inicial + gerador.nextInt(num + 1);
        
        //if(valor <= 5 || valor <= 30)
        //    System.out.println(valor);
        
        return valor;
        //*/
        
        //return inicial + gerador.nextInt(num + 1);
    }
}
