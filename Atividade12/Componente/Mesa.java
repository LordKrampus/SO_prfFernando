package Componente;

import EstruturaDados.Fila.FilaCircular;
import EstruturaDados.elemento.Elemento;
import Interface.Notificavel;

import java.util.ArrayList;

public class Mesa extends FilaCircular {
    int[] disponibilidade;

    public Mesa(int maximo){
        super(maximo);

        this.disponibilidade = new int[maximo];
    }

    public int buscarLugar(boolean bloquear) throws Exception{
        for(int i = 0; i < super.maximo; i++){
            if(this.disponibilidade[i] == 0) {

                if(bloquear)
                    this.disponibilidade[i] = 1;

                return i;
            }
        }

        throw new Exception(".exception - Nenhum Lugar disponível.");
    }

    public Cadeira ocuparLugar(Filosofo filosofo) throws Exception {
        Cadeira novaCadeira = new Cadeira(this.buscarLugar(true), this);
        novaCadeira.setFilosofo(filosofo);

        return novaCadeira;
    }

    private Lugar prepararLugar(Cadeira cadeira, Lugar proximo, Lugar anterior){
        int idCadeira;
        Garfo novoGarfo;
        Prato novoPrato;

        idCadeira = cadeira.getId();
        novoGarfo = new Garfo(idCadeira);
        novoPrato = new Prato(idCadeira);

        return new Lugar(cadeira, novoGarfo, novoPrato, proximo, anterior);
    }

    private void configGarfoE(Lugar lugar, Garfo garfoE){
        lugar.setGarfoE(garfoE);
    }

    @Override
    public void add(Object valor) throws StackOverflowError {
        try{
            this.add((Filosofo) valor);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void add(Filosofo filosofo) throws StackOverflowError, Exception {
        if(super.estaCheia()) throw new StackOverflowError(".error - A estrutura está cheia.");

        //preparar cadeira
        Cadeira cadeira = ocuparLugar(filosofo);

        //arranjar na mesa
        Lugar topBefore = (Lugar) super.top;

        Lugar lugarNovo = prepararLugar(cadeira, topBefore, null);
        this.top = lugarNovo;

        if(this.first == null) {
            this.first = this.top;
            this.iterador = this.first;
        }

        this.top.setAnterior(this.first);
        this.first.setProximo(this.top);

        if(topBefore == null) {
            this.top.setProximo(this.first);
        }else{
            topBefore.setAnterior(this.top);
            this.top.setProximo(topBefore);
        }

        //garfo esequerdo do lugar adicionado (configuração)
        this.configGarfoE((Lugar) super.top, topBefore.getGarfoD());

        //garfo esquerdo do primeiro indice de cadeira (first) na mesa (atualização)
        this.configGarfoE((Lugar) super.first, ((Lugar) this.top).getGarfoD());

        super.tamanho++;
    }

    public boolean resolverPrioridade(Cadeira cadeira) throws Exception{
        Lugar iterador = (Lugar)this.buscar(cadeira);

        Lugar atual = iterador;
        Lugar anterior = (Lugar)iterador.getProximo();

        if(atual.getPrioridade()) {
            //anterior esta comendo?
            if (((Cadeira) anterior.getValor()).getFilosofo().getEstado().equals("comendo")) {
                atual.getGarfoD().setEstado(true); //adio a vontade, ainda tem prioridade
            } else {
                //ganha vez
                atual.getGarfoE().setEstado(true);
                atual.setPrioridade(false);
                anterior.setPrioridade(true);

                return true;
            }
        } else {
            // não tem prioridade, deixa passar dessa vez, mas numa proxima ocasiam,
            //  tem maior chance de ser o da vez, desde que o anterior não esteja comendo.
            atual.getGarfoD().setEstado(true);
            atual.setPrioridade(true);
        }

        return false;
    }

    public Prato buscarPrato(Cadeira cadeira) throws Exception {
        /*
        if(this.estadoIterador != false)
            this.bloquearIterador();
        else throw new Exception(".exception - iterador bloqueiado");
         */
        Lugar iterador = (Lugar)super.buscar(cadeira);
        if(iterador == null)
            throw new Exception(".exception - nào foi possível acesar o prato (" + cadeira.getId() + ").");

        Prato saida = iterador.getPrato();

        //this.estadoIterador = true;
        return saida;
    }

    public Garfo buscarGarfoE(Cadeira cadeira) throws Exception {
        Lugar iterador = (Lugar)super.buscar(cadeira);

        if(iterador == null)
            throw new Exception(".exception - nào foi possível acesar o garfo esquerdo (" + cadeira.getId() + ").");

        Garfo saida = iterador.getGarfoE();
        return saida;
    }

    public Garfo buscarGarfoD(Cadeira cadeira) throws Exception {
        Lugar iterador = (Lugar)super.buscar(cadeira);

        if(iterador == null)
            throw new Exception(".exception - nào foi possível acesar o garfo direito (" + cadeira.getId() + ").");

        Garfo saida = iterador.getGarfoD();
        return saida;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();

        Elemento iterador = this.top;

        if(iterador == null)
            return "Null";

        Object valor;
        do{
            valor = iterador.getValor();

            sb.append(valor.toString() + " ");

            if(iterador == this.top){
                sb.append("\t(top)");
            }
            if(iterador == this.first){
                sb.append("\t(first)");
            }
            if(iterador == this.iterador){
                sb.append("\t(iterador)");
            }
            if(((Lugar) iterador).getPrioridade() == true){
                sb.append("(prioridade)");
            }

            sb.append("\n");
            iterador = iterador.getProximo();
        }while(iterador != this.top);

        return sb.toString();
    }

}
