import java.util.ArrayList;

public class Board {
    private static Board instance = null;

    private Board() {
        Roomarray = new ArrayList<>();
    }

    public static Board getinstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    private int days;
    private ArrayList<Room> Roomarray;
    private int currdays;

    public int getdays() {
        return days;
    }


    public void setdays(int day) {
        days = day;
    }

    public Room[] getRooms() {
        Room[] template = new Room[0];
        return Roomarray.toArray(template);
    }

    public void setRoom(Room room) {
        Roomarray.add(room);
    }

    public int getcurrdays() {
        return currdays;
    }

    public void setcurrdays(int curday) {
        currdays = curday;
    }

    public void moveToTrailer(Actor player) {
        Room Trailer = findRoom("Trailer");
        player.setRoom(Trailer);
    }

    public Room findRoom(String roomName) {
        for (Room room : Roomarray) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        throw new IllegalArgumentException("No such room");
    }

    public static void ResetDay() throws Exception {
Game boardgame = Game.getinstance();
gameboard boardimage = gameboard.getinstance();

Board board = Board.getinstance();
Actor[] Actorturns = boardgame.getPlayers();
for(Actor player : Actorturns){
   board.moveToTrailer(player);
}
Room[] Roomarray = board.getRooms();
deckList Deck = deckList.getinstance();
for(Room sroom : Roomarray){
   if(sroom.getName().equals("Casting Office") || sroom.getName().equals("Trailer")){
      //do nothing if tailer or casting room
   }
   else{
   SceneRoom sceneroom = (SceneRoom) sroom;
   Scene newscene = Deck.getScene();
   int[][] shotareas = sceneroom.getShotArea();
   for (int j = 0; j < sceneroom.getMshots(); j++){
      int x = shotareas[j][0];
      int y = shotareas[j][1];
    boardimage.shotadd(sceneroom, x, y);
   }
   int[] grabarea = sroom.getArea();
   sceneroom.setScene(newscene);

   boardimage.Addunrevealedscene(grabarea[0], grabarea[1]);
   sceneroom.reset();
   }
}
     boardimage.setVisible(true);

getinstance().currdays++;
}

}
