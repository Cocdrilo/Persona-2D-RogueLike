package main;

import entity.Entity;

/**
 * Manages collision checks for entities and the game environment.
 */
public class CollisionCheck {

    GamePanel gp;

    /**
     * Initializes the CollisionCheck with the specified GamePanel.
     *
     * @param gp The GamePanel instance to associate with the CollisionCheck.
     */
    public CollisionCheck(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Checks for collisions with tiles in the game environment.
     *
     * @param entity The entity for which to check collisions.
     */
    public void checkTile(Entity entity) {

        int entityLeftX = entity.WorldX + entity.solidArea.x;
        int entityRightX = entity.WorldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.WorldY + entity.solidArea.y;
        int entityBottomY = entity.WorldY + entity.solidArea.y + entity.solidArea.height;


        int entityLeftCol = entityLeftX / gp.tileSize;
        int entityRightCol = entityRightX / gp.tileSize;
        int entityTopRow = entityTopY / gp.tileSize;
        int entityBottomRow = entityBottomY / gp.tileSize;

        int TileNum1, TileNum2;


        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopY - entity.speed) / gp.tileSize;
                TileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                TileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[TileNum1].collision || gp.tileM.tile[TileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomY + entity.speed) / gp.tileSize;
                TileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                TileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[TileNum1].collision || gp.tileM.tile[TileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
                TileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                TileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[TileNum1].collision || gp.tileM.tile[TileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
                TileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                TileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[TileNum1].collision || gp.tileM.tile[TileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }

    }

    /**
     * Checks for collisions with objects in the game environment.
     *
     * @param entity The entity for which to check collisions.
     * @param player Flag indicating whether the entity is the player.
     * @return The index of the object with which the entity collided.
     */
    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int objInArray = 0; objInArray < gp.obj.length; objInArray++) {

            if (gp.obj[objInArray] != null) {

                //Get posicion de entidad
                entity.solidArea.x = entity.WorldX + entity.solidArea.x;
                entity.solidArea.y = entity.WorldY + entity.solidArea.y;

                //Get Posicion de OBJ
                gp.obj[objInArray].solidArea.x = gp.obj[objInArray].WorldX + gp.obj[objInArray].solidArea.y;
                gp.obj[objInArray].solidArea.y = gp.obj[objInArray].WorldY + gp.obj[objInArray].solidArea.y;

                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                }
                if (entity.solidArea.intersects(gp.obj[objInArray].solidArea)) {
                    if (gp.obj[objInArray].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = objInArray;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.SolidAreaDefaultY;
                gp.obj[objInArray].solidArea.x = gp.obj[objInArray].solidAreaDefaultX;
                gp.obj[objInArray].solidArea.y = gp.obj[objInArray].SolidAreaDefaultY;

            }

        }

        return index;

    }

    //COLISION CON OTRAS ENTIDADES

    /**
     * Checks for collisions with other entities in the game environment.
     *
     * @param entity The entity for which to check collisions.
     * @param target Array of entities to check collisions against.
     * @return The index of the entity with which the collision occurred.
     */
    public int checkEntity(Entity entity, Entity[] target) {

        int index = 999;

        for (int entityInArray = 0; entityInArray < target.length; entityInArray++) {

            if (target[entityInArray] != null) {

                //Get posicion de entidad
                entity.solidArea.x = entity.WorldX + entity.solidArea.x;
                entity.solidArea.y = entity.WorldY + entity.solidArea.y;

                //Get Posicion de OBJ
                target[entityInArray].solidArea.x = target[entityInArray].WorldX + target[entityInArray].solidArea.y;
                target[entityInArray].solidArea.y = target[entityInArray].WorldY + target[entityInArray].solidArea.y;

                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                }

                //BUGFIX PARA QUE NO SE DETECTE A SI MISMO COMO COLISION Y NO SE PUEDAN MOVER
                if (entity.solidArea.intersects(target[entityInArray].solidArea)) {
                    if (target[entityInArray] != entity) {
                        entity.collisionOn = true;
                        index = entityInArray;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.SolidAreaDefaultY;
                target[entityInArray].solidArea.x = target[entityInArray].solidAreaDefaultX;
                target[entityInArray].solidArea.y = target[entityInArray].SolidAreaDefaultY;

            }

        }

        return index;
    }


    /**
     * Checks for collisions with the player entity.
     *
     * @param entity The entity for which to check collisions.
     * @return True if a collision with the player occurred, false otherwise.
     */
    public boolean checkPlayer(Entity entity) {

        boolean hitPlayer = false;
        //Get posicion de entidad
        entity.solidArea.x = entity.WorldX + entity.solidArea.x;
        entity.solidArea.y = entity.WorldY + entity.solidArea.y;

        //Get Posicion de OBJ
        gp.player.solidArea.x = gp.player.WorldX + gp.player.solidArea.y;
        gp.player.solidArea.y = gp.player.WorldY + gp.player.solidArea.y;

        switch (entity.direction) {
            case "up" -> entity.solidArea.y -= entity.speed;
            case "down" -> entity.solidArea.y += entity.speed;
            case "left" -> entity.solidArea.x -= entity.speed;
            case "right" -> entity.solidArea.x += entity.speed;
        }
        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            hitPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.SolidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.SolidAreaDefaultY;

        return hitPlayer;
    }

}
