package base;

public class Object {

    private Vector position;
    private Vector size;

    public Object (Vector position,Vector size){
        this.position = position;
        this.size = size;
    }

    public Vector position(){
        return position;
    }

    public Vector size(){
        return size;
    }

    public void size(Vector a){
        size = a;
    }

    public void position(Vector position){
        this.position = position;
    }

    public void draw(){

    }

    public boolean isColliding(Vector a){
        boolean x = (Math.abs(a.x() - position.x()) <= size.x());
        //boolean y = (Math.abs(a.y() - position.y()) <= size.y());
        boolean z = (Math.abs(a.z() - position.z()) <= size.z());
        return (x && z);
    }


}
