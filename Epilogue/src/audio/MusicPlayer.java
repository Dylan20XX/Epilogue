package audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;

public class MusicPlayer {

	// Variables for the music
	public static Clip clip;
	public static boolean mute = false;
	static BooleanControl muteMusic;
	
	// Methods that actually create the music
	public static void playMusic(String musicLocation) {

		try {
			// Make the method and pass the variables to the method
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(MusicPlayer.class.getResourceAsStream(musicLocation));
			clip = AudioSystem.getClip();
			clip.open(audioInput);
			clip.loop(Clip.LOOP_CONTINUOUSLY);

			muteMusic = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
			muteMusic.setValue(mute);

			clip.start();// start the music

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// Method to stop the music
	public static void StopMusic() {
		// Stop the music
		clip.stop();

	}

	public static void changeMute() {
		mute = !mute;
		muteMusic.setValue(mute);
	}

}
