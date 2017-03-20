import java.util.ArrayList;

/**
 * Created by natalie_hsu on 3/16/17.
 */
public class Missle extends Sprite{

    private Sprite target;

    public Missle(int x, int y, int dir, World world) {
        super(x, y, dir, world);
        setPic("missile.png", NORTH);
        setSpeed(8);
        ArrayList<Sprite> sprites = world.getAllSprites();
        target = sprites.get((int) (Math.random() * sprites.size()));
        while (target.equals(this) && sprites.size() > 1) {
            target = sprites.get((int) (Math.random() * sprites.size()));
        }
    }

    @Override
    public void update(){
        super.update();
        int d = getWorld().getDirection(this.getLoc(), target.getLoc());
        setDir(d);
        if(!getTarget().equals(this) && getTarget().intersects(this)){
            getWorld().removeSprite(getTarget());
            ArrayList<Sprite> sprites = getWorld().getAllSprites();
            setTarget(sprites.get((int)(Math.random() * sprites.size())));
            while(getTarget().equals(this) && sprites.size()>1){
                setTarget(sprites.get((int)(Math.random() * sprites.size())));
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
