package audio;

import java.io.BufferedInputStream;

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
		String streamLocation = "";
		if(musicLocation.charAt(0) == '/')
			streamLocation = musicLocation;
		else
			streamLocation = "/" + musicLocation;
			
		try {
			// Make the method and pass the variables to the method
			BufferedInputStream bufInput = new BufferedInputStream(MusicPlayer.class.getResourceAsStream(streamLocation));
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(bufInput);
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
