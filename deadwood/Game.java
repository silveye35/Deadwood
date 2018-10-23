import java.util.Random;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.Arrays;
import java.util.*;


import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;


public class Game {
    private static Game instance = null;

    private Game() {
        Pplayer = PseudoPlayer.getinstance();
    }

    public static Game getinstance() {
        if (instance == null) {
            instance = new Game() {
            };
        }
        return instance;
    }

    public void setnumofplayers(int numplay) {
        numplayers = numplay;
        Actorturns = new Actor[numplayers];
    }

    public int getnumofplayers() {
        return numplayers;
    }

    public Actor[] getPlayers() {
        return Arrays.copyOf(Actorturns, Actorturns.length);
    }

    private int index = 0;

    private int getindex() {
        return index;
    }

    public Actor[] Actorturns;
    private int playersin = 0;
    public PseudoPlayer Pplayer;

    public void setActor(Actor actor) {
        Actorturns[playersin] = actor;
        playersin++;
    }

    public void taketurn() {
        Actorturns[index].takeTurn();
        if (index == numplayers - 1) {
            index = 0;
        } else {
            index++;
        }
        Pplayer.takeTurn();
    }

    public Integer numplayers;

    public void setplayerorder() {
        Board boardgame = Board.getinstance();
        if (numplayers.equals(3)) {
            boardgame.setdays(3);
            boardgame.setcurrdays(0);
        } else if (numplayers.equals(2)) {
            boardgame.setdays(3);
            boardgame.setcurrdays(0);
        } else {
            boardgame.setdays(4);
            boardgame.setcurrdays(0);
        }
        if (numplayers.equals(5)) {
            for (int i = 0; i < numplayers; i++) {
                Actorturns[i].setCredits(2);
            }
        }
        if (numplayers.equals(6)) {
            for (int i = 0; i < numplayers; i++) {
                Actorturns[i].setCredits(4);
            }
        }
        if (numplayers.equals(7)) {
            for (int i = 0; i < numplayers; i++) {
                Actorturns[i].setRank(2);
            }
        }
        if (numplayers.equals(8)) {
            for (int i = 0; i < numplayers; i++) {
                Actorturns[i].setRank(2);
            }
        }
        List<Actor> ActorList = Arrays.asList(Actorturns);
        Collections.shuffle(ActorList);
        Actorturns = ActorList.toArray(new Actor[ActorList.size()]);
    }

    private Room findRoom(String name, Room[] rooms) {
        for (int e = 0; e < rooms.length; e++) {
            if ((rooms[e].getName()).equals(name)) {
                return rooms[e];
            }
        }
        return null;
    }

    public static int rolldice() {
        int roll = 0;
        Random r = new Random();
        roll = r.nextInt(6) + 1;
        return roll;
    }

