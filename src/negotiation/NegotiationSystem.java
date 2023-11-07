package negotiation;

import main.BattleSystem;

import java.util.ArrayList;
import java.util.List;

public class NegotiationSystem {
    private NegotiationManager negotiationManager;

    public BattleSystem battleSystem;
    List<Pregunta> preguntas;
    List<Pregunta> preguntasRealizadas;
    public Pregunta preguntaActual;

    private int numOpciones = 0;
    public int angryMeter = 0;
    public int happyMeter = 0;
    public int moneyRequested = 0;
    public boolean endNegotiation = false;
    public boolean selectingReward = false;
    public boolean moneyRequest = false;
    public boolean canRequestMoney = true;

    public NegotiationSystem(BattleSystem battleSystem) {
        negotiationManager = new NegotiationManager();
        preguntas = negotiationManager.getPreguntas();
        preguntasRealizadas = new ArrayList<>();
        this.battleSystem = battleSystem;

    }

    public void startNegotiation() {
        numOpciones = 0;

        // Modificar el rango de randomAction para evitar 0 en el primer ciclo
        int randomAction = 0;  // Establecer un valor predeterminado diferente de 0 en el primer ciclo

        if (!preguntasRealizadas.isEmpty()) {
            randomAction = (int) (Math.random() * 2);
        }

        if (randomAction == 0 || !canRequestMoney) {
            Pregunta pregunta = randomizeQuestions();
            preguntaActual = pregunta;
            System.out.println(pregunta.getTexto());
            for (Opcion opcion : pregunta.getOpciones()) {
                System.out.println(opcion.getId() + ". " + opcion.getTexto());
                numOpciones++;
            }
            preguntasRealizadas.add(pregunta);
            preguntas.remove(pregunta);
        } else if (randomAction == 1 && canRequestMoney) {
            moneyRequest = true;
            numOpciones = 2;
            requestMoney();
            System.out.println(requestMoneyText());
            canRequestMoney = false;
        } else if (randomAction == 2) {
            // No funcional actualmente requestItem();
        }
    }

    public Pregunta randomizeQuestions() {
        //Random Number from 0 to preguntas.size() - 1
        int random = (int) (Math.random() * preguntas.size());
        return preguntas.get(random);
    }

    public void requestMoney() {
        moneyRequested= (int) (Math.random() * 50) + 1;
    }

    public String requestMoneyText() {
        return "El oponente te está pidiendo " + moneyRequested + " monedas.";
    }

    public void processMoneyRequest(int selector) {
        boolean moneyPayed = false;

        if (selector == 0) {
            if (battleSystem.party.Leader.stats.money >= moneyRequested) {
                battleSystem.party.Leader.subtractMoney(moneyRequested);
                System.out.println("Has pagado " + moneyRequested + " monedas.");
                moneyPayed = true;
            }
            else{
                System.out.println("No tienes suficiente dinero");
            }
            moneyRequest = false;
        } else if (selector == 1) {
            System.out.println("Has rechazado la solicitud");
            moneyRequest = false;
        }
        if (moneyPayed){
            happyMeter += 5;
            System.out.println("HappyMeter: " + happyMeter);
        }
        else{
            angryMeter += 5;
            System.out.println("AngryMeter: " + angryMeter);
        }

        if (happyMeter >= 20) {
            System.out.println("Has ganado la negociación");
            selectingReward = true;
        }
        if (angryMeter >= 20) {
            System.out.println("Has perdido la negociación");
            endNegotiation = true;
            battleSystem.nextTurn();
        }
        moneyRequest = false;
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
            battleSystem.nextTurn();
        }
    }

    public void happyMetterOptions(int selector) {
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
        return numOpciones;
    }


}
