/*
**********************************************************************************************************************************************
CS5413 : pgm2 - problem III written by Ali Alsahlanee, Fadhil (PART 3)
Semester : Fall 2019
Prof. N.Park
Assignment Identity Management: strongly-trusted(untrasted), shortest-trust-Path(single: source and destination) and minimum spanning tree
Description: IDMsystem.java
This is a main code of the GUI (API)Interface that will be represented a User or Group (associated with attributes) 
nodes as a colored circles and connected them with a weighted colored lines (Weighted Edges).
Enter the source and destination to make a trusted to get shortest-trust-Path, and a minimum spanning tree 
however, this traversal graph will be used DFS/BFS travesals algorithms.
1. Privacy Graph: shows privacy through edgeWeight as directed graph.
2. Anonymity Graph: shows anonymity through edgeWeight as undirected graph.
*************************************************************************************************************************************************
*/
import java.util.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.*;
@SuppressWarnings("unchecked")// uses to remove any unchecked during compilation
@Deprecated // uses to remove any deprecation during compilation
public class IDMsystem extends JFrame implements ActionListener
{
   private int IDMxSize = 920;
   private int IDMySize = 620;
   private boolean directedGraph;
   private Ellipse2D.Double circle;
   private Vector circleVector, lineVector;
   private double startXVert, startYVert, endXVert, endYVert;
   private boolean hasEdgeBegun = false;
   private GraphIDM graph;
   private CircleObject startingEdge;
   private JTextField IDMtext1, IDMtext2;
   private JButton IDMshortest, clearMini, clearMiniGraph;
   private Container frame;
   private JLabel label1,label2;

   public IDMsystem(String title, boolean directed)
   {
      super(title);
      setSize(IDMxSize, IDMySize);
      setResizable(false);
      directedGraph = directed;
      circleVector = new Vector();
      lineVector = new Vector();
      graph = new GraphIDM(40);
      frame = getContentPane();
      frame.setLayout(null);
      IDMtext1 = new JTextField("Enter Source");
      IDMtext2 = new JTextField("Enter Destination");
      IDMshortest = new JButton("Get: Shortest-trust-Path");
      clearMiniGraph = new JButton("Remove: the whole Graph");
      clearMini = new JButton("Clear: Minimum-spanning tree");
	  label1 = new JLabel("text1");
	  label2 = new JLabel("text2");
      frame.add(clearMiniGraph);
      frame.add(IDMtext1);
      frame.add(IDMtext2);
      frame.add(IDMshortest);
      frame.add(clearMini);
	  frame.add(label1);
	  frame.add(label2);
	  IDMtext1.setBounds(25, 17, 100, 23);
      IDMtext2.setBounds(135, 17, 110, 23);
      IDMshortest.setBounds(245, 17, 180, 23);
      clearMini.setBounds(420, 17, 220, 23);
      clearMiniGraph.setBounds(628, 17, 180, 23);
      IDMshortest.addActionListener(this);
      clearMini.addActionListener(this);
      clearMiniGraph.addActionListener(this);
      Dimension ScreenSize=getToolkit().getScreenSize();
      int IDMscreenWidth=ScreenSize.width;
      int IDMscreenHeight=ScreenSize.height;
      setLocation(IDMscreenWidth/2-(int) IDMxSize/2,IDMscreenHeight/2-(int) IDMySize/2);
      addMouseListener(new myMouseListener());
      addWindowListener(new WindowAdapter() 
                        {
                           public void windowClosing(WindowEvent e)
                           {
                              System.exit(0);
                           }
                        });


      circle = new Ellipse2D.Double();
      show();
   }

