package game;

import javax.swing.JApplet;

public class AppletStart extends JApplet {
    
    @Override
    public void init() {
        Game.main(null);
    }
}