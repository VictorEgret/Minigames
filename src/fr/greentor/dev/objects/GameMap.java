package fr.greentor.dev.objects;

import fr.greentor.dev.maps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public enum GameMap {

    TEST(new Location[]{new Location(Bukkit.getWorld("world"), 0, 75, 0), new Location(null, 50, 100, 50)},
         new Location[]{new Location(Bukkit.getWorld("world"), 0, 75, 0)},
         maps.getTestMap(),
         "OITC" );

    Location[] corners;
    Location[] spawns;
    Material[][][] map;
    String gameType;

    GameMap(Location[] corners, Location[] spawn, Material[][][] map, String gameType){
        this.corners = corners;
        this.spawns = spawn;
        this.gameType = gameType;
    }

    public Location[] getSpawns() {
        return spawns;
    }

    public Location[] getCorners() {
        return corners;
    }

    public String getGameType() {
        return gameType;
    }

    public void generate(){
        World world = Bukkit.getWorld("world");
        for(int x = this.getCorners()[0].getBlockX(); x < this.getCorners()[1].getBlockX(); x++){
            for(int y = this.getCorners()[0].getBlockY(); y < this.getCorners()[1].getBlockY(); y++){
                for(int z = this.getCorners()[0].getBlockZ(); z < this.getCorners()[1].getBlockZ(); z++){
                    Location currentLoc = new Location(world, x, y, z);
                    world.getBlockAt(currentLoc).setType(this.map[x][y][z]);
                }
            }
        }
    }
}
