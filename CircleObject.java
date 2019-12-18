import java.awt.geom.*;
import java.awt.*;
public class CircleObject extends KeyedItem
{
   private Ellipse2D.Double oval;
   private String title;
   private GradientPaint paint;
   private boolean active;
   private Color outline, textColor;
 
   public CircleObject (Ellipse2D.Double cir, String title)
   {
      super(title); 
      oval = cir;
      this.title = title;
      paint = new GradientPaint((int) cir.getX(), 0, Color.yellow, (int) (cir.getX()+cir.getWidth()), 0, Color.white);
      outline = Color.black;
      textColor = Color.red;
   }

   public GradientPaint getPaint()
   {
      return paint;
   }
  
   public void drawCircle(Graphics2D g2)
   {
      g2.setPaint(paint);
      g2.fill(oval);
      g2.setPaint(outline);
      g2.setStroke(new BasicStroke(1f));
      g2.draw(oval);
      g2.setPaint(textColor);
      g2.drawString(title, (float) (oval.getX() + oval.getWidth()/6), (float) getCenterY());
   }

   public Ellipse2D.Double getCircle()
   {
      return oval;
   }
  
   public void setActive(boolean newVal)
   {
      active = newVal;
      if (active)
      {
         outline = Color.red;
         paint = new GradientPaint((int) oval.getX(), 0, Color.green, (int) (oval.getX()+oval.getWidth()), 0, Color.white);
         textColor = Color.black;
      }
      else
      {
         outline = Color.black;
         paint = new GradientPaint((int) oval.getX(), 0, Color.blue, (int) (oval.getX()+oval.getWidth()), 0, Color.white);
         textColor = Color.yellow;
      }
   }

   public boolean getActive()
   {
      return active;
   }

   
   public double getCenterX()
   {
      return oval.getX()+40;
   }

   
   public double getCenterY()
   {
      return oval.getY()+30;
   }
}