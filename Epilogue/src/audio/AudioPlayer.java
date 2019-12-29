package audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;

public class AudioPlayer {

        // Variables for the music
        static Clip clip;
        static boolean mute = false;
        static BooleanControl muteAudio;

        // Methods that actually create the music
        public static void playAudio(String audioLocation) {

                try {

                        // Make the method and pass the variables to the method
                        File Sound = new File(AudioPlayer.class.getResource(audioLocation).toURI());
                        AudioInputStream audioInput = AudioSystem.getAudioInputStream(Sound);
                        clip = AudioSystem.getClip();
                        clip.open(audioInput);

                        muteAudio = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
                        muteAudio.setValue(mute);
                        
                        clip.start();// start the music

                } catch (Exception ex) {
                        ex.printStackTrace();
                }

        }

        // Method to stop the music
        public static void StopAudio() {
                // Stop the music
                clip.stop();

        }

        public static void changeMute() {
                mute = !mute;
                muteAudio.setValue(mute);
        }

}
