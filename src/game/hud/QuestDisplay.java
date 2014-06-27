package game.hud;

import static game.hud.HUD.BACKGROUND_BLACK;
import game.map.Area;
import game.npc.quest.QuestSequence;
import game.player.Player;
import game.state.StateSingleplayer;
import game.util.Options;
import game.util.resource.FontLibrary;
import java.util.List;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class QuestDisplay extends PassiveHUD {
    
    private List<QuestSequence> quests;
    
    public QuestDisplay(boolean visible, List<QuestSequence> quests) {
        super(visible);
        this.quests = quests;
    }
    
    @Override
    public int getDepth() {
        return -1;
    }
    
    @Override
    public void respondToUserInput(Input in) {
        if (in.isKeyPressed(Options.OPEN_QUEST_DISPLAY.key()))
            visible = !visible;
    }
    
    @Override
    public void display(Graphics g, Player player, Area currentArea, int camX, int camY) {
        int viewX = StateSingleplayer.VIEW_SIZE_X;
        int worldX = StateSingleplayer.WORLD_SIZE_X;
        int worldY = StateSingleplayer.WORLD_SIZE_Y;
        
        g.setColor(BACKGROUND_BLACK);
        
        int posX = camX+5;
        int posY = camY+5;
        int width = (int)(2.3*viewX)/6;
        int height = (int)(((double)worldY/worldX)*(2.3*viewX)/4);
        
        g.fillRect(posX,posY,width,height);
                        
        g.setFont(FontLibrary.PIXEL_FONT.getFont());
        
        g.setColor(FONT_WHITE);
        
        for (int i=0;i<Math.min(quests.size(),10);i++) {
            QuestSequence q = quests.get(i);
            g.drawString(q.toString(),posX,posY+20*i);
        }
    }
}
