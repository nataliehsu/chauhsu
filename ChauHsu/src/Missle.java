import java.util.ArrayList;

/**
 * Created by natalie_hsu on 3/16/17.
 */
public class Missle extends Sprite{

    private Sprite target;

    public Missle(int x, int y, int dir, World world){
        super(x, y, dir, world);

    ArrayList<Sprite> sprites = world.getAllSprites();
    target = sprites.get((int)(Math.random() * sprites.size()));
        while(target.equals(this) && sprites.size()>1){
        target = sprites.get((int)(Math.random() * sprites.size()));
    }

    setPic("Tarantula.png ", NORTH);
    setSpeed(8);
}

    @Override
    public void update(){
        int d = getWorld().getDirection(this.getLoc(), target.getLoc());
        setDir(d);

        super.update();
    }

    public Sprite getTarget(){
        return target;
    }
    public void setTarget(Sprite newTarget){
        target = newTarget;
    }

}
