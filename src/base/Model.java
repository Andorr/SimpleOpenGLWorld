package base;

import com.jogamp.opengl.GL2;

import javax.naming.spi.DirectoryManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Model extends Object{

    private Vector[] vs;
    private Vector[] fs;
    private Vector[] colors;
    private static GL2 gl;
    private Vector color;
    private Vector rotation;

    public Model (Vector position, Vector size, Vector rotation, Vector color, String fileName, GL2 gl){
        super(position,size);
        loadModel(fileName);
        this.gl = gl;
        initializeColors();
        this.color = color;
        this.rotation = rotation;
    }

    private void loadModel(String fileName){
        try{
            FileReader model = new FileReader(new File(fileName + ".obj"));
            BufferedReader br = new BufferedReader(model);
            for(int i = 0; i < 4; i++){
                br.readLine();
            }

            ArrayList<Vector> vertexes = new ArrayList<Vector> ();
            ArrayList<Vector> faces = new ArrayList<Vector> ();

            String line = br.readLine();
            while(line != null){
                if(line.startsWith("v ")){
                    line = line.substring(2);
                    if(line.startsWith(" ")){line = line.substring(1); System.out.print("\nSubstring");}
                    String[] coordinates = line.split(" ");
                    Vector vertex = new Vector(Float.parseFloat(coordinates[0]),Float.parseFloat(coordinates[1]),Float.parseFloat(coordinates[2]));
                    vertexes.add(vertex);
                }
                else if(line.startsWith("f ")){
                    line = line.substring(2);

                    String[] coordinates = line.split( " ");
                    float[] indexes  = new float[coordinates.length];
                    for(int i = 0; i < coordinates.length; i++){
                        String[] vertexIndexes = coordinates[i].split("/");
                        indexes[i] = Float.parseFloat(vertexIndexes[0]);
                    }
                    faces.add(new Vector(indexes));
                }
                line = br.readLine();
            }
            br.close();
            Vector[] vertexArray = new Vector[vertexes.size()];
            vs = vertexes.toArray(vertexArray);
            Vector[] faceArray = new Vector[faces.size()];
            fs = faces.toArray(faceArray);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(){
        gl.glPushMatrix();
        gl.glTranslatef(position().x(),position().y(),position().z());
        gl.glRotatef(rotation.y(),0,1,0);
        for(int i = 0; i < fs.length; i++){
            Vector c = getColor(color,i);

            gl.glBegin(GL2.GL_POLYGON);
                float[] indexes = fs[i].getValues();
                for(int k = 0; k < indexes.length; k++){
                    int index = (int)indexes[k] - 1;
                    Vector vertex = vs[index];
                    gl.glColor3f(c.x() + vertex.z()*0.1f,c.y() - vertex.y()*0.1f,c.z() - vertex.z()*0.1f);
                    gl.glVertex3f(vertex.x(), vertex.y(),vertex.z());
                }
            gl.glEnd();
        }
        gl.glPopMatrix();
    }

    private void initializeColors(){
        colors = new Vector[fs.length];
        for(int i = 0; i < colors.length; i++){
            colors[i] = Draw.randomColor();
        }
    }

    private Vector getColor(Vector color, int i){
        float r = color.x() + (float)Math.sin(i*10)/10f;
        float g = color.y() + (float)Math.sin(i*10)/10f;
        float b = color.z() + (float)Math.sin(i*10)/10f;
        return new Vector(r,g,b);
    }
}
