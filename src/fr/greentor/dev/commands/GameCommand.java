package fr.greentor.dev.commands;

import fr.greentor.dev.managers.GameManager;
import fr.greentor.dev.objects.Game;
import fr.greentor.dev.objects.GameMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class GameCommand implements CommandExecutor, Listener {

    private final String gameMessage = "Commandes de gestion de jeux:\n" +
            "/game create (gameName): Crééer une partie\n" +
            "/game start (gameID): Démarrer la partie\n" +
            "/game join (gameID): Rejoindre la partie\n" +
            "/game leave: Quitter la partie\n" +
            "/game end (gameID): Arreter la partie\n" +
            "/game info (gameID): Donne les info sur la partie\n";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            //DEBUG COMMANDS
            if (cmd.getName().equalsIgnoreCase("gameList")) {
                p.sendMessage(GameManager.activesGames.toString());
                if (GameManager.activesGames.size() >= 1){
                    p.sendMessage(GameManager.activesGames.get(0).getInfos());
                }
            }

            if (cmd.getName().equalsIgnoreCase("game")) {
                p.sendMessage(String.valueOf(args.length));
                if (args.length < 1){
                    p.sendMessage(gameMessage);
                } else {

                    Game playerGame = GameManager.findGameByPlayer(p);

                    if (args[0].equalsIgnoreCase("info")) {
                        if (playerGame == null){
                            p.sendMessage("Vous n'etes dans aucune partie");
                        } else {
                            p.sendMessage(playerGame.getInfos());
                        }
                    }
                    if (args[0].equalsIgnoreCase("create")) {
                        if (args.length <= 1){
                            p.sendMessage("Veuillez spécifier le nom du jeu");
                        } else {
                            if (args[1].equalsIgnoreCase("OITC")){
                                GameManager.createGame(new Game("One In The Chamber", 10, GameMap.TEST));
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("join")) {
                        if (args.length <= 1){
                            p.sendMessage("Veuillez spécifier l'ID de la partie");
                        } else {
                            Game joinGame = GameManager.findGameByID(new Integer(args[1]));
                            if (joinGame == null){
                                p.sendMessage("L'ID " + args[1] + " n'est pas attribué");
                            } else {
                                if (GameManager.findGameByPlayer(p) == null){
                                    joinGame.addPlayer(p);
                                } else {
                                    p.sendMessage("Tu est déjà dans une partie");
                                }
                            }
                        }
                    }

                    if (args[0].equalsIgnoreCase("leave")) {
                        if (playerGame == null){
                            p.sendMessage("Vous n'etes dans aucune partie");
                        } else {
                            playerGame.removePlayer(p);
                        }
                    }

                    if (args[0].equalsIgnoreCase("end")) {
                        if (playerGame == null){
                            if (args.length <= 1){
                                p.sendMessage("Veuillez spécifier l'ID de la partie");
                            } else {
                                Game endGame = GameManager.findGameByID(new Integer(args[1]));
                                if (endGame == null){
                                    p.sendMessage("L'ID " + args[1] + " n'est pas attribué");
                                } else {
                                    endGame.endGame(false);
                                }
                            }
                        } else {
                            playerGame.endGame(false);
                        }
                    }

                    if (args[0].equalsIgnoreCase("start")) {
                        if (playerGame == null){
                            if (args.length <= 1){
                                p.sendMessage("Veuillez spécifier l'ID de la partie");
                            } else {
                                Game startGame = GameManager.findGameByID(new Integer(args[1]));
                                if (startGame == null){
                                    p.sendMessage("L'ID " + args[1] + " n'est pas attribué");
                                } else {
                                    startGame.startGame(false);
                                }
                            }
                        } else {
                            playerGame.startGame(false);
                        }
                    }
                }
            }
        }
        return false;
    }
}