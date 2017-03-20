/**
 * Created by natalie_hsu on 3/20/17.
 */
public class Aliens extends Sprite {
    public Aliens(int x, int y, int dir, World world){
        super(x, y, dir, world);
        setPic("alien.png", NORTH);
        setSpeed(0);
    }

}
