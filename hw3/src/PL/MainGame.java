package PL;

import BL.GameManager.*;
import BL.Tiles.Players.Player;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainGame {
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            TileFactory tiles = new TileFactory();
            System.out.println("Select a player: ");
            int indexplayer=1;
            for (Player p :tiles.listPlayers()){
                System.out.println(indexplayer+": "+p.describe());
                indexplayer++;
            }

            int chosenPlayer = scanner.nextInt();
            while (chosenPlayer<1 || chosenPlayer>6) {
                System.out.println("please select a player from the following: 1,2,3,4,5,6 : ");
                chosenPlayer = scanner.nextInt();
            }

            //define messageCallback
            MessageCallback messageCallback= msg->System.out.println(msg);

            //initialize file parser
            FileParser fileParser=new FileParser(messageCallback);

            //player initialize to user chosen player
            Player player = tiles.producePlayer(chosenPlayer);
            System.out.println("Your player is: " + player.getName());

            //read levels
            try {
                List<String> GameLevels = Files.list(Paths.get(args[0])).sorted().map(Path::toString).collect(Collectors.toList());
                List<GameLevel> levels = new ArrayList<>();
                for (String levelFile : GameLevels) {
                    //create new GameLevel and insert to list levels
                    levels.add(fileParser.parseLevel(levelFile, player, tiles));
                }

                //for each levels preform game manager
                for (int i = 0; i < levels.size(); i++) {
                    GameLevel level = levels.get(i);
                    Position playerPosition=level.GetPlayerStartPosition();
                    level.getPlayer().setPosition(playerPosition);
                    GameManager gameManager = new GameManager(level, level.getPlayer(),level.GetEnemies(), messageCallback );
                    System.out.println();
                    System.out.println("Level " + (i + 1) + " Started");
                    while (!gameManager.DeadPlayer() & !gameManager.DeadEnemies()) {
                        gameManager.printBoard();
                        String userChoice=scanner.next();
                        while(userChoice.length()!=1){
                            System.out.println("you should enter one of this characters: {w-up, s-down, d-right, a-left, e-cast ability");
                            userChoice=scanner.next();
                        }
                        gameManager.tick(userChoice.charAt(0));
                    }
                    //case of uploadlevel or finish game with user victory
                    if(gameManager.DeadEnemies()){
                        if(i!=levels.size()-1){
                            Player myPlayer=level.getPlayer();
                            levels.get(i+1).SetPlayer(myPlayer);
                        }
                        else
                            System.out.println("You Won!");
                    }
                    else  {//player is dead
                        gameManager.printBoard();
                        player.describe();
                        System.out.println("Game Over.");
                        break;
                    }

                }
            }
            catch (IOException e) {
                System.err.println(e.getMessage()+"\n"+e.getStackTrace());
            }
        }
    }

