import javax.swing.JFrame;

public class GraphMaker extends JFrame {

   public int sizeX = 1000, sizeY = 1000;

    public GraphMaker()
    {
      add(new Graph(sizeX,sizeY));

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(sizeX, sizeY);
      setLocationRelativeTo(null);
      setTitle("Graph");
      setResizable(true);
      setVisible(true);
    }//constructor

    public static void main(String[] args)
    {
      new GraphMaker();
    }//main
}//class
