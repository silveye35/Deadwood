import java.util.ArrayList;


public class Room {
    private String roomName;
    private ArrayList<Room> adjacent;
    private int[] area;

    public Room(String name) {
        this.roomName = name;
        adjacent = new ArrayList<Room>();
        area = new int[4];
    }

    public ArrayList<Room> getAdjacentRooms() {
        return adjacent;
    }

    public boolean isAdjacent(Room adj) {
        for (Room r : adjacent) {
            if (r.equals(adj)) {
                return true;
            }
        }
        return false;
    }

    public void addAdjacentRoom(Room room) {
        adjacent.add(room);
    }

    public String getName() {
        return roomName;
    }
    
     public int[] getArea(){
        return area;
    }
    
     public void setArea(int[] foundarea) {
        area = foundarea;
    }


    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(roomName + "\n");
        str.append("Adjacent rooms:\n");
        for(Room adj : adjacent){
            str.append(adj.getName() + "\n");
        }
        return str.toString();
    }

    public boolean equals(Room r2) {
        return roomName.equals(r2.getName());
    }
}
