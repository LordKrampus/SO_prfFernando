import Componente.Filosofo;
import Componente.Mesa;

public class Programa {

    public static void main(String[] args){
        int qtd = 5;

        Filosofo[] filosofos = new Filosofo[qtd];

        Mesa mesa = new Mesa(qtd);

        int i;
        for(i = 0; i < 5; i++){
            filosofos[i] = new Filosofo(i, "Filosofo " + i);

            try {
                filosofos[i].ArranjarLugar(mesa);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }

            System.out.println(mesa.toString());
        }

        /*
        //Filosofos conseguem pensar!
        filosofos[0].pensar();
        filosofos[1].pensar();
        filosofos[2].pensar();
        filosofos[3].pensar();
        filosofos[4].pensar();
        //*/

        /*
        Notificador ntf = Notificador.getInstancia();
        Cronometro cnm1 = new Cronometro(ntf, filosofos[0].getId());
        Cronometro cnm2 = new Cronometro(ntf, filosofos[1].getId());

        cnm1.setTempo(5);
        cnm2.setTempo(6);
        //cnm.correrTempo();
        cnm1.start();
        //cnm2.start();
        */
        /*
        Notificador ntf = Notificador.getInstancia();
        Cronometro cnm = new Cronometro(ntf, filosofos[0].getId());

        cnm.setTempo(5);
        cnm.correrTempo();
        */

        //System.out.println(Math.ceil(4.1));

        RotinaFilosofo rf[] = new RotinaFilosofo[qtd];
        for(i = 0; i < 5; i++){
            try {
                rf[i] = new RotinaFilosofo(filosofos[i]);
            }catch(Exception e){
                System.out.println(e.getMessage() + "(start())");
            }
        }

        rf[0].start();
        rf[1].start();
        rf[2].start();
        rf[3].start();
        rf[4].start();
    }

}
