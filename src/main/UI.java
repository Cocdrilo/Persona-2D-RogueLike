package main;

import entity.Drawable;
import entity.Player;
import monster.shadowStandar;
import negotiation.Opcion;
import negotiation.Pregunta;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class represents the user interface (UI) for the game.
 * It includes methods for drawing various screens such as the title screen,
 * status screen, dialogue screen, and combat screen.
 */
public class UI implements Drawable {

    GamePanel gp;
    Graphics2D g2;
    Font Arcadia, p5Hatty;

    Font franklin;

    BufferedImage image;
    BufferedImage titleImage;
    BufferedImage pressTurnIcon1;
    BufferedImage pressTurnIcon2;
    BufferedImage backgroundCombat;

    //ArrayList de text para que sean Scrolling
    ArrayList<String> messageList = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    private Timer timer;

    private int selectedIndex = 0;

    public String currentDialogue = "";
    public String currentName = "";
    public int commandNum = 0;
    public int commandNum2;
    public int commandNum3 = 0;
    public int subState = 0;
    public boolean magicMenu = false;
    public boolean itemMenu = false;

    //VARIABLES PARA MANEJO DE INVENTARIO
    public int slotCol = 0;
    public int slotRow = 0;


    /**
     * Constructs a new UI instance.
     *
     * @param gp The GamePanel instance associated with this UI.
     */
    public UI(GamePanel gp) {

        this.gp = gp;

        //CUSTOM TEXTOS IMPORT
        try {
            InputStream is = getClass().getResourceAsStream("/font/Arcadia.ttf");
            Arcadia = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/p5hatty.ttf");
            p5Hatty = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/FranklinGothic.ttf");
            franklin = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        try {
            titleImage = ImageIO.read(getClass().getResourceAsStream("/TitleScreen/Dungeon.png"));
            pressTurnIcon1 = ImageIO.read(getClass().getResourceAsStream("/BattleImages/PressTurnIcon.png"));
            pressTurnIcon2 = ImageIO.read(getClass().getResourceAsStream("/BattleImages/PressTurnIconHalfTurn.png"));
            backgroundCombat = ImageIO.read(getClass().getResourceAsStream("/BattleImages/Combat_Background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int duracionTextoEnMilisegundos = 4000; // 4 segundos
        timer = new Timer(duracionTextoEnMilisegundos, e -> draw(g2));

    }

    /**
     * Loads an image from the specified file path.
     *
     * @param filePath The file path of the image to be loaded.
     * @return The loaded BufferedImage.
     */
    public BufferedImage loadImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Adds a message to the message list for scrolling display.
     *
     * @param text The text message to be added.
     */
    public void addMessage(String text) {
        messageList.add(text);
        messageCounter.add(0);
    }

    /**
     * Calculates the X-coordinate for centering text on the screen.
     *
     * @param text The text for which the X-coordinate is calculated.
     * @return The X-coordinate for centering the text.
     */
    public int getXforCenterText(String text) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    /**
     * Calculates the X-coordinate for aligning text to the right.
     *
     * @param text  The text for which the X-coordinate is calculated.
     * @param tailX The tail X-coordinate to align the text to.
     * @return The X-coordinate for aligning the text to the right.
     */
    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }

    /**
     * Draws the pause screen with a "PAUSED" message.
     */
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F));
        String text = "PAUSED";
        int x = getXforCenterText(text);
        int y = (gp.screenHeight / 2) + 30;

        g2.drawString(text, x, y);

    }

    /**
     * Draws a sub-window with rounded corners.
     *
     * @param x      The X-coordinate of the sub-window.
     * @param y      The Y-coordinate of the sub-window.
     * @param width  The width of the sub-window.
     * @param height The height of the sub-window.
     */
    public void drawSubWindow(int x, int y, int width, int height) {

        Color rgb = new Color(0, 0, 0, 50);

        g2.setColor(rgb);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        rgb = new Color(255, 99, 71);
        g2.setColor(rgb);
        g2.fillRect(x, y, width, 5);
    }

    /**
     * Draws the dialogue screen with character names and text.
     */
    public void drawDialogueScreen() {
        //WINDOW
        int x = 0;
        int y = gp.tileSize * 9;
        int width = gp.screenWidth;
        int height = (int) (gp.tileSize * 3.5);

        drawSubWindow(x, y, width, height);

        //TEXT NAME
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;
        Color rgb = new Color(210, 255, 101);
        g2.setColor(rgb);
        g2.drawString(currentName, x, y);

        //TEXT
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        //x += gp.tileSize/2;
        y += (int) (gp.tileSize / 1.5);
        rgb = new Color(255, 255, 255);
        g2.setColor(rgb);
        if (currentDialogue != null) {
            for (String line : currentDialogue.split("\n")) {
                g2.drawString(line, x, y);
                y += 40;
            }
        }

    }

