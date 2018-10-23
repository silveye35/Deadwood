//make this a singleton object...

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;


public class gameboard extends JFrame {
    private static gameboard instance = null;
    



  // Private Attributes
  
  // JLabels

  JLabel boardlabel;
  JLabel cardlabel;
  JLabel playerlabel;
  JLabel shotlabel;
  JLabel mLabel;
  
  //JButtons
  JButton bAct;
  JButton bRehearse;
  JButton bMove;
  JButton bEnd;
  JButton bUpgrade;
  JButton bWork;


  
  // JLayered Pane
  JLayeredPane bPane;
  
  // Constructor
  private gameboard() {
      
       // Set the title of the JFrame
       super("Deadwood");
       // Set the exit option for the JFrame
       setDefaultCloseOperation(EXIT_ON_CLOSE);
      
       // Create the JLayeredPane to hold the display, cards, dice and buttons

       bPane = getLayeredPane();

       // Create the deadwood board and menu
       boardlabel = new JLabel();
       ImageIcon icon =  new ImageIcon("board.jpg");
       boardlabel.setIcon(icon); 
       boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
       // Add the board to the lowest layer
       bPane.add(boardlabel, new Integer(0));
       
       // Set the size of the GUI
       setSize(icon.getIconWidth()+200,icon.getIconHeight());
       // Create the Menu for action buttons
       mLabel = new JLabel("MENU");
       mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
       bPane.add(mLabel,new Integer(0));
       

       // Create Action buttons
       bAct = new JButton("ACT");
       bAct.setBackground(Color.white);
       bAct.setBounds(icon.getIconWidth()+10, 30,100, 20);
       bAct.addMouseListener(new boardMouseListener());
       
       bRehearse = new JButton("REHEARSE");
       bRehearse.setBackground(Color.white);
       bRehearse.setBounds(icon.getIconWidth()+10,60,100, 20);
       bRehearse.addMouseListener(new boardMouseListener());
       
       bMove = new JButton("MOVE");
       bMove.setBackground(Color.white);
       bMove.setBounds(icon.getIconWidth()+10,90,100, 20);
       bMove.addMouseListener(new boardMouseListener());
       
       bEnd = new JButton("END TURN");
       bEnd.setBackground(Color.white);
       bEnd.setBounds(icon.getIconWidth()+10, 120,100, 20);
       bEnd.addMouseListener(new boardMouseListener());
       
       bUpgrade = new JButton("UPGRADE");
       bUpgrade.setBackground(Color.white);
       bUpgrade.setBounds(icon.getIconWidth()+10,150,100, 20);
       bUpgrade.addMouseListener(new boardMouseListener());
       
       bWork = new JButton("TAKE ROLE");
       bWork.setBackground(Color.white);
       bWork.setBounds(icon.getIconWidth()+10,180,100, 20);
       bWork.addMouseListener(new boardMouseListener());
       
   
   
       // Place the action buttons in the top layer
       bPane.add(bAct, new Integer(1));
       bPane.add(bRehearse, new Integer(1));
       bPane.add(bMove, new Integer(1));
       bPane.add(bEnd, new Integer(1));
       bPane.add(bUpgrade, new Integer(1));
       bPane.add(bWork, new Integer(1));
       bWork.setEnabled(false);
       bUpgrade.setEnabled(false);
       bAct.setEnabled(false);
       bRehearse.setEnabled(false);

       }
       
    public static gameboard getinstance() {
        if (instance == null) {
            instance = new gameboard();
        }
        return instance;
    }
       
       public void Addunrevealedscene(int x, int y){
       // Add a scene card to this room
      
       cardlabel = new JLabel();
       ImageIcon cIcon =  new ImageIcon("cards/back.png");
       cardlabel.setIcon(cIcon); 
       cardlabel.setBounds(x,y,cIcon.getIconWidth(),cIcon.getIconHeight());
       cardlabel.setOpaque(true);
       bPane = getLayeredPane();
       //remove unrevealed scenecard
       // Add the card to the lower layer
       bPane.add(cardlabel, new Integer(1));
    }
       
