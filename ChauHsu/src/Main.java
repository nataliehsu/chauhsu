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
    private ArrayList<Missle> missles;
    private Spaceship spaceShip;

    public Main() {
        setSize(FRAMEWIDTH, FRAMEHEIGHT);
        theWorld = new World(FRAMEWIDTH, FRAMEHEIGHT);
        aliens = new ArrayList<Aliens>();
        missles = new ArrayList<Missle>();
        spaceShip = new Spaceship(100, 100, 270, theWorld);

        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for (Aliens s: aliens){
                    s.update();
                }
                for(Missle m: missles){
                    m.update();
                }
                spaceShip.update();

                if(keys[KeyEvent.VK_W]){
                    keys[KeyEvent.VK_W] = false; //probably.
                }
                if(keys[KeyEvent.VK_A]){
                    keys[KeyEvent.VK_A] = false; //probably.
                }
                if(keys[KeyEvent.VK_D]){
                    keys[KeyEvent.VK_D] = false; //probably.
                }
                if(keys[KeyEvent.VK_S]){
                    keys[KeyEvent.VK_S] = false;
                }
                if(keys[KeyEvent.VK_R]){
                }

                repaint();
            }
        });
        timer.start();

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                keys[keyEvent.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                keys[keyEvent.getKeyCode()] = false;
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
        for(Missle m: missles){
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
