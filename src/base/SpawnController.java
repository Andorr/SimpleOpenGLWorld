package base;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SpawnController {

    private static Draw draw;
    private static GL2 gl;
    private static GLUT glut;

    private int boxCount = 0;
    private final float WIDTH;
    private final float HEIGHT;
    private final float START_TIME = 90f;

    private long startMillies;
    private int score = 0;

    private ArrayList<Object> objects;

    public SpawnController(Draw draw, float mapWidth, float mapHeight, GL2 gl, GLUT glut){
        this.draw = draw;
        this.WIDTH = mapWidth;
        this.HEIGHT = mapHeight;
        this.objects = new ArrayList<Object> ();
        this.startMillies = System.currentTimeMillis();
        this.gl = gl;
        this.glut = glut;
    }

    private void initializeNewBox (){
        float x = (float)(Math.random()*(WIDTH - 2f) - WIDTH/2);
        float z = (float)(Math.random()*(WIDTH - 2f) - WIDTH/2);
        float sizeX = (float)(Math.random()*(2.5f) + 1.5f);
        float sizeY = (float)(Math.random()*(2.5f) + 1.5f);
        float sizeZ = (float)(Math.random()*(2.5f) + 1.5f);
        Vector pos = new Vector(x,sizeY/2,z);
        Vector scale = new Vector(sizeX,sizeY,sizeZ);
        objects.add(new Box(pos,scale,draw,draw.randomColor()));
    }

    private float calculateTimeLeft(){
        return START_TIME - ((System.currentTimeMillis() - startMillies)/1000f);
    }

    public String time(){
        float timeLeft = calculateTimeLeft();
        if(timeLeft <= 0){return "00:00";}
        int min = (int)(timeLeft/60);
        int sec = (int)(timeLeft%60);
        return String.valueOf(min) + ":" + String.valueOf(sec);
    }

    public void update(){
        if(calculateTimeLeft() <= 0){
            objects.clear();
            return;
        }
        else if(objects.size() == 0){
            boxCount++;
            for(int i = 0; i < boxCount; i++){
                initializeNewBox();
            }
        }

        for(Object o : objects){
            Vector pos = o.position();
            pos.y(o.size().y()/2);
            o.position(pos);
            o.draw();
        }

    }

    public void checkCollision(Vector position){
        RenderString(position.x() + 0.5f,position.y() + 2f,position.z(),5,time());
        RenderString(position.x() + 0.5f,position.y() + 3f,position.z(),5,"Score: " + String.valueOf(score));
        for(Object o : objects){
            if(o.isColliding(position)){
                o.size(o.size().multiply(0.99f));
                if(o.size().magnitude() <= 0.6f){
                    objects.remove(o);
                    score++;
                }
                return;
            }
        }
    }

    private void RenderString(float x, float y, float z, int font, String text)
    {
        gl.glColor3f(1f, 215f/255f,0f);
        gl.glRasterPos3f(x,y,z);
        for(int i = 0; i < text.length(); i++){
            glut.glutBitmapCharacter(font,text.charAt(i));
        }
    }
}
