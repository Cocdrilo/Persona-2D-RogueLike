package negotiation;

import java.util.List;

public class NegotiationSystem {
    private NegotiationManager negotiationManager;
    List<Pregunta> preguntas;

    public NegotiationSystem() {
        negotiationManager = new NegotiationManager();
        preguntas = negotiationManager.getPreguntas();
    }

    public void startNegotiation() {
        Pregunta pregunta = randomizeQuestions();
        System.out.println(pregunta.getTexto());
        for (Opcion opcion : pregunta.getOpciones()) {
            System.out.println(opcion.getId() + ". " + opcion.getTexto());
        }
    }

    private Pregunta randomizeQuestions() {
        //Random Number from 0 to preguntas.size() - 1
        int random = (int) (Math.random() * preguntas.size());
        return preguntas.get(random);
    }





}
