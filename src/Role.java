package src;

public class Role {
    private String name;
    private String flavorLine;
    private int rankRequired;
    private Player worker;

    public Role(){
        // Called exclusively from parsers
    }

    public boolean takeRole(){
        // checks if the calling player is able to take the role, if so, takes it
        return false;
    }
}
