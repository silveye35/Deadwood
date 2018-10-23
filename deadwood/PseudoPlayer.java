import java.util.Arrays;

public class PseudoPlayer implements Player {
    private static PseudoPlayer instance = null;

    private PseudoPlayer() {
    }

    public static PseudoPlayer getinstance() {
        if (instance == null) {
            instance = new PseudoPlayer();
        }
        return instance;
    }

    public Scene[] listofRooms;

    public void takeTurn() {
        int scenes = 0;
        //Check the status of all the scenes
        for (Room r : Board.getinstance().getRooms()) {
            if (r instanceof SceneRoom) {
                if (((SceneRoom) r).getScene().getStatus() == 1) {
                    scenes++;
                }
            }
        }
        //If only one scene is active, end the day
        if (scenes == 1) {
            System.out.println("Ending day");
            endday();
        }
    }

    public void endday() {
        for (Room r : Board.getinstance().getRooms()) {
            if (r instanceof SceneRoom) {
                ((SceneRoom) r).reset();
            }
        }

        Board board = Board.getinstance();
        Game game = Game.getinstance();
        for(Actor player : game.getPlayers()){
            board.moveToTrailer(player);
            player.setRole(null);
        }
        if (board.getcurrdays() == board.getdays()) {
            endgame();
        } else {
            try {
                Board.ResetDay();
            }catch(Exception e){
                //If there's an exception here I don't think there's anything we can do but quit
                Deadwood.quit();
            }
        }
    }

    public void endgame() {
        Game game = Game.getinstance();
        Actor[] players = game.getPlayers();
        Arrays.sort(players, null);
        UI.win(players);
        Deadwood.quit();
    }
}
