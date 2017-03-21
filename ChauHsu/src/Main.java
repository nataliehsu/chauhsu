import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private int count;
    private int maxCount;

    public Main() {
        setSize(FRAMEWIDTH, FRAMEHEIGHT);
        theWorld = new World(FRAMEWIDTH, FRAMEHEIGHT);
        count = 0;
        maxCount = 100;
        aliens = new ArrayList<Aliens>();
        missiles = new ArrayList<Missile>();
        spaceShip = new Spaceship(100, 100, 270, theWorld);

        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(count >= maxCount){
                    int randX = (int)(Math.random()*FRAMEWIDTH) - 50;
                    int randY = (int)(Math.random()*FRAMEHEIGHT) - 50;
                    aliens.add(new Aliens(randX, randY, 90, theWorld));
                }
                for (Aliens s: aliens){
                    s.update();
                }
                for(Missile m: missiles){
                    m.update();
                }
                spaceShip.update();

                repaint();
            }
        });
        timer.start();

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                int code = keyEvent.getKeyChar();
                if(code == 'r'){
                    //restart
                }
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
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
