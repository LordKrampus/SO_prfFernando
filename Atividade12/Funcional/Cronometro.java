package Funcional;

/*
    Realiza contexto de contagem de cronometro.
        Não ativa linha de processamento independente, precisa ser verificado constantemente. (depreciado)
        Ativa linha de processamento independente Thread. -> precisa notificar alguem.
 */
public class Cronometro extends Thread{
    long tempo;
    long tempoPercorrido;

    long horaInicio;
    long horaFinal;

    Notificador notificador;
    int refId;

    public Cronometro(int id){
        this.tempo = 0;
        this.tempoPercorrido = 0;

        this.horaInicio = 0;
        this.horaFinal = 0;

        this.notificador = Notificador.getInstancia();
        this.refId = id;
    }

    @Override
    public void run(){
        this.correrTempo();
    }

    public void setTempo(long tempo){
        this.tempo = tempo;
    }

    public void resetarCronometro(){
        this.horaInicio = 0;
        this.horaFinal = 0;
        this.tempoPercorrido = 0;
    }

    private void prepararTempo(){
        this.horaInicio = this.getTempoAtual();
        this.horaFinal = this.getTempoAtual() + this.tempo;
    }

    private void sincronizarTempoMomento(long tempoPercorrido){
        tempoPercorrido = this.getTempoPercorrido();

        if(this.tempoPercorrido != tempoPercorrido) {
            this.marcarTempoPercorrido(tempoPercorrido);

            //System.out.println(this.toString() + " - " + this.getTempoPercorrido());
        }
    }

    private void alertarCronometro(){
        this.notificador.notificar(this.refId);
    }

    public void correrTempo(){
        long tempoPercorrido = 0;

        this.prepararTempo();

        while(!this.atingiuCronometro()){
            this.sincronizarTempoMomento(tempoPercorrido);
        }

        this.sincronizarTempoMomento(tempoPercorrido);

        this.alertarCronometro();
    }

    public void marcarTempoPercorrido(long tempoPercorrido){
        this.tempoPercorrido = tempoPercorrido;
    }

    public void marcarTempoPercorrido(){
        this.tempoPercorrido = this.getTempoPercorrido();
    }

    public long getTempoAtual(){
        return System.currentTimeMillis()/1000;
        //return System.currentTimeMillis();
    }

    public long getTempoPercorrido(){
        if(this.tempo == 0) return 0;
        return  this.getTempoAtual() - this.horaInicio;
    }

    public long getCronometro(){
        return this.horaFinal - this.getTempoAtual();
    }

    public boolean atingiuCronometro(){
        return this.getCronometro() <= 0;
    }
}
