package fr.greentor.dev;

import fr.greentor.dev.commands.GameCommand;
import fr.greentor.dev.listeners.PlayerListener;
import fr.greentor.dev.managers.GameManager;
import fr.greentor.dev.objects.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Minigames extends JavaPlugin implements Listener{
    private static Minigames instance;

    @Override
    public void onEnable(){

        instance = this;

        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable(){
    }

    public static Minigames getInstance() {
        return instance;
    }

    private void registerCommands(){
        String[] commands = {"game", "gamelist"};

        for (String command : commands){
            Objects.requireNonNull(getCommand(command)).setExecutor(new GameCommand());
        }
    }

    public void registerEvents() {
        Listener[] listeners = {this, new PlayerListener()};

        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    @EventHandler
    public void chatFormat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();
        String message = e.getMessage();

        Game playerGame = GameManager.findGameByPlayer(p);

        e.getFormat();

        if (playerGame != null) {
            String formattedMsg = "[" +playerGame.getName() + "] " + p.getName() + "§r: " + message;
            e.setFormat(formattedMsg.replace('&', '§'));
        }
    }
}