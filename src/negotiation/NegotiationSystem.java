package negotiation;

import Object.Consumables.OBJ_Potion_Health;
import Object.Consumables.OBJ_Potion_Mana;
import Object.Equipables.OBJ_WEAPON_Piercing;
import Object.Equipables.OBJ_WEAPON_Slash;
import main.BattleSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the negotiation system in the game.
 */
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

    /**
     * Constructs a new NegotiationSystem.
     *
     * @param battleSystem The BattleSystem associated with the negotiation.
     */
    public NegotiationSystem(BattleSystem battleSystem) {
        negotiationManager = new NegotiationManager();
        preguntas = negotiationManager.getPreguntas();
        preguntasRealizadas = new ArrayList<>();
        this.battleSystem = battleSystem;

    }

    /**
     * Starts the negotiation process.
     */
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
        }
    }

    /**
     * Randomly gives an item for the player's inventory.
     */
    public void randomizeItems(){
        //Numero Random del 1 al 3
        int random = (int) (Math.random() * 3) + 1;
        switch (random) {
            case 1 -> {
                OBJ_Potion_Health pocion = new OBJ_Potion_Health(battleSystem.gp);
                battleSystem.party.Leader.inventory.add(pocion);
                System.out.println("Has recibido una Pocion de Vida");
            }
            case 2 -> {
                OBJ_Potion_Mana pocion = new OBJ_Potion_Mana(battleSystem.gp);
                battleSystem.party.Leader.inventory.add(pocion);
                System.out.println("Has recibido una Pocion de Mana");
            }
            case 3 -> {
                OBJ_WEAPON_Piercing pierceWeapon = new OBJ_WEAPON_Piercing(battleSystem.gp);
                battleSystem.party.Leader.inventory.add(pierceWeapon);
                System.out.println("Has recibido un arma Perforante");
            }
        }
    }

    /**
     * Randomly selects a question from the available questions.
     *
     * @return The randomly selected question.
     */
    public Pregunta randomizeQuestions() {
        //Random Number from 0 to preguntas.size() - 1
        int random = (int) (Math.random() * preguntas.size());
        return preguntas.get(random);
    }

    /**
     * Initiates a money request.
     */
    public void requestMoney() {
        moneyRequested = (int) (Math.random() * 50) + 1;
    }

    /**
     * Generates a text describing the opponent's money request.
     *
     * @return The money request text.
     */
    public String requestMoneyText() {
        return "El oponente te está pidiendo " + moneyRequested + " monedas.";
    }

    /**
     * Processes the player's response to a money request.
     *
     * @param selector The player's response (0 for accept, 1 for reject).
     */
    public void processMoneyRequest(int selector) {
        boolean moneyPayed = false;

        if (selector == 0) {
            if (battleSystem.party.Leader.stats.money >= moneyRequested) {
                battleSystem.party.Leader.subtractMoney(moneyRequested);
                System.out.println("Has pagado " + moneyRequested + " monedas.");
                moneyPayed = true;
            } else {
                System.out.println("No tienes suficiente dinero");
            }
            moneyRequest = false;
        } else if (selector == 1) {
            System.out.println("Has rechazado la solicitud");
            moneyRequest = false;
        }
        if (moneyPayed) {
            happyMeter += 5;
            System.out.println("HappyMeter: " + happyMeter);
        } else {
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

    /**
     * Initiates a request for an item from the player.
     */
    public void requestItem() {
        randomizeItems();
    }


    /**
     * Updates the negotiation meters based on a random event.
     */
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

    /**
     * Handles options when the player wins the negotiation.
     *
     * @param selector The selected option.
     */
    public void happyMetterOptions(int selector) {
        //Opciones que tienes al Ganar la Negociación
        switch (selector) {
            case 0 ->
                // 1. Añadir el monstruo a party.
                    battleSystem.party.addMonsterToParty(battleSystem.monster.name);
            case 1 ->
                // 2. Obtener un item. WIP
                    randomizeItems();
            case 2 -> {
                // 3. Obtener dinero.
                int random = (int) (Math.random() * (50) * battleSystem.monster.stats.level) + 10;
                battleSystem.party.Leader.addMoney(random);
            }
        }
        selectingReward = false;
    }

    /**
     * Gets the number of available options in the negotiation.
     *
     * @return The number of options.
     */
    public int getNumOpciones() {
        return numOpciones;
    }


}
