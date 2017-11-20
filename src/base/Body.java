package base;

import com.jogamp.opengl.GL2;

public class Body {
    private static GL2 gl;
    private static Draw draw;

    private static final float BODY_WIDTH = 0.36f;
    private static final float BODY_HEIGHT = 0.54f;
    private static final float BODY_LENGTH = 0.27f;

    private static final float ARM_WIDTH = 0.18f;
    private static final float ARM_HEIGHT = 0.54f;
    private static final float ARM_LENGTH = 0.2f;

    private static final float LEG_WIDTH = 0.18f;
    private static final float LEG_HEIGHT = 0.54f;
    private static final float LEG_LENGTH = 0.27f;

    private static final float HEAD_WIDTH = 0.27f;
    private static final float HEAD_HEIGHT = 0.3f;
    private static final float HEAD_LENGTH = 0.27f;

    private float animRot = 0f;
    private static float animStrength = 3f;

    public Body(GL2 gl,Draw draw){
        this.gl = gl;
        this.draw = draw;
    }

    public Draw draw(){
        return draw;
    }

    public void drawBody(float x, float y, float z,float rotY){
        gl.glTranslatef(x,y,z);
        gl.glRotatef((float)Math.toDegrees(rotY),0f,1f,0f);
        gl.glPushMatrix();
        gl.glColor3f(1f,0f,0f);
        draw.drawBox(0f,0f,0f,BODY_WIDTH,BODY_HEIGHT,BODY_LENGTH); //Body

        //Right Arm
        gl.glTranslatef(0f,ARM_HEIGHT*0.5f,0f);
        gl.glRotatef(animRot,1f,0f,0f);
        gl.glColor3f(0.2f,0.2f,0.2f);
        draw.drawAnchorBox(-BODY_WIDTH*0.5f - ARM_WIDTH,0f,0f,ARM_WIDTH,ARM_HEIGHT,ARM_LENGTH);
        gl.glRotatef(-animRot,1f,0f,0f);
        gl.glTranslatef(0f,-ARM_HEIGHT*0.5f,0f);

        //Left Arm
        gl.glTranslatef(0f,ARM_HEIGHT*0.5f,0f);
        gl.glRotatef(-animRot,1f,0f,0f);
        gl.glColor3f(0.2f,0.2f,0.2f);
        draw.drawAnchorBox(BODY_WIDTH*0.5f,0f,0f,ARM_WIDTH,ARM_HEIGHT,ARM_LENGTH);
        gl.glRotatef(animRot,1f,0f,0f);
        gl.glTranslatef(0f,-ARM_HEIGHT*0.5f,0f);

        //Right Foot
        gl.glTranslatef(0f,- BODY_HEIGHT*0.5f,0f);
        gl.glRotatef(animRot,1f,0f,0f);
        gl.glColor3f(0f,0f,1f);
        draw.drawAnchorBox(0f,0f,0f,LEG_WIDTH,LEG_HEIGHT,LEG_LENGTH);
        gl.glRotatef(-animRot,1f,0f,0f);
        gl.glTranslatef(0f,BODY_HEIGHT*0.5f,0f);

        //Left Foot
        gl.glTranslatef(0f,- BODY_HEIGHT*0.5f,0f);
        gl.glColor3f(0f,1f,1f);
        gl.glRotatef(-animRot,1f,0f,0f);
        draw.drawAnchorBox(-LEG_WIDTH,0f,0f,LEG_WIDTH,LEG_HEIGHT,LEG_LENGTH);
        gl.glRotatef(animRot,1f,0f,0f);
        gl.glTranslatef(0f,BODY_HEIGHT*0.5f,0f);

        //Head
        gl.glColor3f(1f,1f,1f);
        draw.drawBox(0f,BODY_HEIGHT*0.5f + HEAD_HEIGHT*0.5f,0f,HEAD_WIDTH,HEAD_HEIGHT,HEAD_LENGTH);

        gl.glLoadIdentity();
        gl.glPopMatrix();
    }

    public void walkAnimation(){
        animRot += animStrength;

        if(animRot >= 60 || animRot <= -60){
            animStrength = -animStrength;
        }
    }

    public void resetWalkAnim(){
        animRot = 0f;
    }

    private float lerp(float start,float end, float percent){
        if(end >= start && start >= end - 1){return end;}
        else if(end <= start && start <= end + 1){return end;}
        return start + percent*(start - end);
    }

    public static float minY (){
        return LEG_HEIGHT + BODY_HEIGHT*0.5f;
    }
}
