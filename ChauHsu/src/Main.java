import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by eileen_chau on 3/16/17.
 */
public class Main extends JPanel{

    public static final int FRAMEWIDTH = 1000, FRAMEHEIGHT = 600;
    private World theWorld;
    private Timer timer;
    private boolean[] keys;
    private ArrayList<Aliens> aliens;
    private ArrayList<Missile> missiles;
    private Spaceship spaceShip;
    private int count, maxCount, countM, maxM, lives, kills, intersect, intersectsM;
    private boolean dead, alienDead;

    public Main() {
        setSize(FRAMEWIDTH, FRAMEHEIGHT);
        theWorld = new World(FRAMEWIDTH, FRAMEHEIGHT);
        count = 0;
        maxCount = 100;
        countM = 0;
        maxM = 50;
        aliens = new ArrayList<Aliens>();
        missiles = new ArrayList<Missile>();
        spaceShip = new Spaceship(100, 100, 90, theWorld);
        lives = 3;
        kills = 0;
        dead = false;
        alienDead = false;

        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                count++;
                int randX = (int)(Math.random()*(FRAMEWIDTH - 100)) + 50;
                int randY = (int)(Math.random()*(FRAMEHEIGHT - 100)) + 50;
                if(count >= maxCount){
                    aliens.add(new Aliens(randX, randY, 90, theWorld));
                    count = 0;
                }
                countM++;
                if(countM >= maxM && aliens.size() > 0){
                    int i = (int)(Math.random() * aliens.size());
                    Sprite s = aliens.get(i);
                    missiles.add(new Missile(s.getLoc().x, s.getLoc().y, 90, theWorld, spaceShip));
                    countM = 0;
                }
                for (int m = 0; m < missiles.size(); m++) {
                    for (int i = 0; i < aliens.size(); i++) {
                        missiles.get(m).update();
                        if (missiles.get(m).intersects(spaceShip)) {
                            intersectsM = m;
                            dead = true;
                        } else if (missiles.get(m).intersects(aliens.get(i)) && count > 80) {
                            alienDead = true;
                            intersect = i;
                            intersectsM = m;
                        }
                    }
                }
                if(dead){
                    lives--;
                    Point p = new Point(100, 100);
                    spaceShip.setLoc(p);
                    missiles.remove(intersectsM);
                    dead = false;
                }
                if(alienDead){
                    kills++;
                    aliens.remove(intersect);
                    missiles.remove(intersectsM);
                    alienDead = false;
                }
                repaint();
            }
        });
        timer.start();

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                int code = keyEvent.getKeyChar();
                if(code == 'r'){
                }
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                spaceShip.setLoc(mouseEvent.getPoint());
            }
        });

    }

    //Our paint method.
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        for (Aliens s: aliens){
            s.draw(g2);
        }
        for(Missile m: missiles){
            m.draw(g2);
        }
        spaceShip.draw(g2);

        g2.setColor(Color.BLACK);
        g2.fillRect(10, 0, 150, 60);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Courier", Font.PLAIN, 20));
        g2.drawString("KILLS: " + kills, 20, 20);
        g2.setFont(new Font("Courier", Font.PLAIN, 20));
        g2.drawString("LIVES: " + lives, 20, 40);

    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Dodge!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, FRAMEWIDTH, FRAMEHEIGHT + 22); //(x, y, w, h) 22 due to title bar.

        Main panel = new Main();
        panel.setSize(FRAMEWIDTH, FRAMEHEIGHT);

        panel.setFocusable(true);
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
        window.setResizable(false);
    }
}
