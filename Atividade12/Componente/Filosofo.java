package Componente;

import Funcional.*;
import Interface.Notificavel;

public class Filosofo implements Notificavel {
    private int id;
    private String nome;
    private Cadeira cadeira;
    private String estado;

    private Cronometro cerebro;
    private int nivelFome;

    private static Notificador notificador;

    public Filosofo(int id, String nome){
        this.id = id;
        this.nome = nome;
        this.estado = "iniciando";
        this.nivelFome = 0;

        this.cadeira = null;

        //adiciona classe para receber notificação
        notificador = Notificador.getInstancia();
        notificador.addNotificavel(this);

        //prepara um cronometro
        this.cerebro = new Cronometro(this.id);
    }

    @Override
    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public Cadeira getCadeira(){
        //return this.cadeira.getId();
        return this.cadeira;
    }

    public int getNivelFome(){
        return this.nivelFome;
    }

    public String getEstado(){
        return this.estado;
    }

    //Sincroniza o filosofo a uma cadeira
    protected void setCadeira(Cadeira cadeira){
        if(this.cadeira != null)
            this.cadeira.setFilosofo(null);

        this.cadeira = cadeira;
    }


    //Sincroniza uma cadeira a uma mesa para um filosofo
    public int ArranjarLugar(Mesa mesa) throws Exception{
        mesa.add(this);

        return this.cadeira.getId();
    }

    /*
    //Sincroniza uma cadeira a uma mesa para um filosofo
    public int setCadeira(int id, Mesa mesa) throws Exception{
        this.cadeira = mesa.ocuparCadeira(id, this);

        return cadeira.getId();
    }
     */

    public void setEstado(String estado){
        this.estado = estado;
    }

    private void sincFome(int fator){
        //uma relção do tempo comendo transforma em fome.
        // Ex.: Um nível de 30 de fome, condiciona a possíbilidade
        // de 30% de o filosofo ter vontade de comer.
        long tempo = fator; //tempo pensando

        if(this.nivelFome + tempo > 100)
            this.nivelFome = 100;
        else
            this.nivelFome += tempo;
    }

    public void comer()throws Exception{
        // a mesa permite preparar a execução de um serviço
        Mesa mesa = cadeira.getMesa();

        Servico servico;
        try {
            servico = new Servico(this.cadeira, mesa);
        }catch(Exception e){
            throw e;
        }

        //simula tempo comendo
        this.estado = "comendo";

        this.cerebro.setTempo(3);
        //this.cerebro.setTempo(100);
        this.cerebro.correrTempo();

        System.out.println(servico.toString());

        //sincroniza resultado sobre o prato
        servico.consumir();

        this.nivelFome = 0;
    }

    public void pensar(){
        //prepara e ativa o cronometro
        int tempo = GeradorAleatorio.gerarInt(1, 5); // 1 à 5 segundos

        this.cerebro.setTempo(tempo);
        //this.cerebro.setTempo(100);

        //pensar
        //this.cerebro.start();
        this.estado = "pensando";

        this.cerebro.correrTempo();
    }

    @Override
    public void rotinaNotificacao(){
        //System.out.println("Filosofo (" + this.getNome() + ") recebe notificação da thread.");

        if(this.estado.equals("pensando")) {
            this.sincFome((int) this.cerebro.getTempoPercorrido());
            //this.estado = "esperando";
        }
        else if(this.estado.equals("comendo")) {
            //this.estado = "esperando";
        }
    }

    @Override
    public String toString() {
        Prato prato = null;

        try{
            prato = this.cadeira.getMesa().buscarPrato(this.cadeira);
        }catch(Exception e ){
            System.out.println(e);
        }

        return String.format("%s (%s) -> \tfome: %d%% \ttempo: %ds \tprato (%d): %s", this.nome, this.estado, this.nivelFome, this.cerebro.getTempoPercorrido(),
                prato.getId(), prato.toString());
    }
}


/*
public void comer()throws Exception{
        if(!sorteador.sortear()) return;

        // a mesa permite preparar a execução de um serviço
        Mesa mesa = cadeira.getMesa();

        try {
            Servico servico = new Servico(this.cadeira, mesa);
        }catch(Exception e){
            throw e;
        }

        //Preparar referencia dos garfos (pensar nos garfos para comer)
        //  deve conseguir pegar os dois garfos.
        servico.pegarGarfoE(mesa.buscarGarfoE(this.cadeira));
        servico.pegarGarfoD(mesa.buscarGarfoD(this.cadeira));

        //servico.ideitificarPrato(mesa.buscarPrato(idCadeira));

        try {
            if(!servico.vrfSituacao())
                throw new Exception(".exception - Situação não favorece o consumo do Prato.");

            this.estado = "comendo";

            //simula tempo comendo
            this.cerebro.setTempo(3);
            this.cerebro.correrTempo();

            servico.consumir();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        //mesa.getServico();
    }
 */