package game.util.resource;

import game.error.ResourceException;
import java.awt.Font;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public enum FontLibrary {
    
    // Menu buttons.
// Menu buttons.
    TIMES_NEW_ROMAN_LARGE("Times New Roman",24),
    QUEST_FONT("Arial Narrow",18);
    
    private UnicodeFont font;
    private String name;
    private int size;
    
    FontLibrary(String name, int size) {
        this.name = name;
        this.size = size;
    }
    
    public UnicodeFont getFont() {
        if (font == null) {
            font = new UnicodeFont(new Font(name,Font.PLAIN,size));
            font.getEffects().add(new ColorEffect(java.awt.Color.white));
            font.addAsciiGlyphs();
            try {
                font.loadGlyphs();
            } catch (SlickException e) {
                throw new ResourceException("Error loading font: " + e);
            }
        }
        return font;
    }
}