   private class myMouseListener extends MouseAdapter
   {
      public void mousePressed(MouseEvent e)
      {
        if (e.getModifiers() == InputEvent.BUTTON1_MASK)
         {
            boolean clickedInCircle = false;
            Rectangle2D.Double upperLimitRec = new Rectangle2D.Double(0, 0, 640, 90);

            if (upperLimitRec.contains(e.getX(), e.getY()))
               clickedInCircle = true;


            
            for (int testIndex=0; testIndex < circleVector.size(); testIndex++)
            {
               CircleObject test = ((CircleObject) circleVector.elementAt(testIndex));
               Rectangle2D rect = test.getCircle().getBounds2D(); 
               rect.setRect(rect.getX()-rect.getWidth()/2, rect.getY()-rect.getHeight()/2, 2*rect.getWidth(), 2*rect.getHeight());
               if (rect.contains(e.getX(), e.getY()) || upperLimitRec.contains(e.getX(), e.getY()))
                  clickedInCircle = true;
            }
            if (!clickedInCircle) 
            {
               circle = new Ellipse2D.Double(e.getX()-35 ,e.getY()-25 , 70, 50);
               String titleString = JOptionPane.showInputDialog("IDMsystem Input a Node:(user) and/or (group)?");

               if (titleString != null && titleString.trim().length() != 0)
               {
                  CircleObject newCircle = new CircleObject(circle, titleString.trim());
                  try
                  {
                     graph.addVertex(newCircle); 
                     circleVector.add(newCircle); 
                     repaint();
                  }
                  catch (GraphException err)
                  {
                     JOptionPane.showMessageDialog(null, err.getMessage());
                  }
               }
            }
         }
         else if (e.getModifiers() == InputEvent.BUTTON3_MASK)
         {

            for (int x=0; x<circleVector.size(); x++)
               ((CircleObject) circleVector.elementAt(x)).setActive(false);

            
            for (int x2=0; x2<lineVector.size(); x2++)
               ((LineObject) lineVector.elementAt(x2)).setActive(false);

            CircleObject checkCir = null;	
            int search = 0;
            boolean escape = false;

            while (search < circleVector.size() && !escape)
            {
               if (((CircleObject) circleVector.elementAt(search)).getCircle().contains(e.getX(), e.getY()))
               {
                  checkCir = (CircleObject) circleVector.elementAt(search);
                  escape = true;
               }
               search++;             
            }

            if (checkCir != null) 
            {
               if (!hasEdgeBegun) 
               {
                  startXVert = checkCir.getCenterX();
                  startYVert = checkCir.getCenterY();
                  startingEdge = checkCir;
                  hasEdgeBegun = true;
                  checkCir.setActive(true);
               }
               else 
               {
                  endXVert = checkCir.getCenterX();
                  endYVert = checkCir.getCenterY();
                  hasEdgeBegun = false;

                  startingEdge.setActive(false);                   

                  if (checkCir.getKey().compareTo(startingEdge.getKey()) != 0) 
                  {
                     try
                     {
                        String numString = JOptionPane.showInputDialog("IDMsystem asks: weight of an Edge(path)?").trim();
                        double edgeWeight = Double.parseDouble(numString);

                        if (directedGraph && edgeWeight > 0) 
                        {
                           graph.addEdge(startingEdge.getKey(), checkCir.getKey(), edgeWeight);
                           
                           for (int killLine=0; killLine<lineVector.size(); killLine++)
                           {
                              if (((LineObject) lineVector.elementAt(killLine)).connects(startingEdge, checkCir))
                                 lineVector.removeElementAt(killLine);
                           }
                           lineVector.add(new LineObject(startingEdge.getCenterX(), startingEdge.getCenterY(), checkCir.getCenterX(), checkCir.getCenterY(), edgeWeight, true));
                        }
                        else if (edgeWeight > 0) 
                        {
                           graph.addUndirectedEdge(startingEdge.getKey(), checkCir.getKey(), edgeWeight);
                           
                           for (int killLine=0; killLine<lineVector.size(); killLine++)
                           {
                              if (((LineObject) lineVector.elementAt(killLine)).connects(startingEdge, checkCir) ||
                                  ((LineObject) lineVector.elementAt(killLine)).connects(checkCir, startingEdge))
                                 lineVector.removeElementAt(killLine);
                           }
                           lineVector.add(new LineObject(startingEdge.getCenterX(), startingEdge.getCenterY(), checkCir.getCenterX(), checkCir.getCenterY(), edgeWeight, false));
                        }
                     }
                     catch(Exception numErr)
                     {
                        JOptionPane.showMessageDialog(null, "Error! occured: please, enter a number!");
                     }
                  }
               }
            }
            else 
            {
               endXVert = -1;
               endYVert = -1;
               startXVert = -1;
               startYVert = -1;
               hasEdgeBegun = false;
              
               for (int x=0; x<circleVector.size(); x++)
                  ((CircleObject) circleVector.elementAt(x)).setActive(false);

               
               for (int x2=0; x2<lineVector.size(); x2++)
                  ((LineObject) lineVector.elementAt(x2)).setActive(false);
            }
            repaint();
         } 
      } 
   }

   public void paint(Graphics IDMg)
   {
      Graphics2D IDMg2 = (Graphics2D) IDMg;
      IDMg2.setPaint(frame.getBackground());
      IDMg2.fill(new Rectangle2D.Double(0,0, IDMxSize, IDMySize)); 

      for (int k1=0; k1<lineVector.size(); k1++)
         ((LineObject) lineVector.elementAt(k1)).drawLine(IDMg2);

      
      for (int m=0; m<circleVector.size(); m++)
         ((CircleObject) circleVector.elementAt(m)).drawCircle(IDMg2);

		IDMtext1.updateUI();
		IDMtext2.updateUI();
		IDMshortest.updateUI();
		clearMini.updateUI();
		clearMiniGraph.updateUI();
   }
   
