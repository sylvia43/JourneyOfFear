package game.map.gen.town;

import game.error.ResourceException;
import game.map.Tile;
import game.map.TileType;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Stores predetermined areas. */
public class Vault {
    
    private String name;
    private Tile[][] tiles;
    
    public Vault(BufferedReader reader) {
        String line;
        int mode = 0;
        Map<Character,TileType> mappings = new HashMap<Character,TileType>();
        List<List<Tile>> map = new ArrayList<>();
        
        do {
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new ResourceException("IO Error: " + e);
            }
            line = line.trim();
            if (line.charAt(0) == '#')
                continue;
            
            switch(mode) {
                case 0: // Looking for name.
                    name = line;
                    mode = 1;
                    break;
                case 1: // Mapping chars to TileTypes.
                    if (line.equals("")) {
                        mode = 2;
                        continue;
                    }
                    mappings.put(line.charAt(0),TileType.getByName(line.substring(1)));
                    break;
                case 2: // Parsing map.
                    List<Tile> row = new ArrayList<Tile>();
                    for (int i=0;i<line.length();i++)
                        row.add(Tile.getTile(mappings.get(line.charAt(i))));
                    map.add(row);
                    break;
            }
        } while(!line.equals("endvault"));
        
        tiles = new Tile[map.size()][];
        
        for (int i=0;i<map.size();i++) {
            List<Tile> row = map.get(i);
            tiles[i] = new Tile[row.size()];
            for (int j=0;j<row.size();j++)
                tiles[i][j] = row.get(j);
        }
    }
}
