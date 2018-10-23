public class Role {
    private int rehearseTracker;
    private String type;
    private int rank;
    private String name;
    private int[] area;
    private boolean taken = false;


    public Role(int rehearseTracker, int rank, String type, String name) {
        this.rehearseTracker = rehearseTracker;
        this.rank = rank;
        this.type = type;
        this.name = name;
        this.area = new int[4];
    }

    public String getType() {
        return type;
    }


    public int[] getarea() {
        return area;
    }
    
    public void setarea(int[] foundarea) {
        area = foundarea;
    }
    
    
    public int getRehearseTracker() {
        return rehearseTracker;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setRehearseTracker(int rehearseTracker) {
        this.rehearseTracker = rehearseTracker;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void take() {
    
        taken = true;
    }

    public void rehearse() {
        rehearseTracker++;
    }

    public void reset() {
        rehearseTracker = 0;
        taken = false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\n" + name + "\n");
        str.append("Rank: " + rank + "\n");
        str.append("Bonus: " + rehearseTracker + "\n");
        str.append((taken ? "Occupied" : "Available") + "\n");
        return str.toString();
    }
}
