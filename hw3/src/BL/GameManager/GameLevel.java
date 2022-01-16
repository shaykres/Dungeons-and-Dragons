package BL.GameManager;

import BL.Tiles.Empty;
import BL.Tiles.Enemies.Enemy;
import BL.Tiles.Players.Player;
import java.util.ArrayList;
import java.util.List;

public class GameLevel {

    private GameBoard gameBoard;
    private Player player;
    private List<Enemy> enemies;
    private Position playerStartPosition;

    public GameLevel(GameBoard gameBoard,Player player) {

        this.gameBoard=gameBoard;
        this.player=player;
        this.enemies=new ArrayList<>();

    }

    public void AddEnemies(Enemy e){
        enemies.add(e);
    }

    public void SetPlayerStartPosition(Position p){
        this.playerStartPosition=p;
         }

    public Position GetPlayerStartPosition(){
        return  playerStartPosition;
    }


    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void onPlayerDeath() {

    }


    public void onEnemyDeath(Enemy e){
        Empty emp=new Empty(e.getPosition());
        enemies.remove(e);
        player.gainExperience(e.GetExperience());
        getGameBoard().add(emp);
        getGameBoard().Remove(e);
    }

    @Override
    public String toString() {
       return String.format("%s\n%s", gameBoard, player.describe());
   }


    public List<Enemy> GetEnemies(){
        return enemies;
    }

    public Player getPlayer() {
        return player;
    }

    public  void SetPlayer(Player player){
        this.player=player;
    }


}

