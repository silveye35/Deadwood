import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;

import java.util.Collections;

public class SceneRoom extends Room {
    private Scene scene;
    private Component[] shotspots ;
    private int shots;
    private int reveal;
    private Component scenelabel;
    private int maxShots;
    private ArrayList<Component> shotlabels;
    private int[][] shotarea;

    public ArrayList<Role> roles;

    public SceneRoom(int shots, String name) {
        super(name);
        int reveal = 0;
        this.shots = shots;
        this.maxShots = shots;
        roles = new ArrayList<Role>();
        shotlabels = new ArrayList<Component>();
        shotspots = new Component[maxShots];
        shotarea = new int[shots][4];
        scenelabel= null;
    }


    public SceneRoom(Scene scene, int shots, String name) {
        super(name);
        this.reveal = 0;
        this.scene = scene;
        this.shots = shots;
        shotlabels = new ArrayList<Component>();
        this.maxShots = shots;
        shotarea = new int[4][shots];
        shotspots = new Component[maxShots];
        scenelabel= null;
        roles = new ArrayList<Role>();
    }

    public SceneRoom(String name) {
        super(name);
        reveal = 0;
        shotlabels = new ArrayList<Component>();
        shotarea = new int[4][3];
        shotspots = new Component[3];
        scenelabel= null;


        roles = new ArrayList<Role>();
    }
    
    public Component getashotlabel(){
      return shotlabels.remove(0);      
    }
    
    public void setshotlabel(Component addlabel){
      shotlabels.add(addlabel);
    }


    public boolean act(int roll) {
        String playerRoom;
        String sceneRoom;
        int[] bonusOutcomes;
        Actor[] bonusWinners;
        int bonus;
        if (roll >= scene.getBudget()) {
            shots--;//Decrease the shot counter if acting is successful
            gameboard boardimage = gameboard.getinstance();
            Component ashotlabel =getashotlabel();
            boardimage.remove(ashotlabel);
            //boardimage.remove(shotarea[])

            if (shots == 0) {
                //add bonus here
                Game game = Game.getinstance();
                Actor[] players = game.getPlayers();
                if(bonusApplies(players, getName())){
                    System.out.println("Bonus time!");
                    bonusOutcomes = bonusRewards(scene.getBudget());
                    bonus = bonusOutcomes.length - 1;
                    for(Actor a: players){
                        if(((a.getRole().getType()).equals("On")) && ((a.getRoom().getName()).equals(getName()))){ //adds bonus to on-card roles
                            a.addCash(bonusOutcomes[bonus]);
                            System.out.println(a.getName() + " won " + bonusOutcomes[bonus]);
                            bonus--;
                        }else if(((a.getRoom().getName()).equals(getName())) && (!((a.getRole().getType()).equals("On")))){ //adds "windfall" to off-card
                            a.addCash(a.getRole().getRank());
                            System.out.println(a.getName() + " won " + a.getRole().getRank());

                        }else{
                            System.out.println("No bonus for " + a.getName());
                        }
                    }

                }
                else{
                    System.out.println("No bonus");
                }
                scene.setStatus(0);//Mark the scene as finished
            }
            return true;
        }
        return false;

    }

    public boolean bonusApplies(Actor[] actors, String room){
        for(Actor a: actors){
            if(((a.getRole().getType()).equals("On")) && ((a.getRoom().getName()).equals(room))){
                return true;
            }
        }
        return false;
    }

    public int[] bonusRewards(int numDice){
        int[] diceOutcomes = new int[numDice];
        for(int i = 0; i < numDice; i++){
            diceOutcomes[i] = (int) (Math.random()*6) + 1;
        }

        Arrays.sort(diceOutcomes);
        return diceOutcomes;
    }

    public Actor[] bonusWinners(Actor[] actors){
        int winners = 0;
        for(Actor a: actors){
            if((a.getRole().getType()).equals("On")){
                winners++;
            }
        }
        Actor[] bonusWinners = new Actor[winners];

        int i = 0;
        for(Actor a: actors){
            if((a.getRole().getType()).equals("On")){
                bonusWinners[i] = a;
            }
            i++;
        }

        return bonusWinners;
    }

    public void addRole(Role role) {
        roles.add(role);
    }
    
    public int[][] getShotArea() {
      return shotarea;
    }
    
    public Component getshotlabels(int j) {
      return shotspots[j];
    }   
    public void setshotlabel(Component label, int j) {
      shotspots[j] = label;
    }
    
    public Component getscenelabel() {
      return scenelabel;
    }   
    public void setscenelabel(Component label) {
          scenelabel = label;
    }
    
    public void addShotArea(int shotnum, int[] area) {
      shotarea[shotnum] = area;
    }


    public Scene getScene() {
        return scene;
    }
    
        public void setScene(Scene scene) {
        this.scene = scene;
    }
    
    public void reveal(){
    reveal = 1;
    }
    
    public int getreveal(){
    return reveal;
    }


    public Role takeRole(String role) {
        ArrayList<Role> roleArray = new ArrayList<Role>(this.roles);
        roleArray.addAll(Arrays.asList(scene.getRoles()));
        for (Role r : roleArray) {
            if (r.getName().equals(role)) {
                if (r.isTaken()) {
                    throw new IllegalArgumentException("Role taken");
                }
                return r;
            }
        }
        throw new IllegalArgumentException("No such role");
    }

    public void reset() {
        shots = this.maxShots;
        reveal = 0;
        for (Role r : roles) {
            r.reset();
        }
    }

    public void setshots(int takes) {
        shots = takes;
        maxShots = takes;
    }
    
    public int getshots(){
      return shots;
    }
    
    public int getMshots(){
      return maxShots;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(super.toString());
        str.append("\n");
        str.append("There are " + shots + " shots left\n");
        str.append(scene + "\n");

        for(Role r : roles){
            str.append(r.toString());
        }
        return str.toString();
    }

    public int getShots() {
        return shots;
    }
}
