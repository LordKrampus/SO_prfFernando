import Processamento.Escalonador;
import entidades.Processo;
import fabricas.FabricaProcessos;
import memorias.Fila;
import Processamento.Processador;

import utilidades.SorteadorCincoPorCento;
import utilidades.SorteadorTrintaPorCento;
import utilidades.templates.Sorteador;

public class Simulacao {
    
    final static int[] TAMANHO_PROCESSOS = {
        10000,
        5000,
        7000,
        3000,
        3000,
        8000,
        2000,
        5000,
        4000,
        10000
    };
    
    static Processo[] processos;
    //static Processador processador;
    static Escalonador escalonador;
    
    public static void main(String[] args) {
        FabricaProcessos fb = FabricaProcessos.getInstancia();
        processos = new Processo[10];
        
        for(int i = 0; i < 10; i++){
            processos[i] = (Processo)fb.gerarProcesso(i, TAMANHO_PROCESSOS[i]);
            System.out.println(processos[i].toString());
        }
        
        
        //*/
        escalonador = (Escalonador) Processador.getInstancia();
        
        int i = 0;
        boolean escalonar = true;
        do{
            if(escalonar)
                escalonador.escalonarProcesso(processos[i]);
            
            escalonador.rotina();
            
            if(++i == processos.length){
                escalonar = false;
                i = 0;
            } 
        }while(escalonador.temProcesso());
        
        System.out.println("\n\nTERMINOU:");
        for(Processo p: processos)
            System.out.println(p.toString());
       //*/
        
        /*/
        Sorteador sorteador;
        sorteador = (SorteadorCincoPorCento) new SorteadorCincoPorCento();
        //sorteador = (SorteadorTrintaPorCento) new SorteadorTrintaPorCento();
        
        int e;
        int valor = 0;
        int contador = 0;
        for(e = 0; e < 25; e++){
            if(sorteador.sortear()){
                contador++;
                
                valor = sorteador.getValor();
                System.out.println("valor sorteado: " +valor + "\titeracao: " + e);
            }
        }
        
        System.out.println(contador++);
        //*/
        
    }
    
}
