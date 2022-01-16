package Tests;

import BL.GameManager.*;
import BL.Tiles.Empty;
import BL.Tiles.Enemies.Enemy;
import BL.Tiles.Enemies.Monster;
import BL.Tiles.Enemies.Trap;
import BL.Tiles.Players.Player;
import BL.Tiles.Players.Warrior;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class generalTest {
     GameBoard gameBoard;
     GameLevel gameLevel;
     GameManager gameManager;
     Player p;
     Enemy monster;
     Trap trap;
     List<Enemy> enemyList;
    MessageCallback messageCallback=msg->System.out.println(msg);

    @Before
    public void setUp() throws Exception {
        p = new Warrior("test player", 100, 100, 100, 1);
        monster = new Monster('k', "test monster", 1, 1, 1, 1, 20);
        trap = new Trap('Q', "test trap", 1, 1, 1, 1, 1, 2);
        enemyList = new LinkedList<Enemy>();
        enemyList.add(monster); enemyList.add(trap);
        gameBoard = new GameBoard();
    for(int x = 0; x<5; x++){
        for(int y = 0; y<5; y++){
            if (x == 0 & y == 2){
                p.setPosition(new Position(x, y));
                gameBoard.add(p);
            }
            else if (x == 4 & y==2){
                monster.setPosition(new Position(x, y));
                gameBoard.add(monster);
            }
            else{
                gameBoard.add(new Empty(new Position(x, y)));
            }
        }
    }
        gameLevel = new GameLevel(gameBoard, p);
        gameManager = new GameManager(gameLevel,p,enemyList, messageCallback );
    }

    @Test
    public void swapTest() {
        gameManager.tick('d');gameManager.tick('d');gameManager.tick('d');
        Assert.assertTrue("failed to kill the enemy", gameManager.DeadEnemies());

    }
}
