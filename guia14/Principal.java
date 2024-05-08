package guia14;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public class Principal extends GLJPanel implements GLEventListener, KeyListener {
	private static final long serialVersionUID = 1L;
	float pos_x= 0;
	float f1pos_x= -1f;  
	float f1pos_y= -0.07f;
	float f2pos_x= -0.5f; 
	float f2pos_y= 0.9f;
	
	private String[] textureFile = { 
			"examen2_02.png"
	};
	
	private Texture[] textures = new Texture[this.textureFile.length];
	
	MisObjetos bmedio=new MisObjetos(-0.20f, -0.60f, 1.20f, 0.40f);
	MisObjetos fna  =new MisObjetos(-0.5f,  0.90f, 0.20f, 0.15f);
	MisObjetos fro =new MisObjetos(-1.00f,-0.13f, 0.20f, 0.15f);	
	
	private float[] trianCoords = { 
			0,0.80f,0,
			0,0,0,
			0.40f,0.00f,0,
			0.40f,0.80f,0, //cesped 1
			0.0f,1.2f,0,
			0.0f,0.0f,0,
			0.40f,0.0f,0,
			0.40f,1.2f,0,//cesped 2  
			0.00f,0.2f,0,
			0.00f,0.0f,0,
			0.15f,0.0f,0,
			0.15f,0.2f,0,  //fantasma rojo
			0.00f,0.20f,0,
			0.00f,0.00f,00f,
			0.15f,0.00f,0,
			0.15f,0.20f,0, //fantasma naranja
	};
		private float[] triangleTexture = {
			0.428f,0.986f,0,
			0.428f,0.607f,0,
			0.975f,0.607f,0,
			0.975f,0.986f,0, 
			0.428f,0.607f,0,
			0.428f,0.986f,0,
			0.975f,0.986f,0,
			0.975f,0.607f,0, 
			0.553f,0.584f,0,
			0.553f,0.305f,0,
			0.838f,0.305f,0,
			0.838f,0.584f,0,	
			0.553f,0.288f,0,
			0.553f,0.017f,0,
			0.832f,0.017f,0,
			0.832f,0.288f,0, 
		};
		private FloatBuffer trianCoordBuffer = Buffers.newDirectFloatBuffer(trianCoords);
		private FloatBuffer triangleTextureBuffer = Buffers.newDirectFloatBuffer(triangleTexture);
	
	public static void main(String[] args) {
        JFrame window = new JFrame("Programación Grafica.");
        Principal panel = new Principal();
        window.setContentPane(panel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        panel.requestFocusInWindow();
        FPSAnimator animator = new FPSAnimator(panel, 400, true);
	    animator.start();
	}
	public Principal() {
        super( new GLCapabilities(null) ); // Makes a panel with default OpenGL "capabilities".
        setPreferredSize( new Dimension(640,480) );
        addGLEventListener(this); // A listener is essential! The listener is where the OpenGL programming lives.
        addKeyListener(this);	
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(1f, 1f, 1f, 0.0f);		
		gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();	
		gl.glVertexPointer( 3, GL2.GL_FLOAT, 0, trianCoordBuffer );	
		gl.glTexCoordPointer( 3,GL2. GL_FLOAT, 0, triangleTextureBuffer );
		
		gl.glEnableClientState( GL2.GL_VERTEX_ARRAY ); 
		gl.glEnableClientState( GL2.GL_TEXTURE_COORD_ARRAY );
		
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();	
		textures[0].bind(gl);
					
		gl.glPushMatrix();	
		gl.glTranslatef(f1pos_x,f1pos_y, 0);		
		gl.glDrawArrays(GL2.GL_QUADS,8,4);
		gl.glPopMatrix();
		
		
		
		gl.glPushMatrix();	
		gl.glTranslatef(f2pos_x,f2pos_y, 0);
		gl.glDrawArrays(GL2.GL_QUADS,12,4);
		gl.glPopMatrix();
		
//		//Area verde 1
//		gl.glPushMatrix();	
//		gl.glTranslatef(-1f, .20f, 0);		
//		gl.glDrawArrays(GL2.GL_QUADS,0,4);	
//		gl.glPopMatrix();
//		
//		//Area verde 1
//				gl.glPushMatrix();	
//				gl.glTranslatef(.60f, .20f, 0);		
//				gl.glDrawArrays(GL2.GL_QUADS,0,4);	
//				gl.glPopMatrix();
//				
//				gl.glPushMatrix();	
//				gl.glTranslatef(-1f, -1f, 0);		
//				gl.glDrawArrays(GL2.GL_QUADS,0,4);	
//				gl.glPopMatrix();
//				
//				gl.glPushMatrix();	
//				gl.glTranslatef(.60f, -1f, 0);		
//				gl.glDrawArrays(GL2.GL_QUADS,0,4);	
//				gl.glPopMatrix();
//		//Area verde Bloque medio
//		
//		gl.glPushMatrix();			
//		gl.glTranslatef(-0.2f, -0.6f, 0);
//		gl.glDrawArrays(GL2.GL_QUADS,4,4);	
//		gl.glPopMatrix();
//		 bloqueMedio();
		 fantasmaNaranja();
	
		gl.glFlush();
//		if (f2pos_y < -1) {
//			f2pos_y=0.9f;
//		}else {
//			f2pos_y -= 0.0003;
//		}
	}
	public void bloqueMedio(){
		if((((f1pos_x+fro.ancho)>=bmedio.posX)&&(f1pos_x<=(bmedio.posX+bmedio.ancho)))) {		
			if((((f1pos_y+fro.alto)>=(bmedio.posY))&&(f1pos_y<=(bmedio.posY+bmedio.alto)))) {
				gameOver();
				f1pos_x=-1f;
				f1pos_y=-0.07f;		
			}
		}
	}
	public void fantasmaNaranja(){
		if((((f1pos_x+fro.ancho)>=f2pos_x)&&(f1pos_x<=(f2pos_x+fna.ancho)))) {		
			if((((f1pos_y+fro.alto)>=(f2pos_y))&&(f1pos_y<=(f2pos_y+fna.alto)))) {
				gameOver();
				f1pos_x=-1f;
				f1pos_y=-0.07f;
			}
		}
	}
	public void gameOver() {
		JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
	
	}
	@Override
	public void keyPressed(java.awt.event.KeyEvent e) {		
		int key = e.getKeyCode();	
		System.out.println(key);
		if (key==38) { //arriba			
			f1pos_y +=0.01;  //contador del eje Y
		}
		if (key==40) {//abajo 
			f1pos_y -=0.01;  //contador del eje Y			
		}
		if (key==39) { //derecha
			f1pos_x +=0.01;  //contador del eje X
		}
		if (key==37) {//Izquierda 
			f1pos_x -=0.01;  //contador del eje X
		}
		
		//naranja
		int  key2 = e.getKeyCode();	
		if ( key2==87) { //arriba			
			f2pos_y +=0.01;  //contador del eje Y
		}
		if ( key2==83) {//abajo 
			f2pos_y -=0.01;  //contador del eje Y			
		}
		if ( key2==68) { //derecha
			f2pos_x +=0.01;  //contador del eje X
		}
		if ( key2==65) {//Izquierda 
			f2pos_x -=0.01;  //contador del eje X
		}
	}
	
	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
        // called when the panel is created
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glOrtho(-4, 4, -2, 2, -2, 2);  // simple orthographic projection
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glClearColor( 0.5F, 0.5F, 0.5F, 1 );
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        for (int i = 0; i < this.textureFile.length; i++) {
    		BufferedImage img;
    		try {
    			img = ImageIO.read(new File("IMG/" + textureFile[i]));
    			ImageUtil.flipImageVertically(img);

    			textures[i] = AWTTextureIO.newTexture(GLProfile.getDefault(), img, true);
    			textures[i].setTexParameteri(gl2, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
    			textures[i].setTexParameteri(gl2, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
    			textures[i].enable(gl2);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
	}
	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void keyTyped(java.awt.event.KeyEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyReleased(java.awt.event.KeyEvent e) {
		// TODO Auto-generated method stub	
	}
}
class MisObjetos {
	float posX;
	float posY;
	float alto;
	float ancho;
	
	public MisObjetos(float ejeX, float ejeY, float alto, float ancho) {
		super();
		this.posX=ejeX;
		this.posY=ejeY;
		this.alto=alto;
		this.ancho=ancho;
	}	
}


