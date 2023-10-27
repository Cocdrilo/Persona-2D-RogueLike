package negotiation;

import monster.shadowStandar;

import java.util.ArrayList;
import java.util.List;

public class NegotiationSystem {
    private NegotiationManager negotiationManager;
    public shadowStandar monster;
    List<Pregunta> preguntas;
    List<Pregunta> preguntasRealizadas;

    private int numOpciones = 0;
    public int angryMeter = 0;
    public int happyMeter = 0;

    public NegotiationSystem(shadowStandar monster) {
        negotiationManager = new NegotiationManager();
        preguntas = negotiationManager.getPreguntas();
        preguntasRealizadas = new ArrayList<>();
        this.monster = monster;
    }

    public void startNegotiation() {
        numOpciones = 0;
        Pregunta pregunta = randomizeQuestions();
        System.out.println(pregunta.getTexto());
        for (Opcion opcion : pregunta.getOpciones()) {
            System.out.println(opcion.getId() + ". " + opcion.getTexto());
            numOpciones++;
        }
        preguntasRealizadas.add(pregunta); // Añade la pregunta a la lista de preguntas realizadas
        preguntas.remove(pregunta); // Elimina la pregunta de la lista de preguntas disponibles

    }

    private Pregunta randomizeQuestions() {
        //Random Number from 0 to preguntas.size() - 1
        int random = (int) (Math.random() * preguntas.size());
        return preguntas.get(random);
    }

    public void updateMeter() {

        int randomValue = (int) (Math.random() * 2) + 1; // Genera un número aleatorio entre 1 y 2
        if (randomValue == 1) {
            angryMeter += 20;
            System.out.println("AngryMeter: " + angryMeter);
        } else if (randomValue == 2) {
            happyMeter += 0;
            System.out.println("HappyMeter: " + happyMeter);
        }
    }

    public int getNumOpciones() {
        System.out.println("NumOpciones: " + numOpciones);
        return numOpciones;
    }



}
