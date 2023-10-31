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
    public boolean moneyRequest = false;

    public NegotiationSystem(BattleSystem battleSystem) {
        negotiationManager = new NegotiationManager();
        preguntas = negotiationManager.getPreguntas();
        preguntasRealizadas = new ArrayList<>();
        this.battleSystem = battleSystem;

    }

    public void startNegotiation() {
        numOpciones = 0;

        // Decidir aleatoriamente si se hará una pregunta, se pedirá dinero o se pedirá un objeto.
        int randomAction = (int) (Math.random() * 3);

        if (randomAction == 0) {
            Pregunta pregunta = randomizeQuestions();
            System.out.println(pregunta.getTexto());
            for (Opcion opcion : pregunta.getOpciones()) {
                System.out.println(opcion.getId() + ". " + opcion.getTexto());
                numOpciones++;
            }
            preguntasRealizadas.add(pregunta);
            preguntas.remove(pregunta);
        } else if (randomAction == 1) {
            requestMoneyText();
            moneyRequest = true;
        } else if (randomAction == 2) {
            requestItem();
        }
    }

    private Pregunta randomizeQuestions() {
        //Random Number from 0 to preguntas.size() - 1
        int random = (int) (Math.random() * preguntas.size());
        return preguntas.get(random);
    }

    public void requestMoneyText() {
        int amountToRequest = (int) (Math.random() * 50) + 1; // Genera un número aleatorio entre 1 y 50
        System.out.println("El oponente te está pidiendo " + amountToRequest + " monedas.");
    }

    public void processMoneyRequest(int selector) {
        int amountToRequest = (int) (Math.random() * 50) + 1; // Genera un número aleatorio entre 1 y 50

        if (selector == 0) {
            if (battleSystem.party.Leader.stats.money >= amountToRequest) {
                battleSystem.party.Leader.subtractMoney(amountToRequest);
                System.out.println("Has pagado " + amountToRequest + " monedas.");
                happyMeter += 5;
            } else {
                System.out.println("No tienes suficiente dinero para cumplir la solicitud.");
                // Puedes manejar lo que ocurre si el jugador no tiene suficiente dinero.
                angryMeter += 5;
            }
        } else if (selector == 1) {
            System.out.println("Has rechazado la solicitud");
            angryMeter += 5;
        }
    }


    public void requestItem() {
        // Aquí puedes implementar la lógica para pedir un objeto al jugador.
        // Por ejemplo, puedes seleccionar un objeto aleatorio de la lista de objetos del jugador.
        // Si el jugador tiene objetos disponibles, puedes agregar la lógica para darle un objeto al oponente.
        // Asegúrate de que haya un mecanismo para verificar si el jugador tiene objetos disponibles.
        // Puedes considerar la creación de una lista de objetos en la clase `Party` para esto.
        // battleSystem.party.Leader.addItem(item); // Agregar un objeto al oponente.
        System.out.println("El oponente está pidiendo un objeto.");
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
        if (happyMeter >= 20) {
            System.out.println("Has ganado la negociación");
            selectingReward = true;
        }
        if (angryMeter >= 20) {
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
        selectingReward = false;
    }

    public int getNumOpciones() {
        System.out.println("NumOpciones: " + numOpciones);
        return numOpciones;
    }


}
