import java.util.ArrayList;
import java.awt.*;


public class Actor implements Player, Comparable {
    private String name;
    private int credits;
    private int cash;
    private int rank;
    private Component playerlabel;
    private String color;
    private Room room;
    private Role role;

    private boolean worked;//True if the player has taken an action this turn.
    private boolean moved;

    public Actor(String name, int startCredits, int startCash, String newcolor) {
        this.name = name;
        this.color = newcolor;
        credits = startCredits;
        cash = startCash;
        rank = 1;
        playerlabel = null;
        room = null;
    }

    public Actor(String name, int startCredits, int startCash, Room startRoom) {
        this.name = name;
        credits = startCredits;
        cash = startCash;
        rank = 1;
        playerlabel = null;
        room = startRoom;
    }
    
    public String getcolor(){
      return color;
    }
    
    public void setcolor(String colorhere){
      color = colorhere;
    }

    public void takeTurn() {
        moved = false;
        worked = false;
        if(room instanceof SceneRoom && ((SceneRoom)room).getScene().getStatus() == 0){
            System.out.println("Scene over, removing role");
            role = null;
        }
        UI.getinstance().commandLine(this);
    }

    //Returns an array of the remaining actions the player can take during this turn
    public ArrayList<Actions> getActions() {
        ArrayList<Actions> moves = new ArrayList<Actions>();
        if (!moved) {
            moves.add(Actions.MOVE);
        }
        if (!worked) {
            moves.add(Actions.WORK);
        }
        return moves;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public int getCash() {
        return cash;
    }

    public int getRank() {
        return rank;
    }
    
    public Component getplayerlabel() {
        return playerlabel;
    }
    
    public void setplayerlabel(Component newlabel) {
      playerlabel= newlabel;
    }


    public void setRank(int newrank) {
        rank = newrank;
    }

    public Room getRoom() {
        return room;
    }

    public Role getRole() {
        return role;
    }

    //Return the player's current score
    //Formula comes from game rules
    public int getScore() {
        return credits + cash + (5 * rank);
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public void addCash(int toAdd){
        cash += toAdd;
    }

    public void setRoom(Room newRoom) {
        this.room = newRoom;
        gameboard boardimage = gameboard.getinstance();
        int[] roomarea = newRoom.getArea();
        boardimage.updatelocationorplayer(this, roomarea[0],roomarea[1]);
        boardimage.revalidate();
        boardimage.repaint();
    }
   

    public void setRole(Role newRole){
        this.role = newRole;
    }

    public void rankUp(Currency c, int rank) {
        if (!(room instanceof CastingOffice)) {
            throw new IllegalArgumentException("Not in Casting Office");
        }
        if (rank <= this.rank) {
            throw new IllegalArgumentException("New rank too low");
        }

        int price = ((CastingOffice) room).getPrice(rank, c);
        switch (c) {
            case CASH:
                if (price <= cash) {
                    this.rank = rank;
                    this.cash -= price;
                } else {
                    throw new IllegalArgumentException("Not enough cash");
                }
                break;
            case CREDITS:
                if (price <= credits) {
                    this.rank = rank;
                    this.credits -= price;
                } else {
                    throw new IllegalArgumentException("Not enough credits");
                }
                break;
        }
        worked = true;
    }

    public void rehearse() {
        //A player can rehearse on their turn. This gives them a bonus
        //that is applied to their roll when acting
        if(role == null){
            throw new IllegalArgumentException("You have no role");
        }
        role.rehearse();
        worked = true;
    }

    public void act() {
        //A player can act their role
        if (!(room instanceof SceneRoom)) {
            throw new IllegalArgumentException("Not in a SceneRoom");
        }

        if (((SceneRoom) room).getScene().getStatus() == 0) {
            role = null;
            throw new IllegalArgumentException("Scene over");
        }
        if (role == null) {
            throw new IllegalArgumentException("No role");
        }
        //Now we are sure acting is possible
        int budget = ((SceneRoom) room).getScene().getBudget();
        int roll = Game.rolldice() + role.getRehearseTracker();

        //Roll a die and compare it to the scene's budget
        if (((SceneRoom) room).act(roll)) {
            System.out.println("You succeed!");
            if (role.getType().equals("On")) {
                credits += 2;
            } else {
                credits += 1;
                cash += 1;
            }
        } else {
            System.out.println("You fail!");
            if (role.getType().equals("Off")) {
                cash += 1;
            }
        }

        worked = true;
    }

    public void move(Room newRoom) {
        if (this.room.isAdjacent(newRoom) && this.role == null) {
            this.room = newRoom;
            gameboard boardimage = gameboard.getinstance();

            SceneRoom sceneroom = (SceneRoom) newRoom;
            if(sceneroom.getreveal() == 0 ){
               sceneroom.reveal();
               Scene newscene = sceneroom.getScene();
               String scenename = newscene.getImage();
               boardimage.Addrevealedscene(sceneroom, scenename);
            }
            int[] roomarea = newRoom.getArea();
            boardimage.updatelocationorplayer(this, roomarea[0],roomarea[1]);
            moved = true;
        } else {
            throw new IllegalArgumentException("You can't move there");
        }
    }

    public void takeRole(String roleName) {
        SceneRoom here;
        if (!(room instanceof SceneRoom)) {
            throw new IllegalArgumentException("Not in a Scene Room");
        }
        if (role != null) {
            throw new IllegalArgumentException("Already have a role");
        }

        here = (SceneRoom) room;
        role = here.takeRole(roleName);
        if(role.getRank() > rank){
            role = null;
            throw new IllegalArgumentException("Rank not high enough");
        }else{
        gameboard boardimage = gameboard.getinstance();
        int[]arearole = role.getarea();
        if (role.getType().equals("Off")){
        boardimage.updatelocationorplayer(this, arearole[0], arearole[1]);
        }
        else{
        int[] areasrole = role.getarea();
        int[] areaofroom = here.getArea();
          boardimage.updatelocationorplayer(this, areasrole[0]+areaofroom[0], areasrole[1]+areaofroom[1]);
         }
            role.take();
        }
        worked = true;
    }

    public String toString() {
        return String.format("Name: %s, rank: %d, credits: %d, cash %d, score: %d\n", name, rank, credits, cash, this.getScore());
    }

    public int compareTo(Object o) {
        Actor a = (Actor) o;
        int thisScore = this.getScore();
        int otherScore = a.getScore();
        if (thisScore < otherScore) {
            return -1;
        } else if (thisScore == otherScore) {
            return 0;
        } else {
            return 1;
        }
    }
}
