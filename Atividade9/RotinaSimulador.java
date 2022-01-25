import algoritmosSubstituicao.AlgoritmoSubstituicao;
import gerenciamentoMemoria.GerenciadorMemoria;
import utilidades.GeradorAleatorio;
import utilidades.PTrintaPorCento;
import utilidades.Sorteador;

import java.util.Scanner;

public class RotinaSimulador {
    static Sorteador sorteador = new PTrintaPorCento();

    public static void executar(GerenciadorMemoria gm, AlgoritmoSubstituicao as, int numInstrucoes){
        int instrucao;

        System.out.println(gm.toStringSPaginas());
        System.out.println(gm.toStringSMolduras());

        for(int i = 0; i < numInstrucoes; i++){
            //requisição de instrução de memoria
            //  escolha da instrução - aleatoriamente
            instrucao = GeradorAleatorio.gerarInt(1, 100);

            //  escolha da operação - probabiliticamente, uma alguma das escolhas, acesos ou modificação
            try{
                if(sorteador.sortear())
                    gm.modificar(instrucao, 1); // valor da modificação - apenas incrementa o dado
                else
                    gm.acessar(instrucao);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }

            gm.notificarAS();
        }

        System.out.println(gm.toStringSPaginas());
        System.out.println(gm.toStringSMolduras());
    }
}
