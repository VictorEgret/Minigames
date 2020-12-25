package fr.greentor.dev;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class main extends JavaPlugin implements Listener{
    private static main instance;

    @Override
    public void onEnable(){

        instance = this;
        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(this, this);
        pm.registerEvents(new gameManager(), this);

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