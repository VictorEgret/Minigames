package fr.greentor.dev.events;

import fr.greentor.dev.objects.Game;

public class GameEndEvent extends GameEvent{

    public GameEndEvent(Game game){
        super(game);
    }

}