    /**
     * Draws the title screen with game title and menu options.
     */
    public void drawTitleScreen() {

        g2.drawImage(titleImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        g2.setFont(Arcadia);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        String text = "SMT ROGUELIKE";
        int x = getXforCenterText(text);
        int y = gp.tileSize + 20;

        //COLOR SOMBRA
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);

        //COLOR PRINCIPAL
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        //Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "NEW GAME";
        x = getXforCenterText(text);
        y += gp.tileSize * 8;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString("->", x - gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenterText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString("->", x - gp.tileSize, y);
        }

        text = "QUIT GAME";
        x = getXforCenterText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString("->", x - gp.tileSize, y);
        }

    }

    /**
     * Draws the status screen showing player and party information.
     */
    public void drawStatusScreen() {

        //CREATE A FRAME
        final int frameX = gp.tileSize / 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(24F));

        int textX = frameX + gp.tileSize / 2;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 34;

        //NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("EXP", textX, textY);
        textY += lineHeight;
        g2.drawString("NEXT EXP", textX, textY);
        textY += lineHeight;
        g2.drawString("HP", textX, textY);
        textY += lineHeight;
        g2.drawString("SP", textX, textY);
        textY += lineHeight;
        g2.drawString("STR", textX, textY);
        textY += lineHeight;
        g2.drawString("MAG", textX, textY);
        textY += lineHeight;
        g2.drawString("AGI", textX, textY);
        textY += lineHeight;
        g2.drawString("VIT", textX, textY);
        textY += lineHeight;
        g2.drawString("MONEY", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("WEAPON", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("ARMOR", textX, textY);

        //VALUES
        int tailX = (frameX + frameWidth) - 30;
        //reseteamos la Y
        textY = frameY + gp.tileSize;
        String valor;

        valor = String.valueOf(gp.player.stats.level);
        textX = getXforAlignToRightText(valor, tailX);
        g2.drawString(valor, textX, textY);

        valor = String.valueOf(gp.player.stats.exp);
        textX = getXforAlignToRightText(valor, tailX);
        g2.drawString(valor, textX, textY + lineHeight);

        valor = String.valueOf(gp.player.stats.nextLevelExp);
        textX = getXforAlignToRightText(valor, tailX);
        g2.drawString(valor, textX, textY + lineHeight * 2);

        valor = String.valueOf(gp.player.stats.hp + "/" + gp.player.stats.maxHp);
        textX = getXforAlignToRightText(valor, tailX);
        g2.drawString(valor, textX, textY + lineHeight * 3);

        valor = String.valueOf(gp.player.stats.mp + "/" + gp.player.stats.maxMp);
        textX = getXforAlignToRightText(valor, tailX);
        g2.drawString(valor, textX, textY + lineHeight * 4);

        valor = String.valueOf(gp.player.stats.str);
        textX = getXforAlignToRightText(valor, tailX);
        g2.drawString(valor, textX, textY + lineHeight * 5);

        valor = String.valueOf(gp.player.stats.vit);
        textX = getXforAlignToRightText(valor, tailX);
        g2.drawString(valor, textX, textY + lineHeight * 6);

        valor = String.valueOf(gp.player.stats.agi);
        textX = getXforAlignToRightText(valor, tailX);
        g2.drawString(valor, textX, textY + lineHeight * 7);

        valor = String.valueOf(gp.player.stats.mag);
        textX = getXforAlignToRightText(valor, tailX);
        g2.drawString(valor, textX, textY + lineHeight * 8);

        valor = String.valueOf(gp.player.stats.money);
        textX = getXforAlignToRightText(valor, tailX);
        g2.drawString(valor, textX, textY + lineHeight * 9);

        g2.drawImage(gp.player.stats.weapon.walkDown1, tailX - gp.tileSize, (textY + lineHeight * 9) + 10, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.stats.armor.walkDown1, tailX - gp.tileSize, (textY + lineHeight * 9) + 20, null);

        //Posicion Inicial para dibujar marcos del equipo
        int memberX = frameX + frameWidth + 10; // Inicia al lado derecho del marco principal
        int memberY = frameY;

        for (int i = 0; i < gp.party.partyMembers.size(); i++) {
            drawSubWindow(memberX, memberY, (frameWidth / 2) + 30, frameHeight);
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(24F));

            // Dibuja la información del miembro del grupo de manera similar a la del jugador
            shadowStandar member = gp.party.partyMembers.get(i);

            // Alinea los valores con el marco del miembro del grupo
            // Utiliza las mismas coordenadas de textoY, textX y tailX para alinear los valores
            textY = memberY + gp.tileSize;
            textX = memberX + gp.tileSize / 2;
            tailX = (memberX + frameWidth) - 30;

            valor = String.valueOf(member.stats.level);
            g2.drawString("Level: " + valor, textX, textY);
            textY += lineHeight;

            valor = String.valueOf(member.stats.exp);
            g2.drawString("EXP: " + valor, textX, textY);
            textY += lineHeight;

            valor = String.valueOf(member.stats.nextLevelExp);
            g2.drawString("NEXT EXP: " + valor, textX, textY);
            textY += lineHeight;

            valor = String.valueOf(member.stats.hp + "/" + member.stats.maxHp);
            g2.drawString("HP: " + valor, textX, textY);
            textY += lineHeight;

            valor = String.valueOf(member.stats.mp + "/" + member.stats.maxMp);
            g2.drawString("MP: " + valor, textX, textY);
            textY += lineHeight;

            valor = String.valueOf(member.stats.str);
            g2.drawString("STR: " + valor, textX, textY);
            textY += lineHeight;

            valor = String.valueOf(member.stats.mag);
            g2.drawString("MAG: " + valor, textX, textY);
            textY += lineHeight;

            valor = String.valueOf(member.stats.agi);
            g2.drawString("AGI: " + valor, textX, textY);
            textY += lineHeight;

            valor = String.valueOf(member.stats.vit);
            g2.drawString("VIT: " + valor, textX, textY);
            textY += lineHeight;

            // Dibuja la imagen de combate de 64x64 debajo del atributo VIT
            image = member.getCombatImage();
            int imageX = memberX + (frameWidth - 64) / 12; // Centra la imagen en el marco
            int imageY = textY + 10; // Espacio para separar la imagen del texto
            g2.drawImage(image, imageX, imageY, 128, 128, null);


            // Avanza a la siguiente posición Y para dibujar el siguiente miembro del grupo
            memberX += gp.tileSize * 3.5;
        }

    }

    /**
     * Draws the combat screen with panels for monsters, turn order, and commands.
     *
     * @param BattleState The BattleSystem instance representing the current battle state.
     */
    public void drawCombatScreen(BattleSystem BattleState) {

        //Draw Background Screen with image
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.drawImage(backgroundCombat, 0, 0, gp.screenWidth, gp.screenHeight, null);

        g2.setFont(franklin);

        int x = (int) (gp.tileSize * 2.5);
        int y = gp.tileSize;
        int width = gp.screenWidth - gp.tileSize * 5;
        int height = gp.tileSize * 4;

        // Draw the monster panel
        drawMonsterPanel(x, y, width, height, BattleState);

        // Draw the turn order panel
        x = gp.screenWidth - gp.tileSize * 2;
        y = gp.tileSize * 5;
        width = gp.tileSize * 2;
        height = (int) (gp.tileSize * 6.5);
        //drawTurnOrderPanel(x, y, width, height);

        // Draw the command menu
        x = 5;
        y = gp.tileSize/2;
        width = (gp.tileSize * 3);
        height = gp.tileSize * 5;
        drawCommandMenu(x, y, width, height);

        // Draw the magic menu
        if (magicMenu) {
            x = x + width;
            int magicMenuY = y;
            int magicMenuWidth = gp.tileSize * 3;
            int magicMenuHeight = height;
            drawMagicMenu(x, magicMenuY, magicMenuWidth, magicMenuHeight, BattleState);
        }
        if (itemMenu) {
            x = x + width;
            int itemMenuY = y;
            int itemMenuWidth = gp.tileSize * 3;
            int itemMenuHeight = height;
            drawItemMenu(x, itemMenuY, itemMenuWidth, itemMenuHeight, BattleState);
        }

        // Draw the player and party panel
        x = (int)(gp.tileSize * 3.5);
        y = (int) (gp.tileSize * 7.5);
        width = gp.screenWidth - gp.tileSize * 5;
        height = gp.tileSize * 4;

        // Code for Resalting the one who is attacking
        selectedIndex = BattleState.currentPartyMemberIndex;
        drawPlayerAndPartyPanel(x, y, width, height, selectedIndex, BattleState);

        // Draw the turn icons
        drawTurnIcons(BattleState.pressTurn);
    }

    /**
     * Draws the item menu during combat, displaying available items for the player.
     *
     * @param x              The X-coordinate of the item menu.
     * @param itemMenuY      The Y-coordinate of the item menu.
     * @param itemMenuWidth  The width of the item menu.
     * @param itemMenuHeight The height of the item menu.
     * @param battleState    The BattleSystem instance representing the current battle state.
     */
    private void drawItemMenu(int x, int itemMenuY, int itemMenuWidth, int itemMenuHeight, BattleSystem battleState) {
        drawSubWindow(x, itemMenuY, itemMenuWidth, itemMenuHeight);
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(24F));

        String[] entityItemNames = battleState.party.Leader.printItems();

        int itemOptionY = itemMenuY + gp.tileSize;


        for (int i = 0; i < entityItemNames.length; i++) {
            String spellText = entityItemNames[i];
            g2.drawString(spellText, x + 15, itemOptionY + i * gp.tileSize);

            if (i == gp.ui.commandNum2) {
                g2.drawString("->", x+3, itemOptionY + i * gp.tileSize);
            }
        }


    }

    /**
     * Draws Attempt to escape failed on screen
     *
     */


    public void drawEscapeFailed(){
        g2.setColor(Color.red);
        g2.setFont(g2.getFont().deriveFont(44F));
        String text = "Attempt to escape failed";
        int x = getXforCenterText(text);
        int y = gp.tileSize * 2;

        g2.drawString(text, x, y);

        timer.restart();

    }

    /**
     * Draws the magic menu during combat, displaying available spells for the player.
     *
     * @param x           The X-coordinate of the magic menu.
     * @param y           The Y-coordinate of the magic menu.
     * @param width       The width of the magic menu.
     * @param height      The height of the magic menu.
     * @param BattleState The BattleSystem instance representing the current battle state.
     */
    private void drawMagicMenu(int x, int y, int width, int height, BattleSystem BattleState) {
        drawSubWindow(x, y, width, height);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(24F));

        String[] entitySpellNames = BattleState.partyMembers.get(BattleState.currentPartyMemberIndex).printSpells();

        int magicOptionY = y + gp.tileSize;

        for (int i = 0; i < entitySpellNames.length; i++) {
            String spellText = entitySpellNames[i];
            g2.drawString(spellText, x + 15, magicOptionY + i * gp.tileSize);

            if (i == gp.ui.commandNum2) {
                g2.drawString("->", x + 3, magicOptionY + i * gp.tileSize);
            }
        }
    }

    /**
     * Draws the panel for displaying information about the current monster in combat.
     *
     * @param x           The X-coordinate of the monster panel.
     * @param y           The Y-coordinate of the monster panel.
     * @param width       The width of the monster panel.
     * @param height      The height of the monster panel.
     * @param BattleState The BattleSystem instance representing the current battle state.
     */
    private void drawMonsterPanel(int x, int y, int width, int height, BattleSystem BattleState) {

        x = gp.tileSize * 6+30;
        y = (int) (gp.tileSize * 1.5);

        image = BattleState.monster.getCombatImage();
        g2.drawImage(image, x , y, 150, 150, null);

        int maxHealth = BattleState.monster.stats.maxHp;
        int currentHealth = BattleState.monster.stats.hp;
        int barX = x + 25;
        int barY = y + 150;

        String monsterHealthText = BattleState.monster.stats.hp + "/" + maxHealth;
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(24F));
        g2.drawString(monsterHealthText, barX, barY - 5);
    }

    /**
     * Draws the panel for displaying the turn order during combat.
     *
     * @param x      The X-coordinate of the turn order panel.
     * @param y      The Y-coordinate of the turn order panel.
     * @param width  The width of the turn order panel.
     * @param height The height of the turn order panel.
     */

    /*
    private void drawTurnOrderPanel(int x, int y, int width, int height) {
        drawSubWindow(x, y, width, height);
    }
    */

    /**
     * Draws the command menu during combat, displaying available commands for the player.
     *
     * @param x      The X-coordinate of the command menu.
     * @param y      The Y-coordinate of the command menu.
     * @param width  The width of the command menu.
     * @param height The height of the command menu.
     */
    private void drawCommandMenu(int x, int y, int width, int height) {
        drawSubWindow(x, y, width, height);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(24F));

        String[] commandOptions = {"Attack", "Magic", "Item", "Defend", "Escape", "Negotiate"};

        for (int i = 0; i < commandOptions.length; i++) {
            g2.drawString(commandOptions[i], x + gp.tileSize / 2, y + gp.tileSize / 2 + i * 40);
            if (commandNum == i) {
                g2.drawString("->", x - 2, y + gp.tileSize / 2 + i * 40);
            }
        }
    }


    /**
     * Draws the player and party panel during combat, displaying player and party information.
     *
     * @param x             The X-coordinate of the player and party panel.
     * @param y             The Y-coordinate of the player and party panel.
     * @param width         The width of the player and party panel.
     * @param height        The height of the player and party panel.
     * @param selectedIndex The index of the selected party member.
     * @param BattleState   The BattleSystem instance representing the current battle state.
     */
    private void drawPlayerAndPartyPanel(int x, int y, int width, int height, int selectedIndex, BattleSystem BattleState) {

        drawPlayer(x, y, selectedIndex);

        int maxHealth2 = gp.player.stats.maxHp;
        int currentHealth2 = gp.player.stats.hp;
        int maxMana = gp.player.stats.maxMp;
        int currentMana = gp.player.stats.mp;

        String playerHealthText = currentHealth2 + "/" + maxHealth2;
        String playerManaText = currentMana + "/" + maxMana;
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(24F));
        g2.drawString(playerHealthText, x + 5, y + 80);
        g2.drawString(playerManaText, x + 5, y + 97);
    }

    /**
     * Draws the player character during combat, highlighting the selected party member.
     *
     * @param x             The X-coordinate of the player.
     * @param y             The Y-coordinate of the player.
     * @param selectedIndex The index of the selected party member.
     */
    private void drawPlayer(int x, int y, int selectedIndex) {
        image = gp.player.standFront;
        g2.drawImage(image, x + 10, y, 64, 64, null);
        if (selectedIndex == 0) {
            g2.setColor(Color.RED);
            g2.drawRoundRect(x + 10, y, 65, 65, 20, 20);
        }

        for (int i = 1; i < gp.battleSystem.partyMembers.size(); i++) {

            shadowStandar monstruo = (shadowStandar) gp.battleSystem.partyMembers.get(i);
            image = monstruo.getCombatImage();
            g2.drawImage(image, x + 128 + ((i - 1) * 120), y, 64, 64, null);

            String partyHealthText = gp.battleSystem.partyMembers.get(i).stats.hp + "/" + gp.battleSystem.partyMembers.get(i).stats.maxHp;
            String partyManaText = gp.battleSystem.partyMembers.get(i).stats.mp + "/" + gp.battleSystem.partyMembers.get(i).stats.maxMp;
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(24F));
            g2.drawString(partyHealthText, x + 125 + ((i - 1) * 120), y + 80);
            g2.drawString(partyManaText, x + 125 + ((i - 1) * 120), y + 97);

            if (selectedIndex == i) {
                g2.setColor(Color.RED);
                g2.drawRoundRect(x + 128 + ((i -1) * 120), y+1, 65, 65, 20, 20);
            }
        }
    }

    /**
     * Draws the turn icons during combat, indicating the order of turns based on the press turn value.
     *
     * @param pressTurn The press turn value indicating the turn order in combat.
     */
    private void drawTurnIcons(int pressTurn) {
        int iconWidth = 64;
        int iconHeight = 64;
        int iconSpacing = 10;
        int startX = gp.screenWidth - gp.tileSize - iconWidth / 2;
        int startY = gp.tileSize / 2;

        for (int i = 0; i < 4; i++) {
            BufferedImage iconImage;
            if (pressTurn >= 8 - i) {
                iconImage = pressTurnIcon1;
            } else if (pressTurn > i) {
                iconImage = pressTurnIcon2;
            } else {
                break;
            }

            g2.drawImage(iconImage, startX, startY, iconWidth, iconHeight, null);
            startX -= (iconWidth + iconSpacing);
        }
    }

    /**
     * Draws the negotiation screen during combat, displaying player, monster, and available options.
     */
    public void drawNegotiationScreen() {
        //WINDOW
        int x = gp.tileSize;
        int y = gp.tileSize;
        int width = gp.screenWidth - gp.tileSize * 2;
        int height = gp.tileSize * 10;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.drawImage(backgroundCombat, 0, 0, gp.screenWidth, gp.screenHeight, null);

        drawSubWindow(x, y, width, height);

        //Draw Player Image on left center
        x = gp.tileSize;
        y = gp.tileSize * 3;
        g2.drawImage(gp.player.standFront, x + 10, y + 10, 128, 128, null);

        //Draw monster Image on right center
        x = gp.screenWidth - gp.tileSize * 5;
        y = gp.tileSize * 3;
        g2.drawImage(gp.battleSystem.monster.combatImage, x + 10, y + 10, 128, 128, null);

        //Draw Question and Options at bottom center
        x = gp.tileSize;
        y = gp.tileSize * 8;
        height = gp.tileSize * 5;
        drawSubWindow(x, y, width, height);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(24F));

        // Check if it's a money request
        if (gp.battleSystem.negotiationSystem.moneyRequest) {
            String moneyRequestText = gp.battleSystem.negotiationSystem.requestMoneyText();
            x = getXforCenterText(moneyRequestText);
            g2.drawString(moneyRequestText, x, y + 60);
            g2.drawString("Si", x, y + 90);
            g2.drawString("No", x, y + 120);
            if (commandNum2 == 0) {
                g2.drawString("->", x - gp.tileSize, y + 90);
            } else if (commandNum2 == 1) {
                g2.drawString("->", x - gp.tileSize, y + 120);
            }

        } else {
            Pregunta pregunta = gp.battleSystem.negotiationSystem.preguntaActual;
            x = getXforCenterText(pregunta.getTexto());
            g2.drawString(pregunta.getTexto(), x, y + 30);
            int commandNumCounter = 0;
            for (Opcion opcion : pregunta.getOpciones()) {
                x = getXforCenterText(opcion.getTexto());
                g2.drawString(opcion.getTexto(), x, y + 60);
                if (commandNum == commandNumCounter) {
                    g2.drawString("->", x - gp.tileSize, y + 60);
                }
                y += 30;
                commandNumCounter++;

            }
        }
    }

    /**
     * Draws the negotiation reward screen, displaying options for adding a demon to the party, asking for items, or asking for money.
     */
    public void drawNegotiationRewardScreen() {
        //Caja con añadir a Party
        int x = gp.tileSize;
        int y = gp.tileSize;
        int width = gp.screenWidth - gp.tileSize * 2;
        int height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(24F));
        x = getXforCenterText("Añadir El Demonio a la Party");
        g2.drawString("Añadir El Demonio a la Party", x, y + 30);
        if (commandNum2 == 0) {
            g2.drawString("->", x - gp.tileSize, y + 30);
        }

        //Caja con Pedir Objetos
        x = gp.tileSize;
        y = gp.tileSize * 5;
        drawSubWindow(x, y, width, height);
        x = getXforCenterText("Pedir Objetos");
        g2.drawString("Pedir Objetos", x, y + 30);
        if (commandNum2 == 1) {
            g2.drawString("->", x - gp.tileSize, y + 30);
        }

        //Caja con Pedir Dinero
        x = gp.tileSize;
        y = gp.tileSize * 10;
        drawSubWindow(x, y, width, height);
        x = getXforCenterText("Pedir Dinero");
        g2.drawString("Pedir Dinero", x, y + 30);
        if (commandNum2 == 2) {
            g2.drawString("->", x - gp.tileSize, y + 30);
        }
    }

    /**
     * Draws the options menu, allowing the player to access various game settings.
     */
    public void drawOptionsMenu() {
        //Displayea un frame con las opciones de status inventario guardar etc
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 3;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2.setColor(Color.gray);
        g2.fillRect(frameX, frameY, frameWidth, frameHeight);
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(24F));

        int textX = frameX + gp.tileSize / 2;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 68;
        String text;

        //NAMES
        text = "STATUS";
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString("->", textX - (gp.tileSize / 2 - 5), textY);
        }
        textY += lineHeight;


        text = "INVENTORY";
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString("->", textX - (gp.tileSize / 2 - 5), textY);
        }
        textY += lineHeight;


        text = "SAVE";
        g2.drawString(text, textX, textY);
        if (commandNum == 2) {
            g2.drawString("->", textX - (gp.tileSize / 2 - 5), textY);
        }
        textY += lineHeight;

        text = "OPTIONS";
        g2.drawString(text, textX, textY);
        if (commandNum == 3) {
            g2.drawString("->", textX - (gp.tileSize / 2 - 5), textY);
        }
        textY += lineHeight;


        text = "EXIT";
        g2.drawString(text, textX, textY);
        if (commandNum == 4) {
            g2.drawString("->", textX - (gp.tileSize / 2 - 5), textY);
        }

    }

    /**
     * Draws the inventory screen, displaying the player's items and allowing item selection.
     */
    public void drawInventoryScreen() {

        //FRAME COMO TAL
        int frameX = gp.tileSize * 2;
        int frameY = gp.tileSize * 2;
        int frameWidth = gp.screenWidth - gp.tileSize * 4;
        int frameHeight = gp.screenHeight - gp.tileSize * 4;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //ITEM SLOTS DENTRO DEL FRAME

        //11 X 7 (inventario Slots)
        final int slotXinicial = frameX + 20;
        final int slotYinicial = frameY + 20;
        int slotX = slotXinicial;
        int slotY = slotYinicial;

        // DIBUJAR ITEMS

        for (int inventoryArrayPos = 0; inventoryArrayPos < gp.player.inventory.size(); inventoryArrayPos++) {

            //Dibujar Cursor sobre objetos equipados
            if (gp.player.inventory.get(inventoryArrayPos) == gp.player.stats.weapon || gp.player.inventory.get(inventoryArrayPos) == gp.player.stats.armor) {
                g2.setColor(new Color(235, 219, 52));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            //DIBUJAR ITEM
            g2.drawImage(gp.player.inventory.get(inventoryArrayPos).walkDown1, slotX, slotY, null);

            //PASAR AL SIGUIENTE SLOT
            slotX += gp.tileSize;
            if (slotX >= slotXinicial + (gp.tileSize * 11)) {
                slotX = slotXinicial;
                slotY += gp.tileSize;
            }
        }


        //CURSORES DE SELCCION
        int cursorX = slotXinicial + (gp.tileSize * slotCol);
        int cursorY = slotYinicial + (gp.tileSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //DIBUJAR CURSORS
        g2.setColor(Color.white);
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //Frame con Descripción del Objeto
        frameX = gp.tileSize * 2;
        frameY = gp.tileSize * 10;
        frameWidth = gp.screenWidth - gp.tileSize * 4;
        frameHeight = gp.tileSize * 4;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // DIBUJAR DESCRIPCION DEL OBJETO
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(24F));

        // Obtén la descripción del objeto del inventario
        String text = "";
        if (gp.player.inventory.size() > slotRow * 11 + slotCol) {
            text = gp.player.inventory.get(slotRow * 11 + slotCol).description;
        }

        int textX = frameX + gp.tileSize / 2;
        int textY = frameY + gp.tileSize;

        // Verifica si el texto no está en blanco antes de dibujarlo
        if (!text.isEmpty()) {
            g2.drawString(text, textX, textY);
        }

    }

    /**
     * Retrieves the index of the selected item slot based on the current row and column.
     *
     * @return The index of the selected item slot.
     */
    public int getItemIndexSlot() {
        return slotCol + (slotRow * 7);
    }

    /**
     * Draws the current game message on the screen.
     */
    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));

        for (int messagePosition = 0; messagePosition < messageList.size(); messagePosition++) {

            if (messageList.get(messagePosition) != null) {
                g2.setColor(Color.white);
                g2.drawString(messageList.get(messagePosition), messageX, messageY);

                //Esta parte lo que hace es actualizar el contador del array list y setearlo a la posicion del for loop con el contador actualizado y aumenta la Y del siguiente texto

                int counter = messageCounter.get(messagePosition) + 1;
                messageCounter.set(messagePosition, counter);
                messageY += 50;

                if (messageCounter.get(messagePosition) > 120) {
                    messageList.remove(messagePosition);
                    messageCounter.remove(messagePosition);
                }
            }
        }
    }

    public void drawCombatMessage() {
        int messageX = gp.screenWidth-gp.tileSize*8;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));

        for (int messagePosition = 0; messagePosition < messageList.size(); messagePosition++) {

            if (messageList.get(messagePosition) != null) {
                g2.setColor(Color.white);
                g2.drawString(messageList.get(messagePosition), messageX, messageY);

                //Esta parte lo que hace es actualizar el contador del array list y setearlo a la posicion del for loop con el contador actualizado y aumenta la Y del siguiente texto

                int counter = messageCounter.get(messagePosition) + 1;
                messageCounter.set(messagePosition, counter);
                messageY += 50;

                if (messageCounter.get(messagePosition) > 120) {
                    messageList.remove(messagePosition);
                    messageCounter.remove(messagePosition);
                }
            }
        }
    }

    /**
     * Draws the level-up screen, allowing the player to allocate stat points after leveling up.
     */
    public void drawLevelupScreen() {
        // Configura el color de fondo en negro
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight); // Ajusta las dimensiones según tu ventana

        // Configura las propiedades de renderizado para mejorar la calidad del texto
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Arial", Font.PLAIN, 24)); // Puedes ajustar el tipo de fuente y el tamaño

        // Define el color de fondo para la opción seleccionada
        Color selectedBackgroundColor = Color.BLUE; // Cambia esto al color deseado

        // Configura el color del texto en blanco
        g2.setColor(Color.WHITE);

        String[] attributes = {"vit", "str", "mag", "agi"};
        int y = 100; // Posición vertical inicial de las cadenas de texto

        for (int i = 0; i < attributes.length; i++) {
            // Comprueba si esta opción está seleccionada y ajusta el color de fondo
            if (i == commandNum) {
                g2.setColor(selectedBackgroundColor);
                g2.fillRect(0, y - 24, gp.screenWidth, 30); // Ajusta el tamaño y la posición según tus necesidades
                g2.setColor(Color.WHITE); // Restaura el color del texto a blanco
            }

            // Dibuja la cadena de texto
            g2.drawString(attributes[i], 50, y);

            // Dibuja el valor de la estadística al lado de la flecha
            String statValue = switch (i) {
                case 0 -> String.valueOf(gp.player.stats.vit);
                case 1 -> String.valueOf(gp.player.stats.str);
                case 2 -> String.valueOf(gp.player.stats.mag);
                case 3 -> String.valueOf(gp.player.stats.agi);
                default -> "N/A";
            };
            g2.drawString(statValue, 250, y); // Ajusta la posición horizontal según tus necesidades

            // Dibuja la flecha hacia la derecha
            int arrowX = 300; // Ajusta la posición horizontal de la flecha según sea necesario
            int arrowY = y - 15; // Ajusta la posición vertical de la flecha según sea necesario
            int arrowLength = 20; // Ajusta la longitud de la flecha según sea necesario
            g2.drawLine(arrowX, arrowY, arrowX + arrowLength, arrowY);
            g2.drawLine(arrowX + arrowLength, arrowY, arrowX + arrowLength - 5, arrowY - 5);
            g2.drawLine(arrowX + arrowLength, arrowY, arrowX + arrowLength - 5, arrowY + 5);
            g2.drawLine(arrowX + arrowLength, arrowY, arrowX + arrowLength - 5, arrowY + 10);

            // Incrementa la posición vertical para la siguiente cadena de texto
            y += 50; // Ajusta la separación vertical entre las cadenas de texto

            //Dibujar pointsperStatLeft abajo a la derecha
            g2.setColor(Color.WHITE);
            g2.drawString("Points Left: " + gp.keyH.pointsPerLevel, gp.screenWidth - 200, gp.screenHeight - 50);
        }
    }

    /**
     * Draws the graphical elements based on the current game state, including title screens, messages, dialogue screens,
     * player menus, status screens, inventory screens, combat screens, negotiation screens, negotiation reward screens,
     * level-up screens, options screens, and more.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    @Override
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(p5Hatty);
        //IMPORTANTE, LE QUITA EL DIFUMINADO RARO
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.setColor(Color.white);

        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        if (gp.gameState == gp.playState) {
            drawMessage();
        }
        //pantalla de pausa
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        //movidas de dialogos
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }

        //PLAYER MENU  SCREEN
        if (gp.gameState == gp.enterMenuState) {
            drawOptionsMenu();
        }
        // STATUS SCREEN
        if (gp.gameState == gp.statusState) {
            drawStatusScreen();
        }
        // INVENTORY SCREEN
        if (gp.gameState == gp.inventoryState) {
            drawInventoryScreen();
        }
        //Combat State
        if (gp.gameState == gp.combatState || gp.gameState == gp.magicMenuState || gp.gameState == gp.battleItemsState) {
            drawCombatScreen(gp.battleSystem);
            drawCombatMessage();
            if (gp.battleSystem.playerIsAttacking) {
                int enemyX = gp.tileSize * 6+30;
                int enemyY = (int) (gp.tileSize * 2);
                gp.battleAnimations.playMeleeAttackAnimation(g2, enemyX, enemyY);
                // Other attack logic...
                gp.battleSystem.playerIsAttacking = false;
            }

            else if (gp.battleSystem.monsterIsAttacking) {
                System.out.println("Monster is attacking");

                int x = (int)(gp.tileSize * 3.5);
                int y = (int) (gp.tileSize * 7);

                //switch Case de selectedIndex var
                switch (gp.battleSystem.attackedSlot) {
                    case 0 -> {
                        gp.battleAnimations.playMeleeAttackAnimation(g2, x,y);
                        System.out.println("Attacking 1");
                    }

                    case 1 ->{
                        System.out.println("Attacking 2");
                        gp.battleAnimations.playMeleeAttackAnimation(g2, x + 128 + ((1 - 1) * 120) + 120,y);
                    }

                    case 2 -> {
                        System.out.println("Attacking 3");
                        gp.battleAnimations.playMeleeAttackAnimation(g2, x + 128 + (120),y);
                    }
                    case 3 ->{
                        System.out.println("Attacking 4");
                        gp.battleAnimations.playMeleeAttackAnimation(g2, x + 128 + ((3 - 1) * 120),y);

                    }
                    default -> System.out.println("Error");

                }
                // Other attack logic...
                gp.battleSystem.monsterIsAttacking = false;
            }
            else if(gp.battleSystem.playerMagic){
                int enemyX = gp.tileSize * 6+30;
                int enemyY = (int) (gp.tileSize * 2);
                gp.battleAnimations.playMagicAttackAnimation(g2, enemyX, enemyY);
                // Other attack logic...
                gp.battleSystem.playerMagic= false;
            }
            else if(gp.battleSystem.monsterMagic){
                int x = (int)(gp.tileSize * 3.5);
                int y = (int) (gp.tileSize * 7.5);

                //switch Case de selectedIndex var
                switch (gp.battleSystem.attackedSlot) {
                    case 0 -> {
                        gp.battleAnimations.playMagicAttackAnimation(g2, x,y);
                        System.out.println("Attacking 1");
                    }

                    case 1 ->{
                        System.out.println("Attacking 2");
                        gp.battleAnimations.playMagicAttackAnimation(g2, x + 128 + ((1 - 1) * 120) + 120,y);
                    }

                    case 2 -> {
                        System.out.println("Attacking 3");
                        gp.battleAnimations.playMagicAttackAnimation(g2, x + 128 + (120),y);
                    }
                    case 3 ->{
                        System.out.println("Attacking 4");
                        gp.battleAnimations.playMagicAttackAnimation(g2, x + 128 + ((3 - 1) * 120),y);

                    }
                    default -> System.out.println("Error");

                }
                // Other attack logic...
                gp.battleSystem.monsterMagic= false;
            }

        }
        //Negotiation State
        if (gp.gameState == gp.negotiationState || gp.gameState == gp.moneyRequestState) {
            drawNegotiationScreen();
        }
        //Negotiation Reward State
        if (gp.gameState == gp.negotiationRewardState) {
            drawNegotiationRewardScreen();
        }
        //Level Up State
        if (gp.gameState == gp.levelUpState) {
            drawLevelupScreen();
        }
        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }
    }

    /**
     * Draws the game options screen, providing access to settings such as full screen, music volume, and sound effects volume.
     */
    public void drawOptionsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        //SUB WINDOW

        int frameX = gp.tileSize * 4;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                options_fullScreenNotification(frameX, frameY);
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3:
                options_endGameConfirmation(frameX, frameY);
                break;
        }
    }

    /**
     * Draws the top-level options screen, including settings for full screen, music, sound effects, controls, and quitting the game.
     *
     * @param frameX The X-coordinate of the frame.
     * @param frameY The Y-coordinate of the frame.
     */
    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;


        //TITLE
        String text = "Options";
        textX = getXforCenterText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);


        //FULL SCREEN
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if (commandNum3 == 0) {
            g2.drawString(">", textX - 25, textY);
        }

        //MUSIC
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum3 == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        //SE
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if (commandNum3 == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        //CONTROL
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if (commandNum3 == 3) {
            g2.drawString(">", textX - 25, textY);
        }

        //END GAME
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum3 == 4) {
            g2.drawString(">", textX - 25, textY);
        }

        //BACK
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if (commandNum3 == 5) {
            g2.drawString(">", textX - 25, textY);
        }

        //FULL SCREEN CHECK BOX
        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.fullScreenOn == true) {
            g2.fillRect(textX, textY, 24, 24);
        }

        //MUSIC VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24); // 120/5=24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //SE VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    /**
     * Draws the full-screen notification screen, providing information about the change requiring a game restart.
     *
     * @param frameX The X-coordinate of the frame.
     * @param frameY The Y-coordinate of the frame.
     */
    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 2;

        currentDialogue = "This change will take effect \nafter restarting";
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //BACK
        textY = frameY + gp.tileSize * 9;
        textX += gp.tileSize * 5;
        g2.drawString("ESC", textX, textY);

    }

    /**
     * Draws the control options screen, providing information about control mappings.
     *
     * @param frameX The X-coordinate of the frame.
     * @param frameY The Y-coordinate of the frame.
     */
    public void options_control(int frameX, int frameY) {

        int textX;
        int textY;

        //TITLE
        String text = "Control";
        textX = getXforCenterText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Move", textX, textY);
        textY += (int) (gp.tileSize * 1.5);
        g2.drawString("Confirm/Take", textX, textY);
        textY += (int) (gp.tileSize * 1.5);
        g2.drawString("Menu", textX, textY);
        textY += (int) (gp.tileSize * 1.5);
        g2.drawString("Pause/Back", textX, textY);
        textY += (int) (gp.tileSize * 1.5);

        textX = (int) (frameX + gp.tileSize * 5.5);
        textY = frameY + gp.tileSize * 3;
        g2.drawString("WASD", textX, textY);
        textY += (int) (gp.tileSize * 1.5);
        g2.drawString("Z", textX, textY);
        textY += (int) (gp.tileSize * 1.5);
        g2.drawString("ENTER", textX, textY);
        textY += (int) (gp.tileSize * 1.5);
        g2.drawString("ESCAPE", textX, textY);
        textY += (int) (gp.tileSize * 1.5);
        g2.drawString("ESC", textX, textY);
        textY += (int) (gp.tileSize * 1.5);
    }

    /**
     * Draws the end-game confirmation screen, prompting the player to confirm quitting the game.
     *
     * @param frameX The X-coordinate of the frame.
     * @param frameY The Y-coordinate of the frame.
     */
    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Quit the game and \nreturn to the title screen?";
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //YES

        String text = "YES";
        textX = getXforCenterText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if (commandNum3 == 0) {
            g2.drawString(">", textX - 25, textY);
        }

        //NO

        text = "NO";
        textX = getXforCenterText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum3 == 1) {
            g2.drawString(">", textX - 25, textY);
        }

    }

}
