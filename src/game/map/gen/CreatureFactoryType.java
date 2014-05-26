package game.map.gen;

public enum CreatureFactoryType {
    
    DEFAULT_TESTING(5,3,1),
    BLANK_MULTIPLAYER(0,0,0);
    
    public int enemies;
    public int difficulty;
    public int npcs;
    
    CreatureFactoryType(int enemies, int difficulty, int npcs) {
        this.enemies = enemies;
        this.difficulty = difficulty;
        this.npcs = npcs;
    }
}
