package main;

import monster.shadowStandar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.TextEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font Arcadia, p5Hatty;

    Font franklin;

    BufferedImage image;
    BufferedImage titleImage;

    //ArrayList de text para que sean Scrolling
    ArrayList<String> messageList = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public String currentDialogue = "";
    public String currentName = "";
    public int commandNum = 0;
    public int commandNum2;
    public int commandNum3 = 0;
    public boolean magicMenu = false;

    //VARIABLES PARA MANEJO DE INVENTARIO
    public int slotCol = 0;
    public int slotRow = 0;

    int subState = 0;


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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addMessage(String text) {
        messageList.add(text);
        messageCounter.add(0);
    }

    public int getXforCenterText(String text) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }

    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F));
        String text = "PAUSED";
        int x = getXforCenterText(text);
        int y = (gp.screenHeight / 2) + 30;

        g2.drawString(text, x, y);

    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color rgb = new Color(0, 0, 0, 50);

        g2.setColor(rgb);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        rgb = new Color(255, 99, 71);
        g2.setColor(rgb);
        g2.fillRect(x, y, width, 5);
    }

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
        g2.drawString("NEXT_EXP", textX, textY);
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

            valor = String.valueOf(member.stats.nextLevelExp);
            g2.drawString("EXP: " + valor, textX, textY);
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

    public void drawCombatScreen(BattleSystem BattleState) {

        g2.setFont(franklin);

        //Dibuja un panel arriba centrado donde se displayeará el monstruo
        int x = (int) (gp.tileSize * 2.5);
        int y = gp.tileSize;
        int width = gp.screenWidth - gp.tileSize * 5;
        int height = gp.tileSize * 4;

        g2.setColor(Color.BLUE);
        g2.fillRect(x, y, width, height);

        drawSubWindow(x, y, width, height);

        // Dentro de este panel de arriba centrado se dibujará el monstruo
        x = gp.tileSize * 6;
        y = (int) (gp.tileSize * 1.5);

        image = BattleState.monster.getCombatImage();
        g2.drawImage(image, x - 18, y - 5, 150, 150, null);

        // Dibuja la barra de vida del monstruo
        int maxHealth = BattleState.monster.stats.maxHp;
        int currentHealth = BattleState.monster.stats.hp;
        int barWidth = 150; // Ancho de la barra de vida
        int barHeight = 10; // Altura de la barra de vida
        int barX = x - 18; // Posición X de la barra de vida
        int barY = y + 150; // Posición Y de la barra de vida

        // Dibuja la vida del monstruo en formato "HP/MAXHP"
        String monsterHealthText = BattleState.monster.stats.hp + "/" + BattleState.monster.stats.maxHp;
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(24F));
        g2.drawString(monsterHealthText, barX + 5, barY - 5);

        // Dibuja un panel centrado a la derecha donde se displayeará el orden de turnos de los jugadores
        x = gp.screenWidth - gp.tileSize * 2;
        y = gp.tileSize * 5;
        width = gp.tileSize * 2;
        height = (int) (gp.tileSize * 6.5);

        g2.setColor(Color.GREEN); // Default Visual
        g2.fillRect(x, y, width, height);

        drawSubWindow(x, y, width, height);

        // Dibuja un menu sencillo que tenga las opciones de atacar, defender, huir, usar item alrededor de abajo a la izquierda pero acercándose al centro
        x = gp.tileSize * 2;
        width = (gp.tileSize * 3);
        height = gp.tileSize * 5;

        g2.setColor(Color.RED); // You can choose any color you like
        g2.fillRect(x, y, width, height);
        drawSubWindow(x, y, width, height);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(24F));

        g2.drawString("Attack", x + gp.tileSize / 2, y + gp.tileSize);
        if (commandNum == 0) {
            g2.drawString("->", x - gp.tileSize, y + gp.tileSize);
        }
        g2.drawString("Magic", x + gp.tileSize / 2, y + gp.tileSize + 40);
        if (commandNum == 1) {
            g2.drawString("->", x - gp.tileSize, y + gp.tileSize + 40);
        }
        g2.drawString("Item", x + gp.tileSize / 2, y + gp.tileSize + 80);
        if (commandNum == 2) {
            g2.drawString("->", x - gp.tileSize, y + gp.tileSize + 80);
        }
        g2.drawString("Defend", x + gp.tileSize / 2, y + gp.tileSize + 120);
        if (commandNum == 3) {
            g2.drawString("->", x - gp.tileSize, y + gp.tileSize + 120);
        }
        g2.drawString("Escape", x + gp.tileSize / 2, y + gp.tileSize + 160);
        if (commandNum == 4) {
            g2.drawString("->", x - gp.tileSize, y + gp.tileSize + 160);
        }

        // Dibuja el menú de magia al lado derecho
        if (magicMenu) {
            int magicMenuX = x + width; // Posición X del menú de magia
            int magicMenuY = y; // Posición Y del menú de magia
            int magicMenuWidth = gp.tileSize * 4; // Ancho del menú de magia
            int magicMenuHeight = height; // Altura del menú de magia

            g2.setColor(Color.MAGENTA); // Color del menú de magia (puedes cambiarlo)
            g2.fillRect(magicMenuX, magicMenuY, magicMenuWidth, magicMenuHeight);

            g2.setColor(Color.BLACK);
            g2.setFont(g2.getFont().deriveFont(24F));

            // Obtén los nombres de los hechizos del jugador
            String[] playerSpellNames = gp.player.printPlayerSpells();

            // Dibuja las opciones de magia
            int magicOptionY = magicMenuY + gp.tileSize;
            for (int i = 0; i < playerSpellNames.length; i++) {
                String spellText = playerSpellNames[i];
                g2.drawString(spellText, magicMenuX + 15, magicOptionY + i * gp.tileSize);

                // Verifica si este es el hechizo seleccionado y ajusta la posición de la flecha
                if (i == gp.ui.commandNum2) {
                    g2.drawString("->", magicMenuX, magicOptionY + i * gp.tileSize);
                }
            }

        }

        // Dibuja centrado abajo un panel donde se displayeará el jugador y su party
        x = (int) (gp.tileSize * 2.5) + 15;
        y = gp.tileSize * 10;
        width = gp.screenWidth - gp.tileSize * 5;
        height = gp.tileSize * 4;

        g2.setColor(Color.YELLOW);
        g2.fillRect(x, y, width, height);
        drawSubWindow(x, y, width, height);

        // Dibuja el jugador
        image = gp.player.standFront;
        g2.drawImage(image, x + 10, y, 64, 64, null);

        // Dibuja hp y mana
        int maxHealth2 = gp.player.stats.maxHp;
        int currentHealth2 = gp.player.stats.hp;
        int maxMana = gp.player.stats.maxMp;
        int currentMana = gp.player.stats.mp;

        // Dibuja la vida del jugador en formato "HP/MAXHP"
        String playerHealthText = gp.player.stats.hp + "/" + gp.player.stats.maxHp;
        String playerManaText = gp.player.stats.mp + "/" + gp.player.stats.maxMp;
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(24F));
        g2.drawString(playerHealthText, x + 5, y + 80);
        g2.drawString(playerManaText, x + 5, y + 97);

        //Hace lo mismo que en player pero para toda la party
        for (int i = 0; i < gp.party.partyMembers.size(); i++) {
            image = gp.party.partyMembers.get(i).getCombatImage();
            g2.drawImage(image, x + 140 + (i * 120), y, 64, 64, null);

            // Dibuja la vida de la party en formato "HP/MAXHP"
            String partyHealthText = gp.party.partyMembers.get(i).stats.hp + "/" + gp.party.partyMembers.get(i).stats.maxHp;
            String partyManaText = gp.party.partyMembers.get(i).stats.mp + "/" + gp.party.partyMembers.get(i).stats.maxMp;
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(24F));
            g2.drawString(partyHealthText, x + 125 + (i * 120), y + 80);
            g2.drawString(partyManaText, x + 125 + (i * 120), y + 97);
        }

    }


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

    public int getItemIndexSlot() {
        return slotCol + (slotRow * 7);
    }

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
        if (gp.gameState == gp.combatState || gp.gameState == gp.magicMenuState) {
            drawCombatScreen(gp.battleSystem);
        }
        if (gp.gameState == gp.levelUpState) {
            drawLevelupScreen();
        }
        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }
    }

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


    }

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

        textX = (int)(frameX + gp.tileSize * 5.5);
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

    public void options_endGameConfirmation(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "Quit the game and \nreturn to the title screen?";
        for(String line: currentDialogue.split("\n")){
            g2.drawString(line,textX,textY);
            textY += 40;
        }

        //YES

        String text = "YES";
        textX = getXforCenterText(text);
        textY += gp.tileSize*3;
        g2.drawString(text,textX,textY);
        if(commandNum3 == 0){
            g2.drawString(">",textX-25,textY);
        }

        //NO

        text = "NO";
        textX = getXforCenterText(text);
        textY += gp.tileSize;
        g2.drawString(text,textX,textY);
        if(commandNum3 == 1){
            g2.drawString(">",textX-25,textY);
        }

    }

}
