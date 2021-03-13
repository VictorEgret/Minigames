package fr.greentor.dev.objects;

import fr.greentor.dev.GameState;
import fr.greentor.dev.events.GameEndEvent;
import fr.greentor.dev.events.GameStartEvent;
import fr.greentor.dev.Minigames;
import fr.greentor.dev.managers.GameManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import static fr.greentor.dev.managers.GameManager.activesGames;

public class Game {

    private final String name;
    private final int maxPlayer;
    private final int gameID = createID();
    private HashMap<Player, Integer> scores = new HashMap<>();
    private GameState gameState = GameState.WAITING;
    private final GameMap map;
    private boolean pvp;
    private boolean fallDamage;
    private double killRegen;

    public Game(String name, int maxPlayer, GameMap map){
        this.name = name;
        this.maxPlayer = maxPlayer;
        this.map = map;
    }

    private int createID(){
        int ID = ThreadLocalRandom.current().nextInt(0, 10000);

        while (GameManager.findGameByID(ID) != null){
            ID = ThreadLocalRandom.current().nextInt(0, 10000);
        }
        return ID;
    }

    public double getKillRegen() {
        return killRegen;
    }

    public void setKillRegen(double killRegen) {
        this.killRegen = killRegen;
    }

    public boolean isPvp() {
        return pvp;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }

    public boolean isFallDamage() {
        return fallDamage;
    }

    public void setFallDamage(boolean fallDamage) {
        this.fallDamage = fallDamage;
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

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
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

        this.setGameState(GameState.LAUNCHING);
        this.sendMessage("§aLa partie de " + this.getName() + " commence...");
        Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent(this));

        new Countdown(5, this).runTaskTimer(Minigames.getInstance(), 0, 20);

        this.generateMap();

        this.setGameState(GameState.LAUNCHED);

        if (forceStart){

        } else {

        }
        this.teleport(this.map.getSpawns()[0]);
    }

    public void addPlayer(Player player){
        if (this.getScores().size() == this.getMaxPlayer()){
            player.sendMessage("Impossible de rejoindre, la partie est complète");
        } else if (this.gameState != GameState.WAITING){
            player.sendMessage("Impossible de rejoindre, la partie est " + this.gameState.toString().toLowerCase());
        } else if (GameManager.findGameByPlayer(player) != null){
            player.sendMessage("Impossible de rejoindre, tu est déjà dans une partie");
        } else {
            this.addScores(player, 0);
            this.sendMessage(player.getName() + " à rejoint la partie de " + this.getName());
            if (this.getScores().size() >= 0.8 * this.getMaxPlayer()) {
                startGame(false);
            }
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
            this.setGameState(GameState.FINISHED);
        }
        activesGames.remove(this);
    }

    public void generateMap(){
        this.map.generate();
    }
}
