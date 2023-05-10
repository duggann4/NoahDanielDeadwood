package src;

public class Upgrade {
    private int level;
    private String curType;
    private int amt;
    public Upgrade(int level, String curType, int amt){
        this.level = level;
        this.curType = curType;
        this.amt = amt;
    }
    public boolean canUpgrade(int rankTo, String curType, int pHas){
        if(level == rankTo && this.curType.equals(curType) && pHas >= amt){
            return true;
        } else {
            return false;
        }
    }
}
