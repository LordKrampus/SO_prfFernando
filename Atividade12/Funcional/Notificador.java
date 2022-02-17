package Funcional;

import Interface.Notificavel;

import java.util.ArrayList;
import java.util.LinkedList;

public class Notificador {
    private ArrayList<Notificavel> notificaveis;

    private static Notificador instancia;

    private Notificador(){
        this.notificaveis = new ArrayList<Notificavel>();
    }

    public static Notificador getInstancia(){
        if(instancia == null)
            instancia = new Notificador();

        return instancia;
    }

    public void addNotificavel(Notificavel notificavel){
        this.notificaveis.add(notificavel);
    }

    public void removeNotificavel(Notificavel notificavel){
        this.notificaveis.remove(notificavel);
    }

    public void notificar(int id){
        //abstracao
        for(Notificavel notificavel: this.notificaveis){
            if(notificavel.getId() == id)
                notificavel.rotinaNotificacao();
        }
    }
}
