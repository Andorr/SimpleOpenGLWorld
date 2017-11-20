package base;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class Draw {

    private static GL2 gl;
    private static GLUT glut;

    private static float currentColor[] = new float[4];

    public Draw(GL2 gl, GLUT glut){
        this.gl = gl;
        this.glut = glut;
    }

    public void setColor(float r, float g, float b){
        gl.glColor3f(r,g,b);
    }

    public void setColor(float r, float g, float b,float a){
        gl.glColor4f(r,g,b,a);
    }

    public void drawBox (float x,float y, float z, float width,float height, float length){
        width = width*0.5f;
        height = height*0.5f;
        length = length*0.5f;

        gl.glGetFloatv(GL2.GL_CURRENT_COLOR, currentColor,0);

        //Front
        gl.glColor3f(currentColor[0] - 0.4f,currentColor[1] - 0.4f,currentColor[2] - 0.4f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(x-width,y-height,z+length); //Bottom Left
        gl.glVertex3f(x+width,y-height,z+length); //Bottom Right
        gl.glVertex3f(x+width,y+height,z+length); //Top Right
        gl.glVertex3f(x-width,y+height,z+length); //Top Left
        gl.glEnd();

        //Back
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(x-width,y-height,z-length); //Bottom Left
        gl.glVertex3f(x+width,y-height,z-length); //Bottom Right
        gl.glVertex3f(x+width,y+height,z-length); //Top Right
        gl.glVertex3f(x-width,y+height,z-length); //Top Left
        gl.glEnd();

        //Left Side
        gl.glColor3f(currentColor[0],currentColor[1],currentColor[2]);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(x+width,y-height,z+length); //Bottom Left
        gl.glVertex3f(x+width,y+height,z+length); //Top Left
        gl.glVertex3f(x+width,y+height,z-length); //Top Right
        gl.glVertex3f(x+width,y-height,z-length); //Bottom Right
        gl.glEnd();

        //Right Side
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(x-width,y-height,z+length); //Bottom Left
        gl.glVertex3f(x-width,y+height,z+length); //Top Left
        gl.glVertex3f(x-width,y+height,z-length); //Top Right
        gl.glVertex3f(x-width,y-height,z-length); //Bottom Right
        gl.glEnd();

        //Top
        gl.glColor3f(currentColor[0] - 0.3f,currentColor[1] - 0.3f,currentColor[2] - 0.3f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(x-width,y+height,z-length); //Bottom Left
        gl.glVertex3f(x+width,y+height,z-length); //Bottom Right
        gl.glVertex3f(x+width,y+height,z+length); //Top Right
        gl.glVertex3f(x-width,y+height,z+length); //Top Left
        gl.glEnd();

        //Bottom
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(x-width,y-height,z-length); //Bottom Left
        gl.glVertex3f(x+width,y-height,z-length); //Bottom Right
        gl.glVertex3f(x+width,y-height,z+length); //Top Right
        gl.glVertex3f(x-width,y-height,z+length); //Top Left
        gl.glEnd();
    }

    public void drawAnchorBox (float x,float y, float z, float width,float height, float length){

        length = length*0.5f;

        gl.glGetFloatv(GL2.GL_CURRENT_COLOR, currentColor,0);

        //Front
        gl.glColor3f(currentColor[0] - 0.4f,currentColor[1]-0.4f,currentColor[2]-0.4f);
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex3f(x,y,z+length); //Bottom Left
            gl.glVertex3f(x+width,y,z+length); //Bottom Right
            gl.glVertex3f(x+width,y-height,z+length); //Top Right
            gl.glVertex3f(x,y-height,z+length); //Top Left
        gl.glEnd();

        //Back
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex3f(x,y,z-length); //Bottom Left
            gl.glVertex3f(x+width,y,z-length); //Bottom Right
            gl.glVertex3f(x+width,y-height,z-length); //Top Right
            gl.glVertex3f(x,y-height,z-length); //Top Left
        gl.glEnd();

        //Left Side
        gl.glColor3f(currentColor[0],currentColor[1],currentColor[2]);
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex3f(x+width,y,z+length); //Bottom Left
            gl.glVertex3f(x+width,y-height,z+length); //Top Left
            gl.glVertex3f(x+width,y-height,z-length); //Top Right
            gl.glVertex3f(x+width,y,z-length); //Bottom Right
        gl.glEnd();

        //Right Side
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex3f(x,y,z+length); //Bottom Left
            gl.glVertex3f(x,y-height,z+length); //Top Left
            gl.glVertex3f(x,y-height,z-length); //Top Right
            gl.glVertex3f(x,y,z-length); //Bottom Right
        gl.glEnd();

        //Top
        gl.glColor3f(currentColor[0] - 0.3f,currentColor[1] - 0.3f,currentColor[2]-0.3f);
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex3f(x,y-height,z-length); //Bottom Left
            gl.glVertex3f(x+width,y-height,z-length); //Bottom Right
            gl.glVertex3f(x+width,y-height,z+length); //Top Right
            gl.glVertex3f(x,y-height,z+length); //Top Left
        gl.glEnd();

        //Bottom
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex3f(x,y,z-length); //Bottom Left
            gl.glVertex3f(x+width,y,z-length); //Bottom Right
            gl.glVertex3f(x+width,y,z+length); //Top Right
            gl.glVertex3f(x,y,z+length); //Top Left
        gl.glEnd();
    }

    public void drawSphere(Vector position, Vector size){
        gl.glPushMatrix();
        gl.glTranslatef(position.x(),position.y(),position.z());
        gl.glScalef(size.x(),size.y(),size.z());
        glut.glutSolidSphere(1,10,10);
        gl.glPopMatrix();
    }

    public void drawQuad(Vector position, Vector size){
        float w = size.x()*0.5f;
        float l = size.z()*0.5f;

        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex3f(position.x() - w,position.y(),position.z() - l);
            gl.glVertex3f(position.x() - w,position.y(),position.z() + l);
            gl.glVertex3f(position.x() + w,position.y(),position.z() + l);
            gl.glVertex3f(position.x() + w,position.y(),position.z() - l);
        gl.glEnd();
    }

    public void drawVector(Vector v){
        gl.glBegin(GL2.GL_LINES);
            gl.glVertex3f(0f,0f,0f);
            gl.glVertex3f(v.x(),v.y(),v.z());
        gl.glEnd();
        gl.glBegin(GL2.GL_POINTS);
            gl.glVertex3f(v.x(),v.y(),v.z());
        gl.glEnd();
    }

    public void drawVector(Vector from, Vector to){
        gl.glBegin(GL2.GL_LINES);
            gl.glVertex3f(from.x(),from.y(),from.z());
            gl.glVertex3f(from.x () + to.x(),from.y() + to.y(),from.z() + to.z());
        gl.glEnd();

        gl.glPointSize(10);
        gl.glBegin(GL2.GL_POINTS);
            gl.glVertex3f(from.x () + to.x(),from.y() + to.y(),from.z() + to.z());
        gl.glEnd();
    }

    public void drawPoint(Vector position){
        gl.glBegin(GL2.GL_POINTS);
            gl.glVertex3f(position.x(),position.y(),position.z());
        gl.glEnd();
    }

    public static Vector randomColor (){
        float r = (float)Math.random()*155 + 100;
        float g = (float)Math.random()*155 + 100;
        float b = (float)Math.random()*155 + 100;
        return new Vector(r/255f,g/255f,b/255f);
    }
}
