import javax.swing.*;

/**
 * Created by eileen_chau on 3/16/17.
 */
public class Main extends JPanel{

    public static final int FRAMEWIDTH = 1000, FRAMEHEIGHT = 600;
    private World theWorld;

    public Main() {
        setSize(FRAMEWIDTH, FRAMEHEIGHT);
        theWorld = new World(FRAMEWIDTH, FRAMEHEIGHT);
        Missle m = new Missle(50, 50, 90, theWorld);
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
