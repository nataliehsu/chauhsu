/**
 * Created by natalie_hsu on 4/3/17.
 */
public class Med extends Sprite{
    public Med(int x, int y, int dir){
        super(x, y, dir);
        setPic("med.png", SOUTH);
        setSpeed(15);
    }
}
