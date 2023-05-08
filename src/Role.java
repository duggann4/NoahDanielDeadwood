package src;

public class Role {
    private String name;
    private String flavorLine;
    private int rankRequired;
    private boolean roleOpen;

    public Role(){
        // Called exclusively from parsers
        name = "";
        flavorLine = "";
        rankRequired = 0;
        roleOpen = true;
    }

    public void setBasic(String name, int rank){
        this.name = name;
        this.rankRequired = rank;
    }

    public void setFlavor(String flavor){
        this.flavorLine = flavor;
    }

    public boolean takeRole(int pRank){
        if(roleOpen && pRank >= rankRequired){
            roleOpen = false;
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return name + ", \"" + flavorLine + "\" requires rank " + rankRequired;
    }
}
