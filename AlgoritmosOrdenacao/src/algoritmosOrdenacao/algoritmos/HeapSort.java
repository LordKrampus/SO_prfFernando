package algoritmosOrdenacao.algoritmos;

import algoritmosOrdenacao.recursosFuncionais.Util;

public class HeapSort extends SortAlgorithm{
    @Override
    public long realizar(Integer[] dados){
        this.criarHeap(dados);
        this.peneirar(dados);

        return super.iteracoes;
    }

    public void criarHeap(Integer[] dados){
        for(int i = (dados.length-1)/2; i >= 0; i--){
            ordenar(dados, i, dados.length-1);
        }
    }

    public void peneirar(Integer[] dados){
        for(int i = dados.length-1; i > 0; i--){
            this.ordenar(dados, 0, i);
            Util.swap(dados, 0, i);
            super.iteracoes++;
        }
    }

    public void ordenar(Integer[] dados, int indiceNoPai, int indiceUltimoElemento){
        int indiceRamo = indiceNoPai * 2 + 1;

        int valorNoPai;
        while(indiceRamo <= indiceUltimoElemento) {
            valorNoPai = dados[indiceNoPai];
            if(indiceRamo < indiceUltimoElemento)
                if(dados[indiceRamo] < dados[indiceRamo+1])
                    indiceRamo++; // o no pai será trocado pelo ramo com o maior valor (se valida a troca for)
            if (valorNoPai < dados[indiceRamo]) {
                Util.swap(dados, indiceNoPai, indiceRamo); // realização da troca
                super.iteracoes++;
                // reajustes dos indices
                indiceNoPai = indiceRamo;
                indiceRamo = indiceNoPai * 2 + 1;
            }else
                break;
        }
    }

    /*
    //https://www.youtube.com/watch?v=zSYOMJ1E52A&feature=youtu.be
    // (recurso subsidiado como base na elaboração principal)
    public void constroiHeap(Integer[] dados, int i, int f) {
        int aux = dados[i];
        int j = i * 2 + 1;
        while(j <= f){
            if(j < f){
                if(dados[j] < dados[j + 1])
                    j += 1;
            }
            if(aux < dados[j]){
                dados[i] = dados[j];
                i = j;
                j = 2 * i + 1;
            }else {
                j = f + 1;
            }
        }
        dados[i] = aux;
    }
    */
}