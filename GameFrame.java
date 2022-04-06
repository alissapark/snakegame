// class for the actual window the game appears on
import javax.swing.JFrame;

public class GameFrame extends JFrame {
  GameFrame() {
    // create a window thing titled snake
    this.add(new GamePanel());
    this.setTitle("Snake");

    // closes the window when clicked
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // doesn't allow user to resize window
    this.setResizable(false);

    // sets window to ideal size
    this.pack();

    // centers the window and makes it visible
    this.setVisible(true);
    this.setLocationRelativeTo(null);
  }
}
