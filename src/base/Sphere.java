package base;

public class Sphere extends Object {

    private static Draw draw;
    private Vector color;
    private boolean isBouncing;
    private Vector velocity;
    private static final float GRAVITY = 0.01f;

    public Sphere(Vector position, Vector size, Draw draw,boolean isBouncing) {
        super(position, size);
        this.draw = draw;
        color = new Vector(1f,1f,1f);
        this.isBouncing = isBouncing;
        this.velocity = new Vector(0f,0f,0f);
    }

    public Sphere(Vector position, Vector size, Draw draw,Vector color, boolean isBouncing) {
        super(position, size);
        this.draw = draw;
        this.color = color;
        this.isBouncing = isBouncing;
    }

    public void draw(){
        draw.setColor(color.x(),color.y(),color.z(),0.5f);
        draw.drawSphere(position(), size());

        if(isBouncing){
            update();
        }
    }

    private void update(){
        position(position().add(velocity));
        if(position().y() - size().y() <= 0f){
            velocity.y(0.3f);
            randomizeColor();
            position().y(size().y());
        }
        else{
            velocity.y(velocity.y() - GRAVITY);
        }
    }

    private void randomizeColor(){
        color.x(((float)Math.random()*155 + 100)/255f);
        color.y(((float)Math.random()*155 + 100)/255f);
        color.z(((float)Math.random()*155 + 100)/255f);
    }
}
