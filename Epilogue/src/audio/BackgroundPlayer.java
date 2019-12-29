package audio;

import java.io.BufferedInputStream;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;

public class BackgroundPlayer {

        // Variables for the music
		public static Clip clip;
        public static boolean mute = false;
        static BooleanControl muteBGM;

        // Methods that actually create the music
        public static void playAudio(String audioLocation) {

                try {

                        // Make the method and pass the variables to the method
                		BufferedInputStream bufInput = new BufferedInputStream(MusicPlayer.class.getResourceAsStream(audioLocation));
                		AudioInputStream audioInput = AudioSystem.getAudioInputStream(bufInput);
                        clip = AudioSystem.getClip();
                        clip.open(audioInput);

                        muteBGM = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
                        muteBGM.setValue(mute);

                        clip.start();// start the music

                } catch (Exception ex) {
                        ex.printStackTrace();
                }

        }

        // Method to stop the music
        public static void StopAudio() {
                // Stop the music
        	if(clip!=null)
                clip.stop();

        }

        public static void changeMute() {
                mute = !mute;
                muteBGM.setValue(mute);
        }

}
