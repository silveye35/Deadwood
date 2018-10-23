import java.util.NoSuchElementException;
import java.util.Scanner;

public class UI {
    private static final String PROMPT = "> ";//Prompt for interface
    private static UI ui = null;

    private UI(){

    }

    //Present command line interface to the player
    //turn is the player whose turn it currently is,
    //and all commands will be applied to them.
    public void commandLine(Actor turn) {
    gameboard boardimage = gameboard.getinstance();
        Scanner reader = new Scanner(System.in);
        
        System.out.println("It's your turn, " + turn.getName());
        System.out.print(PROMPT);

        while (reader.hasNextLine()) {
        
            String line = reader.nextLine();
            Scanner parser = new Scanner(line);

            String command = parser.next();
            try {
                switch (command) {
                    case "who":
                        who(turn);
                        break;
                    case "where":
                        where(turn);
                        break;
                    case "move":
                        if (turn.getActions().contains(Actions.MOVE)) {
                            move(turn, parser.nextLine());
                        }
                        break;
                    case "upgrade":
                        if (turn.getActions().contains(Actions.WORK)) {
                            upgrade(turn, parser.next(), parser.nextInt());
                        }
                        break;
                    case "rehearse":
                        if (turn.getActions().contains(Actions.WORK)) {
                            rehearse(turn);
                        }
                        break;
                    case "work":
                        if (turn.getActions().contains(Actions.WORK)) {
                            work(turn, parser.nextLine().trim());
                        }
                        break;
                    case "act":
                        if (turn.getActions().contains(Actions.WORK)) {
                            act(turn);
                        }
                        break;
                    case "help":
                        new UI().help();
                        break;
                    case "end":
                        return;
                    case "endscene"://Debug
                        if(turn.getRoom() instanceof SceneRoom){
                             ((SceneRoom) turn.getRoom()).getScene().setStatus(0);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Unrecognized command");
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid command: " + line);
                System.err.println(e.getMessage());
            } catch (NoSuchElementException e){
                new UI().help();
            }
            System.out.print(PROMPT);
        }
    }

    public void help(){
        System.out.println("Available commands are:");
        System.out.println("move \"place\" -- moves to a given room");
        System.out.println("who -- identify the current player");
        System.out.println("where -- show information about your room");
        System.out.println("work \"part\" -- take the indicated role");
        System.out.println("upgrade $ \"level\" -- upgrade to the indicated level");
        System.out.println("upgrade cr \"level\" -- upgrade to the indicated level");
        System.out.println("rehearse -- rehearse your current role");
        System.out.println("act -- attempt to act your current role");
        System.out.println("end -- end your turn");
        System.out.println("help -- display this message");
    }

    public static UI getinstance(){
        if(ui == null){
            ui = new UI();
        }
        return ui;
    }


    //These are the commands the interface supports

    //Display information about the current player
    public void who(Actor current) {
        System.out.printf("The current player is: %s\n", current);
    }

    //Display information about the room the turn player is currently in
    public void where(Actor current) {
        System.out.printf("You are currently in: %s\n", current.getRoom());
    }

    //Turn player takes a role
    public void work(Actor current, String role) {
        current.takeRole(role);
    }

    public void move(Actor current, String dest) {
        Room r = Board.getinstance().findRoom(dest.trim());
        current.move(r);
        System.out.printf("%s moved to %s\n", current.getName(), dest);
    }

    //Upgrade rank, using type as the currency
    public void upgrade(Actor current, String type, int rank) {
        Currency c;
        if (type.equals("$")) {
            c = Currency.CASH;
        } else if (type.equals("cr")) {
            c = Currency.CREDITS;
        } else {
            throw new IllegalArgumentException();
        }
        current.rankUp(c, rank);
    }

    public void rehearse(Actor current) {
        current.rehearse();
    }

    public void act(Actor current) {
        current.act();
    }

    //Print a message congratulating the winner of the game
    //Parameter is an array of actors sorted by score
    public static void win(Actor[] players) {
        Actor winner = players[players.length - 1];
        System.out.println("=*=*=*=*=*=*=*=*=*=*=");
        System.out.println("= !CONGRATULATIONS! =");
        System.out.println("=*=*=*=*=*=*=*=*=*=*=");
        System.out.printf("You won, %s!\nYour score was %d\n", winner.getName(), winner.getScore());

        System.out.println("=*=*=*=*=*=*=*=*=*=");
        System.out.println();
        for (int i = players.length - 2; i <= 0; i--) {
            System.out.printf("%s, score: %d\n", players[i].getName(), players[i].getScore());
        }
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
        System.out.println("Thank you for playing Deadwood");
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");


    }


}
