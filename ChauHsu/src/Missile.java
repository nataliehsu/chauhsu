import java.util.ArrayList;

/**
 * Created by natalie_hsu on 3/16/17.
 */
public class Missile extends Sprite{
    private Sprite target;
    public Missile(int x, int y, int dir, World world, Sprite player) {
        super(x, y, dir, world);
        setPic("missile.png", NORTH);
        setSpeed(5);
        System.out.println("  ");
        ArrayList<Sprite> sprites = world.getAllSprites();
        target = player;
        while (target.equals(this) && sprites.size() > 1) {
            target = player;
        }
    }

    @Override
    public void update(){
        super.update();
        int d = getWorld().getDirection(this.getLoc(), target.getLoc());
        setDir(d);
            if (!getTarget().equals(this) && getTarget().intersects(this)) {
                getWorld().removeSprite(target);
                ArrayList<Sprite> sprites = getWorld().getAllSprites();
                setTarget(target);
                while (getTarget().equals(this) && sprites.size() > 1) {
                    setTarget(target);
                }
            }
    }

    public Sprite getTarget(){
        return target;
    }
    public void setTarget(Sprite newTarget){
        target = newTarget;
    }

}
