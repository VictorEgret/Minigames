package fr.greentor.dev.managers;

import fr.greentor.dev.objects.Game;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class gameManager {

    public static ArrayList<Game> activesGames = new ArrayList<>();

    public static void createGame(Game game){
        activesGames.add(game);

        TextComponent gameCreatedMSG = new TextComponent("§aUne partie de " + game.getName() + " a été créée §e[Clicker pour rejoindre]");
        gameCreatedMSG.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eRejoindre").create()));
        gameCreatedMSG.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/game join " + game.getGameID()));

        Bukkit.getServer().spigot().broadcast(gameCreatedMSG);
    }

    public static Game findGameByID(int ID){
        if (!activesGames.isEmpty()) {
            for (Game game : activesGames) {
                if (game.getGameID() == ID) {
                    return game;
                }
            }
        }
        return null;
    }

    public static Game findGameByPlayer(Player p){
        if (!activesGames.isEmpty()) {
            for (Game game : activesGames) {
                if (game.getScores().get(p) != null) {
                    return game;
                }
            }
        }
        return null;
    }

}

