import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Random;

public class MyPanel extends JPanel implements ActionListener, KeyListener {

    //Objects appearance
    final int panelWidth = 600, panelHeight = 400, playerWidth = 20,  playerHeight = 60, wrongMargin = 5;
    double ballX = 300, ballY = 0, ballVelx = 4, ballVely =4, ballDiameter = 20;

    Color player1Color = java.awt.Color.black;
    Color player2Color = java.awt.Color.black;

    //Player attributes
    int player1y = panelWidth/2 ,player2y = panelWidth/2, player1vel = 0, player2vel = 0;
    int player1Score = 0, player2Score = 0, scoreSize = 90;
    String score = player1Score+"  "+player2Score;

    Timer timer;

    Random rand = new Random();
    
    MyPanel(){
        
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setBackground(Color.white);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
     
        timer = new Timer(1, this);
        timer.start();
    }


    public void paint(Graphics g){

        super.paint(g);

        Graphics2D middleField = (Graphics2D) g;
        middleField.fillRect(panelWidth/2, 0, 4, 600);

        Graphics2D circle = (Graphics2D) g;
        circle.fill(new Ellipse2D.Double(ballX, ballY, ballDiameter, ballDiameter));

        Graphics2D player1 = (Graphics2D) g;
        Graphics2D player2 = (Graphics2D) g;

        player1.setColor(player1Color);
        player1.fillRect(0, player1y, playerWidth, playerHeight);
        
        player2.setColor(player2Color);
        player2.fillRect(panelWidth-playerWidth, player2y, playerWidth, playerHeight);

        Font myFont = new Font ("Courier New", 1, scoreSize);
        Graphics2D stats = (Graphics2D) g;
        stats.setFont(myFont);
        stats.drawString(score,195,panelHeight/2);
    }

    //When a player score
    public void score(int player){
        if (player == 1){
            player1Score++;
            ballVelx = 5;
        }
        else {
            player2Score++;
            ballVelx = -5;
        }
        ballX = panelWidth/2;
        ballY = panelHeight/2;
        score = player1Score+"  "+player2Score;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Maxspeed for the ball
        if (ballVelx > 15){
            ballVelx = 15;
        }
        if (ballVelx < -15){
            ballVelx = -15;
        }
        
        //When the ball is hitting top or bottom
        if (ballY < 0 || ballY > panelHeight-ballDiameter){
            ballVely = -ballVely;
        }

        //When ball is hitting a player
        if ((ballX <= playerWidth) && (ballY + wrongMargin >= player1y && ballY <= player1y+playerHeight + wrongMargin)){
            ballVelx = -ballVelx*1.15;
            ballVely = rand.nextDouble(-5, 5);
        }
        if ((ballX >= panelWidth-ballDiameter-playerWidth) && (ballY + wrongMargin >= player2y && ballY <= player2y+playerHeight + wrongMargin)){
            ballVelx = -ballVelx*1.15;
            ballVely = rand.nextDouble(-5, 5);
        }

        if (ballX < playerWidth-wrongMargin*2){
            score(2);
        }
        if (ballX > panelWidth-playerWidth+wrongMargin*2){
            score(1);
        }

        player1y += player1vel;
        player2y += player2vel;
        ballX += ballVelx;
        ballY += ballVely;
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public void up(int playerNumber){
        if (playerNumber == 1){
            player1vel = -5;
        }
        else {
            player2vel = -5;
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            player2vel = -5;
        }
        if (code == KeyEvent.VK_DOWN) {
            player2vel = 5;
        }
        if (code == KeyEvent.VK_W) {
            player1vel = -5;
        }
        if (code == KeyEvent.VK_S) {
            player1vel = 5;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN) {
            player2vel = 0;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_S) {
            player1vel = 0;
        }
    }
}
