package PL;

import BL.GameManager.*;
import BL.Tiles.Enemies.Enemy;
import BL.Tiles.Players.Player;
import BL.Tiles.Empty;
import BL.Tiles.Wall;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class FileParser {
    private MessageCallback messageCallback;
    public FileParser(MessageCallback messageCallback) {
        this.messageCallback=messageCallback;
    }

    public GameLevel parseLevel(String levelFile, Player player, TileFactory tiles) {
        try {
            List<String> l = Files.readAllLines(Paths.get(levelFile));
            //char[][] arr = new Tile[l.size()][l.get(0).length()];
            GameBoard board = new GameBoard();
            GameLevel m = new GameLevel(board, player);
            for (int i = 0; i < l.size(); i++) {
                String row = l.get(i);
                for (int j = 0; j < row.length(); j++) {
                    char c = row.charAt(j);
                    Position p = new Position(j, i);
                    if (c == '#') {
                        board.add(new Wall(p));
                    } else if (c == '.') {
                        board.add(new Empty(p));
                    } else if (c == '@') {
                        //System.out.println("player position: "+p.toString());
                        m.SetPlayerStartPosition(p);
                        player.SetMessageCallBack(messageCallback);
                        player.setDeathCallBack(() -> m.onPlayerDeath());
                        board.add(player);
                    } else {
                        Enemy e = tiles.produceEnemy(c);
                        e.setPosition(p);
                        m.AddEnemies(e);
                        e.setDeathCallBack(() -> m.onEnemyDeath(e));
                        e.SetMessageCallBack(messageCallback);
                        board.add(e);
                    }
                }
            }

            return m;
        }
        catch (IOException e) {
            System.err.println(e.getMessage()+"\n"+e.getStackTrace());
        }
        return null;

    }
}


