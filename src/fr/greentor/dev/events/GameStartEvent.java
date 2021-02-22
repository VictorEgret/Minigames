package fr.greentor.dev.events;

import fr.greentor.dev.objects.Game;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends Event implements Cancellable {

    private final Game game;
    private boolean isCancelled;
    private static final HandlerList handlers = new HandlerList();

    public GameStartEvent(Game game){
        this.game = game;
        this.isCancelled = false;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
