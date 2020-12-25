package fr.greentor.dev;

import org.bukkit.Location;

public class GameMap {

    Location spawn;
    int width;
    int lenght;

    public GameMap(Location spawn, int width, int lenght){
        this.spawn = spawn;
        this.width = width;
        this.lenght = lenght;
    }
}
