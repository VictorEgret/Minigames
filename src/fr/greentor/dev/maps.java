package fr.greentor.dev;

import org.bukkit.Material;

public class maps {
    public static Material[][][] testMap;

    public static Material[][][] getTestMap() {
        for(int x = 0 ; x<11; x++){
            for(int y = 0 ; y<6; y++){
                for(int z = 0 ; z<11; z++){
                    testMap[x][y][z] = Material.BEACON;
                }
            }
        }
    return testMap;
    }
}
