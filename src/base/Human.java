package base;

import com.jogamp.opengl.GL2;

import java.awt.event.*;
import java.util.ArrayList;

public class Human implements KeyListener {

    private Body body;
    private GL2 gl;

    private float movementSpeed = 0.1f;
    private float rotationSpeed = 0.1f;
    private static final float GRAVITY = 0.0014f;

    private Vector position;
    private Vector velocity = new Vector(0f,0f,movementSpeed);
    private Vector rotation = Vector.zero();


    private int rotationDirection = 1;

    private boolean isWalking;
    private boolean isRotating;
    private boolean isJumping;
    private boolean isSprinting = false;
    private boolean wasPaddling = false;
    private boolean rotatingCamera = false;

    private float cameraZoom = 6f;
    private float cameraRotation = 0f;
    private float cameraRotationSpeed = 0.015f;
    private float minY = Body.minY();

    public Human(Body body,Vector position , GL2 gl){
        this.body = body;
        this.position = position;
        this.gl = gl;
    }

    public float getX() {return position.x();}

    public float getY() {return position.y();}

    public float getZ() {return position.z();}

    public Vector getVelocity(){return velocity;}
    public Vector getPosition(){return position;}

    public void update(){
        if(isWalking){
            position = position.add(velocity);
            body.walkAnimation();
        }
        if(isRotating){
            rotation.y(rotation.y() + rotationSpeed*rotationDirection);
            velocity = velocity.rotateByY(rotationSpeed*rotationDirection);
        }

        if(isJumping){
            velocity.y(velocity.y() - GRAVITY);
            position.y(position.y() + velocity.y());
            if(position.y() <= minY){
                position.y(minY);
                velocity.y(0f);
                isJumping = false;
            }
        }

        body.drawBody(position.x(),position.y(),position.z(),rotation.y());

        gl.glColor3f(1f,0f,0f);
        body.draw().drawVector(position);
        gl.glColor3f(0f,1f,0f);
        body.draw().drawVector(position,velocity.multiply(200));
        gl.glColor3f(0f,0f,1f);
        body.draw().drawVector(rotation);
    }

    public void checkCollisions(ArrayList<Object> objects){
        float maxY = Body.minY();
        Platform curO = null;
        for(Object o : objects){
            if(o instanceof Platform){
                if(o.isColliding(position) && position.y() >= o.position().y() + Body.minY()){
                    if(maxY < o.position().y() + Body.minY()){
                        maxY = o.position().y() + Body.minY();
                        curO = (Platform)o;
                    }
                }
            }
        }
        if(curO != null && position.y() <= curO.position().y() + Body.minY()){
            position = position.add(curO.getVelocity());
        }
        minY = maxY;
        isJumping = true;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W && !isWalking){
            isWalking = true;
            if(wasPaddling){
                float y = velocity.y();
                velocity = velocity.multiply(-1).normalized().multiply(movementSpeed);
                velocity.y(y);
                wasPaddling = false;
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_S && !isWalking){
            isWalking = true;
            if(!wasPaddling){
                float y = velocity.y();
                velocity = velocity.multiply(-1).normalized().multiply(movementSpeed*0.5f);
                velocity.y(y);
                wasPaddling = true;
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_D){
            isRotating = true;
            rotationDirection = -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_A){
            isRotating = true;
            rotationDirection = 1;
        }
        else if(velocity.y() == 0f && e.getKeyCode() == KeyEvent.VK_SPACE){
            velocity.y(0.05f);
            isJumping = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_P){
            rotatingCamera = !rotatingCamera;
        }
        else if(e.getKeyCode() == KeyEvent.VK_SHIFT && !isSprinting){
            System.out.println("shift");
            isSprinting = true;
            velocity = velocity.multiply(2);
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S){
            isWalking = false;
            body.resetWalkAnim();
        }
        else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A) {
            isRotating = false;
        }
        else if(e.getKeyCode() == KeyEvent.VK_SHIFT && isSprinting && velocity.y () == 0f){
            velocity = velocity.multiply(0.5f);
            isSprinting = false;
        }
    }

    public Vector getForward(){
        return new Vector(0f,0f,1f).rotateByY(rotation.y());
    }

    public Vector cameraPos (){
        if(!rotatingCamera) {
            Vector cameraPos = position.sub(getForward().normalized().multiply(6 + cameraZoom));
            cameraPos.z(cameraPos.z());
            cameraPos.y(position.y() + cameraZoom -2f);
            return cameraPos;
        }
        else{
            Vector cameraPos = position;
            Vector offset = new Vector(0f, cameraZoom,cameraZoom);
            offset = offset.rotateByY(cameraRotation);
            cameraRotation += cameraRotationSpeed;
            return cameraPos.add(offset);
        }
    }

    private class MouseController implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if(e.getWheelRotation() > 0){
                cameraZoom = (cameraZoom == 24f)? 24f : cameraZoom + 1f;
            }
            else {
                cameraZoom = (cameraZoom == 2f)? 2f : cameraZoom -1f;
            }
        }
    }

    public MouseWheelListener mouseListener(){
        return new MouseController();
    }
}
