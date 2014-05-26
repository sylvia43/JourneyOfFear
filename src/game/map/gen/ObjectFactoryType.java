package game.map.gen;

public enum ObjectFactoryType {
    
    DEFAULT_TESTING(5,3,1,true),
    BLANK_MULTIPLAYER(0,0,0,false);
    
    public int enemies;
    public int difficulty;
    public int npcs;
    public boolean obstacles;
    
    ObjectFactoryType(int enemies, int difficulty, int npcs, boolean obstacles) {
        this.enemies = enemies;
        this.difficulty = difficulty;
        this.npcs = npcs;
        this.obstacles = obstacles;
    }
}
