package base;

public class Platform extends Object {

    private static Draw draw;
    private Vector color;
    private Vector velocity = Vector.zero();
    private Vector startPosition;
    private boolean fixed = true;
    private float maxDistance = 4f;

    public Platform(Vector position, Vector size,Draw draw, Vector color, boolean fixed, Vector velocity){
        super(position,size);
        this.draw = draw;
        this.color = color;
        this.fixed = fixed;
        this.startPosition = position;
        this.velocity = velocity;
    }

    public Vector getVelocity(){
        return this.velocity;
    }

    public void draw(){
        if(!fixed){
            position(position().add(velocity));
            if(Math.abs(position().x() - startPosition.x()) >= maxDistance || Math.abs(position().z () - startPosition.z()) >= maxDistance){
                velocity = velocity.multiply(-1);
            }
        }

        draw.setColor(color.x(),color.y(),color.z());
        draw.drawBox(position().x(),position().y(),position().z(),size().x(),size().y(),size().z());
    }

    public boolean isColliding(Vector a){
        boolean x = (Math.abs(a.x() - position().x()) <= size().x()*0.55f);
        boolean z = (Math.abs(a.z() - position().z()) <= size().z()*0.55f);
        return (x && z);
    }
}
