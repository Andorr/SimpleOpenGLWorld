package base;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main extends GLCanvas implements GLEventListener {

    private static GLCanvas canvas;

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    private static GL2 gl;
    private static GLU glu;
    private static GLUT glut;

    private static final float mapWidth = 30f;

    private Draw draw;
    private Human player;
    private ArrayList<Object> objects;
    private SpawnController sc;

    public Main (){
        this.addGLEventListener(this);
        this.addKeyListener(player);
    }

    public static void main(String[] args){
        canvas = new Main();
        canvas.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        JFrame frame = new JFrame();
        frame.add(canvas);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        FPSAnimator animator = new FPSAnimator(canvas,60,true);
        animator.start();
    }

    public void init(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor(0f,0f,0f,0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE_MINUS_SRC_ALPHA);
        glu = new GLU();
        glut = new GLUT();

        draw = new Draw(gl,glut);
        Body b = new Body(gl,draw);
        player = new Human(b,new Vector(0f,0.68f,0f),gl);
        canvas.addKeyListener(player);
        canvas.addMouseWheelListener(player.mouseListener());

        initializeEnvironment();
        sc = new SpawnController(draw,mapWidth*2,mapWidth*2f,gl,glut);
    }

    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void display(GLAutoDrawable glAutoDrawable) {
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        setCameraPos();
        gl.glColor3f(1f,1f,1f);

        gl.glPushMatrix();
        drawGround();

        player.update();
        player.checkCollisions(objects);
        sc.update();
        sc.checkCollision(player.getPosition());


        for(Object o : objects){
            o.draw();
        }
    }

    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int width, int height) {
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, (float)width/height, 1, 100.0); // fovy, aspect, zNear, zFar

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private void drawGround (){
        gl.glColor3f(1f,1f,1f);
        for(float i = -mapWidth; i <= mapWidth; i++){
            gl.glBegin(GL2.GL_LINES);
                gl.glVertex3f(i,0f,-mapWidth);
                gl.glVertex3f(i,0f,mapWidth);
            gl.glEnd();
        }

        for(float i = -mapWidth; i <= mapWidth; i++){
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex3f(-mapWidth,0f,i);
            gl.glVertex3f(mapWidth,0f,i);
            gl.glEnd();
        }
    }

    private void setCameraPos (){
        Vector cameraPos = player.cameraPos();
        glu.gluLookAt(cameraPos.x(),cameraPos.y(),cameraPos.z(),player.getX(), player.getY(),player.getZ(),0f,1f,0f);
    }

    private void initializeEnvironment(){
        objects = new ArrayList<Object> ();

        for(int i = -10; i < 10; i++){
            objects.add(new Sphere(new Vector(-20f,4f + 10f + i,-i*2f),new Vector(0.7f,0.7f,0.7f),draw,true));
        }

        objects.add(new Model(new Vector(0f,0f,15f),new Vector(1f,1f,1f), new Vector(0f,180f,0f), new Vector(0f,205/255f,0f),"models/Dragon",gl));
        objects.add(new Model(new Vector(30f,5f,30f), new Vector(1f,1f,1f),new Vector(0f,0f,0f), new Vector(0.5f,0.3f,0.5f),"models/Flag",gl));
        objects.add(new Model(new Vector(-30f,5f,30f), new Vector(1f,1f,1f),new Vector(0f,0f,0f), new Vector(0.5f,0.3f,0.5f),"models/Flag",gl));
        objects.add(new Model(new Vector(-30f,5f,-30f), new Vector(1f,1f,1f),new Vector(0f,0f,0f), new Vector(0.5f,0.3f,0.5f),"models/Flag",gl));
        objects.add(new Model(new Vector(30f,5f,-30f), new Vector(1f,1f,1f),new Vector(0f,0f,0f), new Vector(0.5f,0.3f,0.5f),"models/Flag",gl));
        objects.add(new Model(new Vector(5.5f,4f,-30f), new Vector(1f,1f,1f),new Vector(0f,0f,0f), new Vector(0.5f,0.3f,0.5f),"models/Flag",gl));
        objects.add(new Model(new Vector(-5.5f,4f,-30f), new Vector(1f,1f,1f),new Vector(0f,180f,0f), new Vector(0.5f,0.3f,0.5f),"models/Flag",gl));
        objects.add(new Model(new Vector(20f,3f,0f), new Vector(1f,1f,1f),new Vector(0f,-90f,0f), new Vector(0.7f,0.3f,0.1f),"models/Plane",gl));
        objects.add(new Model(new Vector(3f,0f,3f), new Vector(1f,1f,1f),new Vector(0f,0f,0f), new Vector(0.2f,0.2f,0.2f),"models/Rock",gl));
        objects.add(new Model(new Vector(20f,0f,-3f), new Vector(1f,1f,1f),new Vector(0f,0f,0f), new Vector(0.2f,0.2f,0.2f),"models/Rock",gl));
        objects.add(new Model(new Vector(-14f,0f,13f), new Vector(1f,1f,1f),new Vector(0f,0f,0f), new Vector(0.2f,0.2f,0.2f),"models/Rock",gl));
        objects.add(new Model(new Vector(-10f,0f,-12f), new Vector(1f,1f,1f),new Vector(0f,0f,0f), new Vector(0.2f,0.2f,0.2f),"models/Rock",gl));

        //Borders
        objects.add(new Box(new Vector(30f,2.5f,30f), new Vector(2f,5f,2f),draw,new Vector(0f,153f/255f,1f)));
        objects.add(new Box(new Vector(-30f,2.5f,30f), new Vector(2f,5f,2f),draw,new Vector(0f,153f/255f,1f)));
        objects.add(new Box(new Vector(-30f,2.5f,-30f), new Vector(2f,5f,2f),draw,new Vector(0f,153f/255f,1f)));
        objects.add(new Box(new Vector(30f,2.5f,-30f), new Vector(2f,5f,2f),draw,new Vector(0f,153f/255f,1f)));
        objects.add(new Box(new Vector(30f,1f,0f),new Vector(1f,2f,60f),draw, new Vector(0f,204f/255f,102f/255f)));
        objects.add(new Box(new Vector(-30f,1f,0f),new Vector(1f,2f,60f),draw, new Vector(0f,204f/255f,102f/255f)));
        objects.add(new Box(new Vector(0f,1f,30f),new Vector(60f,2f,1f),draw, new Vector(0f,204f/255f,102f/255f)));
        objects.add(new Box(new Vector(-17.5f,1f,-30f),new Vector(25f,2f,1f),draw, new Vector(0f,153f/255f,77f/255f)));
        objects.add(new Box(new Vector(17.5f,1f,-30f),new Vector(25f,2f,1f),draw, new Vector(0f,153f/255f,77f/255f)));
        objects.add(new Box(new Vector(-5f,2f,-30f),new Vector(2f,4f,2f),draw, new Vector(0f,153f/255f,1f)));
        objects.add(new Box(new Vector(5f,2f,-30f),new Vector(2f,4f,2f),draw, new Vector(0f,153f/255f,1f)));

        //Platforms

        for(int k = 0; k < 10; k += 2){
            Vector pos = new Vector(-5f + k*2,0.5f*k,-10f);
            objects.add(new Platform(pos,new Vector(2f,0.1f,2f),draw, new Vector(76f/255f,0f,153f/255f),true, Vector.zero()));
            pos = new Vector(13f,0.5f*k + 5f,-7f + k*2);
            objects.add(new Platform(pos,new Vector(2f,0.1f,2f),draw, new Vector(76f/255f,0f,153f/255f),false, new Vector(0.03f,0f,0f)));
            pos = new Vector(10f - k*2,0.5f*k + 10f,10f);
            objects.add(new Platform(pos,new Vector(2f,0.1f,2f),draw, new Vector(76f/255f,0f,153f/255f),true,Vector.zero()));
            pos = new Vector(-8f,0.5f*k + 15f,5f - k*2);
            objects.add(new Platform(pos,new Vector(2f,0.1f,2f),draw, new Vector(76f/255f,0f,153f/255f),false, new Vector(0.03f,0f,0f)));
        }

        objects.add(new Platform(new Vector(-8f,20.5f,-7f),new Vector(2f,0.1f,2f),draw,new Vector(1f,1f,51f/255f),true,Vector.zero()));
    }
}
