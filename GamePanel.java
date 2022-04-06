/*
Alissa Park
Dale Britton
Computer Science III
12/6/2021
*/

import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
  
  // variable declarations
  static final int SCREEN_WIDTH = 600;
  static final int SCREEN_HEIGHT = 600;
  static final int UNIT_SIZE = 25;
  static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
  static final int DELAY = 75;
  final int x[] = new int[GAME_UNITS];
  final int y[] = new int[GAME_UNITS];
  int bodyParts = 6;
  int applesEaten;
  int appleX;
  int appleY;
  char direction = 'R';
  boolean running = false;
  Timer timer;
  Random random;

  // creates a game window
  GamePanel(){
    random = new Random();
    this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    this.setBackground(Color.white);
    this.setFocusable(true);
    this.addKeyListener(new MyKeyAdapter());
    startGame();
  }
  
  // creates a timer when the game is started so the snake moves every delay
  public void startGame() {
    newApple();
    running = true;
    timer = new Timer(DELAY, this);
    timer.start();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    draw(g);
  }

  // assigns colors/fonts to various components of game
  public void draw(Graphics g){
    if(running){
      g.setColor(Color.green);
      g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

      for(int i = 0; i < bodyParts; i++){
        if(i==0){
          g.setColor(Color.black);
          g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
        else{
          Color green = new Color(45, 180, 0);
          g.setColor(green);
          g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
      }
      g.setColor(Color.red);
      g.setFont(new Font("Ink Free", Font.BOLD, 40));
      FontMetrics metrics = getFontMetrics(g.getFont());
      g.drawString("score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("score: " + applesEaten)) / 2, g.getFont().getSize());
      
    } else {
      gameOver(g);
    }

  }

  // displays new apples on the screen
  public void newApple() {
    appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
    appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
  }
      
  // moves the snake based on the key that was pressed
  public void move() {
    for(int i = bodyParts; i > 0; i--) {
      x[i] = x[i - 1];
      y[i] = y[i - 1];
    }
    switch (direction) {
      case 'U':
        y[0] = y[0] - UNIT_SIZE;
        break;
      case 'D':
        y[0] = y[0] + UNIT_SIZE;
        break;
      case 'L':
        x[0] = x[0] - UNIT_SIZE;
        break;
      case 'R':
        x[0] = x[0] + UNIT_SIZE;
        break;
    }
  }
  
  // checks if the snake ate the apple
  public void checkApple() {
    if((x[0] == appleX) && (y[0] == appleY)) {
      bodyParts++;
      applesEaten++;
      newApple();
    }
  }

  public void checkCollisions(){
    // checks if the head collides w/ its own body
    for(int i = bodyParts; i > 0; i--){
      if((x[0] == x[i]) && (y[0] == y[i])){
        running = false;
      } 
    }
    
    // checks to see if snake collides with left border
    if(x[0] < 0){
      running = false;
    }
    // checks to see if snake collides with right border
    if(x[0] > SCREEN_WIDTH){
      running = false;
    }
    // checks to see if snake collides with top border
    if(y[0] < 0){
      running = false;
    }
    // checks to see if snake collides with bottom border
    if(y[0] > SCREEN_HEIGHT){
      running = false;
    }

    if(!running){
      timer.stop();
    }
  }

  // displays whenever the game ends
  public void gameOver(Graphics g) {
    g.setColor(Color.red);
    g.setFont(new Font("Ink Free", Font.BOLD, 40));
    FontMetrics metrics1 = getFontMetrics(g.getFont());
    g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
    g.setColor(Color.red);
    g.setFont(new Font("Ink Free", Font.BOLD, 75));
    FontMetrics metrics2 = getFontMetrics(g.getFont());
    g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
  }
  
  // while the snake is still alive, perform the actions of the game
  @Override
  public void actionPerformed(ActionEvent e) {
    if(running) {
      move();
      checkApple();
      checkCollisions();
    }
    repaint();
  }
  
  public class MyKeyAdapter extends KeyAdapter{
    @Override
    // takes the pressed key and assigns it as the direction
    public void keyPressed (KeyEvent e){
      switch(e.getKeyCode()){
        case KeyEvent.VK_LEFT:
          if(direction != 'R'){
            direction = 'L';
          }
          break;
        case KeyEvent.VK_RIGHT:
          if(direction != 'L'){
            direction = 'R';
          }
          break;
        case KeyEvent.VK_UP:
          if(direction != 'D'){
            direction = 'U';
          }
          break;
        case KeyEvent.VK_DOWN:
          if(direction != 'U'){
            direction = 'D';
          }
          break;
      }
    }
  }
}
