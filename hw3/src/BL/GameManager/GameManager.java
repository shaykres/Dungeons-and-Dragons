package BL.GameManager;

import BL.Tiles.Enemies.Enemy;
import BL.Tiles.Players.Player;
import BL.Tiles.Tile;

import java.util.List;

public class GameManager {

    private GameLevel gameLevel;
    private MessageCallback m;
    private List<Enemy> enemies;
    private Player player;
    private boolean deadPlayer;
    private boolean allEnemiesDead;



    public GameManager( GameLevel level,Player player,List<Enemy> enemies,  MessageCallback m) {
        this.m = m;
        this.enemies = enemies;
        this.player = player;
        deadPlayer = false;
        allEnemiesDead = false;
        gameLevel=level;

    }

    public void printBoard() {
        m.send(gameLevel.getGameBoard().PrintBoard());
        m.send(player.describe());
    }

    public void tick(char c) {
        Position positionPlayer = player.getPosition();

        if(c =='w') {
            Tile up=gameLevel.getGameBoard().GetTile(positionPlayer.x,positionPlayer.y-1);
            player.interact(up);
        }
        else if(c =='s') {
            Tile down = gameLevel.getGameBoard().GetTile(positionPlayer.x,positionPlayer.y+1);
            player.interact(down);
        }
        else if(c =='a') {
            Tile left = gameLevel.getGameBoard().GetTile(positionPlayer.x-1,positionPlayer.y);
            player.interact(left);
        }
        else if(c =='d') {
            Tile right = gameLevel.getGameBoard().GetTile(positionPlayer.x+1,positionPlayer.y);
            player.interact(right);
        }
        else if(c =='e') {
            player.castAbility(enemies);
        }
        else if(c!='q') {
            m.send("Invalid Input");
            return;
        }


        enemies= gameLevel.GetEnemies();

        OnGameTick(); //first player and then a enemy makes a random movement tick or chase a player
        if (enemies.size() == 0) {
            allEnemiesDead = true;
        }

        if (player.getTile() == 'X')
            deadPlayer = true;
    }

    private void OnGameTick() {
        player.OnGameTick();
        for (Enemy e : enemies) {
            Position pos =  e.gameEnemyTick(player);
            if (!e.getPosition().equals(pos))
                e.interact(gameLevel.getGameBoard().GetTile(pos.getX(),pos.getY()));
        }
    }

    public boolean DeadPlayer() {
        return deadPlayer;
    }

    public boolean DeadEnemies() {
        return allEnemiesDead;
    }


}