       public void Addrevealedscene(SceneRoom sroom, String scenenum){
       Component unrevealedlabel = sroom.getscenelabel();
       // Add a scene card to this room
       if(unrevealedlabel != null){
         remove(unrevealedlabel);
       }
       int area[] = sroom.getArea();
       cardlabel = new JLabel();
       ImageIcon cIcon =  new ImageIcon("cards/"+scenenum);
       cardlabel.setIcon(cIcon); 
       cardlabel.setBounds(area[0],area[1],cIcon.getIconWidth(),cIcon.getIconHeight());
       cardlabel.setOpaque(true);
       bPane = getLayeredPane();
       //remove unrevealed scenecard

       // Add the card to the lower layer
       bPane.add(cardlabel, new Integer(2));
       
    }
    
    public void shotadd(SceneRoom sroom, int x, int y){
       shotlabel = new JLabel();
       ImageIcon sIcon = new ImageIcon("shot.png");
       shotlabel.setIcon(sIcon);
       shotlabel.setBounds(x, y, sIcon.getIconWidth(), sIcon.getIconHeight());
       bPane = getLayeredPane();
       
       sroom.setshotlabel(shotlabel);
       bPane.add(shotlabel,new Integer(3));

}
    
   public void updatelocationorplayer(Actor player, int x, int y){
       Component oldlabel = player.getplayerlabel();
       if(oldlabel != null){
       bPane.remove(oldlabel);
       }
       String color = player.getcolor();
       String c = "v";
       if (color.equals("red")){
        c = "r";
       } 
       if (color.equals("violet")){
        c = "v";
       }
       if (color.equals("yellow")){
        c = "y";
       } 
       if (color.equals("green")){
        c = "g";
       }
       if (color.equals("pink")){
        c = "p";
       } 
       if (color.equals("orange")){
        c = "o";
       }
       if (color.equals("blue")){
        c = "b";
       } 
       if (color.equals("cyan")){
        c = "c";
       }
       if (oldlabel != null){
        removeimage(oldlabel);
       }
       int Rank = player.getRank();
       // Add a dice to represent a player. 
       playerlabel = new JLabel();
       ImageIcon pIcon = new ImageIcon("dice/"+c+Rank+".png");
       playerlabel.setIcon(pIcon);
       playerlabel.setBounds(x,y,pIcon.getIconWidth(),pIcon.getIconHeight());  
       //playerlabel.setBounds(114,227,46,46);
       bPane = getLayeredPane();
       player.setplayerlabel(playerlabel);

       bPane.add(playerlabel,new Integer(4));
      }
      
      //create remove images functions
      
      
  // This class implements Mouse Events
  
  class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
          UI ui = UI.getinstance();//For accessing the implementations of commands
         if (e.getSource()== bAct){
            if(bAct.isEnabled()){

                //We'll need to figure out how to get the current player for this
                //ui.act(player);

               System.out.println("Acting is Selected\n");
            }
         }
         else if (e.getSource()== bRehearse){
            if(bRehearse.isEnabled()){
                //ui.rehearse(player)
               System.out.println("Rehearse is Selected\n");
            }
         }
         else if (e.getSource()== bMove){
            if(bMove.isEnabled()){
         //create popup menu here for selection of room
                //ui.move(player, dest)
               System.out.println("Move is Selected\n");
            }
         }  
         else if (e.getSource()== bEnd){
            if(bEnd.isEnabled()){
                //ui.end(player)
            System.out.println("End is Selected\n");
            }
         }
         else if (e.getSource()== bWork){
             //ui.work(player)
            if(bWork.isEnabled()){

         //create popup menu here for selection of upgrade
            System.out.println("Take Role is Selected\n");
            }
         }         
      }
      public void mousePressed(MouseEvent e) {
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
   }
   
   public void removeimage(Component Labelname){
          bPane.remove(Labelname);
   }

   //can use a ListSelectionModel for Move and room selecting

  public static void main(String[] args) {
    gameboard board = getinstance();
    //unrevealedscene calls are made in resetday. 
    
    //shots next
    //board.shotadd(36, 11);
    //board.shotadd(89, 11);
    //board.shotadd(141, 11);
        board.setVisible(true);

    //board.updatelocationorplayer(Actor, 114, 320, null); 
    //board.updatelocationorplayer(Actor, 114, 227, board.playerlabel);
    //board.bPane.remove(board.playerlabel);
    //board.bPane.remove(board.cardlabel);
    //board.bPane.remove(board.shotlabel);
    //board.Addunrevealedscene(20, 60);




    //note that the label must be kept track of so that it can be removed.
  }


}