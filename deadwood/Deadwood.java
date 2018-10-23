import java.util.Scanner;
import java.util.ArrayList;

public class Deadwood {



    public static void main(String args[]) throws Exception {
        int numplayers;
        ArrayList<String> colors = new ArrayList<String>();
        colors.add("blue");
        colors.add("cyan");
        colors.add("green");
        colors.add("orange");
        colors.add("pink");
        colors.add("red");
        colors.add("violet");
        colors.add("yellow");
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the number of players: ");
        numplayers = input.nextInt();
        Game game = Game.getinstance();
 //     gameboard board = new gameboard();
 //     board.setVisible(true);
  
        game.setnumofplayers(numplayers);
        ArrayList<Actor> players = new ArrayList<Actor>();
        for (int i = 1; i <= numplayers; i++) { 
            String playercolor = null;
            System.out.printf("Player %d's name: ", i);
            String playerName = input.next();
            while(!colors.contains(playercolor)){
            if(playercolor != null){
            System.out.println("That is not a correct color choice. Try again.");
            }
             System.out.printf("Player %d's color choices are :", i);
            System.out.println(colors.toString());
            System.out.printf("Player %d's color: ", i);
            playercolor = input.next();
            }
            colors.remove(playercolor);
            //Players start with no money and no credits
            game.setActor(new Actor(playerName, 0, 0, playercolor));
        }
        game.startGame();

        while (true) {
            game.taketurn();
        }
    }

    public static void quit() {
        System.exit(0);
    }
}
