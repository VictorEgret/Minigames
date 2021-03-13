package fr.greentor.dev.events;

import fr.greentor.dev.objects.Game;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEvent extends Event implements Cancellable {

    private final Game game;
    private boolean isCancelled;
    private static final HandlerList handlers = new HandlerList();

    public GameEvent(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
