package src;

public class Role {
    private String name;
    private String flavorLine;
    private int rankRequired;
    private boolean roleOpen;

    public Role(){
        // Called exclusively from parsers
        roleOpen = true;
    }

    public boolean takeRole(int pRank){
        if(roleOpen && pRank >= rankRequired){
            roleOpen = false;
            return true;
        } else {
            return false;
        }
    }

    public String toString(){
        return name + ", \"" + flavorLine + "\" requires rank " + rankRequired;
    }
}
