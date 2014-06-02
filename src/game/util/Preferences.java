package game.util;

import game.error.ResourceException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Preferences {
    
    private static final Preferences pref = new Preferences("pref.dat");
    public Preferences get() { return pref; }
    
    private Map<String,String> map;
    private BufferedReader in;
    
    private Preferences(String file) {
        try {
            map = new HashMap<String,String>();
            File f = new File(file);
            if (!f.exists()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("");
                }
            }
            
            in = new BufferedReader(new FileReader("pref.dat"));
        } catch (IOException e) {
            throw new ResourceException(e);
        }
    }
    
    public String get(String key) {
        return map.get(key);
    }
}