   public void actionPerformed(ActionEvent e)
   {
      int IDMtotalWeight = 0;
      if (e.getSource() == IDMshortest)
      {
         String start = IDMtext1.getText().trim();
         String end = IDMtext2.getText().trim();
         Vector path;
         CircleObject test1, test2;
         LineObject lineTest;

         for (int x=0; x<circleVector.size(); x++)
            ((CircleObject) circleVector.elementAt(x)).setActive(false);

         for (int x2=0; x2<lineVector.size(); x2++)
            ((LineObject) lineVector.elementAt(x2)).setActive(false);

         if (start.trim().length() != 0 && end.trim().length() != 0)
         {
            path = graph.shortestPath(start, end);
              
            if (path != null)
            {
              for (int y=0; y<circleVector.size(); y++)
               {
                  test1 = (CircleObject) circleVector.elementAt(y);
                  for (int y2=0; y2<path.size(); y2++)
                  {
                     test2 = (CircleObject) path.elementAt(y2);
                     if (test1.getKey().compareTo(test2.getKey()) == 0)
                        test1.setActive(true);
                  }
               }
               
               
               if (path.size() > 1)
                  for (int t=0; t<path.size()-1; t++)
                  {
                     test1 = (CircleObject) path.elementAt(t);
                     test2 = (CircleObject) path.elementAt(t+1);
                     IDMtotalWeight += graph.getWeight(test1.getKey(), test2.getKey());
                     for (int t1=0; t1<lineVector.size(); t1++)
                     {
                        lineTest = (LineObject) lineVector.elementAt(t1);
                        if (directedGraph){
                           if (lineTest.connects(test1, test2))
                              lineTest.setActive(true);
                        }
                        else{
                           if (lineTest.connects(test1, test2) || lineTest.connects(test2, test1))
                              lineTest.setActive(true);
                        }
                     }
                  }

               
               if (IDMtotalWeight > 0)
               {
                  if (directedGraph)
                     setTitle("Privacy-Graph shortest-trust-Path total weight: " + IDMtotalWeight);
                  else
                     setTitle("Anonymity-Graph shortest-trust-Path total weight: " + IDMtotalWeight);
               }
                     
               repaint();
            }
            else 
               JOptionPane.showMessageDialog(null, "IDMsystem Warning message: No path is possible between (untrusted path):\n" + IDMtext1.getText() + ", " + IDMtext2.getText());
         }
      } 

      
      else if (e.getSource() == clearMiniGraph)
      {
         graph.makeEmpty();
         lineVector.clear();
         circleVector.clear();
         repaint();
         IDMtext1.setText("");
         IDMtext2.setText("");
      
         if (directedGraph)
            setTitle("<<Privacy Graph: shows privacy through edgeWeight as directed graph>>");
         else
            setTitle("<<Anonymity Graph: shows anonymity through edgeWeight as undirected graph>>");
      }

      
      else if (e.getSource() == clearMini)
      {
         
         for (int x=0; x<circleVector.size(); x++)
            ((CircleObject) circleVector.elementAt(x)).setActive(false);

         
         for (int x2=0; x2<lineVector.size(); x2++)
            ((LineObject) lineVector.elementAt(x2)).setActive(false);

         
         if (directedGraph)
            setTitle("<<Privacy Graph: shows privacy through edgeWeight as directed graph>>");
         else
            setTitle("<<Anonymity Graph: shows anonymity through edgeWeight as undirected graph>>");

         repaint();
      }
   }

   public static void main(String[] args)
   {
      int IDMgraph = JOptionPane.showConfirmDialog(null, "IDM system asking: would you like to start as a Privacy Graph?", "Privacy or Anonymity Graph ID managment system", JOptionPane.YES_NO_OPTION);

      if (IDMgraph == JOptionPane.CLOSED_OPTION)
         System.exit(0);
      else if (IDMgraph == JOptionPane.YES_OPTION)
         new IDMsystem("<Privacy-Graph for Users and/or Groups>", true);
      else if (IDMgraph == JOptionPane.NO_OPTION)
         new IDMsystem("<Anonymity-Graph for Users and/or Groups>", false);
   }
    Image offScreenBuffer;
	public void update(Graphics g)
    {
       
	   Graphics IDMgr; 
       if (offScreenBuffer==null ||
                (! (offScreenBuffer.getWidth(this) == this.getSize().width
                && offScreenBuffer.getHeight(this) == this.getSize().height)))
       {
           offScreenBuffer = this.createImage(getSize().width, getSize().height);
       }

       IDMgr = offScreenBuffer.getGraphics();

       paint(IDMgr); 
       g.drawImage(offScreenBuffer, 0, 0, this);
                  
    }


}