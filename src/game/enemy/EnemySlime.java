package game.enemy;

import game.player.Player;

public class EnemySlime extends Enemy {

    public EnemySlime(Player player) {
        super(player);
    }
    
    @Override
    protected void initializeVariables() {
        spritePointer = 3;
    }
}