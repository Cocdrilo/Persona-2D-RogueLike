package negotiation;

import main.BattleSystem;
import monster.shadowStandar;

import java.util.ArrayList;
import java.util.List;

public class NegotiationSystem {
    private NegotiationManager negotiationManager;

    public BattleSystem battleSystem;
    List<Pregunta> preguntas;
    List<Pregunta> preguntasRealizadas;

    private int numOpciones = 0;
    public int angryMeter = 0;
    public int happyMeter = 0;
    public boolean endNegotiation = false;
    public boolean selectingReward = false;

    public NegotiationSystem(BattleSystem battleSystem) {
        negotiationManager = new NegotiationManager();
        preguntas = negotiationManager.getPreguntas();
        preguntasRealizadas = new ArrayList<>();
        this.battleSystem = battleSystem;

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
            angryMeter += 5;
            System.out.println("AngryMeter: " + angryMeter);
        } else if (randomValue == 2) {
            happyMeter += 5;
            System.out.println("HappyMeter: " + happyMeter);
        }
        if(happyMeter >= 20){
            System.out.println("Has ganado la negociación");
            selectingReward = true;
        }
        if(angryMeter >= 20){
            System.out.println("Has perdido la negociación");
            endNegotiation = true;
        }
    }

    public void happyMetterOptions(int selector) {
        System.out.println("Selector de recompensa: " + selector);
        //Opciones que tienes al Ganar la Negociación
        switch (selector) {
            case 0 ->
                // 1. Añadir el monstruo a party.
                    battleSystem.party.addMonsterToParty(battleSystem.monster.name);
            case 1 ->
                // 2. Obtener un item. WIP
                    System.out.println("1. Obtener un item");
            case 2 -> {
                // 3. Obtener dinero.
                int random = (int) (Math.random() * (50) * battleSystem.monster.stats.level) + 10;
                battleSystem.party.Leader.addMoney(random);
            }
        }
        selectingReward= false;
    }

    public int getNumOpciones() {
        System.out.println("NumOpciones: " + numOpciones);
        return numOpciones;
    }



}
