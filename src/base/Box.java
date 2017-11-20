package base;

public class Box extends Object{

    private static Draw draw;
    private Vector color;

    public Box(Vector position, Vector size, Draw draw) {
        super(position, size);
        this.draw = draw;
        color = new Vector(1f,1f,1f);
    }

    public Box(Vector position, Vector size, Draw draw,Vector color) {
        super(position, size);
        this.draw = draw;
        this.color = color;
    }

    public void draw(){
        draw.setColor(color.x(),color.y(),color.z());
        draw.drawBox(position().x(),position().y(),position().z(),size().x(),size().y(),size().z());
    }
}
