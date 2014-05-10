package game.hud;

import org.newdawn.slick.Input;

/**
 * HUD that always displays and never interacts
 * with the player, such as Minimap.
 */
public abstract class PassiveHUD extends HUD {
    
    @Override
    public void respondToUserInput(Input in) { }
    
    @Override
    public boolean isVisible() {
        return true;
    }
}
