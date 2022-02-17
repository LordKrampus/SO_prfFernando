package Interface;

/*
Objetos de classes Notificavel podem acionar rotinaNotificacao (rotina de notificação) a qualquer momento.
 */
public interface Notificavel {
    public void rotinaNotificacao();
    public int getId();
}
