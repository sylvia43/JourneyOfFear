package game.util.resource;

import game.error.ResourceException;
import java.awt.Font;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/** Stores fonts. */
public enum FontLibrary {
    
    // Menu buttons.
    TIMES_NEW_ROMAN_LARGE("Times New Roman",24,true),
    QUEST_FONT("Arial Narrow",18,true),
    PIXEL_FONT("pixel_font.ttf",36,false);
    
    private UnicodeFont font;
    private String name;
    private int size;
    private boolean systemFont;
    
    FontLibrary(String name, int size, boolean systemFont) {
        this.name = name;
        this.size = size;
        this.systemFont = systemFont;
    }
    
    public UnicodeFont getFont() {
        if (font == null) {
            if (systemFont) {
                font = new UnicodeFont(new Font(name,Font.PLAIN,size));
                font.getEffects().add(new ColorEffect(java.awt.Color.white));
                font.addAsciiGlyphs();
                try {
                    font.loadGlyphs();
                } catch (SlickException e) {
                    throw new ResourceException("Error loading font: " + e);
                }
            } else {
                try {
                    font = new UnicodeFont("resources/font/" + name,size,false,false);
                } catch (SlickException e) {
                    throw new ResourceException("Error creating font: " + e);
                }
                font.getEffects().add(new ColorEffect(java.awt.Color.white));
                font.addAsciiGlyphs();
                try {
                    font.loadGlyphs();
                } catch (SlickException e) {
                    throw new ResourceException("Error loading font: " + e);
                }
            }
        }
        return font;
    }
}
