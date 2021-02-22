package fr.greentor.dev.objects;

import fr.greentor.dev.events.GameEndEvent;
import fr.greentor.dev.events.GameStartEvent;
import fr.greentor.dev.main;
import fr.greentor.dev.managers.gameManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import static fr.greentor.dev.managers.gameManager.activesGames;

public class Game {

    private final String[] gameStates = {"Lancée", "Lancement", "En attente", "Terminée"};

    private final String name;
    private final int maxPlayer;
    private final int gameID = createID();
    private HashMap<Player, Integer> scores = new HashMap<>();
    private String gameState = gameStates[2];
    private final GameMap map;

    public Game(String name, int maxPlayer, GameMap map){
        this.name = name;
        this.maxPlayer = maxPlayer;
        this.map = map;
    }

    private int createID(){
        int ID = ThreadLocalRandom.current().nextInt(0, 10000);

        while (gameManager.findGameByID(ID) != null){
            ID = ThreadLocalRandom.current().nextInt(0, 10000);
        }
        return ID;
    }

    public int getGameID() {
        return gameID;
    }

    public void sendMessage(String message){
        for (Player p : this.getScores().keySet()){
            p.sendMessage(message);
        }
    }

    public void sendTextComponent(TextComponent message){
        for (Player p : this.getScores().keySet()){
            p.spigot().sendMessage(message);
        }
    }

    public void teleport(Location loc){
        for (Player p : this.getScores().keySet()){
            p.teleport(loc);
        }
    }

    public GameMap getMap() {
        return map;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public String getName() {
        return name;
    }

    public HashMap<Player, Integer> getScores() {
        return scores;
    }

    public void setScores(HashMap<Player, Integer> scores) {
        this.scores = scores;
    }

    private void addScores(Player player, Integer score) {
        this.scores.put(player, score);
    }

    public void startGame(boolean forceStart){

        this.setGameState(gameStates[1]);
        this.sendMessage("§aLa partie de " + this.getName() + " commence...");
        Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent(this));

        new Countdown(5, this).runTaskTimer(main.getInstance(), 0, 20);

        this.generateMap();

        if (forceStart){

        } else {

        }
        this.teleport(this.map.getSpawns()[0]);
    }

    public void addPlayer(Player player){
        this.addScores(player, 0);
        this.sendMessage(player.getName() + " à rejoint la partie de " + this.getName());
        if (this.getScores().size() >= 0.8 * this.getMaxPlayer()){
            startGame(false);
        }
    }

    public String getInfos() {
        return "Jeu : " + this.getName() + "\n" +
               "ID de la partie : " + this.getGameID() + "\n" +
               "Etat de la partie : " + this.getGameState() + "\n" +
               "Joueurs : " + this.getScores().size() + " / " + this.getMaxPlayer() + "\n" +
               "Scores : " + this.getScores().toString();
    }

    public void removePlayer(Player player) {
        this.sendMessage(player.getName() + " à quitté la partie de " + this.getName());
        this.scores.remove(player);
        /*if (this.getScores().size() <= 1){
            this.endGame(false);
        }*/
    }

    public void endGame(boolean forceEnd) {
        Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(this));
        if (forceEnd){
            Bukkit.broadcastMessage("La partie de " + this.getName() + " a été terminée de force");
        } else {
            Bukkit.broadcastMessage("La partie de " + this.getName() + " est terminée");
            this.setGameState(gameStates[3]);
        }
        activesGames.remove(this);
    }

    public void generateMap(){
        this.map.generate();
    }
}
