package fr.greentor.dev.events;

import fr.greentor.dev.objects.Game;

public class GameStartEvent extends GameEvent {

    public GameStartEvent(Game game){
        super(game);
    }

}
