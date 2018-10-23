import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.*;
import java.util.*;
import java.io.*;


public class deckList {
    private static deckList instance = null;
    private deckList() throws Exception{
        createDeck();
        ArrayList<Integer> numbers = new ArrayList<>();

        for(int i = 0; i < 40; i++){
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        randomScenes = new Scene[scenes.length];
        for(int i =0; i < randomScenes.length; i++){
            randomScenes[i] = scenes[numbers.get(i)];
        }
    }
    private static Scene[] scenes;
    private static Scene[] randomScenes;
    static int sceneIndex = 0;


    public static deckList getinstance() throws Exception{
        if (instance == null) {
            instance = new deckList() {
            };
        }
        return instance;
    }

    public static void createDeck() throws Exception{
        try{
            List cards = new ArrayList();
            File fXmlFile = new File("./cards.xml");
            DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("card");
            scenes = new Scene[nList.getLength()];

            System.out.println("----------------------------");
            for(int temp = 0; temp < nList.getLength();temp++){
                Node nNode = nList.item(temp);
//            System.out.println("Current element: " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String sceneName = eElement.getAttribute("name");
                    int budget = Integer.parseInt(eElement.getAttribute("budget"));
                    String image =  eElement.getAttribute("img");
//                    System.out.println("Scene name: " + sceneName + "; Budget: " + budget);
                    NodeList partList = eElement.getElementsByTagName("part");
                    Role[] roles =  new Role[partList.getLength()];

                    for(int i = 0; i < partList.getLength();i++){
                        Node sNode = partList.item(i);
                        //tempNode = sNode;
                        NodeList aList = sNode.getChildNodes();
                        Node nodex = aList.item(1);
                        if (nodex.getNodeType() == Node.ELEMENT_NODE && sNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element xElement = (Element) nodex;
                            int ax = Integer.valueOf(xElement.getAttribute("x"));
                            int ay = Integer.valueOf(xElement.getAttribute("y"));
                            int ah = Integer.valueOf(xElement.getAttribute("h"));
                            int aw = Integer.valueOf(xElement.getAttribute("w"));
                            int[] foundarea = {ax, ay, ah, aw}; 
                            Element sElement = (Element) sNode;
                            String partName = sElement.getAttribute("name");
                            int rank = Integer.parseInt(sElement.getAttribute("level"));
                            Role role = new Role(0, rank, "On", partName);
                            role.setarea(foundarea);
                            roles[i] = role;
//                            System.out.println("   Part: " + partName + "; Rank: " + rank);
                        }
                    }
                    Scene newScene = new Scene(budget, roles, sceneName, 1);
                    newScene.setImage(image);
                    scenes[temp] = newScene;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Scene getScene() throws Exception{
        if(sceneIndex > 40){
            throw new Exception("No more scenes available.");
        }else{
            Scene grabbedScene = randomScenes[sceneIndex];
            sceneIndex++;
            return grabbedScene;

        }

    }
}
