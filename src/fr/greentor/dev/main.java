package fr.greentor.dev;

import fr.greentor.dev.managers.gameManager;
import fr.greentor.dev.objects.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class main extends JavaPlugin implements Listener{
    private static main instance;

    @Override
    public void onEnable(){

        instance = this;

        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable(){
    }

    public static main getInstance() {
        return instance;
    }

    private void registerCommands(){
        String[] commands = {"game", "gamelist"};

        for (String command : commands){
            Objects.requireNonNull(getCommand(command)).setExecutor(new commands());
        }
    }

    public void registerEvents() {
        Listener[] listeners = {this};

        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    @EventHandler
    public void chatFormat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();
        String message = e.getMessage();

        Game playerGame = gameManager.findGameByPlayer(p);

        e.getFormat();

        if (playerGame != null) {
            String formattedMsg = "[" +playerGame.getName() + "] " + p.getName() + "§r: " + message;
            e.setFormat(formattedMsg.replace('&', '§'));
        }
    }
}