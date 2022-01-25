import algoritmosSubstituicao.*;
import gerenciamentoMemoria.GerenciadorMemoria;
import utilidades.GeradorAleatorio;
import utilidades.GeradorTabelaMemoria;

/**
 * Crie um simulador para a execução dos algoritmos de substituição de páginas. Para isso teremos 1 matriz com 10 linhas e 5 colunas para modelar as molduras de páginas na memória RAM e outra matriz 100 linhas e 5 colunas que vai representar as páginas em disco. Os campos da matriz são:  Número de Página (N), Instrução (I), Dado (D), Bit de Acesso R, Bit de Modificação M.

- Crie inicialmente a matriz 100x5, onde ela deverá ser preenchida da seguinte forma:

--> A coluna N terá os números de 0 a 99, sequencialmente ( linha 0 -> N = 0, linha 1 -> N = 1, linha 2 -> N = 2...)

--> A coluna I terá os números de 1 a 100, sequencialmente ( linha 0 -> N = 1, linha 1 -> N = 2, linha 2 -> N = 3...)

--> A coluna D terá números de 1 a 50, sorteados ale aleatoriamente. 

 --> A coluna R = 0.

--> A coluna M = 0.

A matriz 10x5 possui os mesmos campos. A matriz 10x5 será preenchida da seguinte maneira:

--> Para cada linha da matriz 10x5, Sorteie um número de 0 a 99. Copie dos dados para a linha da matriz 10x5 a partir da matriz 100x5, usando como índice para a linha o número que foi sorteado. Ou seja, serão sorteadas 10 linhas da matriz 100x5 e copiadas para a matriz 10x5.

Posteriormente para a execução do simulador,  será sorteado um número de 1 a 100, referente a instrução (campo I) que está sendo requisitada para a execução na CPU. Será feito uma pesquisa no campo I da matriz 10x5 para verificar se o valor da instrução está carregado na memória RAM. 

Caso esteja, duas operações serão realizadas:

1) O bit de acesso R vai receber o valor 1.

2) A página terá 30% de chance de sofrer uma modificação. Ou seja, caso a probabilidade seja atingida, duas ações serão realizadas:

2.1) O campo Dado (D) será atualizado da seguinte maneira: D = D + 1;

2.2) O campo Modificado será atualizado: M = 1;

Caso o número de instrução sorteado não esteja presente na matriz 10x5, deverá ser utilizado um algoritmo de substituição de página para realizar a substituição.

Obs1.: O simulador deverá executar 500 instruções para cada algoritmo de substituição de página.

Obs2.: Os algoritmos de substituição de páginas para serem implementados são; NRU, FIFO, FIFO-SC e RELÓGIO

Obs3.: No início da execução deve ser impresso as matriz 10x5 e a matriz 100x5, e ao final das 500 instruções deve ser impresso novamente a matriz 10x5 e a matriz 100x5.

Obs4.: Faça um vídeo para cada algoritmo de 5 a 10 minutos explicando o algoritmo, o código e os resultados encontrados.


 */
public class Simulador {

    public static void main(String[] args) {
        //prepara configuração de simulação predefinida do sistema de memoria
        int[][] paginas = GeradorTabelaMemoria.gerarTabelaPaginas(100);
        int[][] molduras = GeradorTabelaMemoria.gerarTabelaMolduras(10, paginas);
        int numInstrucoes = 500;

        System.out.println("***Algoritmo de Substituição: FIFO!***");

        //inicializa gerenciador de memoria configurado
        AlgoritmoSubstituicao as = null;
        GerenciadorMemoria gm = null;
        try {
            as = new AS_FIFO();
            gm = new GerenciadorMemoria(paginas, molduras, as);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return;
        }

        RotinaSimulador.executar(gm, as, numInstrucoes);


        System.out.println("***Algoritmo de Substituição: FIFO_SC!***");

        //inicializa gerenciador de memoria configurado
        as = null;
        gm = null;
        try {
            as = new AS_FIFO_SC();
            gm = new GerenciadorMemoria(paginas, molduras, as);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return;
        }

        RotinaSimulador.executar(gm, as, numInstrucoes);


        System.out.println("***Algoritmo de Substituição: NRU!***");

        //inicializa gerenciador de memoria configurado
        as = null;
        gm = null;
        try {
            as = new AS_NRU();
            gm = new GerenciadorMemoria(paginas, molduras, as);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return;
        }

        RotinaSimulador.executar(gm, as, numInstrucoes);


        System.out.println("***Algoritmo de Substituição: Relogio!***");

        //inicializa gerenciador de memoria configurado
        as = null;
        gm = null;
        try {
            as = new AS_Relogio();
            gm = new GerenciadorMemoria(paginas, molduras, as);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return;
        }

        RotinaSimulador.executar(gm, as, numInstrucoes);
    }
}