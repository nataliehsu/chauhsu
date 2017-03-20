/**
 * Created by natalie_hsu on 3/20/17.
 */
public class Spaceship extends Sprite {
    public Spaceship(int x, int y, int dir, World world){
        super(x, y, dir, world);
        setPic("spaceship.png", EAST);
        setSpeed(8);
    }
}
