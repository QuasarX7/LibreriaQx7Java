
package it.quasar_x7.java.audio;

import java.io.File;
import java.net.URL;
import javax.sound.sampled.*;

/**
 *
 * @author ninja
 */
public class Audio {

    private Clip clip=null;
    
    public Audio(String file) throws Exception {
        // specify the sound to play
        // (assuming the sound can be played by the audio system)
        File soundFile = new File(file);
        AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);

        // load the sound into memory (a Clip)
        DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(sound);

        // due to bug in Java Sound, explicitly exit the VM when
        // the sound has stopped.
        clip.addLineListener(new LineListener() {
          public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.STOP) {
              event.getLine().close();
            }
          }
        });

    }

    public Audio(URL resource) throws Exception {       
           
        File soundFile = new File(resource.toURI());            
        AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);

        // load the sound into memory (a Clip)
        DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
        clip = (Clip) AudioSystem.getLine(info);
        if(clip!=null)
            clip.open(sound);
        else return;

        // due to bug in Java Sound, explicitly exit the VM when
        // the sound has stopped.
        clip.addLineListener(new LineListener() {
          public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.STOP) {
              event.getLine().close();
            }
          }
        });
        
    }
    
   
    public void avvia(){
        clip.start();
    }
    
    public void ferma(){
        clip.close();
    }
    
    public void ripeti(int i){
        clip.loop(i);
    }
        
}
