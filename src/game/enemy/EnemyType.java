package game.enemy;

public enum EnemyType {

    BLOB("blob","blobs"),
    RED_BLOB("red blob","red blobs"),
    GREEN_BLOB("green blob","green blobs"),
    MUTANT("mutant","mutants"),
    ATTACKING("attacking enemy","attacking enemies");
    
    private String singular;
    private String plural;
    
    public String getName(int n) {
        return n>1?plural:singular;
    }
    
    EnemyType(String singular, String plural) {
        this.singular = singular;
        this.plural = plural;
    }
}
