package base;

public class Vector {

    private float x,y,z,w;
    private int length;

    public Vector(float x,float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
        length = 3;
    }

    public Vector(float x,float y, float z,float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        length = 4;
    }

    public Vector(float[] points){
        if(points.length > 0){
            this.x = points[0];
            this.y = points[1];
            this.z = points[2];
            length = 3;
        }
        else{
            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.w = 0;
            length = 4;
        }

        if(points.length > 3){
            this.w = points[3];
            length = 4;
        }
    }

    public float magnitude(){
        return (float)Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }

    public float w() { return w; }

    public void x(float value){
        x = value;
    }

    public void y(float value){
        y = value;
    }

    public void z(float value){
        z = value;
    }

    public void w(float value) { w = value; length = 4;}

    public float[] getValues(){
        if(length == 3){
            float[] values = {x,y,z};
            return values;
        }
        else{
            float[] values = {x,y,z,w};
            return values;
        }
    }

    public Vector multiply(Vector b){
        return new Vector(x*b.x, y*b.y,z*b.z);
    }

    public Vector multiply(float value){
        return new Vector(x*value,y*value,z*value);
    }

    public Vector add(Vector b){
        return new Vector(x + b.x,y + b.y,z+b.z);
    }

    public Vector sub(Vector b){
        return new Vector(x -b.x, y - b.y, z - b.z);
    }

    public Vector normalized(){
        float length = this.magnitude();
        return new Vector(x/length,y/length,z/length);
    }

    public float angle(Vector b){
        float lengthA = this.magnitude();
        float lengthB = b.magnitude();
        return (float)Math.acos((this.add(b).magnitude())/lengthA*lengthB);
    }

    public float angle(){
        return angle(new Vector(0f,0f,1f));
    }

    public Vector rotateByY(float angle){
        float rotX = (float)(x*Math.cos(angle)+ z*Math.sin(angle));
        float rotZ = (float)(-x*Math.sin(angle) + z*Math.cos(angle));
        return new Vector(rotX,y,rotZ);
    }

    public static Vector zero(){
        return new Vector(0,0,0);
    }

    public String toString(){
        if(length == 3) return "[" + x + "," + y + "," + z + "]";
        return "[" + x + "," + y + "," + z + "," + w + "]";

    }
}
