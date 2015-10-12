import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.lang.Math;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;
import java.util.HashSet;

public class Graph extends JPanel
{
  public int sizeX, sizeY;
  public Map<Integer,Integer> pointMap = new HashMap<Integer,Integer>();
  public int fromX, toX;

  public Graph(int sizeX, int sizeY)
  {
    setFocusable(true);
    setDoubleBuffered(true);
    this.sizeX = sizeX;
    this.sizeY = sizeY;

    this.fromX = -sizeX * 10;
    this.toX = sizeX * 10;

    addMouseWheelListener(new ZoomInOut());
    addKeyListener(new MoveAround());
  }//constructor

  public ImageIcon ii = new ImageIcon(this.getClass().getResource("Red.png"));
  public Image redImage = ii.getImage();

  public void paint(Graphics g)
  {
    super.paint(g);
    Graphics2D g2d = (Graphics2D)g;

    setFunction();
    drawAxisXY(g2d);
    drawGraph(g2d);

    Toolkit.getDefaultToolkit().sync();
    g.dispose();
  }//paint

  public double SPACING_X = 10.0;
  public double SPACING_Y = 10.0;

  public int moveAroundX = 0, moveAroundY = 0;

  public void setFunction()
  {
    pointMap.clear();

    for(int x = fromX; x < toX; x++)
      pointMap.put(x - moveAroundX,function(x) + moveAroundY);
  }//setFunction

  public int function(int xx)
  {
    double x = (double)(xx / SPACING_X);

////////////////////  SET FUNCTION HERE ///////////////////////////////////////
    double SET_FUNC = x * sin(x);//////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////

    return (int)(SET_FUNC * SPACING_Y);
  }//function

  public int f(int x)
  {
    if(pointMap.get(x - sizeX/2) == null)
      return 0;

    return sizeY/2 - pointMap.get(x - sizeX/2);
  }//f

  public void drawGraph(Graphics2D g2d)
  {
    for(int x = fromX; x < toX - 1; x++)
    {
      g2d.drawLine(x,  f(x), x+1, f(x+1));

      //if(f(x) == f(x+1))
        //g2d.drawImage(redImage,x,f(x),null);
    }
  }//drawGraph

  public void drawAxisXY(Graphics2D g2d)
  {
    int axisXModifier = sizeY/2 - moveAroundY;
    int axisYModifier = sizeX/2 - moveAroundX;

    g2d.drawLine(0, axisXModifier, sizeX, axisXModifier); //X axis
    g2d.drawLine(axisYModifier, sizeY, axisYModifier, 0); //Y axis

    int HALF_LENGTH = 3;

    for(int xx = axisYModifier; xx > 0; xx -= SPACING_X)
      g2d.drawLine(xx, axisXModifier + HALF_LENGTH, xx, axisXModifier - HALF_LENGTH); //X axis numbering
    for(int xx = axisYModifier; xx < sizeX; xx += SPACING_X)
      g2d.drawLine(xx, axisXModifier + HALF_LENGTH, xx, axisXModifier - HALF_LENGTH); //X axis numbering

    for(int yy = axisXModifier; yy > 0; yy -= SPACING_Y)
      g2d.drawLine(axisYModifier + HALF_LENGTH, yy, axisYModifier - HALF_LENGTH, yy); //Y axis numbering
    for(int yy = axisXModifier; yy < sizeY; yy += SPACING_Y)
      g2d.drawLine(axisYModifier + HALF_LENGTH, yy, axisYModifier - HALF_LENGTH, yy); //Y axis numbering

  }//drawAxisXY

  public static double abs(double x){return Math.abs(x);}

  public static double sin (double x){return Math.sin(x);}
  public static double cos (double x){return Math.cos(x);}
  public static double tan (double x){return Math.tan(x);}
  public static double asin(double x){return Math.asin(x);}
  public static double acos(double x){return Math.acos(x);}
  public static double atan(double x){return Math.atan(x);}

  public static double pow(double x1, double x2 ){return Math.pow(x1,x2);}
  public static double root2(double x){return Math.sqrt(x);}
  public static double e(double x){return Math.exp(x);}
  public static double log2(double x){return Math.log(x);}
  public static double log10(double x){return Math.log10(x);}

  public static double random(double x1, double x2){return (Math.random() * x2) + x1;}
  public static final double e = Math.E;
  public static final double pi = Math.PI;

  public class ZoomInOut implements MouseWheelListener
  {
    public void mouseWheelMoved(MouseWheelEvent e)
    {
      int RATIO = 4;
      int n = RATIO * -e.getWheelRotation();

      if(SPACING_X > RATIO || n > 0)
        SPACING_X += n;

      if(SPACING_Y > RATIO || n > 0)
        SPACING_Y += n;

      repaint();
    }//mouseWheelMoved
  }//innerclass

  public int moveAroundXRatio = (int)SPACING_X * 2;
  public int moveAroundYRatio = (int)SPACING_Y * 2;

  public class MoveAround implements KeyListener
  {
    public Set<Integer> pressed = new HashSet<Integer>();

    public synchronized void keyPressed(KeyEvent e)
    {
      pressed.add(e.getKeyCode());

      for(int key : pressed)
      {
        if (key == KeyEvent.VK_LEFT) { moveAroundX -= moveAroundXRatio; }
        if (key == KeyEvent.VK_RIGHT) { moveAroundX += moveAroundXRatio; }
        if (key == KeyEvent.VK_UP) { moveAroundY -= moveAroundYRatio;}
        if (key == KeyEvent.VK_DOWN) { moveAroundY += moveAroundYRatio; }
      }//for

      repaint();
    }//keyPressed

    public synchronized void keyReleased(KeyEvent e)
    {
      pressed.remove(e.getKeyCode());
    }//keyReleased


    public void keyTyped(KeyEvent e){}

  }//innerclass
}//class

//g2d.drawImage(pointImage,x,(int)(Math.tan(x)), this);

