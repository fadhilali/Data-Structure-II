import java.awt.geom.*;
import java.awt.*;
public class LineObject
{
   private Line2D.Double line, miniLine;
   private boolean active, directed;
   private int midX, midY, stringX, stringY;
   private Paint currentColor;
   private String weight;
   double originalX1, originalY1, originalX2, originalY2;

   public LineObject(Line2D.Double line, double newWeight, boolean directed)
   {
      this.line = line;
      originalX1 = line.getX1();
      originalY1 = line.getY1();
      originalX2 = line.getX2();
      originalY2 = line.getY2();
      this.directed = directed;
      weight = newWeight + "";
      currentColor = Color.red;
      midX = (int) (line.getX1() + line.getX2())/2;
      midY = (int) (line.getY1() + line.getY2())/2;
      stringX = midX;
      stringY = midY;

      if (directed)
      {
         currentColor = new  GradientPaint((float) line.getX1(), (float) line.getY1(), Color.red, (float) line.getX2(), (float) line.getY2(), Color.white);
         if (line.getX1() > line.getX2())
         {
            stringX = stringX - 20;
            stringY = stringY - 10;
            line.setLine(line.getX1(), line.getY1()-5, line.getX2(), line.getY2()-5);
            miniLine = new Line2D.Double(line.getX1(), line.getY1(), midX, midY-5);
         }
         else
         {
            stringX = stringX - 20;
            stringY = stringY + 20;
            line.setLine(line.getX1(), line.getY1()+5, line.getX2(), line.getY2()+5);
            miniLine = new Line2D.Double(line.getX1(), line.getY1(), midX, midY+5);
         }
      }
   }

   public LineObject(double x1, double y1, double x2, double y2, double newWeight, boolean newbool)
   {
      this(new Line2D.Double(x1, y1, x2, y2), newWeight, newbool);
   }

   public boolean connects(CircleObject cir1, CircleObject cir2)
   {
      if (cir1.getCenterX() == originalX1 && cir1.getCenterY() == originalY1 && 
      cir2.getCenterX() == originalX2 && cir2.getCenterY() == originalY2)
          return true;
       else
          return false;
   }

   public void drawLine(Graphics2D IDMg2)
   {
      IDMg2.setStroke(new BasicStroke(5f));
      IDMg2.setPaint(currentColor);
      IDMg2.draw(line);

      if (directed)
      {
         IDMg2.setPaint(Color.black);
         IDMg2.setStroke(new BasicStroke(1f));
         IDMg2.draw(miniLine);
      }

      IDMg2.setPaint(Color.blue);
      IDMg2.drawString(weight, stringX, stringY);
   }

   public void setActive(boolean newState)
   {
      active = newState;
      if (active)
      {
         if (directed)
            currentColor = new  GradientPaint((float) line.getX1(), (float) line.getY1(), Color.green, (float) line.getX2(), (float) line.getY2(), Color.white);
         else
            currentColor = Color.green;
      }
      else
      {
         if (directed)
            currentColor = new  GradientPaint((float) line.getX1(), (float) line.getY1(), Color.red, (float) line.getX2(), (float) line.getY2(), Color.white);
         else
            currentColor = Color.red;
      }
   }

   public boolean getActive()
   {
      return active;
   }
}