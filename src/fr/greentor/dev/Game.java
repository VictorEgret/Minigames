package fr.greentor.dev;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import static fr.greentor.dev.gameManager.activesGames;
import static fr.greentor.dev.gameManager.gameMessage;

public class Game {

    private final String[] gameStates = {"Lancée", "Lancement", "En attente", "Terminée"};

    String name;
    int maxPlayer;
    int gameID = createID();
    HashMap<Player, Integer> scores = new HashMap<>();
    String gameState = gameStates[2];
    GameMap map;

    public GameMap getMap() {
        return map;
    }

    public Game(String name, int maxPlayer){
        this.name = name;
        this.maxPlayer = maxPlayer;
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
        gameMessage(this, "§aLa partie de " + this.getName() + " commence...");

        new Countdown(5, this).runTaskLater(main.getInstance(), 20);

        if (forceStart){

        } else {

        }
    }

    public void addPlayer(Player player){
        this.addScores(player, 0);
        gameMessage(this, player.getName() + " à rejoint la partie de " + this.getName());
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
        gameMessage(this, player.getName() + " à quitté la partie de " + this.getName());
        this.scores.remove(player);
        /*if (this.getScores().size() <= 1){
            this.endGame(false);
        }*/
    }

    public void endGame(boolean forceEnd) {
        if (forceEnd){
            Bukkit.broadcastMessage("La partie de " + this.getName() + " a été terminée de force");
        } else {
            Bukkit.broadcastMessage("La partie de " + this.getName() + " est terminée");
            this.setGameState(gameStates[3]);
        }
        activesGames.remove(this);
    }
}
