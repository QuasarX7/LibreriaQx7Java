package it.quasar_x7.java.Sistema;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

/**
 *
 * @author ninja
 */
public class IO {
    
    /*********************************************
     * Legge fino al caratere invio
     * 
     * @return 
     *********************************************/
    static public String tastiera() {
         Scanner s= new Scanner (System.in);
         String t ="";
         t=s.nextLine();
         s.close();
         return t;
         
    }
        
    static public void video(Object output) { 
        System.out.print(output);
    } 
    
    
    /************************************************
     * 
     * @param fileJPG nome del file JPEG, se null non 
     * genera il file
     * @param x
     * @param y
     * @param lunghezza
     * @param altezza
     * @return
     * @throws AWTException
     * @throws IOException 
     ***************************************************/
   static public BufferedImage catturaVideo(String fileJPG,int x, int y, int lunghezza,int altezza) throws AWTException, IOException { 
        GraphicsEnvironment environment =GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = environment.getDefaultScreenDevice();
        
        Robot robot = new Robot(screen);//throws AWTException
        BufferedImage immagine = robot.createScreenCapture(new Rectangle(x, y, lunghezza, altezza));
           
        if(fileJPG!=null){
            OutputStream out = new FileOutputStream(fileJPG);
           // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            //encoder.encode(immagine);//throws  IOException 
        }  
        
        return immagine;
    }
    
  
}
