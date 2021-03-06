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
    private ArrayList<Med> firstAid;
    private Spaceship spaceShip;
    private int count, maxCount, countM, maxM, lives, kills, intersect, intersectsM, level, originalSpeed, intersectMed, selfKillPrevent;
    private boolean dead, alienDead, life;

    public Main() {
        setSize(FRAMEWIDTH, FRAMEHEIGHT);
        theWorld = new World(FRAMEWIDTH, FRAMEHEIGHT);
        count = 0;
        maxCount = 100;
        countM = 0;
        maxM = 50;
        aliens = new ArrayList<Aliens>();
        missiles = new ArrayList<Missile>();
        firstAid = new ArrayList<Med>();
        spaceShip = new Spaceship(100, 100, 90, theWorld);
        lives = 3;
        kills = 0;
        dead = false;
        alienDead = false;
        life = false;
        level = 1;
        originalSpeed = 5;
        selfKillPrevent = 20;

        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                count++;
                int randX = (int)(Math.random()*(FRAMEWIDTH - 300)) + 150;
                int randY = (int)(Math.random()*(FRAMEHEIGHT - 300)) + 150;
                if(count >= maxCount){
                    aliens.add(new Aliens(randX, randY, 90, theWorld));
                    count = 0;
                    countM = 0;
                }
                countM++;
                if(countM >= maxM && aliens.size() > 0){
                    int i = (int)(Math.random() * aliens.size());
                    Sprite s = aliens.get(i);
                    Missile m = new Missile(s.getLoc().x, s.getLoc().y, 90, theWorld, spaceShip);
                    missiles.add(m);
                    if(level > 1) {
                        originalSpeed += level;
                    }
                    if(level >= 10){
                        countM = 5;
                    }
                    m.setSpeed(originalSpeed);
                    originalSpeed = 5;

                    countM = 0;
                }
                for (int m = 0; m < missiles.size(); m++) {
                    missiles.get(m).update();
                    for (int i = 0; i < aliens.size(); i++) {
                        if (missiles.get(m).intersects(spaceShip)) {
                            intersectsM = m;
                            dead = true;
                        } else if (missiles.get(m).intersects(aliens.get(i)) && countM > selfKillPrevent) {
                            alienDead = true;
                            intersect = i;
                            intersectsM = m;
                        }
                    }
                }
                if(dead){
                    lives--;
                    missiles.remove(intersectsM);
                    dead = false;
                }
                if(alienDead) {
                    kills++;
                    aliens.remove(intersect);
                    missiles.remove(intersectsM);
                    alienDead = false;
                }
                if(aliens.size() == 0 && kills > 0){
                    level++;
                    count = 100;
                    if(maxCount - 20 >= 20) {
                        maxCount -= 20;
                    }
                    if(maxM - 20 >= 10) {
                        maxM -= 20;
                    }
                    missiles.clear();
                    Med m = new Med(randX, 0, 270);
                    firstAid.add(m);
                }
                if(level > 2){
                    selfKillPrevent = 8;
                }
                for (int i = 0; i < firstAid.size(); i++) {
                    firstAid.get(i).update();
                    if(firstAid.get(i).intersects(spaceShip)){
                        life = true;
                        intersectMed = i;
                    }
                }
                if(life){
                    lives++;
                    life = false;
                    firstAid.remove(intersectMed);
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
                    restart();
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
        theWorld.setBackground("bg.png");
        theWorld.drawSprites(g2);
        for (Aliens s: aliens){
            s.draw(g2);
        }
        for(Missile m: missiles){
            m.draw(g2);
        }
        for(Med med: firstAid){
            med.draw(g2);
        }
        spaceShip.draw(g2);

        g2.setColor(Color.BLACK);
        g2.fillRect(10, 0, 150, 60);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Courier", Font.PLAIN, 20));
        g2.drawString("KILLS: " + kills, 20, 20);
        g2.setFont(new Font("Courier", Font.PLAIN, 20));
        g2.drawString("LIVES: " + lives, 20, 40);
        g2.drawString("LEVEL: " + level, 20, 60);
        if(lives == 0){
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Courier", Font.PLAIN, 100));
            g2.drawString("GAME OVER", 200, 300);
            g2.setFont(new Font("Courier", Font.PLAIN, 70));
            g2.drawString("PRESS [r] TO RESTART", 85, 400);
            g2.setFont(new Font("Courier", Font.PLAIN, 20));
            g2.drawString("KILLS: " + kills, 20, 20);
            g2.setFont(new Font("Courier", Font.PLAIN, 20));
            g2.drawString("LIVES: " + lives, 20, 40);
            g2.drawString("LEVEL: " + level, 20, 60);
            timer.stop();
        }

    }

    public void restart(){
        count = 0;
        maxCount = 100;
        countM = 0;
        maxM = 50;
        if(lives == 0) {
            spaceShip = new Spaceship(100, 100, 90, theWorld);
        }
        selfKillPrevent = 20;
        lives = 3;
        kills = 0;
        level = 0;
        originalSpeed = 5;
        dead = false;
        alienDead = false;
        life = false;
        level = 1;
        originalSpeed = 5;
        selfKillPrevent = 20;
        timer.restart();
        aliens.clear();
        missiles.clear();

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