    public void startGame()  throws Exception{
        Board boardgame = Board.getinstance();
        try {
            File fXmlFile = new File("./board.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("set");
            int shotindex = 0;
            int roleindex = 0;
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Node nodearea = eElement.getElementsByTagName("area").item(0);
                    SceneRoom newroom = new SceneRoom(eElement.getAttribute("name"));
                    NodeList take = doc.getElementsByTagName("take");
                    Node tNode = take.item(shotindex);
                    if (tNode.getNodeType() == Node.ELEMENT_NODE && nodearea.getNodeType() == Node.ELEMENT_NODE ) {
                        Element tElement = (Element) tNode;
                        Element AreaElement = (Element) nodearea;
                        //arrays for 
                           int sx = Integer.valueOf(AreaElement.getAttribute("x"));
                           int sy = Integer.valueOf(AreaElement.getAttribute("y"));
                           int sh = Integer.valueOf(AreaElement.getAttribute("h"));
                           int sw = Integer.valueOf(AreaElement.getAttribute("w"));
                           int[] foundsetarea = {sx, sy, sh, sw};
                           newroom.setArea(foundsetarea);
                        int prevshots = Integer.valueOf(tElement.getAttribute("number"));
                        newroom.setshots(prevshots);
                        Node areanode = take.item(shotindex);
                        //for each shot in the room, create a 4 sized array and add it to the shotarray
                        for(int o = 0; o < prevshots; o++ ){
                           tNode = take.item(o);
                           NodeList arealist = tNode.getChildNodes();
                           Node area = take.item(o+shotindex);
                           Element aElement = (Element) area;
                           Node nodex = aElement.getElementsByTagName("area").item(0);
                           Element xElement = (Element) nodex;
                           int x = Integer.valueOf(xElement.getAttribute("x")); 
                           int y = Integer.valueOf(xElement.getAttribute("y"));
                           int h = Integer.valueOf(xElement.getAttribute("h"));
                           int w = Integer.valueOf(xElement.getAttribute("w"));
                           int[] foundarea = {x, y, h, w};
                           newroom.addShotArea(o, foundarea);
                           areanode = take.item(shotindex+1);
                        }
                        shotindex = shotindex + prevshots;
                    }

                    NodeList part = doc.getElementsByTagName("parts");
                    Node pNode = part.item(temp);
                    NodeList pList = pNode.getChildNodes();
                    if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                        for (int y = 0; y < pList.getLength(); y++) {
                            Node tempNode = pList.item(y);
                            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                            NodeList aList = tempNode.getChildNodes();
                                Node nodex = aList.item(1);
                                if (nodex.getNodeType() == Node.ELEMENT_NODE) {
                                Element xElement = (Element) nodex;
                                int ax = Integer.valueOf(xElement.getAttribute("x"));
                                int ay = Integer.valueOf(xElement.getAttribute("y"));
                                int ah = Integer.valueOf(xElement.getAttribute("h"));
                                int aw = Integer.valueOf(xElement.getAttribute("w"));
                                int[] foundarea = {ax, ay, ah, aw}; 
                                NamedNodeMap nodeMap = tempNode.getAttributes();
                                Node node = nodeMap.item(0);
                                Node nnamenode = nodeMap.item(1);
                                Role newrole = new Role(0, Integer.valueOf(node.getNodeValue()), "Off", nnamenode.getNodeValue());
                                newrole.setarea(foundarea);
                                newroom.addRole(newrole);
                            }
                            }
                        }
                    }

                    boardgame.setRoom(newroom);

                }
            }
            //all scenerooms have been created
            Room Trailer = new Room("Trailer");
            int[] trailerarray = {991, 248, 194, 201};
            Trailer.setArea(trailerarray);
            boardgame.setRoom(Trailer);
            int[] rankPricesCredits = new int[7];
            int[] rankPricesCash = new int[7];
            NodeList uList = doc.getElementsByTagName("upgrade");
            for (int k = 0; k < uList.getLength(); k++) {
                Node uNode = uList.item(k);
                Element kElement = (Element) uNode;
                int index = Integer.valueOf(kElement.getAttribute("level"));
                String currency = kElement.getAttribute("currency");
                int value = Integer.valueOf(kElement.getAttribute("amt"));

                if (currency.equals("dollar")) {
                    rankPricesCash[index] = value;
                } else {
                    rankPricesCredits[index] = value;
                }
            }
            
            CastingOffice Castingoffice = new CastingOffice(rankPricesCash, rankPricesCredits);
            int[] officearea = {9, 459, 208, 209};
            Castingoffice.setArea(officearea);
            boardgame.setRoom(Castingoffice);
            Room[] Rooms = boardgame.getRooms();
            //at this points all rooms are in the array but they need to add their neighbors
            Room Neighbor0 = findRoom("Train Station", Rooms);
                                if (Neighbor0 != null) {
                                    Castingoffice.addAdjacentRoom(Neighbor0);
                                    Neighbor0.addAdjacentRoom(Castingoffice);
                                }
            Room Neighbor1 = findRoom("Ranch", Rooms);
                                if (Neighbor1 != null) {
                                    Castingoffice.addAdjacentRoom(Neighbor1);
                                    Neighbor1.addAdjacentRoom(Castingoffice);

                                }
            Room Neighbor2 = findRoom("Secret Hideout", Rooms);
                                if (Neighbor2 != null) {
                                    Castingoffice.addAdjacentRoom(Neighbor2);
                                    Neighbor2.addAdjacentRoom(Castingoffice);

                                }   
            Room Neighbor3 = findRoom("Main Street", Rooms);
                                if (Neighbor3 != null) {
                                    Trailer.addAdjacentRoom(Neighbor3);
                                    Neighbor3.addAdjacentRoom(Trailer);

            Room Neighbor4 = findRoom("Saloon" , Rooms);
                                if (Neighbor4 != null) {
                                    Trailer.addAdjacentRoom(Neighbor4);
                                    Neighbor4.addAdjacentRoom(Trailer);

                                }
            Room Neighbor5 = findRoom("Hotel", Rooms);
                                if (Neighbor5 != null) {
                                    Trailer.addAdjacentRoom(Neighbor5);
                                    Neighbor5.addAdjacentRoom(Trailer);

                                }                                 }                                                      
            for (int t = 0; t < nList.getLength(); t++) {
                Node tNode = nList.item(t);
                if (tNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) tNode;
                    String Homename = eElement.getAttribute("name");
                    Room Home = findRoom(Homename, Rooms);
                    NodeList part = doc.getElementsByTagName("neighbors");
                    Node hNode = part.item(t);
                    NodeList neList = hNode.getChildNodes();
                    if (hNode.getNodeType() == Node.ELEMENT_NODE) {
                        for (int x = 0; x < neList.getLength(); x++) {
                            Node tempeNode = neList.item(x);
                            if (tempeNode.getNodeType() == Node.ELEMENT_NODE) {
                                NamedNodeMap nodeeMap = tempeNode.getAttributes();
                                Node nodde = nodeeMap.item(0);
                                String searchname = nodde.getNodeValue();
                                Room Neighbor = findRoom(searchname, Rooms);
                                if (Neighbor != null) {
                                    Home.addAdjacentRoom(Neighbor);
                                }
                            }
                        
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setplayerorder();
        deckList Deck = deckList.getinstance();
        gameboard boardimage = gameboard.getinstance();
        boardgame.ResetDay();
            }


}
