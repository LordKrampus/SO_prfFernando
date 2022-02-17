import Componente.Cadeira;
import Componente.Filosofo;
import Componente.Mesa;
import Componente.Prato;
import Funcional.Sorteador;

public class RotinaFilosofo extends Thread {
    private Filosofo filosofo;
    private Mesa mesa;
    private Prato prato;
    private Cadeira cadeira;
    private int iteracao;

    private Sorteador sorteador;

    public RotinaFilosofo(Filosofo filosofo) throws Exception{
        this.filosofo = filosofo;
        this.iteracao = 0;

        this.cadeira = this.filosofo.getCadeira();
        this.mesa = this.cadeira.getMesa();
        this.prato = this.mesa.buscarPrato(this.cadeira);

        //probabilidade de ocorrencias
        this.sorteador = new Sorteador();
    }

    public int getIteracao(){
        return this.iteracao;
    }

    @Override
    public void run(){
        //executa rotina do filosofo:
        // vontade de comer(provavel - tem base a fome), se não:
        //      -> tentar comer, se não:
        //              ->reduzir fome (ocupar recursos, consumilos e depois liberar recursos)
        // : vontade de pensar -> fome almenta.

        while(!this.prato.vazio()) {
            if(!this.rotinaComer()) {
                this.rotinaPensar();
            }
            System.out.println(System.currentTimeMillis()/1000);
            System.out.println(this + "\n" + this.mesa.toString());

            this.iteracao++;
        }
    }

    private boolean rotinaComer(){
        this.sorteador.setProbabilidade(this.filosofo.getNivelFome());
        //this.sorteador.setProbabilidade(100);

        if(this.sorteador.sortear()) {
            try {
                this.filosofo.comer(); // tentar comer

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }else return false;

        return true;
    }

    private void rotinaPensar(){
        this.filosofo.pensar(); // se não, filoso fica pensando
    }
}
